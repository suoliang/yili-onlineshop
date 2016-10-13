package com.fushionbaby.log.service;

import com.fushionbaby.log.model.LogMemberMoney;

/**
 * @author 张明亮
 */
public interface LogMemberMoneyService {
	
	void add(LogMemberMoney log);
	
	void deleteById(Long id);
}
