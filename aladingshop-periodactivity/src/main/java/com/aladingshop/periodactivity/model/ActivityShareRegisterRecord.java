/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:35:29
 */
package com.aladingshop.periodactivity.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 活动注册用户记录
 * @author 孟少博
 * @date 2015年9月28日上午11:35:29
 */
public class ActivityShareRegisterRecord {

	private Long id;
	
	/** 分享者ID*/
	private Long memberSharesId;
	
	/** 注册者Id*/
	private Long memberRegisterId;
	
	/** 获得红包数量*/
	private BigDecimal gainRedEnvelope;
	
	/** signId地址*/
	private String signId;
	
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	public Long getMemberSharesId() {
		return memberSharesId;
	}

	public void setMemberSharesId(Long memberSharesId) {
		this.memberSharesId = memberSharesId;
	}

	public Long getMemberRegisterId() {
		return memberRegisterId;
	}

	public void setMemberRegisterId(Long memberRegisterId) {
		this.memberRegisterId = memberRegisterId;
	}

	public BigDecimal getGainRedEnvelope() {
		return gainRedEnvelope;
	}

	public void setGainRedEnvelope(BigDecimal gainRedEnvelope) {
		this.gainRedEnvelope = gainRedEnvelope;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
