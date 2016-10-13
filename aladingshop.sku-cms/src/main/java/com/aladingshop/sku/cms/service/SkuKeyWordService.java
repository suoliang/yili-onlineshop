/**
 * 
 */
package com.aladingshop.sku.cms.service;

import com.aladingshop.sku.cms.model.SkuKeyWord;

/**
 * @description 商品关键字查询接口
 * @author 孙涛
 * @date 2015年9月6日上午11:34:55
 */
public interface SkuKeyWordService<T extends SkuKeyWord> {
	/**
	 * 根据产品code获取关键字
	 * 
	 * @param skuCode
	 * @return
	 */
	SkuKeyWord findByProductCode(String productCode);

	/**
	 * 添加关键字
	 * 
	 * @param word
	 */
	void addKey(SkuKeyWord word);

	/**
	 * 更新关键字
	 * 
	 * @param word
	 */
	void updateKey(SkuKeyWord word);

	/**
	 * 删除关键字
	 * 
	 * @param id
	 */
	void deleteKey(Long id);
}
