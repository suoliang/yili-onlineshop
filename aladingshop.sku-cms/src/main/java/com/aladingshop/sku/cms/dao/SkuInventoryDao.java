/**
 * 
 */
package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuInventory;
import com.fushionbaby.common.condition.sku.SkuInventoryQueryCondition;

/**
 * @author mengshaobo
 * 
 *         索亮 20141101更新
 * 
 */
public interface SkuInventoryDao {

	void add(SkuInventory skuInventory);

	// 测试时使用
	void deleteById(Long id);

	void update(SkuInventory skuInventory);

	SkuInventory findById(Long id);

	List<SkuInventory> findAll();

	/**
	 * 分页查询
	 * 
	 * @author suoliang
	 */
	List<SkuInventory> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);

	/**
	 * 查询得到某商品的库存数量
	 * 
	 * @param skuId
	 * @return
	 * @author 张明亮
	 */
	Integer getInventoryQuantity(String skuCode);

	/**
	 * 根据商品ID得到该商品的库存信息
	 * 
	 * @param skuId
	 * @return
	 */
	SkuInventory findBySkuCode(String skuCode);
	
	void addInventoryQuantity(SkuInventory skuInventory);

	void updateInventoryQuantity(SkuInventory skuInventory);
	
	/***
	 * 通过条件查询商品库存信息
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return
	 */
	List<SkuInventory> queryByCondition(SkuInventoryQueryCondition queryCondition);

	/**
	 * 
	 * @param productId
	 * @param color
	 * @param size
	 * @return
	 */
	SkuInventory querySkuIventory(String productCode, String color, String size);
}
