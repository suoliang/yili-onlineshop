package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuPromotionsInfoDao;
import com.aladingshop.sku.cms.model.SkuPromotionsInfo;
import com.aladingshop.sku.cms.service.SkuPromotionsInfoService;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author King
 * 
 */
@Service
public class SkuPromotionsInfoServiceImpl implements SkuPromotionsInfoService<SkuPromotionsInfo> {

	@Autowired
	private SkuPromotionsInfoDao skuPromotionsInfoDao;

	public void add(SkuPromotionsInfo maPromotionsInfo) throws DataAccessException {
		skuPromotionsInfoDao.add(maPromotionsInfo);
	}

	public void deleteByPmid(Long pmid) throws DataAccessException {
		skuPromotionsInfoDao.deleteByPmid(pmid);
	}

	public void updateByPmid(SkuPromotionsInfo promotionsInfo) throws DataAccessException {
		skuPromotionsInfoDao.updateByPmid(promotionsInfo);
	}

	public SkuPromotionsInfo findByPmid(Long pmid) throws DataAccessException {
		return skuPromotionsInfoDao.findByPmid(pmid);
	}

	/** 分页 */
	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = skuPromotionsInfoDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SkuPromotionsInfo> list = skuPromotionsInfoDao.getList(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SkuPromotionsInfo>());
		}
		return page;
	}

	public SkuPromotionsInfo findByPromotionsName(String promotionsName) {
		return skuPromotionsInfoDao.findByPromotionsName(promotionsName);
	}

	public void deleteByPromotionsCode(String promotionsCode) {
		skuPromotionsInfoDao.deleteByPromotionsCode(promotionsCode);
		
	}

	public void updateByPromotionsCode(SkuPromotionsInfo skuPromotionsInfo) {
		skuPromotionsInfoDao.updateByPromotionsCode(skuPromotionsInfo);
		
	}

	public SkuPromotionsInfo findByPromotionsCode(String promotionsCode) {
		return skuPromotionsInfoDao.findByPromotionsCode(promotionsCode);
	}

	
}
