package com.aladingshop.sku.cms.service;

import com.aladingshop.sku.cms.model.SkuEpoint;
import com.fushionbaby.common.util.BasePagination;


public interface SkuEpointService<T extends SkuEpoint> {
	
	public void add(SkuEpoint skuEpoint);
	
	public SkuEpoint findBySkuCode(String skuCode);
	
	public void deleteBySkuCode(String skuCode);
	
	public void update(SkuEpoint skuEpoint);
	
	
	public BasePagination getListPage(BasePagination page);

	public void deleteById(Long id);

	public SkuEpoint findById(Long id);
	
}
