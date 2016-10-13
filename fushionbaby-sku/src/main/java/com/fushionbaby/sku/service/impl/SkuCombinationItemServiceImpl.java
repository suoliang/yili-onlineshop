/**
 * 
 */
package com.fushionbaby.sku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sku.dao.SkuCombinationItemDao;
import com.fushionbaby.sku.model.SkuCombinationItem;
import com.fushionbaby.sku.service.SkuCombinationItemService;

/**
 * @author mengshaobo
 *
 */
@Service
public  class SkuCombinationItemServiceImpl implements SkuCombinationItemService {

	@Autowired
	private SkuCombinationItemDao dao;
	
	public SkuCombinationItem queryById(Long id) {
		return dao.queryById(id);
	}

	public SkuCombinationItem queryListBySkuCode(String skuCode) {
		return dao.queryListBySkuCode(skuCode);
	}

	public List<SkuCombinationItem> queryListByCombinationId(Long combinationId) {
		return dao.queryListByCombinationId(combinationId);
	}

	public void deleteByCombinationId(Long combinationId) {
		dao.deleteByCombinationId(combinationId);
	}

	public void add(SkuCombinationItem item) {
		dao.add(item);
		
	}

	public void deleteBySkuCode(String skuCode) {
		dao.deleteBySkuCode(skuCode);
		
	}

	public void deleteBySkuId(Long skuId) {
		// TODO Auto-generated method stub
		
	}

	public SkuCombinationItem queryListBySkuId(Long skuId) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
