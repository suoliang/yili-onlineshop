package com.fushionbaby.cms.controller.sku.promotions;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.model.SkuPromotionsInfo;
import com.aladingshop.sku.cms.model.SkuPromotionsSku;
import com.aladingshop.sku.cms.service.SkuCategoryService;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.sku.cms.service.SkuPromotionsInfoService;
import com.aladingshop.sku.cms.service.SkuPromotionsSkuService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.LogPromotionsRecordDto;
import com.fushionbaby.cms.dto.SkuDto;
import com.fushionbaby.cms.dto.SkuPromotionsInfoDto;
import com.fushionbaby.cms.dto.SkuPromotionsSkuDto;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.log.model.LogPromotionsRecord;
import com.fushionbaby.log.service.LogPromotionsRecordService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 商品活动control
 * 
 * @author xpf 
 */
@Controller
@RequestMapping("/skuPromotions")
public class SkuPromotionsController extends BaseController {

	@Autowired
	private SkuPromotionsInfoService<SkuPromotionsInfo> skuPromotionsInfoService;
	
	@Autowired
	private SkuPromotionsSkuService<SkuPromotionsSku> skuPromotionsSkuService;
	
	@Autowired
	private LogPromotionsRecordService logPromotionsRecordService;
	
	@Autowired
	private SkuInfoService skuInfoService;
	
	@Autowired
	private MemberService<Member> memberService;

	@Autowired
	private SkuCategoryService<SkuCategory> categoryService;

	private static final Log log = LogFactory.getLog(SkuPromotionsController.class);

	/**
	 * 初始化商品活动列表
	 * 
	 * @param name
	 * @param code
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("skuPromotionsInfoList")
	public String getSkuLabelRelationList(@ModelAttribute("findForm") SkuPromotionsInfoDto skuPromotionsInfoDto, BasePagination page,
			ModelMap model, HttpSession session) {
		skuPromotionsList(skuPromotionsInfoDto, page, model, session);

		return "models/sku/skuPromotions/skuPromotionsList";
	}

	private void skuPromotionsList(SkuPromotionsInfoDto skuPromotionsInfoDto, BasePagination page, ModelMap model, HttpSession session) {
		if (ObjectUtils.equals(page, null)) {
			page = new BasePagination();
		}
		Map<String, String> params = setParamsBySkuPromotionsInfoDto(skuPromotionsInfoDto);
		page.setParams(params);
		page = skuPromotionsInfoService.getListPage(page);
		model.addAttribute("skuPromotionsInfoDto", skuPromotionsInfoDto);
		model.addAttribute("page", page);

		if (session == null)
			return;

		model.addAttribute("ADD_REMARK", session.getAttribute("ADD_SKUPROMOTIONS_SUCCESS"));
		model.addAttribute("MODIFY_REMARK", session.getAttribute("MODIFY_SKUPROMOTIONS_SUCCESS"));

		session.setAttribute("ADD_SKUPROMOTIONS_SUCCESS", "");
		session.setAttribute("MODIFY_SKUPROMOTIONS_SUCCESS", "");
	}

	private Map<String, String> setParamsBySkuPromotionsInfoDto(SkuPromotionsInfoDto skuPromotionsInfoDto) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("promotionsName", skuPromotionsInfoDto.getPromotionsName());
		params.put("promotionsCode", skuPromotionsInfoDto.getPromotionsCode());
		return params;
	}

	

	/**
	 * 修改商品活动页面
	 * 
	 * @param name
	 * @param code
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("editSkuPromotions")
	public String editSkuLabel(@RequestParam("pmCode") String pmCode, ModelMap model) {
		SkuPromotionsInfo promotionsInfo = skuPromotionsInfoService.findByPromotionsCode(pmCode);

		model.addAttribute("editPromotionsInfo", promotionsInfo);
		return "models/sku/skuPromotions/skuPromotionsModify";
	}

	/**
	 * 修改商品活动信息
	 * @throws ParseException 
	 * 
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "updateSkuPromotions", method = RequestMethod.POST)
	public String modifyBrand(@RequestParam("jsonStr") String jsonStr, HttpSession session) throws ParseException {
		// 解析JSON
		SkuPromotionsInfo skuPromotionsInfo = jsonConvertSkuPromotions(jsonStr);
		// 获取当前登录用户id
		skuPromotionsInfo.setUpdateId(Long.valueOf(CMSSessionUtils.getSessionUser(session).getId()));

		try {
			skuPromotionsInfoService.updateByPromotionsCode(skuPromotionsInfo);
			session.setAttribute("MODIFY_SKUPROMOTIONS_SUCCESS", "0");
		} catch (DataAccessException e) {
			log.error("修改商品标签信息失败:" + e);
			return Constant.FAILURE;
		}

		return Constant.SUCCESS;
	}

	/**
	 * 新增商品活动页面
	 * 
	 * @param name
	 * @param code
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("toAddSkuPromotions")
	public String editSkuLabel() {
		return "models/sku/skuPromotions/skuPromotionsAdd";
	}

	/**
	 * 新增活动信息
	 * @throws ParseException 
	 * 
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "addSkuPromotions", method = RequestMethod.POST)
	public String addSkuLabel(@RequestParam("jsonStr") String jsonStr, HttpSession session) throws ParseException {
		// 解析JSON
		SkuPromotionsInfo skuPromotionsInfo = jsonConvertSkuPromotions(jsonStr);
		// 获取当前登录用户id
		skuPromotionsInfo.setCreateId(Long.valueOf(CMSSessionUtils.getSessionUser(session).getId()));

		try {
			skuPromotionsInfoService.add(skuPromotionsInfo);
			session.setAttribute("ADD_SKUPROMOTIONS_SUCCESS", "0");
		} catch (DataAccessException e) {
			log.error("新增信息失败:" + e);
			return Constant.FAILURE;
		}

		return Constant.SUCCESS;
	}

	private SkuPromotionsInfo jsonConvertSkuPromotions(String jsonStr) throws ParseException {
		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(jsonStr);
		SkuPromotionsInfo skuPromotionsInfo = new SkuPromotionsInfo();
		skuPromotionsInfo.setPromotionsName(json.getAsJsonObject().get("promotionsName").getAsString());
		skuPromotionsInfo.setPromotionsCode(json.getAsJsonObject().get("promotionsCode").getAsString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		skuPromotionsInfo.setStartDate(sdf.parse(json.getAsJsonObject().get("startDate").getAsString()));
		skuPromotionsInfo.setEndDate(sdf.parse(json.getAsJsonObject().get("endDate").getAsString()));
		skuPromotionsInfo.setTimeRange(json.getAsJsonObject().get("timeRange").getAsString());
		skuPromotionsInfo.setIsRepeatBuy(json.getAsJsonObject().get("isRepeatBuy").getAsString());
		BigDecimal salesPrice=new BigDecimal(json.getAsJsonObject().get("salesPrice").getAsString());
		skuPromotionsInfo.setSalesPrice(salesPrice);
		return skuPromotionsInfo;
	}

	/**
	 * 前端校验商品活动code是否重复
	 * 
	 * @param state
	 * @param memberId
	 * @param orderCode
	 */
	@ResponseBody
	@RequestMapping(value = "existSkuPromotionsCode", method = RequestMethod.GET)
	public Object existSkuLabelCode(@RequestParam("promotionsCode") String promotionsCode) {
		SkuPromotionsInfo skuPromotionsInfo = skuPromotionsInfoService.findByPromotionsCode(promotionsCode);
		if (skuPromotionsInfo == null) {
			return true;
		}

		return false;
	}

	/**
	 * 根据活动查出相关商品列表
	 * 
	 * @param labelCode
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("skuListByPromotions")
	public String getSkuListByLabel(@RequestParam("promotionsCode") String pmCode,
			SkuDto skuDto,
			BasePagination page, ModelMap model,
			HttpSession session) {
		// 根据labelCode获取全部skuCode
		List<String> skuCodes = skuPromotionsSkuService.findSkuCodesByPmCode(pmCode);

		this.skuList(pmCode, skuDto, skuCodes, page, model, session);

		model.addAttribute("CURRENT_PROMOTIONS_CODE", pmCode);

		return "models/sku/skuPromotions/skuPromotionsSkuList";
	}

	private void skuList(String pmCode,SkuDto skuDto,  List<String> skuCodes, 
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
		List<SkuPromotionsSkuDto> relDtos = new ArrayList<SkuPromotionsSkuDto>();
		if (!CollectionUtils.isEmpty(skuList)) {
			for (Sku sku : skuList) {
				SkuPromotionsSkuDto relDto = new SkuPromotionsSkuDto();
				relDto.setUniqueCode(sku.getUniqueCode());
				relDto.setSkuNo(sku.getSkuNo());
				relDto.setBarCode(sku.getBarCode());
				relDto.setSkuName(sku.getName());
				SkuPromotionsSku promotionsSku = skuPromotionsSkuService.findByPmCodeAndSkuCode(pmCode, sku.getUniqueCode());
				relDto.setSort(ObjectUtils.toString(promotionsSku.getSort(), "0"));
				relDto.setSkuPromotionsStatus(promotionsSku.getSkuPromotionsStatus());
				relDto.setSkuStatus(sku.getStatus());
				relDto.setCreateTime(promotionsSku.getCreateTime());
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
		params.put("name", skuDto.getSkuName());
		params.put("skuCodes", getAllCodes(skuCodes));
		params.put("barCode", skuDto.getBarCode());
		return params;
	}

	/**
	 * 前端删除关系数据
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteSkuPromotionsRelation", method = RequestMethod.POST)
	public Object deleteSkuLabelRelation(@RequestParam("promotionsCode") String promotionsCode,
			@RequestParam("skuCode") String skuCode) {
		SkuPromotionsSku skuPromotionsSku = new SkuPromotionsSku();
		skuPromotionsSku.setPmCode(promotionsCode);
		skuPromotionsSku.setSkuCode(skuCode);
		try {
			// 删除关系表相关数据
			skuPromotionsSkuService.deleteByPmCodeAndSkuCode(promotionsCode, skuCode);;
		} catch (Exception e) {
			log.error("删除关系数据失败：" + e);
			return Constant.FAILURE;
		}

		return Constant.SUCCESS;
	}

	/**
	 * 添加活动商品页
	 * 
	 * @param labelCode
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("addNoRelationSku")
	public String addNoRelationSku(SkuDto skuDto, BasePagination page, ModelMap model) {
		// 根据labelCode获取全部skuCode
		List<String> skuCodes = skuPromotionsSkuService.findSkuCodesByPmCode(skuDto.getPromotionsCode());
		/** 查询出所有二级分类 */
		model.addAttribute("categorys", categoryService.getCategoryByLevel(2,null));

		skuNoRelationList(skuDto, skuCodes, page, model);

		model.addAttribute("CURRENT_PROMOTIONS_CODE", skuDto.getPromotionsCode());

		return "models/sku/skuPromotions/skuPromotionsRelationAdd";
	}

	private void skuNoRelationList(SkuDto skuDto, List<String> skuCodes, BasePagination page, ModelMap model) {
		if (ObjectUtils.equals(page, null)) {
			page = new BasePagination();
		}
		Map<String, String> params = setParamsByNoRelationSkuDto(skuCodes, skuDto);
		page.setParams(params);
		page = skuInfoService.getPageList(page);
		model.addAttribute("SkuDto", skuDto);
		model.addAttribute("page", page);
	}

	private Map<String, String> setParamsByNoRelationSkuDto(List<String> skuNos, SkuDto skuDto) {
		Map<String, String> params = new HashMap<String, String>();
		String reStr = getAllCodes(skuNos);
		params.put("noSkuCodes", Objects.equals(reStr, "null") ? null : reStr);
		params.put("skuNo", skuDto.getSkuNo());
		params.put("name", skuDto.getSkuName());
		params.put("status", skuDto.getStatus());
		params.put("productCode", skuDto.getProductCode());
		params.put("grandCategoryCode", skuDto.getGrandCategoryCode());

		params.put("barCode",skuDto.getBarCode());
		
		return params;
	}

	/**
	 * 前端添加关系数据
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addPromotionsSku", method = RequestMethod.POST)
	public Object addSkuByLabel(@RequestParam("pmCode") String pmCode, @RequestParam("skuCodes") String skuCodes,
			ModelMap model,HttpSession session) {
		List<String> all = Arrays.asList(skuCodes.split(","));
		for (String currentSkuCode : all) {
			SkuPromotionsSku promotionsSku =new SkuPromotionsSku();
			AuthUser auUser = CMSSessionUtils.getSessionUser(session);
			promotionsSku.setCreateId(auUser.getId());
			promotionsSku.setPmCode(pmCode);
			promotionsSku.setSkuCode(currentSkuCode);
			promotionsSku.setSkuPromotionsStatus("n");
			try {
				skuPromotionsSkuService.add(promotionsSku);
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
	@RequestMapping("/skuPromotionsRelationEdit/{pmCode}/{skuCode}")
	public String skuLabelRelationEditModel(@PathVariable String pmCode, @PathVariable String skuCode,
			ModelMap model, RedirectAttributes redirectAttributes) {

		SkuPromotionsSku promotionsSku =new SkuPromotionsSku();
		promotionsSku.setPmCode(pmCode);
		promotionsSku.setSkuCode(skuCode);
		promotionsSku=skuPromotionsSkuService.findByPmCodeAndSkuCode(pmCode, skuCode);
		if (promotionsSku == null) {
			addMessage(redirectAttributes, "数据异常");
			return "redirect:" + Global.getAdminPath() + "/skuPromotions/skuListByPromotions?promotionsCode=" + pmCode;
		}
		SkuPromotionsSkuDto promotionsSkuDto= new SkuPromotionsSkuDto();
		promotionsSkuDto.setId(promotionsSku.getId());
		promotionsSkuDto.setLimitCount(promotionsSku.getLimitCount());
		promotionsSkuDto.setPmCode(promotionsSku.getPmCode());
		Sku sku=skuInfoService.queryByUniqueCode(promotionsSku.getSkuCode());
		if(ObjectUtils.notEqual(null, sku)){
			promotionsSkuDto.setSkuName(sku.getName());
		}
		promotionsSkuDto.setSkuPromotionsStatus(promotionsSku.getSkuPromotionsStatus());
		if(ObjectUtils.notEqual(null, promotionsSku.getSort())){
			promotionsSkuDto.setSort(promotionsSku.getSort().toString());
		}
		promotionsSkuDto.setSpecialPrice(promotionsSku.getSpecialPrice());
		promotionsSkuDto.setUniqueCode(promotionsSku.getSkuCode());
		model.addAttribute("promotionsSkuDto", promotionsSkuDto);

		return "models/sku/skuPromotions/skuPromotionsSkuEdit";
	}

	/**
	 * 编辑
	 * 
	 * @param rel
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value="/editPromotionsSkuRelation", method = RequestMethod.POST)
	public String editLabelRelation(@RequestParam("jsonStr") String jsonStr) throws ParseException {
		SkuPromotionsSku rel=jsonConvertSkuPromotionsSku(jsonStr);
		SkuPromotionsSku findRel = skuPromotionsSkuService.findById(rel.getId());
		if (findRel == null) {
			return Constant.FAILURE;
		}
		skuPromotionsSkuService.updateById(rel);
		return Constant.SUCCESS;
	}

	private SkuPromotionsSku jsonConvertSkuPromotionsSku(String jsonStr) throws ParseException {
		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(jsonStr);
		SkuPromotionsSku skuPromotionsSku = new SkuPromotionsSku();
		skuPromotionsSku.setId(json.getAsJsonObject().get("id").getAsLong());
		skuPromotionsSku.setLimitCount(json.getAsJsonObject().get("limitCount").getAsInt());
		skuPromotionsSku.setPmCode(json.getAsJsonObject().get("pmCode").getAsString());
		skuPromotionsSku.setSkuCode(json.getAsJsonObject().get("skuCode").getAsString());
		skuPromotionsSku.setSkuPromotionsStatus(json.getAsJsonObject().get("skuPromotionsStatus").getAsString());
		skuPromotionsSku.setSort(json.getAsJsonObject().get("sort").getAsLong());
		skuPromotionsSku.setSpecialPrice(json.getAsJsonObject().get("specialPrice").getAsBigDecimal());
		return skuPromotionsSku;
	}
	
	/**
	 * 删除活动
	 * 
	 * @param labelCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteSkuPromotions", method = RequestMethod.POST)
	public Object deleteSkuPromotions(@RequestParam("promotionsCode") String promotionsCode) {

		try {
			// 删除关系表相关数据
			skuPromotionsSkuService.deleteByPmCode(promotionsCode);
			// 删除活动
			skuPromotionsInfoService.deleteByPromotionsCode(promotionsCode);
		} catch (Exception e) {
			log.error("删除关系数据失败：" + e);
			return Constant.FAILURE;
		}

		return Constant.SUCCESS;
	}
	
	/**
	 * 活动交易记录列表
	 * 
	 * @param labelCode
	 * @param page
	 * @param model 	
	 * @return
	 */
	@RequestMapping("logPromotionsRecordList")
	public String getSkuListByLabel(String orderCode,String loginName,String skuName,String pmCode,
			String createTimeFrom,String createTimeTo,
			BasePagination page, ModelMap model,
			HttpSession session) {
		model.addAttribute("orderCode", orderCode);
		model.addAttribute("loginName", loginName);
		model.addAttribute("skuName", skuName);
		model.addAttribute("pmCode", pmCode);
		model.addAttribute("createTimeFrom", createTimeFrom);
		model.addAttribute("createTimeTo", createTimeTo);
		if (ObjectUtils.equals(page, null)) {
			page = new BasePagination();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("pmCode", pmCode);
		params.put("createTimeFrom", createTimeFrom);
		params.put("createTimeTo", createTimeTo);
		params.put("orderCode",orderCode);
		if(!StringUtils.isEmpty(loginName)){
			Member memberNew=memberService.findByLoginName(loginName);
			if(ObjectUtils.notEqual(null, memberNew)){
				params.put("memberId",memberNew.getId().toString());
			}else{
				page.setResult(new ArrayList<LogPromotionsRecordDto>());
				model.addAttribute("page", page);
				return "models/sku/skuPromotions/skuPromotionsRecordList";
			}
		}
		
		
		if(StringUtils.isNotBlank(skuName)){
			List<Sku> skuInfoList = skuInfoService.findSkusByLikeName(skuName);
			if(skuInfoList.size()>0){
				String includeSkuCodes="";
				for(Sku skuInfo: skuInfoList){
					includeSkuCodes+=",'"+skuInfo.getUniqueCode()+"'";
				}
				includeSkuCodes=includeSkuCodes.substring(1);
				params.put("includeSkuCodes", includeSkuCodes);
			}else{
				page.setResult(new ArrayList<LogPromotionsRecordDto>());
				model.addAttribute("page", page);
				return "models/sku/skuPromotions/skuPromotionsRecordList";
			}
			
		}
		
		page.setParams(params);
		page = logPromotionsRecordService.getListPage(page);
		@SuppressWarnings("unchecked")
		List<LogPromotionsRecord> recordList=(ArrayList<LogPromotionsRecord>)page.getResult();
		List<LogPromotionsRecordDto> recordDtoList=new ArrayList<LogPromotionsRecordDto>();
		for(LogPromotionsRecord recordtmp : recordList){
			LogPromotionsRecordDto dtotmp=new LogPromotionsRecordDto();
			dtotmp.setId(recordtmp.getId());
			Member member = memberService.findById(recordtmp.getMemberId());
			dtotmp.setLoginName(member.getLoginName());
			dtotmp.setMemberId(recordtmp.getMemberId());
			dtotmp.setOrderCode(recordtmp.getOrderCode());
			dtotmp.setPmCode(recordtmp.getPmCode());
			SkuPromotionsInfo promotionsInfo=skuPromotionsInfoService.findByPromotionsCode(recordtmp.getPmCode());
			dtotmp.setPromotionsName(promotionsInfo.getPromotionsName());
			Sku sku = skuInfoService.queryByUniqueCode(recordtmp.getSkuCode());
			dtotmp.setSkuName(sku.getName());
			dtotmp.setCreateTime(recordtmp.getCreateTime());
			recordDtoList.add(dtotmp);
		}
		page.setResult(recordDtoList);
		model.addAttribute("page", page);
		return "models/sku/skuPromotions/skuPromotionsRecordList";
	}
	
	/**
	 * 删除活动
	 * 
	 * @param labelCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteSkuPromotionsRecord", method = RequestMethod.POST)
	public Object deleteSkuPromotionsRecord(Long id) {
		try {
			logPromotionsRecordService.deleteById(id);
		} catch (Exception e) {
			log.error("删除关系数据失败：" + e);
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}
	
}
