package com.fushionbaby.core.vo;

import java.util.List;

public class OrderUser {
	/** 订单状态 **/
	private String orderStatus;
	/** 订单状态信息显示-对互联网显示 */
	private String orderInfo;
	/** 订单编号 **/
	private String orderCode;
	/** 商品列表 **/
	private List<SkuByOrderLine> skuByOrderLines;
	/** 实付运费 **/
	private String actualTransferFee;
	/** 代金卷金额 **/
	private String cardAmount;
	/** 积分金额 */
	private String epointsMoney;
	/** 红包金额*/
	private String redAmount;
	/** 卖家是否已发货 **/
	private String transStatus;
	/** 卖家发货时间 */
	private String deliveryTime;
	/** 收货人 **/
	private String receiver;
	/** 收货人手机号码 **/
	private String receiverMobile;
	/** 收货人省 */
	private String province;
	/** 收货人市 */
	private String city;
	/** 收货人区 */
	private String district;
	/** 收货人详细地址 **/
	private String address;
	/** 实际付款金额（含运费） **/
	private String paymentTotalActual;
	/** PC付款方式,如果没有默认为支付宝 */
	private String webPaymentType;
	/** APP付款方式,如果没有默认为支付宝 */
	private String appPaymentType;
	/** 客服电话 **/
	private String customerServicePhone;
	/** 下单时间 **/
	private String createOrderTime;
	/** 下单截至时间,系统取消订单时间 */
	private String orderEndTime;
	/** 成功支付时间 **/
	private String paymentCompleteTime;
	/** 买家留言 */
	private String memo;
	/** 订单来源-手机，web... */
	private String sourceCode;
	/** 审核不通过时的原因显示 */
	private String failReason;
	/** 订单行所有购买的商品数量加总*/
	private Long orderLineTotalQuantity;
	/** 确认收货时间 **/
	private String lastReceiveTime;
	
	/** APP端详情展示 */
	private List<AppOrderDetail> listAppDetail;
	
	
	public String getLastReceiveTime() {
		return lastReceiveTime;
	}

	public void setLastReceiveTime(String lastReceiveTime) {
		this.lastReceiveTime = lastReceiveTime;
	}

	public Long getOrderLineTotalQuantity() {
		return orderLineTotalQuantity;
	}

	public void setOrderLineTotalQuantity(Long orderLineTotalQuantity) {
		this.orderLineTotalQuantity = orderLineTotalQuantity;
	}

	public String getRedAmount() {
		return redAmount;
	}

	public void setRedAmount(String redAmount) {
		this.redAmount = redAmount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public List<SkuByOrderLine> getSkuByOrderLines() {
		return skuByOrderLines;
	}

	public void setSkuByOrderLines(List<SkuByOrderLine> skuByOrderLines) {
		this.skuByOrderLines = skuByOrderLines;
	}

	public String getActualTransferFee() {
		return actualTransferFee;
	}

	public void setActualTransferFee(String actualTransferFee) {
		this.actualTransferFee = actualTransferFee;
	}

	public String getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(String cardAmount) {
		this.cardAmount = cardAmount;
	}

	public String getEpointsMoney() {
		return epointsMoney;
	}

	public void setEpointsMoney(String epointsMoney) {
		this.epointsMoney = epointsMoney;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPaymentTotalActual() {
		return paymentTotalActual;
	}

	public void setPaymentTotalActual(String paymentTotalActual) {
		this.paymentTotalActual = paymentTotalActual;
	}

	public String getWebPaymentType() {
		return webPaymentType;
	}

	public void setWebPaymentType(String webPaymentType) {
		this.webPaymentType = webPaymentType;
	}

	public String getAppPaymentType() {
		return appPaymentType;
	}

	public void setAppPaymentType(String appPaymentType) {
		this.appPaymentType = appPaymentType;
	}

	public String getCustomerServicePhone() {
		return customerServicePhone;
	}

	public void setCustomerServicePhone(String customerServicePhone) {
		this.customerServicePhone = customerServicePhone;
	}

	public String getCreateOrderTime() {
		return createOrderTime;
	}

	public void setCreateOrderTime(String createOrderTime) {
		this.createOrderTime = createOrderTime;
	}

	public String getPaymentCompleteTime() {
		return paymentCompleteTime;
	}

	public void setPaymentCompleteTime(String paymentCompleteTime) {
		this.paymentCompleteTime = paymentCompleteTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOrderEndTime() {
		return orderEndTime;
	}

	public void setOrderEndTime(String orderEndTime) {
		this.orderEndTime = orderEndTime;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public List<AppOrderDetail> getListAppDetail() {
		return listAppDetail;
	}

	public void setListAppDetail(List<AppOrderDetail> listAppDetail) {
		this.listAppDetail = listAppDetail;
	}

}
