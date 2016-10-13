package com.aladingshop.sku.cms.dto;

public class SkuProductDto {
	
	private Long id;
	/** 产品编号*/
	private String code;
	/** 产品名称*/
	private String name;
	/** 是否使用*/
	private String disable;
	/** 创建时间*/
	private String createTime;
	/** 创建开始时间*/
	private String createTimeFrom;
	/** 创建结束时间*/
	private String createTimeTo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public String getCreateTimeFrom() {
		return createTimeFrom;
	}

	public void setCreateTimeFrom(String createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}

	public String getCreateTimeTo() {
		return createTimeTo;
	}

	public void setCreateTimeTo(String createTimeTo) {
		this.createTimeTo = createTimeTo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
