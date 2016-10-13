package com.fushionbaby.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.sysmgr.model.SysmgrAdvertisement;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysmgrAdvertisementDao {

	public void add(SysmgrAdvertisement sysAdvertisement);

	public void deleteById(Long id);

	public void update(SysmgrAdvertisement sysAdvertisement);

	public SysmgrAdvertisement findById(Long id);

	public List<SysmgrAdvertisement> findAll();

	public Integer getTotal(Map<String, Object> map);

	public List<SysmgrAdvertisement> getListPage(Map<String, Object> map);

	public List<SysmgrAdvertisement> getListByAdCode(Map<String, Object> map);

}
