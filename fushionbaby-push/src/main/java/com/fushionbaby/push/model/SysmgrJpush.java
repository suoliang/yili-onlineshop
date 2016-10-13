package com.fushionbaby.push.model;

import java.util.Date;

/***
 * 推送的dao
 * @author xupeijun
 *
 */
public class SysmgrJpush{

	/** 自增id*/
	private Long id;
	/** 推送时间*/
	private String pushTitle;
	/** 推送标题*/
	private Date pushTime;
	/** 推送内容*/
	private String pushContent;
	/** 推送的标签*/
	private String pushTag;
	/** 推送别名*/
	private String pushAlias;
	/** 推送的状态*/
	private String pushIsOk;
	/** 推送内容的url链接*/
	private String pushUrl;
	/** 推送对象*/
	private String pushDevice;
	private String messageType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPushTitle() {
		return pushTitle;
	}
	public void setPushTitle(String pushTitle) {
		this.pushTitle = pushTitle;
	}

	public Date getPushTime() {
		return pushTime;
	}
	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}
	public String getPushContent() {
		return pushContent;
	}
	public void setPushContent(String pushContent) {
		this.pushContent = pushContent;
	}
	public String getPushTag() {
		return pushTag;
	}
	public void setPushTag(String pushTag) {
		this.pushTag = pushTag;
	}
	public String getPushAlias() {
		return pushAlias;
	}
	public void setPushAlias(String pushAlias) {
		this.pushAlias = pushAlias;
	}

	public String getPushIsOk() {
		return pushIsOk;
	}
	public void setPushIsOk(String pushIsOk) {
		this.pushIsOk = pushIsOk;
	}
	public String getPushUrl() {
		return pushUrl;
	}
	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}
	public String getPushDevice() {
		return pushDevice;
	}
	public void setPushDevice(String pushDevice) {
		this.pushDevice = pushDevice;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	
	
}
