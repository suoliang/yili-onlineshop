/**
 * 
 */
package com.aladingshop.sku.cms.dao;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.sku.cms.model.SkuKeyWord;

/**
 * @description 商品关键字dao
 * @author 孙涛
 * @date 2015年9月6日上午10:30:43
 */
public interface SkuKeyWordDao {
	/**
	 * 根据产品code查询关键字
	 * 
	 * @param skuCode
	 * @return
	 */
	SkuKeyWord findByProductCode(@Param("productCode") String productCode);

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
