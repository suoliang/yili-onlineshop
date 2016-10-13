package com.fushionbaby.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.log.dao.LogBizExceptionDao;
import com.fushionbaby.log.model.LogBizException;
import com.fushionbaby.log.service.LogBizExceptionService;
@Service
public class LogBizExceptionServiceImpl implements LogBizExceptionService{
	@Autowired
	private LogBizExceptionDao logBizExceptionDao;
	
	public void add(LogBizException logBizException) {
		logBizExceptionDao.add(logBizException);
	}

}
