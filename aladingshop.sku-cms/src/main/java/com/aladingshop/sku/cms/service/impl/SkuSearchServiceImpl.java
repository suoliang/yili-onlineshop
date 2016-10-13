/**
 * 
 */
package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuDao;
import com.aladingshop.sku.cms.dto.SkuSearchDto;
import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuBrand;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.model.SkuExtraInfo;
import com.aladingshop.sku.cms.model.SkuLabelRelation;
import com.aladingshop.sku.cms.model.SkuPrice;
import com.aladingshop.sku.cms.service.SkuBrandService;
import com.aladingshop.sku.cms.service.SkuCategoryService;
import com.aladingshop.sku.cms.service.SkuExtraInfoService;
import com.aladingshop.sku.cms.service.SkuLabelRelationService;
import com.aladingshop.sku.cms.service.SkuPriceService;
import com.aladingshop.sku.cms.service.SkuSearchService;
import com.aladingshop.sku.cms.thread.EngineSynThredRun;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.BeanNullConverUtil;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年8月27日上午10:28:52
 */
@Service
public class SkuSearchServiceImpl implements SkuSearchService {

	
	@Autowired
	private SkuDao skuDao;
	
	
	@Autowired
	private SkuBrandService<SkuBrand> skuBrandService;
	
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	
	@Autowired
	private SkuPriceService<SkuPrice> skuPriceService;
	
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	@Autowired
	private SkuLabelRelationService<SkuLabelRelation> skuLabelRelationService;
	
	
	/* (non-Javadoc)
	 * @see com.aladingshop.sku.cms.service.SkuSearchService#addSkuDocument()
	 */
	public void addSkuDocument() {
		List<SkuSearchDto> skuList = this.queryBySameDayOperateing();
		Map<String, Map<String, Object>> data = new HashMap<String, Map<String, Object>>();
		Map<String,Object> map = null;
		for (SkuSearchDto sku : skuList) {
			map = new HashMap<String, Object>();
			map.put("skuCode",sku.getSkuCode());
			map.put("skuName", sku.getSkuName());
			map.put("skuKeyWords", sku.getKeyWords() + sku.getSkuName() + sku.getSkuBrand() + sku.getSkuCategory());
			map.put("skuLabel", sku.getSkuLabel());
			map.put("skuDescription", sku.getSkuDescription());
			map.put("skuBrand", sku.getSkuBrand());
			map.put("skuBrandCode",sku.getSkuBrandCode());
			map.put("skuCategoryCode",sku.getSkuCategoryCode());
			map.put("skuCategory", sku.getSkuCategory());
			map.put("skuPrice", sku.getSkuPrice());
			map.put("salesVolume", sku.getSalesVolume());
			map.put("actualSalesVolume", sku.getActualSalesVolume());
			map.put("onshelvestime", sku.getOnshelvestime());
			map.put("skuStatus", sku.getSkuStatus());
			String contetnt = sku.getSkuName() + sku.getSkuBrand()+ sku.getSkuCategory() + sku.getSkuDescription() + sku.getKeyWords();
			map.put("content", contetnt);
			data.put("sku"+sku.getSkuCode(), map);
		}
				
		Thread thread = new Thread(new EngineSynThredRun(data), "synEngineThread");
		thread.start();

	}

	public List<SkuSearchDto> queryBySameDayOperateing() {
		List<Sku> skuList = skuDao.queryBySameDayOperateing();
		
		List<String> labelRelationSkuCodes = skuLabelRelationService.queryBySameDayOperateing();
		List<String> skuCodeList = new ArrayList<String>();
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		if(labelRelationSkuCodes!=null){
			skuCodeList.addAll(labelRelationSkuCodes);
		}
		queryCondition.setSkuCodeList(skuCodeList);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		List<Sku> skusByProduct = skuDao.queryByCondition(queryCondition);
		Set<Sku> setSkus = new HashSet<Sku>(skuList);
		for (Sku sku : skusByProduct) {
			setSkus.add(sku);
		}
		
		List<Sku> newSkuList = new ArrayList<Sku>(setSkus);
		
		return this.getSkuSearchaDtoList(newSkuList);
	}
	
	private List<SkuSearchDto> getSkuSearchaDtoList(List<Sku> skuList){
		
		List<SkuSearchDto> skuDtos = new ArrayList<SkuSearchDto>();
		for (Sku sku : skuList) {
			SkuSearchDto skuSearch = new SkuSearchDto();
			if(StringUtils.isNotBlank(sku.getBrandCode())){
				SkuBrand skuBrand = skuBrandService.findByBrandCode(sku.getBrandCode());
				skuSearch.setSkuBrandCode(sku.getBrandCode());
				skuSearch.setSkuBrand(ObjectUtils.notEqual(null, skuBrand) && StringUtils.isNotBlank(skuBrand.getBrandName()) ? skuBrand.getBrandName() : "");
			}
			if(StringUtils.isNotBlank(sku.getCategoryCode())){
				SkuCategory skuCategory = skuCategoryService.findByCode(sku.getCategoryCode(), sku.getStoreCode());
				skuSearch.setSkuCategoryCode(sku.getCategoryCode());
				skuSearch.setSkuCategory(ObjectUtils.notEqual(null, skuCategory) && StringUtils.isNotBlank(skuCategory.getName()) ? skuCategory.getName() : "");
			}
			skuSearch.setSkuName(sku.getName());
			skuSearch.setSkuDescription(sku.getDescription());
			skuSearch.setSkuCode(sku.getUniqueCode());
			SkuPrice skuPrice = skuPriceService.findBySkuCode(sku.getUniqueCode());
			skuSearch.setSkuPrice(skuPrice==null?"0.00":skuPrice.getCurrentPrice()+"");
			SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(sku.getUniqueCode());
			skuSearch.setSalesVolume(skuExtraInfo.getSalesVolume());
			skuSearch.setActualSalesVolume(skuExtraInfo.getActualSalesVolume());
			if(skuExtraInfo!=null && skuExtraInfo.getOnshelvestime() !=null){
				skuSearch.setOnshelvestime(DateFormatUtils.format(skuExtraInfo.getOnshelvestime(), "yyyy-MM-dd'T'HH:mm:ss'Z'") );
			}
			List<String> labelCodes = skuLabelRelationService.findLabelCodesBySkuCodes(sku.getUniqueCode());
			skuSearch.setSkuLabel(StringUtils.join(labelCodes, ","));
			BeanNullConverUtil.nullConver(skuSearch);
			skuDtos.add(skuSearch);
		}
		return skuDtos;
	}

}
