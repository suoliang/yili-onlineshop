package com.fushionbaby.sku.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.sku.dao.SkuHotDao;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuCategory;
import com.fushionbaby.sku.service.SkuCategoryService;
import com.fushionbaby.sku.service.SkuHotService;
/***
 * 
 * @author xupeijun
 *
 */
@Service
public class SkuHotServiceImpl implements SkuHotService {

	@Autowired
	private SkuHotDao skuHotDao;
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	
	private static Log LOGGER = LogFactory.getLog(SkuHotServiceImpl.class);


	/* (non-Javadoc)
	 * @see com.fushionbaby.sku.service.SkuHotService#findHotSku(com.fushionbaby.common.condition.sku.SkuEntryQueryCondition)
	 */
	public List<Sku> findHotSkuList(SkuEntryQueryCondition queryCondition) {
		String categoryCode = queryCondition.getCategoryCode();
		try {
			 queryCondition.setCategoryCodes(skuCategoryService.getLowestChildCategorysByCode(queryCondition.getStoreCode(),categoryCode));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return skuHotDao.findHotSkuList(queryCondition);
	}

	



}
