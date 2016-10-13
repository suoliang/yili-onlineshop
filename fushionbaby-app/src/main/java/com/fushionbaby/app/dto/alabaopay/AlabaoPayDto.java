package com.fushionbaby.app.dto.alabaopay;

/***
 * @description 如意消费卡支付实体类DTO
 * @author 索亮
 * @date 2016年2月24日上午11:56:21
 */
public class AlabaoPayDto {
	/** 描述 */
	private String desc;
	/** 订单总价,折扣前价格 */
	private String totalPrice; 
	/** 折扣后订单价格 */
	private String discountPrice;
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getDiscountPrice() {
		return discountPrice;
	}
	
	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}
	
}
