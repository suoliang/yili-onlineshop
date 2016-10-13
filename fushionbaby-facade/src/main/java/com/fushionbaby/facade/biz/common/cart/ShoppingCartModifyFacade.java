package com.fushionbaby.facade.biz.common.cart;

import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.dto.cart.CartModifyAfterDto;

public interface ShoppingCartModifyFacade {
		
	/**
	 * 登录时根据条件操作
	 * @param queryCondition 查询条件
	 * @return
	 */
	CartModifyAfterDto hasLoginOperation(ShoppingCartBo shoppingCartBo);
	/**
	 * 未登录根据条件操作
	 * @param queryCondition 查询条件
	 * @return
	 */
	CartModifyAfterDto noLoginOpertation(ShoppingCartBo shoppingCartBo);
	 /**
	  * 登录时选中状态
	  * @param queryCondition
	  * @return
	  */
	CartModifyAfterDto hasLoginModifySelect(ShoppingCartBo shoppingCartBo);
	 /**
	  * 未登录时选择状态
	  * @param queryCondition
	  * @return
	  */
	 CartModifyAfterDto noLoginModifySelect(ShoppingCartBo shoppingCartBo);

}
