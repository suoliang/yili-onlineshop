package com.fushionbaby.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.log.dao.LogSoSysCancelOrderDao;
import com.fushionbaby.log.model.LogSoAdminCancelOrder;
import com.fushionbaby.log.service.LogSoSysCancelOrderService;

/**
 * 
 * @author cyla
 * <p>完成系统取消订单功能</p>
 *
 */
@Service
public class LogSoSysCancelOrderServiceImpl implements
		LogSoSysCancelOrderService {

	@Autowired
	private LogSoSysCancelOrderDao logSoSysCancelOrderDao;
	
	public void add(LogSoAdminCancelOrder log) {
		logSoSysCancelOrderDao.add(log);
	}

}
