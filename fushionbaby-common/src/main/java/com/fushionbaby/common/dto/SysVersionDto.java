package com.fushionbaby.common.dto;

public class SysVersionDto {

	// 版本名称
	private String name;
	// 版本号
	private Integer version;
	// 新特性
	private String content;
	// 发布时间
	private String updateTime;
	// 发布地址
	private String updateUrl;
	// 是否强制更新
	private Integer isForce;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUrl() {
		return updateUrl;
	}

	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}

	public Integer getIsForce() {
		return isForce;
	}

	public void setIsForce(Integer isForce) {
		this.isForce = isForce;
	}

}
