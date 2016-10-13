/**
 * 
 */
package com.fushionbaby.sku.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sku.dao.SkuTogetherStatusDao;
import com.fushionbaby.sku.model.SkuBrandRelation;
import com.fushionbaby.sku.model.SkuCategoryRelation;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuGiftRelation;
import com.fushionbaby.sku.model.SkuLinkSkusRelation;
import com.fushionbaby.sku.model.SkuTogether;
import com.fushionbaby.sku.model.SkuTogetherStatus;
import com.fushionbaby.sku.service.SkuTogetherStatusService;

/**
 * @author mengshaobo
 * 
 */
@Service
public class SkuTogetherStatusServiceImpl implements SkuTogetherStatusService<SkuTogetherStatus> {
	@Autowired
	private SkuTogetherStatusDao skuTogetherStatusDao;

	public void add(SkuTogetherStatus skuTogetherStatus){
		this.skuTogetherStatusDao.add(skuTogetherStatus);
	}

	public void updateBySkuTogetherId(SkuTogetherStatus skuTogetherStatus) {
		this.skuTogetherStatusDao.updateBySkuTogetherId(skuTogetherStatus);
	}

	public SkuTogetherStatus findBySkuTogetherId(Long skuTogetherId) {
		return this.skuTogetherStatusDao.findBySkuTogetherId(skuTogetherId);
	}

	public void deleteBySkuTogetherId(Long skuTogetherId) {
		this.skuTogetherStatusDao.deleteBySkuTogetherId(skuTogetherId);
	}
}
