package com.fushionbaby.sku.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.sku.dao.SkuCategoryImageDao;
import com.fushionbaby.sku.model.SkuCategoryImage;
import com.fushionbaby.sku.service.SkuCategoryImageService;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月6日上午11:34:03
 */
@Service
public class SkuCategoryImageServiceImpl implements SkuCategoryImageService<SkuCategoryImage>{

	
	@Autowired
	private SkuCategoryImageDao skuCategoryImageDao;


	public List<SkuCategoryImage> findByCategoryCode(String categoryCode) {
		
		return this.skuCategoryImageDao.findByCategoryCode(categoryCode);
	}


	public List<SkuCategoryImage> findByCategoryCodeAndImageType(
			String categoryCode, String typeCode) {
		List<SkuCategoryImage> images = this.findByCategoryCode(categoryCode);
		if(images==null || CollectionUtils.isEmpty(images)){
			return new ArrayList<SkuCategoryImage>();
		}
		List<SkuCategoryImage> results = new ArrayList<SkuCategoryImage>();
		for (SkuCategoryImage skuCategoryImage : images) {
			if(StringUtils.equals(skuCategoryImage.getImageTypeCode(),typeCode)){
				results.add(skuCategoryImage);
			}
		}
		
		return results;
	}

	
}
