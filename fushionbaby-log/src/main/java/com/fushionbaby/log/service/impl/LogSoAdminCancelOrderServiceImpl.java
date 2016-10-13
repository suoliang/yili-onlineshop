package com.fushionbaby.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.log.dao.LogSoAdminCancelOrderDao;
import com.fushionbaby.log.model.LogSoAdminCancelOrder;
import com.fushionbaby.log.service.LogSoAdminCancelOrderService;

/**
 * 
 * @author cyla
 * <p>完成系统取消订单功能</p>
 *
 */
@Service
public class LogSoAdminCancelOrderServiceImpl implements
		LogSoAdminCancelOrderService {

	@Autowired
	private LogSoAdminCancelOrderDao logSoAdminCancelOrderDao;
	
	public void add(LogSoAdminCancelOrder log) {
		logSoAdminCancelOrderDao.add(log);
	}

}
