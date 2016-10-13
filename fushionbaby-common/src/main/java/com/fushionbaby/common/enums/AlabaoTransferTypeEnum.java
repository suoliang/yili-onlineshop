package com.fushionbaby.common.enums;

import java.util.HashMap;
import java.util.Map;

/***
 * 如意消费卡的转入方式
 * @author chenyingtao
 *
 *
 */
public enum AlabaoTransferTypeEnum {
	/**
	 * 1,如意消费卡
	 */
	ALABAO("1","如意消费卡"),
	
	/**
	 * 2,银联
	 */
	ALABAO_APP_UNION("2","银联"),
	
	/**
	 * 3,微信
	 */
	ALABAO_APP_WX("3","微信"),
	
	/**
	 * 4,支付宝
	 */
	ALABAO_APP_ZFB("4","支付宝"),
	
	ALABAO_APP_YDB("5","益多宝"),
	ALABAO_APP_RED("6","红包"),
	ALABAO_APP_REFUND("7","退款"),
	ALABAO_APP_ENTITY_CARD("8","实体卡");
	
	
	/**
	 * 状态代码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	private AlabaoTransferTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/** 根据枚举代码得到对应的汉字 */
	public static String parseCode(String code) {
		for (AlabaoTransferTypeEnum c : AlabaoTransferTypeEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
	
	/** 通过name得到code */
	public static String getCode(String name) {
		for (AlabaoTransferTypeEnum c : AlabaoTransferTypeEnum.values()) {
			if (c.name.equals(name)) {
				return c.code;
			}
			
		}
		return null;
		}
		
	/** 通过code得到name */
		public static String getName(String code) {
			for (AlabaoTransferTypeEnum c : AlabaoTransferTypeEnum.values()) {
				if (c.code.equals(code)) {
					return c.name;
				}
			}
	    	return null;
	}
	
		
		/***
		 * 得到该枚举类的map集合
		 * @return
		 */
		public static Map<String, String> getAlabaoTransferTypeMap(){
			Map<String, String> map=new HashMap<String, String>();
			for(AlabaoTransferTypeEnum o:AlabaoTransferTypeEnum.values()){
				map.put(o.getCode(), o.getName());
			}
			return map;
		}
	
	public static void main(String[] args) {
		System.out.println(AlabaoTransferTypeEnum.getName("1") );
		System.out.println(AlabaoTransferTypeEnum.getCode("支付宝") );
	    Map<String, String> map=AlabaoTransferTypeEnum.getAlabaoTransferTypeMap();
    for (String key : map.keySet()) {
    	System.out.println(key);
		
	}
	}
}
