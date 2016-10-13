/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年11月6日上午10:31:31
 */
package com.fushionbaby.sku.dao;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuLabelQueryCondition;
import com.fushionbaby.sku.model.SkuLabelImage;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年11月6日上午10:31:31
 */
public interface SkuLabelImageDao {

	
	
	List<SkuLabelImage> findByCondition(
			SkuLabelQueryCondition queryCondition);
}
