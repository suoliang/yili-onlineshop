package com.fushionbaby.statistics.model;

import java.math.BigDecimal;
import java.util.Date;
/** 
 * @author cyla
 * 
 */
public class StatisticsOrder {
	
	private Long id;
	private BigDecimal todaySalesMoney;
	private Integer todayOrderNumber;
	private Date createTime;
	private Integer buyabovetwo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public BigDecimal getTodaySalesMoney() {
		return todaySalesMoney;
	}
	public void setTodaySalesMoney(BigDecimal todaySalesMoney) {
		this.todaySalesMoney = todaySalesMoney;
	}
	public Integer getTodayOrderNumber() {
		return todayOrderNumber;
	}
	public void setTodayOrderNumber(Integer todayOrderNumber) {
		this.todayOrderNumber = todayOrderNumber;
	}
	public Integer getBuyabovetwo() {
		return buyabovetwo;
	}
	public void setBuyabovetwo(Integer buyabovetwo) {
		this.buyabovetwo = buyabovetwo;
	}
}
