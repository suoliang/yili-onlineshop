/**
 * 
 */
package com.aladingshop.card.dto.req;

import com.aladingshop.card.dto.BaseReqDto;

/**
 * @description 益多宝退卡请求Dto
 * @author 孙涛
 * @date 2015年10月12日上午10:47:31
 */
public class YiDuoBaoCardBackRepDto extends BaseReqDto {
	/** 用户银行卡所在行中文名称 */
	private String bankName;
	/** 用户银行卡卡号 */
	private String bankCardNo;
	/** 持卡人姓名 */
	private String cardHolder;
	 /** 银行的支行名*/
    private String bankBranchName;

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

}
