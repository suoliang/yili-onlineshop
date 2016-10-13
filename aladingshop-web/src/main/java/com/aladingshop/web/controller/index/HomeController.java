package com.aladingshop.web.controller.index;

import java.util.ArrayList;
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

import com.aladingshop.web.vo.CategoryGoodVo;
import com.aladingshop.web.vo.CategoryVo;
import com.fushionbaby.cache.service.GlobalConfig;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;
import com.fushionbaby.common.constants.AdvertisementConfigConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.LabelConstant;
import com.fushionbaby.common.constants.SortTypeConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.sku.CategoryDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.dto.sku.web.PageSetDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.facade.biz.common.sku.BannerFacade;
import com.fushionbaby.facade.biz.common.sku.BrandFacade;
import com.fushionbaby.facade.biz.common.sku.CategoryFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.sysmgr.model.SysmgrAppHomeConfig;
import com.fushionbaby.sysmgr.service.SysmgrAppHomeConfigService;

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
	
	/**配置的要显示的一级分类*/
	@Autowired
	private SysmgrAppHomeConfigService<SysmgrAppHomeConfig> sysmgrAppHomeConfigService;

	@RequestMapping(value = "home")
	public String initHomePage(ModelMap model, HttpServletRequest request) {
		/** 记录开始时间 */
		Long startTime = System.currentTimeMillis();
		/**
		 * 首页banner
		 */
		model.addAttribute("BANNER", bannerFacade.getFocusPic(AdvertisementConfigConstant.WEB_INDEX_FOCUS));
		/** 每日专场 */
		String mrzcCount = globalConfig.findByCode(GlobalConfigConstant.WEB_MRZC);
		model.addAttribute(LabelConstant.MRZC, this.getHomeMrzcLabelSkuList(NumberUtils.toInt(mrzcCount),
				LabelConstant.MRZC, ImageStandardConstant.IMAGE_STANDARD_PC_160));
		/** 精品推荐 */
/*		String jptjCount = globalConfig.findByCode(GlobalConfigConstant.WEB_JPTJ);

		model.addAttribute(LabelConstant.JPTJ, this.getHomeJptjLabelSkuList(NumberUtils.toInt(jptjCount),
				LabelConstant.JPTJ, ImageStandardConstant.IMAGE_STANDARD_PC_254));
*/
		
		List<CategoryVo> homeFloorList = this.getHomeFloorList(NumberUtils.toInt(globalConfig.findByCode(GlobalConfigConstant.WEB_FLOOR_GOOD)));
		model.addAttribute("FLOORLIST", homeFloorList);
		/** 分类集合 */
	/*	model.addAttribute("FLOORLIST", 
				this.getHomeFloorList(
				NumberUtils.toInt(globalConfig.findByCode(GlobalConfigConstant.WEB_FLOOR_GOOD))
				//,NumberUtils.toInt(globalConfig.findByCode(GlobalConfigConstant.WEB_FLOOR_HOT_GOOD))
				//,NumberUtils.toInt(globalConfig.findByCode(GlobalConfigConstant.WEB_FLOOR_HOT_BRAND))
				));*/
		
		
		
		model.addAttribute(
				"BANNERCATEGORY",
				getHomeCategoryList(NumberUtils.toInt(globalConfig.findByCode(GlobalConfigConstant.WEB_MENU_HOT_BRAND))));

		LOGGER.info("initial home page cost time(ms):" + String.valueOf(System.currentTimeMillis() - startTime));
		return "home";
	}

	/**
	 * 获取首页每日专场标签下的商品集合
	 * 
	 * @param count
	 *            显示数量
	 * @param labelCode
	 *            标签编号
	 * @param imageStandardCode
	 *            图片尺寸
	 * @return	
	 */
	private List<SkuDto> getHomeMrzcLabelSkuList(Integer count, String labelCode, String imageStandardCode) {

		SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();

		queryCondition.setStart((WebConstant.START_INDEX - 1) * count);
		queryCondition.setLimit(count);
		queryCondition.setSortParam(SortTypeConstant.DEFAULT);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setLabelCode(labelCode);
		queryCondition.setImageStandardCode(imageStandardCode);
		queryCondition.setAppHome(CommonConstant.NO);
		queryCondition.setDisabled(CommonConstant.YES);

		List<SkuDto> labelSkus = skuFacade.getLabelSkuList(queryCondition);

		return labelSkus;
	}

	/**
	 * 获取首页精品推荐标签下的商品集合
	 * 
	 * @param count
	 *            显示数量
	 * @param labelCode
	 *            标签编号
	 * @param imageStandardCode
	 *            图片尺寸
	 * @return
	 */
/*	private List<CategoryGoodVo> getHomeJptjLabelSkuList(Integer count, String labelCode, String imageStandardCode) {
		List<CategoryGoodVo> goodVos = new ArrayList<CategoryGoodVo>();
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setLabelCode(labelCode);
		queryCondition.setStart((WebConstant.START_INDEX - 1) * count);
		queryCondition.setLimit(count);
		queryCondition.setSortParam(SortTypeConstant.DEFAULT);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setImageStandardCode(imageStandardCode);
		List<LabelCategoryRelationDto> relList = skuFacade.getSkuListByLabelCategory(queryCondition);
		for (LabelCategoryRelationDto labCateRelDto : relList) {
			CategoryGoodVo goodVo = new CategoryGoodVo();
			CategoryDto categoryDto = categoryFacade.getCategoryDto(StringUtils.EMPTY,labCateRelDto.getCategoryCode());
			goodVo.setChildCategory(categoryDto);
			goodVo.setSkuList(labCateRelDto.getSkuDtoList());
			goodVos.add(goodVo);
		}

		return goodVos;
	}*/

	private List<CategoryVo> getHomeCategoryList(Integer count) {
		List<CategoryVo> cateVoList = new ArrayList<CategoryVo>();
		List<CategoryGoodVo> cateGoodList = null;
		/** 获取所有一级分类 */
		//List<CategoryDto> allCa = categoryFacade.findAllCategory(StringUtils.EMPTY,false, CommonConstant.YES);
		SysmgrAppHomeConfig config = sysmgrAppHomeConfigService.findByPlatfrom(2);
		List<CategoryDto> allCa = this.findAllCategory(config.getCategory());
		
		/** 封装为页面显示CategoryGoodVo对象 [封装一级二级三级分类] */
		for (CategoryDto caDto : allCa) {
			CategoryVo categoryVo = new CategoryVo();
			cateGoodList = new ArrayList<CategoryGoodVo>();
			/** 所有二级分类集合及三级分类集合 */
			List<CategoryDto> curChilds = categoryFacade.findChildCategory(StringUtils.EMPTY,caDto.getCode(), true);
			if (curChilds == null) {
				continue;
			}

			for (CategoryDto curChild : curChilds) {
				CategoryGoodVo goodVo = new CategoryGoodVo();
				goodVo.setChildCategory(curChild);
				/** 根据一级分类关联品牌列表 */
				//categoryVo.setBrandDtos(brandFacade.getBrandDtoByCategoryCode(caDto.getCode(), count));
				/** 三级分类 */
				if(curChild.getChildCategory()!=null&&curChild.getChildCategory().size()>0)//过滤掉三级分类没有的二级
					{  goodVo.setThirdCategory(curChild.getChildCategory());
					     cateGoodList.add(goodVo);
				    }
			}

			categoryVo.setCategoryDto(caDto);
			categoryVo.setCategoryGoodVos(cateGoodList);
			cateVoList.add(categoryVo);
		}
		return cateVoList;
	}

	/***
	 * 和 app 显示的一级分类一样，后台可配置
	 * @param category
	 * @return
	 */
	private List<CategoryDto> findAllCategory(String category) {
		List<CategoryDto> categoryDtoList=new ArrayList<CategoryDto>();
		for (String code : StringUtils.split(category, ",")) {
			CategoryDto skuCategory = categoryFacade.getCategoryDto(null, code);
			List<CategoryDto> curChilds = categoryFacade.findChildCategory(StringUtils.EMPTY,code, false);
			if(skuCategory!=null&&curChilds!=null&&curChilds.size()>0)
			   categoryDtoList.add(skuCategory);
		}
		return categoryDtoList;
	}

	
	@SuppressWarnings("unchecked")
	private List<CategoryVo> getHomeFloorList(Integer skuCount
			//, Integer hotSkuCount
			//, Integer hotBrandCount
			) {
		List<CategoryVo> cateGoods = new ArrayList<CategoryVo>();
		List<CategoryGoodVo> cateGoodList = null;
		/** 获取所有一级分类 */

		List<CategoryDto> allCa = categoryFacade.findAllCategory(StringUtils.EMPTY,false, CommonConstant.YES);

		for (int i=0;i< allCa.size();i++) {
			/**移除掉 美容的和没发的分类*/
			if("7788".contains(allCa.get(i).getCode()))
				allCa.remove(i);
		}
		/** 封装为页面显示CategoryGoodVo对象 [封装一级二级分类] */
		for (CategoryDto caDto : allCa) {
			cateGoodList = new ArrayList<CategoryGoodVo>();
			List<CategoryDto> curChilds = categoryFacade.findChildCategory(StringUtils.EMPTY,caDto.getCode(), false);
			if (curChilds == null) {
				continue;
			}

			for (CategoryDto curChild : curChilds) {
				CategoryGoodVo goodVo = new CategoryGoodVo();
				goodVo.setChildCategory(curChild);
				/** 根据二级分类获取相关商品列表 */
				PageSetDto sortPageSet = this.getSkuByQueryCondition(skuCount, curChild.getCode(),
						ImageStandardConstant.IMAGE_STANDARD_PC_160);
				goodVo.setSkuList((List<SkuDto>) sortPageSet.getResults());
				/** 根据二级分类关联品牌列表 */
				/*goodVo.setHotBrands(brandFacade.getBrandDtoByCategoryCode(curChild.getCode(), hotBrandCount));*/
				/** 根据二级分类关联热销商品TOP3TODO */
			/*	List<SkuDto> hotSkuList = this.getHotSkuByQueryCondition(hotSkuCount, curChild.getCode(),
						ImageStandardConstant.IMAGE_STANDARD_PC_54);
				goodVo.setHotGoods(hotSkuList);*/
				cateGoodList.add(goodVo);
			}

			cateGoods.add(new CategoryVo(caDto, cateGoodList));
		}
		return cateGoods;
	}

	private PageSetDto getSkuByQueryCondition(Integer count, String categoryCode, String imageStandardCode) {
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setCategoryCode(categoryCode);
		queryCondition.setStart((WebConstant.START_INDEX - 1) * count);
		queryCondition.setLimit(count);
		queryCondition.setSortParam(SortTypeConstant.DEFAULT);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setImageStandardCode(imageStandardCode);
		return skuFacade.searchSkuList(queryCondition);
	}

/*	private List<SkuDto> getHotSkuByQueryCondition(Integer count, String categoryCode, String imageStandardCode) {
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setCategoryCode(categoryCode);
		queryCondition.setStart((WebConstant.START_INDEX - 1) * count);
		queryCondition.setLimit(count);
		queryCondition.setSortParam(SortTypeConstant.DEFAULT);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setImageStandardCode(imageStandardCode);
		return skuFacade.getHotSkuList(queryCondition);
	}*/
}
