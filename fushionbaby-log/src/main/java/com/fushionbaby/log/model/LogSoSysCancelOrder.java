package com.fushionbaby.log.model;

import java.util.Date;

/**
 * 
 * @author cyla
 * <p>系统取消订单</p>
 *
 */
public class LogSoSysCancelOrder {
	
	// 系统取消Id
	private Long id;
	// 订单编号
	private String orderCode;
	// 取消时间
	private Date cancelTime;
	// 用户Id
	private Long memberId;
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
	
	
}
