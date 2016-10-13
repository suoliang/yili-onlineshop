package com.fushionbaby.common.dto.cart;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张明亮
 * 购物车
 */
public class CartDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**  是否登录状态 y:已登录状态，n：未登录状态*/
	private String loginStatus;
	
	/** 购买商品总数量*/
	private Integer pnumTotal;
	
	/** 本购物车总计金额*/
	private String priceTotal;
	
	/** 购物车行商品集合*/
	private List<CartItemDto> items;
	
	/** 仅供APP端：shoppingCartSid*/
	private String shoppingCartSid;
	
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
	public List<CartItemDto> getItems() {
		return items;
	}
	public void setItems(List<CartItemDto> items) {
		this.items = items;
	}
	public String getShoppingCartSid() {
		return shoppingCartSid;
	}
	public void setShoppingCartSid(String shoppingCartSid) {
		this.shoppingCartSid = shoppingCartSid;
	}
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	
	
	
}
