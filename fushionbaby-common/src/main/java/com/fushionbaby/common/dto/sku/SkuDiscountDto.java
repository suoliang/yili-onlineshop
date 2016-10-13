package com.fushionbaby.common.dto.sku;

import java.io.Serializable;

/**
 * 商品折扣dto
 * @author duxihu
 *
 */
public class SkuDiscountDto implements Serializable {

	
	private static final long serialVersionUID = 6583432970584353396L;
	private int num;//数量
	private String price;//折扣总价
	
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	
}
