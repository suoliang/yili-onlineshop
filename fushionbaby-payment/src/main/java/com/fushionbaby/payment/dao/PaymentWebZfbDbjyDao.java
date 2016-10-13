package com.fushionbaby.payment.dao;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.payment.model.PaymentWebZfbDbjy;

public interface PaymentWebZfbDbjyDao {

	void add(PaymentWebZfbDbjy paymentWebZfbDbjy);
	
	void update(PaymentWebZfbDbjy paymentWebZfbDbjy);
	
	void updateByOrderNumberAndMemberId(PaymentWebZfbDbjy paymentWebZfbDbjy);
	
	PaymentWebZfbDbjy queryBySoCode(String soCode);
	
	PaymentWebZfbDbjy queryByOrderNumber(String orderNumber);

	void deleteById(Long id);
	
	public void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);
	
}
