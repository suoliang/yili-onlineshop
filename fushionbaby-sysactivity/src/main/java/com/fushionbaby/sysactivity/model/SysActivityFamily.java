package com.fushionbaby.sysactivity.model;

import java.util.Date;

/**
 * 
 * @author xupeijun
 * 
 */
public class SysActivityFamily {
	private Long id;

	private String title;
	private Date publishTime;
	private Long clickNum;
	private String familyUrl;
	private String content;

	private String webBannerUrl;
	private String appBannerUrl;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Long getClickNum() {
		return clickNum;
	}

	public void setClickNum(Long clickNum) {
		this.clickNum = clickNum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getFamilyUrl() {
		return familyUrl;
	}

	public void setFamilyUrl(String familyUrl) {
		this.familyUrl = familyUrl;
	}


	public String getWebBannerUrl() {
		return webBannerUrl;
	}

	public void setWebBannerUrl(String webBannerUrl) {
		this.webBannerUrl = webBannerUrl;
	}

	public String getAppBannerUrl() {
		return appBannerUrl;
	}

	public void setAppBannerUrl(String appBannerUrl) {
		this.appBannerUrl = appBannerUrl;
	}

}