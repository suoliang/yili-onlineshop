package com.fushionbaby.wap.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fushionbaby.common.constants.CommonConstant;

/**
 * @author 张明亮
 * 购物车列表
 */
public class CartItemDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String skuId;//商品id
	private String cartRowsId;//购物车行id
	private String imgPath;//图片路径
	private String name;//商品名称
	private String type;//商品类型
	private Integer pnum;//购买数量
	private BigDecimal price;//价格
	private String color;//颜色
	private String size;//尺寸
	private BigDecimal rowPriceTotal;//购物车行小计
	private String isSelected=CommonConstant.YES;
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getPnum() {
		return pnum;
	}
	public void setPnum(Integer pnum) {
		this.pnum = pnum;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
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
	public BigDecimal getRowPriceTotal() {
		return rowPriceTotal;
	}
	public void setRowPriceTotal(BigDecimal rowPriceTotal) {
		this.rowPriceTotal = rowPriceTotal;
	}
	public String getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
}
