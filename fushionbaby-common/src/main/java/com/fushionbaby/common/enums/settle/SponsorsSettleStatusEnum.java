package com.fushionbaby.common.enums.settle;

/**
 * @description 商户结算状态
 * @author 索亮
 * @date 2015年12月23日下午3:51:23
 */
public enum SponsorsSettleStatusEnum {
	/**
	 * 1,未结算，待结算
	 */
	NOT_SETTLED("1","待结算"),
	/**
	 * 2,结算中，申请中
	 */
	ING_SETTLED("2","结算中"),
	/**
	 * 3,已结算，结算完成
	 */
	HAS_SETTLED("3","已结算");
	
	/**
	 * 状态代码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	private SponsorsSettleStatusEnum(String code,String name){
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
		for (SponsorsSettleStatusEnum c : SponsorsSettleStatusEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
	
}
