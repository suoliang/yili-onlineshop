package com.fushionbaby.order.dao;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.order.model.OrderTrace;


public interface OrderTraceDao {

    void updateByMemberIdAndOrderCode(OrderTrace orderTrace);

    OrderTrace findByOrderCode(String orderCode);

    OrderTrace findByOrderTraceId(Long id);
    /** 用于测试 */
	void add(OrderTrace orderTrace);
	
	void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);
}