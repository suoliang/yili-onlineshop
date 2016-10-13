package com.fushionbaby.auth.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.auth.dao.AuthRoleDao;
import com.fushionbaby.auth.model.AuthRole;
import com.fushionbaby.auth.service.AuthRoleService;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author xupeijun
 * 
 */
@Service
public class AuthRoleServiceImpl implements AuthRoleService<AuthRole> {
	@Autowired
	private AuthRoleDao auRoleDao;

	public void add(AuthRole auRole) throws DataAccessException {
		this.auRoleDao.add(auRole);
	}

	public void deleteById(Long id) throws DataAccessException {
		this.auRoleDao.deleteById(id);
	}

	public void update(AuthRole auRole) throws DataAccessException {
		this.auRoleDao.update(auRole);
	}

	public List<AuthRole> findAll() throws DataAccessException {
		return this.auRoleDao.findAll();
	}

	public AuthRole findById(Long id) throws DataAccessException {
		return this.auRoleDao.findById(id);
	}

	/**
	 * 分页 suoliang
	 * 
	 */
	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = auRoleDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<AuthRole> list = auRoleDao
					.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AuthRole>());
		}
		return page;
	}
	public AuthRole findByName(String name) {
		return this.auRoleDao.findByName(name);
	}

}