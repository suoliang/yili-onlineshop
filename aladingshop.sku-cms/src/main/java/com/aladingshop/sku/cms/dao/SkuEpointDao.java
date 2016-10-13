package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuEpoint;


public interface SkuEpointDao {
	
	public void add(SkuEpoint skuEpoint);
	
	public SkuEpoint findBySkuCode(String skuCode);
	
	public void deleteBySkuCode(String skuCode);
	
	public void update(SkuEpoint skuEpoint);
	
	public Integer getTotal(Map<String, Object> params);
	
	public List<SkuEpoint> getListPage(Map<String, Object> params);

	public void deleteById(Long id);

	public SkuEpoint findById(Long id);
}
