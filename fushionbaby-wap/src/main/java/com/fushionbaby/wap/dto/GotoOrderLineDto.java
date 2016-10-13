package com.fushionbaby.wap.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 张明亮
 * 封装了为下订单gotoOrder页面的商品列表信息
 */
public class GotoOrderLineDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long skuId;//商品id
	private String skuName;//商品名称
	private String imgPath;//商品图片
	private Integer requestedQty;//数量,订购数量
	private BigDecimal currentPrice;//单价,销售单价
	private BigDecimal currentPriceTotal;//购买行小计
	private String color;//商品颜色
	private String size;//尺寸
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public Integer getRequestedQty() {
		return requestedQty;
	}
	public void setRequestedQty(Integer requestedQty) {
		this.requestedQty = requestedQty;
	}
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	public BigDecimal getCurrentPriceTotal() {
		return currentPriceTotal;
	}
	public void setCurrentPriceTotal(BigDecimal currentPriceTotal) {
		this.currentPriceTotal = currentPriceTotal;
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
}
