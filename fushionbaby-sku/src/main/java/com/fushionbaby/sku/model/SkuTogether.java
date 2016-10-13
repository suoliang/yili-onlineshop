/**
 * 
 */
package com.fushionbaby.sku.model;

import java.util.Date;

/**
 * @author mengshaobo 商品基本信息
 */
public class SkuTogether {

	/** id */
	private Long id;
	/** 商品编码 */
	private String skuCode;
	/** 商品名称 */
	private String skuName;
	/** 省*/
	private String province;
	/** 市 */
	private String city;
	/** 区 */
	private String district;
	/** 团开始时间 */
	private Date beginTime;
	/** 团结束时间 */
	private Date endTime;
	/** 创建时间 */
	private Date createTime;
	/** 创建用户id */
	private Long createId;
	/** 更新id */
	private Long updateId;
	/** 更新时间 */
	private Date updateTime;
	
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
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


}
