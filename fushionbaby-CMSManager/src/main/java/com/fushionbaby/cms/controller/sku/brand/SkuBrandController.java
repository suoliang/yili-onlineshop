/**
 * 
 */
package com.fushionbaby.cms.controller.sku.brand;

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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuBrand;
import com.aladingshop.sku.cms.model.SkuCategoryBrandRelation;
import com.aladingshop.sku.cms.service.SkuBrandService;
import com.aladingshop.sku.cms.service.SkuCategoryBrandRelationService;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.SkuBrandDto;
import com.fushionbaby.cms.util.BatchUploadFiles;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.ImagePathUtil;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.cms.util.excel.ImportExcel;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author mengshaobo
 * 
 */
@Controller
@RequestMapping("/skuBrand")
public class SkuBrandController extends BaseController {
	@Autowired
	private SkuBrandService<SkuBrand> skuBrandService;
	@Autowired
	private SkuCategoryBrandRelationService<SkuCategoryBrandRelation> skuCategoryBrandRelationService;
	
	@Autowired
	private SkuInfoService skuService; 
	private static final Log LOGGER = LogFactory.getLog(SkuBrand.class);
	private static final String FTP_PATH = "/userfiles/images/brand";
	private static final String PRE_ = "models/sku/skuBrand/";

	private Map<String, String> setParamsBySkuBrandDto(SkuBrandDto skuBrandDto) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("brandCode", skuBrandDto.getBrandCode());
		params.put("brandName", skuBrandDto.getBrandName());
		params.put("isShow", skuBrandDto.getIsShow());
		return params;
	}

	/***
	 * 初始化品牌列表
	 * 
	 * @param skuBrandDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("skuBrandList")
	public String findList(SkuBrandDto skuBrandDto, BasePagination page, ModelMap model, HttpSession session) {

		skuBrandList(skuBrandDto, page, model, session);

		return PRE_ + "skuBrandList";
	}

	private void skuBrandList(SkuBrandDto skuBrandDto, BasePagination page, ModelMap model, HttpSession session) {
		if (ObjectUtils.equals(page, null)) {
			page = new BasePagination();
		}
		Map<String, String> params = setParamsBySkuBrandDto(skuBrandDto);
		page.setParams(params);
		page = skuBrandService.getListPage(page);
		model.addAttribute("skuBrandDto", skuBrandDto);
		model.addAttribute("page", page);

		if (session == null)
			return;

		model.addAttribute("ADD_REMARK", session.getAttribute("ADD_BRAND_SUCCESS"));
		model.addAttribute("MODIFY_REMARK", session.getAttribute("MODIFY_BRAND_SUCCESS"));

		session.setAttribute("ADD_BRAND_SUCCESS", "1");
		session.setAttribute("MODIFY_BRAND_SUCCESS", "1");
	}

	/**
	 * 删除品牌
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteById", method = RequestMethod.POST)
	public String deleteById(@RequestParam(value = "brandId") String id) {
		try {
			skuBrandService.deleteById(Long.valueOf(id));
		} catch (DataAccessException e) {
			LOGGER.error("删除品牌数据失败:" + e);
			return Constant.FAILURE;
		}

		return Constant.SUCCESS;
	}

	/***
	 * 产品管理品牌集合
	 * 
	 * @param skuBrandDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("selectSkuBrandList")
	public String selectSkuBrandList(SkuBrandDto skuBrandDto, BasePagination page, ModelMap model) {
		skuBrandList(skuBrandDto, page, model, null);
		return "models/product/selectSkuBrandList";
	}

	/***
	 * 分类关联品牌
	 * 
	 * @param skuBrandDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("selectCheckSkuBrandList")
	public String selectCheckSkuBrandList(SkuBrandDto skuBrandDto, String categoryCode, BasePagination page,
			ModelMap model) {
		skuBrandList(skuBrandDto, page, model, null);
		List<SkuCategoryBrandRelation> list = skuCategoryBrandRelationService.findByCategoryCode(categoryCode);
		model.addAttribute("categoryList", list);
		model.addAttribute("CURRENT_CATEGORY_CODE", categoryCode);
		return "models/sku/skuCategory/skuCategoryBrandRelationAdd";
	}

	/**
	 * 添加品牌
	 * 
	 * @param maBrand
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addBrand", method = RequestMethod.POST)
	public Object addBrand(@RequestParam("jsonStr") String jsonStr, HttpSession session) {
		// 字符串转JSON
		SkuBrand brand = jsonConvertSkuBrand(jsonStr);
		// 获取当前登录用户ID
		brand.setCreateId(Long.valueOf(CMSSessionUtils.getSessionUser(session).getId()));
		try {
			skuBrandService.add(brand);
			session.setAttribute("ADD_BRAND_SUCCESS", "0");
		} catch (DataAccessException e) {
			LOGGER.error("添加品牌数据失败:" + e);
			return Constant.FAILURE;
		}

		return Constant.SUCCESS;
	}

	private SkuBrand jsonConvertSkuBrand(String jsonStr) {

		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(jsonStr);
		SkuBrand brand = new SkuBrand();
		brand.setId(StringUtils.isBlank(json.getAsJsonObject().get("id").getAsString()) ? null : Long.valueOf(json
				.getAsJsonObject().get("id").getAsString()));
		brand.setBrandName(json.getAsJsonObject().get("brandName").getAsString());
		brand.setBrandCode(json.getAsJsonObject().get("brandCode").getAsString());
		brand.setBrandDesc(json.getAsJsonObject().get("brandDesc").getAsString());
		brand.setBrandLogoAppUrl(StringUtils.isBlank(json.getAsJsonObject().get("brandLogoAppUrl").getAsString()) ? null
				: ImagePathUtil.getImageName(json.getAsJsonObject().get("brandLogoAppUrl").getAsString()));
		brand.setBrandLogoWebUrl(StringUtils.isBlank(json.getAsJsonObject().get("brandLogoWebUrl").getAsString()) ? null
				: ImagePathUtil.getImageName(json.getAsJsonObject().get("brandLogoWebUrl").getAsString()));
		brand.setBrandPrefix(json.getAsJsonObject().get("brandPrefix").getAsString());
		brand.setBrandSiteUrl(json.getAsJsonObject().get("brandSiteUrl").getAsString());
		brand.setIsShow(json.getAsJsonObject().get("isShow").getAsString());
		brand.setSortOrder(StringUtils.isBlank(json.getAsJsonObject().get("sortOrder").getAsString()) ? 0 : Integer
				.parseInt(json.getAsJsonObject().get("sortOrder").getAsString()));
		brand.setOldBrandLogoAppUrl(StringUtils.isBlank(json.getAsJsonObject().get("oldBrandLogoAppUrl").getAsString()) ? null
				: ImagePathUtil.getImageName(json.getAsJsonObject().get("oldBrandLogoAppUrl").getAsString()));
		brand.setOldBrandLogoWebUrl(StringUtils.isBlank(json.getAsJsonObject().get("oldBrandLogoWebUrl").getAsString()) ? null
				: ImagePathUtil.getImageName(json.getAsJsonObject().get("oldBrandLogoWebUrl").getAsString()));

		return brand;
	}

	/**
	 * 修改品牌
	 * 
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "modifyBrand", method = RequestMethod.POST)
	public String modifyBrand(@RequestParam("jsonStr") String jsonStr, HttpSession session) {
		// 字符串转JSON
		SkuBrand brand = jsonConvertSkuBrand(jsonStr);
		// 获取当前登录用户ID
		brand.setUpdateId(Long.valueOf(CMSSessionUtils.getSessionUser(session).getId()));

		// 判断用户是否更新图片
		if (Objects.equals(brand.getBrandLogoAppUrl(), brand.getOldBrandLogoAppUrl())) { // APP端图片
			brand.setBrandLogoAppUrl(null);
		} else {
			BatchUploadFiles.delFtpImage(BatchUploadFiles.getFileName(brand.getBrandLogoAppUrl()), FTP_PATH + "/"
					+ BatchUploadFiles.getFileBeforDic(brand.getBrandLogoAppUrl())); // FTP删除
		}

		if (Objects.equals(brand.getBrandLogoWebUrl(), brand.getOldBrandLogoWebUrl())) { // WEB端图片
			brand.setBrandLogoWebUrl(null);
		} else {
			BatchUploadFiles.delFtpImage(BatchUploadFiles.getFileName(brand.getBrandLogoWebUrl()), FTP_PATH + "/"
					+ BatchUploadFiles.getFileBeforDic(brand.getBrandLogoWebUrl())); // FTP删除
		}

		try {
			SkuBrand orgBrand = skuBrandService.findById(brand.getId());
			if(orgBrand == null){
				return Constant.FAILURE;
			}
			
			if(!StringUtils.equals(brand.getBrandCode(),orgBrand.getBrandCode())){
				this.modifyBrandRel(orgBrand.getBrandCode(),brand.getBrandCode());
			}
			
			skuBrandService.update(brand);
			
			session.setAttribute("MODIFY_BRAND_SUCCESS", "0");
		} catch (DataAccessException e) {
			LOGGER.error("修改品牌数据失败:" + e);
			return Constant.FAILURE;
		}

		return Constant.SUCCESS;
	}
	private void modifyBrandRel(String orgBrandCode,String newBrandCode){
		List<SkuCategoryBrandRelation> cbrList = skuCategoryBrandRelationService.findByBrandCode(orgBrandCode);
		for (SkuCategoryBrandRelation brandRel : cbrList) {
			brandRel.setBrandCode(newBrandCode);
			skuCategoryBrandRelationService.update(brandRel);
		}
		List<Sku> skuList = skuService.queryByBrandCode(orgBrandCode);
		for (Sku sku : skuList) {
			sku.setBrandCode(newBrandCode);
			skuService.update(sku);
		}
	}
	

	/**
	 * 初始化添加品牌页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("addBrandPage")
	public String addBrandPageInit() {

		return PRE_ + "skuBrandAdd";
	}

	/**
	 * 初始化修改品牌页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("modifyBrandPage")
	public String modifyBrandPageInit(@RequestParam("bid") String brandId, ModelMap model,
			RedirectAttributes redirectAttributes) {
		SkuBrand brand = skuBrandService.findById(Long.valueOf(brandId));
		if (brand == null) {
			addMessage(redirectAttributes, "商品品牌出错！");
			return "redirect:" + Global.getAdminPath() + "/skuBrand/skuBrandList";
		}
		if (StringUtils.isNotBlank(brand.getBrandLogoAppUrl())) {
			brand.setBrandLogoAppUrl(Global.getImagePath() + brand.getBrandLogoAppUrl());
		}
		if (StringUtils.isNotBlank(brand.getBrandLogoWebUrl())) {
			brand.setBrandLogoWebUrl(Global.getImagePath() + brand.getBrandLogoWebUrl());
		}
		model.addAttribute("brand", brand);

		return PRE_ + "skuBrandModify";
	}

	/**
	 * 前端校验品牌code是否存在
	 * 
	 * @param state
	 * @param memberId
	 * @param orderCode
	 */
	@ResponseBody
	@RequestMapping(value = "existBrandCode", method = RequestMethod.GET)
	public Object existBrandCode(@RequestParam("brandCode") String brandCode,
			@RequestParam("oldBrandCode") String oldBrandCode) {
		SkuBrand brand = skuBrandService.findByBrandCode(brandCode);
		if (brand == null || Objects.equals(brandCode, oldBrandCode)) {
			return true;
		}
		return false;
	}

	@RequestMapping("batchSkuBrand")
	public String batchSkuBrand(Model model) {
		model.addAttribute("title", "商品品牌导入");
		model.addAttribute("action", "skuBrand/importSkuBrandExcel");
		return "models/sku/import/import";
	}

	@RequestMapping("importSkuBrandExcel")
	public String importSkuPriceExcel(MultipartFile excelFile, ModelMap model, HttpSession session) {
		model.addAttribute("info", "导入成功");
		AuthUser user = CMSSessionUtils.getSessionUser(session);
		int count = 0;
		int rowNum = 0;
		try {
			ImportExcel ei = new ImportExcel(excelFile, 0, 0);
			for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {

				Row row = ei.getRow(i);
				String brandCode = ObjectUtils.toString(ei.getCellValue(row, 0));
				if (StringUtils.isBlank(brandCode)) {
					continue;
				}
				rowNum++;
				SkuBrand brand = this.skuBrandService.findByBrandCode(brandCode);
				if (brand == null) {
					brand = new SkuBrand();
				}
				String skuBrandName = ObjectUtils.toString(ei.getCellValue(row, 1));
				String preCode = ObjectUtils.toString(ei.getCellValue(row, 2));
				String url = ObjectUtils.toString(ei.getCellValue(row, 3));
				brand.setBrandCode(brandCode);
				brand.setBrandDesc(null);
				brand.setBrandLogoAppUrl(url);
				brand.setBrandLogoWebUrl(url);
				brand.setBrandName(skuBrandName);
				brand.setBrandPrefix(preCode);
				brand.setBrandSiteUrl(url);
				brand.setCreateId(user.getId());
				brand.setCreateTime(new Date());
				brand.setIsShow(CommonConstant.YES);
				brand.setOldBrandLogoAppUrl(url);
				brand.setOldBrandLogoWebUrl(url);
				brand.setSortOrder(null);
				brand.setUpdateId(user.getId());
				brand.setUpdateTime(new Date());
				if (brand.getId() != null) {
					this.skuBrandService.update(brand);
				} else {
					this.skuBrandService.add(brand);
				}
				count++;
			}
		} catch (Exception e) {
			model.addAttribute("info", "导入失败");
			e.printStackTrace();
		}
		model.addAttribute("rowNum", rowNum);// Excel 中总记录数
		model.addAttribute("count", count);// 添加的记录数量
		model.addAttribute("title", "商品品牌批量导入");
		return "models/sku/import/importResult";
	}

}
