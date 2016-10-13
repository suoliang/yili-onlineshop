package com.aladingshop.sku.cms.service;

import java.util.List;

import com.aladingshop.sku.cms.dto.SkuSearchDto;


/**
 * @description 商品搜索引擎
 * @author 孟少博
 * @date 2015年8月27日上午10:27:28
 */
public interface SkuSearchService {
	/** 添加商品到搜索引擎文档*/
	 void addSkuDocument();
	 
	 
		/**
		 * 查询当天操作过的商品
		 * @return
		 */
	List<SkuSearchDto> queryBySameDayOperateing();
}
