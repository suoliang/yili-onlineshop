package com.aladingshop.alabao.service.impl; 


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.alabao.model.AlabaoBankConfig;
import com.aladingshop.alabao.dao.AlabaoBankConfigDao;
import com.aladingshop.alabao.service.AlabaoBankConfigService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class AlabaoBankConfigServiceImpl implements AlabaoBankConfigService<AlabaoBankConfig>  { 
	
	@Autowired
	private AlabaoBankConfigDao objectDao;
	
	public void add(AlabaoBankConfig object) {
		objectDao.add(object);
		
	}
	
	public AlabaoBankConfig findById(Long id) {
		return objectDao.findById(id);
	}
	
	public void update(AlabaoBankConfig object) {
		objectDao.update(object);
		
	}
	
	public List<AlabaoBankConfig> findAll() {
		return objectDao.findAll();
		
	}
	
	public void deleteById(Long id) {
		objectDao.deleteById(id);
	}


	public BasePagination getListPage(BasePagination page) {
		Integer total = objectDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<AlabaoBankConfig> list = objectDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AlabaoBankConfig>());
		}
		return page;
	}

	public AlabaoBankConfig findByBankCode(String bankCode) {
		return objectDao.findByBankCode(bankCode);
	}
	
	public AlabaoBankConfig findByBankName(String bankName) {
		return objectDao.findByBankName(bankName);
	}

}
