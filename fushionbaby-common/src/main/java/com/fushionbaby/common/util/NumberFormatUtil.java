package com.fushionbaby.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字相关的工具类，将数字四舍五入保留两位小数
 * @author King 索亮
 *
 */
public class NumberFormatUtil {
	
	/**
	 * 将数字四舍五入并保留两位小数
	 * @param number
	 * @return
	 * 		  String
	 */
	public static String numberFormat(BigDecimal number){
		if (CheckObjectIsNull.isNull(number)) {
			return "0.00";
		}
		DecimalFormat df = new DecimalFormat("#.00");   
		String numberFormat = df.format(number);
		//如果长度为3表示格式化后的数值小数点前没有0，将格式化的数据前加个0
		if (numberFormat.length() == 3) {
			return "0"+numberFormat;
		}
		return numberFormat;
	}
	
	public static BigDecimal numberFormatToCorner(BigDecimal number){
		
		DecimalFormat df = new DecimalFormat("#.0");   
		String numberFormat = df.format(number);
		if (numberFormat.length() == 2) {
			return  new BigDecimal("0" + numberFormat + "0");
		}
		return new BigDecimal(numberFormat + "0");
		
	}
	
	
}
