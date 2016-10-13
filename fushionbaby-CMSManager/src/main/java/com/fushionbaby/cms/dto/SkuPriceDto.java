/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月3日上午11:17:05
 */
package com.fushionbaby.cms.dto;

import java.math.BigDecimal;

/**
 * @description 商品价格DTO
 * @author 孟少博
 * @date 2015年12月3日上午11:17:05
 */
public class SkuPriceDto {

	/** 商品编号*/
	private String skuCode;
	
	/** 预售价 */
	private BigDecimal prePrice;
	
	/** 现价 */
	private BigDecimal currentPrice;
	
	/** 零售价/吊牌价 */
	private BigDecimal retailPrice;
	
	/** 成本价 */
	private BigDecimal costPrice;
	
	/** 市场价 */
	private BigDecimal marketPrice;
	
	/** 阿拉丁VIP价*/
	private BigDecimal aladingPrice;
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public BigDecimal getPrePrice() {
		return prePrice;
	}
	public void setPrePrice(BigDecimal prePrice) {
		this.prePrice = prePrice;
	}
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	public BigDecimal getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public BigDecimal getAladingPrice() {
		return aladingPrice;
	}
	public void setAladingPrice(BigDecimal aladingPrice) {
		this.aladingPrice = aladingPrice;
	}
	
	
	
	
}
