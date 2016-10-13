/**
 * 
 */
package com.fushionbaby.order.condition;

/**
 * @author mengshaobo 订单查询条件
 */
public class SoSalesOrderQueryCondition {
	/** 订单号 */
	private String code;// 订单号
	/** 付款状态 */
	private String financeStatus;// 付款状态
	/** 订单状态 */
	private String orderStatus;// 订单状态
	/** 支付方式 */
	private String paymentType;// 支付方式
	/** 删除标记 */
	private String isDelete;// 删除标记
	/** 网站会员id */
	private Long memberId;// 网站会员id
	/** 网站会员登录名 */
	private String memberName;// 网站会员登录名
	/** 积分处理 */
	private String isHandlePoint;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getIsHandlePoint() {
		return isHandlePoint;
	}

	public void setIsHandlePoint(String isHandlePoint) {
		this.isHandlePoint = isHandlePoint;
	}

}
