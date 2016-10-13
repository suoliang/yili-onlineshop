package com.fushionbaby.core.service;

import com.fushionbaby.core.model.OrderTraceUser;

public interface OrderTraceUserService<T extends OrderTraceUser> {
	/** 添加 */
	void addOrderTraceUser(OrderTraceUser orderTraceUser);
	/** 修改订单状态 */
	void updatetTraceStatus(OrderTraceUser orderTraceUser);
}
