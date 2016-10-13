package com.fushionbaby.sku.model;

import java.math.BigDecimal;
import java.util.Date;

public class SkuEpoint {
	private Long id;
	private String skuCode;
	private String skuName;
	private Date createTime;
	private Date updateTime;
	private String labelCode;
	private BigDecimal needEpoint;
	
	/**额外展示字段*/
	private String imgPath;
	
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
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
	public BigDecimal getNeedEpoint() {
		return needEpoint;
	}
	public void setNeedEpoint(BigDecimal needEpoint) {
		this.needEpoint = needEpoint;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getLabelCode() {
		return labelCode;
	}
	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}
	
	
}
