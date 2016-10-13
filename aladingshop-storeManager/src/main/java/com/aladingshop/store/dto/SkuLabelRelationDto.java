/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月26日上午11:33:00
 */
package com.aladingshop.store.dto;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月26日上午11:33:00
 */
public class SkuLabelRelationDto {
	
	/** 商品唯一编号*/
	private String skuCode;

	/** 商品名称*/
	private String skuName;
	
	/** 是否显示*/
	private String disable;
	
	/** 商品编号*/
	private String skuNo;

	/** 显示顺序*/
	private Integer showOrder;
	
	
	
	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}
	
	
	
}
