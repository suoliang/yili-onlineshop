package com.fushionbaby.payment.service;

import com.fushionbaby.payment.model.PaymentAppUnion;

/***
 * 手机 -- 银联支付Service
 * @author King 索亮
 *
 */
public interface PaymentAppUnionService<T extends PaymentAppUnion> {
	/**添加*/
	public void add(PaymentAppUnion paymentAppUnionStatus);
	
	/**修改*/
	public void updateByMemberIdAndOrderCode(PaymentAppUnion paymentAppUnionStatus);
	
	/**删除*/
	public void deleteById(Long id);
	
	/**通过id得到银联支付对象*/
	public PaymentAppUnion getById(Long id);
	
	/**通过支付的订单编号获得银联支付对象*/
	public PaymentAppUnion getByOrderNumber(String orderNumber);
	
	/**通过会员id和订单编号得到银联支付对象*/
	public PaymentAppUnion getByMemberIdAndOrderCode(Long memberId,String orderCode);
	
	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);
}
