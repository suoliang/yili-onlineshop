package com.fushionbaby.common.enums;

public enum SkuEpointLabelCodeEnum {
	/**
	 * 1,热门兑换
	 * @return 
	 */
	HOT_EXCHANGE("1","热门兑换"),
	
	/**
	 * 2,可以兑换
	 */
	COMMON_EXCHANGE("2","可以兑换");
	
	
	/**
	 * 标签代码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	private SkuEpointLabelCodeEnum(String code, String name) {
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
		for (SkuEpointLabelCodeEnum c : SkuEpointLabelCodeEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
}
