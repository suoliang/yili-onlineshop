package com.fushionbaby.common.dto.sku;

import java.io.Serializable;
import java.util.List;

import com.fushionbaby.common.dto.BrandDto;

/**
 * 分类DTO
 * 
 * @author mengshaobo
 * 
 */

public class CategoryDto implements Serializable, Comparable<CategoryDto> {

	private static final long serialVersionUID = -2695810279333209876L;

	/** 是否显示 */
	@Deprecated
	private String isShow;

	/** 分类等级 */
	private Integer level;

	/** 分类编号 */
	private String code;

	/** 分类名称 */
	private String name;

	/** 分类英文名称 -- 列表页3 */
	private String englishName;

	/** 分类LOGO图片路径 */
	private String logoUrl;

	/** 分类标题图片路径 */
	private List<String> titleUrl;
	/** 分类banner图片路径 */
	private List<String> bannerUrl;

	/** 父分类code */
	private String grandCode;

	/** 暂当remark用 */
	private String linkUrl;

	/** 子分类 */
	private List<CategoryDto> childCategory;

	public List<String> getTitleUrl() {
		return titleUrl;
	}

	public void setTitleUrl(List<String> titleUrl) {
		this.titleUrl = titleUrl;
	}

	public List<String> getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(List<String> bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	/** 分类 下的品牌列表 */
	private List<BrandDto> brandList;

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

	public List<BrandDto> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<BrandDto> brandList) {
		this.brandList = brandList;
	}

	public List<CategoryDto> getChildCategory() {
		return childCategory;
	}

	public void setChildCategory(List<CategoryDto> childCategory) {
		this.childCategory = childCategory;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParentCode() {
		return grandCode;
	}

	public void setParentCode(String parentCode) {
		this.grandCode = parentCode;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(CategoryDto o) {
		return this.getLevel().compareTo(o.getLevel());
	}
}
