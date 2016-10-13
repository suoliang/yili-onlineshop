package com.fushionbaby.common.util;

import org.apache.commons.lang3.StringUtils;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
/***
 * @description 对JSON格式进行校验
 * @author 索亮
 * @date 2015年9月17日下午3:08:23
 */
public class JsonValidateUtils {

	/**
	 * 验证是不是错误的格式，如果错误返回true
	 * @param json
	 * @return
	 */
	public static boolean isBadJson(String json) {
		return !isGoodJson(json);
	}
	
	/**
	 * 验证是不是正确的格式，如果正确返回true
	 * @param json
	 * @return
	 */
	public static boolean isGoodJson(String json) {
		if (StringUtils.isBlank(json)) {
			return false;
		}
		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			return false;
		}
	}
	
}
