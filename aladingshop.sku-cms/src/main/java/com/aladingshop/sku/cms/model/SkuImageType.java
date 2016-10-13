package com.aladingshop.sku.cms.model;

/**
 * 商品的图片类型
 * 
 * @author xupeijun
 * 
 */
public class SkuImageType {

	private Long id;
	/** 图片类型名称*/
	private String name;
	/** 图片编号*/
	private String code;
	/** 是否多个*/
	private String isMultiple;
	/** 是否使用*/
	private String disable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIsMultiple() {
		return isMultiple;
	}

	public void setIsMultiple(String isMultiple) {
		this.isMultiple = isMultiple;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

}
