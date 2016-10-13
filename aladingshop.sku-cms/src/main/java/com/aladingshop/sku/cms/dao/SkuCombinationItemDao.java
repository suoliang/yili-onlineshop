/**
 * 
 */
package com.aladingshop.sku.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.sku.cms.model.SkuCombinationItem;

/**
 * @author mengshaobo
 *
 */
public interface SkuCombinationItemDao {
	
	void add(SkuCombinationItem item);
	
	/**
	 * 
	 * @param combinationId
	 */
	void deleteByCombinationId(@Param("combinationId")Long combinationId);
	
	/**
	 * 
	 * @param skuId
	 */
	void deleteBySkuCode(@Param("skuCode") String skuCode);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	SkuCombinationItem queryById(Long id);
	/**
	 * 查询商品组合
	 * @param skuId
	 * @return
	 */
	SkuCombinationItem queryListBySkuCode(String skuCode);
	/**
	 * 查询商品组合集合
	 * @param combinationId
	 * @return
	 */
	List<SkuCombinationItem> queryListByCombinationId(Long combinationId);
}
