package com.fushionbaby.config.model;

/**
 * 
 * @author xupeijun
 * 
 */
public class SysmgrAdvertisementConfig {
	private Long id;

	private String adCode;

	private String sourceCode;

	/*
	 * private Integer pictureHeight;
	 * 
	 * private Integer pictureWeight;
	 * 
	 * private Integer pictureNumber;
	 */

	private String isUse;

	private String remark;

	private String adName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdCode() {
		return adCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}


	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

}