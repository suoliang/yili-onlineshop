package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuCategoryQueryCondition;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.sku.model.SkuCategory;

/**
 * 
 * @author King
 * 
 */
public interface SkuCategoryService<T extends SkuCategory> extends BaseService<T> {
	/***
	 * 查询所有分类信息
	 * @return
	 */
	List<SkuCategory> findAllCategory(String storeCode);
	/***
	 * 通过父级分类code查询出子分类集合
	 * @param grandcategoryCode
	 * @return
	 */
	List<SkuCategory> findCategoryByGrandcategoryCode(String storeCode,String grandcategoryCode);
	
	T getCategoryByCode(String storeCode,String code);
	
	/**
	 * 根据分类等级获取分类集合
	 * @param level 分类级别
	 * @return
	 */
	List<SkuCategory> getCategoryByLevel(String storeCode,Integer level);
	
	
	List<SkuCategory> findByCondition(SkuCategoryQueryCondition queryCondition);
	
	/**
	 * 通过分类编号获取该分类下的所有三级分类
	 * @param categoryCode 分类编号
	 * @return
	 */
	List<String> getLowestChildCategorysByCode(String storeCode,String categoryCode) throws Exception;
	
	
	/***
	 * 获取相同的分类
	 * @param categoryCode 分类编号
	 * @return
	 */
	List<SkuCategory> getIdenticalCategory(String storeCode,String categoryCode);
	/**
	 * 获取一级分类
	 * @param categoryCode 分类编号
	 * @return
	 */
	SkuCategory getParentCategory(String storeCode,String categoryCode);

}