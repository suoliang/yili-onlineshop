package com.fushionbaby.log.service;

import com.fushionbaby.log.model.LogSoCancelOrder;
/**
 * 
 * @author glc
 *
 * @param <T>
 */
public interface LogSoCancelOrderService {
    
	void add(LogSoCancelOrder log);
	
	void deleteById(Long id);
	
}
