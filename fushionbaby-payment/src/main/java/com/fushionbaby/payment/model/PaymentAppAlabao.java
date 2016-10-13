package com.fushionbaby.payment.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 如意宝支付状态信息记录
 * @author 索亮
 * @date 2015年10月13日上午11:21:06
 */
public class PaymentAppAlabao implements Serializable  {

	private static final long serialVersionUID = -8479379258036617611L;
	/** 主键id */
	private Long id;
	/** 订单号 */
	private String orderCode;
	/** 会员id */
	private Long memberId;
	/** 如意宝账户account */
	private String account;
	/** 订单金额 */
	private String settleAmount;
	/** 交易开始日期时间yyyyMMddHHmmss */
	private String tradeTime;
	/** 订单简介 */
	private String orderDes;
	/** 如意宝支付状态 1：未支付 2：正在处理中 3：支付成功 4：业务处理失败 */
	private int alabaoStatus;
	/** 来源1-Android,2-IOS*/
	private String sourceCode;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/**远程ip地址*/
	private String remoteAddr;

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

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public int getAlabaoStatus() {
		return alabaoStatus;
	}

	public void setAlabaoStatus(int alabaoStatus) {
		this.alabaoStatus = alabaoStatus;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	
}
