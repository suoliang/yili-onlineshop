package com.fushionbaby.cache;

import com.fushionbaby.cache.util.RedisUtil;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SessionCache{
	public static void put(String key, String value) {
		new RedisUtil().set(key, value);
	}
	
	public static Object get(String key) {
		if (CheckIsEmpty.isEmpty(key)) {
			return null;
		}
		String string = new RedisUtil().get(key);
		if (CheckIsEmpty.isEmpty(string)) {
			return null;
		}
		// 解析data
		Gson gson = new Gson();
		UserDto user = gson.fromJson(string,
				new TypeToken<UserDto>() {
				}.getType());
		return user;
	}
	
	public static void remove(String key) {
		new RedisUtil().del(key);
	}
	
	/*public static  int DEFAULT_EXPIRATE_MINUTES=30;
	
	
	public static void put(String key, Object value) {
		if(key==null || value==null){
            return ;
        }
        put(key, value,SessionCache.DEFAULT_EXPIRATE_MINUTES);
	}

	public static void put(String key, Object value, int expiraMinutes) {
		if(key==null || value==null){
            return ;
        }
        MemCachedClient proxy = MemcachedProxyBySession.getInstance();
        Calendar c=Calendar.getInstance();
        c.add(Calendar.MINUTE, expiraMinutes);
        proxy.set(key, value,c.getTime());
	}

	
	public static Object get(String key) {
		MemCachedClient proxy = MemcachedProxyBySession.getInstance();
        Object o = proxy.get(key);
        if(o!=null){ //防止会话过期,自动更新过期时间
        	put(key,o);
        }
        return o;
	}
	

	public static void remove(String key) {
		MemCachedClient proxy = MemcachedProxyBySession.getInstance();
        proxy.delete(key);
	}*/
	
}
