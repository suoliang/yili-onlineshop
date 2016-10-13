/**
 * 
 */
package com.aladingshop.sku.cms.model;

import java.util.Date;

/**
 * @author mengshaobo 商品商城库存信息
 */
public class SkuInventory {
	/** id */
	private Long id;
	/** 产品code */
	private String productCode;
	/** 商品可用量 */
	private Integer availableQty;
	/** 颜色 */
	private String skuColor;
	/** 创建时间 */
	private Date createTime;
	/** 最近修改时间 */
	private Date lastModifyTime;
	/** 一款extentionCode的规格，如尺寸、颜色。JSON格式存储，如{color:`red`,size:`M`} */
	private String propertyvalues;
	/** 尺寸 */
	private String skuSize;
	/** 版本时间 */
	private Date version;
	/** 商品编号 */
	private String skuCode;
	/** 商品名称*/
	private String skuName;

	/** 商品库存类型 */
	private Integer inventorytype;
	/** 创建用户id */
	private Long createId;
	/** 更新时间 */
	private Date updateTime;
	/** 更新id */
	private Long updateId;

	/** 门店编码*/
	private String storeCode;
	
	

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Integer getAvailableQty() {
		return availableQty == null ? 0 : availableQty;
	}

	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty == null ? 0 : availableQty;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSkuColor() {
		return skuColor;
	}

	public void setSkuColor(String skuColor) {
		this.skuColor = skuColor;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getPropertyvalues() {
		return propertyvalues;
	}

	public void setPropertyvalues(String propertyvalues) {
		this.propertyvalues = propertyvalues == null ? null : propertyvalues
				.trim();
	}

	public String getSkuSize() {
		return skuSize;
	}

	public void setSkuSize(String skuSize) {
		this.skuSize = skuSize == null ? null : skuSize.trim();
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Integer getInventorytype() {
		return inventorytype == null ? 0 : inventorytype;
	}

	public void setInventorytype(Integer inventorytype) {
		this.inventorytype = inventorytype;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

}
