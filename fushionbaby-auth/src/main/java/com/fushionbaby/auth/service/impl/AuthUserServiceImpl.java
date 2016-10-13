package com.fushionbaby.auth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.auth.dao.AuthUserDao;
import com.fushionbaby.auth.model.AuthPrivilege;
import com.fushionbaby.auth.model.AuthRolePrivilege;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.auth.model.AuthUserRole;
import com.fushionbaby.auth.service.AuthPrivilegeService;
import com.fushionbaby.auth.service.AuthRolePrivilegeService;
import com.fushionbaby.auth.service.AuthUserRoleService;
import com.fushionbaby.auth.service.AuthUserService;
import com.fushionbaby.common.constants.CmsConstant;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author xupeijun
 * 
 */
@Service
public class AuthUserServiceImpl implements AuthUserService<AuthUser> {

	@Autowired
	private AuthUserDao authUserDao;
    
	@Autowired
	private AuthPrivilegeService<AuthPrivilege> authPrivilegeService;
	/** 用户角色关联*/
	@Autowired
	private AuthUserRoleService<AuthUserRole> authUserRoleService;
	/** 角色权限关联*/
	@Autowired
	private AuthRolePrivilegeService<AuthRolePrivilege> authRolePrivilegeService;

	public void add(AuthUser authUser) throws DataAccessException {
		this.authUserDao.add(authUser);
	}

	public void deleteById(Long id) throws DataAccessException {
		this.authUserDao.deleteById(id);
	}

	public void update(AuthUser authUser) throws DataAccessException {
		authUserDao.update(authUser);
	}
	public void updateStatus(Long id, Integer status) {
		AuthUser authUser = new AuthUser();
		authUser.setId(id);
		authUser.setStatus(status);
		authUserDao.updateStatus(authUser);
	}

	public AuthUser findById(Long id) throws DataAccessException {
		return authUserDao.findById(id);
	}

	public List<AuthUser> findAll() throws DataAccessException {
		return this.authUserDao.findAll();
	}

	public AuthUser findAuthUserByUserName(String userName) {
		return this.authUserDao.findAuthUserByUserName(userName);
	}

	public AuthUser isSystemUser(Long id) {
		return this.authUserDao.isSystemUser(id);
	}
	
	public BasePagination getListPage(BasePagination page) {
		Integer total = this.authUserDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<AuthUser> list = this.authUserDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AuthUser>());
		}
		return page;
	}

	/**
	 * 以系统用户身份登录，能够得到所有权限
	 */
	public List<Map<AuthPrivilege, List<AuthPrivilege>>> loginAsSystem() {
		List<Map<AuthPrivilege, List<AuthPrivilege>>> menuPrivilegeList = new ArrayList<Map<AuthPrivilege, List<AuthPrivilege>>>();
		Map<AuthPrivilege, List<AuthPrivilege>> menuPrivilege = new LinkedHashMap<AuthPrivilege, List<AuthPrivilege>>();
		List<AuthPrivilege> first = this.authPrivilegeService.findAllFirst();
		menuPrivilege=getFirstAndSecondPrivilegeMapByFirstPrivilegeList(first);
		menuPrivilegeList.add(menuPrivilege);
		return menuPrivilegeList;
	}

	/**
	 * 以普通用户的身份登录 只要拥有了第一级权限 就拥有了第一级下的所有的第二级权限
	 */
//	public List<Map<AuthPrivilege, List<AuthPrivilege>>> loginAsNormalUser(Long userId) {
//		List<Map<AuthPrivilege, List<AuthPrivilege>>> menuPrivilegeList = new ArrayList<Map<AuthPrivilege, List<AuthPrivilege>>>();
//		Map<AuthPrivilege, List<AuthPrivilege>> menuPrivilege = new LinkedHashMap<AuthPrivilege, List<AuthPrivilege>>();
//		List<AuthPrivilege> first = this.authPrivilegeService.listParentPrivilegeByUserId(userId);
//		if (first.size() > 0) {
//			menuPrivilege=getFirstAndSecondPrivilegeMapByFirstPrivilegeList(first,userId);
//			menuPrivilegeList.add(menuPrivilege);
//		}
//		return menuPrivilegeList;
//	}

	/***
	 * 普通用户登录
	 * @param first
	 * @return
	 */
//	private Map<AuthPrivilege, List<AuthPrivilege>> getFirstAndSecondPrivilegeMapByFirstPrivilegeList(List<AuthPrivilege> first,Long userId) {
//		Map<AuthPrivilege, List<AuthPrivilege>> menuPrivilege = new LinkedHashMap<AuthPrivilege, List<AuthPrivilege>>();
//		for (int i = 0; i < first.size(); i++) {
//			AuthPrivilege auPrivilege = first.get(i);
//			/** 将每个一级权限赋上相应的图标class */
//			auPrivilege.setIconClass(CmsConstant.ICON_CLASSES[i	% CmsConstant.ICON_CLASSES.length]);
//			
//			List<AuthPrivilege> second = this.authPrivilegeService.findByParentId(auPrivilege.getId());
//			menuPrivilege.put(auPrivilege, second);
//		}
//		return menuPrivilege;
//	}
	/***
	 * 系统用户使用
	 * @param first
	 * @return
	 */
	private Map<AuthPrivilege, List<AuthPrivilege>> getFirstAndSecondPrivilegeMapByFirstPrivilegeList(List<AuthPrivilege> first) {
		Map<AuthPrivilege, List<AuthPrivilege>> menuPrivilege = new LinkedHashMap<AuthPrivilege, List<AuthPrivilege>>();
		for (int i = 0; i < first.size(); i++) {
			AuthPrivilege auPrivilege = first.get(i);
			/** 将每个一级权限赋上相应的图标class */
			auPrivilege.setIconClass(CmsConstant.ICON_CLASSES[i	% CmsConstant.ICON_CLASSES.length]);
			List<AuthPrivilege> second = this.authPrivilegeService.findByParentId(auPrivilege.getId());
			menuPrivilege.put(auPrivilege, second);
		}
		return menuPrivilege;
	}

	public boolean isUniqueByUserName(String name,long id) {
		AuthUser user = this.authUserDao.findAuthUserByUserName(name);
		if (user == null) {
			return true;
		} else if(ObjectUtils.equals(user.getId(), id)) {
			return true;
		}else{
			return false;
		}

	}


	public List<Map<AuthPrivilege, List<AuthPrivilege>>> loginAsNormalUser(Long id) {
		List<Map<AuthPrivilege, List<AuthPrivilege>>> menuPrivilegeList = new ArrayList<Map<AuthPrivilege, List<AuthPrivilege>>>();
		/**由用户id得到角色id列表*/
		List<AuthUserRole> roleList=this.authUserRoleService.findByUserId(id);
		List<String> roleIdList=this.getRoleIdList(roleList);
		/**得到用户的权限id列表*/
		List<Long> privilegeIdList=new ArrayList<Long>();
		for (String roleId : roleIdList) {
			privilegeIdList.addAll(this.authRolePrivilegeService.findByRoleId(Long.valueOf(roleId)));
		}
		List<AuthPrivilege> allPrivilegeList=new ArrayList<AuthPrivilege>();
		List<AuthPrivilege> firstPrivilegeList=new ArrayList<AuthPrivilege>();
		List<AuthPrivilege> allSecondPrivilegeList=new ArrayList<AuthPrivilege>();
		//遍历权限id的list 得到用户的所有权限，分别得到一级权限和二级权限
		for (Long privilegeId:privilegeIdList) {
			AuthPrivilege authPrivilege=this.authPrivilegeService.findById(privilegeId);
			if(authPrivilege.getParentId()==null&&authPrivilege.getLevel().equals(1)){
				firstPrivilegeList.add(authPrivilege);
			}else{
				allSecondPrivilegeList.add(authPrivilege);
			}
			allPrivilegeList.add(authPrivilege);
		}
		//遍历用户的一级权限，得到每个一级权限下的二级权限，添加map信息
		for(int i=0;i<firstPrivilegeList.size();i++){
			AuthPrivilege authPrivilegeFirst=firstPrivilegeList.get(i);
			Map<AuthPrivilege, List<AuthPrivilege>> map=new HashMap<AuthPrivilege, List<AuthPrivilege>>();
			List<AuthPrivilege> secondPrivilegeList=new ArrayList<AuthPrivilege>();
			authPrivilegeFirst.setIconClass(CmsConstant.ICON_CLASSES[i% CmsConstant.ICON_CLASSES.length]);
			Long parentId=authPrivilegeFirst.getId();
			for (AuthPrivilege authPrivilegeSecond : allSecondPrivilegeList) {
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
	private List<String> getRoleIdList(List<AuthUserRole> roleList) {
		List<String> roleIdList=new ArrayList<String>();
		for (AuthUserRole authUserRole : roleList) {
			roleIdList.add(String.valueOf(authUserRole.getRoleId()));
		}
		return roleIdList;
	}

	public List<AuthUser> findDistributionUser(Integer levelTwo) {
		
		return this.authUserDao.findDistributionUser(levelTwo);
	}

}
