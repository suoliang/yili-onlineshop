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
import com.fushionbaby.pay.controller.app.wx.service.AppWxService;
/**
 * 
 * @description APP端微信支付
 * @author 索亮
 * @date 2015年9月1日下午3:10:06
 */
@Controller
@RequestMapping("/appwx")
public class AppWXController {
	
	private final static Log LOGGER = LogFactory.getLog(AppWXController.class);
	@Autowired 
	private AppWxService appWxService;
	
	/**
	 * 获取APP微信预支付id字符串
	 * @param sid
	 * @param orderCode
	 * @param sourceCode
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getprepayid")
	public Object getPrepayIdStr(
			@RequestParam("sid") String sid,
			@RequestParam("orderCode") String orderCode,
			@RequestParam("sourceCode") String sourceCode,
			HttpServletRequest request)  {
		try {
			Object object= appWxService.getPrepayId(request,sid, orderCode, sourceCode);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("APP微信预支付出错", e);
			return Jsonp.error("微信预支付出错");
		}	
	}
	
	/**
	 * APP微信后台异步通知
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/notify")
	public void notifyAppWx(HttpServletRequest request, HttpServletResponse response)  {
		try {
			appWxService.notifyAppWx(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("APP微信回调出错", e);
		}
	}
	
}
