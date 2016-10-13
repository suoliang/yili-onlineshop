package com.fushionbaby.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysmgr.dao.SysmgrStoreApplyDao;
import com.fushionbaby.sysmgr.model.SysmgrStoreApply;
import com.fushionbaby.sysmgr.service.SysmgrStoreApplyService;


@Service
public class SysmgrStoreApplyServiceImpl implements SysmgrStoreApplyService<SysmgrStoreApply> {
	
	@Autowired
	private SysmgrStoreApplyDao sysmgrStoreApplyDao;

	public void add(SysmgrStoreApply sysmgrStoreApply) {
		this.sysmgrStoreApplyDao.add(sysmgrStoreApply);
	}

	public void deleteById(Long id) {
		this.sysmgrStoreApplyDao.deleteById(id);
	}

	public void update(SysmgrStoreApply sysmgrStoreApply) {
		this.sysmgrStoreApplyDao.update(sysmgrStoreApply);
	}

	public SysmgrStoreApply findById(Long id) {
		return this.sysmgrStoreApplyDao.findById(id);
	}

	public List<SysmgrStoreApply> findAll() {
		return this.sysmgrStoreApplyDao.findAll();
	}

	public BasePagination getListPage(BasePagination page){
		Integer total = this.sysmgrStoreApplyDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SysmgrStoreApply> list = this.sysmgrStoreApplyDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SysmgrStoreApply>());
		}
		return page;
	}

}
