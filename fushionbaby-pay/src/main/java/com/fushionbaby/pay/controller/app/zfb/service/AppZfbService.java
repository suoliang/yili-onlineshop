package com.fushionbaby.pay.controller.app.zfb.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fushionbaby.pay.controller.app.zfb.model.AlipayModel;

public interface AppZfbService {

	/**
	 * 支付宝App端异步通知
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public AlipayModel getTradeInfo(HttpServletRequest request,String sid, String orderCode, String sourceCode,
			String appId) throws Exception;
}
