package com.aladingshop.alabao.service.impl; 


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aladingshop.alabao.model.AlabaoRolloffConfig;
import com.aladingshop.alabao.dao.AlabaoRolloffConfigDao;
import com.aladingshop.alabao.service.AlabaoRolloffConfigService;
import com.fushionbaby.common.util.BasePagination;

@Service
@Transactional
public class AlabaoRolloffConfigServiceImpl implements AlabaoRolloffConfigService<AlabaoRolloffConfig>  { 
	
	@Autowired
	private AlabaoRolloffConfigDao objectDao;
	
	public void add(AlabaoRolloffConfig object) {
		objectDao.add(object);
		
	}
	
	public AlabaoRolloffConfig findById(Long id) {
		return objectDao.findById(id);
	}
	
	public void update(AlabaoRolloffConfig object) {
		objectDao.update(object);
		
	}
	
	public List<AlabaoRolloffConfig> findAll() {
		return objectDao.findAll();
		
	}
	
	public void deleteById(Long id) {
		objectDao.deleteById(id);
	}


	public BasePagination getListPage(BasePagination page) {
		Integer total = objectDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<AlabaoRolloffConfig> list = objectDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AlabaoRolloffConfig>());
		}
		return page;
	}

	public AlabaoRolloffConfig findByRollOffCode(String rollOffCode) {
		return objectDao.findByRollOffCode(rollOffCode);
	}
}
