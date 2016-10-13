package com.fushionbaby.cms.dto;

import java.math.BigDecimal;
import java.util.Date;

public class StoreSponsorsApplySettleDto {
	
	
	private Long id;
	/**门店code*/
	private String storeCode;
	/**申请编号,申请编码,和结算统计表对应*/
	private String applyNumber;
	
	private Date applyTime;
	/**申请的总金额*/
	private BigDecimal applyTotalAmount;
	/**结算方式-银行卡*/
	private String settleMethod;
	
	/**结算状态1.待结算,未结算;2.结算中,申请中;3.已结算,结算完成*/
	private String settlementStatus;
	private Date createTime;
	private Date updateTime;
	/**门店后台操作人员ID*/
	private Long updateId;
	/**结算完成时间,结算完成更新此字段*/
	private Date settlementTime;
	/** 创建开始时间*/
	private String createTimeFrom;
	/** 创建结束时间*/
	private String createTimeTo;
	/**门店名称*/
	private String storeName;
	/**门店编号*/
	private String storeNumber;
	
	/**银行名称*/
	private String bankName;
	/**支行名称*/
	private String bankBranchName;
	/**卡号*/
	private String cardNo;
	/**持卡人*/
	private String cardHolder; 

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getApplyNumber() {
		return applyNumber;
	}

	public void setApplyNumber(String applyNumber) {
		this.applyNumber = applyNumber;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public BigDecimal getApplyTotalAmount() {
		return applyTotalAmount;
	}

	public void setApplyTotalAmount(BigDecimal applyTotalAmount) {
		this.applyTotalAmount = applyTotalAmount;
	}

	public String getSettleMethod() {
		return settleMethod;
	}

	public void setSettleMethod(String settleMethod) {
		this.settleMethod = settleMethod;
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
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

	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	
	
}
