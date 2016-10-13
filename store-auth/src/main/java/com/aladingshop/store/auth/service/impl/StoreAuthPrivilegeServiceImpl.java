package com.aladingshop.store.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.store.auth.dao.StoreAuthPrivilegeDao;
import com.aladingshop.store.auth.model.StoreAuthPrivilege;
import com.aladingshop.store.auth.service.StoreAuthPrivilegeService;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author xupeijun
 * 
 */
@Service
public class StoreAuthPrivilegeServiceImpl implements StoreAuthPrivilegeService<StoreAuthPrivilege> {
	@Autowired
	private StoreAuthPrivilegeDao storeAuPrivilegeDao;

	public void deleteById(Long id) throws DataAccessException {
		storeAuPrivilegeDao.deleteById(id);
	}

	public void add(StoreAuthPrivilege auPrivilege) throws DataAccessException {
		storeAuPrivilegeDao.add(auPrivilege);
	}

	public StoreAuthPrivilege findById(Long id) throws DataAccessException {
		return storeAuPrivilegeDao.findById(id);
	}

	public List<StoreAuthPrivilege> findAll() throws DataAccessException {
		return storeAuPrivilegeDao.findAll();
	}

	public void update(StoreAuthPrivilege auPrivilege) throws DataAccessException {
		this.storeAuPrivilegeDao.update(auPrivilege);
	}

	public List<StoreAuthPrivilege> findByParentId(Long parentId) {
		return this.storeAuPrivilegeDao.findByParentId(parentId);
	}
	public List<StoreAuthPrivilege> findByLevel(Integer level) {
		return this.storeAuPrivilegeDao.findByLevel(level);
	}
	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {

		Integer total = this.storeAuPrivilegeDao.getTotal(page.getSearchParamsMap());
		   page.setCurrentTotal(total);
		if (total > 0) {
			List<StoreAuthPrivilege> list = this.storeAuPrivilegeDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<StoreAuthPrivilege>());
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
       List<StoreAuthPrivilege> first_second=this.storeAuPrivilegeDao.findByParentId(firstPrivilegeId);
			if(first_second.size()>0){
				for (StoreAuthPrivilege StoreAuthPrivilege : first_second) {
                        	secondids.add(StoreAuthPrivilege.getId());
				}
			}
	return secondids;
}

	public List<StoreAuthPrivilege> findAllFirst() {
		return this.storeAuPrivilegeDao.findAllFirst();
	}
	
/*	public List<StoreAuthPrivilege> listParentPrivilegeByUserId(Long userId) {
		return this.storeAuPrivilegeDao.listParentPrivilegeByUserId(userId);
	}*/

	public List<StoreAuthPrivilege> findFirstByStoreCode(String storeCode) {
		
		return this.storeAuPrivilegeDao.findFirstByStoreCode(storeCode);
	}


}