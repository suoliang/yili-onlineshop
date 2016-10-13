package com.fushionbaby.common.enums.alabao;

/**
 * @description 如意消费卡转出方式
 * @author 索亮
 * @date 2015年12月3日上午11:14:10
 */
public enum AlabaoTurnOutEnum {
	/**
	 * 1,转出到如意消费卡
	 */
	TURN_TO_ALABAO("1","转出到如意消费卡"),
	
	/**
	 * 2,转出到银行卡
	 */
	TURN_TO_BANK("2","转出到银行卡");
	
	/**
	 * 状态代码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	private AlabaoTurnOutEnum(String code, String name) {
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
		for (AlabaoTurnOutEnum c : AlabaoTurnOutEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
	
}
