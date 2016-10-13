package com.aladingshop.sku.cms.service;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuHot;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @author xupeijun
 * 
 */
public interface SkuHotService<T extends SkuHot> extends BaseService<T> {

	BasePagination getListPage(BasePagination page);

	void updateCount(Long id, Integer count);

	String findAllCodes();

	/***
	 * 查询前几个商品热销
	 * 
	 * @param size
	 * @return
	 */
	List<SkuHot> findByTop(Integer size);

}
