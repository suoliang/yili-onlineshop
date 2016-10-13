package com.fushionbaby.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.core.dao.OrderEpointDao;
import com.fushionbaby.core.model.OrderEpoint;
import com.fushionbaby.core.service.OrderEpointService;

@Service
public class OrderEpointServiceImpl implements OrderEpointService<OrderEpoint> {
	@Autowired
	private OrderEpointDao orderEpointDao; 
	
	public void add(OrderEpoint integral) {
		orderEpointDao.add(integral);
	}

	public List<OrderEpoint> findByMemberId(Long memberId) {
		return orderEpointDao.findByMemberId(memberId);
	}

}
