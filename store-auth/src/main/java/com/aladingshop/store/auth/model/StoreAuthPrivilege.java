package com.aladingshop.store.auth.model;

import java.util.Date;

/***
 * 门店 系统权限
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月25日下午2:00:03
 */
public class StoreAuthPrivilege {
	/**自增id*/
	private Long id;
    /** 门店code*/
	private String storeCode;
	/** 权限名称*/
	private String name;
    /** 父权限id*/
	private Long parentId;
    /** 排序*/
	private Integer sortNo;
    /** 权限url*/
	private String url;
	/** 权限等级*/
	private Integer level;
    /** 创建时间*/
	private Date createTime;
	/** 修改时间*/
	private Date updateTime;
	/** 创建者id*/
	private Long createId;
	/** 更新者id*/
	private Long updateId;
	
	
	
	/** 做页面显示 */
	private boolean select;

	/** 一级权限的图标 */
	private String iconClass;

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

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
		this.name = name == null ? null : name.trim();
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	
	
}