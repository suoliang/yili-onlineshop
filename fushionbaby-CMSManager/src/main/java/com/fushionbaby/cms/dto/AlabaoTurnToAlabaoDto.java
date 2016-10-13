package com.fushionbaby.cms.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AlabaoTurnToAlabaoDto {
	
	/**转出账户名*/
	private String account;
	/**转入账户名*/	
	private String otherAccount;
	/**转移金额*/	
	private BigDecimal transferMoney;
	/**转移状态y成功n失败*/	
	private String turnOutStatus;
	/**创建时间*/	
	private Date createTime;
	/**流水号,如意转入和转出,turn_to_alabao有相同标识*/
	private String serialNum;
	
	private String memo;
	
	/**创建开始时间*/
	private String createTimeFrom;
	/**创建结束时间*/
	private String createTimeTo;
	
	/**转出人真实姓名*/
	private String trueName;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOtherAccount() {
		return otherAccount;
	}

	public void setOtherAccount(String otherAccount) {
		this.otherAccount = otherAccount;
	}

	public BigDecimal getTransferMoney() {
		return transferMoney;
	}

	public void setTransferMoney(BigDecimal transferMoney) {
		this.transferMoney = transferMoney;
	}

	public String getTurnOutStatus() {
		return turnOutStatus;
	}

	public void setTurnOutStatus(String turnOutStatus) {
		this.turnOutStatus = turnOutStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getCreateTimeFrom() {
		return createTimeFrom;
	}

	public void setCreateTimeFrom(String createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}

	public String getCreateTimeTo() {
		return createTimeTo;
	}

	public void setCreateTimeTo(String createTimeTo) {
		this.createTimeTo = createTimeTo;
	}


	
	
}
