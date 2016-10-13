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
import com.fushionbaby.pay.controller.app.wx.service.AppWxYiduobaoService;

/***
 * 益多宝（阿拉丁卡） 微信支付
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月22日上午11:21:58
 */
@Controller
@RequestMapping("/appwxYiduobao")
public class AppWXYiduobaoController {
	
	private static final Log LOGGER = LogFactory.getLog(AppWXYiduobaoController.class);
	
	@Autowired
	private AppWxYiduobaoService appWxYiduobaoService;
	
	/**
	 * 获取APP微信预支付id字符串
	 * @param YiduobaoSid
	 * @param orderCode
	 * @param sourceCode
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getYiduobaoWXMap")
	public Object getPrepayIdStr(HttpServletRequest request,
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac)  {
		try {
			return appWxYiduobaoService.getYiduobaoPrepayId(request, data, mac); 
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("APP微信如意宝预支付出错", e);
			return Jsonp.error("微信预支付出错");
		}	
	}
	
	/**
	 * 阿拉丁卡APP微信后台异步通知
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/yiduobaoNotify")
	public void notifyYiduobaoAppWx(HttpServletRequest request, HttpServletResponse response)  {
		try {
			appWxYiduobaoService.notifyYiduobaoAppWx(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("如意宝APP微信回调出错", e);
		}
	}
	
	

}
