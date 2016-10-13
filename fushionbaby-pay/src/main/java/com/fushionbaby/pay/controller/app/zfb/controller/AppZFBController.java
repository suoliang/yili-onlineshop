package com.fushionbaby.pay.controller.app.zfb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.pay.controller.app.zfb.model.AlipayModel;
import com.fushionbaby.pay.controller.app.zfb.service.AppZfbService;

/**
 * 支付宝App客户端调用Controller
 * 
 * @author guolongchao 2014-12-23
 */
@Controller
@RequestMapping("/appzfb")
public class AppZFBController {

	@Autowired
	private AppZfbService appZfbService;

	/**
	 * 吐给app端支付宝相关的相关model
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/alipaymodel")
	public Object alipaymodel(HttpServletRequest request, @RequestParam("sid")String sid,@RequestParam("orderCode") String orderCode,
			@RequestParam("sourceCode")String sourceCode, @RequestParam("appId") String appId) {

		try {
			AlipayModel payReq = appZfbService.getTradeInfo(request,sid,orderCode,sourceCode,appId);
			if (payReq != null) {
				return Jsonp_data.success(payReq);
			}
			return Jsonp.error();
		} catch (Exception e) {
			e.printStackTrace();
			return Jsonp.error();
		}
	}

	/**
	 * app后台异步通知
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/notify")
	public void notify(HttpServletRequest request, HttpServletResponse response) {
		try {
			appZfbService.notify(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
