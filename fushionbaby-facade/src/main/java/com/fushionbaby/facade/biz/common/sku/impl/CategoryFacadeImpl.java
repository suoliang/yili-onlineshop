/**
 * 
 */
package com.fushionbaby.facade.biz.common.sku.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import util.ImageConstantFacade;

import com.fushionbaby.common.condition.sku.SkuCategoryQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.sku.CategoryDto;
import com.fushionbaby.common.dto.sku.LabelCategoryRelationDto;
import com.fushionbaby.common.util.BeanCopyUtil;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.facade.biz.common.sku.BrandFacade;
import com.fushionbaby.facade.biz.common.sku.CategoryFacade;
import com.fushionbaby.sku.model.SkuCategory;
import com.fushionbaby.sku.model.SkuCategoryImage;
import com.fushionbaby.sku.model.SkuLabelCategoryRelation;
import com.fushionbaby.sku.service.SkuCategoryImageService;
import com.fushionbaby.sku.service.SkuCategoryService;
import com.fushionbaby.sku.service.SkuLabelCategoryRelationService;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年7月29日上午10:29:31
 */
@Service
public class CategoryFacadeImpl implements CategoryFacade {

	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	@Autowired
	private SkuCategoryImageService<SkuCategoryImage> skuCategoryImageService;

	@Autowired
	private SkuLabelCategoryRelationService<SkuLabelCategoryRelation> skuLabelCategoryRelationService;

	@Autowired
	private BrandFacade brandFacade;

	/** 记录日志 */
	private static final Log LOGGER = LogFactory
			.getLog(CategoryFacadeImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.CategoryFacade#getCategoryDto(java
	 * .lang.String)
	 */
	public CategoryDto getCategoryDto(String storeCode,String categoryCode) {
		SkuCategoryQueryCondition queryCondition = new SkuCategoryQueryCondition();
		queryCondition.setStoreCode(storeCode);
		queryCondition.setCode(categoryCode);
		
		List<SkuCategory> skuCategoryList = skuCategoryService
				.findByCondition(queryCondition);
		if(skuCategoryList==null || skuCategoryList.size()==0){
			return new CategoryDto();
		}
		SkuCategory skuCategory = skuCategoryList.get(0);
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName(skuCategory.getName());
		categoryDto.setCode(skuCategory.getCode());
		categoryDto.setLevel(skuCategory.getCategoryLevel());
		categoryDto.setParentCode(skuCategory.getGrandcategoryCode());
		List<SkuCategoryImage> categoryImagesList = skuCategoryImageService
				.findByCategoryCode(categoryCode);

		List<String> bannerList = new ArrayList<String>();
		List<String> titleList = new ArrayList<String>();
		for (SkuCategoryImage skuCategoryImage : categoryImagesList) {
			skuCategoryImage.setImgUrl(StringUtils.isNotBlank(skuCategoryImage
					.getImgUrl()) ? ImageConstantFacade.IMAGE_SERVER_ROOT_PATH
					+ skuCategoryImage.getImgUrl() : "");
			if (StringUtils.equals(skuCategoryImage.getImageTypeCode(),
					WebConstant.CATEGORY_LOGO)) {
				categoryDto.setLogoUrl(skuCategoryImage.getImgUrl());
			} else if (StringUtils.equals(skuCategoryImage.getImageTypeCode(),
					WebConstant.CATEGORY_TITLE)) {
				titleList.add(skuCategoryImage.getImgUrl());
			} else {
				bannerList.add(skuCategoryImage.getImgUrl());
			}
		}
		categoryDto.setBannerUrl(bannerList);
		categoryDto.setTitleUrl(titleList);
		return categoryDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.CategoryFacade#findAllCategory(
	 * boolean)
	 */
	public List<CategoryDto> findAllCategory(String storeCode,boolean isNext, String showType) {

		SkuCategoryQueryCondition queryCondition = new SkuCategoryQueryCondition();
		queryCondition.setStoreCode(storeCode);
		queryCondition.setCategoryLevel(1);
		boolean flag = StringUtils.endsWithAny(showType, new String[] {
				CommonConstant.YES, CommonConstant.NO });
		queryCondition.setIsShow(flag ? showType : null);
		List<SkuCategory> firstCategorys = skuCategoryService
				.findByCondition(queryCondition);
		return this.getCategoryDto(storeCode,firstCategorys, isNext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.CategoryFacade#findChildCategory
	 * (java.lang.String, boolean)
	 */
	public List<CategoryDto> findChildCategory(String storeCode,String code, boolean isNext) {
		List<SkuCategory> childCategorys = skuCategoryService
				.findCategoryByGrandcategoryCode(storeCode,code);
		
		return this.getCategoryDto(storeCode,childCategorys, isNext);
	}

	/**
	 * 分类集合DTO
	 * 
	 * @param categorys
	 * @return
	 */
	private List<CategoryDto> getCategoryDto(String storeCode,List<SkuCategory> categorys,
			boolean isNext) {
		List<CategoryDto> cgdtos = new ArrayList<CategoryDto>();
		if(CollectionUtils.isEmpty(categorys)){
			return cgdtos;
		}
		
		for (SkuCategory skuCategory : categorys) {
			if (StringUtils.equals(skuCategory.getIsShow(), CommonConstant.NO)) {
				continue;
			}
			String code = skuCategory.getCode();
			CategoryDto c = new CategoryDto();
			c.setIsShow(skuCategory.getIsShow());
			c.setName(skuCategory.getName());
			c.setEnglishName(skuCategory.getEnglishName());
			c.setCode(code);
			c.setLevel(skuCategory.getCategoryLevel());
			c.setLinkUrl(skuCategory.getLinkUrl());
			c.setLogoUrl(StringUtils.isNotBlank(
					skuCategory.getCategoryLogoUrl()) ? 
							ImageConstantFacade.IMAGE_SERVER_ROOT_PATH + skuCategory.getCategoryLogoUrl() : "");
			if (isNext && skuCategory.getCategoryLevel()<3) {
				c.setChildCategory(this.findChildCategory(storeCode,code, isNext));
			}
//			c.setBrandList(brandFacade.getBrandDtoByCategoryCode(code, null));
			BeanNullConverUtil.nullConver(c);
			
			cgdtos.add(c);
		}
		return cgdtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.CategoryFacade#getLabelCategory
	 * (java.lang.String)
	 */
	public List<LabelCategoryRelationDto> getLabelCategory(String labelCode) {
		List<LabelCategoryRelationDto> relDtoList = new ArrayList<LabelCategoryRelationDto>();
		List<SkuLabelCategoryRelation> relList = skuLabelCategoryRelationService
				.findListByLabelCode(labelCode);
		for (SkuLabelCategoryRelation rel : relList) {
			LabelCategoryRelationDto relDto = new LabelCategoryRelationDto();
			try {
				BeanCopyUtil.copyProperty(rel, relDto, null);
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
	 * com.fushionbaby.facade.biz.common.sku.CategoryFacade#findByCategoryCode
	 * (java.lang.String)
	 */
	public SkuCategory findByCategoryCode(String storeCode,String categoryCode) {
		return skuCategoryService.getCategoryByCode(storeCode,categoryCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.CategoryFacade#getParentCategoryDto
	 * (java.lang.String)
	 */
	public CategoryDto getParentCategoryDto(String storeCode,String code) {

		SkuCategory skuCategory = skuCategoryService.getParentCategory(storeCode,code);
		CategoryDto categoryDto = new CategoryDto();
		if(skuCategory!=null){
			categoryDto.setName(skuCategory.getName());
			categoryDto.setCode(skuCategory.getCode());
			categoryDto.setLogoUrl(StringUtils.isNotBlank(skuCategory.getCategoryLogoUrl()) ? ImageConstantFacade.IMAGE_SERVER_ROOT_PATH
							+ skuCategory.getCategoryLogoUrl() : "");
		}
		return categoryDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.CategoryFacade#findParentCategory
	 * (java.lang.String)
	 */
	public List<CategoryDto> findParentCategory(String storeCode,String code) {
		List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>();
		CategoryDto categoryDto = this.getCategoryDto(storeCode,code);
		categoryDtos.add(categoryDto);
		while (categoryDtos.get(categoryDtos.size() - 1).getLevel() != 1) {
			/** 当前分类level不为1则继续获取 */
			categoryDtos.add(this.getCategoryDto(storeCode,categoryDtos.get(categoryDtos.size() - 1).getParentCode()));
		}

		Collections.sort(categoryDtos);
		/** 重新排序 */
		return categoryDtos;
	}

}
