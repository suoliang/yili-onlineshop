package com.fushionbaby.sku.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fushionbaby.sku.dao.SkuEpointDao;
import com.fushionbaby.sku.model.SkuEpoint;
import com.fushionbaby.sku.service.SkuEpointService;

@Service
public class SkuEpointServiceImpl implements SkuEpointService{
	@Autowired
	private SkuEpointDao skuEpointDao;

	public SkuEpoint findBySkuCode(String skuCode) {
		return skuEpointDao.findBySkuCode(skuCode);
	}

	public List<SkuEpoint> findByLabelCode(String labelCode) {
		return skuEpointDao.findByLabelCode(labelCode);
	}
	
}
