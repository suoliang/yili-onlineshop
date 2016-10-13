package com.fushionbaby.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.core.dao.OrderCardRelationUserDao;
import com.fushionbaby.core.model.OrderCardRelationUser;
import com.fushionbaby.core.service.OrderCardRelationUserService;

/**
 * 
 * @author cyla
 * 
 */
@Service
public class OrderCardRelationUserServiceImpl implements
		OrderCardRelationUserService {

	@Autowired
	private OrderCardRelationUserDao soOrderCardRelationDao;

	public void add(OrderCardRelationUser soOrderCardRelation) {
		soOrderCardRelationDao.add(soOrderCardRelation);
	}

	public BasePagination findBySoCode(BasePagination page) {
		int total = soOrderCardRelationDao.getTotal(page.getSearchParamsMap());
		List<OrderCardRelationUser> result = new ArrayList<OrderCardRelationUser>();
		if(total != 0) {
			result = soOrderCardRelationDao.getPageList(page.getSearchParamsMap());
		}
		page.setTotal(total);
		page.setResult(result);
		return page;
	}

}
