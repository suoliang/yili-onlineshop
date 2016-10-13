package com.fushionbaby.common.dto.sku;

import java.io.Serializable;

/**
 * 
 * @author mengshaobo 商品列表
 */
public class SkuDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 唯一Code */
	private String skuCode;
	
	/** 商品颜色 */
	private String color;
	
	/** 商品尺寸 */
	private String size;
	
	/** 商品名称 */
	private String name;
	
	/** 商品图片路径 */
	private String imgPath;
	
	/** 商品单价(现价) */
	private String priceNew;
	
	/** 原价*/
	@Deprecated
	private String priceOld;
	
	/** vip会员价*/
	private String priceVip;
	
	/** 是否是会员商品*/
	private String isMemberSku;
	
	/** 折扣值 */
	//private String discount;
	
	/** 商品评价数量 */
	private Integer commentCount;
	
	/** 是否有赠品 */
	private String hasGift;
	
	/** 商品描述*/
	private String description;
	
	/** 品牌名*/
	private String brandName;
	
	/** 产品编码*/
	private String productCode;
	
	public String getPriceOld() {
		return priceOld;
	}

	public void setPriceOld(String priceOld) {
		this.priceOld = priceOld;
	}


	
	public String getIsMemberSku() {
		return isMemberSku;
	}

	public void setIsMemberSku(String isMemberSku) {
		this.isMemberSku = isMemberSku;
	}

	public String getPriceVip() {
		return priceVip;
	}

	public void setPriceVip(String priceVip) {
		this.priceVip = priceVip;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getDescription() {
		return description;
	}
	

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}


	public String getPriceNew() {
		return priceNew;
	}

	public void setPriceNew(String priceNew) {
		this.priceNew = priceNew;
	}

//	public String getDiscount() {
//		return discount;
//	}
//
//	public void setDiscount(String discount) {
//		this.discount = discount;
//	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public String getHasGift() {
		return hasGift;
	}

	public void setHasGift(String hasGift) {
		this.hasGift = hasGift;
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

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

}
