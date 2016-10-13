package com.aladingshop.sku.cms.service;

import org.springframework.dao.DataAccessException;

import com.aladingshop.sku.cms.model.SkuPromotionsInfo;
import com.fushionbaby.common.util.BasePagination;

public interface SkuPromotionsInfoService <T extends SkuPromotionsInfo> {

	void add(SkuPromotionsInfo skuPromotionsInfo);

	void deleteByPmid(Long pmid);
	
	void deleteByPromotionsCode(String pmCode);

	void updateByPmid(SkuPromotionsInfo skuPromotionsInfo);
	
	void updateByPromotionsCode(SkuPromotionsInfo skuPromotionsInfo);

	SkuPromotionsInfo findByPmid(Long pmid);
	
	SkuPromotionsInfo findByPromotionsCode(String pmCode);
	
	SkuPromotionsInfo findByPromotionsName(String promotionsName);


	/**
	 * 分页查询
	 * @author suoliang
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;

}
