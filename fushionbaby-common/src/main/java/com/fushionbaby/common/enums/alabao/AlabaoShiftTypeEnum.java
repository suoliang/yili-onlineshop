package com.fushionbaby.common.enums.alabao;

/***
 * @description 如意宝转入类型
 * @author 索亮
 * @date 2015年11月26日下午3:40:40
 */
public enum AlabaoShiftTypeEnum {
	/**
	 * 1,如意消费卡转入
	 */
	ALABAO("1","如意消费卡转入"),
	/**
	 * 2,银联转入
	 */
	ALABAO_APP_UNION("2","银联转入"),
	/**
	 * 3,微信转入
	 */
	ALABAO_APP_WX("3","微信转入"),
	/**
	 * 4,支付宝转入
	 */
	ALABAO_APP_ZFB("4","支付宝转入"),
	/**
	 * 5,阿拉丁卡转入
	 */
	ALABAO_APP_YDB("5","阿拉丁卡转入"),
	/**
	 * 6,红包转入
	 */
	ALABAO_APP_RED("6","红包转入"),
	/**
	 * 7,退款转入
	 */
	ALABAO_APP_REFUND("7","退款转入"),
	/**
	 * 8,实体卡转入
	 */
	ALABAO_APP_ENTITY_CARD("8","实体卡转入");
	
	/**
	 * 状态代码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	private AlabaoShiftTypeEnum(String code,String name){
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
		for (AlabaoShiftTypeEnum c : AlabaoShiftTypeEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
	
}
