package com.fushionbaby.payment.dao;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.payment.model.PaymentWebUnion;
/**
 * 
 * @description WEB银联支付dao
 * @author 索亮
 * @date 2015年8月25日上午8:58:27
 */
public interface PaymentWebUnionDao {
	
	public void add(PaymentWebUnion paymentWebUnionStatus);
	
	public void updateByMemberIdAndOrderCode(PaymentWebUnion paymentWebUnionStatus);
	
	public void deleteById(Long id);
	
	public PaymentWebUnion getById(Long id);
	
	public PaymentWebUnion getByOrderNumber(String orderNumber);
	
	public PaymentWebUnion getByMemberIdAndOrderCode(@Param("memberId")Long memberId,@Param("orderCode")String orderCode);
	
	public void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);

}
