package com.fushionbaby.sku.model;

import java.util.Date;

/**
 * 
 * @author King
 * 
 *         品牌管理
 * 
 */
public class SkuBrand {
	private Long id;
	private String brandCode;
	/** 品牌名称 */
	private String brandName;
	/** 品牌前缀 */
	private String brandPrefix;
	/** 上传的该品牌公司logo图片 */
	private String brandLogoWebUrl;
	/** 品牌的网址 */
	private String brandSiteUrl;
	/** 品牌在前台页面的显示顺序，数字越大越靠后 */
	private Integer sortOrder;
	/** 该品牌是否显示，N，否；Y，显示 */
	private String isShow;
	/** 品牌描述 */
	private String brandDesc;
	/* 上传的该品牌公司手机客户端logo图片 */
	private String brandLogoAppUrl;
	/** 创建时间 */
	private Date createTime;
	/** 创建id */
	private Long createId;
	/** 更新时间 */
	private Date updateTime;
	/** 更新id */
	private Long updateId;

	private String oldBrandLogoWebUrl;

	private String oldBrandLogoAppUrl;

	public String getOldBrandLogoWebUrl() {
		return oldBrandLogoWebUrl;
	}

	public void setOldBrandLogoWebUrl(String oldBrandLogoWebUrl) {
		this.oldBrandLogoWebUrl = oldBrandLogoWebUrl;
	}

	public String getOldBrandLogoAppUrl() {
		return oldBrandLogoAppUrl;
	}

	public void setOldBrandLogoAppUrl(String oldBrandLogoAppUrl) {
		this.oldBrandLogoAppUrl = oldBrandLogoAppUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName == null ? null : brandName.trim();
	}

	public String getBrandPrefix() {
		return brandPrefix;
	}

	public void setBrandPrefix(String brandPrefix) {
		this.brandPrefix = brandPrefix == null ? null : brandPrefix.trim();
	}

	public String getBrandSiteUrl() {
		return brandSiteUrl;
	}

	public void setBrandSiteUrl(String brandSiteUrl) {
		this.brandSiteUrl = brandSiteUrl == null ? null : brandSiteUrl.trim();
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getBrandDesc() {
		return brandDesc;
	}

	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc == null ? null : brandDesc.trim();
	}

	public String getBrandLogoWebUrl() {
		return brandLogoWebUrl;
	}

	public void setBrandLogoWebUrl(String brandLogoWebUrl) {
		this.brandLogoWebUrl = brandLogoWebUrl;
	}

	public String getBrandLogoAppUrl() {
		return brandLogoAppUrl;
	}

	public void setBrandLogoAppUrl(String brandLogoAppUrl) {
		this.brandLogoAppUrl = brandLogoAppUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	@Override
	public String toString() {
		return "MaBrand [id=" + id + ", brandName=" + brandName
				+ ", brandPrefix=" + brandPrefix + ", brandLogoUrl="
				+ brandLogoWebUrl + ", barndSiteUrl=" + brandSiteUrl
				+ ", sortOrder=" + sortOrder + ", isShow=" + isShow
				+ ", brandDesc=" + brandDesc + ", " + brandLogoAppUrl
				+ ", createTime=" + createTime + ", createId=" + createId
				+ ", updateTime=" + updateTime + ", updateId=" + updateId
				+ ",]";
	}
}