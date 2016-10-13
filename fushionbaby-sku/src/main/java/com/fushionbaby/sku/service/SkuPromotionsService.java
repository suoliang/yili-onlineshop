/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午11:50:17
 */
package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.sku.model.SkuPromotions;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午11:50:17
 */

public interface SkuPromotionsService {


	List<SkuPromotions> queryByPmCode(String pmCode);
	
	SkuPromotions queryByPmCodeAndSkuCode(String pmCode,String skuCode);
}
