/**
 * 
 */
package com.fushionbaby.common.condition.sku;

import com.fushionbaby.common.condition.QueryCondition;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年7月29日下午2:14:25
 */
public class LabelCategoryRelationQueryCondition extends QueryCondition {
	
	/** 标签编码 */
	private String labelCode;
	
	/** 分类code */
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
