/**
 * 
 */
package com.fushionbaby.log.service;

import com.fushionbaby.log.model.LogSendGoodsConfirm;

/**
 * @author mengshaobo
 * 
 */
public interface LogSendGoodsConfirmService {
	/**
	 * 添加日志，确认发货记录
	 * 
	 * @param logSendGoodsConfirm
	 */
	void add(LogSendGoodsConfirm logSendGoodsConfirm);
}
