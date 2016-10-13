/**
 * 
 */
package com.aladingshop.sku.cms.service.impl;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuCombinationDao;
import com.aladingshop.sku.cms.dao.SkuCombinationItemDao;
import com.aladingshop.sku.cms.model.SkuCombination;
import com.aladingshop.sku.cms.model.SkuCombinationItem;
import com.aladingshop.sku.cms.service.SkuCombinationService;
import com.fushionbaby.common.condition.sku.SkuCombinationQueryCondition;

/**
 * @author mengshaobo
 *
 */
@Service
public class SkuCombinationServiceImpl implements SkuCombinationService {
	
	@Autowired
	private SkuCombinationItemDao skuCombinationItemDao;

	@Autowired
	private SkuCombinationDao skuCombinationDao;
	/* (non-Javadoc)
	 * @see com.fushionbaby.sku.service.SkuCombinationService#queryById(java.lang.Long)
	 */
	public SkuCombination queryById(Long id) {
		
		return skuCombinationDao.queryById(id);
	}
	/*
	 * (non-Javadoc)
	 * @see com.fushionbaby.sku.service.SkuCombinationService#queryBySkuId(java.lang.Long)
	 */
	
	public SkuCombination queryBySkuCode(String skuCode) {
		SkuCombinationItem item = skuCombinationItemDao.queryListBySkuCode(skuCode);
		if(ObjectUtils.equals(null, item)){
			return null;
		}
		return this.queryById(item.getCombinationId());
	}

	public List<SkuCombination> queryByConditon(SkuCombinationQueryCondition queryCondition) {
		return skuCombinationDao.queryByConditon(queryCondition);
	}

	public int getTotalByCondition(SkuCombinationQueryCondition queryCondition) {
		
		return skuCombinationDao.getTotalByCondition(queryCondition);
	}

	public void add(SkuCombination skuCombination) {
		skuCombinationDao.add(skuCombination);		
	}

	public void update(SkuCombination skuCombination) {
		skuCombinationDao.update(skuCombination);
	}

	public void deleteById(Long id) {
		skuCombinationDao.deleteById(id);
		
	}

}
