package com.fushionbaby.facade.biz.common.sku.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.dto.sku.SkuCombinationDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.facade.biz.AbstactSkuFacade;
import com.fushionbaby.facade.biz.common.sku.SkuCombinationFacade;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuCombination;
import com.fushionbaby.sku.model.SkuCombinationItem;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.service.SkuCombinationItemService;
import com.fushionbaby.sku.service.SkuInventoryService;

/**
 * 
 * @author mengshaobo
 *
 */
@Service
public class SkuCombinationFacadeImpl extends AbstactSkuFacade implements SkuCombinationFacade {
	@Autowired
	private SkuCombinationItemService skuCombinationItemService;
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	
	public List<SkuDto> getSkuCombinationBySkuCode(String skuCode) {
		SkuCombinationItem skuCombinationItem = skuCombinationItemService.queryListBySkuCode(skuCode);
		if(ObjectUtils.equals(null, skuCombinationItem)){
			return new ArrayList<SkuDto>();
		}
		
		return this.getSkuByCombinationId(skuCombinationItem.getCombinationId());
	}

	public List<SkuDto> getSkuByCombinationId(Long combinationId) {
		List<SkuCombinationItem> itemList = skuCombinationItemService.
				queryListByCombinationId( combinationId);
		
		List<String> skuCodes = new ArrayList<String>();
		for (SkuCombinationItem item : itemList) {
			skuCodes.add(item.getSkuCode());
		}
		
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setSkuCodeList(skuCodes);
		List<Sku> skuList = skuService.queryByCondition(queryCondition);
		
		List<SkuDto> skuDtoList  = this.getSkuDtoList(skuList);
		for (SkuDto sku : skuDtoList) {
			SkuInventory skuInventory = skuInventoryService.findBySkuCode(sku.getSkuCode());
			sku.setColor(StringUtils.trimToNull(skuInventory.getSkuColor())  );
			sku.setSize(StringUtils.trimToNull(skuInventory.getSkuSize()));
		}
		return skuDtoList;
	}

	public SkuCombinationDto getSkuCombinationDto(List<SkuDto> skuList,
			SkuCombination skuCombination) {
		BigDecimal originalPrice =  BigDecimal.valueOf(0);
		BigDecimal combinationPrice = BigDecimal.valueOf(0);
		BigDecimal discount = BigDecimal.valueOf(0);
		for (SkuDto sku : skuList) {
			originalPrice = originalPrice.add(new BigDecimal(sku.getPriceNew()));
		}
		SkuCombinationDto skuCombinationDto = new SkuCombinationDto();
		if(ObjectUtils.notEqual(null, skuCombination)){	
			discount = skuCombination.getDiscount();
			combinationPrice = originalPrice.subtract(discount);
			skuCombinationDto.setCombinationId(skuCombination.getId());
		}
		skuCombinationDto.setOriginalPrice(originalPrice.toString());
		skuCombinationDto.setSkuDtoList(skuList);
		skuCombinationDto.setCombinationPrice(combinationPrice.toString());
		skuCombinationDto.setDiscount(discount.toString());
		return skuCombinationDto;
	}

}
