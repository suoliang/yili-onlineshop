package com.fushionbaby.facade.biz.common.cart;

import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;

/**
 * 购物车门面
 * @author King 索亮
 *
 */
public interface ShoppingCartAddFacade {
	/**
	 * 检查商品是否下架
	 * @param skuId 商品序号
	 * @return
	 */
	String checkSkuStatus(String skuCode);
	/***
	 * 检查商品库存
	 * @param quantityNum 购买数量
	 * @param skuId 商品序号
	 * @return
	 */
	String checkSkuInventoryNum(int quantityNum,String skuCode);
	/**
	 * 检查已经登陆的购物车大小
	 * @param memberId 当前用户Id
	 * @return
	 */
	Jsonp_data checkHasLoginCartSize(ShoppingCartBo shoppingCartBo);
	/***
	 * 已经登陆的购物车添加
	 * @param memberId 当前用户Id
	 * @param skuId 商品序号
	 * @param quantityNum 购买数量
	 */
	Jsonp hasLoginCartAdd(ShoppingCartBo shoppingCartBo);
	/**
	 * 没有登陆的购物车添加
	 * @param visitKey 
	 * @param quantityNum 购买数量
	 * @param skuId 商品序号
	 */
	Jsonp noLoginCartAdd(ShoppingCartBo shoppingCartBo);
	/**
	 * 得到选中的购物车总数
	 * @param memberId 当前用户Id
	 * @return
	 */
	Integer getSelectedCartSkuCountByMemberId(ShoppingCartQueryCondition cartQueryCondition);
	/***
	 * 检查没有登陆的购物车
	 * @param visityKey
	 * @return
	 */
	Jsonp_data checkNoLoginCartSize(ShoppingCartBo shoppingCartBo);
	/**
	 * 得到未登录状态的购物车总数
	 * @param visitKey
	 * @return
	 */
	Integer getRedisCount(ShoppingCartBo shoppingCartBo);
}
