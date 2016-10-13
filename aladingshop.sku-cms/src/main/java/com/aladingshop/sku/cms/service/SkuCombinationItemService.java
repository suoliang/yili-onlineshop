package com.aladingshop.sku.cms.service;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuCombinationItem;

public interface SkuCombinationItemService {
	
	void add(SkuCombinationItem item);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	SkuCombinationItem queryById(Long id);
	/**
	 * 
	 * @param combinationId
	 */
	void deleteByCombinationId(Long combinationId);
	/**
	 * 
	 * @param skuId
	 */
	void deleteBySkuCode(String skuCode);
	
	/**
	 * 
	 * @param skuId
	 * @return
	 */
	SkuCombinationItem queryListBySkuCode(String skuCode);
	/**
	 * 
	 * @param combinationId
	 * @return
	 */
	List<SkuCombinationItem> queryListByCombinationId(Long combinationId);
}
