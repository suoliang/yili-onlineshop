package com.fushionbaby.facade.biz.common.cart.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.facade.biz.common.cart.CartRedisUserFacade;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月5日上午10:16:48
 */
@Service
public class CartRedisUserFacadeImpl implements CartRedisUserFacade{

	@Autowired
	private CartRedisUserService<ShoppingCartSku> cartRedisUserService;
	public void loginCart(String visitKey,String storeVisitKey, UserDto user) {
		cartRedisUserService.loginCart(visitKey,storeVisitKey,user);
		
	}

}
