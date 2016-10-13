/**
 * 
 */
package com.fushionbaby.common.condition.sku;

import java.util.List;

/**
 * @author mengshaobo
 * 
 */
public class SkuEntryQueryCondition extends BaseSkuQueryCondition {
	/** 产品编号*/
	private String productCode;
	/** 编号 */
	private String barCode;
	/** 商品编号*/
	private String skuNo;
	/** 名称 */
	private String name;
	/** 颜色*/
	private String color;
	/** 规格*/
	private String size;
	/** 商品编号集合 */
	private List<String> skuCodeList;
	/** 是否为赠品 */
	private String isGift;
	/** 标签编号 */
	private String labelCode;
	/** 关键字 */
	private String searchKey;
	/** 新品（用于排序） */
	private String newSkuSort;
	/** 品牌编号 */
	private String brandCode;
	/** 分类编号 */
	private String categoryCode;
	/** 分类编号集合*/
	private List<String> categoryCodes;
	
	/** 查询起始时间 */
	private String createTimeFrom;
	private String createTimeTo;

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


	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}
	

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
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

	public List<String> getCategoryCodes() {
		return categoryCodes;
	}

	public void setCategoryCodes(List<String> categoryCodes) {
		this.categoryCodes = categoryCodes;
	}



	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}


	public String getIsGift() {
		return isGift;
	}

	public void setIsGift(String isGift) {
		this.isGift = isGift;
	}


	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getNewSkuSort() {
		return newSkuSort;
	}

	public void setNewSkuSort(String newSkuSort) {
		this.newSkuSort = newSkuSort;
	}



	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


	public List<String> getSkuCodeList() {
		return skuCodeList;
	}

	public void setSkuCodeList(List<String> skuCodeList) {
		this.skuCodeList = skuCodeList;
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



}
