package com.fushionbaby.cms.dto;

import java.util.Date;

public class FinanceRefundApplyDto {
	/**自增id*/
	private Long id;
	/**会员id*/
	private Long memberId;
	/**订单号*/
	private String orderCode;
	/** 订单来源 订单的来源(1、app端  2、web端 3、wap端）*/
	private String orderSource;
	/** 订单支付类型 (1、支付宝 2、微信 3、银联 4、如意消费卡）*/
	private String orderPayType;
	/** 后台用户处理时间*/
	private Date dealTime;
	/** 申请退款的原因*/
	private String refundReason;
	/**该条记录的状态(1、未处理 2、已处理完成退款）*/
	private String status;
	/** 后台更新处理人的id*/
	private String dealId;
	/** 创建时间*/
	private Date createTime;
	/** 更新时间*/
	private Date updateTime;
	
	private String createTimeFrom;
	
	private String createTimeTo;
	
	private String memberName;

	private String dealName;
	/**申请退款金额*/
	private String paymentTotalActual;
	
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

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getOrderPayType() {
		return orderPayType;
	}

	public void setOrderPayType(String orderPayType) {
		this.orderPayType = orderPayType;
	}


	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateTimeFrom() {
		return createTimeFrom;
	}

	public void setCreateTimeFrom(String createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}

	public String getCreateTimeTo() {
		return createTimeTo;
	}

	public void setCreateTimeTo(String createTimeTo) {
		this.createTimeTo = createTimeTo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getPaymentTotalActual() {
		return paymentTotalActual;
	}

	public void setPaymentTotalActual(String paymentTotalActual) {
		this.paymentTotalActual = paymentTotalActual;
	}
	
	
	
}
