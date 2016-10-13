package com.fushionbaby.facade.biz.common.order.dto;

/***
 * 为app和web的订单部分的公用方法准备的dto
 * 
 * 运费，积分，代金券，发票，订单总计 ，获得最终的钱数
 * 
 * @author xupeijun
 *
 */
public class OrderExtraCommonDto {
	/** 用户标志的sid */
	private String sid;
	/** 支付的订单编号（id） */
	private String payOffId;
	/** 地域code */
	private String areaCode;
	/** 是否使用积分 */
	private String isUsePoint;
	/** 是否使用发票 */
	private String isUseTax;
	/** 代金券号码 */
	private String couponCode;
	/** 是否使用代金券 */
	private String isUseCouponCard;
	/** 使用账号金额 */
	private String useAccountMoney;
	/** 积分 */
	private String epoints;
	/** 使用益多宝收益额账号code */
	private String InterestCode;
	/** 使用益多宝赠券额账号code */
	private String rebateCode;
	/** 是否使用益多宝收益额 */
	private String isUseInterest;
	/** 是否使用益多宝赠券额 */
	private String isUseRebate;
	/** 代金券使用的商品id */
	private String skuCode;
	/** 手机或则web端的标志 1代表web 0代表 手机app */
	private String flag;
	/** 地址编号 */
	private String addressId;
	/** 是否选中使用积分y使用n不使用积分 */
	private String isPoint;
	/** 是否要发票 */
	private String isInvoice;
	/** 发票类型1个人2公司 */
	private String invoiceType;
	/** 发票抬头 */
	private String invoiceTitle;
	/** 支付方式 */
	private String paymentType;
	/** 送货时段,会员方便的收货时间段 */
	private String sendDate;
	/** 订单来源 */
	private String sourceCode;
	/** 订单备注 */
	private String memo;
	/** member_id */
	private Long memberId;
	/** 订单行号 */
	private String soCode;
	/** 订单号 */
	private String orderCode;
    /** 密码*/
	private String password;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getEpoints() {
		return epoints;
	}

	public void setEpoints(String epoints) {
		this.epoints = epoints;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getPayOffId() {
		return payOffId;
	}

	public void setPayOffId(String payOffId) {
		this.payOffId = payOffId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getIsUsePoint() {
		return isUsePoint;
	}

	public void setIsUsePoint(String isUsePoint) {
		this.isUsePoint = isUsePoint;
	}

	public String getIsUseTax() {
		return isUseTax;
	}

	public void setIsUseTax(String isUseTax) {
		this.isUseTax = isUseTax;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
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

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getIsUseCouponCard() {
		return isUseCouponCard;
	}

	public void setIsUseCouponCard(String isUseCouponCard) {
		this.isUseCouponCard = isUseCouponCard;
	}

	public String getSoCode() {
		return soCode;
	}

	public void setSoCode(String soCode) {
		this.soCode = soCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getUseAccountMoney() {
		return useAccountMoney;
	}

	public void setUseAccountMoney(String useAccountMoney) {
		this.useAccountMoney = useAccountMoney;
	}

	public String getInterestCode() {
		return InterestCode;
	}

	public void setInterestCode(String interestCode) {
		InterestCode = interestCode;
	}

	public String getRebateCode() {
		return rebateCode;
	}

	public void setRebateCode(String rebateCode) {
		this.rebateCode = rebateCode;
	}

	public String getIsUseInterest() {
		return isUseInterest;
	}

	public void setIsUseInterest(String isUseInterest) {
		this.isUseInterest = isUseInterest;
	}

	public String getIsUseRebate() {
		return isUseRebate;
	}

	public void setIsUseRebate(String isUseRebate) {
		this.isUseRebate = isUseRebate;
	}

}
