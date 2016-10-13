package com.fushionbaby.member.dao;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuCollectQueryCondition;
import com.fushionbaby.member.model.MemberSkuCollect;

/**
 * 
 * @author King
 * 
 */
public interface MemberSkuCollectDao {

	void add(MemberSkuCollect skuCollect);

	void deleteById(Long id);

	void update(MemberSkuCollect skuCollect);

	MemberSkuCollect findById(Long id);

	List<MemberSkuCollect> findAll();
	
	List<MemberSkuCollect> findBySkuIdAndMemberId(MemberSkuCollect skuCollect);
	
	/**
	 * 通过条件查询
	 * @param queryCondition 查询条件
	 * @return
	 */
	List<MemberSkuCollect> queryByCondition(SkuCollectQueryCondition queryCondition);
	
	/***
	 * 获得总数
	 * @param queryCondition 查询条件
	 * @return
	 */
	Integer getTotalByCondition(SkuCollectQueryCondition queryCondition);

}