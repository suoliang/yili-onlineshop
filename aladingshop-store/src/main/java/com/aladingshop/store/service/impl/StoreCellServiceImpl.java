package com.aladingshop.store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.dao.StoreCellDao;
import com.aladingshop.store.model.StoreCell;
import com.aladingshop.store.querycondition.StoreCellQueryCondition;
import com.aladingshop.store.service.StoreCellService;
import com.fushionbaby.common.util.BasePagination;
/**
 * 
 * @author King
 *
 */
@Service
public class StoreCellServiceImpl implements StoreCellService<StoreCell> {
	
	@Autowired
	private StoreCellDao storeCellDao;

	public List<StoreCell> queryByCondition(
			StoreCellQueryCondition queryCondition) {
		return storeCellDao.queryByCondition(queryCondition);
	}

	public StoreCell findById(Long id) {
		return storeCellDao.findById(id);
	}

	public void add(StoreCell storeCell) {
		storeCellDao.add(storeCell);
		
	}

	public void update(StoreCell storeCell) {
		storeCellDao.update(storeCell);
		
	}

	public void deleteById(Long id) {
		storeCellDao.deleteById(id);
		
	}

	public BasePagination getPageList(BasePagination pageParams) {
		
		Map<String, Object> map = pageParams.getSearchParamsMap();
		List<StoreCell> storeList = new ArrayList<StoreCell>();
		Integer total = storeCellDao.getTotal(map);
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			storeList = storeCellDao.getList(map);
		}
		pageParams.setResult(storeList);
		return pageParams;
		
	}

	

}
