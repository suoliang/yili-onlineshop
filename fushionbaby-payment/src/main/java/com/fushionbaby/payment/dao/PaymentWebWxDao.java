package com.fushionbaby.payment.dao;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.payment.model.PaymentWebWx;

/***
 * @description WEB微信支付dao
 * @author 索亮
 * @date 2015年8月27日下午3:47:36
 */
public interface PaymentWebWxDao {
	
	public void add(PaymentWebWx paymentWebWx);
	
	public void updateByMemberIdAndOrderCode(PaymentWebWx paymentWebWx);
	
	public void deleteById(Long id);
	
	public PaymentWebWx getById(Long id);
	
	public PaymentWebWx getByOrderNumber(String orderNumber);
	
	public PaymentWebWx getByMemberIdAndOrderCode(@Param("memberId")Long memberId,@Param("orderCode")String orderCode);
	
	public void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);
}
