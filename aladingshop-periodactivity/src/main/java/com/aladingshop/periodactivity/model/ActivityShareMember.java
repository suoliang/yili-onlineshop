/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:32:10
 */
package com.aladingshop.periodactivity.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 活动分享送红包用户
 * @author 孟少博
 * @date 2015年9月28日上午11:32:10
 */
public class ActivityShareMember {

	private Long id;
	
	/** 用户ID*/
	private Long memberId;
	
	/** 邀请码*/
	private String inviteCode;
	
	/** 累计获得红包数量*/
	private BigDecimal grandGainRedEnvelope;
	
	/** 可用红包数量*/
	private BigDecimal existingRedEnvelope;
	
	private Date createTime;
	
	private Date updateTime;

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

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public BigDecimal getGrandGainRedEnvelope() {
		return grandGainRedEnvelope;
	}

	public void setGrandGainRedEnvelope(BigDecimal grandGainRedEnvelope) {
		this.grandGainRedEnvelope = grandGainRedEnvelope;
	}

	public BigDecimal getExistingRedEnvelope() {
		return existingRedEnvelope;
	}

	public void setExistingRedEnvelope(BigDecimal existingRedEnvelope) {
		this.existingRedEnvelope = existingRedEnvelope;
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
	
	
}
