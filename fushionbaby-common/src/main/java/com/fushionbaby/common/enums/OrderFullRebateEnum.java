package com.fushionbaby.common.enums;
/***
 * 订单满赠的枚举
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月28日上午9:43:53
 */
public enum OrderFullRebateEnum {
	
	/** 订单满100赠送30元代金券*/
	FULL_ONE_HUNDRED_REBATE("100","30"),
	/**订单满50赠送10元代金券*/
	FULL_FIFTY_REBATE("50","10");
	
	/**订单满金额*/
	private String orderMoney;
	/**优惠券赠送金额*/
	private String rebateMoney;

	/** 构造方法 */
	private OrderFullRebateEnum(String order, String rebate) {
		this.orderMoney = order;
		this.rebateMoney = rebate;
	}

	/** 通过orderMoney 得到 rebateMoney */
	public static String getRebateMoney(String order) {
		for (OrderFullRebateEnum c : OrderFullRebateEnum.values()) {
			if (c.orderMoney.equals(order)) {
				return c.rebateMoney;
			}
		}
		return null;
	}
	/** 通过赠券金额 得到订单金额 */
	public static String getOrderMoney(String rebate) {
		for (OrderFullRebateEnum c : OrderFullRebateEnum.values()) {
			if (c.rebateMoney.equals(rebate)) {
				return c.orderMoney;
			}
		}
		return null;
	}
	public String getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(String order) {
		this.orderMoney = order;
	}

	public String getRebateMoney() {
		return rebateMoney;
	}

	public void setRebateMoney(String rebate) {
		this.rebateMoney = rebate;
	}

	public static void main(String[] args) {
		System.out.println(OrderFullRebateEnum.FULL_FIFTY_REBATE.getOrderMoney());
		System.out.println(OrderFullRebateEnum.FULL_FIFTY_REBATE.getRebateMoney());
		System.out.println(OrderFullRebateEnum.getOrderMoney("10"));
		System.out.println(OrderFullRebateEnum.getRebateMoney("100"));
	}
	
}
