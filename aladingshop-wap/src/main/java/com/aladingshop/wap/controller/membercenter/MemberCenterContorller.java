package com.aladingshop.wap.controller.membercenter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;

/**
 * 
 * @author cyla
 * <p>处理用户意见反馈的功能</p>
 * @version 1.0.0
 */
@Controller
@RequestMapping("/memberCenter")
public class MemberCenterContorller {
	/**注入日志*/
	private static final Log LOGGER = LogFactory.getLog(MemberCenterContorller.class);
	
	
	@RequestMapping("toMemberCenter")
	public String toMemberCenter(HttpServletRequest request,Model model){
		String sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
		UserDto user = (UserDto) SessionCache.get(sid);
		if(user == null || user.getMemberId() == null){
			model.addAttribute("isLogin", "n");
		}else{
			model.addAttribute("isLogin", "y");
		}
		return "membercenter/person";
	}
	
	@RequestMapping("toSetting")
	public String toSetting(){
		return "membercenter/setting";
	}
	
	@RequestMapping("toAboutUs")
	public String toAboutUs(){
		return "membercenter/about";
	}
	
	@RequestMapping("toContact")
	public String toContact(){
		return "membercenter/contact";
	}
	/***
	 * 退出登录
	 * response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("/logout")
	public Object loginLogOut(HttpServletRequest request,HttpServletResponse response){
		try {
			Cookie cookie = CookieUtil.getCookie(request, CookieConstant.COOKIE_SID);
			if (!ObjectUtils.equals(cookie, null)) {
				String sid = cookie.getValue();
				CookieUtil.removeCookie(response, cookie);// 移除Cookie
				// 移除SessionCache中的用户对象
				SessionCache.remove(sid);
			}

			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("MemberCenterContorller退出登录有误wap" + e);
			return Jsonp.error();
		}
	}
}
