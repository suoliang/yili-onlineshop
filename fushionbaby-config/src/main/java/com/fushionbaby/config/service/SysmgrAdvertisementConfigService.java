package com.fushionbaby.config.service;

import java.util.List;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrAdvertisementConfig;

/**
 * 
 * @author xupeijun
 * 
 * @param <T>
 */
public interface SysmgrAdvertisementConfigService<T extends SysmgrAdvertisementConfig>
		extends BaseService<T> {

	public BasePagination getListPage(BasePagination page);

	List<SysmgrAdvertisementConfig> findAll();

	public void updateIsUse(Long id, String isUse);

	public SysmgrAdvertisementConfig findByAdCode(String ad_code);

}
