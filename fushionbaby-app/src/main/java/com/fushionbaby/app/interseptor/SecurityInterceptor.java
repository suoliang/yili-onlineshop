
package com.fushionbaby.app.interseptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.dto.UserDto;



/**
 * 判断用户权限，未登录用户跳转到登录页面
 * @author duxihu
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String sid = request.getParameter("sid");
		if (StringUtils.isNotBlank(sid)) {
		   UserDto userDto = (UserDto)SessionCache.get(sid);
		   if(userDto!=null){
			   return super.preHandle(request, response, handler);
		   }
		   //未登录，返回到登录接口
		   response.sendRedirect(request.getContextPath() +"/login/noLogin");
		   return false;
		   
		}
		 //未登录，返回到登录接口
		 response.sendRedirect(request.getContextPath() +"/login/noLogin");
		 return false;
		
		
	}

}
