package com.fushionbaby.sysmgr.service;

import java.util.List;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysmgr.model.SysmgrAdvertisement;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysmgrAdvertisementService<T extends SysmgrAdvertisement> extends BaseService<T> {

	BasePagination getListPage(BasePagination page);

	/**
	 * 查询前台要显示的图片列表
	 * 
	 * @param ad_code
	 *            广告代码
	 * @param source
	 *            图片显示的地方（web phone）
	 * @param limit
	 *            显示图片的个数
	 * 
	 *            0129新需求 不要数量限制
	 * @return
	 */
	// public List<SysmgrAdvertisement> getListByAdCode(String ad_code, Integer
	// limit);
	public List<SysmgrAdvertisement> getListByAdCode(String ad_code);
}
