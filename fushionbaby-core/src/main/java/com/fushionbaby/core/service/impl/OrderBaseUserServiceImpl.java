package com.fushionbaby.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.core.dao.OrderBaseUserDao;
import com.fushionbaby.core.model.OrderBaseUser;
import com.fushionbaby.core.service.OrderBaseUserService;

@Service
public class OrderBaseUserServiceImpl implements OrderBaseUserService<OrderBaseUser> {
	@Autowired
	private OrderBaseUserDao orderBaseUserDao;

	public void addOrderBase(OrderBaseUser orderBaseUser) {
		orderBaseUserDao.addOrderBase(orderBaseUser);
	}

	public void updateOrderStatus(OrderBaseUser orderBaseUser) {
		orderBaseUserDao.updateOrderStatus(orderBaseUser);
	}

	public List<OrderBaseUser> getListPage(Map<String, Object> map) {
		return orderBaseUserDao.getListPage(map);
	}

	public Integer getTotal(Map<String, Object> map) {
		return orderBaseUserDao.getTotal(map);
	}

	public List<OrderBaseUser> getWaitingListByMemberId(Long memerId) {
		return orderBaseUserDao.getWaitingListByMemberId(memerId);
	}

	public OrderBaseUser findObjectByMemIdAndOrdCode(Long memberId,
			String orderCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("orderCode", orderCode);
		return orderBaseUserDao.findObjectByMemIdAndOrdCode(map);
	}
}
