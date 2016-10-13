package com.fushionbaby.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.auth.dao.AuthUserRoleDao;
import com.fushionbaby.auth.model.AuthUserRole;
import com.fushionbaby.auth.service.AuthUserRoleService;

/***
 * 
 * @author xupeijun
 * 
 */
@Service
public class AuthUserRoleServiceImpl implements
		AuthUserRoleService<AuthUserRole> {

	@Autowired
	private AuthUserRoleDao authUserRoleDao;

	public void add(AuthUserRole entity) throws DataAccessException {
		this.authUserRoleDao.add(entity);
	}

	public void deleteById(Long id) throws DataAccessException {
		this.authUserRoleDao.deleteById(id);
	}

	public void update(AuthUserRole entity) throws DataAccessException {
		this.authUserRoleDao.update(entity);

	}

	public AuthUserRole findById(Long id) throws DataAccessException {
		return authUserRoleDao.findById(id);
	}

	public List<AuthUserRole> findAll() throws DataAccessException {
		return authUserRoleDao.findAll();
	}

	public List<AuthUserRole> findByRoleId(Long roleId) {
		return authUserRoleDao.findByRoleId(roleId);
	}

	public void deleteByUserId(Long id) {
		this.authUserRoleDao.deleteByUserId(id);
	}

	public List<AuthUserRole> findByUserId(Long id) {
		return this.authUserRoleDao.findByUserId(id);
	}

}
