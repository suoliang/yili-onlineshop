/**
 * 
 */
package com.fushionbaby.facade.biz.common.sku;

import java.util.List;

import com.fushionbaby.common.condition.QueryCondition;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.SelectedSkuDto;
import com.fushionbaby.common.dto.sku.SkuDetailDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.dto.sku.app.SkuBrowseHistoriesDto;
import com.fushionbaby.member.model.MemberSkuShareContent;
import com.fushionbaby.sku.model.SkuKeyWord;

/**
 * @author mengshaobo
 *
 */
public interface SkuDetailFacade {
	/**
	 * 获得商品详情
	 * 
	 * @param selectedSkuDto
	 *            当前选中的商品信息
	 * @return
	 */
	SkuDetailDto getSkuDetailModel(SelectedSkuDto selectedSkuDto);

	/***
	 * 通过商品编号获取商品搭配（猜你喜欢的）
	 * 
	 * @param skuId
	 *            商品号
	 * @return
	 */
	List<SkuDto> getLinkSkus(String skuCode);

	/**
	 * 获取商品历史浏览记录
	 * 
	 * @param user
	 *            用户
	 * @return
	 */
	List<SkuBrowseHistoriesDto> getSkuBrowseHistorises(QueryCondition queryCondtion);

	/**
	 * 添加或修改商品浏览历史记录
	 * 
	 * @param user
	 *            用户
	 * @param skuCode
	 *            商品编号
	 */
	void addOrUpdateBrowseHistories(UserDto user, String skuCode);

	/**
	 * 添加用户商品分享
	 * 
	 * @param memberSkuShare
	 * @return
	 */
	String addmemberSkuShare(UserDto user, MemberSkuShareContent memberSkuShare);

	/**
	 * 通过产品编号获取关键字
	 * 
	 * @param productCode
	 * @return
	 */
	SkuKeyWord findByProductCode(String productCode);

}
