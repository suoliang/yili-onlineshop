package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sku.model.SkuImageTypeConfig;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SkuImageTypeConfigService<T extends SkuImageTypeConfig> extends
		BaseService<T> {

	BasePagination getListPage(BasePagination page);

	/***
	 * 通过 图片类型的后缀 得到该对象
	 * 
	 * @param string
	 * @return
	 */
	SkuImageTypeConfig findBySuffix(String string);
	
	List<T> findAll();
}
