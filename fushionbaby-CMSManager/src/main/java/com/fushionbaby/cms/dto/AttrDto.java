package com.fushionbaby.cms.dto;
/**
 * 
 * @author 孟少博
 *
 */
public class AttrDto {
	
	/** id*/
	private Long id;
	
	/** 属性名*/
	private String attrName;
	
	/** 属性值*/
	private String attrValue;
	
	/** 显示顺序*/
	private Integer showOrder;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	public Integer getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	
	
}
