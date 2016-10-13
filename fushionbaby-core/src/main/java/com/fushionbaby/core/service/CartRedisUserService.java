package com.fushionbaby.core.service;

import java.math.BigDecimal;
import java.util.List;

import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartModifyAfterDto;


/**
 * 购物车Redis操作接口
 * 
 * @author 张明亮
 */
public interface CartRedisUserService<SoShoppingCartSku> {

	/**
	 * 购物车添加商品行记录,
	 * 
	 * @param visitKey
	 * @param quantity
	 */
	public void addCartItem(String visitKey, SoShoppingCartSku item, int quantity);

	/**
	 * 修改购物车购买的商品数量
	 * 
	 * @param visitKey
	 * @param index
	 * @param number
	 */
	public void updateCartItemQuantity(String visitKey, long index, int number);
	
	/**
	 * 修改购物车购买的商品数量
	 * 
	 * @param visitKey
	 * @param skuCode
	 * @param number
	 */
	public void updateCartItemQuantityBySkuCode(String visitKey, String skuCode, int number);

	/**
	 * 根据购物车id和购物车商品skuCode修改购物车行记录是否被选中
	 * 
	 * @param shoppingCartSummaryId
	 * @param skuCode
	 * @param isSelected
	 */
	void updateCartItemSelected(String visitKey, String skuCode, String isSelected);

	/**
	 * 更新购物车所有商品行记录的,isSelected
	 * 
	 * @param visitKey
	 * @param isSelected
	 */
	void updateCartItemSelectedAll(String visitKey, String isSelected);

	/**
	 * 根据购物车的visitKey,删除购物车制定index的商品行
	 * 
	 * @param visitKey
	 * @param index
	 */
	public void delCartItem(String visitKey, long index);

	/**
	 * 删除整个购物车
	 * 
	 * @param visitKey
	 */
	public void delCart(String visitKey);

	/**
	 * 购物车分页查询
	 */
	public List<SoShoppingCartSku> getListPage(String visitKey, int start, int end);
	
	/***
	 * 购物车分页查询
	 * @param storeCode
	 * @param visitKey
	 * @param start
	 * @param end
	 * @return
	 */
//	public List<ShoppingCartSku> getSkuListPageByStoreCode(String storeCode,String visitKey, int start, int end);

	/**
	 * 查询购物车中已经勾选的商品行记录
	 * 
	 * @param visitKey
	 * @return List<SoShoppingCartSku>
	 */
	List<SoShoppingCartSku> findCartSkuSelected(String visitKey);

	/**
	 * 根据购物车id得到购物车isSelected状态的数量
	 * 
	 * @param shoppingCartSummaryId
	 * @param isSelected
	 * @return int
	 */
	int findCartItemSelectedCount(String visitKey,String isSelected);
	
	/**
	 * 根据购物车唯一key,和购物车item的id获取购物车中一件商品行的信息
	 * 
	 * @param visitKey
	 * @param index
	 * @return
	 */
	public SoShoppingCartSku getCartItemById(String visitKey, long index);

	/**
	 * 根据购物车唯一key,和购物车item的skuCode获取购物车中一件商品行的信息
	 * 
	 * @param visitKey
	 * @param skuCode
	 *            商品号
	 * @return
	 */
	public SoShoppingCartSku getCartItemBySkuCode(String visitKey,String skuCode);


	/**
	 * 会员登录,购物车操作
	 * 
	 * @param visitKey
	 * @param memberId
	 */
	public void loginCart(String visitKey,String storeVisitKey, UserDto userDto);

	/**
	 * 获取购物车总大小
	 * 
	 * @param visitKey
	 * @return long
	 */
	public long getCartSize(String visitKey);

	/**
	 * 获取购物车商品总数量
	 * 
	 * @param visitKey
	 * @return long
	 */
	public int getPnumSzie(String visitKey);

	/**
	 * 向list左边追加加元素
	 * 
	 * @param visitKey
	 * @param item
	 */
	public void lpush(String visitKey, String item);

	/**
	 * 判断key是否在Redis存在,存在返回ture,否则返回false
	 * 
	 * @param key
	 * @return
	 */
	public boolean isExists(String key);

	/**
	 * 返回重新计算价格后的cart,数量/单价小计/吊牌价小计
	 * @param visitKey
	 * @param cart
	 * @return
	 */
	public CartModifyAfterDto getSumCartSku(String visitKey);
	
	/**
	 * 返回选中商品的总单价
	 * @param visitKey
	 * @return
	 */
	public BigDecimal getSelectedSkuPrice(String visitKey);
	/**
	 * 返回所有商品的总单价
	 * @param visitKey
	 * @return
	 */
	public BigDecimal getAllSkuPrice(String visitKey);
	/**
	 * 返回所有商品的个数
	 * @param visitKey
	 * @return
	 */
	public int getQuantityTotal(String visitKey);
	

	/**
	 * 返回visitKey对应的购物车购买总数量
	 * @param visitKey
	 * @return
	 */
	public int getSumCartSkuQuantity(String visitKey);
	
	/**
	 * 更新购物车表到Redis
	 * 
	 * @param visitKey
	 * @param cart
	 */
	public void saveCart(String visitKey, SoShoppingCartSku soShoppingCartSku);
	
}
