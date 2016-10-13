/**
 * 
 */
package util;

import java.util.List;

/**
 * @description 非空判断工具类
 * @author 孙涛
 * @date 2015年8月11日上午11:04:26
 */
public class EmptyValidUtils {
	public static boolean arrayIsEmpty(List<?> list) {
		if (list == null || list.size() == 0) {
			return true;
		}

		return false;
	}
}
