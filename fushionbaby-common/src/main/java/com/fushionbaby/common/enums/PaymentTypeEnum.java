/**
 * 
 */
package com.fushionbaby.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mengshaobo 支付状态枚举
 */
public enum PaymentTypeEnum {
	/** 支付宝支付Web担保交易 */
	PAYMENT_ZFB_WEB_DBJY("ZFB_WEB_DBJY", "WEB支付宝担保交易"),
	/** 支付宝支付Web即时到帐 */
	PAYMENT_ZFB_WEB_JSDZ("ZFB_WEB_JSDZ", "WEB支付宝即时到帐"),
	/** 支付宝支付app */
	PAYMENT_ZFB_APP("ZFB_APP", "APP支付宝"),
	/** 微信支付WEB */
	PAYMENT_WX_WEB("WX_WEB", "WEB微信支付"),
	/** 微信支付APP */
	PAYMENT_WX_APP("WX_APP", "APP微信支付"),
	/** 在线银联支付WEB */
	PAYMENT_ZXYL_WEB("ZXYL_WEB", "WEB银联支付"),
	/** 在线银联支付APP */
	PAYMENT_ZXYL_APP("ZXYL_APP", "APP银联支付"),
	/** 货到付款支付WEB */
	PAYMENT_HDFK_WEB("HDFK_WEB", "WEB货到付款"),
	/** 货到付款支付APP */
	PAYMENT_HDFK_APP("HDFK_APP", "APP货到付款"),
	/** 如意宝支付APP */
	PAYMENT_ALABAO_APP("ALABAO_APP", "APP如意宝支付"),
	/** 0元支付 */
	PAYMENT_ZERO("ZERO", "0元支付"),
	/** WAP支付宝支付 */
	PAYMENT_ZFB_WAP_JSDZ("ZFB_WAP_JSDZ", "WAP支付宝即时到帐"),
	/** 微信公众号支付 */
	PAYMENT_WX_GZH("WX_GZH", "微信公众号支付");
	
	private String code;
	private String name;

	private PaymentTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	/** 通过编号获取名称 */
	public static String getNameByCode(String code) {
		for (PaymentTypeEnum p : PaymentTypeEnum.values()) {
			if (p.code.equals(code)) {
				return p.name;
			}
		}
		return "-";
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	/***
	 * 得到该枚举类的map集合
	 * @return
	 */
	public  static Map<String, String> getPaymentTypeMap(){
		Map<String, String> map=new HashMap<String, String>();
		for (PaymentTypeEnum p:PaymentTypeEnum.values()) {
			map.put(p.getCode(), p.getName());
		}
		return map;
	}
	
	
	public static void main(String[] args) {
		 Map<String, String> map= getPaymentTypeMap();
		System.out.println(map.get("ZFB_WEB_DBJY"));
	}
	
}
