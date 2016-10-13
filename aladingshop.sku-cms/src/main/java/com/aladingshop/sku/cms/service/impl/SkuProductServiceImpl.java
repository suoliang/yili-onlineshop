package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuProductDao;
import com.aladingshop.sku.cms.model.SkuProduct;
import com.aladingshop.sku.cms.service.SkuProductService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @author glc 商品产品表
 */
@Service
public class SkuProductServiceImpl implements SkuProductService<SkuProduct> {

	@Autowired
	private SkuProductDao skuProductDao;

	public void add(SkuProduct model) throws DataAccessException {
		skuProductDao.add(model);
	}

	public void update(SkuProduct model) throws DataAccessException {
		skuProductDao.update(model);
	}

	public List<SkuProduct> findAll() throws DataAccessException {
		return skuProductDao.findAll();
	}

	public void deleteById(Long id) throws DataAccessException {
		skuProductDao.deleteById(id);
	}

	public SkuProduct findById(Long id) throws DataAccessException {
		return skuProductDao.findById(id);
	}

	public BasePagination getListPage(BasePagination page) throws DataAccessException {
		Integer total = skuProductDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SkuProduct> list = skuProductDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SkuProduct>());
		}
		return page;
	}

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

	public List<SkuProduct> queryBySameDayOperateing() {
	
		return skuProductDao.queryBySameDayOperateing();
	}

}
