/**
 * 
 */
package com.fushionbaby.facade.biz.common.sku.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import util.ImageConstantFacade;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.fushionbaby.common.condition.sku.LabelCategoryRelationQueryCondition;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.condition.sku.SkuSearchQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.dto.BrandDto;
import com.fushionbaby.common.dto.sku.CategoryDto;
import com.fushionbaby.common.dto.sku.LabelCategoryRelationDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.dto.sku.web.PageSetDto;
import com.fushionbaby.common.dto.sku.web.SortPageSetDto;
import com.fushionbaby.common.dto.sysmgr.SearchKeywordsDto;
import com.fushionbaby.common.util.BeanCopyUtil;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.facade.biz.common.order.dto.SkuSearchResultDto;
import com.fushionbaby.facade.biz.common.sku.AbstractIndexFacade;
import com.fushionbaby.facade.biz.common.sku.BrandFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.sku.dto.SkuSearchDto;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuBrand;
import com.fushionbaby.sku.model.SkuCategory;
import com.fushionbaby.sku.model.SkuLabelCategoryRelation;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.service.SkuCategoryService;
import com.fushionbaby.sku.service.SkuHotService;
import com.fushionbaby.sku.service.SkuLabelCategoryRelationService;
import com.fushionbaby.sku.service.search.SkuSearchService;
import com.fushionbaby.sysmgr.model.SysmgrSearchKeywords;
import com.fushionbaby.sysmgr.service.SysmgrSearchKeywordsService;

/**
 * @author mengshaobo
 *
 */
@Service
public class SkuFacadeImpl extends AbstractIndexFacade implements SkuFacade {

	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	@Autowired
	private SysmgrSearchKeywordsService<SysmgrSearchKeywords> sysSearchKeyImpl;
	@Autowired
	private SkuSearchService skuSearchService;
	@Autowired
	private SkuHotService skuHotService;
	@Autowired
	private SkuLabelCategoryRelationService<SkuLabelCategoryRelation> skuLabelCategoryRelationService;
	@Autowired
	private BrandFacade brandFacade;
	
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	private static final Log LOGGER = LogFactory.getLog(SkuFacadeImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.SkuFacade#searchSkuList(com.fushionbaby
	 * .common.condition.sku.SkuEntryQueryCondition)
	 */
	public PageSetDto searchSkuList(SkuEntryQueryCondition queryCondition) {
		queryCondition.setSearchKey(this.getSearchKey(queryCondition
				.getSearchKey()));
		SortPageSetDto pageSet = new SortPageSetDto();
		List<String> categoryCodes = null;
		try {
			categoryCodes = skuCategoryService
					.getLowestChildCategorysByCode(queryCondition.getStoreCode(),queryCondition
							.getCategoryCode());
		} catch (Exception e) {
			LOGGER.error("查询商品分类时出现异常，返回结果是：null！" + e.getMessage());
		}
		queryCondition.setCategoryCodes(categoryCodes);

		List<Sku> skuList = skuService.queryByCondition(queryCondition);
		List<SkuDto> skuDtoList = this.getSkuDtoList(skuList);
		for (SkuDto skuDto : skuDtoList) {
			skuDto.setImgPath(imageProcess.getThumImagePath(
					skuDto.getSkuCode(), queryCondition.getImageStandardCode()));
		}
		pageSet.setResults(skuDtoList);
		Integer amount = skuService.queryTotalByCondition(queryCondition);
		pageSet.setAmount(amount.longValue());
		pageSet.setTotalPageByAmount(amount.longValue());
		if (queryCondition.getStart() != null
				&& queryCondition.getLimit() != null) {
			pageSet.setCurPage(queryCondition.getStart()
					/ queryCondition.getLimit() + 1);
		}
		pageSet.setSortParam(queryCondition.getSortParam());
		pageSet.setSortType(queryCondition.getSortType() == null ? StringUtils.EMPTY
				: queryCondition.getSortType());
		return pageSet;
	}

	private String getSearchKey(String searchKey) {
		if (!StringUtils.isBlank(searchKey)) {
			String newSearchKey = "";
			char[] searchKeyChars = searchKey.toCharArray();
			for (int i = 0; i < searchKeyChars.length; i++) {
				if (searchKeyChars.length == 1) {
					newSearchKey += searchKeyChars[i] + "";
				} else {
					if (i < (searchKeyChars.length - 1)) {
						newSearchKey += searchKeyChars[i] + "%";
					} else {
						newSearchKey += searchKeyChars[i] + "";
					}
				}
			}
			return newSearchKey;
		}
		return searchKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.facade.biz.common.sku.SkuFacade#insertSearchKey(com.
	 * fushionbaby.common.dto.sysmgr.SearchKeywordsDto)
	 */
	public void insertSearchKey(SearchKeywordsDto searchKeywordsDto) {

		sysSearchKeyImpl.insertSearchKey(searchKeywordsDto.getKeyword(),
				searchKeywordsDto.getSourceCode(), searchKeywordsDto.getIp());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.SkuFacade#skuListBysearchKey(com
	 * .fushionbaby.common.condition.sku.SkuEntryQueryCondition)
	 */
	public SkuSearchResultDto skuListBysearchKey(
			SkuSearchQueryCondition queryCondition) {

		List<Sku> skuList = skuSearchService.querySkuList(queryCondition);
		List<SkuDto> skuDtoList = this.getSkuDtoList(skuList);
		for (SkuDto skuDto : skuDtoList) {
			skuDto.setImgPath(imageProcess.getThumImagePath(
					skuDto.getSkuCode(), queryCondition.getImageStandardCode()));
		}
		SkuSearchResultDto searchResultDto = new SkuSearchResultDto();

		SkuSearchDto skuSearch = skuSearchService
				.queryResByCondition(queryCondition);
		PageSetDto pageSet = new PageSetDto();
		pageSet.setResults(skuDtoList);
		Long numFound = skuSearch.getNumFound();
		pageSet.setCurPage(queryCondition.getStart());
		pageSet.setTotalPageByAmount(numFound);
		pageSet.setAmount(numFound);
		searchResultDto.setPageSet(pageSet);

		List<BrandDto> brandDtos = this.getSearchResultBrandDto(skuSearch
				.getBrandList());
		searchResultDto.setBrandList(brandDtos);

		List<CategoryDto> categoryDtos = this
				.getSearchResultCategoryDto(skuSearch.getCategoryList());
		searchResultDto.setCategoryList(categoryDtos);
		return searchResultDto;
	}

	/**
	 * 获取搜索结果商品品牌列表
	 * 
	 * @param brandList
	 * @return
	 */

	private List<BrandDto> getSearchResultBrandDto(List<SkuBrand> brandList) {
		List<BrandDto> brandDtos = new ArrayList<BrandDto>();
		if (brandList != null) {
			for (SkuBrand brand : brandList) {
				if (brand == null) {
					continue;
				}
				brandDtos.add(this.assBrandDto(brand));
			}
		}

		return brandDtos;

	}

	/**
	 * 获取搜索结果商品分类列表
	 * 
	 * @param categoryList
	 * @return
	 */
	private List<CategoryDto> getSearchResultCategoryDto(
			List<SkuCategory> categoryList) {
		List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>();
		for (SkuCategory skuCategory : categoryList) {
			CategoryDto c = new CategoryDto();
			c.setName(skuCategory.getName());
			c.setCode(skuCategory.getCode());
			c.setLogoUrl(StringUtils.isBlank(skuCategory.getCategoryLogoUrl()) ? StringUtils.EMPTY
					: ImageConstantFacade.IMAGE_SERVER_ROOT_PATH
							+ skuCategory.getCategoryLogoUrl());
			categoryDtos.add(c);
		}
		return categoryDtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.SkuFacade#getHotSkuList(com.fushionbaby
	 * .common.condition.sku.SkuEntryQueryCondition)
	 */
	public List<SkuDto> getHotSkuList(SkuEntryQueryCondition queryCondition) {

		List<Sku> skuList = skuHotService.findHotSkuList(queryCondition);

		List<SkuDto> skuDtoList = this.getSkuDtoList(skuList);
		for (SkuDto skuDto : skuDtoList) {
			skuDto.setImgPath(imageProcess.getThumImagePath(
					skuDto.getSkuCode(), queryCondition.getImageStandardCode()));
		}
		return skuDtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.SkuFacade#getSkuListByLabelCategory
	 * (com.fushionbaby.common.condition.sku.SkuEntryQueryCondition)
	 */
	public List<LabelCategoryRelationDto> getSkuListByLabelCategory(
			SkuEntryQueryCondition queryCondition) {
		List<LabelCategoryRelationDto> relDtoList = new ArrayList<LabelCategoryRelationDto>();
		List<SkuLabelCategoryRelation> relList = skuLabelCategoryRelationService
				.findListByLabelCode(queryCondition.getLabelCode());
		for (SkuLabelCategoryRelation rel : relList) {
			LabelCategoryRelationDto relDto = new LabelCategoryRelationDto();
			try {
				BeanCopyUtil.copyProperty(rel, relDto, null);
				LabelCategoryRelationQueryCondition lcrq = new LabelCategoryRelationQueryCondition();
				lcrq.setCategoryCode(rel.getCategoryCode());
				lcrq.setLabelCode(rel.getLabelCode());
				lcrq.setStart(queryCondition.getStart());
				lcrq.setLimit(queryCondition.getLimit());
				List<String> skuCodeList = skuLabelCategoryRelationService
						.findSkuCodeByLabelAndCategory(lcrq);
				if (CollectionUtils.isEmpty(skuCodeList)) {
					continue;
				}
				List<SkuDto> skuDtoList = this
						.findSkuListBySkuCodeList(skuCodeList);
				for (SkuDto skuDto : skuDtoList) {
					skuDto.setImgPath(imageProcess.getThumImagePath(
							skuDto.getSkuCode(),
							queryCondition.getImageStandardCode()));
				}
				relDto.setSkuDtoList(skuDtoList);
			} catch (Exception e) {
				LOGGER.error("entity 转换DTO时出现异常" + e.getMessage());
			}
			relDtoList.add(relDto);
		}

		return relDtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.SkuFacade#findSkuListBySkuCodeList
	 * (java.util.List)
	 */
	public List<SkuDto> findSkuListBySkuCodeList(List<String> skuCodeList) {

		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setSkuCodeList(skuCodeList);

		List<Sku> skuList = skuService.queryByCondition(queryCondition);

		return this.getSkuDtoList(skuList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.SkuFacade#queryTerm(java.lang.String
	 * , int)
	 */
	public List<Map<String, Object>> queryTerm(String q, int limit) {

		List<Map<String, Object>> results = skuSearchService.autoComplete(q,
				limit);
		return results;
	}

	public SkuDto findBySkuCode(String skuCode) {

		Sku sku = skuService.queryBySkuCode(skuCode);
		List<Sku> skuList = new ArrayList<Sku>();
		skuList.add(sku);

		SkuDto skuDto = this.getSkuDtoList(skuList).get(0);
		BeanNullConverUtil.nullConver(skuDto);
		return skuDto;
	}

	public BigDecimal getCurrentSkuPrice(String skuCode, Long memberId,String isMemberSku) {
		
		SkuPrice skuPrice = skuPriceService.findBySkuCode(skuCode);
		
		if(memberId!=null && StringUtils.equals(isMemberSku, CommonConstant.YES) 
				&& skuPrice.getAladingPrice()!=null && skuPrice.getAladingPrice().compareTo(BigDecimal.ZERO)==1 ){
			
			AlabaoAccount alabaoAccount = alabaoAccountService.findByMemberId(memberId);
			if(alabaoAccount!=null && StringUtils.equals(alabaoAccount.getLevel(),"3")){
				return skuPrice.getAladingPrice();
			}
		}
		return skuPrice.getCurrentPrice();
	}

}
