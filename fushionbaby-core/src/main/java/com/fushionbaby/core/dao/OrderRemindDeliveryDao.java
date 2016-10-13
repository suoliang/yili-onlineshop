package com.fushionbaby.core.dao;


import org.apache.ibatis.annotations.Param;

import com.fushionbaby.core.model.OrderRemindDelivery;


public interface OrderRemindDeliveryDao {
	
	void add(OrderRemindDelivery orderRemindDelivery);
	
    void updateByMemberIdAndOrderCode(OrderRemindDelivery orderRemindDelivery);

    OrderRemindDelivery findByMemberIdAndOrderCode(@Param("memberId")Long memberId,@Param("orderCode")String orderCode);
	

}