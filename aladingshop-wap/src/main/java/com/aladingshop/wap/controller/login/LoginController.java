package com.aladingshop.wap.controller.login;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CartConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.WapConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.log.service.LogMemberLoginService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.google.gson.Gson;

/***
 * @description WAP登录
 * @author 索亮
 * @date 2015年10月27日上午9:29:20
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	/** 注入日志 */
	private static final Log LOGGER = LogFactory.getLog(LoginController.class);

	/** 注入会员 */
	@Autowired
	private MemberService<Member> memberService;

	/** 注入登录日志 */
	@Autowired
	private LogMemberLoginService logMemberLoginService;

	/**
	 * Redis操作购物车,未登录会员使用的购物车
	 */
	@Autowired
	private CartRedisUserService<ShoppingCartSku> cartRedisService;

	@Autowired
	private UserFacade userFacade;

	/***
	 * 跳转到登录页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "login/login";
	}

	/***
	 * 用户登录接口
	 * 
	 * @param userName
	 *            账号
	 * @param password
	 *            密码
	 * @return User对象
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("login")
	public Object userLogin(String userName, String password,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String visitKey = CookieUtil.getCookieValue(request,
					CartConstant.COOKIE_VISIT_KEY);
			// 如果客户端请求中没有visitKey为客户端产生新的
			if (StringUtils.isBlank(visitKey)) {
				String uuid = RandomNumUtil.getUUIDString()
						+ RandomNumUtil.getUUIDString();
				// 暂时用默认cookie过期时间
				CookieUtil.setCookie((HttpServletResponse) response,
						CartConstant.COOKIE_VISIT_KEY, uuid,
						CookieConstant.COOKIE_MAX_AGE * 12);
				visitKey = uuid;
			}

			if (memberService.findByLoginName(userName) == null) {
				return Jsonp.error("您输入的用户名和密码不匹配!");
			}
			Member member = memberService.loginByLoginNamePassword(userName,
					MD5Util.MD5(password));
			if (member == null) {
				return Jsonp.error("您输入的用户名和密码不匹配!");
			}
			// 判断用户是不是禁用状态
			if (StringUtils.equals(member.getDisable(), CommonConstant.YES)) {
				/** 登录失败信息添加到日志 */
				logMemberLoginService.add(null, userName,
						WapConstant.WAP_IP_ADDRESS, CommonConstant.NO);
				return Jsonp.error("请确认您的当前操作是否合法!");
			}
			// 登陆成功日志信息
			logMemberLoginService.add(member.getId(), userName,
					WapConstant.WAP_IP_ADDRESS, CommonConstant.YES);

			UserDto user = new UserDto();
			String sid = RandomNumUtil
					.getCharacterAndNumber(WapConstant.UNIQUE_CODE);
			CookieUtil.setCookie(response, CookieConstant.COOKIE_SID, sid,
					CookieConstant.COOKIE_LOGIN_AGE);
			user.setSid(sid);
			user.setMemberId(member.getId());
			user.setLoginName(member.getLoginName());
			BigDecimal epoints = (BigDecimal) ((member.getEpoints() == null) ? 0
					: member.getEpoints());
			user.setEpoints(epoints);
			// user.setImgPath(ImageConstantApp.MEMBER_IMAGE_SERVER_PATH+"/"+member.getImgPath());
			SessionCache.put(sid, new Gson().toJson(user));
			// SessionCache.put(sid, user, 43200);
			// 把Redis缓存中的未登录的购物车信息同步到该用户的购物车中、并清除缓存中的购物车
			cartRedisService.loginCart(visitKey,StringUtils.EMPTY,user);
			/** 从当前cookie中获取用户最后浏览页 */
			// String url = CookieUtil.getCookieValue(request,
			// CookieConstant.COOKIE_CUURENT_URL);
			// String url
			// if (StringUtils.isBlank(url)) {
			// url = "/html/home.html";
			// }
			return Jsonp_data.success("/html/home.html");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("LoginController用户登陆有误WAP" + e);
			return Jsonp.error("出错了，用户登陆失败!");
		}
	}

	/*** 点击立即注册时跳转到注册页面 */
	@RequestMapping("/toRegister")
	public String toRegister() {
		return "login/registering-phone";
	}

	/*** 邀请码注册时跳转到注册页面 */
	@RequestMapping("/toShareRegister")
	public String toShareRegister(String code, ModelMap model,
			HttpServletResponse response) {
		/** 获取32位唯一标识码 */
		String register_code = RandomNumUtil
				.getCharacterAndNumber(WapConstant.UNIQUE_CODE);
		CookieUtil.setCookie(response, CookieConstant.REGISTER_COOKIE,
				register_code);
		model.addAttribute("code", code);
		return "login/registering-phone";
	}

	/***
	 * 用户点击注册时请求
	 */
	@ResponseBody
	@RequestMapping("clickRegister")
	public Object clickRegister(HttpServletResponse response) {
		try {
			/** 获取32位唯一标识码 */
			String register_code = RandomNumUtil
					.getCharacterAndNumber(WapConstant.UNIQUE_CODE);
			CookieUtil.setCookie(response, CookieConstant.REGISTER_COOKIE,
					register_code);
			return Jsonp.success();
		} catch (Exception e) {
			LOGGER.error("LoginController注册请求有误" + e);
			return Jsonp.error("获取唯一标识码失败!");
		}
	}

	/***
	 * 用户点击注册时请求
	 */
	@ResponseBody
	@RequestMapping("clickForget")
	public Object clickForget(HttpServletResponse response) {
		try {
			/** 获取32位唯一标识码 */
			String forgetCode = RandomNumUtil
					.getCharacterAndNumber(WapConstant.UNIQUE_CODE);
			CookieUtil.setCookie(response,
					CookieConstant.FORGETPASSWORD_COOKIE, forgetCode);
			return Jsonp.success();
		} catch (Exception e) {
			LOGGER.error("LoginController忘记密码请求有误" + e);
			return Jsonp.error("获取唯一标识码失败!");
		}
	}

	/*** 点击立即注册时跳转到注册页面 */
	@RequestMapping("/toForget")
	public String toForget() {
		return "login/forgot-password";
	}

	/**
	 * 验证用户是否登录
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "isLogin", method = RequestMethod.POST)
	public Object getUserName(HttpServletRequest request) {
		UserDto userDto = null;
		try {
			String sid = CookieUtil.getCookieValue(request,
					CookieConstant.COOKIE_SID);
			userDto = userFacade.getLatestUserBySid(sid);
		} catch (Exception e) {
			LOGGER.error("LoginController获取登陆用户信息有误" + e);
			return Jsonp.error(String.valueOf(WebConstant.LOGIN_FAILURE));
		}

		if (userDto == null) {
			return Jsonp_data.success(CommonConstant.NO);
		}

		return Jsonp_data.success(CommonConstant.YES);
	}

}
