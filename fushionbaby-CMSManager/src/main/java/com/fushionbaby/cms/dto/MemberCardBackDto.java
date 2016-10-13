package com.fushionbaby.cms.dto;


/***
 * 会员储蓄卡
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年9月8日上午11:03:50
 */
public class MemberCardBackDto extends YiDuoBaoOrderDto{
	
	/** 持卡人名字*/
	private String bankCardHolder;
	/** 银行卡卡号*/
	private String bankCardNo;
	/** 卡号（阿拉丁卡）*/
	private String cardNo;
	/**状态*/
	private String backStatus;
	/** 银行名称*/
	private String bankName;
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBackStatus() {
		return backStatus;
	}

	public void setBackStatus(String backStatus) {
		this.backStatus = backStatus;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankCardHolder() {
		return bankCardHolder;
	}

	public void setBankCardHolder(String bankCardHolder) {
		this.bankCardHolder = bankCardHolder;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	
}
