package com.fushionbaby.pay.controller.app.wx.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description APP微信支付Service
 * @author 索亮
 * @date 2015年8月12日下午4:48:38
 */
public interface AppWxService {
	/**
	 * 获取APP微信预支付id
	 * @param request
	 * @param sid
	 * @param orderCode
	 * @param sourceCode
	 * @return
	 * @throws Exception
	 */
	Object getPrepayId(HttpServletRequest request,String sid,String orderCode, String sourceCode)
	throws Exception;
	/**
	 * APP微信支付回调
	 * @param request
	 * @param response
	 */
	void notifyAppWx(HttpServletRequest request, HttpServletResponse response);
}
