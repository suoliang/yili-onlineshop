package com.fushionbaby.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.order.dao.OrderCardRelationDao;
import com.fushionbaby.order.model.OrderCardRelation;
import com.fushionbaby.order.service.OrderCardRelationService;

/**
 * 
 * @author cyla
 * 
 */
@Service
public class OrderCardRelationServiceImpl implements
		OrderCardRelationService {

	@Autowired
	private OrderCardRelationDao soOrderCardRelationDao;

	public void add(OrderCardRelation soOrderCardRelation) {
		soOrderCardRelationDao.add(soOrderCardRelation);
	}

	public BasePagination findBySoCode(BasePagination page) {
		int total = soOrderCardRelationDao.getTotal(page.getSearchParamsMap());
		List<OrderCardRelation> result = new ArrayList<OrderCardRelation>();
		if(total != 0) {
			result = soOrderCardRelationDao.getPageList(page.getSearchParamsMap());
		}
		page.setTotal(total);
		page.setResult(result);
		return page;
	}

	public void deleteByOrderCodeAndMemberId(String orderCode, Long MemberId) {
		soOrderCardRelationDao.deleteByOrderCodeAndMemberId(orderCode, MemberId);
		
	}

}
