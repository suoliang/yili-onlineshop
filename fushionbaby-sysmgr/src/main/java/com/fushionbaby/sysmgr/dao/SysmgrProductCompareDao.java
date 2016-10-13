package com.fushionbaby.sysmgr.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.sysmgr.model.SysmgrProductCompare;


/**
 * 
 * @author cyla
 * 
 */
public interface SysmgrProductCompareDao {

	public void add(SysmgrProductCompare sysmgrProductCompare);
	
	public void update(SysmgrProductCompare sysmgrProductCompare);
	
	public void deleteSysmgrProductCompareById(long id);
	
	public SysmgrProductCompare getSysmgrProductCompareById(long id);
	
	public SysmgrProductCompare getSysmgrProductCompareBySkuId(long skuId);
	
	public List<SysmgrProductCompare> findAll();
}
