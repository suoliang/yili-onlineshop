package com.fushionbaby.pay.controller.app.alabao.service;

import javax.servlet.http.HttpServletRequest;

/***
 * @description 如意宝支付方式接口
 * @author 索亮
 * @date 2015年10月12日下午2:27:34
 */
public interface AppAlabaoService {
	/**
	 * 获取如意宝支付的实体
	 * @param sid
	 * @param alabaoSid
	 * @param orderCode
	 * @param sourceCode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	Object getAlabaoPayModel(HttpServletRequest request, String data, String mac) throws Exception;
	/**
	 * @description 用户输入密码进行支付
	 * @param sid
	 * @param alabaoSid
	 * @param orderCode
	 * @param payPassword
	 * @return
	 */
	Object alabaoPay(String data, String mac) throws Exception;
	
}
