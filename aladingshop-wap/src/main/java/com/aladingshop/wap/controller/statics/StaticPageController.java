package com.aladingshop.wap.controller.statics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 静态页面跳转，无数据或操作
 * 
 * @author suntao
 *
 */
@Controller
@RequestMapping("/static")
public class StaticPageController {
	/***
	 * 跳转到附近阿拉丁静态页
	 * 
	 */
	@RequestMapping("/nearAlading")
	public String cardIndexRequest() {
		return "static/location";
	}

}
