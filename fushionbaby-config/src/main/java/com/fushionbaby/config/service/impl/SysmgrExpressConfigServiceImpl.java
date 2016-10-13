package com.fushionbaby.config.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.dao.SysmgrExpressConfigDao;
import com.fushionbaby.config.model.SysmgrExpressConfig;
import com.fushionbaby.config.service.SysmgrExpressConfigService;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月3日上午10:02:22
 */
@Service
public class SysmgrExpressConfigServiceImpl implements	SysmgrExpressConfigService {

	@Autowired
	private SysmgrExpressConfigDao sysExpressConfigDao;

	public SysmgrExpressConfig findByExpressCompanyName(String name) {
		return this.sysExpressConfigDao.findByExpressCompanyName(name);
	}
	public void add(SysmgrExpressConfig sysmgrExpressConfig) {
		 this.sysExpressConfigDao.add(sysmgrExpressConfig);
	}
	public BasePagination getListPage(BasePagination page) {
		Integer total = this.sysExpressConfigDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SysmgrExpressConfig> list = this.sysExpressConfigDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SysmgrExpressConfig>());
		}
		return page;
	}
	public List<SysmgrExpressConfig> findAll() {
	
		return this.sysExpressConfigDao.findAll();
	}

}
