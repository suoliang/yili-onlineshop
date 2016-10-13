package com.fushionbaby.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.payment.dao.PaymentWebZfbJsdzDao;
import com.fushionbaby.payment.model.PaymentWebZfbJsdz;
import com.fushionbaby.payment.service.PaymentWebZfbJsdzService;

@Service
public class PaymentWebZfbJsdzServiceImpl implements PaymentWebZfbJsdzService<PaymentWebZfbJsdz>{

	@Autowired
	private PaymentWebZfbJsdzDao paymentWebZfbJsdzDao;
	
	public void add(PaymentWebZfbJsdz paymentWebZfbJsdz) {
		paymentWebZfbJsdzDao.add(paymentWebZfbJsdz);
	}

	public void updateByMemberIdAndOrderCode(PaymentWebZfbJsdz paymentWebZfbJsdz) {
		paymentWebZfbJsdzDao.updateByMemberIdAndOrderCode(paymentWebZfbJsdz);
	}

	public PaymentWebZfbJsdz queryByMemberIdAndOrderCode(Long memberId,String orderCode) {
		return paymentWebZfbJsdzDao.queryByMemberIdAndOrderCode(memberId,orderCode);
	}

	public PaymentWebZfbJsdz queryByOrderNumber(String orderNumber) {
		return paymentWebZfbJsdzDao.queryByOrderNumber(orderNumber);
	}

	public void deleteById(Long id) {
		paymentWebZfbJsdzDao.deleteById(id);		
	}
	
	public void deleteByOrderCodeAndMemberId(String orderCode, Long memberId) {
		paymentWebZfbJsdzDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
		
	}

	public PaymentWebZfbJsdz queryByOrderCode(String orderCode) {
		
		return paymentWebZfbJsdzDao.queryByOrderCode(orderCode);
	}

}
