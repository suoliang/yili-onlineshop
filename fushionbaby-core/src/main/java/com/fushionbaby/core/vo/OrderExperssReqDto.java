package com.fushionbaby.core.vo;

import com.fushionbaby.common.dto.express.ExpressReqDto;

/***
 * 查询 订单状态的请求参数封装
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月2日下午8:21:14
 */
public class OrderExperssReqDto  extends ExpressReqDto{

	/** 订单编码 */
	private String orderCode;
	/** 用户标志 */
	private String sid;
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
	


}
