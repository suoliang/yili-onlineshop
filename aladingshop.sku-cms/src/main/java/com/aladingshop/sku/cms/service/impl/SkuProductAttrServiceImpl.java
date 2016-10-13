/**
 * 
 */
package com.aladingshop.sku.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuProductAttrDao;
import com.aladingshop.sku.cms.model.SkuProductAttr;
import com.aladingshop.sku.cms.service.SkuProductAttrService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @author mengshaobo
 * 
 */
@Service
public class SkuProductAttrServiceImpl implements SkuProductAttrService<SkuProductAttr> {

	@Autowired
	private SkuProductAttrDao skuProductAttrDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.web.service.MaGoodsAttrService#add(com.fushionbaby.web
	 * .model.MaGoodsAttr)
	 */
	public void add(SkuProductAttr maGoodsAttr) {
		skuProductAttrDao.add(maGoodsAttr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.web.service.MaGoodsAttrService#deleteById(java.lang.Integer
	 * )
	 */
	public void deleteById(Long id) {
		skuProductAttrDao.deleteById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.web.service.MaGoodsAttrService#update(com.fushionbaby
	 * .web.model.MaGoodsAttr)
	 */
	public void update(SkuProductAttr maGoodsAttr) {
		skuProductAttrDao.update(maGoodsAttr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.web.service.MaGoodsAttrService#findById(java.lang.Integer
	 * )
	 */
	public SkuProductAttr findById(Long id) {
		return skuProductAttrDao.findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.web.service.MaGoodsAttrService#findAll()
	 */
	public List<SkuProductAttr> findAll() {
		return skuProductAttrDao.findAll();
	}

	public BasePagination getListPage(BasePagination page) throws DataAccessException {
		return null;
	}

//	public void moveAttrValue() {
//		List<SkuProductAttrValue> list = skuProductAttrValueDao.findAttrValue();
//
//		for (SkuProductAttrValue attrValue : list) {
//			skuProductAttrValueDao.update(attrValue);
//		}
//	}

	
	public void delAttrByProductCode(String productCode) {
		skuProductAttrDao.delProductAttrByProductCode(productCode);
	}

	public List<SkuProductAttr> findByProductCode(String productCode) {
		
		return skuProductAttrDao.findByProductCode(productCode);
	}



}
