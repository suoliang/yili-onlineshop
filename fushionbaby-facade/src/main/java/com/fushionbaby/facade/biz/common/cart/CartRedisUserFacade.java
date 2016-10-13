package com.fushionbaby.facade.biz.common.cart;

import com.fushionbaby.common.dto.UserDto;

/***
 * 
 * @description 类描述... 购物车Redis操作接口
 * @author 徐培峻
 * @date 2015年8月5日上午10:15:51
 */
public interface CartRedisUserFacade {

	void loginCart(String visitKey,String storeVisitKey, UserDto user);

}
