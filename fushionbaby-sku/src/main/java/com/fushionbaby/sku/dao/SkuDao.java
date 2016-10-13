/**
 * 
 */
package com.fushionbaby.sku.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.sku.model.Sku;

/**
 * @author mengshabo
 *
 */
public interface SkuDao {
	
	/**
	 * 添加商品 
	 * @param skuEntry
	 * @return 添加数量
	 */
	Long insert(Sku skuEntry);
	
	/** 
	 * 通过商品编号删除
	 * @param id
	 */
	void deleteById(Long id);

	/**
	 * 修改商品信息
	 * @param skuEntry
	 */
	void update(Sku skuEntry);
	
	/**
	 * 通过商品Id查询
	 * @param id
	 * @return
	 */
	Sku findById(Long id);
	
	/**
	 * 通过商品编号查询商品
	 * 
	 * @param skuCode
	 *            商品编号
	 * @return 该商品
	 */
	Sku queryBySkuCode(@Param("skuCode") String skuCode);

	/**
	 * 通过产品编号查询商品
	 * 
	 * @param productId
	 *            产品编号
	 * @return
	 */
	List<Sku> queryByProductCode(String productCode);
	/**
	 * 条件查询
	 * @param queryCondition
	 * @return
	 */
	List<Sku> queryByCondition(SkuEntryQueryCondition queryCondition);
	/**
	 * 查询记录数
	 * @param queryCondition
	 * @return
	 */
	Integer getTotal(SkuEntryQueryCondition queryCondition);
	
	/**
	 * 查询出当天操作过的所有商品
	 * @return
	 */
	List<Sku> queryBySameDayOperateing();
}
