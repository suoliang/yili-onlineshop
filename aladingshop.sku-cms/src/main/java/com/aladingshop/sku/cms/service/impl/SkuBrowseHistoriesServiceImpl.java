package com.aladingshop.sku.cms.service.impl;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuBrowseHistoriesDao;
import com.aladingshop.sku.cms.model.SkuBrowseHistories;
import com.aladingshop.sku.cms.service.SkuBrowseHistoriesService;

/**
 * 
 * @author King
 * 
 */
@Service
public class SkuBrowseHistoriesServiceImpl implements SkuBrowseHistoriesService {
	
	@Autowired
	private SkuBrowseHistoriesDao skuBrowseHistoriesDao;

	public SkuBrowseHistories findByMemberIdAndSkuCode(SkuBrowseHistories skuBrowseHistories) {
		return skuBrowseHistoriesDao.findByMemberIdAndSkuCode(skuBrowseHistories);
		
	}

	public void add(SkuBrowseHistories skuBrowseHistories) {
		skuBrowseHistoriesDao.add(skuBrowseHistories);
		
	}

	public void updateBrowseCounts(SkuBrowseHistories skuBrowseHistories) {
		skuBrowseHistoriesDao.updateBrowseCounts(skuBrowseHistories);
		
	}

	public void addOrUpdateBrowseHistories(SkuBrowseHistories skuBrowseHistories) {
		if(ObjectUtils.equals(null, findByMemberIdAndSkuCode(skuBrowseHistories))){
			add(skuBrowseHistories);
		}else{
			updateBrowseCounts(skuBrowseHistories);
		}
		
	}

	public List<SkuBrowseHistories> findTopNByMemberId(Long memberId,Integer browseHistoriesShowCount) {
		return skuBrowseHistoriesDao.findTopNByMemberId(memberId,browseHistoriesShowCount);
	}

	

}