package com.aladingshop.sku.cms.dao;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuPrice;

/**
 * @author cyla
 * 
 */
public interface SkuPriceDao {

	void add(SkuPrice skuPrice);
	
	SkuPrice findBySkuCode(String skuCode);
	
	List<SkuPrice> findAll();
	
	void deleteById(Long id);
	
	void deleteBySkuCode(String skuCode);
	
	void update(SkuPrice skuPrice);

}
