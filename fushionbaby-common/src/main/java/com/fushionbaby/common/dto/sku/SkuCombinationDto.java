package com.fushionbaby.common.dto.sku;

import java.util.List;

/**
 * 优惠商品组合
 * @author mengshaobo
 *
 */
public class SkuCombinationDto {
	/** 组合编号*/
	private Long combinationId;
	/** 商品集合*/
	private List<SkuDto> skuDtoList;
	/**优惠价*/
	private String discount;
	/**组合价*/
	private String combinationPrice;
	/**原价*/
	private String originalPrice;
	public List<SkuDto> getSkuDtoList() {
		return skuDtoList;
	}
	public void setSkuDtoList(List<SkuDto> skuDtoList) {
		this.skuDtoList = skuDtoList;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getCombinationPrice() {
		return combinationPrice;
	}
	public void setCombinationPrice(String combinationPrice) {
		this.combinationPrice = combinationPrice;
	}
	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	public Long getCombinationId() {
		return combinationId;
	}
	public void setCombinationId(Long combinationId) {
		this.combinationId = combinationId;
	}
	
}
