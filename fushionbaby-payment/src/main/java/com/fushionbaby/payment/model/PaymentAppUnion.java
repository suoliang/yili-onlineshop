package com.fushionbaby.payment.model;

import java.io.Serializable;
import java.util.Date;

/**
 * APP -- 银联支付状态表信息
 * @author King 索亮
 */
public class PaymentAppUnion implements Serializable {

	private static final long serialVersionUID = -4242733021595423073L;
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
	/** 银联支付状态 1：未支付 2：正在处理中 3：支付成功 4：业务处理失败 */
	private int unionStatus;
	/** 来源1-Android,2-IOS*/
	private String sourceCode;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** union */
	private String tn;
	/** union */
	private String qn;
	/**远程ip地址*/
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

	public int getUnionStatus() {
		return unionStatus;
	}

	public void setUnionStatus(int unionStatus) {
		this.unionStatus = unionStatus;
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

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

	public String getQn() {
		return qn;
	}

	public void setQn(String qn) {
		this.qn = qn;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

}
