/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午11:54:01
 */
package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.sku.model.SkuPromotionsInfo;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午11:54:01
 */
public interface SkuPromotionsInfoService {
	
	SkuPromotionsInfo queryByPmCode(String promotionsCode);
	
	List<SkuPromotionsInfo> queryAll();
}
