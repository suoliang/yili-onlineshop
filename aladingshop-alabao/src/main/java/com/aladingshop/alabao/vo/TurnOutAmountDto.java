package com.aladingshop.alabao.vo;
/**
 * @description 转出记录
 * @author 索亮
 * @date 2015年9月23日下午5:44:16
 */
public class TurnOutAmountDto {
	/** 如意包用户登录标识 */
	private String alabaoSid;
	/** 来源 */
	private String sourceCode;
	/** 转出金额 */
	private String transferMoney;
	/** 银行卡名称 */
	private String bankName;
	/** 银行卡号 */
	private String cardNo;
	/** 持卡人姓名 */
	private String cardHolder;
	/** 付款密码 */
	private String payPassword;
	/** 银行支行名称*/
    private String bankBranchName;
    /** 请求短信标识码 */
    private String bankSmsCode;
    /** 短信验证码(用户输入) */
    private String smsCode;
    /** 用户银行关联id */
    private Long accountBankId;
    /** 备注 */
    private String memo;

	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public String getAlabaoSid() {
		return alabaoSid;
	}
	public void setAlabaoSid(String alabaoSid) {
		this.alabaoSid = alabaoSid;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getTransferMoney() {
		return transferMoney;
	}
	public void setTransferMoney(String transferMoney) {
		this.transferMoney = transferMoney;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardHolder() {
		return cardHolder;
	}
	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public String getBankSmsCode() {
		return bankSmsCode;
	}
	public void setBankSmsCode(String bankSmsCode) {
		this.bankSmsCode = bankSmsCode;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public Long getAccountBankId() {
		return accountBankId;
	}
	public void setAccountBankId(Long accountBankId) {
		this.accountBankId = accountBankId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
