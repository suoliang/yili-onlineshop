/**
 * 
 */
package com.fushionbaby.common.dto.sku;

/**
 * @author mengshaobo 商品类型 + 商品属性 + 属性值
 */
public class SkuAttrBo {

	/** 商品类型名称 */
	private Long skuTypeId;
	/** 商品编号 */
	@Deprecated
	private Long skuId;
	/** 产品编号 */
	private Long pid;
	/** 商品属性名称 */
	private String attrName;
	/** 属性编号 */
	private Long attrId;
	/** 商品属性值 */
	private String attrValue;

	public Long getSkuTypeId() {
		return skuTypeId;
	}

	public void setSkuTypeId(Long skuTypeId) {
		this.skuTypeId = skuTypeId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
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

	public Long getAttrId() {
		return attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

}
