package com.fushionbaby.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.payment.dao.PaymentAppZfbDao;
import com.fushionbaby.payment.model.PaymentAppZfb;
import com.fushionbaby.payment.service.PaymentAppZfbService;
/**
 * 
 * @author King 索亮
 *
 */
@Service
public class PaymentAppZfbServiceImpl implements PaymentAppZfbService<PaymentAppZfb>{

	@Autowired
	private PaymentAppZfbDao paymentAppZfbDao;
	
	public void add(PaymentAppZfb paymentAppZfbStatus) {
		paymentAppZfbDao.add(paymentAppZfbStatus);
	}

	public void updateByMemberIdAndOrderCode(PaymentAppZfb paymentAppZfbStatus) {
		paymentAppZfbDao.updateByMemberIdAndOrderCode(paymentAppZfbStatus);
	}

	public void deleteById(Long id) {
		paymentAppZfbDao.deleteById(id);
	}

	public PaymentAppZfb getById(Long id) {
		return paymentAppZfbDao.getById(id);
	}

	public PaymentAppZfb getByOrderNumber(String orderNumber) {
		return paymentAppZfbDao.getByOrderNumber(orderNumber);
	}

	public PaymentAppZfb getByMemberIdAndOrderCode(Long memberId,String orderCode) {
		return paymentAppZfbDao.getByMemberIdAndOrderCode(memberId,orderCode);
	}

	public void deleteByOrderCodeAndMemberId(String orderCode, Long memberId) {
		paymentAppZfbDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
		
	}
}
