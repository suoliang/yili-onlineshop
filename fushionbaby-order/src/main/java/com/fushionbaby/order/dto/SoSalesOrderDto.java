package com.fushionbaby.order.dto;

/**
 * @author 张明亮
 */
public class SoSalesOrderDto {
	private String skuCode;//商品编码
	private String code;//订单号
	private String financeStatus;//付款状态
	private String transStatus;//物流状态
	private String orderStatus;//订单状态
	private String paymentType;//支付方式
	private String isDelete;//删除标记
    private String createTimeFrom;//下单日期区间起始
    private String createTimeTo;//下单日期区间结束
    private String paymentCompleteTimeFrom;//订单付款时间区间起始
    private String paymentCompleteTimeTo;//订单付款时间区间结束
    private String lastReceiveTimeFrom;//订单收货时间区间起始
    private String lastReceiveTimeTo;//下单日期区间结束
    private Long memberId;//网站会员id
    private String memberName;//网站会员登录名
    private String totalActualStart;//订单金额区间
    private String totalActualEnd;//订单金额区间
    private String totalActual;//订单金额区间
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
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
	public String getPaymentCompleteTimeFrom() {
		return paymentCompleteTimeFrom;
	}
	public void setPaymentCompleteTimeFrom(String paymentCompleteTimeFrom) {
		this.paymentCompleteTimeFrom = paymentCompleteTimeFrom;
	}
	public String getPaymentCompleteTimeTo() {
		return paymentCompleteTimeTo;
	}
	public void setPaymentCompleteTimeTo(String paymentCompleteTimeTo) {
		this.paymentCompleteTimeTo = paymentCompleteTimeTo;
	}
	public String getLastReceiveTimeFrom() {
		return lastReceiveTimeFrom;
	}
	public void setLastReceiveTimeFrom(String lastReceiveTimeFrom) {
		this.lastReceiveTimeFrom = lastReceiveTimeFrom;
	}
	public String getLastReceiveTimeTo() {
		return lastReceiveTimeTo;
	}
	public void setLastReceiveTimeTo(String lastReceiveTimeTo) {
		this.lastReceiveTimeTo = lastReceiveTimeTo;
	}
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
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
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
	public String getTotalActualStart() {
		return totalActualStart;
	}
	public void setTotalActualStart(String totalActualStart) {
		this.totalActualStart = totalActualStart;
	}
	public String getTotalActualEnd() {
		return totalActualEnd;
	}
	public void setTotalActualEnd(String totalActualEnd) {
		this.totalActualEnd = totalActualEnd;
	}
	public String getTotalActual() {
		return totalActual;
	}
	public void setTotalActual(String totalActual) {
		this.totalActual = totalActual;
	}
	
}
