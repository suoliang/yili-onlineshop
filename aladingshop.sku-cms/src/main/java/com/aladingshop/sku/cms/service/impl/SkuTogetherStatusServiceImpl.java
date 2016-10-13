/**
 * 
 */
package com.aladingshop.sku.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuTogetherStatusDao;
import com.aladingshop.sku.cms.model.SkuTogetherStatus;
import com.aladingshop.sku.cms.service.SkuTogetherStatusService;

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
