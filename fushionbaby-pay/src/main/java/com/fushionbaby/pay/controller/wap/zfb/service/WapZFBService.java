package com.fushionbaby.pay.controller.wap.zfb.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WapZFBService {
	/**获取WAP端支付宝支付请求*/
	void getWapAlipay(String sid, String orderCode, HttpServletRequest request, HttpServletResponse response) throws Exception;
	/**支付成功后异步回调处理*/
	void wapZfbNotify(HttpServletRequest request, HttpServletResponse response) throws Exception;
	/**支付成功后同步回调处理*/
	void wapZfbReturn(HttpServletRequest request, HttpServletResponse response)  throws Exception;
}
