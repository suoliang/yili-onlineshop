package com.aladingshop.card.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.card.dao.MemberCardOrderDao;
import com.aladingshop.card.model.MemberCardOrder;
import com.aladingshop.card.service.MemberCardOrderService;
import com.fushionbaby.common.util.BasePagination;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年9月30日上午10:48:58
 */
@Service
public class MemberCardOrderServiceImpl implements MemberCardOrderService<MemberCardOrder>{

	@Autowired
	private MemberCardOrderDao memberCardOrderDao;
	
	public void add(MemberCardOrder order) throws DataAccessException {
		this.memberCardOrderDao.add(order);
	}

	public void deleteById(Long id) throws DataAccessException {
		this.memberCardOrderDao.deleteById(id);
	}

	public void update(MemberCardOrder order) throws DataAccessException {
		this.memberCardOrderDao.update(order);
	}

	public MemberCardOrder findById(Long id) throws DataAccessException {
		return this.memberCardOrderDao.findById(id);
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = this.memberCardOrderDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<MemberCardOrder> list = this.memberCardOrderDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<MemberCardOrder>());
		}
		return page;
	}

	public List<MemberCardOrder> findAll() {
         return this.memberCardOrderDao.findAll();
	}

	public MemberCardOrder findByMemberIdOrderCode(Long memberId,String orderCode) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("orderCode", orderCode);
 		return this.memberCardOrderDao.findByMemberIdOrderCode(map);
	}

	public void updateByMemberIdOrderCode(MemberCardOrder cardOrder) {
		this.memberCardOrderDao.updateByMemberIdOrderCode(cardOrder);
	}



}
