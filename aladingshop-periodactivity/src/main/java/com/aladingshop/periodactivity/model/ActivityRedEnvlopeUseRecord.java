/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:29:49
 */
package com.aladingshop.periodactivity.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 活动红包使用记录
 * @author 孟少博
 * @date 2015年9月28日上午11:29:49
 */
public class ActivityRedEnvlopeUseRecord {

	
	private Long id;
	
	/** 当前用户*/
	private Long memberId;
	
	/** 订单编号*/
	private String orderCode;
	
	/** 红包使用金额*/
	private BigDecimal redEnvelopeAmount;
	
	/** 使用状态*/
	private Integer useStatus;
	
	/** 类型*/
	private Integer type;
	
	private Date createTime;
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public BigDecimal getRedEnvelopeAmount() {
		return redEnvelopeAmount;
	}

	public void setRedEnvelopeAmount(BigDecimal redEnvelopeAmount) {
		this.redEnvelopeAmount = redEnvelopeAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
