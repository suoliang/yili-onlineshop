package com.fushionbaby.app.controller.login;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.VerificationConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.log.service.LogMemberLoginService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuPriceService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.google.gson.Gson;

/**
 * 登录
 * @author King 索亮
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	/**注入日志*/
	private static final Log LOGGER = LogFactory.getLog(LoginController.class);
	
	/**注入会员*/
	@Autowired
	private MemberService<Member> memberService;
	
	/**注入登录日志*/
	@Autowired
	private LogMemberLoginService logMemberLoginService;

	/**注入短信*/
	@Autowired
	private SmsService<Sms> smsService;
	
	/**
	 * Redis操作购物车,未登录会员使用的购物车
	 */
	@Autowired
	private CartRedisUserService<ShoppingCartSku> cartRedisService;
	
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> shoppingCartSkuUserService;
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	@Autowired
	private SkuPriceService<SkuPrice> skuPriceService;
	


	/***
	 * 用户登录接口
	 * @param visit_key 同步购物车使用
	 * @param username	账号
	 * @param password	密码
	 * @param macAddr  手机mac地址
	 * @return User对象
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("enter")
	public Object userLogin(
			String userName,String password,String sourceCode,
			@RequestParam(value="visitKey",defaultValue="")String visitKey,
			@RequestParam(value="storeVisitKey",defaultValue="")String storeVisitKey,
			@RequestParam(value="macAddr",defaultValue="") String macAddr) {
		try {
			UserDto user = null;
			if (CheckIsEmpty.isEmpty(userName, password, sourceCode)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			if (!(StringUtils.equals(sourceCode, SourceConstant.ANDROID_CODE) || StringUtils.equals(sourceCode, SourceConstant.IOS_CODE))) {
				return Jsonp.error("source来源参数有误!");
			}
			if (memberService.findByLoginName(userName) == null) {
				return Jsonp.error("您输入的用户名和密码不匹配!");
			}
			Member member = memberService.loginByLoginNamePassword(userName,MD5Util.MD5(password));
			if (member == null) {
				return Jsonp.error("您输入的用户名和密码不匹配!");
			}
			//判断用户是不是禁用状态
			if(StringUtils.equals(member.getDisable(), CommonConstant.YES)) {
				/**登录失败信息添加到日志*/
				logMemberLoginService.add(null, userName, AppConstant.MOBILEPHONE_IP_ADDRESS, CommonConstant.NO);
				return Jsonp.error("请确认您的当前操作是否合法!");
			}
			//登陆成功日志信息
			logMemberLoginService.add(member.getId(), userName, AppConstant.MOBILEPHONE_IP_ADDRESS, CommonConstant.YES);
			user = new UserDto();
			/**是否有如意消费卡*/
			//AlabaoAccount account=alabaoAccountService.findByMemberId(member.getId());
			AlabaoAccount account=alabaoAccountService.findByAccount(userName);
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
			String sid = RandomNumUtil.getCharacterAndNumber(AppConstant.UNIQUE_CODE);
			user.setSid(sid);
			user.setMemberId(member.getId());
			user.setLoginName(member.getLoginName());
			
			BigDecimal epoints = (BigDecimal) ((member.getEpoints() == null) ? 0 : member.getEpoints());
			user.setEpoints(epoints);
			
			//user.setImgPath(ImageConstantApp.MEMBER_IMAGE_SERVER_PATH+"/"+member.getImgPath());
			SessionCache.put(sid, new Gson().toJson(user));
			/**把 sid和登录用户关联一下*/
			SessionCache.put(AppConstant.APP_SID+member.getLoginName(), sid);
			//SessionCache.put(sid, user, 43200);
			//把Redis缓存中的未登录的购物车信息同步到该用户的购物车中、并清除缓存中的购物车
			cartRedisService.loginCart(visitKey,storeVisitKey,user);
			this.modifyCartSkuPriceByVip(user.getMemberId());/*到购物车中 修改VIP会员商品的价格*/
			BeanNullConverUtil.nullConver(user);
			return Jsonp_data.success(user);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("LoginController用户登陆有误APP" + e);
			return Jsonp.error("出错了，用户登陆失败!");
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
	
	/*到购物车中 修改VIP会员商品的价格*/
	private void modifyCartSkuPriceByVip(Long memberId){
		
		AlabaoAccount alabaoAccount= alabaoAccountService.findByMemberId(memberId);
		if(alabaoAccount==null){
			return;
		}
		ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
		queryCondition.setMemberId(memberId);
		List<ShoppingCartSku>  cartList = shoppingCartSkuUserService.findByQueryCondition(queryCondition);
		if(cartList==null){
			return ;
		}
		for (ShoppingCartSku cartSku : cartList) {
			
			SkuExtraInfo extraInfo = skuExtraInfoService.findBySkuCode(cartSku.getSkuCode());
			if(extraInfo==null || !StringUtils.equals(extraInfo.getIsMemberSku(),CommonConstant.YES)){
				continue;
			}
			SkuPrice skuPrice = skuPriceService.findBySkuCode(cartSku.getSkuCode());
			if(skuPrice==null || skuPrice.getAladingPrice()==null){
				continue;
			}
			cartSku.setCurrentPrice(skuPrice.getAladingPrice());
			cartSku.setLineTotalPrice(skuPrice.getAladingPrice().multiply(BigDecimal.valueOf(cartSku.getQuantity())));
			
			shoppingCartSkuUserService.update(cartSku);
		}
		
	}
	
	/***
	 * 退出登录
	 * response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("logout")
	public Object loginLogOut(
			@RequestParam(value="sid",defaultValue="")String sid){
		try {
			if (CheckIsEmpty.isEmpty(sid)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			//移除SessionCache中的用户对象
			SessionCache.remove(sid);
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("LoginController退出登录有误APP" + e);
			return Jsonp.error();
		}
	}
	
	
	/***
	 * 未登录，提示请先登录
	 * 
	 */
	@ResponseBody
	@RequestMapping("noLogin")
	public Object noLogin(){
		return Jsonp.noLoginError("请先登录！");
	}

	/***
	 * 通过alabaoSid得到阿拉宝对象
	 * @param alabaoSid
	 * @return
	 */
	@RequestMapping("getAlbaoUser")
	@ResponseBody
	public Object getAlbaoUser(@RequestParam("alabaoSid") String alabaoSid){
		AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
		BeanNullConverUtil.nullConver(alabaoUserDto);
		return Jsonp_data.success(alabaoUserDto);
	}
	
	
}
