package com.aladingshop.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.auth.service.StoreAuthUserService;
@Controller
@RequestMapping("/store")
public class StoreController {

	
	@Autowired
	private StoreAuthUserService<StoreAuthUser> userService;
	
	
	
	@RequestMapping("/test")
	public String test(){
		
		List<StoreAuthUser> userList=userService.findAll();
	     System.out.println(userList.size());
		
		return "index";
	}
	
}
