package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuCategoryImage;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月6日上午11:15:39
 */
public interface SkuCategoryImageDao {

	
	/**
	 * 
	 * @return 查询全部分类图片
	 */
	List<SkuCategoryImage> findAll();

	/**
	 * 
	 * @param skuCategoryImage
	 */
	void add(SkuCategoryImage skuCategoryImage);

	/***
	 * 
	 * @param id
	 */
	void deleteById(Long id);

	/**
	 * 
	 * @param skuCategoryImage
	 */

	void update(SkuCategoryImage skuCategoryImage);

	/**
	 * 
	 * @param id
	 * @return
	 */
	SkuCategoryImage findById(Long id);

	Integer getTotal(Map<String, Object> searchParamsMap);

	List<SkuCategoryImage> getListPage(Map<String, Object> searchParamsMap);


}
