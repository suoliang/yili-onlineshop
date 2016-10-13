/**
 * 
 */
package com.fushionbaby.sku.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sku.dao.SkuKeyWordDao;
import com.fushionbaby.sku.model.SkuKeyWord;
import com.fushionbaby.sku.service.SkuKeyWordService;

/**
 * @description 商品关键字接口实现
 * @author 孙涛
 * @date 2015年9月6日上午11:31:03
 */
@Service
public class SkuKeyWordServiceImpl implements SkuKeyWordService<SkuKeyWord> {
	@Autowired
	private SkuKeyWordDao keyWordDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuKeyWordService#findBySkuCode(java.lang
	 * .String)
	 */
	public SkuKeyWord findByProductCode(String productCode) {
		return keyWordDao.findByProductCode(productCode);
	}

}
