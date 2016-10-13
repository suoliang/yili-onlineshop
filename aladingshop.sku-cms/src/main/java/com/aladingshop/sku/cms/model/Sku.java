/**
 * 
 */
package com.aladingshop.sku.cms.model;

import java.util.Date;

/**
 * @author mengshaobo 商品基本信息
 */
public class Sku {

	/** id */
	private Long id;
	
	/** code*/
	private String code;

	/** 唯一编码 */
	private String uniqueCode;

	/** 商品条形码 */
	private String barCode;

	/** 商品编号 */
	private String skuNo;

	/** 商品名称 */
	private String name;

	/** 商品颜色 */
	private String color;

	/** 商品尺寸 */
	private String size;

	/** 分类编号 */
	private String categoryCode;

	/** 品牌编号 */
	private String brandCode;

	/** 产品编号 */
	private String productCode;

	/** 商品描述 */
	private String description;

	/** 商品状态，默认是未上架 */
	private String status;

	/** 限购数量 */
	private Integer quotaNum;

	/** 显示顺序 */
	private Integer showorder;

	/** 创建时间 */
	private Date createTime;

	/** 创建用户id */
	private Long createId;

	/** 更新id */
	private Long updateId;

	/** 更新时间 */
	private Date updateTime;
	
	/** 门店编码*/
	private String storeCode;
	
	

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public Integer getShoworder() {
		return showorder;
	}

	public void setShoworder(Integer showorder) {
		this.showorder = showorder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Integer getQuotaNum() {
		return quotaNum;
	}

	public void setQuotaNum(Integer quotaNum) {
		this.quotaNum = quotaNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
