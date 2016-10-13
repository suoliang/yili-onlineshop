/**
 * 
 */
package com.fushionbaby.common.condition.sku;

/**
 * @author mengshaobo 商品库存查询条件
 */
public class SkuInventoryQueryCondition {
	/** 产品号 */
	private String productCode;
	/** 尺寸 */
	private String size;
	/** 颜色 */
	private String color;
	/** 可用数量 */
	private Integer availableQty;

	/** 商品编号 */
	private String skuCode;


	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}

}
