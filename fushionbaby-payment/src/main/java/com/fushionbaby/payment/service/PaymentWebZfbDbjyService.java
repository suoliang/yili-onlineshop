package com.fushionbaby.payment.service;

import com.fushionbaby.payment.model.PaymentWebZfbDbjy;

public interface PaymentWebZfbDbjyService {


	void add(PaymentWebZfbDbjy paymentWebZfbDbjy);
	
	void update(PaymentWebZfbDbjy paymentWebZfbDbjy);
	
	void updateByOrderNumberAndMemberId(PaymentWebZfbDbjy paymentWebZfbDbjy);
	
	PaymentWebZfbDbjy queryBySoCode(String soCode);
	
	PaymentWebZfbDbjy queryByOrderNumber(String orderNumber);
	
	void deleteById(Long id);
	
	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);
}
