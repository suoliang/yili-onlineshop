package com.aladingshop.store.auth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.store.auth.dao.StoreAuthUserDao;
import com.aladingshop.store.auth.model.StoreAuthPrivilege;
import com.aladingshop.store.auth.model.StoreAuthRolePrivilege;
import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.auth.model.StoreAuthUserRole;
import com.aladingshop.store.auth.service.StoreAuthPrivilegeService;
import com.aladingshop.store.auth.service.StoreAuthRolePrivilegeService;
import com.aladingshop.store.auth.service.StoreAuthUserRoleService;
import com.aladingshop.store.auth.service.StoreAuthUserService;
import com.fushionbaby.common.constants.CmsConstant;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author xupeijun
 * 
 */
@Service
public class StoreAuthUserServiceImpl implements StoreAuthUserService<StoreAuthUser> {

	@Autowired
	private StoreAuthUserDao storeAuthUserDao;
    
	@Autowired
	private StoreAuthPrivilegeService<StoreAuthPrivilege> storeAuthPrivilegeService;

    @Autowired
    private StoreAuthUserRoleService<StoreAuthUserRole> storeAuthUserRoleService;
    
	@Autowired
    private StoreAuthRolePrivilegeService<StoreAuthRolePrivilege> authRolePrivilegeService;
	
	@Autowired
	private StoreAuthPrivilegeService<StoreAuthPrivilege> authPrivilegeService;

	public void add(StoreAuthUser StoreAuthUser) throws DataAccessException {
		this.storeAuthUserDao.add(StoreAuthUser);
	}

	public void deleteById(Long id) throws DataAccessException {
		this.storeAuthUserDao.deleteById(id);
	}

	public void update(StoreAuthUser StoreAuthUser) throws DataAccessException {
		storeAuthUserDao.update(StoreAuthUser);
	}
	public void updateIsDisabled(Long id, String isDisabled) {
		StoreAuthUser storeAuthUser = new StoreAuthUser();
		storeAuthUser.setId(id);
		storeAuthUser.setIsDisabled(isDisabled);
		storeAuthUserDao.updateIsDisabled(storeAuthUser);
	}

	public StoreAuthUser findById(Long id) throws DataAccessException {
		return storeAuthUserDao.findById(id);
	}

	public List<StoreAuthUser> findAll() throws DataAccessException {
		return this.storeAuthUserDao.findAll();
	}

	public StoreAuthUser findStoreAuthUserByUserNameAndStoreNumber(String userName,String storeNumber) {
		return this.storeAuthUserDao.findStoreAuthUserByUserNameAndStoreNumber(userName,storeNumber);
	}

	public StoreAuthUser isSystemUser(Long id) {
		return this.storeAuthUserDao.isSystemUser(id);
	}
	
	public BasePagination getListPage(BasePagination page) {
		Integer total = this.storeAuthUserDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<StoreAuthUser> list = this.storeAuthUserDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<StoreAuthUser>());
		}
		return page;
	}

	/**
	 * 以系统用户身份登录，能够得到所有权限
	 */
	public List<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>> loginAsSystem() {
		List<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>> menuPrivilegeList = new ArrayList<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>>();
		Map<StoreAuthPrivilege, List<StoreAuthPrivilege>> menuPrivilege = new LinkedHashMap<StoreAuthPrivilege, List<StoreAuthPrivilege>>();
		List<StoreAuthPrivilege> first = this.storeAuthPrivilegeService.findAllFirst();
		menuPrivilege=getFirstAndSecondPrivilegeMapByFirstPrivilegeList(first);
		menuPrivilegeList.add(menuPrivilege);
		return menuPrivilegeList;
	}

	/**
	 * 以普通用户的身份登录 只要拥有了第一级权限 就拥有了第一级下的所有的第二级权限
	 */
//	public List<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>> loginAsNormalUser(Long userId) {
//		List<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>> menuPrivilegeList = new ArrayList<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>>();
//		Map<StoreAuthPrivilege, List<StoreAuthPrivilege>> menuPrivilege = new LinkedHashMap<StoreAuthPrivilege, List<StoreAuthPrivilege>>();
//		List<StoreAuthPrivilege> first = this.storeAuthPrivilegeService.listParentPrivilegeByUserId(userId);
//		if (first.size() > 0) {
//			menuPrivilege=getFirstAndSecondPrivilegeMapByFirstPrivilegeList(first);
//			menuPrivilegeList.add(menuPrivilege);
//		}
//		return menuPrivilegeList;
//	}

	
	
	public List<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>> loginAsNormalUser(Long id) {
		List<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>> menuPrivilegeList = new ArrayList<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>>();
		/**由用户id得到角色id列表*/
		List<StoreAuthUserRole> roleList=this.storeAuthUserRoleService.findByUserId(id);
		List<String> roleIdList=this.getRoleIdList(roleList);
		/**得到用户的权限id列表*/
		List<Long> privilegeIdList=new ArrayList<Long>();
		for (String roleId : roleIdList) {
			privilegeIdList.addAll(this.authRolePrivilegeService.findByRoleId(Long.valueOf(roleId)));
		}
		List<StoreAuthPrivilege> allPrivilegeList=new ArrayList<StoreAuthPrivilege>();
		List<StoreAuthPrivilege> firstPrivilegeList=new ArrayList<StoreAuthPrivilege>();
		List<StoreAuthPrivilege> allSecondPrivilegeList=new ArrayList<StoreAuthPrivilege>();
		//遍历权限id的list 得到用户的所有权限，分别得到一级权限和二级权限
		for (Long privilegeId:privilegeIdList) {
			StoreAuthPrivilege authPrivilege=this.authPrivilegeService.findById(privilegeId);
			if(authPrivilege.getParentId()==null&&authPrivilege.getLevel().equals(1)){
				firstPrivilegeList.add(authPrivilege);
			}else{
				allSecondPrivilegeList.add(authPrivilege);
			}
			allPrivilegeList.add(authPrivilege);
		}
		//遍历用户的一级权限，得到每个一级权限下的二级权限，添加map信息
		for(int i=0;i<firstPrivilegeList.size();i++){
			StoreAuthPrivilege authPrivilegeFirst=firstPrivilegeList.get(i);
			Map<StoreAuthPrivilege, List<StoreAuthPrivilege>> map=new HashMap<StoreAuthPrivilege, List<StoreAuthPrivilege>>();
			List<StoreAuthPrivilege> secondPrivilegeList=new ArrayList<StoreAuthPrivilege>();
			authPrivilegeFirst.setIconClass(CmsConstant.ICON_CLASSES[i% CmsConstant.ICON_CLASSES.length]);
			Long parentId=authPrivilegeFirst.getId();
			for (StoreAuthPrivilege authPrivilegeSecond : allSecondPrivilegeList) {
				if(parentId.equals(authPrivilegeSecond.getParentId())){
					secondPrivilegeList.add(authPrivilegeSecond);
				}
			}
			map.put(authPrivilegeFirst, secondPrivilegeList);
			menuPrivilegeList.add(map);
		}
		return menuPrivilegeList;
	}
	
	
	/***
	 * 得到用户角色的id 集合
	 * @param roleList
	 * @return
	 */
	private List<String> getRoleIdList(List<StoreAuthUserRole> roleList) {
		List<String> roleIdList=new ArrayList<String>();
		for (StoreAuthUserRole authUserRole : roleList) {
			roleIdList.add(String.valueOf(authUserRole.getRoleId()));
		}
		return roleIdList;
	}
	/***
	 * 通过一级权限的list集合，得到一级权限和二级权限列表的map集合，并返回，20150205
	 * @param first
	 * @return
	 */
	private Map<StoreAuthPrivilege, List<StoreAuthPrivilege>> getFirstAndSecondPrivilegeMapByFirstPrivilegeList(
			List<StoreAuthPrivilege> first) {
		Map<StoreAuthPrivilege, List<StoreAuthPrivilege>> menuPrivilege = new LinkedHashMap<StoreAuthPrivilege, List<StoreAuthPrivilege>>();
		for (int i = 0; i < first.size(); i++) {
			StoreAuthPrivilege auPrivilege = first.get(i);
			/** 将每个一级权限赋上相应的图标class */
			auPrivilege.setIconClass(CmsConstant.ICON_CLASSES[i	% CmsConstant.ICON_CLASSES.length]);
			List<StoreAuthPrivilege> second = this.storeAuthPrivilegeService.findByParentId(auPrivilege.getId());
			menuPrivilege.put(auPrivilege, second);
		}
		return menuPrivilege;
	}

	public boolean isUniqueByUserNameAndStoreCode(String name,String storeCode,long id) {
		StoreAuthUser user = this.storeAuthUserDao.findStoreAuthUserByUserNameAndStoreCode(name,storeCode);
		if (user == null) {
			return true;
		} else if(ObjectUtils.equals(user.getId(), id)) {
			return true;
		}else{
			return false;
		}

	}

	public StoreAuthUser findStoreAuthUserByUserNameAndStoreCode(String userName, String storeCode) {
		return this.storeAuthUserDao.findStoreAuthUserByUserNameAndStoreCode(userName, storeCode);
	}

	public StoreAuthUser findSysUserByStoreCode(String storeCode) {
		return this.storeAuthUserDao.findSysUserByStoreCode(storeCode);
	}

	

}
