package com.aladingshop.sku.cms.service;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuLabelCategoryRelation;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 标签和分类的关联
 * @author 索亮
 * @date 2015年7月27日下午7:22:03
 */
public interface SkuLabelCategoryRelationService<T extends SkuLabelCategoryRelation> {
	/** 添加 */
	void add(SkuLabelCategoryRelation labelCategoryConfig);
	/**根据标签关联分类id删除*/
	void deleteById(Long id);
	/** 删除 */
	void delete(String labelCode,String categoryCode);
	/** 修改 */
	void update(SkuLabelCategoryRelation labelCategoryConfig);
	/** 根据标签查询出对象集合 */
	List<SkuLabelCategoryRelation> findListByLabelCode(String labelCode);
	/** 根据关联id查询出对象 */
	SkuLabelCategoryRelation findObjectById(Long id);
	/** 分页 */
	BasePagination getListPage(BasePagination page);
}
