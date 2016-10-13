package com.fushionbaby.pay.controller.app.union.controller;

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
import com.fushionbaby.pay.controller.app.union.service.AppUnionAlabaoService;

/***
 * @description 如意宝银联转入方式
 * @author 索亮
 * @date 2015年10月30日下午3:44:25
 */
@Controller
@RequestMapping("/appUnionAlabao")
public class AppUnionAlabaoController {
	
	private static final Log LOGGER = LogFactory.getLog(AppUnionAlabaoController.class);
	
	@Autowired
	private AppUnionAlabaoService appUnionAlabaoService;
	
	/**
	 * 获取APP银联支付交易tn号
	 * @param alabaoSid
	 * @param orderCode
	 * @param sourceCode
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAlabaoUnionTn")
	public Object getPrepayIdStr(HttpServletRequest request,
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac)  {
		try {
			return appUnionAlabaoService.getAlabaoUnionTn(request, data, mac); 
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("APP银联如意宝获取tn号出错", e);
			return Jsonp.error("获取tn号出错");
		}	
	}
	
	/**
	 * 如意宝APP微信后台异步通知
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/alabaoUnionNotify")
	public void notifyAlabaoAppWx(HttpServletRequest request, HttpServletResponse response)  {
		try {
			appUnionAlabaoService.alabaoUnionNotify(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("如意宝APP银联回调出错", e);
		}
	}
	
}
