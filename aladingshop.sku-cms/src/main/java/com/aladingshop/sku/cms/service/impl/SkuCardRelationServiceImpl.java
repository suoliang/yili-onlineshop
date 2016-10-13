package com.aladingshop.sku.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuCardRelationDao;
import com.aladingshop.sku.cms.model.SkuCardRelation;
import com.aladingshop.sku.cms.service.SkuCardRelationService;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年9月7日下午2:10:07
 */
@Service
public class SkuCardRelationServiceImpl implements SkuCardRelationService<SkuCardRelation> {

	
	@Autowired
	private SkuCardRelationDao skuCardRelationDao;
	
	public void add(SkuCardRelation entity) throws DataAccessException {
		this.skuCardRelationDao.add(entity);
	}

	public void deleteById(Long id) throws DataAccessException {
		this.skuCardRelationDao.deleteById(id);
	}

	public void update(SkuCardRelation entity) throws DataAccessException {
        this.skuCardRelationDao.update(entity);		
	}

	public SkuCardRelation findById(Long id) throws DataAccessException {
		return this.skuCardRelationDao.findById(id);
	}
}
