package com.aladingshop.sku.cms.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuEpointDao;
import com.aladingshop.sku.cms.model.SkuEpoint;
import com.aladingshop.sku.cms.service.SkuEpointService;
import com.fushionbaby.common.util.BasePagination;


@Service
public class SkuEpointServiceImpl implements SkuEpointService<SkuEpoint>{
	@Autowired
	private SkuEpointDao skuEpointDao;

	public void add(SkuEpoint skuEpoint) {
		skuEpointDao.add(skuEpoint);
		
	}

	public SkuEpoint findBySkuCode(String skuCode) {
		return skuEpointDao.findBySkuCode(skuCode);
	}

	public void deleteBySkuCode(String skuCode) {
		skuEpointDao.deleteBySkuCode(skuCode);
	}

	public void update(SkuEpoint skuEpoint) {
		skuEpointDao.update(skuEpoint);
		
	}

	public BasePagination getListPage(BasePagination page)throws DataAccessException {
		Map<String,Object> params = page.getSearchParamsMap();
		Integer total = skuEpointDao.getTotal(params);
		List<SkuEpoint> result = new ArrayList<SkuEpoint>();
		if(total > 0){
			result = skuEpointDao.getListPage(params);
		}
		page.setCurrentTotal(total);
		page.setResult(result);
		return page;
	}

	public void deleteById(Long id) {
		skuEpointDao.deleteById(id);	
	}

	public SkuEpoint findById(Long id) {
		return skuEpointDao.findById(id);
	}
	
	
}
