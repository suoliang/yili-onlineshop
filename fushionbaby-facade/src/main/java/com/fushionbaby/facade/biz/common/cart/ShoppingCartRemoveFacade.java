package com.fushionbaby.facade.biz.common.cart;

import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.dto.cart.CartDto;

public interface ShoppingCartRemoveFacade {
	/**
	 * 已登录用户的购物车移除商品
	 * @param user 当前用户
	 * @param codeList 商品编号集合
	 * @param 购物车SID
	 */
	CartDto hasLoginRemoveBath(ShoppingCartBo shoppingCartBo);
	/**
	 * 未登录时移除购物车商品
	 * @param visitKey k值
	 * @param codeList 商品编号集合
	 */
	CartDto noLoginRemoveBath(ShoppingCartBo shoppingCartBo);
}
