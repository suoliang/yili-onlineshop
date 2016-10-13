/**
 * 
 */
package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuLabelRelation;
import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;

/**
 * @author mengshaobo
 * 
 */
public interface SkuLabelRelationDao {

	void add(SkuLabelRelation maSkuLabelRelation);

	void deleteById(Long id);

	void update(SkuLabelRelation entity);

	SkuLabelRelation findById(Long id);

	List<SkuLabelRelation> findAll();

	/***
	 * 通过条件查询标签关联信息
	 * 
	 * @param queryCondition
	 * @return
	 */
	List<SkuLabelRelation> querySkuLabelRelationByCondition(
			SkuLabelRelationQueryCondition queryCondition);
	

	/**
	 * 分页查询总条数
	 * 
	 * @param map
	 * @return
	 */
	Integer getTotal(Map<String, Object> map);

	/**
	 * 分页查询
	 * 
	 * @param map
	 * @return
	 */
	List<SkuLabelRelation> getListPage(Map<String, Object> map);


	/**
	 * 条件查询,根据多个sku_id,in查询、得到所有sku_id对应的,label_id
	 * 
	 * @param skuIds
	 * @return
	 */
	List<String> findLabelCodesBySkuCodes(Map<String, Object> map);

	/**
	 * 标签编号查询商品编号集合
	 * @param labelCode
	 * @return
	 */
	List<String> findSkuCodesByLabelCode(String labelCode);
	/**
	 * 查询当天操作的商品编号
	 * @return
	 */
	List<String> queryBySameDayOperateing();
}
