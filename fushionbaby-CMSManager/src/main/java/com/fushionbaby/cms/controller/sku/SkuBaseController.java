/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月3日下午2:13:15
 */
package com.fushionbaby.cms.controller.sku;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuBrand;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.model.SkuExtraInfo;
import com.aladingshop.sku.cms.model.SkuImage;
import com.aladingshop.sku.cms.model.SkuInventory;
import com.aladingshop.sku.cms.model.SkuKeyWord;
import com.aladingshop.sku.cms.model.SkuPrice;
import com.aladingshop.sku.cms.service.SkuBrandService;
import com.aladingshop.sku.cms.service.SkuCategoryService;
import com.aladingshop.sku.cms.service.SkuExtraInfoService;
import com.aladingshop.sku.cms.service.SkuImageService;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.sku.cms.service.SkuInventoryService;
import com.aladingshop.sku.cms.service.SkuKeyWordService;
import com.aladingshop.sku.cms.service.SkuPriceService;
import com.aladingshop.sku.cms.service.SkuSearchService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.SkuCategoryDto;
import com.fushionbaby.cms.dto.SkuDto;
import com.fushionbaby.cms.dto.SkuExtraInfoDto;
import com.fushionbaby.cms.dto.SkuPriceDto;
import com.fushionbaby.common.condition.sku.SkuCategoryQueryCondition;
import com.google.gson.Gson;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月3日下午2:13:15
 */
@Controller
@RequestMapping("sku")
public abstract class SkuBaseController extends BaseController {

	
	private static final Log LOGGER=LogFactory.getLog(SkuBaseController.class);
	
	@Autowired
	protected SkuInfoService skuService;
	@Autowired
	protected SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	@Autowired
	protected SkuPriceService<SkuPrice> skuPriceService;
	@Autowired
	protected SkuExtraInfoService<SkuExtraInfo> extraInfoService;
	@Autowired
	protected SkuInventoryService<SkuInventory> inventoryService;
	@Autowired
	protected SkuImageService<SkuImage> skuImageService;

	@Autowired
	protected SkuSearchService skuSearchService;

	@Autowired
	protected SkuCategoryService<SkuCategory> skuCategoryService;

	@Autowired
	protected SkuBrandService<SkuBrand> skuBrandService;
	
	@Autowired
	protected SkuKeyWordService<SkuKeyWord> skuKeyWordService;
	
	
	
	protected List<SkuDto>  getSkuDtoList(List<Sku> skus){
		
		List<SkuDto> skuList = new ArrayList<SkuDto>();
		Long time=new Date().getTime();
		for (Sku sku : skus) {
			LOGGER.info("得到一个skudto的开始时间"+time);
			skuList.add(this.getSkuDto(sku.getUniqueCode()));
			LOGGER.info("得到一个skudto的结束时间"+(new Date().getTime()-time)/1000);
		}
		LOGGER.info("得到整个list<skudto>的结束时间"+(new Date().getTime()-time)/1000);
		return skuList;
	}
	
	
	/**
	 * 获取所以分类JSON字符串
	 * 
	 * @return
	 */
	protected String getCategoryJson() {

		SkuCategoryQueryCondition queryCondition = new SkuCategoryQueryCondition();
		queryCondition.setStoreCode(null);
		List<SkuCategory> skuCategoryList = skuCategoryService.queryByCondition(queryCondition);
				
		List<SkuCategoryDto> skuCategoryDtoList = new ArrayList<SkuCategoryDto>();
		SkuCategoryDto skuCategoryDto = null;
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
	
	
	
	protected SkuDto getSkuDto(String uniqueCode){
		SkuDto skuDto = new SkuDto();
		Sku sku = skuService.queryByUniqueCode(uniqueCode);
		if(sku==null){
			return skuDto;
		}
		skuDto.setId(sku.getId());
		skuDto.setUniqueCode(sku.getUniqueCode());
		skuDto.setSkuNo(sku.getSkuNo());
		skuDto.setBarCode(sku.getBarCode());
		skuDto.setStoreCode(sku.getStoreCode());
		skuDto.setSkuName(sku.getName());
		skuDto.setProductCode(sku.getProductCode());
		skuDto.setBrandCode(sku.getBrandCode());
		skuDto.setColor(sku.getColor());
		skuDto.setSize(sku.getSize());
		skuDto.setQuotaNum(sku.getQuotaNum()==null?null:sku.getQuotaNum().toString());
		skuDto.setShoworder( sku.getShoworder()==null ? null : sku.getShoworder().toString());
		skuDto.setStatus(sku.getStatus());
		skuDto.setSkuDescription(sku.getDescription());
		skuDto.setCreateTime(sku.getCreateTime());
		skuDto.setUpdateTime(sku.getUpdateTime());
		
		if(StringUtils.isNotBlank(sku.getBrandCode())){
			SkuBrand skuBrand = skuBrandService.findByBrandCode(sku.getBrandCode());
			skuDto.setBrandName(skuBrand==null ? StringUtils.EMPTY:skuBrand.getBrandName());
		}
		skuDto.setCategoryCode(sku.getCategoryCode());
		if(StringUtils.isNotBlank(sku.getCategoryCode())){
			SkuCategory skuCategory = skuCategoryService.findByCode(sku.getCategoryCode(),null);
			skuDto.setGrandCategoryCode(skuCategory==null ? null : skuCategory.getGrandcategoryCode());
			skuDto.setCategoryName(skuCategory==null ? null : skuCategory.getName());
		}
		
		skuDto.setSkuPriceDto(this.getSkuPriceDto(sku.getUniqueCode()));
		
		SkuExtraInfoDto skuExtraInfoDto = this.getSkuExtraInfoDto(sku.getUniqueCode());
		skuDto.setIsMemberSku(skuExtraInfoDto.getIsMemberSku());
		
		SkuKeyWord skuKeyWord = skuKeyWordService.findByProductCode(sku.getProductCode());
		skuDto.setKeyWords(skuKeyWord==null?StringUtils.EMPTY:skuKeyWord.getKeyWords());
		
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
