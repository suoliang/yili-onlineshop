/**
 * 
 */
package com.aladingshop.refund.model;

import java.util.Date;

/**
 * @description 类描述...
 * @author 徐鹏飞
 * @date 2015年8月18日下午2:46:53
 */
public class Refund {
	private Long id;
	private String batchNo;
	private Long memberId;
	private String orderCode;
	private String settleAmount;
	private String redEnvlopeAmount;
	private String paymentType;
	private String sourceCode;
	private Date createTime;
	private Long createId;
	private String refundStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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
	public String getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getRedEnvlopeAmount() {
		return redEnvlopeAmount;
	}
	public void setRedEnvlopeAmount(String redEnvlopeAmount) {
		this.redEnvlopeAmount = redEnvlopeAmount;
	}
	
	
}
