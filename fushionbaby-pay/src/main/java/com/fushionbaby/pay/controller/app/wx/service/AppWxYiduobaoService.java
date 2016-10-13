package com.fushionbaby.pay.controller.app.wx.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * 阿拉丁卡   微信支付服务
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月22日上午11:24:40
 */
public interface AppWxYiduobaoService {

	
	/**
	 * 益多宝（阿拉丁卡）转入获取APP微信预支付id
	 * @param request
	 * @param alabaoSid
	 * @param orderCode
	 * @param sourceCode
	 * @return
	 * @throws Exception
	 */
	Object getYiduobaoPrepayId(HttpServletRequest request,String data,String mac) throws Exception;
	/**
	 * 益多宝（阿拉丁卡）APP微信支付回调
	 * @param request
	 * @param response
	 */
	void notifyYiduobaoAppWx(HttpServletRequest request, HttpServletResponse response);
}
