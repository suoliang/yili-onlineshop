package com.fushionbaby.cms.dto;
/**
 * 
 * @author 余浩
 *
 *
 */
public class SoSoMemberDto {
	/**
	 * 订单编号
	 */
	private String orderCode;
	/**
	 * 收货人姓名
	 */
	private String receiver;
	/**
	 * 收货人手机号
	 */
	private String receiverMobile;
	/**
	 * 订单创建时间
	 */
	private String createTimeFrom;
	/**
	 * 订单结束时间
	 */
	private String createTimeTo;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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
	
	
}
