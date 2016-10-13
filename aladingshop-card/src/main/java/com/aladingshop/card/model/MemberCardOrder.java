package com.aladingshop.card.model;

import java.math.BigDecimal;
import java.util.Date;

/***
 * 益多宝订单
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年9月29日下午4:02:56
 */
public class MemberCardOrder {

	/** 自增 id */
	private Long id;
	/** 会员的id */
	private Long memberId;
	/**会员名 */
	private String memberName;
	/** 订单号*/
	private String orderCode;
	/**订单状态 */
	private String orderStatus;
	/**订单总金额 */
	private BigDecimal totalActual;
	/** 支付方式*/
	private String payType;
	/** 支付完成时间*/
	private Date payCompleteTime;
	/** 订单的财务状态*/
	private String financeStatus;
	/** 订单来源*/
	private String sourceCode;
	/** 储蓄卡的创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** 更新者id*/
	private Long updateId;
	/** 说明备注*/
   private String memo;
   
   private String orderType;
   
   
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
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
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public BigDecimal getTotalActual() {
		return totalActual;
	}
	public void setTotalActual(BigDecimal totalActual) {
		this.totalActual = totalActual;
	}
	
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Date getPayCompleteTime() {
		return payCompleteTime;
	}
	public void setPayCompleteTime(Date payCompleteTime) {
		this.payCompleteTime = payCompleteTime;
	}
	public String getFinanceStatus() {
		return financeStatus;
	}
	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
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
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}	
	
	
}
