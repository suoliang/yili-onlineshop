package com.aladingshop.sku.cms.model;

import java.util.Date;

/**
 * 商品产品model
 * 
 * @author glc
 */
@Deprecated
public class SkuProduct {

	/** 产品Id */
	private Long id;
	
	/** 产品编号 */
	private String code;
	
	/** 产品名称 */
	private String name;
	
	/** 是否启用 y:启用 n:不启用'*/
	private String disable;
	
	/** 创建id */
	private Long createId;
	
	/** 更新id */
	private Long  updateId;
	
	/** 创建时间*/
	private Date createTime;
	
	/** 修改时间*/
	private Date updateTime;


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
		this.code = code;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
