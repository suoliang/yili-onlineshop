package com.aladingshop.alabao.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.alabao.dao.AlabaoConfigDao;
import com.aladingshop.alabao.model.AlabaoConfig;
import com.aladingshop.alabao.service.AlabaoConfigService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class AlabaoConfigServiceImpl implements AlabaoConfigService<AlabaoConfig> {

	@Autowired
	private AlabaoConfigDao alabaoConfigDao;
	
	public void add(AlabaoConfig alabaoConfig) {
		alabaoConfigDao.add(alabaoConfig);
		
	}

	public void deleteById(Long id) {
		alabaoConfigDao.deleteById(id);
		
	}

	public AlabaoConfig findById(Long id) {
		return alabaoConfigDao.findById(id);
	}

	public void updateById(AlabaoConfig alabaoConfig) {
		alabaoConfigDao.updateById(alabaoConfig);
		
	}


	public BasePagination getListPage(BasePagination page) {
		Integer total = alabaoConfigDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<AlabaoConfig> list = alabaoConfigDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AlabaoConfig>());
		}
		return page;
	}

	public AlabaoConfig findByRateCode(String rateCode) {
		return alabaoConfigDao.findByRateCode(rateCode);
	}


}
