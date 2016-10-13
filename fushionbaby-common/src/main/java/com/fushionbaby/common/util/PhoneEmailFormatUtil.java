package com.fushionbaby.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 
 * @description 类描述... phone email 格式验证
 * @author 徐培峻
 * @date 2015年8月10日下午5:32:39
 */
public class PhoneEmailFormatUtil {
	
	
	public static Boolean isEmail(String email){
		Pattern p = Pattern.compile("([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,}");
		Matcher m = p.matcher(email);
		return m.find();
	}
	
	
	public static Boolean isPhone(String phone){
		Pattern p = Pattern.compile("^[1]((3[0-9])|(5[0-9])|(7[0-9])|(8[0-9]))\\d{8}$"); 
		Matcher m = p.matcher(phone);
		return m.find();
	}
	
	
	public static void main(String[] args) {
		
		System.out.println(isEmail("120@qq.com"));
		System.out.println(isPhone("131212181481"));
			
	}

}
