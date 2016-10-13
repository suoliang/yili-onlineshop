package com.fushionbaby.common.enums.alabao;
/**
 * @description 如意消费卡收券详情--图片
 * @author 索亮
 * @date 2015年12月3日上午11:30:29
 */
public enum AlabaoIncomeDetailImageEnum {
	/**
	 * 1,如意消费卡收券
	 */
	ALABAO_INCOME("1","bill/201512/income_ruyixiaofeika_icon.png");
	
	/**
	 * 状态代码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	private AlabaoIncomeDetailImageEnum(String code, String name) {
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
		for (AlabaoIncomeDetailImageEnum c : AlabaoIncomeDetailImageEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
	
}
