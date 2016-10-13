/**
 * 
 */
package com.aladingshop.store.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author chenyingtao
 *
 *
 */
public class OrderDto {
	
	
	
	private Long id;



	private String orderCode;

	private Date createTime;

	private String memo;

	private String sysCancelReason;

	private BigDecimal totalActual;

	private String orderStatus;

	private Long memberId;

	private String memberName;
	private String receiver;
	private String receiverMobile;
	private String address;
	private BigDecimal paymentTotalActual;
	
	
	public BigDecimal getPaymentTotalActual() {
		return paymentTotalActual;
	}

	public void setPaymentTotalActual(BigDecimal paymentTotalActual) {
		this.paymentTotalActual = paymentTotalActual;
	}

	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}


	public String getSysCancelReason() {
		return sysCancelReason;
	}

	public void setSysCancelReason(String sysCancelReason) {
		this.sysCancelReason = sysCancelReason == null ? null : sysCancelReason.trim();
	}

	public BigDecimal getTotalActual() {
		return totalActual;
	}

	public void setTotalActual(BigDecimal totalActual) {
		this.totalActual = totalActual;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}


	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
}
