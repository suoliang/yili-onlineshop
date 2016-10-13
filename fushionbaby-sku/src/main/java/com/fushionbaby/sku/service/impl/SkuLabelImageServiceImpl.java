/**
 * @description aladingshop.com 上海一里网络科技有限公司
 * @author 孟少博
 * @date 2015年11月6日上午10:30:10
 */
package com.fushionbaby.sku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.condition.sku.SkuLabelQueryCondition;
import com.fushionbaby.sku.dao.SkuLabelImageDao;
import com.fushionbaby.sku.model.SkuLabelImage;
import com.fushionbaby.sku.service.SkuLabelImageService;

/**
 * @description 标签图片
 * @author 孟少博
 * @date 2015年11月6日上午10:30:10
 */
@Service
public class SkuLabelImageServiceImpl implements SkuLabelImageService<SkuLabelImage> {
	
	@Autowired
	private SkuLabelImageDao skuLabelImageDao;
 
	public List<SkuLabelImage> findByCondition(
			SkuLabelQueryCondition queryCondition) {
		return skuLabelImageDao.findByCondition(queryCondition);
	}

}
