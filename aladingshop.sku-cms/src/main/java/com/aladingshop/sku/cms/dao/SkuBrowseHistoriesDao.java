package com.aladingshop.sku.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.sku.cms.model.SkuBrowseHistories;

public interface SkuBrowseHistoriesDao {
	
	SkuBrowseHistories findByMemberIdAndSkuCode(SkuBrowseHistories skuBrowseHistories);
	
	void add(SkuBrowseHistories skuBrowseHistories);
	
	void updateBrowseCounts(SkuBrowseHistories skuBrowseHistories);
	
	List<SkuBrowseHistories> findTopNByMemberId(@Param("memberId")Long memberId,@Param("browseHistoriesShowCount")Integer browseHistoriesShowCount);
}
