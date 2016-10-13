package com.fushionbaby.pay.controller.web.wx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.pay.controller.web.wx.service.WebWxService;
/**
 * @description WEB微信支付
 * @author 索亮
 * @date 2015年8月27日下午4:43:46
 */
@Controller
@RequestMapping("/webwx")
public class WebWxController {
	private static final Log LOGGER = LogFactory.getLog(WebWxController.class);
	
	@Autowired
	private WebWxService webWxService;
	
	/**
	 * 获取二维码图片
	 * @param name
	 * @param request
	 * @return
	 */
	@RequestMapping("/getQRCode")
	public void getQRCodeImage(
			@RequestParam("sid") String sid,
			@RequestParam("orderCode") String orderCode,
			HttpServletRequest request, HttpServletResponse response)  {
		try {
			webWxService.getQRCode(sid, orderCode, request, response);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("WEB微信获取二维码出错", e);
		}		
	}
	
	/**
	 * 后台异步通知
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/notify")
	public void notifyWx(HttpServletRequest request, HttpServletResponse response)  {
		try {
			webWxService.notifyWx(request,response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("WEB微信回调出错", e);
		}
	}
	
}
