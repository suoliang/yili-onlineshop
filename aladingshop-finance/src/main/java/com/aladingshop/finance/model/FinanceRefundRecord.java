package com.aladingshop.finance.model;

import java.math.BigDecimal;
import java.util.Date;

/***
 * 退款成功的记录
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月6日下午5:41:36
 */
public class FinanceRefundRecord {

	/**自增id*/
	private Long id;
	/**会员id*/
	private Long memberId;
	/**订单号*/
	private String orderCode;
	/** 订单的金额（要退款的金额）*/
	private BigDecimal orderAmount;
	/**订单的支付方式 1、支付宝 2、微信 3、银联 4、如意消费卡*/
	private String orderPayType;
	/** 订单来源 订单的来源(1、app端  2、web端 3、wap端）*/
	private String orderSource;
	/**完成时间*/
	private Date completeTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderPayType() {
		return orderPayType;
	}
	public void setOrderPayType(String orderPayType) {
		this.orderPayType = orderPayType;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	
}
