package com.fushionbaby.cache;

import java.util.Calendar;
import com.whalin.MemCached.MemCachedClient;

public class DataCache{
	
	public static  int DEFAULT_EXPIRATE_MINUTES = 30;

	public static void put(String key, Object value) {
		if(key==null || value==null){
            return ;
        }
        put(key, value,DataCache.DEFAULT_EXPIRATE_MINUTES);
	}

	

	public static void put(String key, Object value, int expiraMinutes) {
		if(key==null || value==null){
            return ;
        }
        MemCachedClient proxy = MemcachedProxyByData.getInstance();
        Calendar c=Calendar.getInstance();
        c.add(Calendar.MINUTE, expiraMinutes);
        proxy.set(key, value,c.getTime());
	}

	public static Object get(String key) {
		MemCachedClient proxy = MemcachedProxyByData.getInstance();
        Object o = proxy.get(key);
        return o;
	}
	
	
	public static void remove(String key) {
		MemCachedClient proxy = MemcachedProxyByData.getInstance();
        proxy.delete(key);
	}


}
