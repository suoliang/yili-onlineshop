package com.fushionbaby.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.payment.dao.PaymentWebWxDao;
import com.fushionbaby.payment.model.PaymentWebWx;
import com.fushionbaby.payment.service.PaymentWebWxService;
/**
 * @description web -- 微信支付实现
 * @author 索亮
 * @date 2015年8月27日下午3:52:57
 */
@Service
public class PaymentWebWxServiceImpl implements PaymentWebWxService<PaymentWebWx>{

	@Autowired
	private PaymentWebWxDao paymentWebWxDao;

	public void add(PaymentWebWx paymentWebWx) {
		paymentWebWxDao.add(paymentWebWx);
	}

	public void updateByMemberIdAndOrderCode(PaymentWebWx paymentWebWx) {
		paymentWebWxDao.updateByMemberIdAndOrderCode(paymentWebWx);
	}

	public void deleteById(Long id) {
		paymentWebWxDao.deleteById(id);
	}

	public PaymentWebWx getById(Long id) {
		return paymentWebWxDao.getById(id);
	}

	public PaymentWebWx getByOrderNumber(String orderNumber) {
		return paymentWebWxDao.getByOrderNumber(orderNumber);
	}

	public PaymentWebWx getByMemberIdAndOrderCode(Long memberId,String orderCode) {
		return paymentWebWxDao.getByMemberIdAndOrderCode(memberId,orderCode);
	}
	
	public void deleteByOrderCodeAndMemberId(String orderCode, Long memberId) {
		paymentWebWxDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
		
	}
}
