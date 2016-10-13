package com.aladingshop.store.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.store.auth.dao.StoreAuthUserRoleDao;
import com.aladingshop.store.auth.model.StoreAuthUserRole;
import com.aladingshop.store.auth.service.StoreAuthUserRoleService;

/***
 * 
 * @author xupeijun
 * 
 */
@Service
public class StoreAuthUserRoleServiceImpl implements	StoreAuthUserRoleService<StoreAuthUserRole> {

	@Autowired
	private StoreAuthUserRoleDao storeAuthUserRoleDao;

	public void add(StoreAuthUserRole entity) throws DataAccessException {
		this.storeAuthUserRoleDao.add(entity);
	}

	public void deleteById(Long id) throws DataAccessException {
		this.storeAuthUserRoleDao.deleteById(id);
	}

	public void update(StoreAuthUserRole entity) throws DataAccessException {
		this.storeAuthUserRoleDao.update(entity);

	}

	public StoreAuthUserRole findById(Long id) throws DataAccessException {
		return storeAuthUserRoleDao.findById(id);
	}

	public List<StoreAuthUserRole> findAll() throws DataAccessException {
		return storeAuthUserRoleDao.findAll();
	}

	public List<StoreAuthUserRole> findByRoleId(Long roleId) {
		return storeAuthUserRoleDao.findByRoleId(roleId);
	}

	public void deleteByUserId(Long id) {
		this.storeAuthUserRoleDao.deleteByUserId(id);
	}

	public List<StoreAuthUserRole> findByUserId(Long id) {
		return this.storeAuthUserRoleDao.findByUserId(id);
	}

}
