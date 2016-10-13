package com.fushionbaby.pay.controller.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNumberUtil {

	/**
	 * 订单号生成
	 * @return
	 */
	public static String generateOrdrNo() {
		DateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder sb = new StringBuilder(formater.format(new Date()));
        for(int i=0;i<6;i++) {
        	sb.append(new Random().nextInt(9));
        }
        return sb.toString();

	}

	public static void main(String[] args) {
		System.out.println(generateOrdrNo());
	}
}
