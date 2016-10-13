package com.fushionbaby.statistics.model;

import java.util.Date;
/**
 * 
 * @author cyla
 *
 */
public class StatisticsMember {

	private Long id;
	private Long todayMemberNumber;
	private Long totalNumber;
	private String androidCode;
	private String iosCode;
	private String webCode;
	private String cmsCode;
	private String defaultChannel;
	private String qqChannel;
	private String wxChannel;
	private String sinaChannel;
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAndroidCode() {
		return androidCode;
	}
	public void setAndroidCode(String androidCode) {
		this.androidCode = androidCode;
	}
	public String getIosCode() {
		return iosCode;
	}
	public void setIosCode(String iosCode) {
		this.iosCode = iosCode;
	}
	public String getWebCode() {
		return webCode;
	}
	public void setWebCode(String webCode) {
		this.webCode = webCode;
	}
	public String getCmsCode() {
		return cmsCode;
	}
	public void setCmsCode(String cmsCode) {
		this.cmsCode = cmsCode;
	}
	public String getDefaultChannel() {
		return defaultChannel;
	}
	public void setDefaultChannel(String defaultChannel) {
		this.defaultChannel = defaultChannel;
	}
	public String getQqChannel() {
		return qqChannel;
	}
	public void setQqChannel(String qqChannel) {
		this.qqChannel = qqChannel;
	}
	public String getWxChannel() {
		return wxChannel;
	}
	public void setWxChannel(String wxChannel) {
		this.wxChannel = wxChannel;
	}
	public String getSinaChannel() {
		return sinaChannel;
	}
	public void setSinaChannel(String sinaChannel) {
		this.sinaChannel = sinaChannel;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getTodayMemberNumber() {
		return todayMemberNumber;
	}
	public void setTodayMemberNumber(Long todayMemberNumber) {
		this.todayMemberNumber = todayMemberNumber;
	}
	public Long getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(Long totalNumber) {
		this.totalNumber = totalNumber;
	}
	
}
