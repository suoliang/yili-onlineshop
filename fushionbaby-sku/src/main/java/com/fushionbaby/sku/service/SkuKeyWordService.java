/**
 * 
 */
package com.fushionbaby.sku.service;

import com.fushionbaby.sku.model.SkuKeyWord;

/**
 * @description 商品关键字service
 * @author 孙涛
 * @date 2015年9月6日上午11:26:21
 */
public interface SkuKeyWordService<T extends SkuKeyWord> {
	/**
	 * 根据商品code获取关键字
	 * 
	 * @param skuCode
	 * @return
	 */
	SkuKeyWord findByProductCode(String productCode);
}
