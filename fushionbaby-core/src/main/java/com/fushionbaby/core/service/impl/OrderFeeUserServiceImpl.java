package com.fushionbaby.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.core.dao.OrderFeeUserDao;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.service.OrderFeeUserService;

/**
 * 订单费用操作
 * @author Leon
 *
 */
@Service
public class OrderFeeUserServiceImpl implements OrderFeeUserService<OrderFeeUser> {

	@Autowired
	private OrderFeeUserDao orderFeeUserDao;

	public void addOrderFeeUser(OrderFeeUser orderFeeUser) {
		orderFeeUserDao.addOrderFeeUser(orderFeeUser);
	}

	public OrderFeeUser findByMIdAndOrdCode(Long memberId,String orderCode) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("orderCode", orderCode);
		return orderFeeUserDao.findByParam(param);
	}

	public OrderFeeUser findByParam(Map<String, Object> map) {
		return orderFeeUserDao.findByParam(map);
	}

	public void updateMIdAndOrdCode(OrderFeeUser orderFeeUser) {
		orderFeeUserDao.updateMIdAndOrdCode(orderFeeUser);
	}

}