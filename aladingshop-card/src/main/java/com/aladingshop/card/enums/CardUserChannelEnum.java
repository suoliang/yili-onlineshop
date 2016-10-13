/**
 * 
 */
package com.aladingshop.card.enums;

/**
 * @description 益多宝使用渠道类型
 * @author 孙涛
 * @date 2015年9月11日下午2:03:32
 */
public enum CardUserChannelEnum {
	/**
	 * 1,普通消费
	 */
	ORDINARY_CONSUMPTION("1", "普通消费"),

	/**
	 * 2,转账
	 */
	TRANSFER_ACCOUNTS("2", "转账"),

	/**
	 * 3,提现
	 */
	WITHDRAW_DEPOSIT("3", "提现"),

	/**
	 * 4,转入阿拉宝
	 */
	SHIFT_TO_ALABAO("4", "转入阿拉宝");

	/**
	 * 对应code
	 */
	private String code;

	/**
	 * 消费类型
	 */
	private String name;

	private CardUserChannelEnum(String code, String name) {
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
