package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuLabelCategoryRelation;

/**
 * @description 标签和分类的关联
 * @author 索亮
 * @date 2015年7月27日下午4:21:02
 */
public interface SkuLabelCategoryRelationDao {
	/** 添加 */
	void add(SkuLabelCategoryRelation labelCategoryConfig);
	/** 根据id删除标签关联分类 */
	void deleteById(Long id);
	/** 删除 */
	void delete(SkuLabelCategoryRelation slcr);
	/** 修改 */
	void update(SkuLabelCategoryRelation labelCategoryConfig);
	/** 根据标签code查询出对象 */
	List<SkuLabelCategoryRelation> findListByLabelCode(String labelCode);
	/** 根据关联id查询出对象 */
	SkuLabelCategoryRelation findObjectById(Long id);
	/** 分页 */
	List<SkuLabelCategoryRelation> getListPage(Map<String, Object> map);
	/**
	 * 分页查询的总记录数
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);
}
