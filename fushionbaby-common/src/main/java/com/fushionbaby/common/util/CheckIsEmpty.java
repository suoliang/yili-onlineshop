package com.fushionbaby.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Leon
 *
 */
public class CheckIsEmpty {
	/**
	 * 检查传入的String类型数据是否为空
	 * @param a
	 * @param strings
	 * @return
	 */
	public static boolean isEmpty(String a,String...strings){
		if (StringUtils.isBlank(a)) {
			return true;
		}	
		for (int i = 0; i < strings.length; i++) {
			if (StringUtils.isBlank(strings[i])) {
				return true;
			}
		}
		return false;
	}
	
}
