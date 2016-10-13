package com.aladingshop.store.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.store.model.StoreCell;
import com.aladingshop.store.querycondition.StoreCellQueryCondition;
/**
 * 
 * @author King
 *
 */
public interface StoreCellDao {
	
	
	 List<StoreCell> queryByCondition(StoreCellQueryCondition queryCondition);
	 
	 
	 Integer getTotal(Map<String, Object> map);
	 
	 List<StoreCell> getList(Map<String, Object> map);
	 
	 StoreCell findById(Long id);      
	 
	 void add(StoreCell storeCell);
	 
	 void update(StoreCell storeCell);
	 
	 void deleteById(Long id);
	 
	 
}
