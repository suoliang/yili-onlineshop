package com.aladingshop.sku.cms.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.sku.cms.model.SkuCategoryImage;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/***
 * 
 * @description 类描述... 分类图片
 * @author 徐培峻
 * @date 2015年8月6日上午11:18:18
 */
public interface SkuCategoryImageService<T extends SkuCategoryImage> extends BaseService<T>{

	/**
	 * 分页查询分类图片列表
	 * 
	 * @param pageParams
	 * @return
	 * @throws DataAccessException
	 */
	BasePagination getListPage(BasePagination pageParams)throws DataAccessException;

	List<SkuCategoryImage> findAll();
	
	
	
	
}
