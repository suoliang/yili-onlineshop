package com.fushionbaby.wap.dto.sysactivity;


/***
 * web端户外活动，查询条件封装类
 * @author xupeijun
 *
 */
public class OutDoorActivityDtoWeb {
	/** 活动的id*/
	private long activitiesId;
	/** 活动名称*/
	private String activitiesName;
	/** 申请人电话*/
	private String phone;
	/** 申请的人数*/
	private Integer number;
	/** 登陆用户*/
	private String loginName;
	public long getActivitiesId() {
		return activitiesId;
	}
	public void setActivitiesId(long activitiesId) {
		this.activitiesId = activitiesId;
	}
	public String getActivitiesName() {
		return activitiesName;
	}
	public void setActivitiesName(String activitiesName) {
		this.activitiesName = activitiesName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}
