package com.fushionbaby.facade.biz.common.config;

import com.fushionbaby.config.model.SysmgrImportanceConfig;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月19日下午2:18:44
 */
public interface ImportanceConfigServiceFacade {

	SysmgrImportanceConfig findByCode(String code);

}
