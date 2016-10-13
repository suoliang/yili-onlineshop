package com.fushionbaby.pay.controller.app.union.service;

/***
 * @description 查询银联的交易记录
 * @author 索亮
 * @date 2016年2月29日下午5:26:23
 */
public interface AppUnionQueryService {

	/***
	 * 查询银联交易记录by商户订单号和订单发送时间
	 * @param orderId  商户订单号
	 * @param txnTime  订单发送时间
	 * @return
	 */
	Object queryUnionRecord(String orderId,String txnTime);
	
}
