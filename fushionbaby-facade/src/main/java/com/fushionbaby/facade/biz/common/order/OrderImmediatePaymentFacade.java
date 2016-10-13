/**
 * 
 */
package com.fushionbaby.facade.biz.common.order;

import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.GotoOrderDto;
import com.fushionbaby.sku.model.Sku;

/**
 * @author mengshaobo
 *
 */
@Deprecated
public interface OrderImmediatePaymentFacade {
	/**
	 * 到订单支付页面的信息
	 * @param user 用户信息
	 * @param skuEntry 商品信息
	 * @return
	 */
	GotoOrderDto  immediatePayment(UserDto user,Sku skuEntry,Integer quantityNumber);
}
