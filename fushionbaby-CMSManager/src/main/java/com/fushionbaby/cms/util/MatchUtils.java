/**
 * 
 */
package com.fushionbaby.cms.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 正则验证工具类
 * @author 孙涛
 * @date 2015年8月31日下午4:29:46
 */
public class MatchUtils {
	/** 是否包含中文正则 */
	public static final String ISHANZI = "[\u4e00-\u9fa5]";
	/** 是否包含特殊符号正则 */
	public static final String REGEX = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

	public static boolean macth(String str, String regEx) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public static String delSymbol(String str) {
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
}
