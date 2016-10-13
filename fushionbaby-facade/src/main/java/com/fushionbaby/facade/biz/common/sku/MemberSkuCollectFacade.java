/**
 * 
 */
package com.fushionbaby.facade.biz.common.sku;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuCollectQueryCondition;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.SkuDto;

/**
 * @author mengshaobo
 *商品收藏
 */
public interface MemberSkuCollectFacade {
	
	/**
	 * 通过商品号移除收藏
	 * @param skuId 商品号
	 * @param user 当前用户
	 */
	Boolean dropCollectBySkuCode(String skuCode,UserDto user);

	/**
	 * 添加收藏
	 * @param skuCode 商品号
	 * @param user 当前用
	 * @return (0.未登录状态 1.已加入状态 2.加入状态)
	 */
	Integer addSkuCollect(String skuCode,UserDto user);
	/**
	 * 通过条件查询商品收藏信息
	 * @param queryCondition 查询条件
	 * @return
	 */
	List<SkuDto> findCollect(SkuCollectQueryCondition queryCondition);
	
	/**
	 * 是否已加入收藏
	 * 
	 * @param skuCode
	 *            商品编号
	 * @param user
	 * @return
	 */
	String getIsCollect(UserDto user, String skuCode) ;
}
