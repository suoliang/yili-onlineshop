package com.fushionbaby.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.log.dao.LogMemberMoneyDao;
import com.fushionbaby.log.model.LogMemberMoney;
import com.fushionbaby.log.service.LogMemberMoneyService;

/**
 * @author 张明亮
 */
@Service
public class LogMemberMoneyServiceImpl implements LogMemberMoneyService {

	@Autowired
	private LogMemberMoneyDao logMemberMoneyDao;
	
	public void add(LogMemberMoney log) {
		logMemberMoneyDao.add(log);
	}

	public void deleteById(Long id) {
		logMemberMoneyDao.deleteById(id);
	}

}
