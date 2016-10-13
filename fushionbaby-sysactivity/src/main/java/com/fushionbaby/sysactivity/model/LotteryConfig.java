package com.fushionbaby.sysactivity.model;

import java.util.Date;
/**
 * @author  cyla
 * 
 */
public class LotteryConfig {

	private Long id;

	private String lotteryCode;

	private Date startTime;

	private Date endTime;

	private String disable;

	private Integer orderStint;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public Integer getOrderStint() {
		return orderStint;
	}

	public void setOrderStint(Integer orderStint) {
		this.orderStint = orderStint;
	}
}
