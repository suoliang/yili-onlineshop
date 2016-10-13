/**
 * 
 */
package com.fushionbaby.common.dto.sku;

import java.io.Serializable;

/**
 * @author mengshaobo 当前选中的商品
 */
public class SelectedSkuDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 颜色 */
	private String color;

	/** 尺寸 */
	private String size;
	
	/** 唯一编码 */
	private String skuCode;

	/** 商品可用量*/
	private Integer availableQty;

	/** 产品编号 */
	private String productCode;


	public Integer getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}


}
