/**
 * 
 */
package com.fushionbaby.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.log.dao.LogSendGoodsConfirmDao;
import com.fushionbaby.log.model.LogSendGoodsConfirm;
import com.fushionbaby.log.service.LogSendGoodsConfirmService;

/**
 * @author mengshaobo
 * 
 */
@Service
public class LogSendGoodsConfirmServiceImpl implements LogSendGoodsConfirmService {

	@Autowired
	private LogSendGoodsConfirmDao logSendGoodsConfirmDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.log.service.LogSendGoodsConfirmService#add(com.fushionbaby
	 * .log.model.LogSendGoodsConfirm)
	 */
	public void add(LogSendGoodsConfirm logSendGoodsConfirm) {
		logSendGoodsConfirmDao.add(logSendGoodsConfirm);
	}

}
