package com.aladingshop.web.controller.pay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladingshop.web.controller.pay.union.service.WebUnionService;
/***
 * @description WEB银联支付
 * @author 索亮
 * @date 2015年8月24日下午5:04:19
 */
@Controller
@RequestMapping("/webunion")
public class WebUnionController {
	
	private static Logger LOGGER = Logger.getLogger(WebUnionController.class);
	
	@Autowired
	private WebUnionService webUnionService;
	
	/**
	 * 银联支付生成html
	 * @param sid
	 * @param orderCode
	 * @param request
	 * @param response
	 */
	@RequestMapping("/pay")
	public void createHtml(String sid, String orderCode, 
			HttpServletRequest request, HttpServletResponse response) {
		try {
			webUnionService.createHtml(sid, orderCode, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("WEB银联支付生成html结果[失败]"+e.getMessage());
		}
	}
	
	/**
	 * 银联支付前台通知
	 * @param request
	 * @param response
	 */
	@RequestMapping("/return")
	public void frontReturn(HttpServletRequest request, HttpServletResponse response){
		try {
			webUnionService.frontReturn(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("WEB银联支付前台回调通知结果[失败]"+e.getMessage());
		}
	}
	
	/**
	 * 银联支付后台通知
	 * @param request
	 * @param response
	 */
	@RequestMapping("/notify")
	public void notifyUnion(HttpServletRequest request, HttpServletResponse response){
		try {
			webUnionService.notifyUnion(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("WEB银联支付后台回调通知结果[失败]"+e.getMessage());
		}
	}
	
}
