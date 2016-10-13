package com.fushionbaby.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.order.dao.OrderTraceDao;
import com.fushionbaby.order.model.OrderTrace;
import com.fushionbaby.order.service.OrderTraceService;

/**
 * @author 张明亮
 * 订单收货地址操作
 */
@Service
public class OrderTraceServiceImpl implements OrderTraceService<OrderTrace> {
    @Autowired
	private OrderTraceDao orderTraceDao;

    public void updateByMemberIdAndOrderCode(OrderTrace orderTrace){
    	orderTraceDao.updateByMemberIdAndOrderCode(orderTrace);
    }

    public OrderTrace findByOrderCode(String orderCode){
    	return orderTraceDao.findByOrderCode(orderCode);
    }

    public OrderTrace findByOrderTraceId(Long id){
    	return orderTraceDao.findByOrderTraceId(id);
    }

    public void add(OrderTrace orderTrace){
		orderTraceDao.add(orderTrace);
    }
	
    public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId){
    	orderTraceDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
	}
}
