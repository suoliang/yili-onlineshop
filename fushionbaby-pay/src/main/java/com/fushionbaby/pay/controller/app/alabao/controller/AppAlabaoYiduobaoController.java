package com.fushionbaby.pay.controller.app.alabao.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.pay.controller.app.alabao.service.AppAlabaoYiduobaoService;
/***
 * 阿拉丁卡订单，用如意宝支付  
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月26日上午9:45:05
 */
@Controller
@RequestMapping("/appYiduobao")
public class AppAlabaoYiduobaoController {

	/** 日志 */
	private static final Log LOGGER = LogFactory.getLog(AppAlabaoYiduobaoController.class);

	@Autowired
	private AppAlabaoYiduobaoService appAlabaoYiduobaoService;

	/**
	 * @description APP账户必须保证先登录,接着判断如意宝账户登录,数据传给APP后APP端进行后续操作
	 * @param sid
	 * @param alabaoSid
	 * @param orderCode
	 * @param sourceCode
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAlabaoPayModel")
	public Object getAlabaoPayModel(HttpServletRequest request,
			@RequestParam(value = "data", defaultValue = "") String data,
			@RequestParam(value = "mac", defaultValue = "") String mac) {
		try {
			return appAlabaoYiduobaoService.getAlabaoYiduobaoPayModel(request, data, mac);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("APP如意宝获取支付信息异常", e);
			return Jsonp.error("获取支付信息异常");
		}
	}

	/**
	 * @description 进行密码校验支付
	 * @param sid
	 * @param alabaoSid
	 * @param orderCode
	 * @param payPassword
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/alabaoPay")
	public Object alabaoPay(
			@RequestParam(value = "data", defaultValue = "") String data,
			@RequestParam(value = "mac", defaultValue = "") String mac) {
		try {
			return appAlabaoYiduobaoService.alabaoPay(data, mac);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("APP如意宝支付异常", e);
			return Jsonp.error("支付异常");
		}
	}
}
