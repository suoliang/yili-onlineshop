/**
 * 
 */
package com.fushionbaby.config.model;

/**
 * @author 孟少博
 *
 */
public class SysmgrDictConfig {
	private Long id;
	/** 标签名*/
	private String label;
	/** 数据值*/
	private String value;
	/** 类型*/
	private String type;
	/** 描述*/
	private String description;
	/** 排行*/
	private Integer sort;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
	
}
