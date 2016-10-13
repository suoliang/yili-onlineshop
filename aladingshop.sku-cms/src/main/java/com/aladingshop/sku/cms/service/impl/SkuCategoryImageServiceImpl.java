package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuCategoryImageDao;
import com.aladingshop.sku.cms.model.SkuCategoryImage;
import com.aladingshop.sku.cms.service.SkuCategoryImageService;
import com.fushionbaby.common.util.BasePagination;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月6日上午11:34:03
 */
@Service
public class SkuCategoryImageServiceImpl implements SkuCategoryImageService<SkuCategoryImage>{

	
	@Autowired
	private SkuCategoryImageDao skuCategoryImageDao;
	public void add(SkuCategoryImage entity) throws DataAccessException {
		this.skuCategoryImageDao.add(entity);
	}

	public void deleteById(Long id) throws DataAccessException {
		this.skuCategoryImageDao.deleteById(id);
		
	}

	public void update(SkuCategoryImage entity) throws DataAccessException {
		this.skuCategoryImageDao.update(entity);
	}

	public SkuCategoryImage findById(Long id) throws DataAccessException {
		return this.skuCategoryImageDao.findById(id);
	}

	public BasePagination getListPage(BasePagination pageParams)
			throws DataAccessException {

		Integer total = this.skuCategoryImageDao.getTotal(pageParams.getSearchParamsMap());
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			List<SkuCategoryImage> list = this.skuCategoryImageDao.getListPage(pageParams
					.getSearchParamsMap());
			pageParams.setResult(list);
		} else {
			pageParams.setResult(new ArrayList<SkuCategoryImage>());
		}
		return pageParams;
		
		
	}

	public List<SkuCategoryImage> findAll() {
		return this.skuCategoryImageDao.findAll();
	}

	
}
