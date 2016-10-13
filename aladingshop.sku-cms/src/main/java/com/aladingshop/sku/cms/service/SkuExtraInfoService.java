package com.aladingshop.sku.cms.service;

import com.aladingshop.sku.cms.model.SkuExtraInfo;
import com.fushionbaby.common.util.BasePagination;

/**
 * @author xpf
 * 
 */
public interface SkuExtraInfoService<T extends SkuExtraInfo> {

	void add(SkuExtraInfo skuExtraInfo);
	
	SkuExtraInfo findBySkuCode(String skuCode);
	
	void deleteBySkuCode(String skuCode);
	
	void update(SkuExtraInfo skuExtraInfo);
	
	void addOrUpdate(SkuExtraInfo skuExtraInfo);
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	BasePagination getPageList(BasePagination page);
}
