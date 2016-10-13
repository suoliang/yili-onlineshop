package com.fushionbaby.payment.service;

import com.fushionbaby.payment.model.PaymentWebWx;

/***
 * @description web -- 微信支付Service
 * @author 索亮
 * @date 2015年8月27日下午3:52:47
 */
public interface PaymentWebWxService<T extends PaymentWebWx> {
	/**添加*/
	public void add(PaymentWebWx paymentWebWx);
	
	/**修改*/
	public void updateByMemberIdAndOrderCode(PaymentWebWx paymentWebWx);
	
	/**删除*/
	public void deleteById(Long id);
	
	/**通过id得到微信支付对象*/
	public PaymentWebWx getById(Long id);
	
	/**通过支付的订单编号获得微信支付对象*/
	public PaymentWebWx getByOrderNumber(String orderNumber);
	
	/**通过订单表订单编号得到微信支付对象*/
	public PaymentWebWx getByMemberIdAndOrderCode(Long memberId,String orderCode);
	
	
	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);
}
