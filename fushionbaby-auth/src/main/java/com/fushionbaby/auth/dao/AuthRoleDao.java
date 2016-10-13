package com.fushionbaby.auth.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.auth.model.AuthRole;
import com.fushionbaby.auth.model.AuthUser;

public interface AuthRoleDao {
	void deleteById(long id);

	void add(AuthRole auRole);

	List<AuthRole> findAll();

	AuthRole findById(long id);

	void update(AuthRole auRole);

	/**
	 * 通过角色id得到所有的 拥有该角色的用户
	 * 
	 * @param roleId
	 * @return
	 */
	List<AuthUser> listAllAuUserByRoleId(List<Long> roleId);

	AuthRole findByName(String name);

	/**
	 * 分页查询
	 * 
	 * @author suoliang
	 */
	List<AuthRole> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);
}