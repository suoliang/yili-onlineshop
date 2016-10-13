/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月19日上午11:40:34
 */
package com.fushionbaby.cms.dto.acticity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月19日上午11:40:34
 */
public class ActivityShareRegisterDto {

	/**
	 * 分享者用户
	 */
	private Long memberShareId;
	
	/**
	 * 分享者用户名
	 */
	private String memberShareName;
	
	/**
	 * 注册者编号
	 */
	private Long memberRegisterId;
	
	/**
	 * 注册者用户名
	 */
	private String memberRegisterName;
	
	
	/***/
	private String memberRegisterPwd;
	
	/**
	 * 邀请码
	 */
	private String inviteCode;
	
	/**
	 * 获得金额
	 */
	private BigDecimal gainRedEnvelope;
	
	
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/** 创建开始时间*/
	private String createStartTime;
	
	/** 创建结束时间*/
	private String createEndTime;
	
	

	public String getMemberRegisterPwd() {
		return memberRegisterPwd;
	}

	public void setMemberRegisterPwd(String memberRegisterPwd) {
		this.memberRegisterPwd = memberRegisterPwd;
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

	public Long getMemberShareId() {
		return memberShareId;
	}

	public void setMemberShareId(Long memberShareId) {
		this.memberShareId = memberShareId;
	}

	public String getMemberShareName() {
		return memberShareName;
	}

	public void setMemberShareName(String memberShareName) {
		this.memberShareName = memberShareName;
	}

	public Long getMemberRegisterId() {
		return memberRegisterId;
	}

	public void setMemberRegisterId(Long memberRegisterId) {
		this.memberRegisterId = memberRegisterId;
	}

	public String getMemberRegisterName() {
		return memberRegisterName;
	}

	public void setMemberRegisterName(String memberRegisterName) {
		this.memberRegisterName = memberRegisterName;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
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
