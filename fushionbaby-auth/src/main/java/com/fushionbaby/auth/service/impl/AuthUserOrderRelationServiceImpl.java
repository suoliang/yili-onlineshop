package com.fushionbaby.auth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.auth.dao.AuthUserOrderRelationDao;
import com.fushionbaby.auth.model.AuthUserOrderRelation;
import com.fushionbaby.auth.service.AuthUserOrderRelationService;
import com.fushionbaby.common.util.BasePagination;
/***
 * 后台用户和订单关联
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月28日上午9:36:37
 */
@Service
public class AuthUserOrderRelationServiceImpl  implements AuthUserOrderRelationService<AuthUserOrderRelation> {

	
	@Autowired
	private AuthUserOrderRelationDao authUserOrderRelationDao;
	
	public void add(AuthUserOrderRelation entity) throws DataAccessException {
        this.authUserOrderRelationDao.add(entity);		
	}

	public void deleteById(Long id) throws DataAccessException {
		this.authUserOrderRelationDao.deleteById(id);
	}

	public void update(AuthUserOrderRelation entity) throws DataAccessException {
		this.authUserOrderRelationDao.update(entity);
	}

	public AuthUserOrderRelation findById(Long id) throws DataAccessException {
		
		return this.authUserOrderRelationDao.findById(id);
	}

	public List<AuthUserOrderRelation> findAll() {
		return this.authUserOrderRelationDao.findAll();
	}

	public BasePagination getListPage(BasePagination page) {
		
		Integer total = authUserOrderRelationDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<AuthUserOrderRelation> list = authUserOrderRelationDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AuthUserOrderRelation>());
		}
		return page;
	}

	public List<AuthUserOrderRelation> findByUserIdAndName(Long userId,	String userName) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("userName", userName);
		return this.authUserOrderRelationDao.findByUserIdAndUserName(map);
	}

	public List<String> findOrderCodeListByUserId(Long id, String loginName) {
		List<String> orderCodeList=new ArrayList<String>();
		List<AuthUserOrderRelation> orderRelationList=this.findByUserIdAndName(id, loginName);
		for (AuthUserOrderRelation authUserOrderRelation : orderRelationList) {
			orderCodeList.add(authUserOrderRelation.getOrderCode());
		}
		return orderCodeList;
	}

	public void deleteByOrderCodeAndUserId(Long id, String orderCode) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userId", id);
		map.put("orderCode", orderCode);
		this.authUserOrderRelationDao.deleteByOrderCodeAndUserId(map);
	}
	

	

}