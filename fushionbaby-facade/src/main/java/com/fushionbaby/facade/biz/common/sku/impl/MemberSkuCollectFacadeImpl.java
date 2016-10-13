/**
 * 
 */
package com.fushionbaby.facade.biz.common.sku.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.common.condition.sku.SkuCollectQueryCondition;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.facade.biz.AbstactSkuFacade;
import com.fushionbaby.facade.biz.common.sku.MemberSkuCollectFacade;
import com.fushionbaby.member.model.MemberSkuCollect;
import com.fushionbaby.member.service.MemberSkuCollectService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuPrice;

/**
 * @author mengshaobo
 *
 */
@Service
public class MemberSkuCollectFacadeImpl extends AbstactSkuFacade implements MemberSkuCollectFacade {
	@Autowired
	private MemberSkuCollectService<MemberSkuCollect> skuCollectService;

	/*
	 * (non-Javadoc)
	 * @see com.fushionbaby.facade.biz.common.sku.SkuCollectFacade#dropCollectBySkuId(java.lang.Long, com.fushionbaby.common.dto.UserDto)
	 */
	public Boolean dropCollectBySkuCode(String skuCode,UserDto user) {
		if (user != null) {
			MemberSkuCollect skuCollect = new MemberSkuCollect();
			skuCollect.setMemberId(user.getMemberId());
			skuCollect.setSkuCode(skuCode);
			return skuCollectService.deleteFavorites(skuCollect);	
		}
		return null;

	}
/*
 * (non-Javadoc)
 * @see com.fushionbaby.facade.biz.common.sku.SkuCollectFacade#addSkuCollect(java.lang.Long, com.fushionbaby.common.dto.UserDto)
 */
	public Integer addSkuCollect(String skuCode, UserDto user) {
		
		if (ObjectUtils.equals(user, null)) {
			return AppConstant.NOT_LOGIN;
		}
		if(!checkUserIdOrSkuCode(skuCode,user.getMemberId())){
			return null;
		}
		SkuCollectQueryCondition queryCondition = new SkuCollectQueryCondition();
		queryCondition.setSkuCode(skuCode);
		queryCondition.setMemberId(user.getMemberId());
		MemberSkuCollect skuCollect = null;
		List<MemberSkuCollect> findCollects = skuCollectService.queryByCondition(queryCondition);
		if (!CollectionUtils.isEmpty(findCollects) && findCollects.size() > 0) {
			skuCollect = findCollects.get(0);
			if(StringUtils.equals(skuCollect.getIsAttention(),CommonConstant.YES)){
				return AppConstant.HAS_ADD;
			}
			skuCollect.setIsAttention(CommonConstant.YES);
			skuCollectService.update(skuCollect);
			return AppConstant.SUCCESS_ADD;
		}
		Sku sku = skuService.queryBySkuCode(skuCode);
		if(sku==null){
			return AppConstant.NOT_FOUND_SKU;
		}
		SkuPrice skuPrice = skuPriceService.findBySkuCode(skuCode);
		if(skuCollect== null){
			skuCollect = new MemberSkuCollect();
			skuCollect.setSkuCode(skuCode);
			skuCollect.setMemberId(user.getMemberId());
			skuCollect.setIsAttention(CommonConstant.YES);
			skuCollect.setCurrentPrice(skuPrice.getCurrentPrice());
			skuCollect.setRetailPrice(skuPrice.getRetailPrice());
			skuCollectService.addFavorites(skuCollect);
		}
	
		return AppConstant.SUCCESS_ADD;
	}
	
	/**
	 * 检查skuId和userId是否输入合法
	 * 
	 * @param skuId
	 *            商品Id
	 * @param userId
	 *            用户Id
	 * @return 是否合法
	 */
	private boolean checkUserIdOrSkuCode(String skuCode, Long userId) {
		if (userId == null || userId == 0) {
			return false;
		}
		if ("".equals(skuCode)) {
			return false;
		}
		return true;
	}
	/*
	 * (non-Javadoc)
	 * @see com.fushionbaby.facade.biz.common.sku.SkuCollectFacade#findCollect(com.fushionbaby.common.condition.sku.SkuCollectQueryCondition)
	 */
	public List<SkuDto> findCollect(SkuCollectQueryCondition queryCondition) {
		
		List<MemberSkuCollect> skuCollectList = skuCollectService.queryByCondition(queryCondition);
		if(CollectionUtils.isEmpty(skuCollectList)){
			return new ArrayList<SkuDto>();
		}
		List<String> skuCodes = new ArrayList<String>();
		for(MemberSkuCollect collect : skuCollectList){
			skuCodes.add(collect.getSkuCode());
		}
		SkuEntryQueryCondition skuQueryCondition = new SkuEntryQueryCondition();
		skuQueryCondition.setSkuCodeList(skuCodes);
		List<Sku> skus = skuService.queryByCondition(skuQueryCondition);
		if(CollectionUtils.isEmpty(skus)){
			return new ArrayList<SkuDto>();
		}
		return this.getSkuDtoList(skus);
		
	}
	/* (non-Javadoc)
	 * @see com.fushionbaby.facade.biz.common.sku.MemberSkuCollectFacade#getIsCollect(com.fushionbaby.common.dto.UserDto, java.lang.String)
	 */
	public String getIsCollect(UserDto user, String skuCode) {

		SkuCollectQueryCondition queryCondition = new SkuCollectQueryCondition();
		queryCondition.setMemberId(user.getMemberId());
		queryCondition.setSkuCode(skuCode);
		queryCondition.setIsAttention(CommonConstant.YES);
		List<MemberSkuCollect> list = skuCollectService.queryByCondition(queryCondition);
		if (CollectionUtils.isEmpty(list)) {
			return CommonConstant.NO;
		}
		return CommonConstant.YES;
		
	}

}
