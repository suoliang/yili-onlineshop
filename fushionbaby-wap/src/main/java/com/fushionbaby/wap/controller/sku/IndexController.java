/**
 * 
 */
package com.fushionbaby.wap.controller.sku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fushionbaby.facade.biz.app.AppSkuFacade;
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
	@Autowired
	private AppSkuFacade appSkuFacade;
	/***
	 * 首页显示商品列表
	 * 
	 * @return
	 */
	@RequestMapping("indexList")
	public String indexList(ModelMap model) {		
		model.addAttribute("indexDto", appSkuFacade.findIndexAll());
		return "index";
	}
	
}
