package com.fushionbaby.common.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.fushionbaby.common.util.RandomNumUtil;


public class DateFormat {

	/**
	 * 日期时间转换为日期时间字符串
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
	/***
	 * 索亮
	 * 转换到分钟
	 * @param date
	 * @return
	 */
	public static String dateToMinuteString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(date);
	}
	
	public static String dateToMMddEEEETime(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd EEEE");
		return df.format(date);
	}
	
	
	public static String timeToHHmmString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(date);
	}
	
	public static String dateToTimeString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(date);
	}
	
	public static String dateTimeToDateString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	
	public static Date stringToDate(String str) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date dateTime=null;
		try {
			dateTime = dateTimeFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateTime;
	}
	/***
	 * 索亮
	 * 将yyyyMMddHHmmss类型的数据转换成Date类型
	 * @param str
	 * @return
	 */
	public static Date yyyyMMddHHmmssToDate(String str) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		Date dateTime=null;
		try {
			dateTime = dateTimeFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateTime;
	}
	/***
	 * 索亮
	 * 年月日的字符串转换成日期格式
	 * @param str
	 * @return
	 */
	public static Date stringToDAYDate(String str) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date dateTime=null;
		try {
			dateTime = dateTimeFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateTime;
	}
	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param time 时间"HH:mm:ss"
	 * @return
	 */
	public static Date stringToDate(int year,int month,int day,String time){
		String dateStr=year+"-"+month+"-"+day+" "+time;
		return stringToDate(dateStr);
	}
	
	public static Date getTodayDate(String time){
		int y,m,d;   
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		y=cal.get(Calendar.YEAR);   
		m=cal.get(Calendar.MONTH)+1;   
		d=cal.get(Calendar.DATE);   
		Date date=DateFormat.stringToDate(y,m,d,time);
		return date;
	}
	public static Date addDay(Date date,int day){
		 Calendar   calendar   =  Calendar.getInstance(); 
	     calendar.setTime(date); 
	     calendar.add(Calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
	     return calendar.getTime(); 
	    
	}
	public static String dateToHHmm(Date date){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(date);
	}
	
	/***
	 * 交易流水号日期格式数据 18 位
	 * @param date
	 * @return
	 */
	public static String dateToSerialNo(Date date){
	   	String dateString=	DateFormatUtils.format(date, "yyyyMMddHHmmss");
		return dateString+RandomNumUtil.getNumber(4);
	}
	
	
	
	
	
	public static void main(String[] args){
		/*Date a = yyyyMMddHHmmssToDate("20150116164480");
		System.out.println(a.getTime());*/
		/*System.out.println(DateFormat.dateToMinuteString(new Date()));
		String time = DateFormat.timeToDateString(new Date());
		System.out.println(time);
		int codeId  =time.compareTo("12:00:00");//大于12点codeId大于0  小于12点codeId小于0
		System.out.println(codeId);*/
		//System.out.println(DateFormat.dateToPadTime(new Date()));
		
		System.out.println(dateToSerialNo(new Date()));
	}
}
