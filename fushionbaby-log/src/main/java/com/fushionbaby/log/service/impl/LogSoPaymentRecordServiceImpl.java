package com.fushionbaby.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.log.dao.LogSoPaymentRecordDao;
import com.fushionbaby.log.model.LogSoPaymentRecord;
import com.fushionbaby.log.service.LogSoPaymentRecordService;
/***
 * 
 * @author xupeijun
 *
 */
@Service
public class LogSoPaymentRecordServiceImpl implements LogSoPaymentRecordService {

	@Autowired
	private LogSoPaymentRecordDao logSoPaymentRecordDao;
	
	public void add(LogSoPaymentRecord log) throws DataAccessException {
		logSoPaymentRecordDao.add(log);
	}

}
