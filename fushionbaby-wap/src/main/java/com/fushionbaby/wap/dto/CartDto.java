package com.fushionbaby.wap.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 张明亮
 * 购物车
 */
public class CartDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer pnumTotal;//购买商品总数量
	private BigDecimal priceTotal;//本购物车总计金额
	private BigDecimal rowsCurrentPriceTotal;//行单价小计
	private List<CartItemDto> items;
	public Integer getPnumTotal() {
		return pnumTotal;
	}
	public void setPnumTotal(Integer pnumTotal) {
		this.pnumTotal = pnumTotal;
	}
	public BigDecimal getPriceTotal() {
		return priceTotal;
	}
	public void setPriceTotal(BigDecimal priceTotal) {
		this.priceTotal = priceTotal;
	}
	public BigDecimal getRowsCurrentPriceTotal() {
		return rowsCurrentPriceTotal;
	}
	public void setRowsCurrentPriceTotal(BigDecimal rowsCurrentPriceTotal) {
		this.rowsCurrentPriceTotal = rowsCurrentPriceTotal;
	}
	public List<CartItemDto> getItems() {
		return items;
	}
	public void setItems(List<CartItemDto> items) {
		this.items = items;
	}
}
