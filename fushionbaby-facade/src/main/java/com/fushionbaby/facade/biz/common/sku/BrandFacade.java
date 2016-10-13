/**
 * 
 */
package com.fushionbaby.facade.biz.common.sku;

import java.util.List;

import com.fushionbaby.common.dto.BrandDto;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年7月29日上午10:33:29
 */
public interface BrandFacade {
	/**
	 * 通过分类编号获取商品品牌
	 * 
	 * @param categoryCode
	 *            分类编号
	 * @return
	 */
	List<BrandDto> getBrandDtoByCategoryCode(String categoryCode, Integer count);
	
	
	/**
	 * 通过品牌号获取商品品牌
	 * 
	 * @param brandId
	 *            品牌编号
	 * @return
	 */
	BrandDto getBrandDto(String brandCode);
	
	
}
