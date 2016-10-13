package com.fushionbaby.sku.model;

import java.util.Date;

/**
 * @author mengshaobo 赠品信息表
 */
public class SkuGiftRelation {
	/** 赠品信息id */
	private Long id;
	/** 有赠品的商品id */
	private String skuCode;
	/** 作为赠品的商品id */
	private String giftSkuCode;
	
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getGiftSkuCode() {
		return giftSkuCode;
	}

	public void setGiftSkuCode(String giftSkuCode) {
		this.giftSkuCode = giftSkuCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}
