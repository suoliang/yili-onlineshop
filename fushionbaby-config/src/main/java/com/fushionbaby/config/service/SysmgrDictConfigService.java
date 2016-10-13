package com.fushionbaby.config.service;

import java.util.List;
import java.util.Map;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrDictConfig;

/**
 * 
 * @author xupeijun
 * 
 * @param <T>
 */
public interface SysmgrDictConfigService<T extends SysmgrDictConfig>
		extends BaseService<T> {

	public void add(SysmgrDictConfig sysDictConfig);

	public void deleteById(Long id);
	
	public void deleteByType(String type);

	public void update(SysmgrDictConfig sysDictConfig);
	
	public T findById(Long id);

	public List<SysmgrDictConfig> findByLabelValueType(String label,String value,String type);

	public Integer getTotal(Map<String, Object> map);

	public BasePagination getListPage(BasePagination page);

}
