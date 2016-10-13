package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.sku.model.SkuPrice;

/**
 * @author cyla
 * 
 */
public interface SkuPriceService<T extends SkuPrice> {

	void add(SkuPrice skuPrice);
	
	SkuPrice findBySkuCode(String code);
	
	List<SkuPrice> findAll();
	
	void deleteById(Long id);
	
	void deleteBySkuCode(String skuCode);
	
	void update(SkuPrice skuPrice);
	
	void addOrUpdate(SkuPrice skuPrice);
}
