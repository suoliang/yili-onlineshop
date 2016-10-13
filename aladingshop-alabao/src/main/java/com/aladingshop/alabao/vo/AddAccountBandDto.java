package com.aladingshop.alabao.vo;
/**
 * @description 用户添加到账户和银行的关联DTO
 * @author 索亮
 * @date 2015年9月24日上午8:52:39
 */
public class AddAccountBandDto {
	/**如意包用户登录标识*/
	private String alabaoSid;
	/** 银行卡卡号 */
	private String cardNo;
	/** 持卡人姓名 */
	private String cardHolder;
	/** 银行名称 */
	private String bankName;
	/** 银行支行名称*/
    private String bankBranchName;
    /** 用户银行卡信息id--修改 */
    private Long accountBankId;
    /** 是否修改过银行卡卡号(y/n)y修改过,n未修改--修改 */
    private String isEditCardNo;
    
    
    
    
    /**银行卡所在省份*/
    private String province;
    /** 银行卡所在城市*/
    private String city;
    
    
    

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
	public Long getAccountBankId() {
		return accountBankId;
	}
	public void setAccountBankId(Long accountBankId) {
		this.accountBankId = accountBankId;
	}
	public String getIsEditCardNo() {
		return isEditCardNo;
	}
	public void setIsEditCardNo(String isEditCardNo) {
		this.isEditCardNo = isEditCardNo;
	}

}
