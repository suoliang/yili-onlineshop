package com.aladingshop.wap.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;

/**
 * 
 * @description 判断用户权限，未登录用户跳转到登录页面
 * @author duxihu
 * @date 2015年7月24日下午3:28:32
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestType = request.getHeader("X-Requested-With");/** 请求的方法是否是ajax*/
		
		if(requestType!=null){
			System.out.println(requestType);
			return super.preHandle(request, response, handler);
		}
		
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (sid != null && !sid.equals("")) {
			UserDto userDto = (UserDto) SessionCache.get(sid);
			if (userDto != null) {
				return super.preHandle(request, response, handler);
			} else {
				// 未登录，返回到登录接口
				response.sendRedirect(request.getContextPath() + "/login/index");
				return false;
			}
		} else {
			// 未登录，返回到登录接口
			response.sendRedirect(request.getContextPath() + "/login/index");
			return false;
		}

	}

}
