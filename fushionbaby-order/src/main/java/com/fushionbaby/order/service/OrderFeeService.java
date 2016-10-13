package com.fushionbaby.order.service;
import com.fushionbaby.order.model.OrderFee;

public interface OrderFeeService <T extends OrderFee>{

    OrderFee findByOrderCode(String orderCode);

    OrderFee findByOrderFeeId(Long id);
    
    OrderFee findByMemberIdAndOrderCode(Long memberId, String orderCode);
    /** 用于测试 */
	void add(OrderFee orderFee);
	
	void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);
}
