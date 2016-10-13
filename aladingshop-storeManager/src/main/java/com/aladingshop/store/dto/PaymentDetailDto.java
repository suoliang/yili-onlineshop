package com.aladingshop.store.dto;

public class PaymentDetailDto {
	/** 商户订单号*/
	private String orderNumber;
	/** 交易流水号*/
	private String tradeNo;
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
}
