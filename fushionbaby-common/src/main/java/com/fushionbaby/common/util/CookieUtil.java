package com.fushionbaby.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fushionbaby.common.constants.CookieConstant;

/**
 * Cookie工具类,封装Cookie常用操作
 * @author 张明亮
 */
public class CookieUtil {

	/**
	 * 删除制定的cookie
	 * @param response
	 * @param cookie
	 */
	public static void removeCookie(HttpServletResponse response, Cookie cookie) {
		if (cookie != null) {
			cookie.setPath("/");
			cookie.setValue("");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}

	/**
	 * 删除指定Cookie
	 * @paramresponse
	 * @paramcookie
	 * @paramdomain
	 */
	public static void removeCookie(HttpServletResponse response,Cookie cookie, String domain) {
		if (cookie != null) {
			cookie.setPath("/");
			cookie.setValue("");
			cookie.setMaxAge(0);
			cookie.setDomain(domain);
			response.addCookie(cookie);
		}
	}

	/**
	 * 根据Cookie名称得到Cookie的值，没有返回Null
	 * @paramrequest
	 * @paramname
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		if (cookie != null) {
			return cookie.getValue();
		} else {
			return null;
		}
	}

	/**
	 * 根据Cookie名称得到Cookie对象，不存在该对象则返回Null
	 * @param request
	 * @param name
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie cookies[] = request.getCookies();
		if (cookies == null || name == null || name.length() == 0) {
			return null;
		}
		Cookie cookie = null;
		for (int i = 0; i < cookies.length; i++) {
			if (!cookies[i].getName().equals(name)) {
				continue;
			}
			cookie = cookies[i];
			if (request.getServerName().equals(cookie.getDomain())) {
				break;
			}
		}
		return cookie;
	}

	/**
	 * 添加一条新的Cookie信息，默认有效时间为7天
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void setCookie(HttpServletResponse response, String name,String value) {
		setCookie(response, name, value,CookieConstant.COOKIE_MAX_AGE);
	}

	/**
	 * 添加一条新的Cookie信息，可以设置其最长有效时间(单位：秒)
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void setCookie(HttpServletResponse response, String name,String value, int maxAge) {
		if (value == null) {
			value = "";
		}
		Cookie cookie = new Cookie(name, value);
		//cookie.setDomain(CookieConstant.DOMAIN);//cookie属于哪个域名
		if (maxAge != 0) {
			cookie.setMaxAge(maxAge);
		} else {
			cookie.setMaxAge(CookieConstant.COOKIE_MAX_AGE);
		}
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}