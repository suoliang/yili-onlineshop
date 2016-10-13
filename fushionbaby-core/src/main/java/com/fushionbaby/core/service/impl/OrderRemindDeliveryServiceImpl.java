package com.fushionbaby.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.core.dao.OrderRemindDeliveryDao;
import com.fushionbaby.core.model.OrderRemindDelivery;
import com.fushionbaby.core.service.OrderRemindDeliveryService;

/**
 * @author 张明亮
 * 订单收货地址操作
 */
@Service
public class OrderRemindDeliveryServiceImpl implements OrderRemindDeliveryService<OrderRemindDelivery> {
    @Autowired
	private OrderRemindDeliveryDao orderRemindDeliveryDao;

    public void updateByMemberIdAndOrderCode(OrderRemindDelivery orderRemindDelivery){
    	orderRemindDeliveryDao.updateByMemberIdAndOrderCode(orderRemindDelivery);
    }

    public void add(OrderRemindDelivery orderRemindDelivery){
		orderRemindDeliveryDao.add(orderRemindDelivery);
    }

	public OrderRemindDelivery findByMemberIdAndOrderCode(Long memberId,String orderCode) {
		return orderRemindDeliveryDao.findByMemberIdAndOrderCode(memberId, orderCode);
	}
	
}
