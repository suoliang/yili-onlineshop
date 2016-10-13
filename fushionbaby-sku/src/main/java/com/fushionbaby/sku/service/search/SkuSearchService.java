/**
 * 
 */
package com.fushionbaby.sku.service.search;

import java.util.List;
import java.util.Map;

import com.fushionbaby.common.condition.sku.SkuSearchQueryCondition;
import com.fushionbaby.sku.dto.SkuSearchDto;
import com.fushionbaby.sku.model.Sku;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年7月24日上午10:03:14
 */
public interface SkuSearchService {
	
	/** 添加商品到搜索引擎文档*/
	 void addSkuDocument();
	 
	 /**
	  * 通过查询条件查询商品信息集合
	  * @param queryCondition 查询条件
	  * @return
	  */
	 List<Sku> querySkuList(SkuSearchQueryCondition queryCondition);
	 
	 /**
	  * 条件查询商品信息
	  * @param queryCondition
	  * @return
	  */
	SkuSearchDto queryResByCondition(SkuSearchQueryCondition queryCondition);
	 
	/**
	 * 查询关键字自动补全 
	 * @param prefix 前缀名
	 * @param min 显示数量
	 * @return
	 */
	List<Map<String,Object>> autoComplete(String prefix, int min);
	 
	 
	 
}
