/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月16日下午4:02:28
 */
package com.fushionbaby.cms.dto.acticity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月16日下午4:02:28
 */
public class ActivityShareMemberDto {
	
	/** 用户Id*/
	private Long memberId;
	
	/** 用户名称*/
	private String memberName;
	
	/** 邀请码*/
	private String inviteCode;
	
	/** 累计赚取红包金额*/
	private BigDecimal grandGainRedEnvelope;
	
	/** 可用红包金额*/
	private BigDecimal existingRedEnvelope;
	
	/** 创建时间*/
	private Date createTime;
	
	/** 修改时间*/
	private Date updateTime;

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
