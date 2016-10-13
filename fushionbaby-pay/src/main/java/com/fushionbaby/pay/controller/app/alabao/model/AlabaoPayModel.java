package com.fushionbaby.pay.controller.app.alabao.model;
/**
 * @description 如意宝支付传给APP端的实体MODEL
 * @author 索亮
 * @date 2015年10月13日下午3:54:56
 */
public class AlabaoPayModel {
	/** 订单描述 */
	private String orderDesc;
	/** 实际付款金额 */
	private String actualMoney;
	/** 原价 */
	private String originalPrice;
	/** 折扣描述 */
	private String payDiscountDesc;

	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public String getActualMoney() {
		return actualMoney;
	}
	public void setActualMoney(String actualMoney) {
		this.actualMoney = actualMoney;
	}
	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	public String getPayDiscountDesc() {
		return payDiscountDesc;
	}
	public void setPayDiscountDesc(String payDiscountDesc) {
		this.payDiscountDesc = payDiscountDesc;
	}

}
