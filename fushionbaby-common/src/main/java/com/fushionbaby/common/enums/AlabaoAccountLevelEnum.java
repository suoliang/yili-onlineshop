package com.fushionbaby.common.enums;

/**
 * @author 张明亮
 * 如意宝会员状态
 */
public enum AlabaoAccountLevelEnum {
	/**
	 * 1,普通用户
	 */
	COMMON_ACCOUNT("1","普通用户"),
	
	/**
	 * 2,测试用户
	 */
	TEST_ACCOUNT("2","测试用户"),
	
	/**
	 * 3,理财vip用户
	 */
	VIP_ACCOUNT("3","理财vip用户");
	
	
	/**
	 * 状态代码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	private AlabaoAccountLevelEnum(String code, String name) {
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
		for (AlabaoAccountLevelEnum c : AlabaoAccountLevelEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
}
