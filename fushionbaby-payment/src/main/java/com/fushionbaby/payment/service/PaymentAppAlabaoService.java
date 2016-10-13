package com.fushionbaby.payment.service;

import com.fushionbaby.payment.model.PaymentAppAlabao;

/***
 * 手机 -- 银联支付Service
 * 
 * @author King 索亮
 *
 */
public interface PaymentAppAlabaoService<T extends PaymentAppAlabao> {
	/** 添加 */
	public void add(PaymentAppAlabao paymentAppAlabao);

	/** 修改 */
	public void updateByMemberIdAndOrderCode(PaymentAppAlabao paymentAppAlabao);

	/** 删除 */
	public void deleteById(Long id);

	/** 通过id得到银联支付对象 */
	public PaymentAppAlabao getById(Long id);

	/** 通过会员id和订单编号得到如意宝支付对象 */
	public PaymentAppAlabao getByMemberIdAndOrderCode(Long memberId,
			String orderCode);

	public void deleteByOrderCodeAndMemberId(String orderCode, Long memberId);
}
