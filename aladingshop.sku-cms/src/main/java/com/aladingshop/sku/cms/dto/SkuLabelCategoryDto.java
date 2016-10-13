package com.aladingshop.sku.cms.dto;

/**
 * @description 标签和分类的关联dto
 * @author 索亮
 * @date 2015年7月28日上午9:41:54
 */
public class SkuLabelCategoryDto {
	/** 标签编码 */
	private String labelCode;
	/** 分类编码 */
	private String categoryCode;
	/** 是否禁用 */
	private String disable;
	
	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}
	
}
