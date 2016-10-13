package com.fushionbaby.core.vo;
/**
 * 待评价订单
 * @author Leon
 *
 */
public class OrderEvaluate {
	/** 订单行id */
	private Long orderLineId;
	/** 订单编码 */
	private String orderCode;
	/** 商品编码 */
	private String skuCode;
	/** 下单时间 */
	private String createOrderTime;
	
	public Long getOrderLineId() {
		return orderLineId;
	}

	public void setOrderLineId(Long orderLineId) {
		this.orderLineId = orderLineId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getCreateOrderTime() {
		return createOrderTime;
	}

	public void setCreateOrderTime(String createOrderTime) {
		this.createOrderTime = createOrderTime;
	}

}
