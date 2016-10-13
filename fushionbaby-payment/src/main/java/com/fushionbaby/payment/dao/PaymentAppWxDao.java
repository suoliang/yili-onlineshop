package com.fushionbaby.payment.dao;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.payment.model.PaymentAppWx;

/***
 * 手机 -- 微信支付dao
 * @author King 索亮
 *
 */
public interface PaymentAppWxDao {
	
	public void add(PaymentAppWx paymentAppWxStatus);
	
	public void updateByMemberIdAndOrderCode(PaymentAppWx paymentAppWxStatus);
	
	public void deleteById(Long id);
	
	public PaymentAppWx getById(Long id);
	
	public PaymentAppWx getByOrderNumber(String orderNumber);
	
	public PaymentAppWx getByMemberIdAndOrderCode(@Param("memberId")Long memberId,@Param("orderCode")String orderCode);

	public void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);
}
