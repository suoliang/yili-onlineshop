package com.fushionbaby.payment.dao;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.payment.model.PaymentWebZfbJsdz;

public interface PaymentWebZfbJsdzDao {

	void add(PaymentWebZfbJsdz paymentWebZfbJsdz);
	
	void updateByMemberIdAndOrderCode(PaymentWebZfbJsdz paymentWebZfbJsdz);
	
	PaymentWebZfbJsdz queryByMemberIdAndOrderCode(@Param("memberId")Long memberId,@Param("orderCode")String orderCode);
	
	PaymentWebZfbJsdz queryByOrderNumber(String orderNumber);

	void deleteById(Long id);

	public void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);

	PaymentWebZfbJsdz queryByOrderCode(String orderCode);
}
