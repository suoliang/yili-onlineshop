/**
 * 
 */
package com.fushionbaby.wap.controller.sku;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mengshaobo
 * 
 */
@Controller
@RequestMapping("/views")
public class FooterController {

	@RequestMapping("footer")
	public String footer(@RequestParam("ZNX") Integer znxCode, ModelMap model) {
		return "foot/footer-znx";
	}

}
