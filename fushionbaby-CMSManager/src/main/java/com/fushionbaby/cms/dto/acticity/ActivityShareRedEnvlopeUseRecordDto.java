/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月19日下午3:19:16
 */
package com.fushionbaby.cms.dto.acticity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月19日下午3:19:16
 */
public class ActivityShareRedEnvlopeUseRecordDto {

	/**
	 * 用户号
	 */
	private Long memberId;
	
	/**
	 * 用户名称
	 */
	private String memberName;
	
	/**
	 * 订单号
	 */
	private String orderCode;
	
	/**
	 * 红包金额
	 */
	private BigDecimal redEnvelopeAmount;
	
	/** 类型*/
	private String type;
	
	/** 创建时间*/
	private Date createTime;
	
	/** 创建开始时间*/
	private String createStartTime;
	
	/** 创建结束时间*/
	private String createEndTime;
	
	
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}

	public String getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}

	/**
	 * 使用状态
	 */
	private String useStatus;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}
	
	
	
}
