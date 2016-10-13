package com.fushionbaby.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.order.dao.OrderRemindDeliveryDao;
import com.fushionbaby.order.model.OrderRemindDelivery;
import com.fushionbaby.order.service.OrderRemindDeliveryService;

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
	
	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId){
		orderRemindDeliveryDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
	}
}
