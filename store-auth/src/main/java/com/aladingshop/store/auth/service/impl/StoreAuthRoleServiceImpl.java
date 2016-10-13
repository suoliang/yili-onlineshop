package com.aladingshop.store.auth.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.store.auth.dao.StoreAuthRoleDao;
import com.aladingshop.store.auth.model.StoreAuthRole;
import com.aladingshop.store.auth.service.StoreAuthRoleService;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author xupeijun
 * 
 */
@Service
public class StoreAuthRoleServiceImpl implements StoreAuthRoleService<StoreAuthRole> {
	@Autowired
	private StoreAuthRoleDao storeAuRoleDao;

	public void add(StoreAuthRole auRole) throws DataAccessException {
		this.storeAuRoleDao.add(auRole);
	}

	public void deleteById(Long id) throws DataAccessException {
		this.storeAuRoleDao.deleteById(id);
	}

	public void update(StoreAuthRole auRole) throws DataAccessException {
		this.storeAuRoleDao.update(auRole);
	}

	public List<StoreAuthRole> findAll(String storeCode) throws DataAccessException {
		return this.storeAuRoleDao.findAll(storeCode);
	}

	public StoreAuthRole findById(Long id) throws DataAccessException {
		return this.storeAuRoleDao.findById(id);
	}

	/**
	 * 分页 suoliang
	 * 
	 */
	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = storeAuRoleDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<StoreAuthRole> list = storeAuRoleDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<StoreAuthRole>());
		}
		return page;
	}


}