package com.fushionbaby.config.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.dao.SysmgrDictConfigDao;
import com.fushionbaby.config.model.SysmgrDictConfig;
import com.fushionbaby.config.service.SysmgrDictConfigService;


/**
 * 
 * @author xu
 * 
 */
@Service
public class SysmgrDictConfigServiceImpl implements
		SysmgrDictConfigService<SysmgrDictConfig> {

	@Autowired
	private SysmgrDictConfigDao sysDictConfigDao;

	public void add(SysmgrDictConfig sysDictConfig)
			throws DataAccessException {
		sysDictConfigDao.add(sysDictConfig);
	}

	public void deleteById(Long id) {
		sysDictConfigDao.deleteById(id);
	}

	public void deleteByType(String type) {
		sysDictConfigDao.deleteByType(type);
	}
	
	public void update(SysmgrDictConfig sysDictConfig){
		sysDictConfigDao.update(sysDictConfig);
	}

	public SysmgrDictConfig findById(Long id){
		return sysDictConfigDao.findById(id);
	}

	public List<SysmgrDictConfig> findByLabelValueType(String label,String value,String type){
		return sysDictConfigDao.findByLabelValueType(label, value, type);
	}
	
	public Integer getTotal(Map<String, Object> map){
		return sysDictConfigDao.getTotal(map);
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = this.sysDictConfigDao.getTotal(page
				.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SysmgrDictConfig> list = this.sysDictConfigDao
					.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SysmgrDictConfig>());
		}
		return page;
		
	}




	
}
