package com.fushionbaby.pay.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.util.jsonp.Jsonp;


@Controller
@RequestMapping("/pay")
public class TestController {
	
	@ResponseBody
	@RequestMapping("/test")
	public Object test(HttpServletRequest request)  {
		try {
			return Jsonp.success();
		} catch(DataAccessException e) {
			e.printStackTrace();
			return Jsonp.error();
		}		
	}
}
