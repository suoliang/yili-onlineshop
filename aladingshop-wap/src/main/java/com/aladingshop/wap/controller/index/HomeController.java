package com.aladingshop.wap.controller.index;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fushionbaby.cache.service.GlobalConfig;
import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;
import com.fushionbaby.common.constants.AdvertisementConfigConstant;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.LabelConstant;
import com.fushionbaby.common.constants.SortTypeConstant;
import com.fushionbaby.common.dto.sku.CategoryDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.EmptyValidUtils;
import com.fushionbaby.facade.biz.common.sku.BannerFacade;
import com.fushionbaby.facade.biz.common.sku.BrandFacade;
import com.fushionbaby.facade.biz.common.sku.CategoryFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;

/**
 * @description 首页control
 * @author 孙涛
 * @date 2015年7月24日上午9:31:13
 */
@Controller
public class HomeController {
	private static Log LOGGER = LogFactory.getLog(HomeController.class);

	@Autowired
	private BannerFacade bannerFacade;

	@Autowired
	private SkuFacade skuFacade;

	@Autowired
	private CategoryFacade categoryFacade;

	@Autowired
	private BrandFacade brandFacade;

	@Autowired
	private GlobalConfig globalConfig;

	@RequestMapping(value = "home")
	public String initHomePage(ModelMap model, HttpServletRequest request) {
		/** 记录开始时间 */
		Long startTime = System.currentTimeMillis();
		/**
		 * 首页banner
		 */
		model.addAttribute("BANNER", bannerFacade
				.getFocusPic(AdvertisementConfigConstant.WAP_INDEX_FOCUS));
		/** 分类列表 */
		model.addAttribute("CATEGORYS", this.getHomeCategoryList(globalConfig
				.findByCode(GlobalConfigConstant.WAP_CATEGORY)));

		/** 专题banner */
		model.addAttribute("SEMINAR", bannerFacade
				.getFocusPic(AdvertisementConfigConstant.WAP_INDEX_SEMINAR));

		/** 特惠街 */
		String thjCount = globalConfig.findByCode(GlobalConfigConstant.WAP_THJ);
		model.addAttribute(LabelConstant.THJ, this.getHomeLabelSkuList(
				NumberUtils.toInt(thjCount), LabelConstant.THJ,
				ImageStandardConstant.IMAGE_STANDARD_PC_300));

		LOGGER.info("initial home page cost time(ms):"
				+ String.valueOf(System.currentTimeMillis() - startTime));
		return "home";
	}

	/**
	 * 获取首页标签下的商品集合
	 * 
	 * @param count
	 *            显示数量
	 * @param labelCode
	 *            标签编号
	 * @param imageStandardCode
	 *            图片尺寸
	 * @return
	 */
	private List<SkuDto> getHomeLabelSkuList(Integer count, String labelCode,
			String imageStandardCode) {

		SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();

		queryCondition.setStart((AppConstant.START_INDEX - 1) * count);
		queryCondition.setLimit(count);
		queryCondition.setSortParam(SortTypeConstant.DEFAULT);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setLabelCode(labelCode);
		queryCondition.setImageStandardCode(imageStandardCode);
		queryCondition.setAppHome(CommonConstant.YES);
		queryCondition.setDisabled(CommonConstant.YES);

		List<SkuDto> labelSkus = skuFacade.getLabelSkuList(queryCondition);

		return labelSkus;
	}

	private List<CategoryDto> getHomeCategoryList(String count) {
		Integer max = Integer.parseInt(count);
		/** 获取所有一级分类 */
		List<CategoryDto> allCa = categoryFacade.findAllCategory(StringUtils.EMPTY,false,
				CommonConstant.YES);
		if (!EmptyValidUtils.arrayIsEmpty(allCa) && allCa.size() > max) {
			allCa = allCa.subList(0, max);
		}

		return allCa;
	}
}
