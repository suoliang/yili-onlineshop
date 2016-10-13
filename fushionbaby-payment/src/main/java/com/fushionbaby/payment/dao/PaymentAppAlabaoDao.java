package com.fushionbaby.payment.dao;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.payment.model.PaymentAppAlabao;

/***
 * @description 如意宝支付DAO
 * @author 索亮
 * @date 2015年10月13日上午11:30:39
 */
public interface PaymentAppAlabaoDao {
	
	public void add(PaymentAppAlabao paymentAppAlabao);
	
	public void updateByMemberIdAndOrderCode(PaymentAppAlabao paymentAppAlabao);
	
	public void deleteById(Long id);
	
	public PaymentAppAlabao getById(Long id);
	
	public PaymentAppAlabao getByMemberIdAndOrderCode(@Param("memberId")Long memberId,@Param("orderCode")String orderCode);
	
	public void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);
}
