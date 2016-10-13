package com.aladingshop.sku.cms.service;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuImageType;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月12日上午11:04:21
 */
public interface SkuImageTypeService<T extends SkuImageType>  extends BaseService<T>{

	BasePagination getListPage(BasePagination page);

	List<SkuImageType> findAll();

	SkuImageType findByCode(String code);

	/***
	 * 修改状态
	 * @param id
	 * @param status
	 */
	void updateStatus(Long id, String status);
}
