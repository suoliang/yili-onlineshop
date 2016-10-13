/**
 * 
 */
package com.aladingshop.card.enums;

/**
 * @description 益多宝卡状态
 * @author 孙涛
 * @date 2015年10月12日下午2:17:07
 */
public enum CardStatusEnum {
	/**
	 * 1,期限内,正常返券时间内
	 */
	Term_IN("1", "期限内"),

	/**
	 * 2,退卡
	 */
	BACK_CARD("2", "退卡"),

	/**
	 * 3,已删除
	 */
	ALREADY_DEL("3", "已删除"),

	/**
	 * 4,期限外
	 */
	Term_OUT("4", "期限外");

	/**
	 * 对应code
	 */
	private String code;

	/**
	 * 消费类型
	 */
	private String name;

	private CardStatusEnum(String code, String name) {
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
		for (CardUserChannelEnum c : CardUserChannelEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
}
