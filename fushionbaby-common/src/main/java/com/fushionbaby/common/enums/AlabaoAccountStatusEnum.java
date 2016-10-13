package com.fushionbaby.common.enums;

/**
 * @author 张明亮
 * 如意宝会员状态
 */
public enum AlabaoAccountStatusEnum {
	/**
	 * 1,待审核
	 */
	WAITING_AUDIT("1","待审核"),
	
	/**
	 * 2,审核通过
	 */
	SUCCESS_AUDIT("2","审核通过"),
	
	/**
	 * 3,审核不通过
	 */
	FAIL_AUDIT("3","审核不通过"),
	
	/**
	 * 4,注销
	 */
	CANCEL("4","注销");
	
	
	/**
	 * 状态代码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	private AlabaoAccountStatusEnum(String code, String name) {
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
		for (AlabaoAccountStatusEnum c : AlabaoAccountStatusEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
}
