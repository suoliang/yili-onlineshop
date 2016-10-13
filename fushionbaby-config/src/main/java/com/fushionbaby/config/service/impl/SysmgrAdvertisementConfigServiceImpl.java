package com.fushionbaby.config.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.dao.SysmgrAdvertisementConfigDao;
import com.fushionbaby.config.model.SysmgrAdvertisementConfig;
import com.fushionbaby.config.service.SysmgrAdvertisementConfigService;

/**
 * 
 * @author xu
 * 
 */
@Service
public class SysmgrAdvertisementConfigServiceImpl implements
		SysmgrAdvertisementConfigService<SysmgrAdvertisementConfig> {

	@Autowired
	private SysmgrAdvertisementConfigDao sysAdvertisementConfigDao;

	public void add(SysmgrAdvertisementConfig sysAdvertisementConfig)
			throws DataAccessException {
		sysAdvertisementConfigDao.add(sysAdvertisementConfig);
	}

	public void deleteById(Long id) throws DataAccessException {
		sysAdvertisementConfigDao.deleteById(id);
	}

	public void update(SysmgrAdvertisementConfig sysAdvertisementConfig)
			throws DataAccessException {
		sysAdvertisementConfigDao.update(sysAdvertisementConfig);
	}

	public SysmgrAdvertisementConfig findById(Long id)
			throws DataAccessException {
		return sysAdvertisementConfigDao.findById(id);
	}

	public List<SysmgrAdvertisementConfig> findAll() throws DataAccessException {
		return sysAdvertisementConfigDao.findAll();
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = this.sysAdvertisementConfigDao.getTotal(page
				.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SysmgrAdvertisementConfig> list = this.sysAdvertisementConfigDao
					.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SysmgrAdvertisementConfig>());
		}
		return page;
	}

	public void updateIsUse(Long id, String isUse) {
		Map<String, Object> map = new HashMap<String, Object>();
		isUse = CommonConstant.YES.equals(isUse) ? CommonConstant.NO
				: CommonConstant.YES;
		map.put("id", id);
		map.put("isUse", isUse);
		this.sysAdvertisementConfigDao.updateIsUse(map);
	}

	public SysmgrAdvertisementConfig findByAdCode(String ad_code)
			throws DataAccessException {
		return sysAdvertisementConfigDao.findByAdCode(ad_code);
	}

}
