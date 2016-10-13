package com.fushionbaby.cms.controller.sku.category;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aladingshop.sku.cms.model.SkuBrand;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.model.SkuCategoryBrandRelation;
import com.aladingshop.sku.cms.model.SkuLabel;
import com.aladingshop.sku.cms.model.SkuLabelCategoryRelation;
import com.aladingshop.sku.cms.service.SkuBrandService;
import com.aladingshop.sku.cms.service.SkuCategoryBrandRelationService;
import com.aladingshop.sku.cms.service.SkuCategoryService;
import com.aladingshop.sku.cms.service.SkuLabelCategoryRelationService;
import com.aladingshop.sku.cms.service.SkuLabelService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.dto.SkuCategoryDto;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.ImagePathUtil;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.cms.util.excel.ImportExcel;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author mengshaobo
 * 
 */
@Controller
@RequestMapping("/skuCategory")
public class SkuCategoryController {
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	@Autowired
	private SkuCategoryBrandRelationService<SkuCategoryBrandRelation> skuCategoryBrandRelationService;
	@Autowired
	private SkuLabelService<SkuLabel> skuLabelService;
	@Autowired
	private SkuLabelCategoryRelationService<SkuLabelCategoryRelation> labelCategoryConfigService;
	@Autowired
	private SkuBrandService<SkuBrand> skuBrandService;
	private static final Log LOGGER = LogFactory.getLog(SkuCategoryController.class);
	
	private static final String PRE_="models/sku/skuCategory/";
	
	
	private Map<String, String> setParamsBySkuCategoryDto(SkuCategoryDto skuCategoryDto){
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", skuCategoryDto.getCode());
		params.put("name", skuCategoryDto.getName());
		params.put("isShow", skuCategoryDto.getIsShow());
		params.put("categoryLevel", skuCategoryDto.getCategoryLevel());
		params.put("grandcategoryCode", skuCategoryDto.getGrandcategoryCode());
		return params;
	}
	private Map<String, String> setParamsBySkuCategoryDto2(SkuCategoryDto skuCategoryDto){
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", skuCategoryDto.getCode());
		params.put("name", skuCategoryDto.getName());
		params.put("isShow", skuCategoryDto.getIsShow());
		params.put("categoryLevel", skuCategoryDto.getCategoryLevel());
		params.put("grandcategoryCode", skuCategoryDto.getGrandcategoryCode());
		String isLabelRelationed=skuCategoryDto.getIsLabelRelation();
		params.put("isLabelRelationed", isLabelRelationed);
		List<SkuLabelCategoryRelation> labelCategoryRelationList=labelCategoryConfigService.findListByLabelCode(skuCategoryDto.getLabelCode());
		String relationedCategoryCodes="";
		if(labelCategoryRelationList.size()>0){
			for(SkuLabelCategoryRelation tmplabelCategoryRelation : labelCategoryRelationList){
					relationedCategoryCodes=relationedCategoryCodes+","+tmplabelCategoryRelation.getCategoryCode();
			}
		}
		if(relationedCategoryCodes.length()>0){
			relationedCategoryCodes=relationedCategoryCodes.substring(1);
		}
		
		params.put("relationedCategoryCodes", relationedCategoryCodes);
		return params;
	}
	/***
	 * 商品分类列表
	 * @param skuCategoryDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("findSkuCategoryList")
	public String skuCategoryList(SkuCategoryDto skuCategoryDto,
			BasePagination page,
			ModelMap model){
		try {
			if (ObjectUtils.equals(page, null)) {
				page = new BasePagination();
			}
			Map<String, String> params = setParamsBySkuCategoryDto(skuCategoryDto);
			page.setParams(params);
			page = skuCategoryService.getListPage(page);
			List<SkuCategoryDto> skuCategoryDtoList=new ArrayList<SkuCategoryDto>();
			@SuppressWarnings("unchecked")
			List<SkuCategory> skuCategoryList=(List<SkuCategory>)page.getResult();
			for(SkuCategory skuCategory: skuCategoryList){
				SkuCategoryDto skuCategoryDtotmp=new SkuCategoryDto();
				skuCategoryDtotmp.setCategoryLevel(skuCategory.getCategoryLevel().toString());
				skuCategoryDtotmp.setCode(skuCategory.getCode());
				skuCategoryDtotmp.setGrandcategoryCode(skuCategory.getGrandcategoryCode());
				SkuCategory skuCategorytmp= skuCategoryService.findByCode(skuCategory.getGrandcategoryCode(),skuCategory.getStoreCode());
				String grandCategoryName="";
				if(!ObjectUtils.equals(null, skuCategorytmp)){
					grandCategoryName=skuCategorytmp.getName();
				}
				skuCategoryDtotmp.setGrandcategoryName(grandCategoryName);
				skuCategoryDtotmp.setId(skuCategory.getId().toString());
				skuCategoryDtotmp.setIsShow(skuCategory.getIsShow());
				skuCategoryDtotmp.setName(skuCategory.getName());
				skuCategoryDtotmp.setCategoryLogoUrl(skuCategory.getCategoryLogoUrl());
				skuCategoryDtotmp.setCreateId(skuCategory.getCreateId());
				skuCategoryDtotmp.setCreateTime(skuCategory.getCreateTime());
				skuCategoryDtotmp.setEnglishName(skuCategory.getEnglishName());
				skuCategoryDtotmp.setKeywords(skuCategory.getKeywords());
				skuCategoryDtotmp.setLinkUrl(skuCategory.getLinkUrl());
				skuCategoryDtotmp.setOldCategoryLogoUrl(skuCategory.getOldCategoryLogoUrl());
				skuCategoryDtotmp.setShowOrder(skuCategory.getShowOrder());
				skuCategoryDtotmp.setUpdateId(skuCategory.getUpdateId());
				skuCategoryDtotmp.setUpdateTime(skuCategory.getUpdateTime());
				skuCategoryDtotmp.setVersion(skuCategory.getVersion());
				skuCategoryDtoList.add(skuCategoryDtotmp);
			}
			model.addAttribute("skuCategoryDtoList", skuCategoryDtoList);
			model.addAttribute("page", page);
			model.addAttribute("imagePath", Global.getImagePath());
			model.addAttribute("skuCategoryDto", skuCategoryDto);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("CMS后台SkuCategoryController查询所有出错"+e);
		}
		return PRE_+"skuCategoryList";
	}
	
	/***
	 * 标签关联分类集合
	 * @param skuCategoryDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("selectCheckCategoryList")
	public String selectCheckCategoryList(SkuCategoryDto skuCategoryDto,
			String labelCode,
			BasePagination page,
			ModelMap model){
		try {
			if (ObjectUtils.equals(page, null)) {
				page = new BasePagination();
			}
			Map<String, String> params = setParamsBySkuCategoryDto2(skuCategoryDto);
			page.setParams(params);
			page = skuCategoryService.getListPage(page);
			model.addAttribute("page", page);
			model.addAttribute("imagePath", Global.getImagePath());
			model.addAttribute("skuCategoryDto", skuCategoryDto);
			model.addAttribute("CURRENT_LABEL_CODE", labelCode);
			SkuLabel skuLabel = skuLabelService.getByCode(labelCode);
			if (ObjectUtils.notEqual(skuLabel, null)) {
				model.addAttribute("maxNumber", skuLabel.getMaxCategoryNum());
			}
			List<SkuLabelCategoryRelation> list = labelCategoryConfigService.findListByLabelCode(labelCode);
			if (!CollectionUtils.isEmpty(list)) {
				model.addAttribute("labelCategoryList", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("CMS后台SkuCategoryController查询所有出错"+e);
		}
		return PRE_+"skuLabelAddCategoryList";
	}
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping("gotoSkuCategoryAdd")
	public String gotoSkuCategoryAdd(){
		return PRE_+"skuCategoryAdd";
	}
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping("gotoSkuCategoryAddByCategoryId")
	public String gotoSkuCategoryAddByCategoryId(
			@RequestParam("categoryId")Long categoryId,
			ModelMap model){
		SkuCategory skuCategory = skuCategoryService.findById(categoryId);
		model.addAttribute("skuCategory", skuCategory);
		return PRE_+"skuCategoryAddByCategoryId";
	}
	/**
	 * 检查分类编码是否存在
	 * @param categoryCode
	 * @param oldCategoryCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("isExistCategoryCode")
	public boolean isExistCategoryCode(@RequestParam("categoryCode") String categoryCode,
			@RequestParam("oldCategoryCode") String oldCategoryCode){
		SkuCategory skuCategory = skuCategoryService.findByCode(categoryCode,null);
		if (ObjectUtils.equals(null,skuCategory.getId()) ||skuCategory == null || Objects.equals(categoryCode, oldCategoryCode)) {
			return true;
		}
		return false;
	}
	/**
	 * 检查父分类编码是否存在
	 * @param categoryCode
	 * @param oldCategoryCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("isExistParentCategoryCode")
	public boolean isExistParentCategoryCode(@RequestParam("parentCategoryCode") String parentCategoryCode){
		if (StringUtils.equals(parentCategoryCode, "0")) {
			return true;
		}
		SkuCategory skuCategory = skuCategoryService.findByCode(parentCategoryCode,null);
		if (ObjectUtils.equals(null, skuCategory.getId()) || skuCategory == null) {
			return false;
		}
		return true;
	}
	/***
	 * 根据父分类查询分类级别
	 * @param parentCategoryCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCategoryLevelByParentCode")
	public Object getCategoryLevelByParentCode(
			@RequestParam("parentCategoryCode") String parentCategoryCode){
		try {
			if (StringUtils.equals(parentCategoryCode, "0")) {
				return Jsonp_data.success(1);
			}
			SkuCategory skuCategory = skuCategoryService.findByCode(parentCategoryCode,null);
			if (!Objects.equals(skuCategory, null)) {
				return Jsonp_data.success(skuCategory.getCategoryLevel() + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取分类级别失败:" + e);
			return Jsonp.error();
		}
		return Jsonp.error();
	}
	
	@ResponseBody
	@RequestMapping(value = "addSkuCategory", method = RequestMethod.POST)
	public Object addSkuCategory(String jsonStr,
			HttpSession session) {
		try {
			AuthUser authUser = CMSSessionUtils.getSessionUser(session);
			SkuCategory skuCategory = jsonConvertSkuCategory(jsonStr);
			skuCategory.setCreateId(authUser.getId());
			skuCategoryService.add(skuCategory);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("添加分类信息失败:" + e);
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}
	
	@RequestMapping("gotoSkuCategoryUpdate")
	public String gotoSkuCategoryUpdate(@RequestParam("categoryId") Long categoryId,
			ModelMap model) {
		try {
			SkuCategory skuCategory = skuCategoryService.findById(categoryId);
			// 拼接图片路径
			skuCategory.setCategoryLogoUrl(Global.getImagePath() + skuCategory.getCategoryLogoUrl());
			model.addAttribute("skuCategory", skuCategory);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("跳转到分类修改页面失败:" + e);
		}
		return PRE_+"skuCategoryUpdate";
	}
	
	@ResponseBody
	@RequestMapping(value = "updateSkuCategory", method = RequestMethod.POST)
	public Object updateSkuCategory(String jsonStr,
			HttpSession session) {
		try {
			AuthUser authUser = CMSSessionUtils.getSessionUser(session);
			SkuCategory skuCategory = jsonConvertSkuCategory(jsonStr);
			skuCategory.setUpdateId(authUser.getId());
			// 判断当前用户是否更换新图片
			if (Objects.equals(skuCategory.getCategoryLogoUrl(),
					skuCategory.getOldCategoryLogoUrl())) {
				skuCategory.setCategoryLogoUrl(skuCategory.getCategoryLogoUrl());
			} else {
				// TODO 删除操作暂未实现
				// FileUtil.delFile(brand.getBrandLogoAppUrl());
			}
			skuCategoryService.update(skuCategory);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("修改分类信息失败:" + e);
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}
	
	private SkuCategory jsonConvertSkuCategory(String jsonStr){
		
		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(jsonStr);
		String id = json.getAsJsonObject().get("id").getAsString();
		String name = json.getAsJsonObject().get("name").getAsString();
		String code = json.getAsJsonObject().get("code").getAsString();
		String showOrder = json.getAsJsonObject().get("showOrder").getAsString();
		String grandcategoryCode = json.getAsJsonObject().get("grandcategoryCode").getAsString();
		String categoryLevel = json.getAsJsonObject().get("categoryLevel").getAsString();
		String englishName = json.getAsJsonObject().get("englishName").getAsString();
		String keywords = json.getAsJsonObject().get("keywords").getAsString();
		String isShow = json.getAsJsonObject().get("isShow").getAsString();
		String categoryLogoUrl = json.getAsJsonObject().get("categoryLogoUrl").getAsString();
		String oldCategoryLogoUrl = json.getAsJsonObject().get("oldCategoryLogoUrl").getAsString();
		SkuCategory skuCategory = new SkuCategory();
		skuCategory.setId(StringUtils.isBlank(id)?null:Long.valueOf(id));
		skuCategory.setName(name);
		skuCategory.setCode(code);
		skuCategory.setShowOrder(StringUtils.isBlank(showOrder)?null:Integer.valueOf(showOrder));
		skuCategory.setGrandcategoryCode(grandcategoryCode);
		skuCategory.setCategoryLevel(Integer.valueOf(categoryLevel));
		skuCategory.setEnglishName(StringUtils.isBlank(englishName)?null:englishName);
		skuCategory.setKeywords(StringUtils.isBlank(keywords)?null:keywords);
		skuCategory.setIsShow(isShow);
		skuCategory.setCategoryLogoUrl(StringUtils.isBlank(categoryLogoUrl)?null:
				 ImagePathUtil.getImageName(categoryLogoUrl));
		skuCategory.setOldCategoryLogoUrl(StringUtils.isBlank(oldCategoryLogoUrl)?null:
				 ImagePathUtil.getImageName(oldCategoryLogoUrl));
		return skuCategory;
	}
	
	/**
	 * 根据分类查出相关品牌列表
	 */
	@RequestMapping("brandListByCategory")
	public String getSkuListByLabel(
			@RequestParam("categoryCode") String categoryCode, BasePagination page,
			ModelMap model) {
		// 根据categoryCode获取全部
		List<SkuCategoryBrandRelation> list = skuCategoryBrandRelationService.findByCategoryCode(categoryCode);
		model.addAttribute("CURRENT_CATEGORY_CODE", categoryCode);
		if (CollectionUtils.isEmpty(list)) {
			return PRE_+"skuCategoryBrandDetail";
		}
		StringBuffer buffer = new StringBuffer();
		for (SkuCategoryBrandRelation scbr : list) {
			buffer.append("," + "'" + scbr.getBrandCode() + "'");
		}
		String result = buffer.toString();
		result = result.substring(1, result.length());
		Map<String, String> map = new HashMap<String, String>();
		map.put("brandCodes", result);
		page.setParams(map);
		page = skuBrandService.getListPage(page);
		
		model.addAttribute("page", page);
		return PRE_+"skuCategoryBrandDetail";
	}
	@ResponseBody
	@RequestMapping("addNotRelationCategoryBrand")
	public String CategoryBrandRelation(String categoryCode,
			String brandCode){
		try {
			SkuCategoryBrandRelation rel = new SkuCategoryBrandRelation();
			rel.setBrandCode(brandCode);
			rel.setCategoryCode(categoryCode);
			rel.setCreateTime(new Date());
			skuCategoryBrandRelationService.add(rel);
		} catch (Exception e) {
			LOGGER.error("添加关联品牌失败:" + e);
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}
	
	@ResponseBody
	@RequestMapping("deleteCategoryBrandRelation")
	public Object deleteCategoryBrandRelation(String categoryCode,
			String brandCode){
		try {
			skuCategoryBrandRelationService.deleteByCategoryAndBrandCode(
					categoryCode, brandCode);
		} catch (Exception e) {
			LOGGER.error("取消关联品牌失败:" + e);
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}
	
	@RequestMapping("batchSkuCategory")
	public String batchSkuCategory(Model model){
		model.addAttribute("title","商品分类导入" );
		model.addAttribute("action", "skuCategory/importSkuCategoryExcel");
		return "models/sku/import/import";
	}
	
	
	@RequestMapping("importSkuCategoryExcel")
	public String importSkuPriceExcel(MultipartFile excelFile, ModelMap model, HttpSession session) {
		model.addAttribute("info", "导入成功");
		AuthUser user = CMSSessionUtils.getSessionUser(session);
		int count = 0;
		int rowNum = 0;
		try {
			ImportExcel ei = new ImportExcel(excelFile, 0, 0);
			for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {

				Row row = ei.getRow(i);
				String skuCategoryCode = ObjectUtils.toString(ei.getCellValue(row, 0));
				if (StringUtils.isBlank(skuCategoryCode)) {
					continue;
				}
				rowNum++;
				SkuCategory skuCategory=this.skuCategoryService.findByCode(skuCategoryCode,null);
				if(skuCategory==null)
				{
					skuCategory=new SkuCategory();
				}
				
				String skuCategoryName = ObjectUtils.toString(ei.getCellValue(row, 1));
				String preSkuCategoryCode = ObjectUtils.toString(ei.getCellValue(row, 2));
				String categoryLevel = ObjectUtils.toString(ei.getCellValue(row, 3));
				String categoryLogoUrl = ObjectUtils.toString(ei.getCellValue(row, 4));
				skuCategory.setCode(skuCategoryCode);
				skuCategory.setCategoryLevel(Integer.valueOf(categoryLevel));
				skuCategory.setCreateId(user.getId());
				skuCategory.setCreateTime(new Date());
				skuCategory.setIsShow(CommonConstant.YES);
				skuCategory.setGrandcategoryCode(preSkuCategoryCode);
				skuCategory.setName(skuCategoryName);
				skuCategory.setCategoryLogoUrl(categoryLogoUrl);
				skuCategory.setUpdateId(user.getId());
				skuCategory.setUpdateTime(new Date());
				skuCategory.setVersion(new Date());
				if(skuCategory.getId()!=null){
					this.skuCategoryService.update(skuCategory);
				}else{
					this.skuCategoryService.add(skuCategory);
				}
				count++;
			}
		} catch (Exception e) {
			model.addAttribute("info", "导入失败");
			e.printStackTrace();
		}
		model.addAttribute("rowNum", rowNum);// Excel 中总记录数
		model.addAttribute("count", count);// 添加的记录数量
		model.addAttribute("title", "商品分类批量导入");
		return "models/sku/import/importResult";
	}
	
}
