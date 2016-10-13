package com.fushionbaby.auth.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.auth.dao.AuthRolePrivilegeDao;
import com.fushionbaby.auth.model.AuthRolePrivilege;
import com.fushionbaby.auth.service.AuthRolePrivilegeService;
/***
 * 
 * @author xupeijun
 *
 */
@Service
public class AuthRolePrivilegeServiceImpl implements AuthRolePrivilegeService<AuthRolePrivilege>{

	@Autowired
	private AuthRolePrivilegeDao authRolePrivilegeDao;
	
	public void add(AuthRolePrivilege entity) throws DataAccessException {
		this.authRolePrivilegeDao.add(entity);
	}

	public void deleteById(Long id) throws DataAccessException {
	   this.authRolePrivilegeDao.deleteById(id);
	}

	public void update(AuthRolePrivilege entity) throws DataAccessException {
     this.authRolePrivilegeDao.update(entity);		
	}

	public AuthRolePrivilege findById(Long id) throws DataAccessException {
		return this.authRolePrivilegeDao.findById(id);
	}

	public List<AuthRolePrivilege> findAll() throws DataAccessException {
		return this.authRolePrivilegeDao.findAll();
	}
	
	public List<Long> findByRoleId(Long roleId) {
	List<AuthRolePrivilege>	authRolePrivilegelist=this.authRolePrivilegeDao.findByRoleId(roleId);
	List<Long> privilegeIds=new ArrayList<Long>();
	if(authRolePrivilegelist.size()>0){
		for (AuthRolePrivilege authRolePrivilege : authRolePrivilegelist) {
			privilegeIds.add(authRolePrivilege.getPrivilegeId());
		}
	}
		return privilegeIds;
	}

	public void deleteByRoleId(Long id) {
		this.authRolePrivilegeDao.deleteByRoleId(id);
	}

	public void updateRole(Long id, List<Long> allid) {
		this.authRolePrivilegeDao.deleteByRoleId(id);
		AuthRolePrivilege authRolePrivilege=new AuthRolePrivilege();
		authRolePrivilege.setRoleId(id);
		for (Long privilegeId : allid) {
			authRolePrivilege.setPrivilegeId(privilegeId);
			this.authRolePrivilegeDao.add(authRolePrivilege);
		}
	}

	public void deleteByPrivilegeId(String id) {
		this.authRolePrivilegeDao.deleteByPrivilegeId(Long.valueOf(id));
		
	}

}
