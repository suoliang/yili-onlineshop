package com.fushionbaby.core.vo;


public class SkuByOrderLine extends OrderEvaluate{
	/** 商品图片 **/
	private String skuImg;
	/** 商品名称 **/
	private String skuName;
	/** 商品单价 **/
	private String skuPrice;
	/** 商品颜色 **/
	private String skuColor;
	/** 商品尺码 **/
	private String skuSize;
	/** 商品是否已评价 */
	private String isComment;
	/** 商品订购数量 **/
	private Integer quantity;
	/** 商品行小计 */
	private String lineTotalPrice;

	public String getSkuImg() {
		return skuImg;
	}

	public void setSkuImg(String skuImg) {
		this.skuImg = skuImg;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(String skuPrice) {
		this.skuPrice = skuPrice;
	}

	public String getSkuColor() {
		return skuColor;
	}

	public void setSkuColor(String skuColor) {
		this.skuColor = skuColor;
	}

	public String getSkuSize() {
		return skuSize;
	}

	public void setSkuSize(String skuSize) {
		this.skuSize = skuSize;
	}

	public String getIsComment() {
		return isComment;
	}

	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getLineTotalPrice() {
		return lineTotalPrice;
	}

	public void setLineTotalPrice(String lineTotalPrice) {
		this.lineTotalPrice = lineTotalPrice;
	}

}
