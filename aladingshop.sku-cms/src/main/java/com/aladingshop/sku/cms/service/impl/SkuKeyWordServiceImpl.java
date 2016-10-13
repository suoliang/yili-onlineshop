/**
 * 
 */
package com.aladingshop.sku.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuKeyWordDao;
import com.aladingshop.sku.cms.model.SkuKeyWord;
import com.aladingshop.sku.cms.service.SkuKeyWordService;

/**
 * @description 商品关键字查询接口实现
 * @author 孙涛
 * @date 2015年9月6日上午11:39:27
 */
@Service
public class SkuKeyWordServiceImpl implements SkuKeyWordService<SkuKeyWord> {
	@Autowired
	private SkuKeyWordDao skuKeyWordDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aladingshop.sku.cms.service.SkuKeyWordService#findBySkuCode(java.
	 * lang.String)
	 */
	public SkuKeyWord findByProductCode(String productCode) {
		return skuKeyWordDao.findByProductCode(productCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aladingshop.sku.cms.service.SkuKeyWordService#addKey(com.aladingshop
	 * .sku.cms.model.SkuKeyWord)
	 */
	public void addKey(SkuKeyWord word) {
		skuKeyWordDao.addKey(word);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aladingshop.sku.cms.service.SkuKeyWordService#updateKey(com.aladingshop
	 * .sku.cms.model.SkuKeyWord)
	 */
	public void updateKey(SkuKeyWord word) {
		skuKeyWordDao.updateKey(word);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aladingshop.sku.cms.service.SkuKeyWordService#deleteKey(java.lang
	 * .Long)
	 */
	public void deleteKey(Long id) {
		skuKeyWordDao.deleteKey(id);
	}

}
