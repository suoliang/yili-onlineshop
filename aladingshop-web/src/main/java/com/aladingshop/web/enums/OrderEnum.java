package com.aladingshop.web.enums;

/**
 * @author 用于网页显示
 */
public enum OrderEnum {
	/**
	 * 1,等待支付
	 */
	WAITING_PAYMENT("等待支付", "1"),

	/**
	 * 2,审核中
	 */
	AUDIT("审核中", "2"),

	/**
	 * 3,审核通过
	 */
	SUCCESS_AUDIT("审核通过", "2"),

	/**
	 * 4,审核不通过(问题单)
	 */
	FAIL_AUDIT("审核失败", "2"),

	/**
	 * 5,已发货
	 */
	SUCCESS_SHIPPED("已发货", "3"),

	/**
	 * 6,会员用户确认收货,会员互联网看到,交易完成
	 */
	WEB_CONFIRM_RECEIPT("交易完成", "4"),

	/**
	 * 7,CMS管理员确认收货,对互联网公布已发货
	 */
	CMS_CONFIRM_RECEIPT("已发货", "3"),

	/**
	 * 8,交易完成
	 */
	SUCCESS_TRANSACTION("交易完成", "5");

	/**
	 * 订单状态代码
	 */
	private String code;

	/**
	 * 订单名称
	 */
	private String name;

	private OrderEnum(String code, String name) {
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
		for (OrderEnum c : OrderEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.getName();
			}
		}
		return "";
	}
}
