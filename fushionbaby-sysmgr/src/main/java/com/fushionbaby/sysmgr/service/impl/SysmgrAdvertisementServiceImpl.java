package com.fushionbaby.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysmgr.dao.SysmgrAdvertisementDao;
import com.fushionbaby.sysmgr.model.SysmgrAdvertisement;
import com.fushionbaby.sysmgr.service.SysmgrAdvertisementService;

/**
 * 
 * @author xupeijun
 * 
 */
@Service
public class SysmgrAdvertisementServiceImpl implements
		SysmgrAdvertisementService<SysmgrAdvertisement> {

	@Autowired
	private SysmgrAdvertisementDao SysmgrAdvertisementDao;

	public void add(SysmgrAdvertisement SysmgrAdvertisement) throws DataAccessException {
		SysmgrAdvertisementDao.add(SysmgrAdvertisement);
	}

	public void deleteById(Long id) throws DataAccessException {
		SysmgrAdvertisementDao.deleteById(id);
	}

	public void update(SysmgrAdvertisement SysmgrAdvertisement) throws DataAccessException {
		SysmgrAdvertisementDao.update(SysmgrAdvertisement);
	}

	public SysmgrAdvertisement findById(Long id) throws DataAccessException {
		return SysmgrAdvertisementDao.findById(id);
	}

	public List<SysmgrAdvertisement> findAll() throws DataAccessException {
		return SysmgrAdvertisementDao.findAll();
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = SysmgrAdvertisementDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SysmgrAdvertisement> list = SysmgrAdvertisementDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SysmgrAdvertisement>());
		}
		return page;
	}

	public List<SysmgrAdvertisement> getListByAdCode(String ad_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adCode", ad_code);
		// map.put("limit", limit);
		return SysmgrAdvertisementDao.getListByAdCode(map);
	}

}
