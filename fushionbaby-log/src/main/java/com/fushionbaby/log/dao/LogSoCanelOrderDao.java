package com.fushionbaby.log.dao;

import com.fushionbaby.log.model.LogSoCancelOrder;

/**
 * 取消订单日志
 * @author glc
 *
 */
public interface LogSoCanelOrderDao {
	
	void add(LogSoCancelOrder log);
	
	void deleteById(Long id);
	
}
   