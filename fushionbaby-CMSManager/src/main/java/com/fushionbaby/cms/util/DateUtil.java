package com.fushionbaby.cms.util;

import static java.util.Calendar.YEAR;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 线程安全工具类 User: zhangleimin Date: 14-6-12
 */
public class DateUtil {

	/** 时间格式：HHmmss */
	public static final String TIME_PATTERN = "HHmmss";
	/** 日期格式：yyyyMMdd */
	public static final String DATE_PATTERN = "yyyyMMdd";
	public static final String SHORT_DATE_PATTERN = "yyMMdd";
	/** 日期时间格式：yyyyMMddHHmmss */
	public static final String FULL_PATTERN = "yyyyMMddHHmmss";
	/** 日期时间格式：yyMMddHHmmss */
	public static final String PART_PATTERN = "yyMMddHHmmss";
	/** 日期时间格式：yyyy.MM.dd HH:mm:ss */
	public static final String TICKET_PATTERN = "yyyy.MM.dd HH:mm:ss";// 火车票购买格式
	public static final String SETTLE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/** 日期时间格式：yyyyMMdd HH:mm:ss */
	public static final String DATEFULL_PATTERN = "yyyyMMdd HH:mm:ss";
	/* 时间式：HHmm */
	public static final String HOUR_OF_MINUTE = "HHmm";

	public static final String YEAR_OF_MINUTE = "yyyyMMddHHmm";

	public static final String YEAR_OF_H = "yyyyMMddHH";

	public static final String FULL_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final String FULL_DATETIME_PATTERN2 = "yyyy/MM/dd HH:mm:ss";

	public static final String FULL_YEAR_MONTH = "yyyyMM";

	/** 日期格式：yyyyMMdd */
	public static final String DATE_PATTERN_LONG = "yyyy-MM-dd";

	public static String getCurrentDateStr(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(new Date());
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static String getDateToString() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		String cDate = Integer.toString(year);
		if (month < 10) {
			cDate = cDate + "0" + Integer.toString(month);
		} else {
			cDate = cDate + Integer.toString(month);
		}
		if (day < 10) {
			cDate = cDate + "0" + Integer.toString(day);
		} else {
			cDate = cDate + Integer.toString(day);
		}
		if (hour < 10) {
			cDate = cDate + "0" + Integer.toString(hour);
		} else {
			cDate = cDate + Integer.toString(hour);
		}
		if (minute < 10) {
			cDate = cDate + "0" + Integer.toString(minute);
		} else {
			cDate = cDate + Integer.toString(minute);
		}
		if (second < 10) {
			cDate = cDate + "0" + Integer.toString(second);
		} else {
			cDate = cDate + Integer.toString(second);
		}
		return cDate.trim();
	}

	/**
	 * 比较当前日期
	 * 
	 * @param d
	 * @return
	 */
	public static boolean xsDate(Date dataTime) {
		try {
			Date currTime = new Date();
			long aug = currTime.getTime() - dataTime.getTime();
			if (aug / 3600000 > 3) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 计算2个日期相差的年份
	 * 
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static long xsDate(Date firstDate, Date lastDate) {
		long timeDate = firstDate.getTime() - lastDate.getTime();
		long day = timeDate / (1000 * 60 * 60 * 24) / 365;
		return day;
	}
}
