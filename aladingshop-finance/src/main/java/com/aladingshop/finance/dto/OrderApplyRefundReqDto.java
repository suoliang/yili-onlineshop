package com.aladingshop.finance.dto;

/***
 * 订单申请退款的请求参数封装
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月13日下午8:00:47
 */
public class OrderApplyRefundReqDto {
	/** 用户标志*/
	private String sid;
	/** 订单号*/
	private String orderCode;
	/**来源*/
	private String sourceCode;
    /** 退款原因*/
	private String refundReason;
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getRefundReason() {
		return refundReason;
	}
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	
	
}
