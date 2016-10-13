package com.fushionbaby.pay.controller.app.zfb.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/***
 * @description 如意宝APP支付宝支付
 * @author 索亮
 * @date 2015年9月11日下午2:26:33
 */
public interface AppZfbAlabaoService {
	
	/***
	 * 支付宝APP端如意宝发起支付
	 * @param request
	 * @param alabaoSid
	 * @param orderCode
	 * @param sourceCode
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public Object getAlabaoTradeInfo(HttpServletRequest request,
			String data,String mac) throws Exception;
	
	/**
	 * 支付宝APP端如意宝异步通知
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void alabaoNotify(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
}
