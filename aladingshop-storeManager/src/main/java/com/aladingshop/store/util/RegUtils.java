/**
 * 
 */
package com.aladingshop.store.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @description 正则工具类
 * @author 孙涛
 * @date 2015年8月26日下午5:22:02
 */
public class RegUtils {
	private static final String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	private static final String regEx2 = "[0-9]*";

	/**
	 * 是否有特殊字符验证
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isSpecialSymbol(String str) {
		Pattern p = Pattern.compile(regEx);
		return p.matcher(str).find();
	}

	public static boolean isInteger(String str) {
		Pattern p = Pattern.compile(regEx2);
		return p.matcher(str.replace(".", "")).matches();
	}

	public static boolean lenValid(Integer len, String symbol, String str) {
		boolean flag = true;
		String[] array = str.split(symbol);
		for (String s : array) {
			if (StringUtils.isNotBlank(s) && s.length() > len) {
				flag = false;
				break;
			}
		}

		return flag;
	}
}
