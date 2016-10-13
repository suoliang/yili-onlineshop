/**
 * 
 */
package com.fushionbaby.common.condition;

import com.fushionbaby.common.dto.UserDto;

/**
 * @author mengshaobo
 *
 */
public class ShoppingCartBo  {
	
	/** 商品序号集合*/
	private String[] skuCodeArray;
	
	/** 门店编号*/
	private String storeCode;
	
	/** 商品序号*/
	private String skuCode;
	
	/** 商品图片显示类型*/
	private String imageType;
	
	/** 当前用户*/
	private UserDto user;
	
	/** 缓存Key*/
	private  String visitKey;
	
	/** 购买数量*/
	private Integer quantity;
	
	/** 是否选中状态*/
	private String isSelected;
	
	/** 购物车Sid*/
	private String shoppingCartSid;
	
	private Integer start;
	
	private Integer limit;
	
	
	
	
	
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getVisitKey() {
		return visitKey;
	}

	public void setVisitKey(String visitKey) {
		this.visitKey = visitKey;
	}

	

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}


	public String[] getSkuCodeArray() {
		return skuCodeArray;
	}

	public void setSkuCodeArray(String[] skuCodeArray) {
		this.skuCodeArray = skuCodeArray;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getShoppingCartSid() {
		return shoppingCartSid;
	}

	public void setShoppingCartSid(String shoppingCartSid) {
		this.shoppingCartSid = shoppingCartSid;
	}

}
