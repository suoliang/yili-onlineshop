package com.fushionbaby.app.controller.login;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.fushionbaby.app.util.RSAUtil;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.ChannelConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.VerificationConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.dto.app.OtherLoginBackInfoDto;
import com.fushionbaby.common.dto.app.OtherLoginUser;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.service.MemberExtraInfoService;
import com.fushionbaby.member.service.MemberService;
import com.google.gson.Gson;

/***
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/login")
public class OtherLoginController {

	/** 注入会员 */
	@Autowired
	private MemberService<Member> memberService;

	/**
	 * Redis操作购物车,未登录会员使用的购物车
	 */
	@Autowired
	private CartRedisUserService<ShoppingCartSku> cartRedisService;

	/** 额外信息表 */
	@Autowired
	private MemberExtraInfoService<MemberExtraInfo> memberExtraInfoService;
	
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@ResponseBody
	@RequestMapping("loginAsWeiXin")
	public Object loginAsWeiXin(
			@RequestParam("userjson") String userjson,
			@RequestParam("visitKey") String visitKey,
			@RequestParam("storeVisitKey") String storeVisitKey,
			@RequestParam("source") String source) {
		
		OtherLoginBackInfoDto infoDto= getInfoDto(userjson, source, visitKey,storeVisitKey);
		infoDto.setSourceChanel(ChannelConstant.WX_CHANNEL);
		return loginCommonPart(infoDto);
	}

	
	@ResponseBody
	@RequestMapping("loginAsQQ")
	public Object loginAsQQ(
			@RequestParam("userjson") String userjson,
			@RequestParam("source") String source,
			@RequestParam("visitKey") String visitKey,
			@RequestParam("storeVisitKey") String storeVisitKey){
		OtherLoginBackInfoDto infoDto=getInfoDto(userjson, source, visitKey,storeVisitKey);
		infoDto.setSourceChanel(ChannelConstant.QQ_CHANNEL);
		return loginCommonPart(infoDto);
	}
	
	
	
	@ResponseBody
	@RequestMapping("loginAsSina")
	public Object loginAsSina(
			@RequestParam("userjson") String userjson,
			@RequestParam("source") String source,
			@RequestParam("visitKey") String visitKey,
			@RequestParam("storeVisitKey") String storeVisitKey){
		OtherLoginBackInfoDto infoDto=getInfoDto(userjson,source,visitKey,storeVisitKey);
		infoDto.setSourceChanel(ChannelConstant.SINA_CHANNEL);
		return loginCommonPart(infoDto);
	}
	
	@ResponseBody
	@RequestMapping("loginAsZFB")
	public Object loginAsZFB(
			@RequestParam("userjson") String userjson,
			@RequestParam("source") String source,
			@RequestParam("visitKey") String visitKey,
			@RequestParam("storeVisitKey") String storeVisitKey){
		OtherLoginBackInfoDto infoDto=getInfoDto(userjson,source,visitKey,storeVisitKey);
		infoDto.setSourceChanel(ChannelConstant.ZFB_CHANNEL);
		return loginCommonPart(infoDto);
	}
	
	private OtherLoginBackInfoDto getInfoDto(String userjson, String source, String visitKey,String storeVisitKey){
		OtherLoginBackInfoDto infoDto=new OtherLoginBackInfoDto();
		infoDto.setSource(source);
		infoDto.setUserjson(userjson);
		infoDto.setVisitKey(visitKey);
		infoDto.setStoreVisitKey(storeVisitKey);
		return infoDto;
		
	}
	
	
	
	private Object loginCommonPart(OtherLoginBackInfoDto infoDto){
		Gson gson = new Gson();
		OtherLoginUser otherUser = gson.fromJson(infoDto.getUserjson(), OtherLoginUser.class);
		String openid = otherUser.getOpenid();
		String name = otherUser.getNickname();
		if (StringUtils.isBlank(openid)) {
			return Jsonp.error("openid数据有误");
		} else {
			String sid = RandomNumUtil.getCharacterAndNumber(WebConstant.UNIQUE_CODE);
			Member member = this.memberService.findByOpenId(openid);
			if (member == null) {
				/** 保存到数据库 */
				Long id = memberService.addLoginMember(openid, infoDto.getSource(),infoDto.getSourceChanel());
				member = this.memberService.findById(id);
				/** 添加到额外会员表 */
				memberExtraInfoService.addMemberExtraInfo(id);
			}
		

			/** 将用户登陆信息存入缓存 */
			UserDto user = new UserDto();
			/**是否有如意消费卡*/
			AlabaoAccount account=alabaoAccountService.findByMemberId(member.getId());
			//	AlabaoAccount account=alabaoAccountService.findByAccount(member.getLoginName());
			user.setIsBind(CommonConstant.NO);
			user.setIsValidate(CommonConstant.NO);
			if(ObjectUtils.notEqual(null, account)){
				user.setAccount(account.getAccount());
				user.setTrueName(account.getTrueName());
				user.setIsValidate(VerificationConstant.IS_VALIDATE_STATUS_SUCCESS.equals(account.getIsValidate())?CommonConstant.YES:CommonConstant.NO);
				user.setIsBind(CommonConstant.YES);
			}
		    String alabaoSid=	setAlabaoLogin(account);
		    user.setAlabaoSid(alabaoSid);
			user.setLoginName(name);
			user.setSid(sid);
			user.setMemberId(member.getId());
			BigDecimal epoints = (BigDecimal) ((member.getEpoints() == null) ? 0 : member.getEpoints());
			user.setEpoints(epoints);
			//user.setImgPath(ImageConstantApp.MEMBER_IMAGE_SERVER_PATH + "/"+ member.getImgPath());
			BeanNullConverUtil.nullConver(user);
			SessionCache.put(sid, new Gson().toJson(user));
			
			/** 将购物车里面的东西加入到 该用户中 */
			cartRedisService.loginCart(infoDto.getVisitKey(),infoDto.getStoreVisitKey(), user);
			
			/**把登陆用户和sid关联*/
			SessionCache.put(AppConstant.APP_SID+openid, sid);
			return Jsonp_data.success(user);
		}
		
		
	}
	
	/***
	 * 如果会员有 如意消费卡 则把该卡的相关信息放到缓存中
	 * @param account
	 */
		private String setAlabaoLogin(AlabaoAccount account) {
			String  alabaoSid=""; 
			if(ObjectUtils.notEqual(account, null)){
				alabaoSid=RandomNumUtil.getCharacterAndNumber(AppConstant.UNIQUE_CODE);
				AlabaoUserDto alabaoUserDto = new AlabaoUserDto();
				alabaoUserDto.setAccount(account.getAccount());
				alabaoUserDto.setAlabaoId(account.getId());
				alabaoUserDto.setMemberId(account.getMemberId());
				alabaoUserDto.setAlabaoSid(alabaoSid);
				alabaoUserDto.setMemberName(account.getTrueName());
				alabaoUserDto.setIsValidate(VerificationConstant.IS_VALIDATE_STATUS_SUCCESS.equals(account.getIsValidate())?CommonConstant.YES:CommonConstant.NO);
				alabaoUserDto.setID(account.getIdentityCardNo());
				AlabaoSessionCache.put(alabaoSid, new Gson().toJson(alabaoUserDto));
				 /**如意消费卡 alabaoSid和账户关联*/
				AlabaoSessionCache.put(AppConstant.APP_ALABAO_SID+account.getAccount(), alabaoSid);
			}
			return alabaoSid;
		}
	
	@RequestMapping("getUserInfo")
	@ResponseBody
	public Object getUserInfo(@RequestParam("userId") String userId,HttpServletResponse response) {
		String userInfo=RSAUtil.ZFBTrustLogin(userId);
	    return Jsonp_data.success(userInfo);
	}

}
