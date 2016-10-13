package com.aladingshop.sku.cms.model;

import java.util.Date;

/**
 * 
 * @author King 商品分类
 */
public class SkuCategory {
	private Long id;
	/** 分类标示符 */
	private String code;
	/** 创建时间 */
	private Date createTime;
	/** 分类名称，可以重复 */
	private String name;
	/** 分类顺序 */
	private Integer showOrder;
	/** 分类版本时间 */
	private Date version;
	/** 一级父级分类.祖节点code */
	private String grandcategoryCode;
	/** 分类级别，一级，二级，三级...*/
	private Integer categoryLevel;
	/** 分类的英文名称 */
	private String englishName;
	/** 分类的关键字，为了搜索 */
	private String keywords;
	/** 是否在前台页面显示，y，显示；n，不显示 */
	private String isShow;
	/** 分类图片logoURL */
	private String categoryLogoUrl;

	/** 创建id */
	private Long createId;
	/** 更新时间 */
	private Date updateTime;
	/** 更新id */
	private Long updateId;
	/** 分类的链接地址 */
	private String linkUrl;
	
	/** 判断是否修改了图片*/
	private String oldCategoryLogoUrl;

	/** 门店编码*/
	private String storeCode;
	
	

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName == null ? null : englishName.trim();
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords == null ? null : keywords.trim();
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getCategoryLogoUrl() {
		return categoryLogoUrl;
	}

	public void setCategoryLogoUrl(String categoryLogoUrl) {
		this.categoryLogoUrl = categoryLogoUrl;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public String getGrandcategoryCode() {
		return grandcategoryCode;
	}

	public void setGrandcategoryCode(String grandcategoryCode) {
		this.grandcategoryCode = grandcategoryCode;
	}

	public Integer getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(Integer categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public String getOldCategoryLogoUrl() {
		return oldCategoryLogoUrl;
	}

	public void setOldCategoryLogoUrl(String oldCategoryLogoUrl) {
		this.oldCategoryLogoUrl = oldCategoryLogoUrl;
	}

	@Override
	public String toString() {
		return "MaCategory [id=" + id + ", code=" + code + ", createTime="
				+ createTime + ", name=" + name + ", showOrder=" + showOrder
				+ ", version=" + version + ", englishName=" + englishName
				+ ", keywords=" + keywords + ", isShow=" + isShow
				+ ", createId=" + createId + ", updateTime=" + updateTime
				+ ", updateId=" + updateId + "]";
	}
}