package com.fushionbaby.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.sysmgr.model.SysmgrStoreApply;

public interface SysmgrStoreApplyDao {
	
	public void add(SysmgrStoreApply sysmgrStoreApply);

	public void deleteById(Long id);

	public void update(SysmgrStoreApply sysmgrStoreApply);

	public SysmgrStoreApply findById(Long id);

	public List<SysmgrStoreApply> findAll();

	public Integer getTotal(Map<String, Object> map);

	public List<SysmgrStoreApply> getListPage(Map<String, Object> map);

}
