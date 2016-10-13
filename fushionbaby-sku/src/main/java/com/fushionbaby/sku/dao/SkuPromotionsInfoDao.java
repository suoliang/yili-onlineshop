/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午11:23:52
 */
package com.fushionbaby.sku.dao;

import java.util.List;

import com.fushionbaby.sku.model.SkuPromotionsInfo;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午11:23:52
 */
public interface SkuPromotionsInfoDao {
	
	SkuPromotionsInfo queryByPmCode(String promotionsCode);
	
	List<SkuPromotionsInfo> queryAll();
	
}
