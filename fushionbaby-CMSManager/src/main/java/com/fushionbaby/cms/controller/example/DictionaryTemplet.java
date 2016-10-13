package com.fushionbaby.cms.controller.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/example")
public class DictionaryTemplet {

	@RequestMapping(value = "/dictTemplet1")
	public String findOrderByOrderInfo() {
		
		return "models/example/dictTemplet";
	}

}
