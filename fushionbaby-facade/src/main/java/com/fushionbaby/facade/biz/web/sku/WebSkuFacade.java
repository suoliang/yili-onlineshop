/**
 * 
 */
package com.fushionbaby.facade.biz.web.sku;

import java.util.List;

import com.fushionbaby.common.dto.sku.IndexDto;
import com.fushionbaby.common.dto.sku.SkuBrandDto;
import com.fushionbaby.common.dto.sku.SkuBrandGraphemeDto;
import com.fushionbaby.common.dto.sku.SkuDetailDto;
import com.fushionbaby.common.dto.sku.SkuHotDto;
import com.fushionbaby.common.dto.sku.web.WebSkuDetailDto;


/**
 * @author mengshaobo
 *
 */
public interface WebSkuFacade {
	/**
	 * 获得商城首页信息
	 * @return
	 */
	IndexDto findIndexAll();
	/**
	 * 商品详情
	 * @param skuDetail
	 * @return
	 */
	WebSkuDetailDto getWebSkuDetail(SkuDetailDto skuDetail);
	
	/**
	 * 获得按字母排序的品牌列表
	 * @param brandList 所以的品牌编号 
	 * @return
	 */
	List<SkuBrandGraphemeDto> getSkuBrandGraphemes(List<SkuBrandDto> brandList);
	/**
	 * 热销商品列表
	 * @param count 显示数量
	 * @return
	 */
	List<SkuHotDto> getSkuHot(Integer count);
}
