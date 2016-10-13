package com.fushionbaby.sysmgr.service;

import java.util.List;

import com.fushionbaby.sysmgr.model.SysmgrProductCompare;


/**
 * 
 * @author cyla
 * 
 */
public interface SysmgrProductCompareService {
    
	public void add(SysmgrProductCompare sysmgrProductCompare);
	
	
	public void update(SysmgrProductCompare sysmgrProductCompare);
	
	
	public void deleteSysmgrProductCompareById(long id);
	
	
	public SysmgrProductCompare getSysmgrProductCompareById(long id);
	
	public SysmgrProductCompare getSysmgrProductCompareBySkuId(long skuId);
	
	public List<SysmgrProductCompare> findAll();
}
