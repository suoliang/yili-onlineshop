package com.aladingshop.alabao.vo;
/**
 * @description 用户转出银行卡列表
 * @author 索亮
 * @date 2015年9月23日下午7:13:41
 */
public class AccountBankDto {
	/** 银行卡卡号 */
	private String cardNo;
	/** 持卡人姓名 */
	private String cardHolder;
	/** 银行名称 */
	private String bankName;
	/** 银行卡尾号 */
	private String bankTailNo;
	/** 银行的icon图 */
	private String bankIconUrl;
	/** 此银行的银行转出信息描述 */
	private String bankDesc;
	/** 银行支行名称*/
	private String bankBranchName;
	/** 银行关联表id--用于编辑和删除操作 */
	private Long accountBankId;
	/** 是否是最近转出的银行卡(y/n) */
	private String isLasted;
	
	/**转出的最大金额*/
	private String balance;
	
	/**银行卡所属地址 省市*/
	private  String address;
	
	

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
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
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankTailNo() {
		return bankTailNo;
	}
	public void setBankTailNo(String bankTailNo) {
		this.bankTailNo = bankTailNo;
	}
	public String getBankIconUrl() {
		return bankIconUrl;
	}
	public void setBankIconUrl(String bankIconUrl) {
		this.bankIconUrl = bankIconUrl;
	}
	public String getBankDesc() {
		return bankDesc;
	}
	public void setBankDesc(String bankDesc) {
		this.bankDesc = bankDesc;
	}
	public Long getAccountBankId() {
		return accountBankId;
	}
	public void setAccountBankId(Long accountBankId) {
		this.accountBankId = accountBankId;
	}
	public String getIsLasted() {
		return isLasted;
	}
	public void setIsLasted(String isLasted) {
		this.isLasted = isLasted;
	}

}
