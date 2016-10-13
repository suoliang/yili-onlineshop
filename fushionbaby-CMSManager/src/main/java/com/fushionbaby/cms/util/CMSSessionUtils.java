package com.fushionbaby.cms.util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.common.constants.SessionKeyConstant;

/** 
 * @author xupeijun
 * @version 2015年6月25日 上午10:35:20 
 * 获得session存入key的值
 */
public class CMSSessionUtils {
	
	public  static AuthUser getSessionUser(HttpSession session){
		AuthUser user=(AuthUser) session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		return  user;
	}

	public  static Object getSessionValueByKey(HttpServletRequest request, String key){
		Object obj=request.getSession().getAttribute(key);
		return  obj;
	}

	
}
