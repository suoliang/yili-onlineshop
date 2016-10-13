package com.aladingshop.alabao.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.alabao.dao.AlabaoOrderDao;
import com.aladingshop.alabao.model.AlabaoOrder;
import com.aladingshop.alabao.service.AlabaoOrderService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class AlabaoOrderServiceImpl implements AlabaoOrderService<AlabaoOrder> {

	@Autowired
	private AlabaoOrderDao alabaoOrderDao;
	
	public void add(AlabaoOrder alabaoOrder) {
		this.alabaoOrderDao.add(alabaoOrder);
	}

	public void deleteById(Long id) {
		this.alabaoOrderDao.deleteById(id);
	}

	public void updateById(AlabaoOrder alabaoOrder) {
		this.alabaoOrderDao.updateById(alabaoOrder);
	}

	public AlabaoOrder findById(Long id) {
		return this.alabaoOrderDao.findById(id);
	}

	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = alabaoOrderDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<AlabaoOrder> list = alabaoOrderDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AlabaoOrder>());
		}
		return page;
	}

	public AlabaoOrder findByMemberIdOrderCode(Long memberId, String orderCode) {
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("orderCode", orderCode);
		return alabaoOrderDao.findByMemberIdOrderCode(map);
	}

	public void updateByMemberIdOrderCode(AlabaoOrder alabaoOrder) {
		alabaoOrderDao.updateByMemberIdOrderCode(alabaoOrder);
	}
	
	public String getListPageTotalActual(BasePagination page) {
		Map<String,Object> map= this.alabaoOrderDao.getListPageTotalActual(page.getSearchParamsMap());
		BigDecimal totalMoney = new BigDecimal("0");
		if(null != map){
		 totalMoney= (BigDecimal) map.get("listPageTotalActual");
		}
		return String.valueOf(totalMoney);
	}
	

}
