/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日下午5:27:37
 */
package com.fushionbaby.common.util.date;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日下午5:27:37
 */
public class DateEndUtil {

	
	
	/**
	 * 获取结束时间的时间戳
	 * @return
	 */
	public static Long getEndDateLong(Date endDate){
		
		Long endDateTime = 0L;
		try {
			endDateTime = DateUtils.parseDate(DateFormatUtils.format(endDate, "yyyy-MM-dd") + " 23:59:59", "yyyy-MM-dd HH:ss:mm").getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return endDateTime;
	}
	
	/**
	 * 获取结束日期字符串
	 * @param endDate
	 * @return
	 */
	public static String getEndDateStr(Date endDate){
		
		Long timestamp = DateEndUtil.getEndDateLong(endDate);
		
		return DateFormatUtils.format(timestamp, "yyyy-MM-dd HH:ss:mm");
	}
}
