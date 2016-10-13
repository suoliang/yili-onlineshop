package com.fushionbaby.sysactivity.model;

import java.util.Date;

/**
 * 
 * @author King
 * 
 */
public class SysActivityActivitiesApply {
	private Long id;

	private Long memberId;

	private Long activitiesId;

	private String activitiesName;

	private Date applyTime;

	private Integer applyNumber;

	private String memberName;

	private String memberPhone;

	private String isTouch;

	private String sourceCode;

	private String isOk;

	/** 来源名称 ,页面显示 */
	private String sourceName;

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

	public Long getActivitiesId() {
		return activitiesId;
	}

	public void setActivitiesId(Long activitiesId) {
		this.activitiesId = activitiesId;
	}

	public String getActivitiesName() {
		return activitiesName;
	}

	public void setActivitiesName(String activitiesName) {
		this.activitiesName = activitiesName == null ? null : activitiesName
				.trim();
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getApplyNumber() {
		return applyNumber;
	}

	public void setApplyNumber(Integer applyNumber) {
		this.applyNumber = applyNumber;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName == null ? null : memberName.trim();
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}



	public String getIsTouch() {
		return isTouch;
	}

	public void setIsTouch(String isTouch) {
		this.isTouch = isTouch;
	}

	public String getIsOk() {
		return isOk;
	}

	public void setIsOk(String isOk) {
		this.isOk = isOk;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

}