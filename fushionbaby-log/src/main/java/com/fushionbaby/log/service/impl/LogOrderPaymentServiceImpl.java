package com.fushionbaby.log.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.log.dao.LogOrderPaymentDao;
import com.fushionbaby.log.model.LogOrderPayment;
import com.fushionbaby.log.service.LogOrderPaymentService;
/**
 * 
 * @author King suoliang
 *
 */
@Service
public class LogOrderPaymentServiceImpl implements LogOrderPaymentService {

	@Autowired
	private LogOrderPaymentDao logOrderPaymentDao;
	
	public void add(LogOrderPayment logOrderPayment) throws DataAccessException {
		logOrderPaymentDao.add(logOrderPayment);
	}

}
