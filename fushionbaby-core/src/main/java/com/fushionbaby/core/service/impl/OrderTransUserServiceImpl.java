package com.fushionbaby.core.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.core.dao.OrderTransUserDao;
import com.fushionbaby.core.model.OrderTransUser;
import com.fushionbaby.core.service.OrderTransUserService;

/**
 * 
 * @author Leon
 *
 */
@Service
public class OrderTransUserServiceImpl implements OrderTransUserService<OrderTransUser> {

	@Autowired
	private OrderTransUserDao orderTransUserDao;

	public void addOrderTransUser(OrderTransUser orderTransUser) {
		orderTransUserDao.addOrderTransUser(orderTransUser);
		
	}

	public OrderTransUser findByParam(Map<String, Object> paramMap) {
		return orderTransUserDao.findByParam(paramMap);
	}
	
	
	
}