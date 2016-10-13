package com.fushionbaby.sku.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sku.dao.SkuImageTypeConfigDao;
import com.fushionbaby.sku.model.SkuImageTypeConfig;
import com.fushionbaby.sku.service.SkuImageTypeConfigService;

/**
 * 
 * @author xupeijun
 * 
 */
@Service
public class SkuImageTypeConfigServiceImpl implements
		SkuImageTypeConfigService<SkuImageTypeConfig> {
	@Autowired
	private SkuImageTypeConfigDao skuImgTypeDao;

	public void add(SkuImageTypeConfig entity) throws DataAccessException {
		this.skuImgTypeDao.add(entity);
	}

	public void deleteById(Long id) throws DataAccessException {
		this.skuImgTypeDao.deleteById(id);
	}

	public void update(SkuImageTypeConfig entity) throws DataAccessException {
		this.skuImgTypeDao.update(entity);
	}

	public SkuImageTypeConfig findById(Long id) throws DataAccessException {
		return this.skuImgTypeDao.findById(id);
	}

	public List<SkuImageTypeConfig> findAll() throws DataAccessException {
		return this.skuImgTypeDao.findAll();
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = this.skuImgTypeDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SkuImageTypeConfig> list = this.skuImgTypeDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SkuImageTypeConfig>());
		}
		return page;
	}

	public SkuImageTypeConfig findBySuffix(String string) {
		return this.skuImgTypeDao.findBySuffix(string);
	}

}
