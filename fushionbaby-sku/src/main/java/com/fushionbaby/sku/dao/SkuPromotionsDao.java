/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午10:54:02
 */
package com.fushionbaby.sku.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.sku.model.SkuPromotions;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午10:54:02
 */
public interface SkuPromotionsDao {
	
	List<SkuPromotions> queryByPmCode(@Param("pmCode") String pmCode);
	
	SkuPromotions queryByPmCodeAndSkuCode(@Param("pmCode") String pmCode,@Param("skuCode") String skuCode);
}
