package com.fushionbaby.common.dto.cart;


public class CartModifyAfterDto {
	/** 购买商品总数量*/
	private Integer pnumTotal;
	
	/** 本购物车总计金额*/ 
	private String priceTotal;
	
	/** 当前购物车行小计*/
	private String currentCartPrice;
	
	/** 商品编号*/
	private String skuCode;

	public Integer getPnumTotal() {
		return pnumTotal;
	}

	public void setPnumTotal(Integer pnumTotal) {
		this.pnumTotal = pnumTotal;
	}

	public String getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(String priceTotal) {
		this.priceTotal = priceTotal;
	}

	public String getCurrentCartPrice() {
		return currentCartPrice;
	}

	public void setCurrentCartPrice(String currentCartPrice) {
		this.currentCartPrice = currentCartPrice;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	
}
