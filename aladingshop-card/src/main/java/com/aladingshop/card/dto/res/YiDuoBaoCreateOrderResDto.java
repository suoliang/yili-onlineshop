/**
 * 
 */
package com.aladingshop.card.dto.res;

/**
 * @description 购益多宝卡下订单响应dto
 * @author 孙涛
 * @date 2015年9月21日下午2:08:59
 */
public class YiDuoBaoCreateOrderResDto {
	/** 返回购买益多宝待支付单号 */
	private String orderCode;
	/** 当前用户SID */
	private String sid;
	/** 支付方式 */
	private String payWay;
	
	/** 阿拉宝登陆用户的sid*/
	private String alabaoSid;
	
	/**订单实际金额*/
	private String actualMoney;
	
	

	public String getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(String actualMoney) {
		this.actualMoney = actualMoney;
	}

	public String getAlabaoSid() {
		return alabaoSid;
	}

	public void setAlabaoSid(String alabaoSid) {
		this.alabaoSid = alabaoSid;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

}
