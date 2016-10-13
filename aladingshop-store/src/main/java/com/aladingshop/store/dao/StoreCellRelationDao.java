package com.aladingshop.store.dao;

import java.util.List;

import com.aladingshop.store.model.StoreCellRelation;
/**
 * 
 * @author King
 *
 */
public interface StoreCellRelationDao {
	
	public void add(StoreCellRelation storeCellRelation);
	
	public void deleteById(Long id);
	
	public List<StoreCellRelation> findByAreaCode(String cellCode);
	
	public List<StoreCellRelation> findByStoreCode(String storeCode);
}
