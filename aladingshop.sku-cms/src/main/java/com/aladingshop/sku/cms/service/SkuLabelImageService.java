/**
 * @description aladingshop.com 上海一里网络科技有限公司
 * @author 孟少博
 * @date 2015年11月3日下午3:13:28
 */
package com.aladingshop.sku.cms.service;

import org.springframework.dao.DataAccessException;

import com.aladingshop.sku.cms.model.SkuLabelImage;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 
 * @author 孟少博
 * @date 2015年11月3日下午3:13:28
 */
public interface SkuLabelImageService<T extends SkuLabelImage> extends BaseService<T>  {

	
	/**
	 * 分页查询标签图片列表
	 * 
	 * @param pageParams
	 * @return
	 * @throws DataAccessException
	 */
	BasePagination getListPage(BasePagination pageParams)throws DataAccessException;
}
