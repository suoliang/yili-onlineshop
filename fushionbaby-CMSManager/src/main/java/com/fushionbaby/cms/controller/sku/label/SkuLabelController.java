package com.fushionbaby.cms.controller.sku.label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.model.SkuExtraInfo;
import com.aladingshop.sku.cms.model.SkuLabel;
import com.aladingshop.sku.cms.model.SkuLabelRelation;
import com.aladingshop.sku.cms.service.SkuCategoryService;
import com.aladingshop.sku.cms.service.SkuExtraInfoService;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.sku.cms.service.SkuLabelRelationService;
import com.aladingshop.sku.cms.service.SkuLabelService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.SkuDto;
import com.fushionbaby.cms.dto.SkuExtraInfoDto;
import com.fushionbaby.cms.dto.SkuLabelRelationDto;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.cms.util.excel.ImportExcel;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.dto.sku.SkuLabelDto;
import com.fushionbaby.common.util.BasePagination;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 商品标签control
 * 
 * @author sun tao 2015年7月3日
 */
@Controller
@RequestMapping("/skuLabel")
public class SkuLabelController extends BaseController {
	@Autowired
	private SkuLabelRelationService<SkuLabelRelation> relationService;

	@Autowired
	private SkuLabelService<SkuLabel> skuLabelService;

	@Autowired
	private SkuInfoService skuInfoService;

	@Autowired
	private SkuCategoryService<SkuCategory> categoryService;
	
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo>  skuExtraInfoService;

	private static final Log log = LogFactory.getLog(SkuLabelController.class);

	/**
	 * 初始化商品标签列表
	 * 
	 * @param name
	 * @param code
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("skuLabelList")
	public String getSkuLabelRelationList(@ModelAttribute("findForm") SkuLabelDto labelDto, BasePagination page,
			ModelMap model, HttpSession session) {
		skuLabelList(labelDto, page, model, session);

		return "models/sku/skuLabel/skuLabelList";
	}

	private void skuLabelList(SkuLabelDto labelDto, BasePagination page, ModelMap model, HttpSession session) {
		if (ObjectUtils.equals(page, null)) {
			page = new BasePagination();
		}
		Map<String, String> params = setParamsBySkuLabelDto(labelDto);
		page.setParams(params);
		page = skuLabelService.getListPage(page);
		model.addAttribute("skuLabelDto", labelDto);
		model.addAttribute("page", page);

		if (session == null)
			return;

		model.addAttribute("ADD_REMARK", session.getAttribute("ADD_SKULABEL_SUCCESS"));
		model.addAttribute("MODIFY_REMARK", session.getAttribute("MODIFY_SKULABEL_SUCCESS"));

		session.setAttribute("ADD_SKULABEL_SUCCESS", "");
		session.setAttribute("MODIFY_SKULABEL_SUCCESS", "");
	}

	private Map<String, String> setParamsBySkuLabelDto(SkuLabelDto labelDto) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", labelDto.getName());
		params.put("code", labelDto.getCode());
		params.put("type",labelDto.getType());
		params.put("storeCode", CommonConstant.STORE_CODE_SHOP);
		params.put("disabled", labelDto.getDisabled());
		return params;
	}

	/**
	 * 删除标签
	 * 
	 * @param labelCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteSkuLabel", method = RequestMethod.POST)
	public Object deleteSkuLabel(@RequestParam("labelCode") String labelCode) {

		try {
			// 删除关系表相关数据
			relationService.deleteByLabelCode(labelCode,null);
			// 删除标签
			skuLabelService.deleteByCode(labelCode);
		} catch (Exception e) {
			log.error("删除关系数据失败：" + e);
			return Constant.FAILURE;
		}

		return Constant.SUCCESS;
	}

	/**
	 * 修改商品标签页面
	 * 
	 * @param name
	 * @param code
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("editSkuLabel")
	public String editSkuLabel(@RequestParam("labelCode") String labelCode, ModelMap model) {
		SkuLabel edSku = skuLabelService.getByCode(labelCode);

		model.addAttribute("editLabel", edSku);
		return "models/sku/skuLabel/skuLabelModify";
	}

	/**
	 * 修改商品标签信息
	 * 
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "modifySkuLabel", method = RequestMethod.POST)
	public String modifyBrand(@RequestParam("jsonStr") String jsonStr, HttpSession session) {
		// 解析JSON
		SkuLabel skuLabel = jsonConvertSkuLabel(jsonStr);
		// 获取当前登录用户id
		skuLabel.setUpdateId(Long.valueOf(CMSSessionUtils.getSessionUser(session).getId()));

		try {
			skuLabelService.update(skuLabel);
			session.setAttribute("MODIFY_SKULABEL_SUCCESS", "0");
		} catch (DataAccessException e) {
			log.error("修改商品标签信息失败:" + e);
			return Constant.FAILURE;
		}

		return Constant.SUCCESS;
	}

	/**
	 * 新增商品标签页面
	 * 
	 * @param name
	 * @param code
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("addSkuLabel")
	public String editSkuLabel() {
		return "models/sku/skuLabel/skuLabelAdd";
	}

	/**
	 * 新增商品标签信息
	 * 
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "addSkuLabel", method = RequestMethod.POST)
	public String addSkuLabel(@RequestParam("jsonStr") String jsonStr, HttpSession session) {
		// 解析JSON
		SkuLabel skuLabel = jsonConvertSkuLabel(jsonStr);
		// 获取当前登录用户id
		skuLabel.setUpdateId(Long.valueOf(CMSSessionUtils.getSessionUser(session).getId()));

		try {
			skuLabelService.add(skuLabel);
			session.setAttribute("ADD_SKULABEL_SUCCESS", "0");
		} catch (DataAccessException e) {
			log.error("修改商品标签信息失败:" + e);
			return Constant.FAILURE;
		}

		return Constant.SUCCESS;
	}

	private SkuLabel jsonConvertSkuLabel(String jsonStr) {
		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(jsonStr);
		SkuLabel skuLabel = new SkuLabel();
		skuLabel.setName(json.getAsJsonObject().get("name").getAsString());
		skuLabel.setDisable(json.getAsJsonObject().get("disable").getAsString());
		skuLabel.setCode(json.getAsJsonObject().get("code").getAsString());
		skuLabel.setType(json.getAsJsonObject().get("type").getAsString());
		String maxCategoryNum = json.getAsJsonObject().get("maxCategoryNum").getAsString();
		skuLabel.setMaxCategoryNum(NumberUtils.toInt(maxCategoryNum, 0) );
		return skuLabel;
	}

	/**
	 * 前端校验商品标签code是否重复
	 * 
	 * @param state
	 * @param memberId
	 * @param orderCode
	 */
	@ResponseBody
	@RequestMapping(value = "existSkuLabelCode", method = RequestMethod.GET)
	public Object existSkuLabelCode(@RequestParam("code") String code) {
		SkuLabel skuLabel = skuLabelService.getByCode(code);
		if (skuLabel == null) {
			return true;
		}

		return false;
	}

	/**
	 * 根据标签查出相关商品列表
	 * 
	 * @param labelCode
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("skuListByLabel")
	public String getSkuListByLabel(@RequestParam("labelCode") String labelCode,
			SkuDto skuDto,
			BasePagination page, ModelMap model,
			HttpSession session) {
		// 根据labelCode获取全部skuCode
		List<String> skuCodes = relationService.findSkuCodesByLabel(labelCode,null);

		this.skuList(labelCode, skuDto, skuCodes, page, model, session);

		model.addAttribute("CURRENT_LABELD_CODE", labelCode);
		
		SkuLabel skuLabel=skuLabelService.getByCode(labelCode);
		
		model.addAttribute("labelName", skuLabel.getName());

		return "models/sku/skuLabel/skuLabelDetail";
	}

	private void skuList(String labelCode, SkuDto skuDto, List<String> skuCodes, 
			BasePagination page, ModelMap model,
			HttpSession session) {
		if (ObjectUtils.equals(page, null)) {
			page = new BasePagination();
		}
		Map<String, String> params = setParamsBySkuList(skuDto,skuCodes);
		page.setParams(params);
		page = skuInfoService.getPageList(page);
		@SuppressWarnings("unchecked")
		List<Sku> skuList = (List<Sku>) page.getResult();
		List<SkuLabelRelationDto> relDtos = new ArrayList<SkuLabelRelationDto>();
		if (!CollectionUtils.isEmpty(skuList)) {
			for (Sku sku : skuList) {
				SkuLabelRelationDto relDto = new SkuLabelRelationDto();
				relDto.setUnCode(sku.getUniqueCode());
				relDto.setSkuNo(sku.getSkuNo());
				relDto.setSkuName(sku.getName());
				SkuLabelRelation labelRelation = relationService.findBySkuCodeAndLabel(sku.getUniqueCode(), labelCode);
				relDto.setShowOrder(ObjectUtils.toString(labelRelation.getShowOrder(), "0"));
				relDto.setDisabled(labelRelation.getDisabled());
				relDto.setSkuStatus(sku.getStatus());
				relDto.setCreateTime(labelRelation.getCreateTime());
				relDto.setBarCode(sku.getBarCode());
				SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(sku.getUniqueCode());
				if(skuExtraInfo!=null ){
					relDto.setIsMemberSku(skuExtraInfo.getIsMemberSku());
				}
				
				relDtos.add(relDto);
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("skuDto", skuDto);
		model.addAttribute("relDtos", relDtos);
	}

	private String getAllCodes(List<String> skuCodes) {
		if (skuCodes == null || skuCodes.size() == 0) {
			return "null";
		}

		StringBuffer buffer = new StringBuffer();
		for (String scode : skuCodes) {
			buffer.append("," + "'" + scode + "'");
		}

		String result = buffer.toString();

		return result.substring(1, result.length());
	}

	private Map<String, String> setParamsBySkuList(SkuDto skuDto,List<String> skuCodes) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("skuNo", skuDto.getSkuNo());
		params.put("barCode", skuDto.getBarCode());
		params.put("name", skuDto.getSkuName());
		params.put("skuCodes", getAllCodes(skuCodes));
		return params;
	}

	/**
	 * 前端删除关系数据
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteSkuLabelRelation", method = RequestMethod.POST)
	public Object deleteSkuLabelRelation(@RequestParam("labelCode") String labelCode,
			@RequestParam("skuCode") String skuCode) {
		SkuLabelRelation labelRelation = relationService.findBySkuCodeAndLabel(skuCode, labelCode);
		if(labelRelation==null){
			return Constant.FAILURE;
		}
		try {
			// 删除关系表相关数据
			relationService.deleteBySkuCodeAndLabelCode(labelRelation);
		} catch (Exception e) {
			log.error("删除关系数据失败：" + e);
			return Constant.FAILURE;
		}

		return Constant.SUCCESS;
	}

	/**
	 * 添加标签商品页
	 * 
	 * @param labelCode
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("addNoRelationSku")
	public String addNoRelationSku(SkuDto skuDto, BasePagination page, ModelMap model) {
		// 根据labelCode获取全部skuCode
		List<String> skuCodes = relationService.findSkuCodesByLabel(skuDto.getLabelCode(),null);
		
		
		/** 查询出所有二级分类 */
		model.addAttribute("categorys", categoryService.getCategoryByLevel(2,null));

		skuNoRelationList(skuDto, skuCodes, page, model);

		model.addAttribute("CURRENT_LABELD_CODE", skuDto.getLabelCode());

		return "models/sku/skuLabel/skuLabelRelationAdd";
	}

	private void skuNoRelationList(SkuDto skuDto, List<String> skuCodes, BasePagination page, ModelMap model) {
		if (ObjectUtils.equals(page, null)) {
			page = new BasePagination();
		}
		Map<String, String> params = setParamsByNoRelationSkuDto(skuCodes, skuDto);
		page.setParams(params);
		//page = skuInfoService.getPageList(page);
		page = skuExtraInfoService.getPageList(page);
		
		@SuppressWarnings("unchecked")
		List<SkuExtraInfo> extraInfos =(List<SkuExtraInfo>) page.getResult();
		
		List<SkuDto> skuDtoList = new ArrayList<SkuDto>();
		
		for (SkuExtraInfo extraInfo : extraInfos) {
			SkuDto sku = new SkuDto();
			SkuExtraInfoDto skuExtraInfoDto = new SkuExtraInfoDto();
			skuExtraInfoDto.setIsMemberSku(extraInfo.getIsMemberSku());
			Sku skuEntry = skuInfoService.queryByUniqueCode(extraInfo.getSkuCode());
			sku.setSkuNo(skuEntry.getSkuNo());
			sku.setUniqueCode(skuEntry.getUniqueCode());
			sku.setSkuName(skuEntry.getName());
			sku.setProductCode(skuEntry.getProductCode());
			sku.setStatus(skuEntry.getStatus());
			sku.setCreateTime(skuEntry.getCreateTime());
			sku.setBarCode(skuEntry.getBarCode());
			sku.setSkuExtraInfo(skuExtraInfoDto);
			skuDtoList.add(sku);
		}
		page.setResult(skuDtoList);		
		
		
		model.addAttribute("SkuDto", skuDto);
		model.addAttribute("page", page);
	}

	private Map<String, String> setParamsByNoRelationSkuDto(List<String> skuCodes, SkuDto skuDto) {
		Map<String, String> params = new HashMap<String, String>();
		String reStr = getAllCodes(skuCodes);
		params.put("isMemberSku", skuDto.getIsMemberSku());
		params.put("noSkuCodes", Objects.equals(reStr, "null") ? null : reStr);
		params.put("skuNo", skuDto.getSkuNo());
		params.put("barCode", skuDto.getBarCode());
		params.put("name", skuDto.getSkuName());
		params.put("status", skuDto.getStatus());
		params.put("productCode", skuDto.getProductCode());
		params.put("storeCode", CommonConstant.STORE_CODE_SHOP);
		params.put("grandCategoryCode", skuDto.getGrandCategoryCode());
		params.put("storeCode", skuDto.getStoreCode());

		return params;
	}
	

	/**
	 * 前端添加关系数据
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addSkuByLabel", method = RequestMethod.POST)
	public Object addSkuByLabel(@RequestParam("labelCode") String labelCode, @RequestParam("skuCodes") String skuCodes,
			ModelMap model) {
		List<String> all = Arrays.asList(skuCodes.split(","));
		for (String currentSkuCode : all) {
			SkuLabelRelation relation = new SkuLabelRelation(labelCode, currentSkuCode, "y",null);
			try {
				relationService.add(relation);
			} catch (DataAccessException e) {
				log.error("添加关系数据失败");
				return Constant.FAILURE;
			}
		}

		return Constant.SUCCESS;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/skuLabelRelationEditModel/{labelCode}/{skuCode}")
	public String skuLabelRelationEditModel(@PathVariable String labelCode, @PathVariable String skuCode,
			ModelMap model, RedirectAttributes redirectAttributes) {

		SkuLabelRelation relation = relationService.findBySkuCodeAndLabel(skuCode, labelCode);

		if (relation == null) {
			addMessage(redirectAttributes, "数据异常");
			return "redirect:" + Global.getAdminPath() + "/sku/skuListByLabel?labelCode=" + labelCode;
		}
		model.addAttribute("relation", relation);

		return "models/sku/skuLabel/skuLabelRelationEdit";
	}

	/**
	 * 编辑
	 * 
	 * @param rel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editLabelRelation")
	public String editLabelRelation(SkuLabelRelation rel) {

		SkuLabelRelation findRel = relationService.findById(rel.getId());
		if (findRel == null) {
			return Constant.FAILURE;
		}
		relationService.update(rel);
		return Constant.SUCCESS;
	}

	/***
	 * 批量导入标签商品
	 * 
	 * @param skuBrandDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("batchSkuLabel")
	public String batchSkuLabel() {
		return "models/sku/skuLabel/skuLabelExcel";
	}

	@RequestMapping("importSkuLabelExcel")
	public String importSkuPriceExcel(MultipartFile excelFile, ModelMap model, HttpSession session) {
		model.addAttribute("info", "导入成功");
		List<String> notExitLabelSkuNos = new ArrayList<String>();
		List<String> relationExitLabelSkuNos = new ArrayList<String>();
		int count = 0;
		int rowNum = 0;
		try {
			ImportExcel ei = new ImportExcel(excelFile, 0, 0);
			for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {

				Row row = ei.getRow(i);

				String labelCode = ObjectUtils.toString(ei.getCellValue(row, 0));
				String skuNo = ObjectUtils.toString(ei.getCellValue(row, 1));
				String showOrder = ObjectUtils.toString(ei.getCellValue(row, 2));
				if ("".equals(showOrder)) {
					showOrder = "0";
				}
				if (StringUtils.isBlank(labelCode) && StringUtils.isBlank(skuNo)) {
					continue;
				}
				rowNum++;
				SkuLabel skuLabel = skuLabelService.getByCode(labelCode);
				Sku sku = skuInfoService.queryBySkuNo(skuNo,null);
				if (ObjectUtils.equals(null, skuLabel) || ObjectUtils.equals(null, sku) || !isInteger(showOrder)) {
					log.info("该标签或商品不存在或者显示顺序不为整数:" + skuLabel + " , " + skuNo + " , " + showOrder);
					notExitLabelSkuNos.add(labelCode + " , " + skuNo + " , " + showOrder);
					continue;
				}
				SkuLabelRelation skuLabelRelation = relationService.findBySkuCodeAndLabel(sku.getUniqueCode(),
						labelCode);
				if (!ObjectUtils.equals(null, skuLabelRelation)) {
					log.info("该标签和商品关联已存在:" + skuLabel + " , " + skuNo);
					relationExitLabelSkuNos.add(labelCode + " , " + skuNo);
					continue;
				}
				SkuLabelRelation excelSkuLabelRelation = new SkuLabelRelation();
				excelSkuLabelRelation.setLabelCode(labelCode);
				excelSkuLabelRelation.setSkuCode(sku.getUniqueCode());
				excelSkuLabelRelation.setShowOrder(Integer.parseInt(showOrder));
				excelSkuLabelRelation.setDisabled("y");
				relationService.add(excelSkuLabelRelation);
				count++;

			}
		} catch (Exception e) {
			model.addAttribute("info", "导入失败");
			e.printStackTrace();
		}
		model.addAttribute("rowNum", rowNum);// Excel 中总记录数
		model.addAttribute("count", count);// 添加的记录数量
		model.addAttribute("notExitSkuNos", notExitLabelSkuNos);// 不存在的标签或商品
		model.addAttribute("relationExitLabelSkuNos", relationExitLabelSkuNos);// 已关联记录
		return "models/sku/skuLabel/batch-success";
	}

	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
