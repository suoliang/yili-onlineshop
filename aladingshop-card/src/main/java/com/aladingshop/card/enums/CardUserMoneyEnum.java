/**
 * 
 */
package com.aladingshop.card.enums;

/**
 * @description 益多宝使用金额类型
 * @author 孙涛
 * @date 2015年9月11日下午2:15:21
 */
public enum CardUserMoneyEnum {
	/**
	 * 1,本金
	 */
	CORPUS("1", "本金"),

	/**
	 * 2,益多宝收益额
	 */
	YIELD("2", "益多宝收益额"),

	/**
	 * 3,益多宝赠券额
	 */
	COUPON_AMOUNT("3", "益多宝赠券额");

	/**
	 * 对应code
	 */
	private String code;

	/**
	 * 消费金额类型
	 */
	private String name;

	private CardUserMoneyEnum(String code, String name) {
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

	/** 根据对应的汉字枚举得到代码 */
	public static String parseCode(String code) {
		for (CardUserMoneyEnum c : CardUserMoneyEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
}
