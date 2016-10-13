package com.fushionbaby.sysactivity.model;

import java.util.Date;

/**
 * 
 * @author xupeijun
 * 
 */
public class SysActivityActivities {
	private Long id;

	private String name;

	private Date time;

	private String place;

	private Integer number;

	private Date limitApplyTime;

	private String isShow;


	private String placePictureUrl;

	private String introduce;
	
	private String webBannerUrl;
	
	private String appBannerUrl;
	
	private String appIntroduceUrl;
	
	private String webIntroduceUrl;
	
	private String introduceUrl;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getLimitApplyTime() {
		return limitApplyTime;
	}

	public void setLimitApplyTime(Date limitApplyTime) {
		this.limitApplyTime = limitApplyTime;
	}


	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getPlacePictureUrl() {
		return placePictureUrl;
	}

	public void setPlacePictureUrl(String placePictureUrl) {
		this.placePictureUrl = placePictureUrl;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
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

	public String getAppIntroduceUrl() {
		return appIntroduceUrl;
	}

	public void setAppIntroduceUrl(String appIntroduceUrl) {
		this.appIntroduceUrl = appIntroduceUrl;
	}

	public String getWebIntroduceUrl() {
		return webIntroduceUrl;
	}

	public void setWebIntroduceUrl(String webIntroduceUrl) {
		this.webIntroduceUrl = webIntroduceUrl;
	}

	public String getIntroduceUrl() {
		return introduceUrl;
	}

	public void setIntroduceUrl(String introduceUrl) {
		this.introduceUrl = introduceUrl;
	}

}