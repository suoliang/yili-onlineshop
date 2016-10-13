package com.fushionbaby.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.payment.dao.PaymentAppAlabaoDao;
import com.fushionbaby.payment.model.PaymentAppAlabao;
import com.fushionbaby.payment.service.PaymentAppAlabaoService;
/**
 * 
 * @author King 索亮
 *
 */
@Service
public class PaymentAppAlabaoServiceImpl implements PaymentAppAlabaoService<PaymentAppAlabao>{

	@Autowired
	private PaymentAppAlabaoDao paymentAppAlabaoDao;
	
	public void add(PaymentAppAlabao paymentAppAlabao) {
		paymentAppAlabaoDao.add(paymentAppAlabao);
	}

	public void updateByMemberIdAndOrderCode(PaymentAppAlabao paymentAppAlabao) {
		paymentAppAlabaoDao.updateByMemberIdAndOrderCode(paymentAppAlabao);
	}

	public void deleteById(Long id) {
		paymentAppAlabaoDao.deleteById(id);
	}

	public PaymentAppAlabao getById(Long id) {
		return paymentAppAlabaoDao.getById(id);
	}

	public PaymentAppAlabao getByMemberIdAndOrderCode(Long memberId,String orderCode) {
		return paymentAppAlabaoDao.getByMemberIdAndOrderCode(memberId,orderCode);
	}

	public void deleteByOrderCodeAndMemberId(String orderCode, Long memberId) {
		paymentAppAlabaoDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
		
	}
	
}
