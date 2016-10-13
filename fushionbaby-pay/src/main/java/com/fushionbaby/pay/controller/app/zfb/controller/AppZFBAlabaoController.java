package com.fushionbaby.pay.controller.app.zfb.controller;

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
import com.fushionbaby.pay.controller.app.zfb.service.AppZfbAlabaoService;
/***
 * @description 支付宝支付转入
 * @author 索亮
 * @date 2015年9月14日上午9:35:07
 */
@Controller
@RequestMapping("/appzfbAlabao")
public class AppZFBAlabaoController {
	
	private static final Log LOGGER = LogFactory.getLog(AppZFBAlabaoController.class);
	
	@Autowired
	private AppZfbAlabaoService appZfbAlabaoService;
	
	/**
	 * 获取支付宝交易信息吐给客户端
	 * @param request
	 * @param alabaoSid
	 * @param orderCode
	 * @param sourceCode
	 * @param appId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getAlabaoModel")
	public Object getAlabaoTradeInfo(HttpServletRequest request,
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		try {
			Object object = appZfbAlabaoService.getAlabaoTradeInfo(request, data, mac);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("APP支付宝如意宝支付出错", e);
			return Jsonp.error("获取支付信息出错");
		}
	}
	
	/**
	 * APP后台异步通知
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/alabaoNotify")
	public void alabaoNotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			appZfbAlabaoService.alabaoNotify(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
