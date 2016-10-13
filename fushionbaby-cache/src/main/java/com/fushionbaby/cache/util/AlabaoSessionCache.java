package com.fushionbaby.cache.util;

import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * @description 如意宝登录session
 * @author 索亮
 * @date 2015年9月10日上午9:53:13
 */
public class AlabaoSessionCache {
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
		AlabaoUserDto user = gson.fromJson(string,
				new TypeToken<AlabaoUserDto>() {
				}.getType());
		return user;
	}
	
	public static void remove(String key) {
		new RedisUtil().del(key);
	}
	
}
