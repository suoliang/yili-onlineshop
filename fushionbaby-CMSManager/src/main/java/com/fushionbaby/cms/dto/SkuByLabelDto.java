/**
 * 
 */
package com.fushionbaby.cms.dto;

/**
 * @author mengshaobo
 * 便签下的商品
 */
public class SkuByLabelDto {
/** 商品序号*/
	private Long id;
	/** 商品编号*/
	private String code;
	/** 商品名称*/
	private String name;
	/** 产品编号*/
	private String productCode;
	/** 显示顺序*/
	private Integer showOrder;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Integer getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}
	
	
	
}
