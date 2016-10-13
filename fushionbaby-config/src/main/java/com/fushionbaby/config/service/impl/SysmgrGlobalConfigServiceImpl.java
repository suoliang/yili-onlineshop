package com.fushionbaby.config.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.dao.SysmgrGlobalConfigDao;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;

/**
 * 
 * @author cyla
 * 
 */

@Service
public class SysmgrGlobalConfigServiceImpl implements SysmgrGlobalConfigService{

	@Autowired
	private SysmgrGlobalConfigDao sysmgrGlobalConfigDao;
	
	
	public void add(SysmgrGlobalConfig sysmgrGlobalConfig) {
		sysmgrGlobalConfigDao.add(sysmgrGlobalConfig);
	}

	
	public void update(SysmgrGlobalConfig sysmgrGlobalConfig) {
		sysmgrGlobalConfigDao.update(sysmgrGlobalConfig);
	}

	
	public void deleteSysmgrGlobalConfigById(long id) {
		sysmgrGlobalConfigDao.deleteSysmgrGlobalConfigById(id);
	}

	
	public SysmgrGlobalConfig getSysmgrGlobalConfigById(long id) {
		return sysmgrGlobalConfigDao.getSysmgrGlobalConfigById(id);
	}

	
	public List<SysmgrGlobalConfig> getSysmgrGlobalConfigByPage(SysmgrGlobalConfig sysmgrGlobalConfig, int offset, int limit) {
		return sysmgrGlobalConfigDao.getSysmgrGlobalConfigByPage(sysmgrGlobalConfig, offset, limit);
	}

	
	public int getCount(SysmgrGlobalConfig sysmgrGlobalConfig) {
		return sysmgrGlobalConfigDao.getCount(sysmgrGlobalConfig);
	}

	public SysmgrGlobalConfig findByCode(String code) {
		return sysmgrGlobalConfigDao.findByCode(code);
	}


	public Integer getConfigValueByCode(String code) {
		SysmgrGlobalConfig config  = this.findByCode(code);
		if(ObjectUtils.notEqual(null, config)){
			Integer value = NumberUtils.toInt(config.getValue());
			return value;
		}
		return null;
	}
	
	public Integer getTotal(Map<String, Object> map){
		return sysmgrGlobalConfigDao.getTotal(map);
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = this.sysmgrGlobalConfigDao.getTotal(page
				.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SysmgrGlobalConfig> list = this.sysmgrGlobalConfigDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SysmgrGlobalConfig>());
		}
		return page;
		
	}

}
