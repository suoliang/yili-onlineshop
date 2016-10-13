/**
 * 
 */
package com.fushionbaby.common.util;

import java.util.List;

/**
 * @description 非空校验工具类
 * @author 孙涛
 * @date 2015年9月11日上午11:47:04
 */
public class EmptyValidUtils {
	public static boolean arrayIsEmpty(List<?> list) {
		return list == null || list.size() == 0;
	}
}
