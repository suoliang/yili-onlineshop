package com.aladingshop.alabao.model;

import java.math.BigDecimal;
import java.util.Date;

public class AlabaoConfig {
	private Long id;
	/** 日利率 */
	private BigDecimal rate;
	/** 返利时间，在第二天的什么时间开始返利 */
	private Date rebateTime;
	/** 更新者id */
	private Long updateId;
	/** 更新时间 */
	private Date updateTime;
	/** 日利率唯一编码 */
	private String rateCode;

	/** 更新者用户名 */
	private String updateName;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public Date getRebateTime() {
		return rebateTime;
	}
	public void setRebateTime(Date rebateTime) {
		this.rebateTime = rebateTime;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getRateCode() {
		return rateCode;
	}
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

}
