package com.fushionbaby.pay.controller.app.union.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.pay.controller.app.union.service.AppUnionQueryService;

/***
 * @description 查询银联交易记录
 * @author 索亮
 * @date 2016年2月29日下午5:32:07
 */
@Controller
@RequestMapping("/appunion")
public class AppUnionQueryController {

	private final static Log LOGGER = LogFactory.getLog(AppUnionQueryController.class);
	
	@Autowired
	private AppUnionQueryService appUnionQueryService;
	
	@ResponseBody
	@RequestMapping("/queryRecord")
	public Object queryRecord(
			@RequestParam("orderId") String orderId,
			@RequestParam("txnTime")String txnTime) {
		try {
			return appUnionQueryService.queryUnionRecord(orderId, txnTime);
		} catch(DataAccessException e) {
			LOGGER.error("银联查询交易记录出错", e);
			return Jsonp.error("银联查询交易记录失败[异常]");
		}
	}
	
}
