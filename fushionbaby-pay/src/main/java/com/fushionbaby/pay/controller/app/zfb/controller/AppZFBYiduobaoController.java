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
import com.fushionbaby.pay.controller.app.zfb.service.AppZfbYiduobaoService;
/***
 * 阿拉丁卡订单   支付宝支付
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月8日上午10:52:42
 */
@Controller
@RequestMapping("/appzfbYiduobao")
public class AppZFBYiduobaoController {
	
	private static final Log LOGGER = LogFactory.getLog(AppZFBYiduobaoController.class);
	
	@Autowired
	private AppZfbYiduobaoService appZfbYiduobaoService;
	
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
	@RequestMapping("getYiduobaoModel")
	public Object getAlabaoTradeInfo(HttpServletRequest request,
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		try {
			Object object = appZfbYiduobaoService.getYiduobaoTradeInfo(request, data, mac);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("APP支付宝益多宝支付异常", e);
			return Jsonp.error("获取支付信息出错");
		}
	}
	
	/**
	 * APP后台异步通知
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/yiduobaoNotify")
	public void alabaoNotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			appZfbYiduobaoService.yiduobaoNotify(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
