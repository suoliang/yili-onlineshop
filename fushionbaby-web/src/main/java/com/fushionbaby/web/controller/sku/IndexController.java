/**
 * 
 */
package com.fushionbaby.web.controller.sku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fushionbaby.facade.biz.web.sku.WebSkuFacade;


/**
 * @author mengshaobo
 * 
 */
@Controller
@RequestMapping("/index")
public class IndexController{
	@Autowired
	private WebSkuFacade webFacade;
	/***
	 * 首页显示商品列表
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String indexList(ModelMap model) {		
		model.addAttribute("indexDto", webFacade.findIndexAll());
		return "index";
	}
	
}
