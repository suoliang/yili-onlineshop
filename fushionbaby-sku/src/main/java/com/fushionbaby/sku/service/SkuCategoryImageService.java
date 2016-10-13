package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.sku.model.SkuCategoryImage;

/***
 * 
 * @description 类描述... 分类图片
 * @author 徐培峻
 * @date 2015年8月6日上午11:18:18
 */
public interface SkuCategoryImageService<T extends SkuCategoryImage>{

	
	List<SkuCategoryImage> findByCategoryCode(String categoryCode);
	
	
	
	List<SkuCategoryImage> findByCategoryCodeAndImageType(String categoryCode,String typeCode);
	
	
}
