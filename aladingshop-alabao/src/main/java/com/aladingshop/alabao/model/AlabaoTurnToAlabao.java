package com.aladingshop.alabao.model;

import java.math.BigDecimal;
import java.util.Date;

/***
 * @description 如意宝转出到如意宝model
 * @author 索亮
 * @date 2015年10月28日下午4:20:03
 */
public class AlabaoTurnToAlabao {
	/**自增id*/
	private Long id;
	/**会员绑定如意宝账户id*/	
	private Long memberId;
	/**账户名*/	
	private String account;
	/**其他用户账户名*/	
	private String otherAccount;
	/**转移金额*/	
	private BigDecimal transferMoney;
	/**转移状态y成功n失败*/	
	private String turnOutStatus;
	/**创建时间*/	
	private Date createTime;
	/**流水号,如意转入和转出,turn_to_alabao有相同标识*/
	private String serialNum;
	/** 批处理号*/
	private String batchNum;
	
	private String memo;
	
	
	

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
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
	
}
