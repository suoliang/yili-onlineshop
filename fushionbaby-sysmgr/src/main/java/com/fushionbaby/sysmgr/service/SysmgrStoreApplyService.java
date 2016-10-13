package com.fushionbaby.sysmgr.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysmgr.model.SysmgrStoreApply;

public interface SysmgrStoreApplyService<T extends SysmgrStoreApply> {
	
	 void add(SysmgrStoreApply sysmgrStoreApply);
	 
	 void deleteById(Long id);
	 
	 void update(SysmgrStoreApply sysmgrStoreApply);
	 
	 SysmgrStoreApply findById(Long id);
	 
	 List<SysmgrStoreApply> findAll();
	 
	 BasePagination getListPage(BasePagination page) throws DataAccessException; 
	 
}
