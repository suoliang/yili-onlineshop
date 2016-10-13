package com.fushionbaby.config.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.dao.SysmgrSfFreightConfigDao;
import com.fushionbaby.config.model.SysmgrSfFreightConfig;
import com.fushionbaby.config.service.SysmgrSfFreightConfigService;

/**
 * 
 * @author cyla
 * 
 */
@Service
public class SysmgrSfFreightConfigServiceImpl implements
		SysmgrSfFreightConfigService<SysmgrSfFreightConfig> {
	@Autowired
	private SysmgrSfFreightConfigDao sysmgrSfFreightDao;

	public void add(SysmgrSfFreightConfig sysmgrSfFreight) {
		sysmgrSfFreightDao.add(sysmgrSfFreight);
	}

	public SysmgrSfFreightConfig findByAreaCode(String areaCode) {
		return sysmgrSfFreightDao.findByAreaCode(areaCode);
	}

	public void updateByAreaCode(SysmgrSfFreightConfig sysmgrSfFreightConfig) {
		sysmgrSfFreightDao.updateByAreaCode(sysmgrSfFreightConfig);;
	}
	
	public Integer getTotal(Map<String, Object> map){
		return sysmgrSfFreightDao.getTotal(map);
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = this.sysmgrSfFreightDao.getTotal(page
				.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SysmgrSfFreightConfig> list = this.sysmgrSfFreightDao
					.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SysmgrSfFreightConfig>());
		}
		return page;
		
	}
}
