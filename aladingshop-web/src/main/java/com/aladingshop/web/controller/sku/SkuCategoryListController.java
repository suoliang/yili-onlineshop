package com.aladingshop.web.controller.sku;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import util.ImageConstantFacade;

import com.aladingshop.card.dto.res.YiDuoBaoConfigResDto;
import com.aladingshop.web.vo.CategoryGoodVo;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.sku.CategoryDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.facade.biz.common.sku.CategoryFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.facade.biz.common.yiduobao.YiDuoBaoCardFacade;
import com.fushionbaby.sysmgr.model.SysmgrAppHomeConfig;
import com.fushionbaby.sysmgr.service.SysmgrAppHomeConfigService;

/**
 * @description 分类列表页controller
 * @author 索亮
 * @date 2015年8月7日上午10:35:17
 */
@Controller
@RequestMapping("/category")
public class SkuCategoryListController {
	private static Log LOGGER = LogFactory.getLog(SkuCategoryListController.class);
	@Autowired
	private CategoryFacade categoryFacade;
	@Autowired
	private SkuFacade skuFacade;
	@Autowired
	private YiDuoBaoCardFacade yiDuoBaoCardFacade;

	
	@Autowired
	private SysmgrAppHomeConfigService<SysmgrAppHomeConfig> sysmgrAppHomeConfigService;
	/**
	 * 洗涤用品
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("01")
	public String CateListA(Model model,@RequestParam("code")String code) {
		
		SysmgrAppHomeConfig config = sysmgrAppHomeConfigService.findByPlatfrom(2);
		if(config==null||config.getCategory()==null||!config.getCategory().contains(code)){
			code="01";
		}
	    String categoryCode = code;
		setCategory01Map(model, categoryCode);
		return "category/listStyle1";
	}

	
	

	/**
	 * 家庭用品
	 * 
	 * @param model
	 * @return
	 */
	//@RequestMapping("02")
	public String cateListB(Model model) {
		String categoryCode = "02";
		setCategory01Map(model, categoryCode);
		return "category/listStyle2";
	}

	/***
	 * 分类列表页面
	 */
	private void setCategory01Map(Model model,String categoryCode) {
		CategoryDto category = categoryFacade.getCategoryDto(StringUtils.EMPTY,categoryCode);

		//List<CategoryGoodVo> cateGoodList = this.getCategoryGoodsVo(categoryCode);
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();

		queryCondition.setCategoryCode(categoryCode);
		queryCondition.setStart(0);
		queryCondition.setLimit(WebConstant.SKU_LIST_SIZE);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_PC_300);

		List<SkuDto> hotSkuList = skuFacade.getHotSkuList(queryCondition);

		model.addAttribute("category", category);
		//model.addAttribute("cateGoodList", cateGoodList);
		model.addAttribute("hotSkuList", hotSkuList);
	}
	/**
	 * 小食品组
	 * 
	 * @param model
	 * @return
	 */
	//@RequestMapping("03")
	public String cateListC(Model model) {
		final String categoryCode = "03";
		CategoryDto category = categoryFacade.getCategoryDto(StringUtils.EMPTY,categoryCode);
		List<CategoryGoodVo> cateGoodList = this.getCategoryGoodsVo(categoryCode);
		for (CategoryGoodVo categoryGoodVo : cateGoodList) {
			SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
			queryCondition.setCategoryCode(categoryGoodVo.getChildCategory().getCode());
			queryCondition.setStart(0);
			queryCondition.setLimit(7);
			queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
			queryCondition.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_PC_300);
			@SuppressWarnings("unchecked")
			List<SkuDto> skuList = (List<SkuDto>) skuFacade.searchSkuList(queryCondition).getResults();
			categoryGoodVo.setSkuList(skuList);
		}

		model.addAttribute("category", category);
		model.addAttribute("cateGoodList", cateGoodList);
		return "category/listStyle3";
	}

	/**
	 * 粮油调味组
	 * 
	 * @param model
	 * @return
	 */
	//@RequestMapping("04")
	public String CateListD(Model model) {
		final String categoryCode = "04";
		setCategory01Map(model, categoryCode);
		return "category/listStyle1";
	}

	/**
	 * 酒水饮料组
	 * 
	 * @param model
	 * @return
	 */
	//@RequestMapping("05")
	public String cateListE(Model model) {
		final String categoryCode = "05";
		setCategory01Map(model, categoryCode);

		return "category/listStyle2";
	}

	/**
	 * 速食组
	 * 
	 * @param model
	 * @return
	 */
	//@RequestMapping("06")
	public String cateListF(Model model) {
		final String categoryCode = "06";
		CategoryDto category = categoryFacade.getCategoryDto(StringUtils.EMPTY,categoryCode);
		List<CategoryGoodVo> cateGoodList = this.getCategoryGoodsVo(categoryCode);
		for (CategoryGoodVo categoryGoodVo : cateGoodList) {
			SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
			queryCondition.setCategoryCode(categoryGoodVo.getChildCategory().getCode());
			queryCondition.setStart(0);
			queryCondition.setLimit(7);
			queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
			queryCondition.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_PC_300);
			@SuppressWarnings("unchecked")
			List<SkuDto> skuList = (List<SkuDto>) skuFacade.searchSkuList(queryCondition).getResults();
			categoryGoodVo.setSkuList(skuList);
		}

		model.addAttribute("category", category);
		model.addAttribute("cateGoodList", cateGoodList);
		return "category/listStyle3";
	}

	/**
	 * 初始化卡产品首页
	 * 
	 * @return
	 */
	//@RequestMapping("010")
	public String initCardIndexReq(HttpServletRequest request, Model model) {
		try {
			List<YiDuoBaoConfigResDto> resDtos = yiDuoBaoCardFacade.getAllConfig();
			model.addAttribute("imgPath", ImageConstantFacade.IMAGE_SERVER_ROOT_PATH);
			model.addAttribute("memberConfigList", resDtos);
			return "/card/cardIndex";
		} catch (Exception e) {
			LOGGER.error("web:CardController.java 跳转到购益多宝卡页面异常", e);
			return "redirect:/login/index";
		}
	}

	/**
	 * 获取分类商品显示的数据
	 * 
	 * @param code
	 *            分类编码
	 * @return
	 */
	public List<CategoryGoodVo> getCategoryGoodsVo(String code) {
		List<CategoryGoodVo> cateGoodList = new ArrayList<CategoryGoodVo>();
		try {

			List<CategoryDto> childCategory = categoryFacade.findChildCategory(StringUtils.EMPTY,code, true);
			if (childCategory == null) {
				return cateGoodList;
			}
			for (CategoryDto curChild : childCategory) {
				CategoryGoodVo goodVo = new CategoryGoodVo();
				goodVo.setChildCategory(curChild);
				/** 三级分类 */
				goodVo.setThirdCategory(curChild.getChildCategory());
				cateGoodList.add(goodVo);
			}

		} catch (Exception e) {
			LOGGER.error("分类列表页出错" + e.getMessage());
		}
		return cateGoodList;

	}

}
