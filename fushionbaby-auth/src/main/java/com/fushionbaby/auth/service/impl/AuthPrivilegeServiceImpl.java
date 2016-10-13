package com.fushionbaby.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.auth.dao.AuthPrivilegeDao;
import com.fushionbaby.auth.model.AuthPrivilege;
import com.fushionbaby.auth.service.AuthPrivilegeService;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author xupeijun
 * 
 */
@Service
public class AuthPrivilegeServiceImpl implements AuthPrivilegeService<AuthPrivilege> {
	@Autowired
	private AuthPrivilegeDao auPrivilegeDao;

	public void deleteById(Long id) throws DataAccessException {
		auPrivilegeDao.deleteById(id);
	}

	public void add(AuthPrivilege auPrivilege) throws DataAccessException {
		auPrivilegeDao.add(auPrivilege);
	}

	public AuthPrivilege findById(Long id) throws DataAccessException {
		return auPrivilegeDao.findById(id);
	}

	public List<AuthPrivilege> findAll() throws DataAccessException {
		return auPrivilegeDao.findAll();
	}

	public void update(AuthPrivilege auPrivilege) throws DataAccessException {
		this.auPrivilegeDao.update(auPrivilege);
	}

	public List<AuthPrivilege> findByParentId(Long parentId) {
		return this.auPrivilegeDao.findByParentId(parentId);
	}
	public List<AuthPrivilege> findByLevel(Integer level) {
		return this.auPrivilegeDao.findByLevel(level);
	}
	public List<AuthPrivilege> findAllFirst() {
		return this.auPrivilegeDao.findAllFirst();
	}
	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {

		Integer total = this.auPrivilegeDao.getTotal(page.getSearchParamsMap());
		   page.setCurrentTotal(total);
		if (total > 0) {
			List<AuthPrivilege> list = this.auPrivilegeDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AuthPrivilege>());
		}
		return page;
	}

/**
 *  通过第一级权限的id字符串 得到二级的和一级的id集合
 */
	public List<Long> getallidsByFirsts(String[] first) {
	  List<Long> allids=new ArrayList<Long>();
	   List<Long> firstids=new ArrayList<Long>();
	   List<Long> secondids=new ArrayList<Long>();
		for (String str : first) {
			firstids.add(Long.valueOf(str));
			secondids=getSecondPrivilegeIdListByFirstPrivilegeId(Long.valueOf(str));	
		}
		allids.addAll(firstids);
		allids.addAll(secondids);
		return allids;
	}
/***
 * 通过一级权限的id得到，一级权限下的二级权限id列表，并返回
 * @param valueOf  20150205
 * @return
 */
private List<Long> getSecondPrivilegeIdListByFirstPrivilegeId(Long firstPrivilegeId) {
	   List<Long> secondids=new ArrayList<Long>();
       List<AuthPrivilege> first_second=this.auPrivilegeDao.findByParentId(firstPrivilegeId);
			if(first_second.size()>0){
				for (AuthPrivilege authPrivilege : first_second) {
                        	secondids.add(authPrivilege.getId());
				}
			}
	return secondids;
}

public List<AuthPrivilege> listParentPrivilegeByUserId(Long userId) {
	return this.auPrivilegeDao.listParentPrivilegeByUserId(userId);
}

}