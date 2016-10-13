package com.aladingshop.card.model;

import java.math.BigDecimal;
import java.util.Date;

public class MemberCardIncomeRecord {
	public MemberCardIncomeRecord() {
	}

	public MemberCardIncomeRecord(Long inMid, String inCano, String inAcount, BigDecimal inMoney) {
		this.incomeMemberId = inMid;
		this.incomeCardCode = inCano;
		this.incomeAcount = inAcount;
		this.incomeMoney = inMoney;
	}

	private Long id;

	private Long incomeMemberId;

	private String incomeCardCode;

	private String incomeAcount;
	/** 收入类型*/
	private String incomeType;

	private BigDecimal incomeMoney;

	private Date createTime;
	
	/**交易流水号*/
	private  String serialNum;

	/* 页面显示当前收益用户的名称 */
	private String loginName;

	
	
	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIncomeMemberId() {
		return incomeMemberId;
	}

	public void setIncomeMemberId(Long incomeMemberId) {
		this.incomeMemberId = incomeMemberId;
	}

	public String getIncomeCardCode() {
		return incomeCardCode;
	}

	public void setIncomeCardCode(String incomeCardCode) {
		this.incomeCardCode = incomeCardCode;
	}

	public String getIncomeAcount() {
		return incomeAcount;
	}

	public void setIncomeAcount(String incomeAcount) {
		this.incomeAcount = incomeAcount;
	}

	public BigDecimal getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

}