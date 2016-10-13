package com.aladingshop.store.auth.model;
/***
 * 门店系统  后台角色
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月25日下午2:04:33
 */
public class StoreAuthRole {
	/** 自增id*/
	private Long id;
	/** 角色描述*/
	private String description;
	/** 角色名称*/
	private String name;
    /** 更新时间*/
	private String updateTime;
	/** 更新者id*/
	private Long updateId;
	/** 门店编码*/
	private String storeCode;
	
	
	/** 页面复选框勾选标记 */

	private boolean select;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

}