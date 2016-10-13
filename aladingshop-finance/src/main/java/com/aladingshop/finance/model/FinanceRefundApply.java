package com.aladingshop.finance.model;

import java.util.Date;

/***
 * 退款申请记录表
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月6日下午5:42:31
 */
public class FinanceRefundApply {
	
	/**自增id*/
	private Long id;
	/**会员id*/
	private Long memberId;
	/**订单号*/
	private String orderCode;
	/** 订单来源 订单的来源*/
	private String orderSource;
	/** 订单支付类型 */
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
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
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
	public String getOrderPayType() {
		return orderPayType;
	}
	public void setOrderPayType(String orderPayType) {
		this.orderPayType = orderPayType;
	}

	
	

}
