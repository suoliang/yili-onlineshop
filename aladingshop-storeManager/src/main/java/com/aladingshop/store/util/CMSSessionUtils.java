package com.aladingshop.store.util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.aladingshop.store.auth.model.StoreAuthUser;
import com.fushionbaby.common.constants.SessionKeyConstant;

/** 
 * @author xupeijun
 * @version 2015年6月25日 上午10:35:20 
 * 获得session存入key的值
 */
public class CMSSessionUtils {
	
	public  static StoreAuthUser getSessionUser(HttpSession session){
		StoreAuthUser user=(StoreAuthUser) session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		return  user;
	}

	public  static Object getSessionValueByKey(HttpServletRequest request, String key){
		Object obj=request.getSession().getAttribute(key);
		return  obj;
	}

	
}
