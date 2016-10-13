package com.fushionbaby.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.payment.dao.PaymentAppUnionDao;
import com.fushionbaby.payment.model.PaymentAppUnion;
import com.fushionbaby.payment.service.PaymentAppUnionService;
/**
 * 
 * @author King 索亮
 *
 */
@Service
public class PaymentAppUnionServiceImpl implements PaymentAppUnionService<PaymentAppUnion>{

	@Autowired
	private PaymentAppUnionDao paymentAppUnionStatusDao;
	
	public void add(PaymentAppUnion paymentAppUnionStatus) {
		paymentAppUnionStatusDao.add(paymentAppUnionStatus);
	}

	public void updateByMemberIdAndOrderCode(PaymentAppUnion paymentAppUnionStatus) {
		paymentAppUnionStatusDao.updateByMemberIdAndOrderCode(paymentAppUnionStatus);
	}

	public void deleteById(Long id) {
		paymentAppUnionStatusDao.deleteById(id);
	}

	public PaymentAppUnion getById(Long id) {
		return paymentAppUnionStatusDao.getById(id);
	}

	public PaymentAppUnion getByOrderNumber(String orderNumber) {
		return paymentAppUnionStatusDao.getByOrderNumber(orderNumber);
	}

	public PaymentAppUnion getByMemberIdAndOrderCode(Long memberId,String orderCode) {
		return paymentAppUnionStatusDao.getByMemberIdAndOrderCode(memberId,orderCode);
	}

	public void deleteByOrderCodeAndMemberId(String orderCode, Long memberId) {
		paymentAppUnionStatusDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
		
	}
}
