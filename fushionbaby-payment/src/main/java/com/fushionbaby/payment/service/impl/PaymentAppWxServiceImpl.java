package com.fushionbaby.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.payment.dao.PaymentAppWxDao;
import com.fushionbaby.payment.model.PaymentAppWx;
import com.fushionbaby.payment.service.PaymentAppWxService;
/**
 * 
 * @author King 索亮
 *
 */
@Service
public class PaymentAppWxServiceImpl implements PaymentAppWxService<PaymentAppWx>{

	@Autowired
	private PaymentAppWxDao paymentAppWxStatusDao;
	
	public void add(PaymentAppWx paymentAppWxStatus) {
		paymentAppWxStatusDao.add(paymentAppWxStatus);
	}

	public void updateByMemberIdAndOrderCode(PaymentAppWx paymentAppWxStatus) {
		paymentAppWxStatusDao.updateByMemberIdAndOrderCode(paymentAppWxStatus);
	}

	public void deleteById(Long id) {
		paymentAppWxStatusDao.deleteById(id);
	}

	public PaymentAppWx getById(Long id) {
		return paymentAppWxStatusDao.getById(id);
	}

	public PaymentAppWx getByOrderNumber(String orderNumber) {
		return paymentAppWxStatusDao.getByOrderNumber(orderNumber);
	}

	public PaymentAppWx getByMemberIdAndOrderCode(Long memberId,String orderCode) {
		return paymentAppWxStatusDao.getByMemberIdAndOrderCode(memberId,orderCode);
	}

	public void deleteByOrderCodeAndMemberId(String orderCode, Long memberId) {
		paymentAppWxStatusDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
		
	}
}
