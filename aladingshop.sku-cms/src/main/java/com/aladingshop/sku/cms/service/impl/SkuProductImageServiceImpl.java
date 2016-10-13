package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aladingshop.sku.cms.dao.SkuProductImageDao;
import com.aladingshop.sku.cms.model.SkuProductImage;
import com.aladingshop.sku.cms.service.SkuProductImageService;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author cyla
 * 
 */
@Service
public class SkuProductImageServiceImpl implements SkuProductImageService<SkuProductImage> {

	@Autowired
	private SkuProductImageDao skuProductImageDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuProductImageService#findBySkuProductCode
	 * (java.lang.String)
	 */
	public List<SkuProductImage> findBySkuProductCode(String skuProductCode) {
		return skuProductImageDao.findBySkuProductCode(skuProductCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuProductImageService#add(com.fushionbaby
	 * .sku.model.SkuProductImage)
	 */
	public void add(SkuProductImage skuProductImage) {
		skuProductImageDao.add(skuProductImage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuProductImageService#update(com.fushionbaby
	 * .sku.model.SkuProductImage)
	 */
	public void update(SkuProductImage skuProductImage) {
		skuProductImageDao.update(skuProductImage);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuProductImageService#deleteById(java.lang
	 * .Long)
	 */
	public void deleteByProductCode(String productCode) {
		List<SkuProductImage> skuProductImage = skuProductImageDao
				.findBySkuProductCode(productCode);
		if (!CollectionUtils.isEmpty(skuProductImage)) {
			skuProductImageDao.deleteByProductCode(productCode);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuProductImageService#findById(java.lang
	 * .Long)
	 */
	public SkuProductImage findById(Long id) {
		return skuProductImageDao.findById(id);
	}

	public List<SkuProductImage> findAllBySkuProductCode(String skuProductCode) {

		return skuProductImageDao.findAllBySkuProductCode(skuProductCode);
	}

	public BasePagination findBySkuProductCode(BasePagination page) {
		int total = skuProductImageDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			page.setResult(skuProductImageDao.getListPage(page.getSearchParamsMap()));
		}else {
			page.setResult(new ArrayList<SkuProductImage>());
		}
		return page;
	}

	public void deleteById(Long id) {
		skuProductImageDao.deleteById(id);
	}
	
	/**
	 * 通过产品编号和产品名称模糊查询产品图片集合 cms端 实现分页
	 * 
	 * @param page
	 * @return
	 */
	public BasePagination findBySkuProductCodeAndProductName(BasePagination page) {
		int total = skuProductImageDao.getTotalByProductCodeAndProductName(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			page.setResult(skuProductImageDao.getListPageByProductCodeAndProductName(page.getSearchParamsMap()));
		}else {
			page.setResult(new ArrayList<SkuProductImage>());
		}
		return page;
	}
}
