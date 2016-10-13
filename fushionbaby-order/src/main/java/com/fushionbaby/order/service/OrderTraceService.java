package com.fushionbaby.order.service;
import com.fushionbaby.order.model.OrderTrace;

public interface OrderTraceService <T extends OrderTrace>{
	
	void updateByMemberIdAndOrderCode(OrderTrace orderTrace);

    OrderTrace findByOrderCode(String orderCode);

    OrderTrace findByOrderTraceId(Long id);
    /** 用于测试 */
	void add(OrderTrace orderTrace);
	
	void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);
}
