package com.aladingshop.store.service;

import java.util.List;

import com.aladingshop.store.model.StoreCell;
import com.aladingshop.store.querycondition.StoreCellQueryCondition;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author King
 *
 */
public interface StoreCellService<T extends StoreCell> {
	
	
	 List<StoreCell> queryByCondition(StoreCellQueryCondition queryCondition);
	 
	 
	 BasePagination getPageList(BasePagination pageParams);
	
	 StoreCell findById(Long id);
	 
	 void add(StoreCell storeCell);
	 
	 void update(StoreCell storeCell);
	 
	 void deleteById(Long id);
	 
}
