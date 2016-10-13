package com.fushionbaby.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.payment.dao.PaymentWebUnionDao;
import com.fushionbaby.payment.model.PaymentWebUnion;
import com.fushionbaby.payment.service.PaymentWebUnionService;

@Service
public class PaymentWebUnionServiceImpl implements PaymentWebUnionService<PaymentWebUnion> {

	@Autowired
	private PaymentWebUnionDao paymentWebUnionDao;

	public void add(PaymentWebUnion paymentWebUnionStatus) {
		paymentWebUnionDao.add(paymentWebUnionStatus);
	}

	public void updateByMemberIdAndOrderCode(PaymentWebUnion paymentWebUnionStatus) {
		paymentWebUnionDao.updateByMemberIdAndOrderCode(paymentWebUnionStatus);
	}

	public void deleteById(Long id) {
		paymentWebUnionDao.deleteById(id);
	}

	public PaymentWebUnion getById(Long id) {
		return paymentWebUnionDao.getById(id);
	}

	public PaymentWebUnion getByOrderNumber(String orderNumber) {
		return paymentWebUnionDao.getByOrderNumber(orderNumber);
	}

	public PaymentWebUnion getByMemberIdAndOrderCode(Long memberId,String orderCode) {
		return paymentWebUnionDao.getByMemberIdAndOrderCode(memberId, orderCode);
	}
	public void deleteByOrderCodeAndMemberId(String orderCode, Long memberId) {
		paymentWebUnionDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
		
	}
}
