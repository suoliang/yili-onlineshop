/**
 * 
 */
package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.common.condition.sku.LabelCategoryRelationQueryCondition;
import com.fushionbaby.sku.model.SkuLabelCategoryRelation;

/**
 * @description 标签和分类的关联
 * @author 索亮
 * @date 2015年7月27日下午7:22:03
 */
public interface SkuLabelCategoryRelationService<T extends SkuLabelCategoryRelation> {
	
	/**
	 * 根据标签查询出对象集合 
	 * @param labelCode
	 * @return
	 */
	List<SkuLabelCategoryRelation> findListByLabelCode(String labelCode);
	
	/**
	 * 根据关联id查询出对象 
	 * @param id
	 * @return
	 */
	SkuLabelCategoryRelation findObjectById(Long id);
	
	/**
	 * 根据标签编号和分类编号查询出商品编号集合
	 * @param labelCode 标签编号
	 * @param categoryCode 分类编号
	 * @return
	 */
	List<String> findSkuCodeByLabelAndCategory(LabelCategoryRelationQueryCondition queryCondtion) ;
	
}
