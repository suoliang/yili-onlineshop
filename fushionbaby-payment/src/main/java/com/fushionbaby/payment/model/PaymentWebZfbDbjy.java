package com.fushionbaby.payment.model;

import java.util.Date;
/**
 * 
 * @author cyla
 *
 */
public class PaymentWebZfbDbjy {
	
	private Long id;
	private String orderNumber;
	private String soCode;
	private Long memberId;
	private String totalAmount;
	private String tradeTime;
	private String soDes;
	private String logisticsType;
	private String logisticsPayment;
	private String logisticsFee;
	private String noFreightAmount;
	private int zfbStatus;
	private Date createtime;
	private Date updateTime;
	private String zfbTradeNo;
	private String remoteAddr;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getSoCode() {
		return soCode;
	}
	public void setSoCode(String soCode) {
		this.soCode = soCode;
	}

	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getSoDes() {
		return soDes;
	}
	public void setSoDes(String soDes) {
		this.soDes = soDes;
	}
	public String getLogisticsType() {
		return logisticsType;
	}
	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}
	public String getLogisticsPayment() {
		return logisticsPayment;
	}
	public void setLogisticsPayment(String logisticsPayment) {
		this.logisticsPayment = logisticsPayment;
	}
	public String getLogisticsFee() {
		return logisticsFee;
	}
	public void setLogisticsFee(String logisticsFee) {
		this.logisticsFee = logisticsFee;
	}
	public String getNoFreightAmount() {
		return noFreightAmount;
	}
	public void setNoFreightAmount(String noFreightAmount) {
		this.noFreightAmount = noFreightAmount;
	}
	public int getZfbStatus() {
		return zfbStatus;
	}
	public void setZfbStatus(int zfbStatus) {
		this.zfbStatus = zfbStatus;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getZfbTradeNo() {
		return zfbTradeNo;
	}
	public void setZfbTradeNo(String zfbTradeNo) {
		this.zfbTradeNo = zfbTradeNo;
	}
	public String getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	
}
