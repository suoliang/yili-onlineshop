/**
 * 
 */
package com.fushionbaby.common.dto.order.app;

import java.math.BigDecimal;

import com.fushionbaby.common.dto.order.GotoOrderDto;

/**
 * @author mengshaobo
 *
 */
public class AppGotoOrderDto extends GotoOrderDto {

	private static final long serialVersionUID = 1L;
	/** 手机端优惠的金额*/
	private String discountMoney;
	
	/** 红包金额*/
	private BigDecimal redMoney;
	
	/**红包使用说明的图片url*/
	private String redUrl;
	
	
	public String getRedUrl() {
		return redUrl;
	}
	public void setRedUrl(String redUrl) {
		this.redUrl = redUrl;
	}
	public BigDecimal getRedMoney() {
		return redMoney;
	}
	public void setRedMoney(BigDecimal redMoney) {
		this.redMoney = redMoney;
	}
	public String getDiscountMoney() {
		return discountMoney;
	}
	public void setDiscountMoney(String discountMoney) {
		this.discountMoney = discountMoney;
	}
	
	

}
