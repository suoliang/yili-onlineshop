package com.aladingshop.store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aladingshop.store.auth.model.StoreAuthUser;
import com.fushionbaby.common.constants.SessionKeyConstant;
/***
 * 门店cms后台管理系统的拦截
 * @author King 索亮
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception {
		StoreAuthUser auUser = (StoreAuthUser) request.getSession().getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if (auUser == null) {
			response.sendRedirect(request.getContextPath() +"/login/index");
			return false;
		}
		return super.preHandle(request, response,handler);
	}
}
