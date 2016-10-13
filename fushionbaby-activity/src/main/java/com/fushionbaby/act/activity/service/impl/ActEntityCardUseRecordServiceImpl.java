package com.fushionbaby.act.activity.service.impl; 


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.act.activity.model.ActEntityCardUseRecord;
import com.fushionbaby.act.activity.dao.ActEntityCardUseRecordDao;
import com.fushionbaby.act.activity.service.ActEntityCardUseRecordService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class ActEntityCardUseRecordServiceImpl implements ActEntityCardUseRecordService<ActEntityCardUseRecord>  { 
	
	@Autowired
	private ActEntityCardUseRecordDao objectDao;
	
	public void add(ActEntityCardUseRecord object) {
		objectDao.add(object);
		
	}
	
	public ActEntityCardUseRecord findById(Long id) {
		return objectDao.findById(id);
	}
	
	public void update(ActEntityCardUseRecord object) {
		objectDao.update(object);
		
	}
	
	public List<ActEntityCardUseRecord> findAll() {
		return objectDao.findAll();
		
	}
	
	public void deleteById(Long id) {
		objectDao.deleteById(id);
	}


	public BasePagination getListPage(BasePagination page) {
		Integer total = objectDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<ActEntityCardUseRecord> list = objectDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<ActEntityCardUseRecord>());
		}
		return page;
	}

	
	public List<ActEntityCardUseRecord> findByCode(String entityCode) {
	
		return  this.objectDao.findByCode(entityCode);
	}

}
