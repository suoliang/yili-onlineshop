package com.aladingshop.store.service;

import java.util.List;

import com.aladingshop.store.model.StoreCellRelation;

/**
 * 
 * @author King
 *
 */
public interface StoreCellRelationService<T extends StoreCellRelation> {

	public void add(StoreCellRelation storeCellRelation);
	public void deleteById(Long id);
	public List<StoreCellRelation> findByAreaCode(String cellCode);
	public List<StoreCellRelation> findByStoreCode(String storeCode);
	
}
