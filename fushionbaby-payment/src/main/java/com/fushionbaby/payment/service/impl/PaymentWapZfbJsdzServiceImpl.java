package com.fushionbaby.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.payment.dao.PaymentWapZfbJsdzDao;
import com.fushionbaby.payment.model.PaymentWapZfbJsdz;
import com.fushionbaby.payment.service.PaymentWapZfbJsdzService;

@Service
public class PaymentWapZfbJsdzServiceImpl implements PaymentWapZfbJsdzService<PaymentWapZfbJsdz>{

	@Autowired
	private PaymentWapZfbJsdzDao paymentWapZfbJsdzDao;
	
	public void add(PaymentWapZfbJsdz paymentWapZfbJsdz) {
		paymentWapZfbJsdzDao.add(paymentWapZfbJsdz);
	}

	public void updateByMemberIdAndOrderCode(PaymentWapZfbJsdz paymentWapZfbJsdz) {
		paymentWapZfbJsdzDao.updateByMemberIdAndOrderCode(paymentWapZfbJsdz);
	}

	public PaymentWapZfbJsdz queryByMemberIdAndOrderCode(Long memberId,String orderCode) {
		return paymentWapZfbJsdzDao.queryByMemberIdAndOrderCode(memberId,orderCode);
	}

	public PaymentWapZfbJsdz queryByOrderNumber(String orderNumber) {
		return paymentWapZfbJsdzDao.queryByOrderNumber(orderNumber);
	}

	public void deleteById(Long id) {
		paymentWapZfbJsdzDao.deleteById(id);		
	}

	public void deleteByOrderCodeAndMemberId(String orderCode, Long memberId) {
		paymentWapZfbJsdzDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
		
	}
}
