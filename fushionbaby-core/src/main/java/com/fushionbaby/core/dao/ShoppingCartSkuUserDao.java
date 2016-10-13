package com.fushionbaby.core.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.ShoppingCartSku;

/**
 * 
 * @author 张明亮 t_so_shopping_cart_sku 购物车 行记录 购物车购物明细表
 */
public interface ShoppingCartSkuUserDao {

	void update(ShoppingCartSku shoppingCartSku);
	
	/**
	 * 为购物车添加一件需要购买的商品
	 * 
	 * @param shoppingCartSku
	 */
	void add(ShoppingCartSku shoppingCartSku);
	
	/**
	 * 
	 * @param queryCondition
	 * @return
	 */
	List<ShoppingCartSku> findByQueryCondition(ShoppingCartQueryCondition queryCondition);
	

	/**
	 * 通过会员id查询购物车中已经勾选的商品行记录
	 * @param memberId
	 * @return List<SoShoppingCartSku>
	 */
	List<ShoppingCartSku> findSelectedCartSkuByMemberId(Long memberId);
	
	/**
	 * 通过会员id查询购物车中已经勾选的商品行记录中的sku_code列表
	 * @param memberId
	 * @return List<String>
	 */
	List<String> findCartSkuSelectedSkuCode(ShoppingCartQueryCondition queryCondition);

	/**
	 * 根据购物车id查询购物车下所有行记录,带分页
	 * 
	 * @param shoppingCartSummaryId
	 * @return
	 */
	List<ShoppingCartSku> getListPage(Map<String, Object> map);
	
	
	List<ShoppingCartSku> findByMemberId(Long memberId);

	/**
	 * 根据购物车行记录id,查询购物车下一件商品
	 * 
	 * @param id
	 * @return
	 */
	ShoppingCartSku findById(Long id);


	/**
	 * 删除购物车下一件商品,根据购物车id和行记录id删除 item Id shoppingCartId
	 * 
	 * @param skuCode
	 * @param memberId
	 */
	void deleteById(Long id);
 
}