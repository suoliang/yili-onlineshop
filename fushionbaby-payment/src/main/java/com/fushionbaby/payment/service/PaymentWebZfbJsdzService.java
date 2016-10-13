package com.fushionbaby.payment.service;

import com.fushionbaby.payment.model.PaymentWebZfbJsdz;

public interface PaymentWebZfbJsdzService<T extends PaymentWebZfbJsdz> {

	void add(PaymentWebZfbJsdz paymentWebZfbJsdz);
	
	void updateByMemberIdAndOrderCode(PaymentWebZfbJsdz paymentWebZfbJsdz);
	
	PaymentWebZfbJsdz queryByMemberIdAndOrderCode(Long memberId,String orderCode);
	
	PaymentWebZfbJsdz queryByOrderNumber(String orderNumber);
	
	void deleteById(Long id);
	
	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);

	PaymentWebZfbJsdz queryByOrderCode(String orderCode);
}
