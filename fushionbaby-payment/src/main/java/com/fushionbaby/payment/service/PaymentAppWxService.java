package com.fushionbaby.payment.service;

import com.fushionbaby.payment.model.PaymentAppWx;

/***
 * 手机 -- 微信支付Service
 * @author King 索亮
 *
 */
public interface PaymentAppWxService<T extends PaymentAppWx> {
	/**添加*/
	public void add(PaymentAppWx paymentAppWxStatus);
	
	/**修改*/
	public void updateByMemberIdAndOrderCode(PaymentAppWx paymentAppWxStatus);
	
	/**删除*/
	public void deleteById(Long id);
	
	/**通过id得到微信支付对象*/
	public PaymentAppWx getById(Long id);
	
	/**通过支付的订单编号获得微信支付对象*/
	public PaymentAppWx getByOrderNumber(String orderNumber);
	
	/**通过会员id和订单编号得到微信支付对象*/
	public PaymentAppWx getByMemberIdAndOrderCode(Long memberId,String orderCode);
	
	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);
}
