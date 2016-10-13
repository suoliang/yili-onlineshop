package com.aladingshop.web.filter;

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
 * 
 * @description 类描述...
 * @author
 * @date 2015年7月23日下午5:30:02
 */
public class CookiesFiter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
			ServletException {
		String visitKey = CookieUtil.getCookieValue((HttpServletRequest) request, CartConstant.COOKIE_VISIT_KEY);
		// 如果客户端请求中没有visitKey为客户端产生新的
		if (StringUtils.isBlank(visitKey)) {
			String uuid = RandomNumUtil.getUUIDString() + RandomNumUtil.getUUIDString();
			// 暂时用默认cookie过期时间
			CookieUtil.setCookie((HttpServletResponse) response, CartConstant.COOKIE_VISIT_KEY, uuid,
					CookieConstant.COOKIE_MAX_AGE * 12);
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestType = httpRequest.getHeader("X-Requested-With");//异步请求

		String curretnPath = StringUtils.isBlank(httpRequest.getQueryString()) ? httpRequest.getServletPath()
				: httpRequest.getServletPath() + "?" + httpRequest.getQueryString();
		String ingore = "jpg,static,png,css,js,login,register,otherLogin,forget,images,gif,code";
		//同步请求 并且 请求url中不含有忽略字符串 
		if (!com.aladingshop.web.util.StringUtils.isConsit(curretnPath, ingore) && requestType == null) {
			/** 登录不存储且ajax请求忽略 */
			// 存储最后浏览URL到cookie
			CookieUtil.setCookie((HttpServletResponse) response, CookieConstant.COOKIE_CUURENT_URL, curretnPath,
					CookieConstant.COOKIE_URL_AGE);
		}

		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void destroy() {
	}
}
