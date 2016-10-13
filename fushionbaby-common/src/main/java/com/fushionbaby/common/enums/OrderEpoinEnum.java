package com.fushionbaby.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum OrderEpoinEnum {

	APP_CODE("1","APP端"),
	
	ANDROID_CODE("2","安卓客户端"),
	
	IOS_CODE("3","IOS客户端"),
	
	WEB_CODE("4","商城客户端"),
	
	OPERATE_CODE("5","运营后台"),
	
	TIMING_TASK_CODE("6","定时任务"),
	
	CMS_CODE("7","CMS管理系统"),
	
	ERP_CODE("8","ERP管理系统"),
	
	WAP_CODE("9","WAP触屏版");
	
	private String code;
	
	private String name;

	private OrderEpoinEnum(String code, String name) {
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
		for (OrderEpoinEnum c : OrderEpoinEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
	
	/***
	 * 得到该枚举类的map集合
	 * @return
	 */
	public static Map<String, String> getOrderConfigServerMap(){
		Map<String, String> map=new HashMap<String, String>();
		for(OrderEpoinEnum o:OrderEpoinEnum.values()){
			map.put(o.getCode(), o.getName());
		}
		return map;
	}
	
	public static void main(String[] args) {
		System.out.println(getOrderConfigServerMap().size());
	}
	
	
}
