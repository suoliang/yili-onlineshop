package com.fushionbaby.pay.controller.app.wx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.pay.controller.app.wx.service.AppWxAlabaoService;

/**
 * @description APP端如意宝--微信支付转入
 * @author 索亮
 * @date 2015年10月8日下午4:11:40
 */
@Controller
@RequestMapping("/appwxAlabao")
public class AppWXAlabaoController {
	
	private static final Log LOGGER = LogFactory.getLog(AppWXAlabaoController.class);
	
	@Autowired
	private AppWxAlabaoService appWxAlabaoService;
	
	/**
	 * 获取APP微信预支付id字符串
	 * @param alabaoSid
	 * @param orderCode
	 * @param sourceCode
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAlabaoWXMap")
	public Object getPrepayIdStr(HttpServletRequest request,
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac)  {
		try {
			return appWxAlabaoService.getAlabaoPrepayId(request, data, mac); 
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("APP微信如意宝预支付出错", e);
			return Jsonp.error("微信预支付出错");
		}	
	}
	
	/**
	 * 如意宝APP微信后台异步通知
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/alabaoNotify")
	public void notifyAlabaoAppWx(HttpServletRequest request, HttpServletResponse response)  {
		try {
			appWxAlabaoService.notifyAlabaoAppWx(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("如意宝APP微信回调出错", e);
		}
	}
}
