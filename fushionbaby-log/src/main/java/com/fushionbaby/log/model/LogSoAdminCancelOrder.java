package com.fushionbaby.log.model;

import java.util.Date;

/**
 * 
 * @author cyla
 * <p>用户取消订单model</p>
 *
 */
public class LogSoAdminCancelOrder {
	
	// 取消Id
	private Long id;
	// 订单编号
	private String orderCode;
	// 取消时间
	private Date cancelTime;
	// 用户Id
	private Long memberId;
	// 取消原因
	private String sysCancelReason;
	
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
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getSysCancelReason() {
		return sysCancelReason;
	}
	public void setSysCancelReason(String sysCancelReason) {
		this.sysCancelReason = sysCancelReason;
	}
	
	
}
