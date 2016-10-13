/**
 * 
 */
package com.fushionbaby.core.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mengshaobo
 *订单关联优惠商品组合
 */
public class SoOrderCombination {

	
	/** 订单编号*/
	private Long id;	
	/** 订单编号*/
	private String orderCode;
	/** 商品组合编号*/
	private Long combinationId;
	/** 订单中包含组合数量*/
	private Integer amount;
	/**订单总价(折扣后)*/
	private BigDecimal totalPrice;		
	/** 创建时间*/
	private Date createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Long getCombinationId() {
		return combinationId;
	}
	public void setCombinationId(Long combinationId) {
		this.combinationId = combinationId;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
