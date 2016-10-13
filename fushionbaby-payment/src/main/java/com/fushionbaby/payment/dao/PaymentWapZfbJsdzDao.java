package com.fushionbaby.payment.dao;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.payment.model.PaymentWapZfbJsdz;

public interface PaymentWapZfbJsdzDao {

	void add(PaymentWapZfbJsdz paymentWapZfbJsdz);
	
	void updateByMemberIdAndOrderCode(PaymentWapZfbJsdz paymentWapZfbJsdz);
	
	PaymentWapZfbJsdz queryByMemberIdAndOrderCode(@Param("memberId")Long memberId,@Param("orderCode")String orderCode);
	
	PaymentWapZfbJsdz queryByOrderNumber(String orderNumber);

	void deleteById(Long id);

	public void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);
}
