/**
 * 
 */
package com.aladingshop.card.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 退卡明细Model
 * @author 孙涛
 * @date 2015年10月10日下午1:33:16
 */
public class MemberCardBack {
	/** ID */
	private Long id;
	/**退卡的会员ID */
	private Long memberId;
	/** 退卡的益多宝卡号 */
	private String cardNo;
	/** 退卡绑定的如意宝账号 */
	private String acount;
	/** 退卡金额 */
	private BigDecimal money;
	/** 退卡到银行的账号 */
	private String bankCardNo;
	/** 退卡到银行账号的持卡人姓名 */
	private String bankCardHolder;
	/** 退卡状态 (1待审核2审核通过3审核不通过4完成5已转入如意宝) */
	private String backStatus;
	/**退卡时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** 更新操作人员ID */
	private Long updateId;
	/** 银行名称*/
	private String bankName;
	/** 银行支行信息名称*/
	private String bankBranchName;
	
	private String cardType;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAcount() {
		return acount;
	}

	public void setAcount(String acount) {
		this.acount = acount;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBackStatus() {
		return backStatus;
	}

	public void setBackStatus(String backStatus) {
		this.backStatus = backStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public String getBankCardHolder() {
		return bankCardHolder;
	}

	public void setBankCardHolder(String bankCardHolder) {
		this.bankCardHolder = bankCardHolder;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	

	
}
