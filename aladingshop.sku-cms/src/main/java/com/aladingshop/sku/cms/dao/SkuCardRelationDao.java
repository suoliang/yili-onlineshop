package com.aladingshop.sku.cms.dao;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuCardRelation;
/***
 * 储值卡和商品关联
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年9月7日下午1:53:16
 */
public interface SkuCardRelationDao {
	/***
	 * 添加
	 * @param skuCardRelation
	 */
	void add(SkuCardRelation skuCardRelation);
	/***
	 * 删除
	 * @param id
	 */
	void deleteById(Long id);
	/***
	 * 更新
	 * @param skuCardRelation
	 */
	void update(SkuCardRelation skuCardRelation);
	/***
	 * 通过id查询
	 * @param id
	 * @return
	 */
	SkuCardRelation findById(Long id);
	/***
	 * 查询全部
	 * @return
	 */
	List<SkuCardRelation> findAll();
}