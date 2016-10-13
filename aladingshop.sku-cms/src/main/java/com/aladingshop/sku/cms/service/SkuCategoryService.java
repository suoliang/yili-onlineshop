package com.aladingshop.sku.cms.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.sku.cms.model.SkuCategory;
import com.fushionbaby.common.condition.sku.SkuCategoryQueryCondition;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author King
 * 
 */
public interface SkuCategoryService<T extends SkuCategory> extends BaseService<T> {
	/**
	 * @param pageParams
	 * @return
	 * @throws DataAccessException
	 */
	BasePagination getListPage(BasePagination pageParams) throws DataAccessException;
	
	
	String findMaxCategoryCode(SkuCategoryQueryCondition queryCondition);
	
	/**
	 * 根据分类等级获取分类集合
	 * @param level 分类级别
	 * @return
	 */
	List<SkuCategory> getCategoryByLevel(Integer level,String storeCode);
	
	/**
	 * 查询条件
	 * @param queryCondition
	 * @return
	 */
	List<SkuCategory> queryByCondition(SkuCategoryQueryCondition queryCondition);
	
	/**
	 * 条件查询获得数量
	 * @param queryCondition
	 * @return
	 */
	Integer queryTotalByCondition(SkuCategoryQueryCondition queryCondition);
	
	
	SkuCategory findByCode(String code,String storeCode) ;
	
	/***
	 * 通过父级分类code查询出子分类集合
	 * @param grandcategoryCode
	 * @return
	 */
	List<SkuCategory> findCategoryByGrandcategoryCode(String grandcategoryCode,String storeCode);
	
	
	List<String> getLowestChildCategorysByCode(String categoryCode,String storeCode) throws Exception  ;
}