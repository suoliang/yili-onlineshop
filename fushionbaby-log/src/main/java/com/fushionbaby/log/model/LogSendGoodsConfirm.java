/**
 * 
 */
package com.fushionbaby.log.model;

/**
 * @author mengshaobo 确认发货日志表
 */
public class LogSendGoodsConfirm {
	private Long id;

	/** 商户网站唯一订单号 */
	private String outOrderNo;
	/** 支付宝交易号 */
	private String tradeNo;
	/** 买家登录支付宝账号 */
	private String buyerLoginEmail;
	/** 卖家支付宝账号对应的支付宝唯一用户号 */
	private String sellerUserId;
	/** 卖家登录支付宝账号 */
	private String sellerLoginEmail;
	/** 卖家支付宝账号 */
	private String sellerAccount;
	/** 卖家类型 */
	private String sellerType;
	/** 卖家的后续动作列表 */
	private String sellerAction;
	/** 买家支付宝账号对应的支付宝唯一用户号 */
	private String buyerUserId;
	/** 买家支付宝账号 */
	private String buyerAccount;
	/** 买家类型 */
	private String buyerType;
	/** 买家的后续动作列表 */
	private String buyerAction;
	/** 交易总金额 */
	private String totalFee;
	/** 交易状态 */
	private String tradeStatus;
	/** 交易类型 */
	private String tradeType;
	/** 交易角色 */
	private String operatorRole;
	/** 合作者身份ID */
	private String partnerId;
	/** 交易创建时间 */
	private String createTime;
	/** 渠道信息 */
	private String channel;
	/** 货币代码 */
	private String currency;
	/** 收款类型 */
	private String gatheringType;
	/** 交易最后时间 */
	private String lastModifiedTime;
	/** 服务费 */
	private String serviceFee;
	/** 服务费率 */
	private String serviceFeeRatio;
	/** 是否停止超时机制 */
	private String stopTimeout;
	/** 交易来源 */
	private String tradeFrom;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOutOrderNo() {
		return outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getBuyerLoginEmail() {
		return buyerLoginEmail;
	}

	public void setBuyerLoginEmail(String buyerLoginEmail) {
		this.buyerLoginEmail = buyerLoginEmail;
	}

	public String getSellerUserId() {
		return sellerUserId;
	}

	public void setSellerUserId(String sellerUserId) {
		this.sellerUserId = sellerUserId;
	}

	public String getSellerLoginEmail() {
		return sellerLoginEmail;
	}

	public void setSellerLoginEmail(String sellerLoginEmail) {
		this.sellerLoginEmail = sellerLoginEmail;
	}

	public String getSellerAccount() {
		return sellerAccount;
	}

	public void setSellerAccount(String sellerAccount) {
		this.sellerAccount = sellerAccount;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getSellerAction() {
		return sellerAction;
	}

	public void setSellerAction(String sellerAction) {
		this.sellerAction = sellerAction;
	}

	public String getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	public String getBuyerAccount() {
		return buyerAccount;
	}

	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}

	public String getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(String buyerType) {
		this.buyerType = buyerType;
	}

	public String getBuyerAction() {
		return buyerAction;
	}

	public void setBuyerAction(String buyerAction) {
		this.buyerAction = buyerAction;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getOperatorRole() {
		return operatorRole;
	}

	public void setOperatorRole(String operatorRole) {
		this.operatorRole = operatorRole;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getGatheringType() {
		return gatheringType;
	}

	public void setGatheringType(String gatheringType) {
		this.gatheringType = gatheringType;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getServiceFeeRatio() {
		return serviceFeeRatio;
	}

	public void setServiceFeeRatio(String serviceFeeRatio) {
		this.serviceFeeRatio = serviceFeeRatio;
	}

	public String getStopTimeout() {
		return stopTimeout;
	}

	public void setStopTimeout(String stopTimeout) {
		this.stopTimeout = stopTimeout;
	}

	public String getTradeFrom() {
		return tradeFrom;
	}

	public void setTradeFrom(String tradeFrom) {
		this.tradeFrom = tradeFrom;
	}

}
