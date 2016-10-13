package com.fushionbaby.core.vo;

import java.math.BigDecimal;

public class OrderParamUser {
	
	/** 用户Id*/
	private Long memberId;
	
	/** 门店编号*/
	private String storeCode;
	
	/** 付款标识码 */
	private String payOffId;

	/** 订单邮寄地址 */
	private String addressId;

	/*** 使用到的商品优惠券id |分隔 */
	private String couponsIds;

	/** 是否选中使用积分y使用n不使用积分<该字段可以为空> */
	private String isPoint;

	/** 使用积分的数量 */
	private String pointUsd;

	/** 使用账号余额 */
	private String accountMoneyUsd;

	/** 是否使用发票 */
	private String isInvoice;

	/** 发票类型1个人2公司 */
	private String invoiceType;

	/** 发票抬头 */
	private String invoiceTitle;

	/** 支付方式 */
	private String paymentType;

	/** 送货时段,会员方便的收货时间段 */
	private String sendDate;

	/** 是否使用代金券 */
	private String isCardNo;

	/** 代金券号码 */
	private String cardNo;

	/** 买家留言 */
	private String memo;

	/** 原价总金额 */
	private BigDecimal retailPriceTotal;

	/** 现价总金额 */
	private BigDecimal currentPriceTotal;

	/** 折扣的总金额 -- 优惠券 */
	private BigDecimal skuDiscountTotal;

	/** 订单总计 */
	private BigDecimal totalActual;

	/** 运费金额 */
	private BigDecimal freightPrice;
	
	/** 红包金额*/
	private BigDecimal redEnvelopeAmount;

	/** 订单编码 */
	private String orderCode;

	/** 进入订单页的类型（0.购物车结算;1.立即购买） */
	private String gotoType;

	/** 来源 */
	private String sourceCode;
	
	
	/**是否自取（自提）*/
	private String isPickUp;
	
	
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	
	public String getIsPickUp() {
		return isPickUp;
	}

	public void setIsPickUp(String isPickUp) {
		this.isPickUp = isPickUp;
	}

	public String getPayOffId() {
		return payOffId;
	}

	public void setPayOffId(String payOffId) {
		this.payOffId = payOffId;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getCouponsIds() {
		return couponsIds;
	}

	public void setCouponsIds(String couponsIds) {
		this.couponsIds = couponsIds;
	}

	public String getIsPoint() {
		return isPoint;
	}

	public void setIsPoint(String isPoint) {
		this.isPoint = isPoint;
	}

	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getIsCardNo() {
		return isCardNo;
	}

	public void setIsCardNo(String isCardNo) {
		this.isCardNo = isCardNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getRetailPriceTotal() {
		return retailPriceTotal;
	}

	public void setRetailPriceTotal(BigDecimal retailPriceTotal) {
		this.retailPriceTotal = retailPriceTotal;
	}

	public BigDecimal getCurrentPriceTotal() {
		return currentPriceTotal;
	}

	public void setCurrentPriceTotal(BigDecimal currentPriceTotal) {
		this.currentPriceTotal = currentPriceTotal;
	}

	public BigDecimal getSkuDiscountTotal() {
		return skuDiscountTotal;
	}

	public void setSkuDiscountTotal(BigDecimal skuDiscountTotal) {
		this.skuDiscountTotal = skuDiscountTotal;
	}

	public BigDecimal getTotalActual() {
		return totalActual;
	}

	public void setTotalActual(BigDecimal totalActual) {
		this.totalActual = totalActual;
	}

	public BigDecimal getFreightPrice() {
		return freightPrice;
	}

	public void setFreightPrice(BigDecimal freightPrice) {
		this.freightPrice = freightPrice;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getGotoType() {
		return gotoType;
	}

	public void setGotoType(String gotoType) {
		this.gotoType = gotoType;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getPointUsd() {
		return pointUsd;
	}

	public void setPointUsd(String pointUsd) {
		this.pointUsd = pointUsd;
	}

	public String getAccountMoneyUsd() {
		return accountMoneyUsd;
	}

	public void setAccountMoneyUsd(String accountMoneyUsd) {
		this.accountMoneyUsd = accountMoneyUsd;
	}

	public BigDecimal getRedEnvelopeAmount() {
		return redEnvelopeAmount;
	}

	public void setRedEnvelopeAmount(BigDecimal redEnvelopeAmount) {
		this.redEnvelopeAmount = redEnvelopeAmount;
	}
	
	
}
