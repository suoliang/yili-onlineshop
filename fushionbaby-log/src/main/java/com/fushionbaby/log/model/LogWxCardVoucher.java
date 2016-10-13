/**
 * 
 */
package com.fushionbaby.log.model;

import java.util.Date;


/**
 * @author mengshaobo
 *
 */
public class LogWxCardVoucher {
	private Long id;
	/** 卡券编号*/
	private String code;
	/** 卡号*/
	private String cardId;
	/** 销毁时间*/
	private Date destroyTime;
	/** 订单号*/
	private String orderId;
	/** 优惠金额*/
	private String  reduceCost;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getDestroyTime() {
		return destroyTime;
	}
	public void setDestroyTime(Date destroyTime) {
		this.destroyTime = destroyTime;
	}
	public String getReduceCost() {
		return reduceCost;
	}
	public void setReduceCost(String reduceCost) {
		this.reduceCost = reduceCost;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	
}
