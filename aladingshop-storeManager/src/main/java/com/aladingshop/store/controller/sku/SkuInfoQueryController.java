/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月15日下午5:36:15
 */
package com.aladingshop.store.controller.sku;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuExtraInfo;
import com.aladingshop.sku.cms.model.SkuImage;
import com.aladingshop.sku.cms.model.SkuInventory;
import com.aladingshop.sku.cms.model.SkuPrice;
import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.config.Global;
import com.aladingshop.store.dto.SkuDto;
import com.aladingshop.store.model.StoreCell;
import com.aladingshop.store.service.StoreCellService;
import com.aladingshop.store.util.FileUploadTools;
import com.aladingshop.store.util.excel.ImportExcel;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SessionKeyConstant;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.RandomNumUtil;

/**
 * @description 商品信息	
 * @author 孟少博
 * @date 2015年9月15日下午5:36:15
 */
@Controller
@RequestMapping("/store/sku")
public class SkuInfoQueryController extends AbstractSkuInfoController {
	
	
	@Autowired
	private StoreCellService<StoreCell> storeCellService;
	
	private static final Log LOGGER = LogFactory.getLog(SkuInfoQueryController.class);
	
	/**
	 * 查询门店商品
	 * @param skuDto 
	 * @param page
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("findList")
	public Object findList(SkuDto skuDto,BasePagination page,Model model,HttpSession session){
		if(page==null){
			page = new BasePagination();
		}
		
		StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(auUser==null){
			return "redirect:" +Global.getAdminPath() + "/login/index";
		}
		
		String storeCode = StringUtils.isBlank(skuDto.getStoreCode()) ?  auUser.getStoreCode() : skuDto.getStoreCode();
		Map<String, String> params = new HashMap<String, String>();
		params.put("storeCode", storeCode);
		params.put("name", skuDto.getSkuName());
		params.put("skuNo", skuDto.getSkuNo());
		params.put("status", skuDto.getStatus());
		if(StringUtils.isNotBlank(skuDto.getCategoryCode())){
			params.put("categoryCode",skuDto.getCategoryCode());
		}
		params.put("createTimeFrom", skuDto.getCreateTimeFrom());
		params.put("createTimeTo", skuDto.getCreateTimeTo());
		params.put("updateTimeFrom", skuDto.getUpdateTimeFrom());
		params.put("updateTimeTo", skuDto.getUpdateTimeTo());
		page.setParams(params);
		BasePagination pageResult = skuInfoService.getPageList(page);
		model.addAttribute("page", pageResult);
		List<SkuDto> storeSkuDtoList = new ArrayList<SkuDto>();
		if(pageResult!=null && pageResult.getResult()!=null){
			for (Sku storeSku : (List<Sku>)pageResult.getResult()) {
				storeSkuDtoList.add(this.getSkuDto(storeSku.getUniqueCode()));
			}
		}
		skuDto.setStoreNumber(auUser.getStoreNumber());
		model.addAttribute("skuDto", skuDto);
//		model.addAttribute("categorys", categoryService.getCategoryByLevel(2,storeCode));
		model.addAttribute("allCategoryJson", this.getCategoryJson(skuDto.getStoreCode(),CommonConstant.YES));
		model.addAttribute("storeSkuDtoList", storeSkuDtoList);
		return "models/sku/storeSkuList";
	}
	
	/**
	 * 修改商品状态
	 * @param id
	 * @param status
	 * @param session
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("modifySkuStatus")
	public Object modifySkuStatus(String code,String status,HttpSession session){
		
		try {

			Sku skuEntry = skuInfoService.queryByUniqueCode(code);
			skuEntry.setStatus(status);
			skuEntry.setUpdateTime(new Date());
			StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
			skuEntry.setUpdateId(auUser!=null ? auUser.getId() : null);
			skuInfoService.update(skuEntry);
			
		} catch (DataAccessException e) {
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
			@RequestParam("codes") String codes, HttpServletRequest request, HttpSession session) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			String[] skuCodes = codes.split(",");
			Set<String> requireEditSkuNos = new HashSet<String>();
			for (int i = 0; i < skuCodes.length; i++) {

				String uniqueCode = skuCodes[i];
				Sku skuEntry = skuInfoService.queryByUniqueCode(uniqueCode);
				skuEntry.setStatus(status);
				skuEntry.setUpdateTime(new Date());
				StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
				skuEntry.setUpdateId(auUser!=null? auUser.getId():null);
				SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(skuEntry.getUniqueCode());

				if (skuExtraInfo == null) {
					requireEditSkuNos.add(skuEntry.getSkuNo());
					continue;
				}

				if (StringUtils.equals(status, SkuStatusEnum.SKU_STATUS_TOP.getStrVlue())) {
					skuExtraInfo.setOnshelvestime(new Date());
				} else if (StringUtils.equals(status, SkuStatusEnum.SKU_STATUS_DOWN.getStrVlue())) {
					skuExtraInfo.setOffshelvestime(new Date());
				}
				skuInfoService.update(skuEntry);
				skuExtraInfoService.addOrUpdate(skuExtraInfo);
			}
			if (requireEditSkuNos.size() > 0) {
				String requireEditSkuCodesStr = "";
				for (String str : requireEditSkuNos) {
					requireEditSkuCodesStr = requireEditSkuCodesStr + str + ",";
				}
				jsonModel.Success("商品号：" + requireEditSkuCodesStr + " 没有设置商品基本信息,请重新操作这几个商品！");
			} else {
				jsonModel.Success("操作成功!");
			}

		} catch (Exception e) {
			jsonModel.Fail("操作出错!");
			LOGGER.error("操作出错", e);
		}
		return jsonModel;
	}
	
	
	/**
	 * 进入商品编辑详情信息页面
	 * @param skuCode 商品编号
	 * @param page 分页信息
	 * @param model
	 * @return
	 */
	
	@RequestMapping("skuEditPage")
	public String skuDetailPage(String skuCode,BasePagination page,Model model){
		
		Sku sku = skuInfoService.queryByUniqueCode(skuCode);
		if(sku==null){
			return null;
		}
		SkuDto storeSkuDto = this.getSkuDto(skuCode);
		
		if(storeSkuDto!=null && storeSkuDto.getAvailableQty()==null){
			
			SkuInventory  skuInventory = skuInventoryService.findBySkuCode(storeSkuDto.getUniqueCode());
			
			storeSkuDto.setAvailableQty(skuInventory.getAvailableQty());
		}
		
		List<SkuImage> imageList = skuImageService.findBySkuCode(skuCode);
		
		model.addAttribute("imageList",  imageList);
		model.addAttribute("imagePath", Global.getImagePath());
		model.addAttribute("sku", storeSkuDto);
		model.addAttribute("page", page);
		model.addAttribute("allCategoryJson", this.getCategoryJson(sku.getStoreCode(),null));

		return  "models/sku/editStoreSku";
		
	}
	/**
	 * 商品编号是否存在
	 * @param skuNo
	 * @param oldSkuNo
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("exitSkuNo")
	public Object exitSkuNo(String skuNo,String oldSkuNo,HttpSession session){
		
		if(StringUtils.equals(skuNo, oldSkuNo)){
			
			return true;
		}
		
		
		StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		
		String storeCode = auUser.getStoreCode();
		
		
		Sku sku = skuInfoService.queryBySkuNo(skuNo, storeCode);

		return ObjectUtils.equals(null, sku) ? true : false;
		
		
	}
	
	/**
	 * 修改门店商品
	 * @param skuDto
	 * @param skuImage1
	 * @param session
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("updateStoreSku")
	public Object updateStoreSku(SkuDto skuDto,MultipartFile skuImage1,
			HttpSession session,Model model,RedirectAttributes redirectAttributes){
		
		StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(auUser==null){
			return "redirect:" +Global.getAdminPath() + "/login/index";
		}
		File image1 = null;
		String imgPath1  = null;
		try {
			if(skuImage1!=null && StringUtils.isNotBlank(skuImage1.getOriginalFilename())){
				 image1 = FileUploadTools.addFile(skuImage1,  IMG_PATH + auUser.getStoreCode());
				 imgPath1 = StringUtils.replace( image1.getPath(), "\\", "/");
				 
				 imgPath1 = StringUtils.replace(imgPath1, "/userfiles/images", "");
			}
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "修改失败！");
		} catch (IOException e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "修改失败！");
		}
		
		
		Sku sku = skuInfoService.queryByUniqueCode(skuDto.getUniqueCode());
		if(sku== null){
			addMessage(redirectAttributes, "修改失败！");
			return "redirect:" +Global.getAdminPath() + "/store/sku/findList";
		}
		sku.setName(skuDto.getSkuName());
		sku.setQuotaNum(StringUtils.isBlank(skuDto.getQuotaNum()) ? 0 : Integer.valueOf(skuDto.getQuotaNum()));
		sku.setShoworder(StringUtils.isBlank(skuDto.getShoworder()) ? 0 : Integer.valueOf(skuDto.getShoworder()));
		sku.setCategoryCode(skuDto.getCategoryCode());
		
		SkuPrice skuPrice = skuPriceService.findBySkuCode(skuDto.getUniqueCode());
		if(skuPrice ==null){
			skuPrice = new SkuPrice();
			skuPrice.setStoreCode(auUser.getStoreCode());
			skuPrice.setSkuCode(sku.getUniqueCode());	
		}
		skuPrice.setCurrentPrice(skuDto.getCurrentPrice());
		
		List<SkuImage> skuImages = skuImageService.findBySkuCode(sku.getUniqueCode());
		if(!CollectionUtils.isEmpty(skuImages) ){
			SkuImage skuImage = skuImages.get(0);
			if(!StringUtils.isBlank(imgPath1)){
				skuImage.setImgUrl(imgPath1);
			}
			skuImageService.update(skuImage);
		}else{
			SkuImage skuImage = new SkuImage();
			skuImage.setSkuCode(sku.getUniqueCode());
			skuImage.setStoreCode(auUser.getStoreCode());
			skuImage.setSortOrder(1);
			skuImage.setImgUrl(imgPath1);
			skuImageService.add(skuImage);
		}
		SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(sku.getUniqueCode());
		if(skuExtraInfo==null){
			skuExtraInfo = this.initExtraInfo(skuExtraInfo);
			skuExtraInfo.setSkuCode(sku.getUniqueCode());
			skuExtraInfo.setStoreCode(auUser.getStoreCode());
			skuExtraInfoService.add(skuExtraInfo);
		}
		skuPriceService.addOrUpdate(skuPrice);
		skuInfoService.addOrUpdate(sku);
		
		SkuInventory skuInventory = skuInventoryService.findBySkuCode(sku.getUniqueCode());
		
		if(skuInventory==null){
			skuInventory = new SkuInventory();
			skuInventory.setStoreCode(auUser.getStoreCode());
			skuInventory.setSkuCode(sku.getUniqueCode());
			skuInventory.setProductCode(sku.getProductCode());
		}
		skuInventory.setAvailableQty(skuDto.getAvailableQty()==null ? 0 : Integer.valueOf(skuDto.getAvailableQty()));
		skuInventoryService.addOrUpdate(skuInventory);
		
		
		addMessage(redirectAttributes, "添加成功！");
		
		
		return "redirect:" +Global.getAdminPath() + "/store/sku/findList";
	}
	
	

	
	/**
	 * 
	 * @return 返回导入商品的页面
	 */
	@RequestMapping("importSku")
	public String importSku(){
		return "models/sku/storeProductExcel";
	}
	

	/**
	 * 批量导入商品
	 * @param excelPath
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/importSkuExcel",method = RequestMethod.POST)
	public String importSkuExcel(MultipartFile excelPath, ModelMap model, HttpSession session){
		//获取登录用户的信息
		StoreAuthUser auUser = (StoreAuthUser) session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(auUser == null){
			return "redirect:"+Global.getAdminPath()+"/login/index";
		}
		model.addAttribute("info", "操作完成");
		
		Sku sku = new Sku();
		sku.setStoreCode(auUser.getStoreCode());
		
		SkuInventory skuInventory = new SkuInventory();
		skuInventory.setStoreCode(auUser.getStoreCode());
		
		SkuPrice skuPrice = new SkuPrice();
		skuPrice.setStoreCode(auUser.getStoreCode());
		
		List<Sku> skuList = new ArrayList<Sku>();
		List<String> existSkuNo = new ArrayList<String>();
		
		try {
			ImportExcel ie = new ImportExcel(excelPath, 0, 0);
			for(int i = ie.getDataRowNum(); i<= ie.getLastDataRowNum(); i++){
				Row row = ie.getRow(i);
				
				//检验商品名字是否为空
				String name = ObjectUtils.toString(ie.getCellValue(row, 1));
				if(StringUtils.isBlank(name)){
					continue;
				}
				//检验读取到的skuNo是否为空，且在数据库中是否唯一
				String skuNo =  ObjectUtils.toString(ie.getCellValue(row, 2));
				
				if(ObjectUtils.notEqual(null, skuInfoService.queryBySkuNo(skuNo, auUser.getStoreCode()))){
					//将已存在的skuNo放入到集合中
					existSkuNo.add(skuNo);
					LOGGER.debug("该商品编号已存在：" + skuNo);
					continue;
				}
				sku.setName(name);
				sku.setSkuNo(skuNo);
				sku.setUniqueCode(RandomNumUtil.getCharacterAndNumber(20));
				sku.setStatus(SkuStatusEnum.SKU_STATUS_INIT.getStrVlue());
				
				
				
				//将商品信息存入商品表
				skuInfoService.insert(sku);
				//将存入数据库的商品加入到集合中
				skuList.add(sku);
				
				Sku sku1 = skuInfoService.queryBySkuNo(skuNo, auUser.getStoreCode());
				
				String currentPrice =  ObjectUtils.toString(ie.getCellValue(row, 3));
				if(currentPrice != null && currentPrice !=""){
					skuPrice.setCurrentPrice(NumberUtils.createBigDecimal(currentPrice));
					skuPrice.setSkuCode(sku1.getUniqueCode());
					skuPriceService.addOrUpdate(skuPrice);
				}
				String availableQty =  ObjectUtils.toString(ie.getCellValue(row, 4));
				if(availableQty != null && availableQty !=""){
					Integer qty = Integer.parseInt(availableQty);
					skuInventory.setAvailableQty(qty);
					skuInventory.setSkuCode(sku1.getUniqueCode());
					skuInventory.setSkuName(name);
					skuInventoryService.addOrUpdate(skuInventory);
				}
				model.addAttribute("rowNum", ie.getLastDataRowNum());
			}
			
		} catch (Exception e) {
			LOGGER.error("批量导入商品失败", e);
		}
		
		model.addAttribute("existSkuNo", existSkuNo);
		model.addAttribute("skuList", skuList);
		
		return "models/sku/importSuccess";
	}
	

}
