package com.fushionbaby.facade.biz.common.sku;

import java.util.List;

import com.fushionbaby.common.dto.sku.SkuCombinationDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.sku.model.SkuCombination;


public interface SkuCombinationFacade {
	 /**
	  * 获得优惠商品组合中的商品信息列表
	  * @param skuId
	  * @return
	  */
	 List<SkuDto> getSkuCombinationBySkuCode(String skuCode);
	 
	 /**
	  * 通过组合编号获取商品信息列表
	  * @param combinationId
	  * @return
	  */
	 List<SkuDto> getSkuByCombinationId(Long combinationId);
	 
	 
	 /**
	  * 获取商品组合信息
	  * @param skuList 商品列表
	  * @param skuCombination 商品组合定义
	  * @return
	  */
	 SkuCombinationDto getSkuCombinationDto(List<SkuDto> skuList,SkuCombination skuCombination);
}
