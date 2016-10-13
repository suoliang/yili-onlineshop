package com.aladingshop.alabao.dto;


/***
 * 后台查询使用  条件封装
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月11日下午5:12:57
 */
public class AlabaoOrderCMSDto {

	/**查询时间起点*/
	private String  createTimeFrom;
	/** 查询时间截点*/
	private String  createTimeTo;
	/** 订单号*/
	private String  orderCode;
	/** 会员名称*/
	private String  memberName;
	/**订单状态*/
	private String  alabaoStatus;
	/**支付方式*/
	private String paymentType;
	/** 总金额*/
	private String totalActual;
	/**阿拉宝账户*/
	private String account;
	/** 商户订单号，支付模块生成 */
	private String orderNumber;
	/**支付交易流水号 */
	private String tradeNo;
	
	
	public String getCreateTimeFrom() {
		return createTimeFrom;
	}
	public void setCreateTimeFrom(String createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}
	public String getCreateTimeTo() {
		return createTimeTo;
	}
	public void setCreateTimeTo(String createTimeTo) {
		this.createTimeTo = createTimeTo;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getAlabaoStatus() {
		return alabaoStatus;
	}
	public void setAlabaoStatus(String alabaoStatus) {
		this.alabaoStatus = alabaoStatus;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getTotalActual() {
		return totalActual;
	}
	public void setTotalActual(String totalActual) {
		this.totalActual = totalActual;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	
	
}
