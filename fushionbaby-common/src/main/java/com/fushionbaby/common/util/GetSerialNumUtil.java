package com.fushionbaby.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
/***
 * @description 生成年月日时分秒+6位随机数的20位随机序列号
 * @author 索亮
 * @date 2015年12月29日下午1:32:15
 */
public class GetSerialNumUtil {
	/**
	 * 订单号生成
	 * @return
	 */
	public static String generateSerialNum() {
		DateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder sb = new StringBuilder(formater.format(new Date()));
        for(int i=0;i<6;i++) {
        	sb.append(new Random().nextInt(9));
        }
        return sb.toString();

	}

	public static void main(String[] args) {
		System.out.println(generateSerialNum());
	}
	
}
