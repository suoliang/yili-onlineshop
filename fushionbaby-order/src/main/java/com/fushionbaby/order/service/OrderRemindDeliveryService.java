package com.fushionbaby.order.service;

import com.fushionbaby.order.model.OrderRemindDelivery;

public interface OrderRemindDeliveryService <T extends OrderRemindDelivery>{
	
	void add(OrderRemindDelivery orderRemindDelivery);
	
    void updateByMemberIdAndOrderCode(OrderRemindDelivery orderRemindDelivery);

    OrderRemindDelivery findByMemberIdAndOrderCode(Long memberId, String orderCode);
	
    void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);

}
