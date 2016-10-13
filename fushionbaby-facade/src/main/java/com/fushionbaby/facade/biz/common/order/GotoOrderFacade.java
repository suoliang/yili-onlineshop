/**
 * 
 */
package com.fushionbaby.facade.biz.common.order;

import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.GotoOrderDto;

/**
 * 购物车页去结算跳到订单页的操作
 * @author Leon
 *
 */
public interface GotoOrderFacade {
	
	/**
	 * 初始化到支付页的参数
	 * @param user
	 * @param cartItemList
	 * @param source
	 * @return
	 */
	GotoOrderDto initGotoOrderDto(ShoppingCartBo shoppingCartBo);
	
	/**
	 * 初始化去支付订单
	 * @param user
	 * @param gotoOrderDto
	 * @return
	 */
	
	GotoOrderDto initGotoOrder(UserDto user,GotoOrderDto gotoOrderDto);
	
	/**
	 * 检查是否支持货到付款
	 * @param provinceCode
	 * @return
	 */
	boolean checkIsSupportHDFK(String provinceCode);
	
	/**
	 * 给去结算页设置默认地址
	 * @param gotoOrderDto 到支付页的订单信息
	 * @param memberId 当前用户Id
	 * @return
	 */
	GotoOrderDto giveGotoOrderDefaultAddress(GotoOrderDto gotoOrderDto,Long memberId);

}
