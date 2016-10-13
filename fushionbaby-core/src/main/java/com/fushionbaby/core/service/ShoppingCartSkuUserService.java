package com.fushionbaby.core.service;

import java.math.BigDecimal;
import java.util.List;

import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.ShoppingCartSku;

/**
 * @author 张明亮
 * @param <T>
 *            购物车明细相关操作
 */
public interface ShoppingCartSkuUserService<T extends ShoppingCartSku> {
	
	
	void update(ShoppingCartSku soShoppingCartSku);
	
	/**
	 * 为购物车添加一行商品
	 * 
	 * @param soShoppingCartSku
	 */
	 void add(ShoppingCartSku soShoppingCartSku);
	 
	 
	 void deleteById(Long id);

	/**
	 * 根据购物车id,查询会员购物车所有商品,分页查询
	 * 
	 * @param shoppingCardId
	 * @throws
	 */
	public List<ShoppingCartSku> getListPage(Long memberId, Integer page_index, Integer page_size);
	
	/**
	 * 根据购物车行记录id,查询购物车下一件商品
	 * 
	 * @param id
	 * @return
	 */
	ShoppingCartSku findById(Long id);

	/***
	 *
	 * @param queryCondition
	 * @return
	 */
	List<ShoppingCartSku> findByQueryCondition(ShoppingCartQueryCondition queryCondition);
	
	
	
	ShoppingCartSku  findBySkuCodeAndMemberId(String skuCode,Long memberId);
	/**
	 * 
	 * @param queryCondition
	 * @return
	 */
	List<String> findCartSkuSelectedSkuCode(ShoppingCartQueryCondition queryCondition);

	/** 
	 * 通过用户Id查询购物车所有商品的价格
	 * @param memberId
	 * @return
	 */
	BigDecimal getAllSkuPrice(Long memberId);
}
