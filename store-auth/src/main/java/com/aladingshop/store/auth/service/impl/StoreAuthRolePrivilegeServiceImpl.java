package com.aladingshop.store.auth.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.store.auth.dao.StoreAuthRolePrivilegeDao;
import com.aladingshop.store.auth.model.StoreAuthRolePrivilege;
import com.aladingshop.store.auth.service.StoreAuthRolePrivilegeService;
/***
 * 
 * @author xupeijun
 *
 */
@Service
public class StoreAuthRolePrivilegeServiceImpl implements StoreAuthRolePrivilegeService<StoreAuthRolePrivilege>{

	@Autowired
	private StoreAuthRolePrivilegeDao storeAuthRolePrivilegeDao;
	
	public void add(StoreAuthRolePrivilege entity) throws DataAccessException {
		this.storeAuthRolePrivilegeDao.add(entity);
	}

	public void deleteById(Long id) throws DataAccessException {
	   this.storeAuthRolePrivilegeDao.deleteById(id);
	}

	public void update(StoreAuthRolePrivilege entity) throws DataAccessException {
     this.storeAuthRolePrivilegeDao.update(entity);		
	}

	public StoreAuthRolePrivilege findById(Long id) throws DataAccessException {
		return this.storeAuthRolePrivilegeDao.findById(id);
	}

	public List<StoreAuthRolePrivilege> findAll() throws DataAccessException {
		return this.storeAuthRolePrivilegeDao.findAll();
	}
	
	public List<Long> findByRoleId(Long roleId) {
	List<StoreAuthRolePrivilege>	StoreAuthRolePrivilegelist=this.storeAuthRolePrivilegeDao.findByRoleId(roleId);
	List<Long> privilegeIds=new ArrayList<Long>();
	if(StoreAuthRolePrivilegelist.size()>0){
		for (StoreAuthRolePrivilege StoreAuthRolePrivilege : StoreAuthRolePrivilegelist) {
			privilegeIds.add(StoreAuthRolePrivilege.getPrivilegeId());
		}
	}
		return privilegeIds;
	}

	public void deleteByRoleId(Long id) {
		this.storeAuthRolePrivilegeDao.deleteByRoleId(id);
	}

	public void updateRole(Long id, List<Long> allid,Long currentUserId) {
		this.storeAuthRolePrivilegeDao.deleteByRoleId(id);
		StoreAuthRolePrivilege storeAuthRolePrivilege=new StoreAuthRolePrivilege();
		storeAuthRolePrivilege.setRoleId(id);
		storeAuthRolePrivilege.setUpdateId(currentUserId);
		for (Long privilegeId : allid) {
			storeAuthRolePrivilege.setPrivilegeId(privilegeId);
			this.storeAuthRolePrivilegeDao.add(storeAuthRolePrivilege);
		}
	}

}
