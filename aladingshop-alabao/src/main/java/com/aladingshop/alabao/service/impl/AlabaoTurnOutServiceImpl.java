package com.aladingshop.alabao.service.impl; 


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.alabao.dao.AlabaoTurnOutDao;
import com.aladingshop.alabao.model.AlabaoTurnOut;
import com.aladingshop.alabao.service.AlabaoTurnOutService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class AlabaoTurnOutServiceImpl implements AlabaoTurnOutService<AlabaoTurnOut>  { 
	
	@Autowired
	private AlabaoTurnOutDao objectDao;
	
	public void add(AlabaoTurnOut object) {
		objectDao.add(object);
		
	}
	
	public AlabaoTurnOut findById(Long id) {
		return objectDao.findById(id);
	}
	
	public void update(AlabaoTurnOut object) {
		objectDao.update(object);
		
	}
	
	public List<AlabaoTurnOut> findAll() {
		return objectDao.findAll();
		
	}
	
	public void deleteById(Long id) {
		objectDao.deleteById(id);
	}


	public BasePagination getListPage(BasePagination page) {
		Integer total = objectDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<AlabaoTurnOut> list = objectDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AlabaoTurnOut>());
		}
		return page;
	}

	public void updateStatus(String turnOutStatus, Long id) {
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("turnOutStatus", turnOutStatus);
		objectDao.updateStatus(map);
	}

	public AlabaoTurnOut findBySerialNum(String serialNum) {
		return objectDao.findBySerialNum(serialNum);
	}

}
