package com.aladingshop.sku.cms.model;

import java.util.Date;

/**
 * @author mengshaobo 热销商品
 */
public class SkuHot {

	/** Id */
	private Long id;
	/** 热销商品的编码 */
	private String skuCode;
	/** 热销商品的名称 */
	private String skuName;
	/** 热销商品的销售数量 */
	private Integer count;
	/** 热销商品添加的时间 */
	private Date addTime;

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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}


}
