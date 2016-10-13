/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月18日下午5:08:41
 */
package com.aladingshop.store.controller.sku;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuBrand;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.model.SkuExtraInfo;
import com.aladingshop.sku.cms.model.SkuImage;
import com.aladingshop.sku.cms.model.SkuInventory;
import com.aladingshop.sku.cms.model.SkuKeyWord;
import com.aladingshop.sku.cms.model.SkuPrice;
import com.aladingshop.sku.cms.model.SkuProductAttr;
import com.aladingshop.sku.cms.service.SkuBrandService;
import com.aladingshop.sku.cms.service.SkuCategoryService;
import com.aladingshop.sku.cms.service.SkuExtraInfoService;
import com.aladingshop.sku.cms.service.SkuImageService;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.sku.cms.service.SkuInventoryService;
import com.aladingshop.sku.cms.service.SkuKeyWordService;
import com.aladingshop.sku.cms.service.SkuPriceService;
import com.aladingshop.sku.cms.service.SkuProductAttrService;
import com.aladingshop.store.controller.BaseController;
import com.aladingshop.store.dto.SkuCategoryDto;
import com.aladingshop.store.dto.SkuDto;
import com.aladingshop.store.dto.SkuExtraInfoDto;
import com.aladingshop.store.dto.SkuPriceDto;
import com.fushionbaby.common.condition.sku.SkuCategoryQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.google.gson.Gson;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月18日下午5:08:41
 */
public abstract class AbstractSkuInfoController extends BaseController {
	
	protected static final String IMG_PATH = "/userfiles/images/sku/store/";

	
	@Autowired
	protected SkuInfoService skuInfoService;
	
	@Autowired
	protected SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	
	@Autowired
	private SkuKeyWordService<SkuKeyWord> skuKeyWordService;
	
	@Autowired
	protected SkuPriceService<SkuPrice> skuPriceService;
	
	@Autowired
	protected SkuCategoryService<SkuCategory>  categoryService;
	
	@Autowired
	protected SkuCategoryService<SkuCategory> skuCategoryService;
	
	@Autowired
	protected SkuProductAttrService<SkuProductAttr> skuProductAttrService;
	
	@Autowired
	protected SkuBrandService<SkuBrand> skuBrandService;
	
	@Autowired
	protected SkuInventoryService<SkuInventory> skuInventoryService;
	
	@Autowired
	protected SkuImageService<SkuImage> skuImageService;
	
	
	/**
	 * 添加商品额外信息
	 */
	protected SkuExtraInfo initExtraInfo(SkuExtraInfo skuExtraInfo){
		if(skuExtraInfo==null){
			skuExtraInfo = new SkuExtraInfo();
		}
		BeanNullConverUtil.nullConver(skuExtraInfo);
		skuExtraInfo.setId(null);
		skuExtraInfo.setHasDiscount(CommonConstant.NO);
		skuExtraInfo.setHasGift(CommonConstant.NO);
		skuExtraInfo.setIsGift(CommonConstant.NO);
		skuExtraInfo.setIsMemberSku(CommonConstant.NO);
		skuExtraInfo.setIsVideo(CommonConstant.NO);
		return skuExtraInfo;
	}
	
	/**
	 * 获取所有分类JSON字符串
	 * 
	 * @return
	 */
	protected String getCategoryJson(String storeCode,String isUseAll) {
		
		SkuCategoryQueryCondition queryCondition = new SkuCategoryQueryCondition();
		queryCondition.setStoreCode(storeCode);
		

		List<SkuCategory> skuCategoryList = skuCategoryService.queryByCondition(queryCondition);
				
		List<SkuCategoryDto> skuCategoryDtoList = new ArrayList<SkuCategoryDto>();
		SkuCategoryDto skuCategoryDto = null;
		if(StringUtils.equals(CommonConstant.YES, isUseAll)){
			skuCategoryDto = new SkuCategoryDto();
			skuCategoryDto.setId("");
			skuCategoryDto.setpId("0");
			skuCategoryDto.setName("全部");
			skuCategoryDto.setCode("");
			skuCategoryDtoList.add(skuCategoryDto);
		}
		for (SkuCategory skuCategory : skuCategoryList) {
			skuCategoryDto = new SkuCategoryDto();
			skuCategoryDto.setId(skuCategory.getCode());
			skuCategoryDto.setpId(StringUtils.isBlank(skuCategory.getGrandcategoryCode()) ? "0" : skuCategory
					.getGrandcategoryCode());
			skuCategoryDto.setName(skuCategory.getName());
			skuCategoryDto.setCode(skuCategory.getCode());
			skuCategoryDtoList.add(skuCategoryDto);
		}
		Gson gson = new Gson();
		return gson.toJson(skuCategoryDtoList);

	}
	
	
	
	protected List<SkuDto>  getSkuDtoList(List<Sku> skus){
		
		List<SkuDto> skuList = new ArrayList<SkuDto>();
		for (Sku sku : skus) {
			skuList.add(this.getSkuDto(sku.getUniqueCode()));
		}
		
		return skuList;
	}
	
	
	
	protected SkuDto getSkuDto(String uniqueCode){
		SkuDto skuDto = new SkuDto();
		Sku sku = skuInfoService.queryByUniqueCode(uniqueCode);
		if(sku==null){
			return skuDto;
		}
		skuDto.setId(sku.getId());
		skuDto.setUniqueCode(sku.getUniqueCode());
		skuDto.setSkuNo(sku.getSkuNo());
		skuDto.setSkuName(sku.getName());
		skuDto.setQuotaNum(sku.getQuotaNum()==null?null:sku.getQuotaNum().toString());
		skuDto.setShoworder( sku.getShoworder()==null ? null : sku.getShoworder().toString());
		skuDto.setStatus(sku.getStatus());
		skuDto.setCreateTime(sku.getCreateTime());
		skuDto.setUpdateTime(sku.getUpdateTime());
		
		skuDto.setCategoryCode(sku.getCategoryCode());
		if(StringUtils.isNotBlank(sku.getCategoryCode())){
			SkuCategory skuCategory = skuCategoryService.findByCode(sku.getCategoryCode(), sku.getStoreCode());
			skuDto.setGrandCategoryCode(skuCategory==null ? null : skuCategory.getGrandcategoryCode());
			skuDto.setCategoryName(skuCategory==null ? null : skuCategory.getName());
		}
		
		SkuInventory skuInventory = skuInventoryService.findBySkuCode(sku.getUniqueCode());
		skuDto.setAvailableQty(skuInventory==null? -1 : skuInventory.getAvailableQty());
		skuDto.setCurrentPrice(this.getSkuPriceDto(sku.getUniqueCode()).getCurrentPrice());
		
		return skuDto;
	}
	
	protected SkuPriceDto getSkuPriceDto(String skuCode){
		SkuPriceDto skuPriceDto = new SkuPriceDto();
		SkuPrice skuPrice = skuPriceService.findBySkuCode(skuCode);
		if(skuPrice==null){
			return skuPriceDto;
		}
		skuPriceDto.setAladingPrice(skuPrice.getAladingPrice());
		skuPriceDto.setCostPrice(skuPrice.getCostPrice());
		skuPriceDto.setCurrentPrice(skuPrice.getCurrentPrice());
		skuPriceDto.setMarketPrice(skuPrice.getMarketPrice());
		skuPriceDto.setPrePrice(skuPrice.getPrePrice());
		skuPriceDto.setRetailPrice(skuPrice.getRetailPrice());
		skuPriceDto.setSkuCode(skuCode);
		
		return skuPriceDto;
	}
	
	protected SkuExtraInfoDto getSkuExtraInfoDto(String skuCode){
		
		SkuExtraInfoDto skuExtraInfoDto = new SkuExtraInfoDto();
		
		SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(skuCode);
		if(skuExtraInfo==null)
		{
			return skuExtraInfoDto;
		}
		skuExtraInfoDto.setActualSalesVolume(skuExtraInfo.getActualSalesVolume());
		skuExtraInfoDto.setHasDiscount(skuExtraInfo.getHasDiscount());
		skuExtraInfoDto.setHasGift(skuExtraInfo.getHasGift());
		skuExtraInfoDto.setIsGift(skuExtraInfo.getIsGift());
		skuExtraInfoDto.setIsMemberSku(skuExtraInfo.getIsMemberSku());
		skuExtraInfoDto.setIsVideo(skuExtraInfo.getIsVideo());
		skuExtraInfoDto.setSalesVolume(skuExtraInfo.getSalesVolume());
		skuExtraInfoDto.setSkuCode(skuCode);
		skuExtraInfoDto.setVideoUrl(skuExtraInfo.getVideoUrl());
		
		return skuExtraInfoDto;
	}
}
