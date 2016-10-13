package com.fushionbaby.pay.controller.app.zfb.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/***
 * 益多宝订单支付
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月8日上午11:06:15
 */
public interface AppZfbYiduobaoService {
	
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
	public Object getYiduobaoTradeInfo(HttpServletRequest request,String data,String mac) throws Exception;
	
	/**
	 * 支付宝APP端益多宝异步通知
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void yiduobaoNotify(HttpServletRequest request,HttpServletResponse response) throws Exception;
	
}
