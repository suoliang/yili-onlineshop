package com.fushionbaby.sku.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.common.condition.QueryCondition;
import com.fushionbaby.sku.model.SkuBrowseHistories;

public interface SkuBrowseHistoriesDao {
	
	SkuBrowseHistories findByMemberIdAndSkuCode(SkuBrowseHistories skuBrowseHistories);
	
	void add(SkuBrowseHistories skuBrowseHistories);
	
	void updateBrowseCounts(SkuBrowseHistories skuBrowseHistories);
	
	List<SkuBrowseHistories> findTopNByMemberId(@Param("memberId")Long memberId,@Param("browseHistoriesShowCount")Integer browseHistoriesShowCount);

	
	List<SkuBrowseHistories> findByCondition(QueryCondition queryConditon);

}
