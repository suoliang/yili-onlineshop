package com.aladingshop.sku.cms.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.sku.cms.model.SkuPromotionsSku;
import com.fushionbaby.common.util.BasePagination;

public interface SkuPromotionsSkuService <T extends SkuPromotionsSku> {

	void add(SkuPromotionsSku skuPromotionsSku);

	void deleteById(Long id);
	
	void deleteByPmCodeAndSkuCode(String pmCode,String skuCode);
	
	void deleteByPmCode(String pmCode);

	void updateById(SkuPromotionsSku skuPromotionsSku);
	
	void updateByPmCodeAndSkuCode(SkuPromotionsSku skuPromotionsSku);

	SkuPromotionsSku findById(Long pmid);
	
	SkuPromotionsSku findByPmCodeAndSkuCode(String pmCode,String skuCode);

	List<String> findSkuCodesByPmCode(String pmCode);
	/**
	 * 分页查询
	 * @author suoliang
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;

}
