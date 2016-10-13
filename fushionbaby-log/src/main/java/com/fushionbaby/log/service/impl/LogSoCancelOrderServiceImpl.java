package com.fushionbaby.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.log.dao.LogSoCanelOrderDao;
import com.fushionbaby.log.model.LogSoCancelOrder;
import com.fushionbaby.log.service.LogSoCancelOrderService;
/**
 * 
 * @author glc
 *
 */
@Service
public class LogSoCancelOrderServiceImpl implements LogSoCancelOrderService {

	@Autowired
	private LogSoCanelOrderDao logSoCanelOrderDao;
	
	public void add(LogSoCancelOrder log) throws DataAccessException {
		logSoCanelOrderDao.add(log);
	}

	public void deleteById(Long id) throws DataAccessException {
		logSoCanelOrderDao.deleteById(id);
	}

}
