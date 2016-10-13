package com.fushionbaby.sku.service.impl;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.condition.QueryCondition;
import com.fushionbaby.sku.dao.SkuBrowseHistoriesDao;
import com.fushionbaby.sku.model.SkuBrowseHistories;
import com.fushionbaby.sku.service.SkuBrowseHistoriesService;

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

	public List<SkuBrowseHistories> findByCondition(QueryCondition queryConditon) {
		return skuBrowseHistoriesDao.findByCondition(queryConditon);
	}

	

}