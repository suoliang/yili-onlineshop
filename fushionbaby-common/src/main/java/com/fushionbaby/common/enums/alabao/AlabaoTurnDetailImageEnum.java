package com.fushionbaby.common.enums.alabao;
/**
 * @description 如意消费卡转出账单详情--图片
 * @author 索亮
 * @date 2015年12月3日上午11:20:15
 */
public enum AlabaoTurnDetailImageEnum {
	/**
	 * 1,转出到如意消费卡
	 */
	TURN_TO_ALABAO("1","bill/201512/turn_ruyixiaofeika_icon.png"),
	
	/**
	 * 2,转出到银行卡
	 */
	TURN_TO_BANK("2","bill/201512/turn_yinhangcard_icon.png");
	
	/**
	 * 状态代码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	private AlabaoTurnDetailImageEnum(String code, String name) {
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
		for (AlabaoTurnDetailImageEnum c : AlabaoTurnDetailImageEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
	
}
