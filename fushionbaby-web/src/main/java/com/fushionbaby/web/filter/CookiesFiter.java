package com.fushionbaby.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fushionbaby.common.constants.CartConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.RandomNumUtil;

/**
 * @author 张明亮
 */
public class CookiesFiter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException{
		
		String visitKey=CookieUtil.getCookieValue((HttpServletRequest)request,CartConstant.COOKIE_VISIT_KEY);
		//如果客户端请求中没有visitKey为客户端产生新的
		if(StringUtils.isBlank(visitKey)){
			String uuid=RandomNumUtil.getUUIDString()+RandomNumUtil.getUUIDString();
			//暂时用默认cookie过期时间
			CookieUtil.setCookie((HttpServletResponse)response,CartConstant.COOKIE_VISIT_KEY,uuid,CookieConstant.COOKIE_MAX_AGE*12);
		}
		filterChain.doFilter(request,response);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
	public void destroy() {
	}
}
