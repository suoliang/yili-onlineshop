package com.fushionbaby.cms.dto;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author 孟少博
 *
 */
public class SkuProductDto {
	/** 产品序号*/
	private Long id;
	/** 产品编号*/
	private String code;
	
	/** 产品名称*/
	private String name;
	/** 是否使用*/
	private String disable;
	/** 创建开始时间*/
	private String createTimeFrom;
	/** 创建结束时间*/
	private String createTimeTo;
	/** 分类编号*/
	private String categoryCode;
	/** 分类名称*/
	private String categoryName;
	/** 品牌编号*/
	private String brandCode;
	/** 品牌编号*/
	private String brandName;
	/** 创建时间*/
	private Date createTime;
	/** 修改时间*/
	private Date updateTime;
	
	/** 商品列表*/
	private List<SkuDto> skuList;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public String getCreateTimeFrom() {
		return createTimeFrom;
	}

	public void setCreateTimeFrom(String createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}

	public String getCreateTimeTo() {
		return createTimeTo;
	}

	public void setCreateTimeTo(String createTimeTo) {
		this.createTimeTo = createTimeTo;
	}

	public List<SkuDto> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<SkuDto> skuList) {
		this.skuList = skuList;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
