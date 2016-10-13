package com.fushionbaby.order.dao;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.order.model.OrderFee;


public interface OrderFeeDao {

    OrderFee findByOrderCode(String orderCode);

    OrderFee findByOrderFeeId(Long id);
    
    OrderFee findByMemberIdAndOrderCode(@Param("memberId")Long memberId, @Param("orderCode")String orderCode);
    /** 用于测试 */
	void add(OrderFee orderFee);
	
	void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);
}