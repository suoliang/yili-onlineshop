package com.fushionbaby.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.payment.dao.PaymentWebZfbDbjyDao;
import com.fushionbaby.payment.model.PaymentWebZfbDbjy;
import com.fushionbaby.payment.service.PaymentWebZfbDbjyService;

@Service
public class PaymentWebZfbDbjyServiceImpl implements PaymentWebZfbDbjyService{

	@Autowired
	private PaymentWebZfbDbjyDao paymentWebZfbDbjyDao;
	
	public void add(PaymentWebZfbDbjy paymentWebZfbDbjy) {
		paymentWebZfbDbjyDao.add(paymentWebZfbDbjy);
	}

	public void update(PaymentWebZfbDbjy paymentWebZfbDbjy) {
		paymentWebZfbDbjyDao.update(paymentWebZfbDbjy);
	}

	public void updateByOrderNumberAndMemberId(PaymentWebZfbDbjy paymentWebZfbDbjy) {
		paymentWebZfbDbjyDao.updateByOrderNumberAndMemberId(paymentWebZfbDbjy);
	}

	public PaymentWebZfbDbjy queryBySoCode(String soCode) {
		return paymentWebZfbDbjyDao.queryBySoCode(soCode);
	}

	public PaymentWebZfbDbjy queryByOrderNumber(String orderNumber) {
		return paymentWebZfbDbjyDao.queryByOrderNumber(orderNumber);
	}

	public void deleteById(Long id) {
		paymentWebZfbDbjyDao.deleteById(id);		
	}

	public void deleteByOrderCodeAndMemberId(String orderCode, Long memberId) {
		paymentWebZfbDbjyDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
		
	}
}
