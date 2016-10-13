/**
 * 
 */
package com.fushionbaby.common.dto.sku;

import java.util.List;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年7月29日上午11:33:13
 */
public class LabelCategoryRelationDto {
	/** 标签编码 */
	private String labelCode;

	/** 分类code */
	private String categoryCode;

	/** 是否禁用 */
	private String disable;

	/** 商品集合 */
	private List<SkuDto> skuDtoList;

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

	public List<SkuDto> getSkuDtoList() {
		return skuDtoList;
	}

	public void setSkuDtoList(List<SkuDto> skuDtoList) {
		this.skuDtoList = skuDtoList;
	}
}
