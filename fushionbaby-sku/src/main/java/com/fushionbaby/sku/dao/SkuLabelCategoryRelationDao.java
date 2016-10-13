package com.fushionbaby.sku.dao;

import java.util.List;

import com.fushionbaby.common.condition.sku.LabelCategoryRelationQueryCondition;
import com.fushionbaby.sku.model.SkuLabelCategoryRelation;

/**
 * @description 标签和分类的关联
 * @author 索亮
 * @date 2015年7月27日下午4:21:02
 */
public interface SkuLabelCategoryRelationDao {
	/** 根据标签code查询出对象集合 */
	List<SkuLabelCategoryRelation> findListByLabelCode(String labelCode);
	/** 根据关联id查询出对象 */
	SkuLabelCategoryRelation findObjectById(Long id);
	
	
	List<String> findSkuCodeByLabelAndCategory(LabelCategoryRelationQueryCondition queryCondtion) ;
	
}
