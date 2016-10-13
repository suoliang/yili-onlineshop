package com.fushionbaby.cms.dto;
/**
 * 
 * @author 孟少博
 *
 */
public class SkuTogetherDto {
	/** 商品团购序号*/
	private Long id;
	/** 商品编号*/
	private String skuCode;
	/** 商品名称*/
	private String skuName;
	/** 省*/
	private String province;
	/** 市*/
	private String city;
	/** 区*/
	private String district;
	/** 团购开始时间*/
	private String beginTime;
	/** 团购开始时间*/
	private String beginTimeFrom;
	/** 团购开始时间*/
	private String beginTimeTo;
	/** 团购结束时间*/
	private String endTime;
	/** 团购结束时间*/
	private String endTimeFrom;
	/** 团购结束时间*/
	private String endTimeTo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getBeginTimeFrom() {
		return beginTimeFrom;
	}
	public void setBeginTimeFrom(String beginTimeFrom) {
		this.beginTimeFrom = beginTimeFrom;
	}
	public String getBeginTimeTo() {
		return beginTimeTo;
	}
	public void setBeginTimeTo(String beginTimeTo) {
		this.beginTimeTo = beginTimeTo;
	}
	public String getEndTimeFrom() {
		return endTimeFrom;
	}
	public void setEndTimeFrom(String endTimeFrom) {
		this.endTimeFrom = endTimeFrom;
	}
	public String getEndTimeTo() {
		return endTimeTo;
	}
	public void setEndTimeTo(String endTimeTo) {
		this.endTimeTo = endTimeTo;
	}
	
	

}
