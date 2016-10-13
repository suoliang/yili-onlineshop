package com.fushionbaby.pay.controller.app.union.service;

import javax.servlet.http.HttpServletRequest;

public interface AppUnionService {

	/***
	 * 异步通知到后台，交易信息
	 * 
	 * @param request
	 * @return
	 */
	public void notifyBack(HttpServletRequest request) throws Exception;

	/***
	 * 获得tn交易流水号
	 * 
	 * @param request
	 * @param sid
	 * @param orderCode
	 * @return
	 */
	public Object getTN(HttpServletRequest request,String sid,String orderCode,String sourceCode); 
	
}
