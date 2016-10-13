package com.fushionbaby.pay.controller.app.union.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.pay.controller.app.union.service.AppUnionService;


/**
 * 
 * @description 大致流程 ：订单号 (so_code)--> 提交支付 (访问 "/tn" 带上订单号以及订单信息，请求到银联，返回会给我们一个
 *         tn的标志号) （tn）-->等待手机端的支付完成 --> 会通知手机端和后台（notify.do）支付的结果。
 *         完成支付，还可以通过接口查询，得到交易的详情列表（query.do）
 * @author 索亮
 * @date 2015年8月11日下午5:12:39
 * @version 2015-08-06银联更新的版本
 */
@Controller
@RequestMapping("/appunion")
public class AppUnionController {
	
	/** 日志*/
	private static  final  Log LOGGER=LogFactory.getLog(AppUnionController.class);
	@Autowired
	private AppUnionService appUnionService;
	/**
	 * 获取交易流水号tn
	 * 
	 * @param name
	 * @param request
	 * @return
	 * 
	 */
	@ResponseBody
	@RequestMapping("/tn")
	public Object getTN(HttpServletRequest request,
			@RequestParam("sid") String sid,
			@RequestParam("orderCode") String orderCode,
			@RequestParam("sourceCode")String sourceCode) {
		Object resp;
		try {
			if (CheckObjectIsNull.isNull(sid,SessionCache.get(sid))) {
				return Jsonp.noLoginError("未登录");
			}
			resp = appUnionService.getTN(request,sid,orderCode,sourceCode);
			
		} catch(DataAccessException e) {
			LOGGER.error("手机银联支付获得交易流水号失败", e);
			return Jsonp.error("手机银联支付获得交易流水号失败[异常]");
		}
		return resp;
	}
	
	/**
	 * app后台异步通知
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/notify")
	public void notify(HttpServletRequest request, HttpServletResponse response) {
		try {
			appUnionService.notifyBack(request);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}		
	}
	
}
