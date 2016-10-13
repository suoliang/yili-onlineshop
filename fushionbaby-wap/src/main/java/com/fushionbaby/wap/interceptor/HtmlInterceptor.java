package com.fushionbaby.wap.interceptor;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fushionbaby.cache.util.RedisUtil;


public class HtmlInterceptor extends HandlerInterceptorAdapter {

	private String htmlPath = "";
	//private String htmlPath = "E:\\data";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		if(htmlPath == null ||htmlPath.length()==0) {
			htmlPath = request.getSession().getServletContext().getRealPath("");
		}
		
		String uri = request.getRequestURI().replace(".do", "/");
		String urlBorw = uri;
		if(!request.getContextPath().equals("")){
			urlBorw = uri.substring(uri.indexOf(request.getContextPath())+request.getContextPath().length());
		}
		
		String paramsStr = getRequestParamValues(request);
		String signStr = bernstein(urlBorw + paramsStr)+"";
		
		String htmlUrl = hget(urlBorw + signStr);
		if (null != htmlUrl && htmlUrl.length() > 0) {
			File file = new File(htmlPath + htmlUrl.replace("/", File.separator));
			if (file.exists()) {
				response.sendRedirect(request.getContextPath()+htmlUrl);
				return false;
			} else {
				hdel(urlBorw + signStr);
				return true;
			}
		}
		//静态文件地址
		String savePath =  htmlPath + File.separator + "html" + urlBorw.replace("/", File.separator) + signStr + ".html";
		hset(urlBorw + signStr,"/html" + urlBorw + signStr + ".html");
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
	
	private synchronized static void hset(String signStr,String value) {
		redis.hset("staticViews", signStr, value);
	}
	
	private synchronized static void hdel(String signStr) {
		redis.hdel("staticViews", signStr);
	}
	
   /**
     * Bernstein's hash
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
