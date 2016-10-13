/**
 * @description aladingshop.com 上海一里网络科技有限公司
 * @author 孟少博
 * @date 2015年11月3日下午4:12:25
 */
package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuLabelImageDao;
import com.aladingshop.sku.cms.model.SkuLabelImage;
import com.aladingshop.sku.cms.service.SkuLabelImageService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 标签图片
 * @author 孟少博
 * @date 2015年11月3日下午4:12:25
 */
@Service
public class SkuLabelImageServiceImpl implements SkuLabelImageService<SkuLabelImage> {
	
	@Autowired
	private SkuLabelImageDao skuLabelImageDao;

	public void add(SkuLabelImage skuLabelImage) throws DataAccessException {
		skuLabelImageDao.add(skuLabelImage);
		
	}

	public void deleteById(Long id) throws DataAccessException {
		skuLabelImageDao.deleteById(id);
		
	}

	public void update(SkuLabelImage skuLabelImage) throws DataAccessException {
		skuLabelImageDao.update(skuLabelImage);
		
	}

	public SkuLabelImage findById(Long id) throws DataAccessException {
		return skuLabelImageDao.findById(id);
	}

	public BasePagination getListPage(BasePagination pageParams)
			throws DataAccessException {
		Integer total = this.skuLabelImageDao.getTotal(pageParams.getSearchParamsMap());
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			List<SkuLabelImage> list = this.skuLabelImageDao.getListPage(pageParams
					.getSearchParamsMap());
			pageParams.setResult(list);
		} else {
			pageParams.setResult(new ArrayList<SkuLabelImage>());
		}
		return pageParams;
	}


}
