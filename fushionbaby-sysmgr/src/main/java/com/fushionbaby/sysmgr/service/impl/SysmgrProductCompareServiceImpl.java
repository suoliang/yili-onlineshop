package com.fushionbaby.sysmgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sysmgr.dao.SysmgrProductCompareDao;
import com.fushionbaby.sysmgr.model.SysmgrProductCompare;
import com.fushionbaby.sysmgr.service.SysmgrProductCompareService;


@Service
public class SysmgrProductCompareServiceImpl implements SysmgrProductCompareService{

	@Autowired
	private SysmgrProductCompareDao sysmgrProductCompareDao;
	
	
	public void add(SysmgrProductCompare sysmgrProductCompare) {
		sysmgrProductCompareDao.add(sysmgrProductCompare);
	}

	
	public void update(SysmgrProductCompare sysmgrProductCompare) {
		sysmgrProductCompareDao.update(sysmgrProductCompare);
	}

	
	public void deleteSysmgrProductCompareById(long id) {
		sysmgrProductCompareDao.deleteSysmgrProductCompareById(id);
	}

	
	public SysmgrProductCompare getSysmgrProductCompareById(long id) {
		return sysmgrProductCompareDao.getSysmgrProductCompareById(id);
	}
	
	public SysmgrProductCompare getSysmgrProductCompareBySkuId(long skuId){
		return sysmgrProductCompareDao.getSysmgrProductCompareById(skuId);
	}
	
	public List<SysmgrProductCompare> findAll(){
		return sysmgrProductCompareDao.findAll();
	}
}
