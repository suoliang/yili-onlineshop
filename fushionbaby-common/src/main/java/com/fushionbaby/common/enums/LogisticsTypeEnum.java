/**
 * 
 */
package com.fushionbaby.common.enums;

/**
 * @author mengshaobo 物流类型
 */
public enum LogisticsTypeEnum {
	POST("平邮"), EXPRESS("其他快递"), EMS("EMS"), DIRECT("无需物流");

	private String name;

	private LogisticsTypeEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
