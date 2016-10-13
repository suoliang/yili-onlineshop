package com.fushionbaby.config.service;


import com.fushionbaby.config.model.SysmgrImportanceConfig;

/**
 *
 * @author cyla
 * 
 */
public interface SysmgrImportanceConfigService {
	
	/**通过code拿到全局对象*/
	public SysmgrImportanceConfig findByCode(String code);

}
