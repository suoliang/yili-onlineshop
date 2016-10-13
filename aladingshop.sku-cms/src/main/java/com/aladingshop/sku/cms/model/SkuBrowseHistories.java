package com.aladingshop.sku.cms.model;

import java.util.Date;

public class SkuBrowseHistories {
	private Long id;
	private Long memberId;
	private String skuCode;
	private Integer browseCounts;
	private Date browseTime;
	
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
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public Integer getBrowseCounts() {
		return browseCounts;
	}
	public void setBrowseCounts(Integer browseCounts) {
		this.browseCounts = browseCounts;
	}
	public Date getBrowseTime() {
		return browseTime;
	}
	public void setBrowseTime(Date browseTime) {
		this.browseTime = browseTime;
	}
	
	
}
