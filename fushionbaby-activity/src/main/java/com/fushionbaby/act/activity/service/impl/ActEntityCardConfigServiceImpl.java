package com.fushionbaby.act.activity.service.impl; 


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.act.activity.model.ActEntityCardConfig;
import com.fushionbaby.act.activity.dao.ActEntityCardConfigDao;
import com.fushionbaby.act.activity.service.ActEntityCardConfigService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class ActEntityCardConfigServiceImpl implements ActEntityCardConfigService<ActEntityCardConfig>  { 
	
	@Autowired
	private ActEntityCardConfigDao objectDao;
	
	public void add(ActEntityCardConfig object) {
		objectDao.add(object);
		
	}
	
	public ActEntityCardConfig findById(Long id) {
		return objectDao.findById(id);
	}
	
	public void update(ActEntityCardConfig object) {
		objectDao.update(object);
		
	}
	
	public List<ActEntityCardConfig> findAll() {
		return objectDao.findAll();
		
	}
	
	public void deleteById(Long id) {
		objectDao.deleteById(id);
	}


	public BasePagination getListPage(BasePagination page) {
		Integer total = objectDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<ActEntityCardConfig> list = objectDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<ActEntityCardConfig>());
		}
		return page;
	}

	public ActEntityCardConfig findByName(String name) {
		return objectDao.findByName(name);
	}

	public void updateIsDisabled(ActEntityCardConfig object) {
		objectDao.updateIsDisabled(object);
	}

}
