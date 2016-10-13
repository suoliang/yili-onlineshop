package com.fushionbaby.statistics.model;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @author King
 *
 */
public class StatisticsSms {
    private Long id;

    private BigDecimal leftAmount;

    private Date createTime;
    
    private Long smsNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(BigDecimal leftAmount) {
        this.leftAmount = leftAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Long getSmsNumber() {
		return smsNumber;
	}

	public void setSmsNumber(Long smsNumber) {
		this.smsNumber = smsNumber;
	}
}