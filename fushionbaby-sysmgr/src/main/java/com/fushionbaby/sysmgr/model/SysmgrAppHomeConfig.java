package com.fushionbaby.sysmgr.model;

/**
 * @description 首页配置
 * @author 孟少博
 * @date 2016年1月11日下午5:53:27
 */
public class SysmgrAppHomeConfig {

	
	private Long id;
	
	/** 平台*/
	private Integer platform;
	
	/** 分类*/
	private String category;
	
	/** 标签*/
	private String label;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
