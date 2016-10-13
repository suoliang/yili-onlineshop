package com.fushionbaby.pay.controller.app.wx.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * @description APP如意宝微信方式转入
 * @author 索亮
 * @date 2015年10月8日上午10:14:27
 */
public interface AppWxAlabaoService {
	/**
	 * 如意宝转入获取APP微信预支付id
	 * @param request
	 * @param alabaoSid
	 * @param orderCode
	 * @param sourceCode
	 * @return
	 * @throws Exception
	 */
	Object getAlabaoPrepayId(HttpServletRequest request,String data,String mac) throws Exception;
	/**
	 * 如意宝APP微信支付回调
	 * @param request
	 * @param response
	 */
	void notifyAlabaoAppWx(HttpServletRequest request, HttpServletResponse response);
}
