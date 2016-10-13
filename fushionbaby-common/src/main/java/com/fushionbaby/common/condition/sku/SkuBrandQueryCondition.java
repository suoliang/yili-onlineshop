/**
 * 
 */
package com.fushionbaby.common.condition.sku;

import com.fushionbaby.common.condition.QueryCondition;

/**
 * @author mengshaobo 品牌信息查询参数
 */
public class SkuBrandQueryCondition extends QueryCondition {
	
	/** 品牌名称 */
	private String brandName;
	/** 品牌前缀 */
	private String brandPrefix;

	/** 是否显示 */
	private String isShow;

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandPrefix() {
		return brandPrefix;
	}

	public void setBrandPrefix(String brandPrefix) {
		this.brandPrefix = brandPrefix;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

}
