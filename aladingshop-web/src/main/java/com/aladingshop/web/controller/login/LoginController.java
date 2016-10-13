package com.aladingshop.web.controller.login;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fushionbaby.cache.SessionCache;
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
import com.fushionbaby.facade.biz.common.cart.CartRedisUserFacade;
import com.fushionbaby.facade.biz.common.log.LogMemberLoginFacade;
import com.fushionbaby.facade.biz.common.member.MemberExtraInfoFacade;
import com.fushionbaby.facade.biz.common.member.MemberFacade;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.member.model.Member;
import com.google.gson.Gson;

/***
 * 
 * @description 类描述...web商城登陆
 * @author 徐培峻
 * @date 2015年8月4日下午1:59:43
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private MemberFacade memberFacade;

	@Autowired
	private LogMemberLoginFacade logMemberLoginFacade;

	@Autowired
	private MemberExtraInfoFacade memberExtraInfoFacade;

	@Autowired
	private CartRedisUserFacade cartRedisUserFacade;

	@Autowired
	private UserFacade userFacade;

	private static final Log loginLog = LogFactory.getLog(LoginController.class);

	@RequestMapping("index")
	public String index(Model model) {
		return "login/login";
	}

	@RequestMapping("/main")
	@ResponseBody
	public Object Login(@RequestParam("userName") String userName,@RequestParam("password") String password,HttpServletRequest request,HttpServletResponse response){
		try {
		String visitKey = CookieUtil.getCookieValue(request, CartConstant.COOKIE_VISIT_KEY);
		//如果客户端请求中没有visitKey为客户端产生新的
		if(StringUtils.isBlank(visitKey)){
			String uuid=RandomNumUtil.getUUIDString()+RandomNumUtil.getUUIDString();
			//暂时用默认cookie过期时间
			CookieUtil.setCookie((HttpServletResponse)response,CartConstant.COOKIE_VISIT_KEY,uuid,CookieConstant.COOKIE_MAX_AGE*12);
			visitKey = uuid;
		}
		Member member = memberFacade.loginByLoginNamePassword(userName,MD5Util.MD5(password));
		/** 是否存在该用户*/
		if(member==null){
			/** 登录失败信息添加到日志 */
			logMemberLoginFacade.addToLog(null, userName, GetIpAddress.getIpAddr(request), CommonConstant.NO);
			return Jsonp.error("您输入的密码和用户名不匹配，请重新输入!");
		}
		/**判断用户是否禁用*/
		if(StringUtils.equals(member.getDisable(), CommonConstant.YES)){
			/** 登录失败信息添加到日志 */
			logMemberLoginFacade.addToLog(null, userName, GetIpAddress.getIpAddr(request),CommonConstant.NO);
			return Jsonp.error("请确认您的当前操作是否合法!");
		}
		/**添加到额外信息表*/
		memberExtraInfoFacade.addMemberExtraInfo(member.getId());
		String sid = RandomNumUtil.getCharacterAndNumber(WebConstant.UNIQUE_CODE);
		UserDto user = memberFacade.setLoginUserInfo(sid, member);
		CookieUtil.setCookie(response, CookieConstant.COOKIE_SID, sid, CookieConstant.COOKIE_LOGIN_AGE);
		SessionCache.put(sid, new Gson().toJson(user));
		cartRedisUserFacade.loginCart(visitKey,StringUtils.EMPTY,user);
		logMemberLoginFacade.addToLog(member.getId(), userName, GetIpAddress.getIpAddr(request),CommonConstant.YES);
		/**从当前cookie中获取用户最后浏览页*/
		String url = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_CUURENT_URL);
		if(StringUtils.isBlank(url)||url.contains("code")){
			 url="/html/home.html";
		}
		return Jsonp_data.success(url);
		} catch (Exception e) {
			loginLog.error("web:LoginController.java 登陆模块异常" + e);
			return Jsonp.error();
		}
		
	}
	
	
	
	
	
	
	
	
	@RequestMapping("login")
	public ModelAndView memberLogin(@RequestParam("userName") String userName,
			@RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		UserDto user = login(userName, password, request, response);
		if (Objects.equals(user.getLoginStatus(), WebConstant.LOGIN_SUCCESS)) {
			/**从当前cookie中获取用户最后浏览页*/
			String url = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_CUURENT_URL);
			if(StringUtils.isBlank(url)){
				return new ModelAndView("redirect:/home");
			}
			
			return new ModelAndView("redirect:"+url);
		}

		return new ModelAndView("error");
	}

	/**
	 * 快速登陆
	 * 
	 * @param skuCode
	 * @param type
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "quickLogin", method = RequestMethod.POST)
	public Object memberCollect(@RequestParam("userName") String userName, @RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response) {
		return Jsonp_data.success(login(userName, password, request, response));
	}

	private UserDto login(String userName, String password, HttpServletRequest request, HttpServletResponse response) {
		try {
			String visitKey = CookieUtil.getCookieValue(request, CartConstant.COOKIE_VISIT_KEY);
			// 如果客户端请求中没有visitKey为客户端产生新的
			if (StringUtils.isBlank(visitKey)) {
				String uuid = RandomNumUtil.getUUIDString() + RandomNumUtil.getUUIDString();
				// 暂时用默认cookie过期时间
				CookieUtil.setCookie((HttpServletResponse) response, CartConstant.COOKIE_VISIT_KEY, uuid,
						CookieConstant.COOKIE_MAX_AGE * 12);
				visitKey = uuid;
			}

			Member member = memberFacade.loginByLoginNamePassword(userName, MD5Util.MD5(password));

			/** 登录成功信息添加到日志 */
			logMemberLoginFacade.addToLog(member.getId(), userName, GetIpAddress.getIpAddr(request), CommonConstant.YES);
			// 获取唯一标识码
			String sid = RandomNumUtil.getCharacterAndNumber(WebConstant.UNIQUE_CODE);

			UserDto user = memberFacade.setLoginUserInfo(sid, member);
			CookieUtil.setCookie(response, CookieConstant.COOKIE_SID, sid, CookieConstant.COOKIE_LOGIN_AGE);

			SessionCache.put(sid, new Gson().toJson(user));
			cartRedisUserFacade.loginCart(visitKey,StringUtils.EMPTY,user);

			return user;
		} catch (Exception e) {
			loginLog.error("web:LoginController.java 用户登录异常", e);
			return new UserDto(WebConstant.LOGIN_FAILURE, e.getMessage());
		}
	}

	/*** 点击立即注册时跳转到注册页面 */
	@RequestMapping("/toRegister")
	public String toRegister() {
		return "login/register";
	}

	/***
	 * 验证用户名是否存在
	 * 
	 * @param userName
	 * 
	 * @param response
	 */
	@RequestMapping("checkIsExit")
	public void checkIsExit(@RequestParam("userName") String userName, @RequestParam("status") String status,
			HttpServletResponse response) {
		try {
			response.reset();
			response.setContentType("text/html;charset=UTF-8");
			Member member = this.memberFacade.findByUserName(userName);
			Boolean result = true;
			if (member == null && "login".equals(status) || member != null && "register".equals(status))
				result = false;
			response.getWriter().print(result);
		} catch (IOException e) {
			loginLog.error("web:loginController.java 验证用户名是否存在异常", e);
		}
	}

	@RequestMapping("checkPassword")
	public void checkPassword(@RequestParam("password") String password, @RequestParam("userName") String userName,
			HttpServletResponse response) {
		try {
			response.reset();
			response.setContentType("text/html;charset=UTF-8");
			Member member = memberFacade.loginByLoginNamePassword(userName, MD5Util.MD5(password));
			Boolean result = true;
			if (member == null || StringUtils.equals(member.getDisable(), CommonConstant.YES))
				result = false;
			response.getWriter().print(result);
		} catch (IOException e) {
			loginLog.error("web:loginController.java 验证用户密码有异常", e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "loginOut", method = RequestMethod.POST)
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
			loginLog.error("web:LoginController退出登录异常" + e);
			return Jsonp.error();
		}
	}

	/**
	 * 获取登录的用户信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
	public Object getUserName(HttpServletRequest request) {
		UserDto userDto = null;
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			userDto = userFacade.getLatestUserBySid(sid);
			return Jsonp_data.success(userDto);
		} catch (Exception e) {
			loginLog.error("LoginController获取登陆用户信息有误" + e);
			return Jsonp.error(String.valueOf(WebConstant.LOGIN_FAILURE));
		}
	}

}
