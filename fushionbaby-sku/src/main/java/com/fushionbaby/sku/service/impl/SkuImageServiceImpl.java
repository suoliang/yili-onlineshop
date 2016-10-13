package com.fushionbaby.sku.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sku.dao.SkuImageDao;
import com.fushionbaby.sku.model.SkuImage;
import com.fushionbaby.sku.service.SkuImageService;

/***
 * 
 * @author xupeijun
 * 
 */
@Service
public class SkuImageServiceImpl implements SkuImageService<SkuImage> {

	@Autowired
	private SkuImageDao skuImgDao;

	public void add(SkuImage entity) throws DataAccessException {
		this.skuImgDao.add(entity);
	}

	public void deleteById(Long id) throws DataAccessException {

		this.skuImgDao.deleteById(id);
	}

	public void update(SkuImage entity) throws DataAccessException {
		this.skuImgDao.update(entity);
	}

	public SkuImage findById(Long id) throws DataAccessException {
		return this.skuImgDao.findById(id);
	}

	public List<SkuImage> findAll() throws DataAccessException {
		return this.skuImgDao.findAll();
	}

	public List<SkuImage> findBySkuCode(String skuCode) {
		return this.skuImgDao.findBySkuCode(skuCode);
	}
	
	public SkuImage findImageBySkuCode(String skuCode, String imageType) {
		return this.skuImgDao.findImageBySkuCode(skuCode, imageType);
	}

	public boolean IsHaveImage(String skuCode) {
		List<SkuImage> list = this.skuImgDao.findBySkuCode(skuCode);

		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuImageService#findBySkuCodeByImageTypeCode
	 * (java.lang.String, java.lang.String)
	 */
	public List<SkuImage> findBySkuCodeByImageTypeCode(String skuCode,
			String imageTypeCode) {

		return skuImgDao.findBySkuCodeByImageTypeCode(skuCode, imageTypeCode);
	}

	public BasePagination getListPage(BasePagination page) {

		Integer total = this.skuImgDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SkuImage> list = this.skuImgDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SkuImage>());
		}
		return page;
	}


}
