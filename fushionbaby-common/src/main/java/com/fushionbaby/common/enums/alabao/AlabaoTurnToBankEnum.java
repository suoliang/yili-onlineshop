package com.fushionbaby.common.enums.alabao;

/**
 * @description 如意消费卡转出到银行时的状态信息
 * @author 索亮
 * @date 2015年11月26日下午5:52:23
 */
public enum AlabaoTurnToBankEnum {
	/**
	 * 1,待审核
	 */
	TURN_BANK_WAIT_AUDIT("1","待审核"),
	/**
	 * 2,审核通过
	 */
	TURN_BANK_AUDIT_SUCCESS("2","审核通过"),
	/**
	 * 3,审核不通过
	 */
	TURN_BANK_AUDIT_FAIL("3","审核不通过"),
	/**
	 * 4,转出成功，完成
	 */
	TURN_BANK_SUCCESS("4","转出成功");
	
	/**
	 * 状态代码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	private AlabaoTurnToBankEnum(String code,String name){
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
		for (AlabaoTurnToBankEnum c : AlabaoTurnToBankEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
	
}
