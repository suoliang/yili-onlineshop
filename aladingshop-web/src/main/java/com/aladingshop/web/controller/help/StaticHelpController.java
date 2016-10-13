/**
 * 
 */
package com.aladingshop.web.controller.help;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description 帮助中心静态control
 * @author 孙涛
 * @date 2015年8月21日上午10:33:31
 */
@Controller
@RequestMapping("/help")
public class StaticHelpController {
	@RequestMapping("register")
	public String registerHelpPage(){
		return "help/help-registered";
	}
	
	@RequestMapping("buy")
	public String buyHelpPage(){
		return "help/help-buy";
	}
}
