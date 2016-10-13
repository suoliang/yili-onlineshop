/**
 * 
 */
package com.fushionbaby.common.condition.sku;

import com.fushionbaby.common.condition.QueryCondition;

/**
 * @author mengshaobo 商品分类查询
 */
public class SkuCategoryQueryCondition  extends QueryCondition{
	
	/** 分类等级*/
	private Integer categoryLevel;
	
	/** 分类编号*/
	private String code;
	
	/** 门店编号*/
	private String storeCode;
	
	/** 是否显示*/
	private String isShow;
	/** 父分类编号*/
	private String grandCode;
	
	/** 分类名称 */
	private String name;
	
	/** 英文名称 */
	private String englishName;

	
	
	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}





	public Integer getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(Integer categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGrandCode() {
		return grandCode;
	}

	public void setGrandCode(String grandCode) {
		this.grandCode = grandCode;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

}
