package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.common.condition.QueryCondition;
import com.fushionbaby.sku.model.SkuBrowseHistories;

public interface SkuBrowseHistoriesService {

	SkuBrowseHistories findByMemberIdAndSkuCode(SkuBrowseHistories skuBrowseHistories);
	
	void add(SkuBrowseHistories skuBrowseHistories);
	
	void updateBrowseCounts(SkuBrowseHistories skuBrowseHistories);
	
	void addOrUpdateBrowseHistories(SkuBrowseHistories skuBrowseHistories);
	
	List<SkuBrowseHistories> findTopNByMemberId(Long memberId,Integer browseHistoriesShowCount);
	
	List<SkuBrowseHistories> findByCondition(QueryCondition queryConditon);
	
}
