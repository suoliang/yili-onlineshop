
package com.fushionbaby.wap.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;



/**
 * 判断用户权限，未登录用户跳转到登录页面
 * @author duxihu
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (sid!= null&&!sid.equals("")) {
		   UserDto userDto = (UserDto)SessionCache.get(sid);
		   if(userDto!=null)
		   {
			   return super.preHandle(request, response, handler);
		   }else{
			   //未登录，返回到登录接口
			   response.sendRedirect(request.getContextPath() +"/login/index.do");
			   return false;
		   }
		}else{
			 //未登录，返回到登录接口
			 response.sendRedirect(request.getContextPath() +"/login/index.do");
			 return false;
		}

		
	}

}
