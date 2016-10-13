package com.aladingshop.sku.cms.service;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuBrowseHistories;

public interface SkuBrowseHistoriesService {

	SkuBrowseHistories findByMemberIdAndSkuCode(SkuBrowseHistories skuBrowseHistories);
	
	void add(SkuBrowseHistories skuBrowseHistories);
	
	void updateBrowseCounts(SkuBrowseHistories skuBrowseHistories);
	
	void addOrUpdateBrowseHistories(SkuBrowseHistories skuBrowseHistories);
	
	List<SkuBrowseHistories> findTopNByMemberId(Long memberId,Integer browseHistoriesShowCount);
}
