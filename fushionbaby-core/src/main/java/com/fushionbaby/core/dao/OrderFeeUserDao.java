package com.fushionbaby.core.dao;

import java.util.Map;

import com.fushionbaby.core.model.OrderFeeUser;

public interface OrderFeeUserDao {

	void addOrderFeeUser(OrderFeeUser orderFeeUser);

	OrderFeeUser findByParam(Map<String, Object> param);
	
	void updateMIdAndOrdCode(OrderFeeUser orderFeeUser);
}
