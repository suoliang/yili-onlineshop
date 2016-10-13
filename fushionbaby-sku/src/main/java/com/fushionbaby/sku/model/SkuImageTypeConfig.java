package com.fushionbaby.sku.model;

/**
 * 商品的图片类型
 * 
 * @author xupeijun
 * 
 */
public class SkuImageTypeConfig {

	private Long id;
	/** 图片类型名称*/
	private String name;
	/** 图片编号*/
	private String code;
	/** 扩展字段*/
	private String suffix;
	/** 是否多个*/
	private String isMultiple;
	/** 是否默认图片*/
	private String isDefault;
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

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getIsMultiple() {
		return isMultiple;
	}

	public void setIsMultiple(String isMultiple) {
		this.isMultiple = isMultiple;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

}
