package com.fushionbaby.payment.dao;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.payment.model.PaymentAppUnion;

/***
 * 手机 -- 银联支付dao
 * @author King 索亮
 *
 */
public interface PaymentAppUnionDao {
	
	public void add(PaymentAppUnion paymentAppUnionStatus);
	
	public void updateByMemberIdAndOrderCode(PaymentAppUnion paymentAppUnionStatus);
	
	public void deleteById(Long id);
	
	public PaymentAppUnion getById(Long id);
	
	public PaymentAppUnion getByOrderNumber(String orderNumber);
	
	public PaymentAppUnion getByMemberIdAndOrderCode(@Param("memberId")Long memberId,@Param("orderCode")String orderCode);

	public void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);
}
