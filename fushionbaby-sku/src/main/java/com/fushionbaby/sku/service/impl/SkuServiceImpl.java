/**
 * 
 */
package com.fushionbaby.sku.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.sku.dao.SkuBrandDao;
import com.fushionbaby.sku.dao.SkuDao;
import com.fushionbaby.sku.dto.SkuSearchDto;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuBrand;
import com.fushionbaby.sku.model.SkuCategory;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.model.SkuKeyWord;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.service.SkuBrandService;
import com.fushionbaby.sku.service.SkuCategoryService;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuKeyWordService;
import com.fushionbaby.sku.service.SkuLabelRelationService;
import com.fushionbaby.sku.service.SkuPriceService;
import com.fushionbaby.sku.service.SkuService;

/**
 * @author mengshaobo
 * 
 */
@Service
public class SkuServiceImpl implements SkuService {
	@Autowired
	private SkuDao skuDao;

	@Autowired
	private SkuBrandDao brandDao;

	@Autowired
	private SkuBrandService<SkuBrand> skuBrandService;
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	@Autowired
	private SkuPriceService<SkuPrice> skuPriceService;
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	
	@Autowired
	private SkuKeyWordService<SkuKeyWord> skuKeyWordService;

	@Autowired
	private SkuLabelRelationService skuLabelRelationService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.FindSkuService#queryByCondition(com.fushionbaby
	 * .sku.condition.MaSkuQueryCondition)
	 */
	public List<Sku> queryByCondition(SkuEntryQueryCondition queryCondition) {
		return skuDao.queryByCondition(queryCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.FindSkuService#queryBySkuCode(java.lang.String
	 * )
	 */
	public Sku queryBySkuCode(String skuCode) {

		return skuDao.queryBySkuCode(skuCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.FindSkuService#queryTotalByCondition(com.
	 * fushionbaby.sku.condition.MaSkuQueryCondition)
	 */
	public Integer queryTotalByCondition(SkuEntryQueryCondition queryCondition) {
		return skuDao.getTotal(queryCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.FindSkuService#queryByProductCode(java.lang
	 * .String)
	 */
	public List<Sku> queryByProductCode(String productCode) {

		return skuDao.queryByProductCode(productCode);
	}

	public List<Sku> querySkuListByLabelRel(List<String> relUnCodes,String storeCode) {

		SkuEntryQueryCondition skuQueryCondition = new SkuEntryQueryCondition();
		skuQueryCondition.setSkuCodeList(relUnCodes);
		skuQueryCondition.setStoreCode(storeCode);
		List<Sku> skus = this.queryByCondition(skuQueryCondition);
		List<Sku> sortSkus = new ArrayList<Sku>();
		for (String relCode : relUnCodes) {
			for (Sku skuEntry : skus) {
				if (ObjectUtils.equals(relCode, skuEntry.getUniqueCode())) {
					Sku sortSku = skuEntry;
					sortSkus.add(sortSku);
				}
			}
		}

		return sortSkus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.sku.service.SkuService#queryBySameDayOperateing()
	 */
	public List<SkuSearchDto> queryBySameDayOperateing() {
		List<Sku> skuList = skuDao.queryBySameDayOperateing();
		List<String> labelRelationSkuCodes = skuLabelRelationService.queryBySameDayOperateing();
		
		List<String> skuCodeList = new ArrayList<String>();
		
		if(labelRelationSkuCodes!=null){
			skuCodeList.addAll(labelRelationSkuCodes);
		}
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setSkuCodeList(skuCodeList);
		List<Sku> skus= skuDao.queryByCondition(queryCondition);
		Set<Sku> setSkus = new HashSet<Sku>(skuList);
		for (Sku sku : skus) {
				setSkus.add(sku);
		}

		List<Sku> newSkuList = new ArrayList<Sku>(setSkus);
		System.out.println("今天修改过的商品数量有："+newSkuList.size());

		return this.getSkuSearchaDtoList(newSkuList);
	}

	private List<SkuSearchDto> getSkuSearchaDtoList(List<Sku> skuList) {

		List<SkuSearchDto> skuDtos = new ArrayList<SkuSearchDto>();
		for (Sku sku : skuList) {
			SkuSearchDto skuSearch = new SkuSearchDto();
			if (StringUtils.isNotBlank(sku.getBrandCode())) {
				SkuBrand skuBrand = skuBrandService.findByBrandCode(sku.getBrandCode());
				skuSearch.setSkuBrandCode(sku.getBrandCode());
				skuSearch.setSkuBrand(ObjectUtils.notEqual(null, skuBrand)
						&& StringUtils.isNotBlank(skuBrand.getBrandName()) ? skuBrand.getBrandName() : "");
			}
			if (StringUtils.isNotBlank(sku.getCategoryCode())) {
				SkuCategory skuCategory = skuCategoryService.getCategoryByCode(StringUtils.EMPTY,sku.getCategoryCode());
				skuSearch.setSkuCategoryCode(sku.getCategoryCode());
				skuSearch.setSkuCategory(ObjectUtils.notEqual(null, skuCategory)
						&& StringUtils.isNotBlank(skuCategory.getName()) ? skuCategory.getName() : "");
			}
			skuSearch.setSkuName(sku.getName());
			skuSearch.setSkuDescription(sku.getDescription());
			skuSearch.setSkuCode(sku.getUniqueCode());
			skuSearch.setSkuStatus(sku.getStatus());
			SkuPrice skuPrice = skuPriceService.findBySkuCode(sku.getUniqueCode());
			skuSearch.setSkuPrice(skuPrice == null ? "0.00" : skuPrice.getCurrentPrice() + "");
			SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(sku.getUniqueCode());
			skuSearch.setSalesVolume(skuExtraInfo==null ? 0 : skuExtraInfo.getSalesVolume());
			skuSearch.setActualSalesVolume(skuExtraInfo==null ? 0 :skuExtraInfo.getActualSalesVolume());
			if (skuExtraInfo != null && skuExtraInfo.getOnshelvestime() != null) {
				skuSearch.setOnshelvestime(DateFormatUtils.format(skuExtraInfo.getOnshelvestime(),
						"yyyy-MM-dd'T'HH:mm:ss'Z'"));
			}
			List<String> skuCodes = new ArrayList<String>();
			skuCodes.add(sku.getUniqueCode());
			List<String> labelCodes = skuLabelRelationService.findLabelCodesBySkuCodes(skuCodes);
			skuSearch.setSkuLabel(StringUtils.join(labelCodes, ","));
			if(StringUtils.isNotBlank(sku.getProductCode())){
				SkuKeyWord  skuKeyWords = skuKeyWordService.findByProductCode(sku.getProductCode());
				if(skuKeyWords!=null && StringUtils.isNotBlank(skuKeyWords.getKeyWords())){
					String[] keyWords = StringUtils.split(skuKeyWords.getKeyWords(), ",");
					skuSearch.setKeyWords(Arrays.asList(keyWords));
				}
			}
			BeanNullConverUtil.nullConver(skuSearch);
			skuDtos.add(skuSearch);
		}
		return skuDtos;
	}

}
