package com.fushionbaby.payment.model;

import java.util.Date;

/***
 * WEB -- 支付宝支付状态表信息
 * 
 * @author Leon
 *
 */
public class PaymentWebZfbJsdz {
	/** 主键id */
	private Long id;
	/** 商户订单号，支付模块生成 */
	private String orderNumber;
	/** 订单号 */
	private String orderCode;
	/** 会员id */
	private Long memberId;
	/** 订单金额 */
	private String settleAmount;
	/** 交易开始日期时间yyyyMMddHHmmss */
	private String tradeTime;
	/** 订单简介 */
	private String orderDes;
	/** 支付状态 TRADE_SUCCESS */
	private String zfbStatus;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** 支付宝交易号 */
	private String zfbTradeNo;
	/** 远程ip地址 */
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getOrderDes() {
		return orderDes;
	}

	public void setOrderDes(String orderDes) {
		this.orderDes = orderDes;
	}

	public String getZfbStatus() {
		return zfbStatus;
	}

	public void setZfbStatus(String zfbStatus) {
		this.zfbStatus = zfbStatus;
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

}
