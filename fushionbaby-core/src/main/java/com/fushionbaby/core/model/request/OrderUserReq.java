package com.fushionbaby.core.model.request;


/**
 * app根据条件查询订单列表请求对象封装
 * 
 * @author sun tao 2015年7月14日
 */
public class OrderUserReq extends RequestBase {
	/** 会员ID **/
	private String sid;
	/** 订单编号 **/
	private String orderCode;
	/** 订单状态 **/
	private String orderStatus;
	/** 待评价(只传y且订单状态为空) */
	private String evaluateStatus;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
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

	public String getEvaluateStatus() {
		return evaluateStatus;
	}

	public void setEvaluateStatus(String evaluateStatus) {
		this.evaluateStatus = evaluateStatus;
	}

}
