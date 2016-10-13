package com.fushionbaby.cms.dto;

public class SkuPromotionsInfoDto {
	private Long pmid;
	/** 活动名 */
	private String promotionsName;
	/** 活动编码 */
	private String promotionsCode;
	
	public Long getPmid() {
		return pmid;
	}
	public void setPmid(Long pmid) {
		this.pmid = pmid;
	}
	public String getPromotionsName() {
		return promotionsName;
	}
	public void setPromotionsName(String promotionsName) {
		this.promotionsName = promotionsName;
	}
	public String getPromotionsCode() {
		return promotionsCode;
	}
	public void setPromotionsCode(String promotionsCode) {
		this.promotionsCode = promotionsCode;
	}
	
	
}
