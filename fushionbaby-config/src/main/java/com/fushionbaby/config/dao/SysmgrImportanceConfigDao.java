package com.fushionbaby.config.dao;


import com.fushionbaby.config.model.SysmgrImportanceConfig;

/**
 * 
 * @author cyla
 * 
 */
public interface SysmgrImportanceConfigDao {

	/**通过code拿到全局对象*/
	public SysmgrImportanceConfig findByCode(String code);

}
