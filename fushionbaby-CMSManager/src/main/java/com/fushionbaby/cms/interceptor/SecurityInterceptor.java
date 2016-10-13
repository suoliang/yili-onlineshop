package com.fushionbaby.cms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.common.constants.SessionKeyConstant;
/***
 * cms后台管理系统的拦截
 * @author King 索亮
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception {
		AuthUser auUser = (AuthUser) request.getSession().getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if (auUser == null) {
			response.sendRedirect(request.getContextPath() +"/login/index");
			return false;
		}
		return super.preHandle(request, response,handler);
	}
}
