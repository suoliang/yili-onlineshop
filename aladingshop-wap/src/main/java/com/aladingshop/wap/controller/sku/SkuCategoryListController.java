package com.aladingshop.wap.controller.sku;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.service.GlobalConfig;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.WapConstant;
import com.fushionbaby.common.dto.sku.CategoryDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.dto.sku.web.PageSetDto;
import com.fushionbaby.common.util.EmptyValidUtils;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.sku.CategoryFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.facade.biz.common.yiduobao.YiDuoBaoCardFacade;

/**
 * @description 分类列表页controller
 * @author 索亮
 * @date 2015年8月7日上午10:35:17
 */
@Controller
@RequestMapping("/category")
public class SkuCategoryListController {
	private static final Log LOGGER = LogFactory
			.getLog(SkuCategoryListController.class);
	@Autowired
	private CategoryFacade categoryFacade;
	@Autowired
	private SkuFacade skuFacade;
	@Autowired
	private YiDuoBaoCardFacade yiDuoBaoCardFacade;
	@Autowired
	private GlobalConfig globalConfig;

	private List<SkuDto> allSku;

	/**
	 * 分类商品初始化
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("cateInit")
	public String categoryInit(
			Model model,
			@RequestParam(value = "categoryCode", defaultValue = "") String categoryCode) {
		/** 获取所有分类 */
		List<CategoryDto> allCa = categoryFacade.findAllCategory(StringUtils.EMPTY,true,
				CommonConstant.YES);

		model.addAttribute("cateVo", allCa);
		model.addAttribute(
				"category",
				StringUtils.isBlank(categoryCode) ? globalConfig
						.findByCode(GlobalConfigConstant.DEFAULT_CATEGORY_CODE)
						: categoryCode);

		return "category/category";
	}

	/**
	 * 关联出一级或二级分类下所有的商品列表
	 * 
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("cateList")
	public String categorySkuList(
			@RequestParam("categoryCode") String categoryCode, ModelMap map) {
		allSku = new ArrayList<SkuDto>();
		CategoryDto categoryDto = categoryFacade.getCategoryDto(StringUtils.EMPTY,categoryCode);
		if (categoryDto == null) {
			map.addAttribute("errorMsg", "请求出错，请重试。");
			return "error";
		}

		List<CategoryDto> categoryDtos = categoryFacade.findChildCategory(StringUtils.EMPTY,
				categoryDto.getCode(), true);
		if (EmptyValidUtils.arrayIsEmpty(categoryDtos)) {
			/** 如果为null，则是三级分类 */
			SkuEntryQueryCondition query = new SkuEntryQueryCondition();
			query.setCategoryCode(categoryDto.getCode());
			query.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_APP_640);
			PageSetDto result = skuFacade.searchSkuList(query);
			allSku.addAll((List<SkuDto>) result.getResults());
		} else {
			for (CategoryDto dto : categoryDtos) {
				/** 二级分类 集合 */
				for (CategoryDto child : dto.getChildCategory()) {
					SkuEntryQueryCondition query = new SkuEntryQueryCondition();
					query.setCategoryCode(child.getCode());
					query.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_APP_640);
					PageSetDto result = skuFacade.searchSkuList(query);
					allSku.addAll((List<SkuDto>) result.getResults());
				}
			}
		}

		/** 算总页数 */
		Integer allPage = (allSku.size() - 1) / WapConstant.WAP_LIST_COUNT + 1;

		map.addAttribute("result",
				this.getPageList(WapConstant.WAP_DEFAULT_PAGE));
		map.addAttribute("currentPage", WapConstant.WAP_DEFAULT_PAGE);
		map.addAttribute("allPage", allPage);
		map.addAttribute("categoryName", categoryDto.getName());

		return "sku/categorylist";
	}

	@ResponseBody
	@RequestMapping("updateList")
	public Object loginSetNewPwd(@RequestParam("currentPage") String curPage) {
		try {
			if (StringUtils.isBlank(curPage)) {
				return Jsonp.error("请求异常，请重试");
			}

			Integer page = Integer.parseInt(curPage) + 1;

			if (EmptyValidUtils.arrayIsEmpty(allSku)) {
				return Jsonp_data.success(new ArrayList<SkuDto>());
			}

			return Jsonp_data.success(getPageList(page));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("SkuLabelController更新列表异常：" + e);
			return Jsonp.error("刷新列表页失败，请重试。");
		}
	}

	private List<SkuDto> getPageList(Integer curPage) {
		List<SkuDto> results = new ArrayList<SkuDto>();
		Integer start = curPage * WapConstant.WAP_LIST_COUNT;
		Integer end = (curPage + 1) * WapConstant.WAP_LIST_COUNT;

		if (start > allSku.size()) {
			return results;
		} else if (end > allSku.size()) {
			end = allSku.size();
		}

		results = allSku.subList(start, end);

		return results;
	}
}
