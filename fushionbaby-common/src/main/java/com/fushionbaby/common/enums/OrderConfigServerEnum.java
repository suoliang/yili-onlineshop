package com.fushionbaby.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 张明亮
 * 公司内部订单的状态记录CMS管理使用
 */
public enum OrderConfigServerEnum {
	/**
	 * 1,等待支付
	 */
	WAITING_PAYMENT("1","等待支付"),
	
	/**
	 * 2,审核中
	 */
	AUDIT("2","审核中"),
	
	/**
	 * 3,审核通过
	 */
	SUCCESS_AUDIT("3","审核通过"),
	
	/**
	 * 4,审核不通过(问题单)
	 */
	FAIL_AUDIT("4","审核不通过(问题单)"),
	
	/**
	 * 5,已发货
	 */
	SUCCESS_SHIPPED("5","已发货"),
	
	/**
	 * 6,会员用户确认收货
	 */
	WEB_CONFIRM_RECEIPT("6","会员确认收货"),
	
	/**
	 * 7,CMS管理员确认收货
	 */
	CMS_CONFIRM_RECEIPT("7","CMS管理员确认收货"),
	
	/**
	 * 8,交易完成
	 */
	SUCCESS_TRANSACTION("8","交易完成"),
	
	/**
	 * 9,会员取消订单
	 */
	WEB_CANCEL("9","会员取消"),
	
	/**
	 * 10,系统定时取消未付款的订单
	 */
	CMS_TIMERTASK_CANCEL("10","系统取消"),
	
	/***
	 * 11,用户拒收
	 */
	USER_REJECT("11","用户拒收"),
	
	/***
	 * 12,已退款
	 */
	USER_REFUND("12","已退款");
	
	/**
	 * 订单状态代码
	 */
	private String code;
	
	/**
	 * 订单名称
	 */
	private String name;
	
	private OrderConfigServerEnum(String code, String name) {
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
		for (OrderConfigServerEnum c : OrderConfigServerEnum.values()) {
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
		for(OrderConfigServerEnum o:OrderConfigServerEnum.values()){
			map.put(o.getCode(), o.getName());
		}
		return map;
	}
	
	public static void main(String[] args) {
		System.out.println(getOrderConfigServerMap().size());
	}
	
	
	
}
