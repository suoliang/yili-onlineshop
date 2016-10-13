package com.fushionbaby.cms.dto;

import java.util.Date;

/***
 * CMS中将商品分类信息转换成json格式使用
 * 
 * @author Leon
 *
 */
public class SkuCategoryDto {
	/** ID值 */
	private String id;
	/** 父级ID */
	private String pId;
	/** 分类名称 */
	private String name;
	/** 分类标示,value值  */
	private String code;
	/** 是否显示y/n */
	private String isShow;
	/** 分类级别 */
	private String categoryLevel;
	/** 父分类code,上级分类code  */
	private String grandcategoryCode;
	/** 父分类名称,上级分类  */
	private String grandcategoryName;
	/** 创建时间 */
	private Date createTime;
	/** 分类顺序 */
	private Integer showOrder;
	/** 分类版本时间 */
	private Date version;
	/** 分类的英文名称 */
	private String englishName;
	/** 分类的关键字，为了搜索 */
	private String keywords;
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
	/** 关联标签code*/
	private String labelCode;
	/** 是否关联标签*/
	private String isLabelRelation;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
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

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(String categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public String getGrandcategoryCode() {
		return grandcategoryCode;
	}

	public void setGrandcategoryCode(String grandcategoryCode) {
		this.grandcategoryCode = grandcategoryCode;
	}

	public String getGrandcategoryName() {
		return grandcategoryName;
	}

	public void setGrandcategoryName(String grandcategoryName) {
		this.grandcategoryName = grandcategoryName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
		this.englishName = englishName;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
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

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getOldCategoryLogoUrl() {
		return oldCategoryLogoUrl;
	}

	public void setOldCategoryLogoUrl(String oldCategoryLogoUrl) {
		this.oldCategoryLogoUrl = oldCategoryLogoUrl;
	}

	public String getIsLabelRelation() {
		return isLabelRelation;
	}

	public void setIsLabelRelation(String isLabelRelation) {
		this.isLabelRelation = isLabelRelation;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

}
