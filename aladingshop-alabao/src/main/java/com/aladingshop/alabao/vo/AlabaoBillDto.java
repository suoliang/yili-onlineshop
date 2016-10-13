package com.aladingshop.alabao.vo;

/**
 * @description 账单记录--(转入转出消费收益)通用DTO
 * @author 索亮
 * @date 2015年11月26日上午11:32:35
 */
public class AlabaoBillDto {
	/** 交易金额(根据需要前加"+,-") */
	private String tradeMoney;
	/** 交易时间 */
	private String createTime;
	/** 交易类型-转出,转入,收益,消费 */
	private String tradeType;
	/** 交易状态(转入成功,转出成功等) */
	private String tradeStatus;
	/** 账户-转出到银行卡时需要特别处理 */
	private String account;
	/** 转出到银行卡，如意消费卡 */
	private String turnOutType;
	/** 账单详情里图片 */
	private String detailImage;
	/** (转出到银行卡失败)原因 */
	private String reason;
	/** 描述 */
	private String remark;
	/** 完整时间,用于排序时使用 */
	private String fullTime;
	/**余额*/
	private String balance;
	
	
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getTradeMoney() {
		return tradeMoney;
	}
	public void setTradeMoney(String tradeMoney) {
		this.tradeMoney = tradeMoney;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getTurnOutType() {
		return turnOutType;
	}
	public void setTurnOutType(String turnOutType) {
		this.turnOutType = turnOutType;
	}
	public String getDetailImage() {
		return detailImage;
	}
	public void setDetailImage(String detailImage) {
		this.detailImage = detailImage;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFullTime() {
		return fullTime;
	}
	public void setFullTime(String fullTime) {
		this.fullTime = fullTime;
	}
	
}
