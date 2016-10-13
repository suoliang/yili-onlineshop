package com.fushionbaby.sku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sku.dao.SkuProductDao;
import com.fushionbaby.sku.model.SkuProduct;
import com.fushionbaby.sku.service.SkuProductService;

/**
 * @author glc 商品产品表
 */
@Service
public class SkuProductServiceImpl implements SkuProductService<SkuProduct> {

	@Autowired
	private SkuProductDao skuProductDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuProductService#findByCode(java.lang.String
	 * )
	 */
	public SkuProduct findByCode(String code) {

		return skuProductDao.findByCode(code);
	}

	public SkuProduct existByCode(String code) {

		return skuProductDao.existByCode(code);
	}

	/* (non-Javadoc)
	 * @see com.fushionbaby.sku.service.SkuProductService#queryBySameDayOperateing()
	 */
	public List<SkuProduct> queryBySameDayOperateing() {
		
		
		
		return skuProductDao.queryBySameDayOperateing();
	}

}
