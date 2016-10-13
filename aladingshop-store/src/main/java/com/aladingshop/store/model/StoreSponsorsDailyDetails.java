package com.aladingshop.store.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户结算列表,每日结算统计
 * @author chenyingtao
 *
 *
 */
public class StoreSponsorsDailyDetails {
	
	private Long id;
	private String storeCode;
	/**结算周期,跑批处理,定时每天按照支付成功时间计算*/
	private Date settlePeriod;
	/**结算周期的订单总金额*/
	private BigDecimal settleOrderAmount;
	/**结算周期支付平台总金额,服务费*/
	private BigDecimal platformFee;
	/**商户结算周期总的实收金额(订单金额-支付平台费)*/
	private BigDecimal realIncomeAmount;
	/**结算周期的订单数量*/
	private Integer settleOrderCount;
	
	private Date createTime;
	private Date updateTime;
	private Long updateId;
	/**每日结算序列,和结算明细表对应*/
	private String dailyNumber;
	/**申请编号,申请编码,和结算表对应*/
	private String applyNumber;
	/**结算状态1.待结算,未结算;2.结算中,申请中;3.已结算,结算完成*/
	private String settlementStatus;
	private String remark;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public Date getSettlePeriod() {
		return settlePeriod;
	}
	public void setSettlePeriod(Date settlePeriod) {
		this.settlePeriod = settlePeriod;
	}
	public BigDecimal getSettleOrderAmount() {
		return settleOrderAmount;
	}
	public void setSettleOrderAmount(BigDecimal settleOrderAmount) {
		this.settleOrderAmount = settleOrderAmount;
	}
	public BigDecimal getPlatformFee() {
		return platformFee;
	}
	public void setPlatformFee(BigDecimal platformFee) {
		this.platformFee = platformFee;
	}
	public BigDecimal getRealIncomeAmount() {
		return realIncomeAmount;
	}
	public void setRealIncomeAmount(BigDecimal realIncomeAmount) {
		this.realIncomeAmount = realIncomeAmount;
	}
	public Integer getSettleOrderCount() {
		return settleOrderCount;
	}
	public void setSettleOrderCount(Integer settleOrderCount) {
		this.settleOrderCount = settleOrderCount;
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
	public String getDailyNumber() {
		return dailyNumber;
	}
	public void setDailyNumber(String dailyNumber) {
		this.dailyNumber = dailyNumber;
	}
	public String getApplyNumber() {
		return applyNumber;
	}
	public void setApplyNumber(String applyNumber) {
		this.applyNumber = applyNumber;
	}
	public String getSettlementStatus() {
		return settlementStatus;
	}
	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
}
