package com.fushionbaby.payment.service;

import com.fushionbaby.payment.model.PaymentWebUnion;

public interface PaymentWebUnionService <T extends PaymentWebUnion> {
	
	/**添加*/
	public void add(PaymentWebUnion paymentWebUnionStatus);
	
	/**修改*/
	public void updateByMemberIdAndOrderCode(PaymentWebUnion paymentWebUnionStatus);
	
	/**删除*/
	public void deleteById(Long id);
	
	/**通过id得到银联支付对象*/
	public PaymentWebUnion getById(Long id);
	
	/**通过支付的订单编号获得银联支付对象*/
	public PaymentWebUnion getByOrderNumber(String orderNumber);
	
	/**通过会员id和订单编号得到银联支付对象*/
	public PaymentWebUnion getByMemberIdAndOrderCode(Long memberId,String orderCode);
	
	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);
	
}
