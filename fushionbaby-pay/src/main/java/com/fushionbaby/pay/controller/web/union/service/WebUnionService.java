package com.fushionbaby.pay.controller.web.union.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * 
 * @description WEB银联支付
 * @author 索亮
 * @date 2015年8月24日下午5:03:41
 */
public interface WebUnionService {
	/***
	 * WEB网关支付生成html
	 * @param sid
	 * @param orderCode
	 * @param request
	 * @param response
	 */
	void createHtml(String sid, String orderCode, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * WEB银联商户前台通知类
	 * @param request
	 * @param response
	 */
	void frontReturn(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 商户后台通知类
	 * @param request
	 * @param response
	 */
	void notifyUnion(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
