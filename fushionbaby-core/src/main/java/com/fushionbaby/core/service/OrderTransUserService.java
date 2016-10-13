package com.fushionbaby.core.service;

import java.util.Map;

import com.fushionbaby.core.model.OrderTransUser;

public interface OrderTransUserService<T extends OrderTransUser> {
	/** 添加 */
	void addOrderTransUser(OrderTransUser orderTransUser);
	/**  */
	OrderTransUser findByParam(Map<String, Object> paramMap);
}
