/**
 * 
 */
package com.aladingshop.sku.cms.service;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuLabelRelation;
import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @author mengshaobo
 * 
 */
public interface SkuLabelRelationService<T extends SkuLabelRelation> extends
		BaseService<T> {

	/**
	 * 通过标签编号查询
	 * 
	 * @param labelId
	 *            标签编号
	 * @param sortParam
	 *            排序参数
	 * @param sortType
	 *            排序类型
	 * 
	 * @param index
	 *            当前页数
	 * 
	 * @param limit
	 *            每页显示数量
	 * @return
	 */
	List<T> queryByLabelCode(String labelCode, String sortParam, String sortType, Integer index, Integer limit);

	/**
	 * 分页查询标签关联 CMS
	 * 
	 * @param page
	 * @return
	 */
	BasePagination getPageList(BasePagination page);

	/**
	 * 通过标签号查询总数
	 * 
	 * @param labelId
	 * @return
	 * */
	Integer getTotalByLabelCode(String labelCode);

	/**
	 * 添加多个商品标签关联
	 * 
	 * @param list
	 */
	void insertList(List<SkuLabelRelation> list);

	/**
	 * 通过标签编号查询商品编号集合(,号分割)
	 * 
	 * @param labelId
	 * @return
	 */
	String getSkuCodeAryByLabelCode(String labelCode);

	/***
	 * 根据标签id删除数据
	 * 
	 * @param labelId
	 */
	void deleteByLabelCode(String labelCode,String storeCode);

	/**
	 * 根据标签code和商品code删除
	 * 
	 * @param skuLabelRelation
	 */
	 void deleteBySkuCodeAndLabelCode(SkuLabelRelation skuLabelRelation);
	
	/**
	 * 通过商品编号和标签获取标签管理
	 * @param skuCode 商品编号
	 * @param labelCode 标签编号
	 * @return
	 */
	SkuLabelRelation findBySkuCodeAndLabel(String skuCode,String labelCode);

	/**
	 * 条件查询,根据多个sku_id,in查询、得到所有sku_id对应的,label_id
	 * 
	 * @param skuIds
	 * @return
	 */
	List<String> findLabelCodesBySkuCodes(String skuCodes);

	/**
	 * 条件查询商品和标签关联信息
	 * @param queryCondition
	 * @return
	 */
	List<SkuLabelRelation> queryByCondition(
			SkuLabelRelationQueryCondition queryCondition);

	/**
	 * 标签号查询商品编号集合
	 * @param labelCode
	 * @return
	 */
	List<String> findSkuCodesByLabel(String labelCode,String storeCode);
	
	
	/**
	 * 查询当天创建的记录 商品编号
	 * @return
	 */
	List<String> queryBySameDayOperateing();
	
}
