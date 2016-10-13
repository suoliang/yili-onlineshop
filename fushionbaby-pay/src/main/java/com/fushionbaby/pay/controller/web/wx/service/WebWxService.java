package com.fushionbaby.pay.controller.web.wx.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/***
 * @description WEB微信支付
 * @author 索亮
 * @date 2015年8月27日下午4:58:32
 */
public interface WebWxService {
	/** 获取微信支付二维码图片 */
	void getQRCode(String sid,String orderCode,HttpServletRequest request,HttpServletResponse response) throws Exception;
	/** WEB微信支付回调 */
	void notifyWx(HttpServletRequest request, HttpServletResponse response);
}
