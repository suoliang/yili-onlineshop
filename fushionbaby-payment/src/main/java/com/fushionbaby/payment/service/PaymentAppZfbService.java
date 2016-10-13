package com.fushionbaby.payment.service;

import com.fushionbaby.payment.model.PaymentAppZfb;

/***
 * 手机 -- 支付宝支付Service
 * @author King 索亮
 *
 */
public interface PaymentAppZfbService<T extends PaymentAppZfb> {
	/**添加*/
	public void add(PaymentAppZfb paymentAppZfbStatus);
	
	/**修改*/
	public void updateByMemberIdAndOrderCode(PaymentAppZfb paymentAppZfbStatus);
	
	/**删除*/
	public void deleteById(Long id);
	
	/**通过id得到支付宝支付对象*/
	public PaymentAppZfb getById(Long id);
	
	/**通过支付的订单编号获得支付宝支付对象*/
	public PaymentAppZfb getByOrderNumber(String orderNumber);
	
	/**通过会员id和订单编号得到支付宝支付对象*/
	public PaymentAppZfb getByMemberIdAndOrderCode(Long memberId,String orderCode);
	
	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);
}
