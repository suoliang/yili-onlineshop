package com.fushionbaby.core.dao;

import java.util.Map;

import com.fushionbaby.core.model.OrderTransUser;

public interface OrderTransUserDao {

	void addOrderTransUser(OrderTransUser orderTransUser);

	OrderTransUser findByParam(Map<String, Object> paramMap);
}
