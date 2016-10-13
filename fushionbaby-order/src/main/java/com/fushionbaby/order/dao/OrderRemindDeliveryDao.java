package com.fushionbaby.order.dao;


import org.apache.ibatis.annotations.Param;

import com.fushionbaby.order.model.OrderRemindDelivery;


public interface OrderRemindDeliveryDao {
	
	void add(OrderRemindDelivery orderRemindDelivery);
	
    void updateByMemberIdAndOrderCode(OrderRemindDelivery orderRemindDelivery);

    OrderRemindDelivery findByMemberIdAndOrderCode(@Param("memberId")Long memberId,@Param("orderCode")String orderCode);
	
    void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);
}