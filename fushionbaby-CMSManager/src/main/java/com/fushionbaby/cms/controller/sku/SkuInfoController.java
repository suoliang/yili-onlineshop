package com.fushionbaby.cms.controller.sku;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuExtraInfo;
import com.aladingshop.sku.cms.model.SkuImage;
import com.aladingshop.sku.cms.model.SkuInventory;
import com.aladingshop.sku.cms.model.SkuPrice;
import com.aladingshop.sku.cms.model.SkuProductAttr;
import com.aladingshop.sku.cms.service.SkuInventoryService;
import com.aladingshop.sku.cms.service.SkuProductAttrService;
import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cache.service.GlobalConfig;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.dto.AttrDto;
import com.fushionbaby.cms.dto.SkuDto;
import com.fushionbaby.cms.util.BatchUploadFiles;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.HttpRequest;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.google.gson.Gson;

/**
 * 
 * @author mengshaobo
 *
 */
@Controller
@RequestMapping("/sku")
public class SkuInfoController extends SkuBaseController {
	private static final Log LOGGER = LogFactory.getLog(SkuInfoController.class);
	private static final String FTP_PATH = "/userfiles/images/sku";
	
	private String REDIRECT_URL = "redirect:" + Global.getAdminPath() + "/sku/findSkuList";
	@Autowired
	private GlobalConfig globalConfig;
	
	@Autowired
	private SkuProductAttrService<SkuProductAttr> skuProductAttrService;

	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	@Autowired
	private StoreService<Store> StoreService;
	/**
	 * 更新solr 索引库
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateSolr")
	public String addSkuDocument() {
		try {
			// skuSearchService.addSkuDocument();
			HttpRequest.sendGet("www.aladingshop.com/webtest/updatesolr", StringUtils.EMPTY);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	/**
	 * 分页查询商品列表
	 * 
	 * @param skuDto
	 *            商品信息
	 * @param page
	 *            分页信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/findSkuList")
	public String findSkuList(SkuDto skuDto, BasePagination page, Model model) {

		BasePagination basePage = new BasePagination();
		if (ObjectUtils.notEqual(page, null)) {
			basePage = page;
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("productCode", skuDto.getProductCode());
		params.put("storeCode", CommonConstant.STORE_CODE_SHOP);
		params.put("barCode", skuDto.getBarCode());
		params.put("skuNo", skuDto.getSkuNo());
		params.put("name", skuDto.getSkuName());
		params.put("status", skuDto.getStatus());
		params.put("createTimeFrom", skuDto.getCreateTimeFrom());
		params.put("createTimeTo", skuDto.getCreateTimeTo());
		params.put("updateTimeFrom", skuDto.getUpdateTimeFrom());
		params.put("updateTimeTo", skuDto.getUpdateTimeTo());
		basePage.setParams(params);
		Long time=new Date().getTime();
		LOGGER.info("skuService.getPageList(basePage) 分页查询开始时间  "+time );
		BasePagination pageResult = skuService.getPageList(basePage);
		LOGGER.info("skuService.getPageList(basePage) 分页查询结束时间为 "+(new Date().getTime()-time)/1000 +"秒。");
		
		LOGGER.info("this.getSkuDtoList((List<Sku>)pageResult.getResult()) 查询开始时间为 "+(new Date().getTime()-time)/1000 +"秒。");
		@SuppressWarnings("unchecked")
		List<SkuDto> skuList = this.getSkuDtoList((List<Sku>)pageResult.getResult());
		LOGGER.info("this.getSkuDtoList((List<Sku>)pageResult.getResult()) 查询结束时间为 "+(new Date().getTime()-time)/1000 +"秒。");
		
		model.addAttribute("storeList", StoreService.findAll());
		model.addAttribute("page", pageResult);
		model.addAttribute("skuList", skuList);
		model.addAttribute("skuDto", skuDto);
		return "models/sku/skuList";
	}
	
	
	@RequestMapping("createSku")
	public String createSku(Model model){
		
		SkuDto skuDto = new SkuDto();
		
		model.addAttribute("allCategoryJson", this.getCategoryJson());
		model.addAttribute("sku", skuDto);
		return "models/sku/createSku";
	}
	
	@RequestMapping("addSku")
	public String addSku(SkuDto skuDto, HttpSession session,
			RedirectAttributes redirectAttributes,Model model){
		
		AuthUser user = CMSSessionUtils.getSessionUser(session);
		if (user == null) {
			return "redirect:" + Global.getAdminPath() + "/login/login";
		}
		
		Sku sku = new Sku();
		sku.setStoreCode( CommonConstant.STORE_CODE_SHOP);
		sku.setUniqueCode(RandomNumUtil.getCharacterAndNumber(20));
		sku.setSkuNo(skuDto.getSkuNo());
		sku.setBarCode(skuDto.getBarCode());
		sku.setBrandCode(skuDto.getBrandCode());
		sku.setCategoryCode(skuDto.getCategoryCode());
		sku.setColor(skuDto.getColor());
		sku.setSize(skuDto.getSize());
		sku.setName(skuDto.getSkuName());
		sku.setDescription(skuDto.getSkuDescription());
		sku.setProductCode(skuDto.getProductCode());
		sku.setQuotaNum(Integer.valueOf(skuDto.getQuotaNum()));
		sku.setShoworder(Integer.parseInt(skuDto.getShoworder()));
		sku.setStatus(SkuStatusEnum.SKU_STATUS_INIT.getStrVlue());

		SkuInventory skuInventory = new SkuInventory();
		skuInventory.setProductCode(skuDto.getProductCode());
		skuInventory.setSkuCode(sku.getUniqueCode());
		skuInventory.setSkuColor(skuDto.getColor());
		skuInventory.setSkuSize(skuDto.getSize());
		
		SkuExtraInfo skuExtraInfo = new SkuExtraInfo();
		BeanNullConverUtil.nullConver(skuExtraInfo);
		skuExtraInfo.setId(null);
		skuExtraInfo.setSkuCode(sku.getUniqueCode());
		skuExtraInfo.setHasDiscount(CommonConstant.NO);
		skuExtraInfo.setHasGift(CommonConstant.NO);
		skuExtraInfo.setIsGift(CommonConstant.NO);
		skuExtraInfo.setIsVideo(CommonConstant.NO);
		skuExtraInfo.setIsMemberSku(CommonConstant.NO);
		skuService.addOrUpdate(sku);
		inventoryService.add(skuInventory);
		skuExtraInfoService.add(skuExtraInfo);
		addMessage(model, "创建成功");
		addMessage(redirectAttributes, "创建成功");
		return "models/sku/createSku";
	}
	
	
	/**
	 * 商品编号是否存在
	 * @param skuNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("exitSkuNo")
	public  boolean exitSkuNo(String skuNo){
		Sku sku	 = skuService.queryBySkuNo(skuNo);
		return sku==null?true:false;
		
	}
	@ResponseBody
	@RequestMapping("exitBarCode")
	public boolean exitBarCode(String barCode){
		Sku sku	 =  skuService.queryByBarCode(barCode);
		return sku==null?true:false;
	}
	
	@ResponseBody
	@RequestMapping(value = "/existSkuNo", method = RequestMethod.GET)
	public Object existSkuCode(String skuNo, String oldSkuNo) {
		if (StringUtils.equals(skuNo, oldSkuNo)) {
			return true;
		}
		Sku sku = skuService.queryBySkuNo(skuNo);

		return ObjectUtils.equals(null, sku) ? true : false;
	}

	@ResponseBody
	@RequestMapping(value = "/existBarCode", method = RequestMethod.GET)
	public Object existBarCode(String barCode, String oldBarCode) {
		if (StringUtils.equals(barCode, oldBarCode)) {
			return true;
		}
		Sku sku = skuService.queryByBarCode(barCode);

		return ObjectUtils.equals(null, sku) ? true : false;
	}

	

	/**
	 * 在产品中添加商品时，编辑商品（该商品还没有加入数据库）；
	 * 
	 * @param scode
	 *            商品编号
	 * @param model
	 * @version 2015-6-16
	 * @return
	 */
	@RequestMapping(value = "/skuEdit/{index}", method = RequestMethod.GET)
	public String skuEdit(@PathVariable String index, String skuJsonParams, ModelMap model) {
		SkuDto skuBo = null;
		try {
			String skuParams = URLDecoder.decode(skuJsonParams, "utf-8");
			skuBo = new Gson().fromJson(skuParams, SkuDto.class);
			/** 关键词 */
			String keys = skuBo.getKeyWords();
			if (StringUtils.isNotBlank(keys)) {
				model.addAttribute("keys", Arrays.asList(keys.split(",")));
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("sku", skuBo);
		return "models/sku/skuEdit";
	}

	/**
	 * 商品管理中，修改商品（该商品已经加入数据库）；
	 * 
	 * @param scode
	 *            商品编号
	 * @param model
	 * @version 2015-6-16
	 * @return
	 */
	@RequestMapping(value = "/{id}/{page}/skuDetailPage", method = RequestMethod.GET)
	public String skuModify(@PathVariable Long id, @PathVariable Integer page, Integer currentPage,
			Integer limit,  Integer total, ModelMap model,
			RedirectAttributes redirectAttributes) {

		Sku sku = skuService.findById(id);
		if (ObjectUtils.equals(null, sku)) {
			addMessage(redirectAttributes, "商品信息出现异常");
			return "redirect:" + Global.getAdminPath() + "/sku/findSkuList";
		}
		List<SkuProductAttr> attrs = skuProductAttrService.findByProductCode(sku.getProductCode());
		SkuDto skuDto = this.getSkuDto(sku.getUniqueCode());
		model.addAttribute("attrs", attrs);
		model.addAttribute("allCategoryJson", this.getCategoryJson());
		model.addAttribute("sku", skuDto);
		model.addAttribute("skuId", id);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("limit", limit);
		model.addAttribute("total", total);
		if (page == 0) {
			return "models/sku/skuModify";
		}
		return "models/sku/skuDetail";
	}
	
	


	/**
	 * 商品详情添加页面
	 * 
	 * @param model
	 * @version 2015-6-16
	 * @return
	 */
	@RequestMapping(value = "/skuAdd", method = RequestMethod.GET)
	public String skuAdd(ModelMap model) {
		return "models/sku/skuEdit";
	}


	/**
	 * 修改商品信息
	 * 
	 * @param id
	 * @param redirectAttributes
	 * @version 2015-6-16
	 * @return
	 */
	@RequestMapping(value = "/modifySku", method = RequestMethod.POST)
	public String modifySku(Long id, SkuDto skuDto,
			 @RequestParam("attrs") List<String> attrs,
			Integer currentPage, Integer limit, Integer total,
			RedirectAttributes redirectAttributes, HttpSession session) {

		AuthUser user = CMSSessionUtils.getSessionUser(session);
		if (user == null) {
			return "redirect:" + Global.getAdminPath() + "/login/login";
		}
		
		String skuCode = skuDto.getUniqueCode();

		Sku sku = skuService.findById(id);
		if (sku == null) {
			addMessage(redirectAttributes, "修改失败，商品信息有误！");
			return REDIRECT_URL;
		}
		if (StringUtils.equals("1", sku.getStatus())) {/* 可编辑状态才可以修改商品编号 */
			sku.setSkuNo(skuDto.getSkuNo());
		}
		sku.setBrandCode(skuDto.getBrandCode());
		sku.setCategoryCode(skuDto.getCategoryCode());
		sku.setColor(skuDto.getColor());
		sku.setSize(skuDto.getSize());
		sku.setName(skuDto.getSkuName());
		sku.setDescription(skuDto.getSkuDescription());
		sku.setProductCode(skuDto.getProductCode());
		sku.setQuotaNum(Integer.valueOf(skuDto.getQuotaNum()));
		sku.setShoworder(Integer.parseInt(skuDto.getShoworder()));

//		SkuPrice skuPrice = skuPriceService.findBySkuCode(skuCode);
//		if (skuPrice == null) {
//			skuPrice = new SkuPrice();
//		}
//		skuPrice.setUpdateId(user.getId());
//		skuPrice.setSkuCode(sku.getUniqueCode());
//		skuPrice.setCostPrice(skuPriceDto.getCostPrice());
//		skuPrice.setCurrentPrice(skuPriceDto.getCurrentPrice());
//		skuPrice.setMarketPrice(skuPriceDto.getMarketPrice());
//		skuPrice.setPrePrice(skuPriceDto.getPrePrice());
//		skuPrice.setRetailPrice(skuPriceDto.getRetailPrice());
//		skuPrice.setAladingPrice(skuPriceDto.getAladingPrice());

		SkuInventory skuInventory = inventoryService.findBySkuCode(skuCode);
		if (skuInventory == null) {
			skuInventory = new SkuInventory();
		}
		skuInventory.setProductCode(skuDto.getProductCode());
		skuInventory.setSkuCode(sku.getUniqueCode());
		skuInventory.setSkuColor(skuDto.getColor());
		skuInventory.setSkuSize(skuDto.getSize());

		skuService.update(sku);
		
		this.addProductAttr(skuDto.getProductCode(), attrs);
//		skuPriceService.update(skuPrice);
		inventoryService.update(skuInventory);
		redirectAttributes.addAttribute("currentPage", currentPage);
		redirectAttributes.addAttribute("limit", limit);
		redirectAttributes.addAttribute("total", total);
		addMessage(redirectAttributes, "修改成功");
		return REDIRECT_URL;
	}
	
	
	/**
	 * 添加产品属性
	 * 
	 * @param productCode
	 *            产品编号
	 * @param attrs
	 *            属性集合 1.先删除该产品下的所以产品属性<br>
	 *            2.添加产品属性<br>
	 */
	private void addProductAttr(String productCode, List<String> attrs) {
		
		List<AttrDto> attrDtos = this.getAttrDtoList(attrs);
		List<SkuProductAttr> findAttrs = skuProductAttrService.findByProductCode(productCode);
		
		Set<Long> removeAttrId = this.getRemoveId(attrDtos, findAttrs);
		if(!CollectionUtils.isEmpty(removeAttrId) ){
			for (Long id : removeAttrId) {
				skuProductAttrService.deleteById(id);
			}
		}
		
		for (AttrDto attrDto : attrDtos) {
			SkuProductAttr attr = new SkuProductAttr();
			attr.setAttrName(attrDto.getAttrName());
			attr.setAttrValue(attrDto.getAttrValue());
			attr.setProductCode(productCode);
			attr.setShowOrder(attrDto.getShowOrder());
			attr.setStoreCode( CommonConstant.STORE_CODE_SHOP);
			if(attrDto.getId()==null || attrDto.getId()==0){
				skuProductAttrService.add(attr);
			}else{
				attr.setId(attrDto.getId());
				skuProductAttrService.update(attr);
				
			}
		}
		
	
	}
	
	/**
	 * 筛选需要删除的属性Id
	 * @param attrDtos
	 * @param findAttrs
	 * @return
	 */
	private Set<Long> getRemoveId(List<AttrDto> attrDtos ,List<SkuProductAttr> findAttrs){
		
		 Set<Long> a = new HashSet<Long>();
		 
		 for (AttrDto attrDto : attrDtos) {
			 if(attrDto!=null && attrDto.getId()!=null && attrDto.getId().longValue()!=0){
				 a.add(attrDto.getId());
			 }
		 }
		 
		 Set<Long> b = new HashSet<Long>();
		 for (SkuProductAttr skuProductAttr : findAttrs) {
			b.add(skuProductAttr.getId());
		 }
		 
		 Set<Long> a1 = new HashSet<Long>();
		 Set<Long> b1 = new HashSet<Long>();
		 a1.addAll(a);
		 b1.addAll(b);
		 a1.removeAll(b);
		 b1.removeAll(a);	
		 
		 return b1;
		
	}
	
	
	/**
	 * 获取商品属性集合
	 * 
	 * @param attrs
	 * @version 2015-6-16
	 * @return
	 */
	private List<AttrDto> getAttrDtoList(List<String> attrs) {
		List<AttrDto> attrDtos = new ArrayList<AttrDto>();
		for (String attr : attrs) {
			if (StringUtils.isBlank(attr)) {
				continue;
			}
			AttrDto attrDto = new Gson().fromJson(attr, AttrDto.class);
			attrDtos.add(attrDto);
		}
		return attrDtos;
	}

	/**
	 * 删除商品记录
	 * 
	 * @param id
	 * @param redirectAttributes
	 * @version 2015-6-16
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}/skuDelete", method = RequestMethod.POST)
	public String skuDelete(@PathVariable Long id) {
		try {
			Sku curSku = skuService.findById(id);
			SkuPrice skuPrice = skuPriceService.findBySkuCode(curSku.getUniqueCode());
			SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(curSku.getUniqueCode());
			SkuInventory skuInventory = skuInventoryService.findBySkuCode(curSku.getUniqueCode());
			if(curSku!=null && curSku.getId()!=null){
				skuService.deleteById(curSku.getId());
			}
			if(skuPrice!=null && skuPrice.getId() !=null){
				skuPriceService.deleteById(skuPrice.getId());
			}
			if(skuExtraInfo!=null && StringUtils.isNotBlank( skuExtraInfo.getSkuCode())){
				skuExtraInfoService.deleteBySkuCode(skuExtraInfo.getSkuCode());
			}
			if(skuInventory!=null && skuInventory.getId()!=null){
				skuInventoryService.deleteById(skuInventory.getId());
			}
			
			List<SkuImage> imageList = skuImageService.findBySkuCode(curSku.getUniqueCode());
			if (ObjectUtils.notEqual(null, imageList)) {
				for (SkuImage skuImage : imageList) {
					skuImageService.deleteById(skuImage.getId());
					BatchUploadFiles.delFtpImage(BatchUploadFiles.getFileName(skuImage.getImgUrl()), FTP_PATH + "/"
							+ BatchUploadFiles.getFileBeforDic(skuImage.getImgUrl())); // FTP删除
				}
			}
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage());
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}

	/**
	 * 修改商品状态
	 * 
	 * @param id
	 *            商品编号
	 * @param status
	 *            状态
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modifySkuStatus", method = RequestMethod.GET)
	public Object modifySkuStatus(@RequestParam("id") Long id, @RequestParam("status") String status,
			HttpSession session) {

		try {
			String newStatus = null;
			if (StringUtils.equals(status, SkuStatusEnum.SKU_STATUS_INIT.getStrVlue())) {
				newStatus = SkuStatusEnum.SKU_STATUS_TOP.getStrVlue();
			} else if (StringUtils.equals(status, SkuStatusEnum.SKU_STATUS_DOWN.getStrVlue())) {
				newStatus = SkuStatusEnum.SKU_STATUS_INIT.getStrVlue();
			} else {
				newStatus = SkuStatusEnum.SKU_STATUS_DOWN.getStrVlue();
			}

			Sku skuEntry = skuService.findById(id);
			skuEntry.setStatus(newStatus);
			skuEntry.setUpdateTime(new Date());
			AuthUser auUser = CMSSessionUtils.getSessionUser(session);
			skuEntry.setUpdateId(auUser.getId());
			SkuExtraInfo skuExtraInfo = extraInfoService.findBySkuCode(skuEntry.getUniqueCode());

			if (skuExtraInfo == null) {
				return Jsonp.error("请先编辑商品基本信息，才能设置商品状态");
			}

			if (StringUtils.equals(newStatus, SkuStatusEnum.SKU_STATUS_TOP.getStrVlue())) {
				skuExtraInfo.setOnshelvestime(new Date());
			} else if (StringUtils.equals(newStatus, SkuStatusEnum.SKU_STATUS_DOWN.getStrVlue())) {
				skuExtraInfo.setOffshelvestime(new Date());
			}
			skuService.update(skuEntry);
			extraInfoService.addOrUpdate(skuExtraInfo);
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage());
			return false;
		}
		return true;

	}

	/***
	 * 批量更新商品状态
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "modifySkusStatus", method = RequestMethod.POST)
	public JsonResponseModel updateOrdersStatus(@RequestParam("status") String status,
			@RequestParam("tempSkuIds") String tempSkuIds, HttpServletRequest request, HttpSession session) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			String[] skuIds = tempSkuIds.split(",");
			Set<String> requireEditSkuNos = new HashSet<String>();
			for (int i = 0; i < skuIds.length; i++) {

				String skuId = skuIds[i];
				String newStatus = null;
				if (StringUtils.equals(status, SkuStatusEnum.SKU_STATUS_INIT.getStrVlue())) {
					newStatus = SkuStatusEnum.SKU_STATUS_TOP.getStrVlue();
				} else if (StringUtils.equals(status, SkuStatusEnum.SKU_STATUS_DOWN.getStrVlue())) {
					newStatus = SkuStatusEnum.SKU_STATUS_INIT.getStrVlue();
				} else {
					newStatus = SkuStatusEnum.SKU_STATUS_DOWN.getStrVlue();
				}

				Sku skuEntry = skuService.findById(Long.parseLong(skuId));
				skuEntry.setStatus(newStatus);
				skuEntry.setUpdateTime(new Date());
				AuthUser auUser = CMSSessionUtils.getSessionUser(session);
				skuEntry.setUpdateId(auUser.getId());
				SkuExtraInfo skuExtraInfo = extraInfoService.findBySkuCode(skuEntry.getUniqueCode());

				if (skuExtraInfo == null) {
					requireEditSkuNos.add(skuEntry.getSkuNo());
					continue;
				}

				if (StringUtils.equals(newStatus, SkuStatusEnum.SKU_STATUS_TOP.getStrVlue())) {
					skuExtraInfo.setOnshelvestime(new Date());
				} else if (StringUtils.equals(newStatus, SkuStatusEnum.SKU_STATUS_DOWN.getStrVlue())) {
					skuExtraInfo.setOffshelvestime(new Date());
				}
				skuService.update(skuEntry);
				extraInfoService.addOrUpdate(skuExtraInfo);
			}
			if (requireEditSkuNos.size() > 0) {
				String requireEditSkuCodesStr = "";
				for (String str : requireEditSkuNos) {
					requireEditSkuCodesStr = requireEditSkuCodesStr + str + ",";
				}
				jsonModel.Success("订单号：" + requireEditSkuCodesStr + " 没有设置商品基本信息,请重新操作这几个商品！");
			} else {
				jsonModel.Success("操作成功!");
			}

		} catch (Exception e) {
			jsonModel.Fail("操作出错!");
			LOGGER.error("操作出错", e);
		}
		return jsonModel;
	}

	


	
	
	@RequestMapping(value = "skuDetails", method = RequestMethod.GET)
	public String skuDetails(@RequestParam(value="uniqueCode")String uniqueCode, ModelMap model,
			RedirectAttributes redirectAttributes) {
		Sku sku = skuService.queryByUniqueCode(uniqueCode);
		SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(uniqueCode);
		SkuPrice skuPrice = skuPriceService.findBySkuCode(uniqueCode);
		SkuInventory skuInventory=inventoryService.findBySkuCode(uniqueCode);
		List<SkuProductAttr> skuProductAttrList = skuProductAttrService.findByProductCode(sku.getProductCode());
		
		model.addAttribute("skuExtraInfo", skuExtraInfo);
		model.addAttribute("skuPrice", skuPrice);
		model.addAttribute("skuInventory", skuInventory);
		model.addAttribute("sku", sku);
		model.addAttribute("skuProductAttrList", skuProductAttrList);
		
		return "models/sku/skuDetails";
	}

}
