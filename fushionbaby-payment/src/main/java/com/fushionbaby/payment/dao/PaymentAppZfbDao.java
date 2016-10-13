package com.fushionbaby.payment.dao;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.payment.model.PaymentAppZfb;

/***
 * APP -- 支付宝支付dao
 * @author King 索亮
 *
 */
public interface PaymentAppZfbDao {
	
	public void add(PaymentAppZfb paymentAppZfbStatus);
	
	public void updateByMemberIdAndOrderCode(PaymentAppZfb paymentAppZfbStatus);
	
	public void deleteById(Long id);
	
	public PaymentAppZfb getById(Long id);
	
	public PaymentAppZfb getByOrderNumber(String orderNumber);
	
	public PaymentAppZfb getByMemberIdAndOrderCode(@Param("memberId")Long memberId,@Param("orderCode")String orderCode);
	
	public void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);
}
