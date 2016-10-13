package com.fushionbaby.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fushionbaby.core.dao.OrderTraceUserDao;
import com.fushionbaby.core.model.OrderTraceUser;
import com.fushionbaby.core.service.OrderTraceUserService;

/**
 * 订单跟踪
 * @author Leon
 *
 */
@Service
public class OrderTraceUserServiceImpl implements OrderTraceUserService<OrderTraceUser> {

	@Autowired
	private OrderTraceUserDao orderTraceUserDao;

	public void addOrderTraceUser(OrderTraceUser orderTraceUser) {
		orderTraceUserDao.addOrderTraceUser(orderTraceUser);
	}

	public void updatetTraceStatus(OrderTraceUser orderTraceUser) {
		orderTraceUserDao.updatetTraceStatus(orderTraceUser);
	}
	
}