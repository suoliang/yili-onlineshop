package com.fushionbaby.sku.dao;

import java.util.List;
import com.fushionbaby.sku.model.SkuEpoint;

public interface SkuEpointDao {
	
	public SkuEpoint findBySkuCode(String skuCode);

	public List<SkuEpoint> findByLabelCode(String labelCode);
	
}
