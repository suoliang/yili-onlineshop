package com.fushionbaby.sku.model;

import java.util.Date;

/**
 * 
 * @author King
 *
 */
public class SkuLabel {
	private Long id;

	private String code;

	private String name;
	
	private String type;

	private String disable;
	
	private Integer maxCategoryNum;
	
	private Long createId;
	
	private Long updateId;
	
	private Date createTime;

	private Date updateTime;

	private Date version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public Integer getMaxCategoryNum() {
		return maxCategoryNum;
	}

	public void setMaxCategoryNum(Integer maxCategoryNum) {
		this.maxCategoryNum = maxCategoryNum;
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

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
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
		return "MaLabel [id=" + id + ", code=" + code + ", createTime="
				+ createTime + ", name=" + name + ", version=" + version
				+ ", createId=" + createId + ", updateTime=" + updateTime
				+ ", updateId=" + updateId + "]";
	}
}