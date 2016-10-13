/**
 * 
 */
package com.fushionbaby.facade.biz.common.order;

import java.util.List;

import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.vo.OrderParamUser;

/**
 * @author mengshaobo
 *
 */
public interface CreateOrderFacade {
	/**
	 * 初始化订单价格相关的参数
	 * 
	 * @param orderParam
	 */
	void initOrderPriceInOrderParam(OrderParamUser orderParam);

	/**
	 * 添加结算订单
	 * 
	 * @param memberId
	 *            用户id
	 * @param orderParam
	 *            订单参数
	 * @return
	 */
	void saveOrder( OrderParamUser orderParam);

	/**
	 * 购物车中购买的商品添加到订单行
	 * 
	 * @param orderCode
	 *            订单号
	 * @param memberId
	 */
	List<ShoppingCartSku> saveOrderLineByCart(String orderCode,ShoppingCartBo ShoppingCartBo);

	/**
	 * 更新该用户下的商品库存(购物车)
	 * 
	 * @param memberId
	 */
	void updateInventoryByCart(List<ShoppingCartSku> cartItemList);

	/**
	 * 数据处理完毕后，删除购物车中的商品
	 * 
	 * @param List
	 *            <ShoppingCartSkuUser>
	 */
	void deleteCartByCart(List<ShoppingCartSku> cartItemList);

	/**
	 * 冗余添加用户地址到订单会员地址表
	 * 
	 * @param memberId
	 * @param addressId
	 * @param orderCode
	 */
	void saveOrderMemberAddress(OrderParamUser orderParam);

	/**
	 * 验证商品库存信息
	 * 
	 * @param memberId
	 * @return
	 */
	Jsonp checkSkuInventory(ShoppingCartBo ShoppingCartBo);

	/**
	 * 修改账号余额信息
	 * 
	 * @param orderParam
	 *            订单参数
	 * @param user
	 */
	void modifyMemberAccountBalance(OrderParamUser orderParam, UserDto user);

}
