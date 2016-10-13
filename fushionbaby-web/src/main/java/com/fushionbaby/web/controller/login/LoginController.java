package com.fushionbaby.web.controller.login;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.AdvertisementConfigConstant;
import com.fushionbaby.common.constants.CartConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.ShoppingCartSkuUser;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.log.model.LogMemberLogin;
import com.fushionbaby.log.service.LogMemberLoginService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.model.MemberGradeConfig;
import com.fushionbaby.member.service.MemberExtraInfoService;
import com.fushionbaby.member.service.MemberGradeConfigService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sysmgr.model.SysmgrAdvertisement;
import com.fushionbaby.sysmgr.service.SysmgrAdvertisementService;
import com.fushionbaby.web.util.ImageConstantWeb;
import com.fushionbaby.web.util.LoginUtilsConstant;

/***
 * 登录
 * 
 * @author King 索亮
 * 
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	/** 记录日志 */
	private static final Log logg = LogFactory.getLog(LoginController.class);

	/** 注入会员 */
	@Autowired
	private MemberService<Member> memberService;

	/** 注入登录日志 */
	@Autowired
	private LogMemberLoginService logMemberLoginService;

	/*** 注入额外会员信息 ***/
	@Autowired
	private MemberExtraInfoService<MemberExtraInfo> memberExtraInfoService;
	
	@Autowired
	private MemberGradeConfigService memberGradeService;
	/**
	 * Redis操作购物车,未登录会员使用的购物车
	 */
	@Autowired
	private CartRedisUserService<ShoppingCartSkuUser> cartRedisService;
	/**会员信息*/
	@Autowired
	private UserFacade userFacade;

	/**广告，*/
	@Autowired
	private SysmgrAdvertisementService<SysmgrAdvertisement> adService;
	/***
	 * 跳转到登录页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("wx_redirectUrl", LoginUtilsConstant.WEIXIN_REDIRECT_URL);
		model.addAttribute("wx_appid", LoginUtilsConstant.WEIXIN_CLIENT_ID);
		setLoginAd(model);
		return "login";
	}

	private void setLoginAd(Model model) {
		List<SysmgrAdvertisement> adList=this.adService.getListByAdCode(AdvertisementConfigConstant.WEB_LOGIN_AD);
		     if(adList.size()>0){
		    	 SysmgrAdvertisement ad=adList.get(0);
		    	 model.addAttribute("login_ad_url",ImageConstantWeb.SYS_AD_PICTURE_SERVER+"/"+ad.getPicturePath() );
		    	 model.addAttribute("ad_link", ad.getUrl());
		    	 }else{
		    		 model.addAttribute("login_ad_url","null");
		    		 model.addAttribute("ad_link", "#");
		    	 }
	}

	/***
	 * 用户点击登录按钮
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Object toLogin(@RequestParam(value = "login_name", defaultValue = "") String login_name,
			@RequestParam(value = "password", defaultValue = "") String password,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String visitKey = CookieUtil.getCookieValue(request, CartConstant.COOKIE_VISIT_KEY);
			//如果客户端请求中没有visitKey为客户端产生新的
			if(StringUtils.isBlank(visitKey)){
				String uuid=RandomNumUtil.getUUIDString()+RandomNumUtil.getUUIDString();
				//暂时用默认cookie过期时间
				CookieUtil.setCookie((HttpServletResponse)response,CartConstant.COOKIE_VISIT_KEY,uuid,CookieConstant.COOKIE_MAX_AGE*12);
				visitKey = uuid;
			}
			UserDto user = null;
			Member member = memberService.loginByLoginNamePassword(login_name,MD5Util.MD5(password));
			/** 是否存在该用户*/
			if(member==null){
				/** 登录失败信息添加到日志 */
				addLoginFailMessageToLog(login_name,GetIpAddress.getIpAddr(request));
				return Jsonp.error("您输入的密码和用户名不匹配，请重新输入!");
			}
			/**判断用户是否禁用*/
			if(StringUtils.equals(member.getDisable(), CommonConstant.YES)){
				/** 登录失败信息添加到日志 */
				logMemberLoginService.add(null, login_name, GetIpAddress.getIpAddr(request),CommonConstant.NO);
				return Jsonp.error("请确认您的当前操作是否合法!");
			}
			updateExtraInfoLoginDaysAndGradeCode(member.getId());
				
			MemberExtraInfo memberExtraInfo = memberExtraInfoService.findByMemberId(member.getId());
			String gradeName = "";
			if (ObjectUtils.notEqual(null, memberExtraInfo) && !StringUtils.isBlank(memberExtraInfo.getGradeCode() )) {
				MemberGradeConfig memberGrade = memberGradeService.findByGradeCode(memberExtraInfo.getGradeCode());
				gradeName = memberGrade.getGradeName();
			}
			/** 登录成功信息添加到日志 */
			logMemberLoginService.add(member.getId(), login_name,
					GetIpAddress.getIpAddr(request), CommonConstant.YES);
			// 获取唯一标识码
			String sid = RandomNumUtil.getCharacterAndNumber(WebConstant.UNIQUE_CODE);
			user=setLoginUserInfo(sid,member,gradeName);
			CookieUtil.setCookie(response, CookieConstant.COOKIE_SID, sid,CookieConstant.COOKIE_LOGIN_AGE);
			SessionCache.put(sid, user);
			cartRedisService.loginCart(visitKey, member.getId());
					
			return Jsonp_data.success(user);
		} catch (Exception e) {
			logg.error("LoginController登陆模块有误" + e);
			return Jsonp.error();
		}
	}

	/***
	 * 获取登陆成功的用户的信息，
	 * @param sid
	 * @param member
	 * @return
	 */
	private UserDto setLoginUserInfo(String sid, Member member,String gradeName) {
		UserDto userDto=new UserDto();
		userDto.setSid(sid);
		userDto.setMember_id(member.getId());
		userDto.setLogin_name(member.getLoginName());
		int epoints = (member.getEpoints() == null) ? 0 : member.getEpoints();
		userDto.setEpoints(epoints);
		userDto.setImg_path(ObjectUtils.equals(member.getImgPath(), null) ? StringUtils.EMPTY
				: member.getImgPath());
		userDto.setGradeName(gradeName);
		return userDto;
	}

	/***
	 * 将登陆失败的信息添加到日志中
	 * @param login_name
	 * @param ipAddr
	 */
	private void addLoginFailMessageToLog(String login_name, String ipAddr) {
		LogMemberLogin logMemberLogin = new LogMemberLogin();
		logMemberLogin.setMemberName(login_name);
		logMemberLogin.setIpAddress(ipAddr);// ip地址
		logMemberLogin.setLoginStatus(CommonConstant.NO);
		logMemberLoginService.add(logMemberLogin);	
	}
	/**
	 * 根据登陆日志更新登陆天数和等级编码
	 * @param member
	 */
	private void updateExtraInfoLoginDaysAndGradeCode(Long memberId){
		/***首先判断登陆天数****/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(new Date());
		List<LogMemberLogin> logMemberList = logMemberLoginService.findByMemberIdAndLoginTime(memberId, today);
		if (CollectionUtils.isEmpty(logMemberList)) {
			MemberExtraInfo memberExtraInfo = memberExtraInfoService.findByMemberId(memberId);
			memberExtraInfo.setLoginDays(memberExtraInfo.getLoginDays() + 1);
			int loginDays = memberExtraInfo.getLoginDays();
			
			for(int i = 1; i < 36;i++){
				if(loginDays == i * i){
					memberExtraInfo.setGradeCode(i+"");
				}
			}
			
			memberExtraInfoService.updateByMemberId(memberExtraInfo);
		}
	}
	
	/**
	 * 获取登录的用户信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getUserInfo")
	public Object getUserName(HttpServletRequest request) {
		UserDto userDto = null;
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			userDto = userFacade.getLatestUserBySid(sid);
			if (ObjectUtils.equals(userDto, null)) {
				return Jsonp.error("用户未登录");
			}
		} catch (Exception e) {
			logg.error("LoginController获取登陆用户信息有误" + e);
			return Jsonp.error("登录用户信息有误");
		}
		return Jsonp_data.success(userDto);
	}

	/**
	 * 点击忘记密码跳转到忘记密码页面
	 * 
	 * @return
	 */
	@RequestMapping("/toForgetPassword")
	public String toForgetPassword() {
		return "back-password";
	}

	/***
	 * 用户点击忘记密码时请求
	 */
	@ResponseBody
	@RequestMapping("clickForgetPassword")
	public Object clickForgetPassword(HttpServletResponse response) {
		try {
			/** 获取32位唯一标识码 */
			String forgetPassword_code = RandomNumUtil
					.getCharacterAndNumber(WebConstant.UNIQUE_CODE);
			CookieUtil.setCookie(response, CookieConstant.FORGETPASSWORD_COOKIE, forgetPassword_code);
			return Jsonp.success();
		} catch (Exception e) {
			logg.error("LoginController忘记密码请求有误" + e);
			return Jsonp.error("获取唯一标识码失败!");
		}
	}

	/*** 点击立即注册时跳转到注册页面 */
	@RequestMapping("/toRegister")
	public String toRegister() {
		return "register";
	}

	/***
	 * 用户点击注册时请求
	 */
	@ResponseBody
	@RequestMapping("clickRegister")
	public Object clickRegister(HttpServletResponse response) {
		try {
			/** 获取32位唯一标识码 */
			String register_code = RandomNumUtil.getCharacterAndNumber(WebConstant.UNIQUE_CODE);
			CookieUtil.setCookie(response, CookieConstant.REGISTER_COOKIE, register_code);
			return Jsonp.success();
		} catch (Exception e) {
			logg.error("LoginController注册请求有误" + e);
			return Jsonp.error("获取唯一标识码失败!");
		}
	}

	/***
	 * 退出登录 -- 移除Cookie和SessionCache responseCode 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("loginOut")
	public Object loginLogOut(HttpServletRequest request, HttpServletResponse response) {
		try {
			Cookie cookie = CookieUtil.getCookie(request, CookieConstant.COOKIE_SID);
			if (!ObjectUtils.equals(cookie, null)) {
				String sid = cookie.getValue();
				CookieUtil.removeCookie(response, cookie);// 移除Cookie
				// 移除SessionCache中的用户对象
				SessionCache.remove(sid);
			} else {
				return Jsonp.success();// cookie为空用户应该清除了,也返回成功
			}
			return Jsonp.success();
		} catch (Exception e) {
			logg.error("LoginController退出登录有误" + e);
			return Jsonp.error();
		}
	}

	/***
	 * 未登录，提示请先登录
	 * 
	 */
	@ResponseBody
	@RequestMapping("noLogin")
	public Object noLogin() {
		return Jsonp.noLoginError("请先登录！");
	}

	
	
	@RequestMapping("getWebLoginAd")
	public  void getWebLoginAd(OutputStream output){
		try {
		     List<SysmgrAdvertisement> adList=this.adService.getListByAdCode(AdvertisementConfigConstant.WEB_LOGIN_AD);
		     if(adList.size()>0){
		    	 SysmgrAdvertisement ad=adList.get(0);
		    	// FileUtils.copyFile(new File(ImageConstant.SYS_AD_PICTURE_SERVER+"/"+ad.getPicturePath()),output);
		    	 FileUtils.copyFile(new File(ImageConstantWeb.SYS_AD_PICTURE_SERVER, ad.getPicturePath()), output);
		     }
		     System.out.println(adList.size());
		} catch (IOException e) {
			logg.error("获得登陆页面图片失败", e);
		}
		
	}
	
	
	public static void main(String[] args) {
		int a=(int) Math.sqrt(82d);
		System.out.println(a);
	}
	
	
	
}
