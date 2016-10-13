/**
 * 
 */
package com.fushionbaby.web.controller.sku;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.common.condition.sku.SkuBrandQueryCondition;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.ImageTypeConstant;
import com.fushionbaby.common.constants.SortTypeConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.sku.SkuBrandDto;
import com.fushionbaby.common.dto.sku.SkuBrandGraphemeDto;
import com.fushionbaby.common.dto.sku.SkuHotDto;
import com.fushionbaby.common.dto.sku.SkuLabelDto;
import com.fushionbaby.common.dto.sku.web.SortPageSetDto;
import com.fushionbaby.common.dto.sysmgr.SearchKeywordsDto;
import com.fushionbaby.common.enums.CategoryPageEnum;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.BeanCopyUtil;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.facade.biz.web.sku.WebSkuFacade;
import com.fushionbaby.sku.model.SkuBrand;
import com.fushionbaby.sku.model.SkuBrandRelation;
import com.fushionbaby.sku.model.SkuCategory;
import com.fushionbaby.sku.model.SkuCategoryRelation;
import com.fushionbaby.sku.model.SkuLabel;
import com.fushionbaby.sku.model.SkuLabelRelation;
//import com.fushionbaby.sku.service.SkuAttrService;
import com.fushionbaby.sku.service.SkuBrandRelationService;
import com.fushionbaby.sku.service.SkuBrandService;
import com.fushionbaby.sku.service.SkuCategoryRelationService;
import com.fushionbaby.sku.service.SkuCategoryService;
import com.fushionbaby.sku.service.SkuLabelRelationService;
import com.fushionbaby.sku.service.SkuLabelService;
import com.fushionbaby.sysmgr.model.SysmgrSearchKeywords;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.sysmgr.service.SysmgrSearchKeywordsService;

/**
 * @author mengshaobo
 * 
 */
@Controller
@RequestMapping("/product")
public class ProductController {

	private static final Log LOGGER = LogFactory.getLog(ProductController.class);
	
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
//	@Autowired
//	private SkuAttrService skuAttrService;
	@Autowired
	private SkuBrandRelationService<SkuBrandRelation> skuBrandRelationService;
	@Autowired
	private SkuBrandService<SkuBrand> skuBrandService;
	@Autowired
	private SkuCategoryRelationService<SkuCategoryRelation> skuCategoryRelationService;
	@Autowired
	private SkuLabelRelationService<SkuLabelRelation> skuLabelRelationService;
	@Autowired
	private SkuLabelService<SkuLabel> skuLabelService;
	@Autowired
	private SysmgrSearchKeywordsService<SysmgrSearchKeywords> sysSearchKeyImpl;
	@Autowired
	protected SysmgrGlobalConfigService configService;
	
	@Autowired
	private SkuFacade skuFacade;
	@Autowired
	private WebSkuFacade webSkuFacade;

	
	private static final String PAGSEET = "pageset";
	/**
	 * 搜索商品
	 * 
	 * @param searchKey
	 *            关键字
	 * @param curPage
	 *            当前页
	 * @param model
	 * @return
	 */
	@RequestMapping("search-list")
	public String searchList(
			@RequestParam("searchKey") String searchKey,
			@RequestParam(value = "cur_page", defaultValue = WebConstant.DEFAULT_PAGE_INDEX + "") Integer curPage,
			@RequestParam(value = "sort_param", defaultValue = SortTypeConstant.DEFAULT + "") Integer sortParam,
			@RequestParam(value = "sort_type", defaultValue = "") Integer sortType,
			ModelMap model, HttpServletRequest request) {
		if (StringUtils.isBlank(StringUtils.trim(searchKey))) {
			model.addAttribute("searchKey", StringUtils.EMPTY);
			return "common/none";
		}
		String searchContent = null;
		try {
			searchContent = URLDecoder.decode(searchKey, "utf-8");
			SearchKeywordsDto searchKeywordsDto = new SearchKeywordsDto();
			searchKeywordsDto.setKeyword(StringUtils.trim(searchContent));
			searchKeywordsDto.setSourceCode(SourceConstant.WEB_CODE);
			searchKeywordsDto.setIp(GetIpAddress.getIpAddr(request));
			skuFacade.insertSearchKey(searchKeywordsDto);
			
			SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
			queryCondition.setImageType(ImageTypeConstant.IMAGE_TYPE_WEB_5);
			queryCondition.setSortParam(sortParam);
			queryCondition.setSortType(sortType);
			queryCondition.setSearchKey(StringUtils.trim(searchContent));
			queryCondition.setStart((curPage - 1) * WebConstant.SKU_LIST_SIZE);
			queryCondition.setLimit(WebConstant.SKU_LIST_SIZE);
			queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
	
			SortPageSetDto pageSet = skuFacade.searchSkuList(queryCondition);
			if(ObjectUtils.equals(null, pageSet) || CollectionUtils.isEmpty(pageSet.getSkuList())) {
				model.addAttribute("searchKey", searchContent.trim());
				return "common/none";
			}
			model.addAttribute(PAGSEET, pageSet);
			model.addAttribute("searchKey", StringUtils.trim(searchKey));
		} catch (UnsupportedEncodingException e) {
			LOGGER.debug(e.getMessage());
			return "common/404";
		}

		return "search-list";
	}

	/**
	 * 查询更多品牌
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("brand-more-list")
	public String brandMoreList(ModelMap model) {
		
		SkuBrandQueryCondition queryCondition = new SkuBrandQueryCondition();
		queryCondition.setIsShow(CommonConstant.YES);
		try {
			List<SkuBrandDto> brandList = skuFacade.findBrandList(queryCondition);
			List<SkuBrandGraphemeDto> skuBrandGraphemeDtos = webSkuFacade.getSkuBrandGraphemes(brandList);
			if(!CollectionUtils.isEmpty(skuBrandGraphemeDtos)){
				model.addAttribute("skuBrandGraphemes", skuBrandGraphemeDtos);
			}
			Integer skuHotCount = configService.getConfigValueByCode(WebConstant.WEB_SKU_HOT_COUNT_BRAND);
			List<SkuHotDto> skuHots = webSkuFacade.getSkuHot(skuHotCount);
			if(!CollectionUtils.isEmpty(skuHots)){
				model.addAttribute("skuHots", skuHots);
			}
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			return "common/404";
		}
		
		
		return "brand_list";

	}

	/***
	 * 标签更多产品
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("product-more-list")
	public String productMoreList(
			@RequestParam(value = "cur_page", defaultValue = WebConstant.DEFAULT_PAGE_INDEX + "") Integer curPage,
			@RequestParam("label_code") String labelCode,
			@RequestParam(value = "brand_code",defaultValue = "") String brandCode,
			@RequestParam(value = "sort_param", defaultValue = SortTypeConstant.DEFAULT + "") Integer sortParam,
			@RequestParam(value = "sort_type", defaultValue = "") Integer sortType, ModelMap model) {
		
		SkuLabel label = skuLabelService.getByCode(labelCode);
		
		if(ObjectUtils.equals(null, label)){
			return "common/404";
		}

		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setImageType(ImageTypeConstant.IMAGE_TYPE_WEB_5);
		queryCondition.setSortParam(sortParam);
		queryCondition.setSortType(sortType);
		queryCondition.setLabelCode(label.getCode());
		queryCondition.setBrandCode(brandCode);
		queryCondition.setStart((curPage - 1) * WebConstant.SKU_LIST_SIZE);
		queryCondition.setLimit(WebConstant.SKU_LIST_SIZE);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		SortPageSetDto pageSet = skuFacade.searchSkuList(queryCondition);		
		SkuLabelDto skuLabelDto = new SkuLabelDto();
		try {
			BeanCopyUtil.copyProperty(label, skuLabelDto, null);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			return "common/404";
		}
		model.addAttribute(PAGSEET, pageSet);
		model.addAttribute("skuLabel", skuLabelDto);
		if(brandCode !=""&&brandCode!=null){
			model.addAttribute("brandId", brandCode);
		}
		return "product-label-list";
	}

	

	/**
	 * 通过品牌号获得商品列表
	 * 
	 * @param brandId
	 *            品牌号
	 * @param model
	 * @return
	 */
	@RequestMapping("productListByBrand")
	public String productListByBrand(
			@RequestParam(value = "cur_page", defaultValue = WebConstant.DEFAULT_PAGE_INDEX + "") Integer curPage,
			@RequestParam(value = "sort_param", defaultValue = SortTypeConstant.DEFAULT + "") Integer sortParam,
			@RequestParam(value = "sort_type", defaultValue = "") Integer sortType,
			@RequestParam("brandCode") String brandCode, ModelMap model) {

		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setImageType(ImageTypeConstant.IMAGE_TYPE_WEB_5);
		queryCondition.setBrandCode(brandCode);
		queryCondition.setSortParam(sortParam);
		queryCondition.setSortType(sortType);
		queryCondition.setStart((curPage - 1) * WebConstant.SKU_LIST_SIZE);
		queryCondition.setLimit(WebConstant.SKU_LIST_SIZE);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());

		try {
			SortPageSetDto pageSet = skuFacade.searchSkuList(queryCondition);		
			model.addAttribute(PAGSEET, pageSet);
			model.addAttribute("skuBrand", skuFacade.getBrandDto(brandCode));
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			return "common/404";
		}

		return "brand-product-list";
	}

	/**
	 * 通过分类号查询产品列表
	 * 
	 * @param categoryId
	 *            分类编号
	 * @param model
	 * @return
	 */
	@RequestMapping("productListByCategory")
	public String productListByCategory(
			@RequestParam(value = "cur_page", defaultValue = WebConstant.DEFAULT_PAGE_INDEX + "") Integer curPage,
			@RequestParam("category_code") String categoryCode,
			@RequestParam(value = "brand_code",defaultValue="") String brandCode, 
			@RequestParam(value = "sort_param", defaultValue = SortTypeConstant.DEFAULT + "") Integer sortParam,
			@RequestParam(value = "sort_type", defaultValue = "") Integer sortType, ModelMap model) {
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setImageType(ImageTypeConstant.IMAGE_TYPE_WEB_5);
		queryCondition.setBrandCode(brandCode);
		queryCondition.setCategoryCode(categoryCode);
		queryCondition.setStart((curPage - 1) * WebConstant.SKU_LIST_SIZE);
		queryCondition.setLimit(WebConstant.SKU_LIST_SIZE);
		queryCondition.setSortParam(sortParam);
		queryCondition.setSortType(sortType);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		SortPageSetDto pageSet = skuFacade.searchSkuList(queryCondition);		
		try {
			SkuCategory category = skuCategoryService.getCategoryByCode(categoryCode);

			model.addAttribute("fushionbaby_title", CategoryPageEnum.getTitle(category.getCode()));
		} catch (Exception e) {	
			LOGGER.debug(e.getMessage());
			return "common/404";
		}
		model.addAttribute("skuCategory",skuFacade.getCategoryDto(categoryCode));
		if(ObjectUtils.notEqual(null, brandCode)){
			model.addAttribute("brandCode", brandCode);
		}
		model.addAttribute(PAGSEET, pageSet);
		return "product-category-list";
	}


}
