package com.fushionbaby.config.service;

import java.util.List;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrVersionConfig;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysmgrVersionConfigService<T extends SysmgrVersionConfig> extends
		BaseService<T> {
	
	/**
	 * 根据来源得到最新的版本号(IOS,Android)
	 */
	int getLatestVersionNum(String sourceCode);

	/**得到最新的版本信息*/
	SysmgrVersionConfig getLatestVersionByLatestVersionNumAndSourceCode(SysmgrVersionConfig sysmgrVersion);
	
	/**
	 * 检查版本是否需要强制更新(IOS,Android)
	 * @param sysVersion
	 *            用户的版本信息
	 * @param sourceCode
	 * 			     来源
	 * @return 当前版本信息
	 */
	List<SysmgrVersionConfig> checkIsNeedUpdateOrNot(SysmgrVersionConfig sysVersion);

	BasePagination getListPage(BasePagination page);

}
