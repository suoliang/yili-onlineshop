package com.fushionbaby.common.dto.cart;

import java.io.Serializable;

/**
 * @author 张明亮
 * 购物车列表
 */
public class CartItemDto implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	/** 商品code*/
	private String skuCode;
	
	/** 购物车行id*/
	private String cartRowsId;
	
	/** 图片路径*/
	private String imgPath;
	
	/** 商品名称*/
	private String name;
	
	/** 购买数量*/
	private Integer pnum;
	
	/** 是否是会员商品*/
	private String isMemberSku;
	
	/** 限购数量  0表示不限购*/
	private Integer quotaNum;
	
	/** 库存数量*/
	private Integer availableQty;
	
	/** 价格*/
	private String price;
	
	/** 颜色*/
	private String color;
	
	/** 尺寸*/
	private String size;
	
	/** 购物车行小计*/
	private String rowPriceTotal;
	
	/** 是否选中*/
	private String isSelected;
	
	
	
	public String getIsMemberSku() {
		return isMemberSku;
	}
	public void setIsMemberSku(String isMemberSku) {
		this.isMemberSku = isMemberSku;
	}
	public Integer getQuotaNum() {
		return quotaNum;
	}
	public void setQuotaNum(Integer quotaNum) {
		this.quotaNum = quotaNum;
	}
	public Integer getAvailableQty() {
		return availableQty;
	}
	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getCartRowsId() {
		return cartRowsId;
	}
	public void setCartRowsId(String cartRowsId) {
		this.cartRowsId = cartRowsId;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPnum() {
		return pnum;
	}
	public void setPnum(Integer pnum) {
		this.pnum = pnum;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	public String getRowPriceTotal() {
		return rowPriceTotal;
	}
	public void setRowPriceTotal(String rowPriceTotal) {
		this.rowPriceTotal = rowPriceTotal;
	}
	public String getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
	
	
}
