package com.fushionbaby.core.vo;

/**
 * @description APP端订单详情
 * @author 索亮
 * @date 2016年1月13日上午9:51:44
 */
public class AppOrderDetail {
	/** 地址展示字符串 */
	private String[] addressStr;
	/** 订单展示字符串 */
	private String[] orderStr;
	/** 价格展示字符串 */
	private String[] priceStr;

	public String[] getAddressStr() {
		return addressStr;
	}

	public void setAddressStr(String[] addressStr) {
		this.addressStr = addressStr;
	}

	public String[] getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String[] orderStr) {
		this.orderStr = orderStr;
	}

	public String[] getPriceStr() {
		return priceStr;
	}

	public void setPriceStr(String[] priceStr) {
		this.priceStr = priceStr;
	}

}
