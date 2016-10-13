/**
 * 
 */
package com.fushionbaby.cms.util;

/**
 * @description 字符串处理工具类
 * @author 孙涛
 * @date 2015年8月31日下午4:27:08
 */
public class StringUtils {
	public static String upFileNameFilter(String fileName) {
		/** 上传文件名特殊符号处理 */
		 if (MatchUtils.macth(fileName, MatchUtils.REGEX)) {
			fileName = MatchUtils.delSymbol(fileName);
		}

		return fileName;
	}
}
