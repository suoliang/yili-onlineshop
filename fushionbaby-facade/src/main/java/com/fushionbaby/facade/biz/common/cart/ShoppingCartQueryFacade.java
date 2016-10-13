package com.fushionbaby.facade.biz.common.cart;

import java.util.List;

import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.dto.cart.CartDto;
import com.fushionbaby.common.dto.cart.CartItemDto;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;

/**
 * 
 * @author mengshaobo
 *
 */
public interface ShoppingCartQueryFacade {
	
	/**
	 * 查询购物车
	 * @param userDto 用户
	 * @param visitKey k值
	 * @param response 
	 * @param imageType 图片类型
	 * @return
	 */
	CartDto queryCartDto(ShoppingCartBo queryCondition);
	
	
	/**
	 * 通过当前登录人查询购物车选中状态
	 * @param memberId 
	 * @return 购物车行记录
	 */
	List<CartItemDto> findSelectedCartSkuByMemberId(ShoppingCartQueryCondition queryCondition);
	
	
	

}
