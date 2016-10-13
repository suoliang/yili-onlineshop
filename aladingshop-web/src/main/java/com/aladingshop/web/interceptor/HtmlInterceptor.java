package com.aladingshop.web.interceptor;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fushionbaby.cache.util.RedisUtil;

/**
 * 静态化拦截器
 * 
 * @author mengshaobo
 *
 */

public class HtmlInterceptor extends HandlerInterceptorAdapter {

	private String htmlPath = "";

	// private String htmlPath = "E:\\data";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (htmlPath == null || htmlPath.length() == 0) {
			htmlPath = request.getSession().getServletContext().getRealPath("");
		}
		String requestUri = request.getRequestURI();
		
		/** 首页以及一级菜单 */

		int n = requestUri.indexOf("/home");
		if (StringUtils.isNotBlank(requestUri) && n >= 0) {
			String savePath = htmlPath + File.separator + "html" + File.separator + "home.html";
			String menuPath = htmlPath + File.separator + "WEB-INF" + File.separator + "ftl" + File.separator
					+ "common" + File.separator + "menu.ftl";
			request.setAttribute("savePath", savePath);
			request.setAttribute("menuPath", menuPath);
			return true;
		}

		/**列表页静态化*/
		int m=requestUri.indexOf("/category/01");
		if (StringUtils.isNotBlank(requestUri) && m >= 0) {
			String code=(String) request.getParameter("code");
			String categorySavePath = htmlPath + File.separator + "html" + File.separator + "list"+code+".html";
			File file=new File(categorySavePath);
			request.setAttribute("categorySavePath", categorySavePath);
			request.setAttribute("code", code);
			request.setAttribute("category_flag", file.exists());
			return true;
		}
		
		/** 商品明细页面 */
		String skuCode = request.getParameter("skuCode");
		n = requestUri.indexOf("/sku/skuDetail");
		if (StringUtils.isNotBlank(skuCode) && StringUtils.isNotBlank(requestUri) && n >= 0) {
			String detailPath = htmlPath + File.separator + "detail" + File.separator + skuCode + ".html";
			/** 判断当前商品明细静态页是否存在 */
			File file = new File(detailPath);
			request.setAttribute("detailPath", detailPath);
			request.setAttribute("flag", file.exists());
			return true;
		}

		String uri = requestUri.replace(".do", "/");
		String urlBorw = uri;
		if (!request.getContextPath().equals("")) {
			urlBorw = uri.substring(uri.indexOf(request.getContextPath()) + request.getContextPath().length());
		}

		String paramsStr = getRequestParamValues(request);
		String signStr = bernstein(urlBorw + paramsStr) + "";

		String htmlUrl = hget(urlBorw + signStr);
		if (null != htmlUrl && htmlUrl.length() > 0) {
			File file = new File(htmlPath + htmlUrl.replace("/", File.separator));
			if (file.exists()) {
				response.sendRedirect(request.getContextPath() + htmlUrl);
				return false;
			} else {
				hdel(urlBorw + signStr);
				return true;
			}
		}

		// 静态文件地址
		String savePath = htmlPath + File.separator + "html" + urlBorw.replace("/", File.separator) + signStr + ".html";

		hset(urlBorw + signStr, "/html" + urlBorw + signStr + ".html");

		request.setAttribute("savePath", savePath);
		return true;
	}

	public static String getRequestParamValues(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		Enumeration<String> enums = request.getParameterNames();
		String key = "";
		String value = "";
		while (enums.hasMoreElements()) {
			key = enums.nextElement();
			value = request.getParameter(key);
			if (value != null && value.trim().length() > 0) {
				sb.append("_" + key + "_" + value);
			}
		}
		return sb.toString();
	}

	private static RedisUtil redis = new RedisUtil();

	private synchronized static String hget(String signStr) {
		if (redis.hexists("staticViews", signStr)) {
			return redis.hget("staticViews", signStr);
		}
		return null;
	}

	private synchronized static void hset(String signStr, String value) {
		redis.hset("staticViews", signStr, value);
	}

	private synchronized static void hdel(String signStr) {
		redis.hdel("staticViews", signStr);
	}

	/**
	 * Bernstein's hash
	 * 
	 * @return 结果hash
	 */
	public static int bernstein(String key) {
		int hash = 0;
		for (int i = 0; i < key.length(); i++) {
			hash = 33 * hash + key.charAt(i);
		}
		return hash;
	}
}
