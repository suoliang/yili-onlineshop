package com.fushionbaby.cms.dto;

import java.util.Date;

/**
 * 
 * @author mengshaobo
 *
 */
public class SkuDto {

	/** id */
	private Long id;
	
	/** 相关标签编码 **/
	private String labelCode;

	/** 品牌编号 */
	private String brandCode;
	
	/** 品牌名称*/
	private String brandName;
	
	/** 分类名称*/
	private String categoryName;

	/** 分类编号 */
	private String categoryCode;

	/** 父分类编号 */
	private String grandCategoryCode;

	/** 产品编号 */
	private String productCode;

	/** 唯一编号 */
	private String uniqueCode;
	
	/** 门店编号*/
	private String storeCode;
	
	/** 商品编号 */
	private String skuNo;
	
	/** 商品条形码*/
	private String barCode;
	
	/** 商品名称 */
	private String skuName;
	
	/** 商品颜色 */
	private String color;
	
	/** 商品尺寸 */
	private String size;
	
	/** 商品状态，默认是未上架 */
	private String status;
	
	/** 限购数量 */
	private String quotaNum;
	
	/** 关键词 */
	private String keyWords;
	
	/** 商品价格*/
	private SkuPriceDto skuPriceDto;
	
	/** 商品描述 */
	private String skuDescription;
	
	/** 显示顺序 */
	private String showorder;
	
	/** 是否会员商品*/
	private String isMemberSku;
	
	/** 商品额外信息*/
	private SkuExtraInfoDto skuExtraInfo;
	
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
	
	/** 活动编码*/
	private String promotionsCode;
	
	
	
	
	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getIsMemberSku() {
		return isMemberSku;
	}

	public void setIsMemberSku(String isMemberSku) {
		this.isMemberSku = isMemberSku;
	}

	public SkuExtraInfoDto getSkuExtraInfo() {
		return skuExtraInfo;
	}

	public void setSkuExtraInfo(SkuExtraInfoDto skuExtraInfo) {
		this.skuExtraInfo = skuExtraInfo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public SkuPriceDto getSkuPriceDto() {
		return skuPriceDto;
	}

	public void setSkuPriceDto(SkuPriceDto skuPriceDto) {
		this.skuPriceDto = skuPriceDto;
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



	public String getPromotionsCode() {
		return promotionsCode;
	}

	public void setPromotionsCode(String promotionsCode) {
		this.promotionsCode = promotionsCode;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
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


	public String getSkuDescription() {
		return skuDescription;
	}

	public void setSkuDescription(String skuDescription) {
		this.skuDescription = skuDescription;
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

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
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

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getGrandCategoryCode() {
		return grandCategoryCode;
	}

	public void setGrandCategoryCode(String grandCategoryCode) {
		this.grandCategoryCode = grandCategoryCode;
	}

}
