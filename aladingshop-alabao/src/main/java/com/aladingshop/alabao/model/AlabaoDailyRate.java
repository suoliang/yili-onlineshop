package com.aladingshop.alabao.model;

import java.math.BigDecimal;
import java.util.Date;

public class AlabaoDailyRate {
	private Long id;
	/** 每日利率值*/
	private BigDecimal rate;
	/** 时间，代表的是在哪一天的记录,格式:2015-09-08*/
	private Date time;
	/** 本条信息的更新时间*/
	private Date updateTime;
	/** 本条信息的更新者*/
	private Long updateId;
	
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
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
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
	
	

}
