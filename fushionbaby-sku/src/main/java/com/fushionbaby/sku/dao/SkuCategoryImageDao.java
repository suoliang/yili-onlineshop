package com.fushionbaby.sku.dao;

import java.util.List;

import com.fushionbaby.sku.model.SkuCategoryImage;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月6日上午11:15:39
 */
public interface SkuCategoryImageDao {


	List<SkuCategoryImage> findByCategoryCode(String categoryCode);

}
