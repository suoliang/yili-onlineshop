/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月17日上午10:59:03
 */
package com.fushionbaby.app.dto.store;

import java.util.List;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月17日上午10:59:03
 */
public class StoreCategoryDto {

	/** 等级*/
	private Integer level;
	
	/** 编号*/
	private String code;
	
	/** 名称*/
	private String name;
	
	/** 图标*/
	private String logoUrl;
	
	/** 子分类*/
	private List<StoreCategoryDto> childCategory;

	
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	





	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public List<StoreCategoryDto> getChildCategory() {
		return childCategory;
	}

	public void setChildCategory(List<StoreCategoryDto> childCategory) {
		this.childCategory = childCategory;
	}
	
	
	
	
	
}
