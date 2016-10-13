package com.aladingshop.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.dao.StoreCellRelationDao;
import com.aladingshop.store.model.StoreCellRelation;
import com.aladingshop.store.service.StoreCellRelationService;
/**
 * 
 * @author King
 *
 */
@Service
public class StoreCellRelationServiceImpl implements StoreCellRelationService<StoreCellRelation> {
	
	@Autowired
	private StoreCellRelationDao storeCellRelationDao;

	public List<StoreCellRelation> findByAreaCode(String cellCode) {
		return storeCellRelationDao.findByAreaCode(cellCode);
	}

	public List<StoreCellRelation> findByStoreCode(String storeCode) {
		return storeCellRelationDao.findByStoreCode(storeCode);
	}

	public void add(StoreCellRelation storeCellRelation) {
		storeCellRelationDao.add(storeCellRelation);
	}

	public void deleteById(Long id) {
		storeCellRelationDao.deleteById(id);		
	}





}
