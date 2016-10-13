package com.aladingshop.sku.cms.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.sku.cms.model.SkuInventory;
import com.fushionbaby.common.condition.sku.SkuInventoryQueryCondition;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @author mengshaobo
 * 
 */
public interface SkuInventoryService<T extends SkuInventory> extends BaseService<T> {

	/**
	 * @author suoliang
	 * @param page
	 *            分页
	 * @return 结果
	 * @throws DataAccessException
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;

	/**
	 * 根据商品的skuId,要购买的数量quantity, <br/>
	 * 验证系统是否还有剩余库存,存在余货,返回true,库存剩余不足或者没有了返回false
	 * 
	 * @param skuCode
	 * @param quantity
	 * @return boolean
	 * @author 张明亮
	 */
	boolean verifyInventory(String skuCode, int quantity);

	/**
	 * 根据商品Id得到该商品的库存信息
	 * 
	 * @param skuId
	 * @return
	 * @author 张明亮
	 */
	SkuInventory findBySkuCode(String skuCode);
	
	/**
	 * 增加库存数量
	 * @param skuId
	 * @param quantity
	 */
	void addInventoryQuantity(String skuCode, int quantity);

	/**
	 * 更新库存数量
	 * @param skuId
	 * @param quantity
	 */
	void updateInventoryQuantity(String skuCode, int quantity);
	
	/**
	 * 通过条件查询库存信息
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return
	 * @author mengshaobo
	 */
	List<SkuInventory> queryByCondition(SkuInventoryQueryCondition queryCondition);

	/**
	 * 通过颜色尺寸和产品编号查询库存商品
	 * 
	 * @param productId
	 *            商品编号
	 * @param color
	 *            颜色
	 * @param size
	 *            尺寸
	 * @return
	 */
	SkuInventory querySkuIventory(String productCode, String color, String size);
	
	void addOrUpdate(SkuInventory skuInventory);
}
