package com.fushionbaby.order.dto;
/**
 * 订单基本表查询条件dto
 * @author Leon
 *
 */
public class OrderBaseDto {
	/**订单编码*/
	private String orderCode;
	/**会员名*/
	private String memberName;
	/**订单状态*/
	private String orderStatus;
	/**下单时间起始*/
	private String createTimeFrom;
	/**下单时间结束*/
	private String createTimeTo;
	/**支付方式*/
	private String paymentType;
	/**订单类型*/
	private String orderType;
	
	/** 门店编号*/
	private String storeCode;
	
	
	
	

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

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

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	

}
