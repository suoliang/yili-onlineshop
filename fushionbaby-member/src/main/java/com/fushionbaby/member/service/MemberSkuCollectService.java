package com.fushionbaby.member.service;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuCollectQueryCondition;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.member.model.MemberSkuCollect;

/**
 * 
 * @author King
 * 
 */
public interface MemberSkuCollectService<T extends MemberSkuCollect> extends BaseService<T> {

	/**
	 * @author cyla
	 * 添加商品到用户收藏夹
	 * @param skuCollect 收藏夹内容
	 * @return 操作结果是否成
	 */
	void addFavorites(MemberSkuCollect skuCollect);
	/**
	 * @author cyla
	 *从用户收藏夹中删除一件商品
	 * @param skuCollect 收藏夹内容
	 * @return 操作结果是否成
	 */
	boolean deleteFavorites(MemberSkuCollect skuCollect);
	/**
	 * 通过商品编号查询收藏商品
	 * @param queryCondition 查询条件
	 * @return
	 */
	List<MemberSkuCollect> queryByCondition(SkuCollectQueryCondition queryCondition);
	/**
	 * 获得商品总数
	 * @param queryCondition
	 * @return
	 */
	Integer getTotalByCondition(SkuCollectQueryCondition queryCondition);
}
