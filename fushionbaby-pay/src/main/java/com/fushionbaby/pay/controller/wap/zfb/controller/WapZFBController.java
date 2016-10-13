package com.fushionbaby.pay.controller.wap.zfb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fushionbaby.pay.controller.wap.zfb.service.WapZFBService;

@Controller
@RequestMapping("/wapZfb")
public class WapZFBController {
	
	private static Logger LOGGER = Logger.getLogger(WapZFBController.class);
	
	@Autowired
	private WapZFBService wapZFBService;
	
	/***
	 * wap支付宝请求
	 * @param sid
	 * @param orderCode
	 * @param request
	 * @param response
	 */
	@RequestMapping("getWapAlipay")
	public void getWapAlipay(String sid, String orderCode,
			HttpServletRequest request, HttpServletResponse response){
		try {
			wapZFBService.getWapAlipay(sid, orderCode, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("wap获取支付宝请求出错", e);
		}
	}
	
	/***
	 * 异步回调
	 * @param request
	 * @param response
	 */
	@RequestMapping("/wapZfbNotify")
	public void wapZfbNotify(HttpServletRequest request, HttpServletResponse response){
		try {
			wapZFBService.wapZfbNotify(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * 同步回调
	 * @param request
	 * @param response
	 */
	@RequestMapping("/wapZfbReturn")
	public void wapZfbReturn(HttpServletRequest request, HttpServletResponse response){
		try {
			wapZFBService.wapZfbReturn(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
