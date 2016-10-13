package com.fushionbaby.pay.controller.app.union.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @description 如意宝银联转入方式
 * @author 索亮
 * @date 2015年10月30日下午1:54:22
 */
public interface AppUnionAlabaoService {
	/***
	 * APP端如意宝转入银联获取tn号
	 * @param request
	 * @param alabaoSid
	 * @param orderCode
	 * @param sourceCode
	 * @return
	 * @throws Exception
	 */
	public Object getAlabaoUnionTn(HttpServletRequest request,
			String data,String mac) throws Exception;
	
	/**
	 * APP端如意宝转入银联异步通知
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void alabaoUnionNotify(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}
