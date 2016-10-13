package com.aladingshop.store.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author mengshaobo
 *
 */
public class SkuDto {
	
	/** 门店序号*/
	private String storeNumber;
	
	/** 门店编号*/
	private String storeCode;

	/** id */
	private Long id;
	
	/** 相关标签编码 **/
	private String labelCode;
	
	/** 分类名称*/
	private String categoryName;

	/** 分类编号 */
	private String categoryCode;

	/** 父分类编号 */
	private String grandCategoryCode;

	/** 唯一编号 */
	private String uniqueCode;
	
	/** 商品编号 */
	private String skuNo;
	
	/** 商品名称 */
	private String skuName;
	
	/** 商品状态，默认是未上架 */
	private String status;
	
	/** 限购数量 */
	private String quotaNum;
	
	/** 库存数量*/
	private Integer availableQty;
	
	/** 商品价格*/
	private BigDecimal currentPrice;
	
	/** 显示顺序 */
	private String showorder;
	
	
	/** 创建开始时间 */
	private String createTimeFrom;
	
	/** 创建结束时间 */
	private String createTimeTo;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 修改时间 */
	private Date updateTime;
	
	/** 修改开始时间*/
	private String updateTimeFrom;
	
	/** 修改结束时间*/
	private String updateTimeTo;

	
	
	
	
	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public Integer getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getGrandCategoryCode() {
		return grandCategoryCode;
	}

	public void setGrandCategoryCode(String grandCategoryCode) {
		this.grandCategoryCode = grandCategoryCode;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getQuotaNum() {
		return quotaNum;
	}

	public void setQuotaNum(String quotaNum) {
		this.quotaNum = quotaNum;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getShoworder() {
		return showorder;
	}

	public void setShoworder(String showorder) {
		this.showorder = showorder;
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

	public String getUpdateTimeFrom() {
		return updateTimeFrom;
	}

	public void setUpdateTimeFrom(String updateTimeFrom) {
		this.updateTimeFrom = updateTimeFrom;
	}

	public String getUpdateTimeTo() {
		return updateTimeTo;
	}

	public void setUpdateTimeTo(String updateTimeTo) {
		this.updateTimeTo = updateTimeTo;
	}
	
	
	
	
	
	

}
