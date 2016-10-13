/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月5日上午10:14:45
 */
package com.fushionbaby.cms.controller.sku;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuBrand;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.model.SkuExtraInfo;
import com.aladingshop.sku.cms.model.SkuImage;
import com.aladingshop.sku.cms.model.SkuInventory;
import com.aladingshop.sku.cms.model.SkuKeyWord;
import com.aladingshop.sku.cms.model.SkuPrice;
import com.aladingshop.sku.cms.model.SkuProductAttr;
import com.aladingshop.sku.cms.service.SkuProductAttrService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cache.service.GlobalConfig;
import com.fushionbaby.cms.controller.sku.excel.SkuInfoExcelReport;
import com.fushionbaby.cms.dto.SkuDto;
import com.fushionbaby.cms.dto.SkuInfoDto;
import com.fushionbaby.cms.util.BatchUploadFiles;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.RegUtils;
import com.fushionbaby.cms.util.bean.BatchUploadImageResult;
import com.fushionbaby.cms.util.bean.FileInfo;
import com.fushionbaby.cms.util.bean.UploadResult;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.cms.util.excel.ImportExcel;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月5日上午10:14:45
 */
@Controller
@RequestMapping("sku")
public class SkuBatchOperateController extends SkuBaseController {

	
	private static final Log LOGGER = LogFactory.getLog(SkuBatchOperateController.class);
	
	@Autowired
	private GlobalConfig globalConfig;
	
	@Autowired
	private SkuProductAttrService<SkuProductAttr> skuProductAttrService;
	

	/***
	 * 批量导入商品价格页
	 * 
	 * @param skuBrandDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("batchSkuPrice")
	public String selectSkuBrandList() {
		return "models/sku/skuPriceExcel";
	}
	
	
	@RequestMapping("importSkuPriceExcel")
	public String importSkuPriceExcel(MultipartFile excelFile, ModelMap model, HttpSession session) {
		model.addAttribute("info", "导入成功");
		AuthUser user = CMSSessionUtils.getSessionUser(session);
		List<String> notExitBarCodes = new ArrayList<String>(); 
		List<SkuPrice> skuPriceList = new ArrayList<SkuPrice>();
				
				
		try {
			ImportExcel ei = new ImportExcel(excelFile, 0, 0);
			for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {

				Row row = ei.getRow(i);
				String barCode = ObjectUtils.toString(ei.getCellValue(row, 0));
				if (StringUtils.isBlank(barCode)) {
					continue;
				}
				Sku sku = skuService.queryByBarCode(barCode,null);
				if (ObjectUtils.equals(null, sku)) {
					LOGGER.info("该条形码的商品不存在:" + barCode);
					notExitBarCodes.add(barCode);
					continue;
				}
				
				String costPrice = ObjectUtils.toString(ei.getCellValue(row, 1));
				String currentPrice = ObjectUtils.toString(ei.getCellValue(row, 2));
				String aladingPrice = ObjectUtils.toString(ei.getCellValue(row, 3));
				
				
				SkuPrice skuPrice = skuPriceService.findBySkuCode(sku.getUniqueCode());
				if (skuPrice == null) {
					skuPrice = new SkuPrice();
				}
				skuPrice.setStoreCode(CommonConstant.STORE_CODE_SHOP);
				skuPrice.setCreateId(user.getId());
				skuPrice.setSkuCode(sku.getUniqueCode());
				skuPrice.setCostPrice(NumberUtils.createBigDecimal(costPrice));
				skuPrice.setCurrentPrice(NumberUtils.createBigDecimal(currentPrice));
				skuPrice.setAladingPrice(NumberUtils.createBigDecimal(aladingPrice));
				skuPriceList.add(skuPrice);
				
				if (skuPrice.getId() != null) {
					skuPriceService.update(skuPrice);
				} else {
					skuPriceService.add(skuPrice);
				}
				skuService.update(sku);
			}
			
			
		} catch (Exception e) {
			model.addAttribute("info", "导入失败");
			e.printStackTrace();
		}
		model.addAttribute("results", skuPriceList);// 添加的记录数量
		model.addAttribute("notExitBarCodes", notExitBarCodes);// 不存在的商品
		return "models/product/batch-success";
	}
	
	/***
	 * 批量上传图片页
	 * 
	 * @param skuBrandDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("initBatchImages")
	public String initBatchImagesPage() {
		return "models/sku/skuBatchImages";
	}

	
	/**
	 * 批量导入商品图片
	 * 
	 * @param state
	 * @param memberId
	 * @param orderCode
	 */
	@ResponseBody
	@RequestMapping(value = "batchImages", method = RequestMethod.POST)
	public Object batchImages(@RequestParam("rootPath") String rootPath) {
		// FTP上传结果集合
		List<UploadResult> results = new ArrayList<UploadResult>();
		List<UploadResult> tempResults = new ArrayList<UploadResult>();
		// 上传全部文件全路径集合
		List<String> allFileAbs = new ArrayList<String>();
		// 验证地址是否正确
		File file = new File(rootPath);
		if (!file.exists()) {
			return new BatchUploadImageResult(Constant.PATH_EXIST);
		}

		FileInfo fileInfo = new FileInfo(rootPath, "/userfiles/images/sku/", "jpgpngjpeggif");

		BatchUploadFiles.getAllFiles(allFileAbs, fileInfo, results);

		Map<String, List<String>> allFiles = convertMap(allFileAbs);
		for (Entry<String, List<String>> entry : allFiles.entrySet()) {
			if (this.validSkuImage(entry, results)) {
				tempResults = BatchUploadFiles.BatchUpload(entry.getValue(), fileInfo.getRemotePath());
				// 插入db
				addSkuImages(tempResults, entry.getKey());
				results.addAll(tempResults);
			}
		}

		return this.getBatchUploadResult(results);
	}

	private boolean validSkuImage(Entry<String, List<String>> entry, List<UploadResult> results) {
		Integer maxCount = Integer.valueOf(globalConfig.findByCode(GlobalConfigConstant.WEB_UPDETAIL_IMG_COUNT));
		Sku currentSku = skuService.queryByBarCode(entry.getKey(),null);
		if (currentSku == null) {
			this.getUncodeNotExist(results, entry.getValue());
			return false;
		}

		List<SkuImage> skuImages = skuImageService.findBySkuCode(currentSku.getUniqueCode());
		int historyLen = skuImages == null ? 0 : skuImages.size();
		int count = entry.getValue().size() + historyLen;
		if (count <= maxCount) {
			return true;
		} else if (maxCount - historyLen > 0) {
			entry.setValue(entry.getValue().subList(0, maxCount - historyLen));
			this.getCountLimit(results, entry.getValue().subList(maxCount - historyLen, entry.getValue().size()));
			return true;
		}

		this.getCountLimit(results, entry.getValue());
		return false;
	}

	private BatchUploadImageResult getBatchUploadResult(List<UploadResult> results) {
		List<UploadResult> errorList = new ArrayList<UploadResult>();
		Integer falCount = 0;
		for (UploadResult result : results) {
			if (Objects.equals(result.getIsSuccess(), Constant.UPLOAD_FAILURE)) {
				errorList.add(result);
				falCount++;
			}
		}

		BatchUploadFiles.deleteSucImage(results);
		return new BatchUploadImageResult(results.size(), results.size() - falCount, falCount, errorList);
	}
	
	
	
	/**
	 * 成功上传的图片集合入库
	 * 
	 * @param results
	 * @param skuUnCode
	 */
	private void addSkuImages(List<UploadResult> results, String skuNo) {
		// 根据code获取到uncode
		String skuUnCode = skuService.queryByBarCode(skuNo,null).getUniqueCode();

		for (UploadResult result : results) {
			if (Objects.equals(result.getIsSuccess(), Constant.UPLOAD_FAILURE)) {
				continue;
			}

			SkuImage skuImage = new SkuImage();
			skuImage.setSkuCode(skuUnCode);
			skuImage.setStoreCode(CommonConstant.STORE_CODE_SHOP);
			skuImage.setImgUrl(BatchUploadFiles.getDicAbsPath(new String[] { "sku" }) + "/" + result.getFileNameAfter());
			try {
				skuImageService.add(skuImage);
			} catch (DataAccessException e) {
				result.setIsSuccess(Constant.UPLOAD_FAILURE);
				result.setErrorDesc(Constant.OPEATION_DB_ERROR);
			}
		}
	}

	/**
	 * 封装商品uncode不存在的结果数据
	 * 
	 * @param results
	 * @param fileabs
	 */
	private void getUncodeNotExist(List<UploadResult> results, List<String> fileabs) {
		for (String abs : fileabs) {
			results.add(new UploadResult(Constant.UPLOAD_FAILURE, Constant.UNCODE_NOT_EXIST, abs.replace(
					BatchUploadFiles.getFileName(abs), ""), BatchUploadFiles.getFileName(abs)));
		}
	}

	/**
	 * 封装商品上传详情图超上限结果数据
	 * 
	 * @param results
	 * @param fileabs
	 */
	private void getCountLimit(List<UploadResult> results, List<String> fileabs) {
		for (String abs : fileabs) {
			results.add(new UploadResult(Constant.UPLOAD_FAILURE, Constant.UPLIAD_COUNT_LIMIT, abs.replace(
					BatchUploadFiles.getFileName(abs), ""), BatchUploadFiles.getFileName(abs)));
		}
	}

	/**
	 * 封装全部上传图片文件为map(key:sku_un_code, value:fileAbsPath)
	 * 
	 * @param allFilesAbs
	 * @return
	 */
	private Map<String, List<String>> convertMap(List<String> allFilesAbs) {
		Map<String, List<String>> resultMap = new HashMap<String, List<String>>();

		for (String currentAbs : allFilesAbs) {
			String skUnCode = BatchUploadFiles.getFileBeforDic(currentAbs);
			List<String> absFiles;
			if (!resultMap.containsKey(skUnCode)) {
				absFiles = new ArrayList<String>();
				absFiles.add(currentAbs);
			} else {
				absFiles = resultMap.get(skUnCode);
				absFiles.add(currentAbs);
			}

			resultMap.put(skUnCode, absFiles);
		}

		return resultMap;
	}

	/***
	 * 批量上传图片结果展示
	 * 
	 * @param skuBrandDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("showUploadImageResult")
	public String initBatchImagesPage(@RequestParam("jsonStr") String jsonStr, ModelMap modelMap) {
		try {
			String convertStr = URLDecoder.decode(jsonStr, "utf-8");
			Gson gson = new Gson();
			BatchUploadImageResult result = gson.fromJson(convertStr, new TypeToken<BatchUploadImageResult>() {
			}.getType());
			modelMap.addAttribute("result", result);
		} catch (Exception e) {
			return "models/error/error";
		}

		return "models/sku/skuBatchImagesResult";
	}
	
	
	
	/**
	 * 导出Excel
	 * @param skuDto
	 * @param redirectAttributes
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "/exportSkuInfoExcel", method = RequestMethod.POST)
	public void exportSkuInfoExcel(SkuDto skuDto, RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletResponse response) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("productCode", skuDto.getProductCode());
			params.put("storeCode", CommonConstant.STORE_CODE_SHOP);
			params.put("barCode", skuDto.getBarCode());
			params.put("skuNo", skuDto.getSkuNo());
			params.put("name", skuDto.getSkuName());
			params.put("status", skuDto.getStatus());
			params.put("createTimeFrom", skuDto.getCreateTimeFrom());
			params.put("createTimeTo", skuDto.getCreateTimeTo());
			List<Sku> list = skuService.findExcelAll(params);
			List<SkuInfoDto> listDto = new ArrayList<SkuInfoDto>();
			for (int i = 0; i < list.size(); i++) {
				Sku sku = list.get(i);
				SkuInfoDto skuInfoDto = new SkuInfoDto();
				SkuPrice skuPrice = skuPriceService.findBySkuCode(sku.getUniqueCode());
				SkuCategory skuCategory = skuCategoryService.findByCode(sku.getCategoryCode(),null);
				SkuBrand skuBrand = skuBrandService.findByBrandCode(sku.getBrandCode());
				skuInfoDto.setCode(sku.getSkuNo());
				skuInfoDto.setBarCode(sku.getBarCode());
				if (skuBrand != null) {
					skuInfoDto.setBrandName(skuBrand.getBrandName());
				}
				if (skuPrice != null) {
					skuInfoDto.setCostPric(skuPrice.getCostPrice());
					skuInfoDto.setCurrentPrice(skuPrice.getCurrentPrice());
					skuInfoDto.setAladingPric(skuPrice.getAladingPrice());
				}
				if (skuCategory != null) {
					skuInfoDto.setCategoryName(skuCategory.getName());
				}
				skuInfoDto.setColor(sku.getColor());
				skuInfoDto.setName(sku.getName());
				skuInfoDto.setSize(sku.getSize());
				listDto.add(skuInfoDto);
			}
			SkuInfoExcelReport skuInfoExcel = new SkuInfoExcelReport();
			skuInfoExcel.getReport("商品信息", listDto, response);
		} catch (Exception e) {
			LOGGER.error("商品信息导出Excel失败" + e.getMessage(), e);
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * 批量导入产品
	 * 
	 * @param excelPath
	 *            地址
	 * @param model
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/batchAddProduct", method = RequestMethod.POST)
	public String batchAddProduct(MultipartFile excelPath, ModelMap model, HttpSession session) throws Throwable {
		model.addAttribute("info", "操作完成");
		AuthUser user = CMSSessionUtils.getSessionUser(session);
		List<String> exitBarCodes = new ArrayList<String>();
		List<String> exitSkuNos = new ArrayList<String>();
		List<Sku> skuList = new ArrayList<Sku>();
		try {
			ImportExcel ei = new ImportExcel(excelPath, 0, 0);
			for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {
				Row row = ei.getRow(i);
				String productCode = ObjectUtils.toString(ei.getCellValue(row, 0));
				if (StringUtils.isBlank(productCode)) {
					continue;
				}
				String skuName = ObjectUtils.toString(ei.getCellValue(row, 1));
				
				String skuNo = ObjectUtils.toString(ei.getCellValue(row, 2));
				Sku s = skuService.queryBySkuNo(skuNo,null);
				if (!ObjectUtils.equals(s, null)) {
					exitSkuNos.add(skuNo);
					LOGGER.debug("该商品编号或条形码已存在：" + skuNo);
					continue;
				}
				
				String barCode = ObjectUtils.toString(ei.getCellValue(row, 3));
				s = skuService.queryByBarCode(barCode,null);
				if (!ObjectUtils.equals(s, null)) {
					exitBarCodes.add(barCode);
					LOGGER.debug("该商品编号或条形码已存在：" + s.getBarCode());
					continue;
				}
				String skuColor = ObjectUtils.toString(ei.getCellValue(row, 4));
				String skuSize = ObjectUtils.toString(ei.getCellValue(row, 5));
				String skuDesc = ObjectUtils.toString(ei.getCellValue(row, 6));
				String brandCode = ObjectUtils.toString(ei.getCellValue(row, 7));
				String categoryCode = ObjectUtils.toString(ei.getCellValue(row, 8));
				String quotaNum = ObjectUtils.toString(ei.getCellValue(row, 9));
				String showorder = ObjectUtils.toString(ei.getCellValue(row, 10));
				Sku sku = new Sku();
				sku.setUniqueCode(RandomNumUtil.getCharacterAndNumber(20));
				sku.setBrandCode(brandCode);
				sku.setCategoryCode(categoryCode);
				sku.setName(skuName);
				sku.setSkuNo(skuNo);
				sku.setBarCode(barCode);
				sku.setColor(skuColor);
				sku.setDescription(skuDesc);
				sku.setSize(skuSize);
				sku.setProductCode(productCode);
				sku.setQuotaNum(NumberUtils.toInt(quotaNum));
				sku.setShoworder(NumberUtils.toInt(showorder));
				sku.setCreateId(user.getId());
				sku.setStatus(SkuStatusEnum.SKU_STATUS_INIT.getStrVlue());
				sku.setStoreCode(CommonConstant.STORE_CODE_SHOP);
			
				skuList.add(sku);
				this.addInitExtraInfo(sku.getUniqueCode());
				this.addInitInventory(sku);
				skuService.addOrUpdate(sku);
			}

		
		} catch (Exception e) {
			model.addAttribute("info", "操作失败");
			e.printStackTrace();
		}
		
		model.addAttribute("results", skuList);
		model.addAttribute("exitSkuNos",exitSkuNos);// 已存在的商品编号集合
		model.addAttribute("exitBarCodes",exitBarCodes);// 已存在的商品条形码集合
		return "models/product/batch-success";
	}

	private void addInitExtraInfo(String skuUnCode) {
		SkuExtraInfo skuExtraInfo = extraInfoService.findBySkuCode(skuUnCode);
		if (skuExtraInfo != null) {
			return;
		}
		skuExtraInfo = new SkuExtraInfo();
		BeanNullConverUtil.nullConver(skuExtraInfo);
		skuExtraInfo.setId(null);
		skuExtraInfo.setStoreCode(CommonConstant.STORE_CODE_SHOP);
		skuExtraInfo.setHasDiscount(CommonConstant.NO);
		skuExtraInfo.setHasGift(CommonConstant.NO);
		skuExtraInfo.setIsGift(CommonConstant.NO);
		skuExtraInfo.setIsVideo(CommonConstant.NO);
		skuExtraInfo.setSkuCode(skuUnCode);
		extraInfoService.add(skuExtraInfo);
	}

	private void addInitInventory(Sku sku) {
		SkuInventory skuInventory = inventoryService.findBySkuCode(sku.getUniqueCode());
		if (skuInventory != null) {
			return;
		}
		skuInventory = new SkuInventory();
		BeanNullConverUtil.nullConver(skuInventory);
		skuInventory.setId(null);
		skuInventory.setSkuName(sku.getName());
		skuInventory.setSkuColor(sku.getColor());
		skuInventory.setSkuSize(sku.getSize());
		skuInventory.setSkuCode(sku.getUniqueCode());
		skuInventory.setProductCode(sku.getProductCode());
		skuInventory.setStoreCode(CommonConstant.STORE_CODE_SHOP);
		inventoryService.add(skuInventory);
	}

	/**
	 * 跳到批量导入产品详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/batchProductImageModel")
	public String batchProductImageModel() {
		return "models/product/productImage";
	}
	
	
	/**
	 * 批量添加产品属性
	 * 
	 * @param excelPath
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/batchAddProductAttr", method = RequestMethod.POST)
	public String batchAddProductAttr(MultipartFile excelPath, HttpSession session, ModelMap model) {
		model.addAttribute("info", "操作完成");
		try {
			AuthUser user = CMSSessionUtils.getSessionUser(session);
			ImportExcel ei = new ImportExcel(excelPath, 0, 0);
			for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {
				Row row = ei.getRow(i);
				String productCode = ObjectUtils.toString(ei.getCellValue(row, 0));
				if (StringUtils.isBlank(productCode)) {
					continue;
				}
				String attrName = ObjectUtils.toString(ei.getCellValue(row, 1));
				String attrValue = ObjectUtils.toString(ei.getCellValue(row, 2));
				String showOrder = ObjectUtils.toString(ei.getCellValue(row, 3));
				SkuProductAttr attr = new SkuProductAttr();
				attr.setProductCode(productCode);
				attr.setShowOrder(StringUtils.isNotBlank(showOrder) ? NumberUtils.toInt(showOrder) : 0);
				attr.setAttrName(attrName);
				attr.setAttrValue(attrValue);
				attr.setCreateId(user.getId());
				skuProductAttrService.add(attr);
			}

		} catch (InvalidFormatException e) {
			model.addAttribute("info", "操作失败");
			e.printStackTrace();
		} catch (IOException e) {
			model.addAttribute("info", "操作失败");
			e.printStackTrace();
		}catch (Exception e) {
			model.addAttribute("info", "文件格式不正确");
			e.printStackTrace();
		}

		return "models/product/batch-success";
	}
	
	
	
	/***
	 * 批量上传关键词
	 * 
	 * @param skuBrandDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("initBatchKeyWords")
	public String initBatchKeyWords() {
		return "models/product/proBatchKeyWords";
	}

	/**
	 * 批量导入商品关键词
	 * 
	 * @param excelFile
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("importSkuKeyWordExcel")
	public String importSkuKeyWordExcel(MultipartFile excelFile, ModelMap model, HttpSession session) {
		/** 获取当前登录用户信息 */
		AuthUser user = CMSSessionUtils.getSessionUser(session);
		model.addAttribute("info", "导入");
		List<String> validProNos = new ArrayList<String>();
		try {
			ImportExcel ei = new ImportExcel(excelFile, 0, 0);
			for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {

				Row row = ei.getRow(i);
				String proCode = ObjectUtils.toString(ei.getCellValue(row, 0));
				/** 如果为纯数字，则需要去掉小数点 */
				if (RegUtils.isInteger(proCode) && proCode.indexOf(".") != -1) {
					proCode = proCode.substring(0, proCode.indexOf("."));
				}
				if (StringUtils.isBlank(proCode)) {
					continue;
				}

				String keyWords = ObjectUtils.toString(ei.getCellValue(row, 1)).replace("，", ",");
				/** 关键字验证 */
				if (StringUtils.isBlank(keyWords) || StringUtils.isBlank(keyWords.replace(",", ""))
						|| RegUtils.isSpecialSymbol(keyWords.replace(",", "")) || !RegUtils.lenValid(5, ",", keyWords)) {
					LOGGER.info("该商品关键词为空或格式不正确:" + proCode);
					validProNos.add(proCode);
					continue;
				}

				SkuKeyWord currentWord = skuKeyWordService.findByProductCode(proCode);
				SkuKeyWord opeWord = new SkuKeyWord(proCode, keyWords);
				if (currentWord == null) {
					opeWord.setCreateId(user.getId());
					skuKeyWordService.addKey(opeWord);
				} else {
					opeWord.setUpdateId(user.getId());
					skuKeyWordService.updateKey(currentWord);
				}
			}
		} catch (Exception e) {
			model.addAttribute("info", "导入失败");
			logger.error("导入商品关键词EXCEL错误：" + e);
		}
		model.addAttribute("validProNos", validProNos);// 关键字不合法的数据
		return "models/product/batch-success";
	}

	
}
