package com.fushionbaby.log.dao;

import com.fushionbaby.log.model.LogMemberMoney;

/**
 * @author 张明亮
 */
public interface LogMemberMoneyDao {
	
	void add(LogMemberMoney log);
	
	void deleteById(Long id);
}