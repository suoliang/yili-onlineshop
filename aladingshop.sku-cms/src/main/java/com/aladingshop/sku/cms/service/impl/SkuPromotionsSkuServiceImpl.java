package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuPromotionsSkuDao;
import com.aladingshop.sku.cms.model.SkuPromotionsSku;
import com.aladingshop.sku.cms.service.SkuPromotionsSkuService;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author King
 * 
 */
@Service
public class SkuPromotionsSkuServiceImpl implements SkuPromotionsSkuService<SkuPromotionsSku> {

	@Autowired
	private SkuPromotionsSkuDao skuPromotionsSkuDao;

	public void add(SkuPromotionsSku maPromotionsSku) throws DataAccessException {
		skuPromotionsSkuDao.add(maPromotionsSku);
	}

	public void deleteById(Long id) throws DataAccessException {
		skuPromotionsSkuDao.deleteById(id);
	}

	public void deleteByPmCode(String pmCode) throws DataAccessException {
		skuPromotionsSkuDao.deleteByPmCode(pmCode);
	}
	
	public void updateById(SkuPromotionsSku promotionsSku) throws DataAccessException {
		skuPromotionsSkuDao.updateById(promotionsSku);
	}

	public SkuPromotionsSku findById(Long id) throws DataAccessException {
		return skuPromotionsSkuDao.findById(id);
	}

	/** 分页 */
	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = skuPromotionsSkuDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SkuPromotionsSku> list = skuPromotionsSkuDao.getList(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SkuPromotionsSku>());
		}
		return page;
	}

	public SkuPromotionsSku findByPmCodeAndSkuCode(String pmCode,String skuCode) {
		return skuPromotionsSkuDao.findByPmCodeAndSkuCode(pmCode,skuCode);
	}

	public void deleteByPmCodeAndSkuCode(String pmCode,String skuCode) {
		skuPromotionsSkuDao.deleteByPmCodeAndSkuCode(pmCode,skuCode);
		
	}

	public void updateByPmCodeAndSkuCode(SkuPromotionsSku skuPromotionsSku) {
		skuPromotionsSkuDao.updateByPmCodeAndSkuCode(skuPromotionsSku);
		
	}

	public List<String> findSkuCodesByPmCode(String pmCode) {
		return skuPromotionsSkuDao.findSkuCodesByPmCode(pmCode);
	}


	
}
