/**
 * 
 */
package com.aladingshop.wap.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description 字符串处理工具类
 * @author 孙涛
 * @date 2015年8月20日上午10:25:32
 */
public class StringUtils {
	public static boolean isConsit(String str, String target) {
		if (org.apache.commons.lang3.StringUtils.isBlank(str) || org.apache.commons.lang3.StringUtils.isBlank(target)) {
			return false;
		}
		target = target.replace("，", ",");
		String[] targets = target.split(",");
		for (String ta : targets) {
			if (str.contains(ta)) {
				return true;
			}
		}

		return false;
	}

	public static List<String> arrayToList(String str) {
		if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
			return new ArrayList<String>();
		}

		String[] array = str.split(",");
		return Arrays.asList(array);
	}
}
