package com.fushionbaby.common.enums.alabao;

public enum AlabaoConsumeDetailImageEnum {
	/**
	 * 1,如意消费卡消费
	 */
	ALABAO_CONSUME("1","bill/201512/consume_ruyixiaofeika_icon.png");
	
	/**
	 * 状态代码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	private AlabaoConsumeDetailImageEnum(String code, String name) {
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
		for (AlabaoConsumeDetailImageEnum c : AlabaoConsumeDetailImageEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
	
}
