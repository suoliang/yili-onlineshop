package com.fushionbaby.payment.service;

import com.fushionbaby.payment.model.PaymentWapZfbJsdz;

public interface PaymentWapZfbJsdzService<T extends PaymentWapZfbJsdz> {

	void add(PaymentWapZfbJsdz paymentWapZfbJsdz);
	
	void updateByMemberIdAndOrderCode(PaymentWapZfbJsdz paymentWapZfbJsdz);
	
	PaymentWapZfbJsdz queryByMemberIdAndOrderCode(Long memberId,String orderCode);
	
	PaymentWapZfbJsdz queryByOrderNumber(String orderNumber);
	
	void deleteById(Long id);
	
	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);
}
