/**
 * 
 */
package com.fushionbaby.facade.biz.common.sku;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;
import com.fushionbaby.common.condition.sku.SkuSearchQueryCondition;
import com.fushionbaby.common.dto.sku.LabelCategoryRelationDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.dto.sku.web.PageSetDto;
import com.fushionbaby.common.dto.sysmgr.SearchKeywordsDto;
import com.fushionbaby.facade.biz.common.order.dto.SkuSearchResultDto;

/**
 * @author mengshaobo
 *
 */
public interface SkuFacade {

	/**
	 * 获取标签下的商品列表
	 * 
	 * @param queryCondition
	 * @return
	 */
	List<SkuDto> getLabelSkuList(SkuLabelRelationQueryCondition queryCondition);

	/**
	 * 通过查询（条件搜索）商品分页后的集合
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return
	 */
	PageSetDto searchSkuList(SkuEntryQueryCondition queryCondition);

	/***
	 * 关键字查询商品
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return
	 */

	SkuSearchResultDto skuListBysearchKey(SkuSearchQueryCondition queryCondition);

	/**
	 * 记录搜索关键字
	 * 
	 * @param searchKeywordsDto
	 *            关键字DTO
	 */
	void insertSearchKey(SearchKeywordsDto searchKeywordsDto);

	
	/**
	 * 热销商品列表 
	 * @param queryCondition
	 * @return
	 */
	List<SkuDto> getHotSkuList(SkuEntryQueryCondition queryCondition);
	/**
	 *  商品编号查询商品信息DTO
	 * @return
	 */
	SkuDto findBySkuCode(String skuCode);
	
	
	/**
	 * 通过商品编号集合获取商品列表
	 * @param skuCodeList 商品编号集合
	 * @return
	 */
	List<SkuDto> findSkuListBySkuCodeList(List<String> skuCodeList);
	
	/**
	 * 查询标签下的分类下的商品集合
	 * @param queryCondition 查询条件
	 * @return
	 */
	List<LabelCategoryRelationDto> getSkuListByLabelCategory(
			SkuEntryQueryCondition queryCondition) ;
	
	/**
	 * 搜索提示
	 * @param q 关键字
	 * @param limit 显示数量
	 * @return
	 */
	List<Map<String,Object>> queryTerm(String q,int limit);
	
	/**
	 * 获得当前用户下的当前商品价格
	 * @param skuCode
	 * @param memberId
	 * @param isMemberSku
	 * @return
	 */
	BigDecimal getCurrentSkuPrice(String skuCode,Long memberId,String isMemberSku);
	
}
