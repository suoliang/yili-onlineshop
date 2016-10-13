package com.fushionbaby.cms.controller.sku;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

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
import com.aladingshop.sku.cms.model.SkuBrand;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.model.SkuExtraInfo;
import com.aladingshop.sku.cms.model.SkuInventory;
import com.aladingshop.sku.cms.model.SkuKeyWord;
import com.aladingshop.sku.cms.model.SkuPrice;
import com.aladingshop.sku.cms.model.SkuProductAttr;
import com.aladingshop.sku.cms.model.SkuProductImage;
import com.aladingshop.sku.cms.service.SkuBrandService;
import com.aladingshop.sku.cms.service.SkuCategoryService;
import com.aladingshop.sku.cms.service.SkuExtraInfoService;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.sku.cms.service.SkuInventoryService;
import com.aladingshop.sku.cms.service.SkuKeyWordService;
import com.aladingshop.sku.cms.service.SkuPriceService;
import com.aladingshop.sku.cms.service.SkuProductAttrService;
import com.aladingshop.sku.cms.service.SkuProductImageService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cache.service.GlobalConfig;
import com.fushionbaby.cms.config.FileUtils;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.dto.AttrDto;
import com.fushionbaby.cms.dto.SkuDto;
import com.fushionbaby.cms.dto.SkuProductDto;
import com.fushionbaby.cms.util.BatchUploadFiles;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.ImagePathUtil;
import com.fushionbaby.cms.util.ImageUploadUtil;
import com.fushionbaby.cms.util.bean.BatchUploadImageResult;
import com.fushionbaby.cms.util.bean.FileInfo;
import com.fushionbaby.cms.util.bean.UploadResult;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.RandomNumUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author 孟少博 商品产品
 */
@Controller
@RequestMapping("/sku")
public class SkuProductController extends SkuBaseController {

	@Autowired
	private SkuInfoService skuInfoService;
	@Autowired
	private SkuProductImageService<SkuProductImage> skuProductImgService;
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> extraInfoService;
	@Autowired
	private SkuPriceService<SkuPrice> priceService;
	@Autowired
	private SkuInventoryService<SkuInventory> inventoryService;
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	@Autowired
	private SkuBrandService<SkuBrand> skuBrandService;
	@Autowired
	private SkuProductAttrService<SkuProductAttr> skuProductAttrService;
	@Autowired
	private GlobalConfig globalConfig;
	@Autowired
	private SkuKeyWordService<SkuKeyWord> skuKeyWordService;

	private static final Log LOGGER = LogFactory.getLog(SkuProductController.class);
	private static final String FTP_PATH = "/userfiles/images/sku/products";
	private static final String FTP_TYPE = "jpgpngjpeggificon";

	/** 重定向到产品首页列表 */
	private static final String REDURECT_URL = "redirect:" + Global.getAdminPath() + "/sku/skuProductList";


	/**
	 * 产品添加页面
	 * 
	 * @param model
	 * @version 2015-6-15
	 * @return
	 */
	@RequestMapping(value = "/productAddPage", method = RequestMethod.GET)
	public String productAddPage(ModelMap model) {
		model.addAttribute("pageType", 1);/* 设置页面为添加页 */
		model.addAttribute("allCategoryJson", this.getCategoryJson());
		return "models/product/productEdit";
	}



	/**
	 * 添加商品
	 * 
	 * @param graphicDetails
	 *            图文详情
	 * @param skus
	 *            商品集合
	 * @param attrs
	 *            属性集合
	 * @param skuProduct
	 *            产品信息
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
	public String saveProduct(@RequestParam("graphicDetails") String graphicDetails,
			@RequestParam("skus") List<String> skus, @RequestParam("attrs") List<String> attrs,
			SkuProductDto skuProduct, HttpSession session, RedirectAttributes redirectAttributes) {
		addMessage(redirectAttributes, "添加成功");
		try {
			AuthUser auUser = CMSSessionUtils.getSessionUser(session);
			String pcode = skuProduct.getCode();
			this.addProductAttr(pcode, attrs);
			List<SkuDto> skuDtos = this.getCurrentSkuDtoList(skus);
			this.addOrUpdateSkuInfo(skuProduct, skuDtos, null, auUser);
			List<String> graphicDetailsList = ImagePathUtil.getImageNameList(graphicDetails);

			for (String imagePath : graphicDetailsList) {
				SkuProductImage productImage = new SkuProductImage();
				productImage.setProductCode(pcode);
				productImage.setImgPath(imagePath);
				productImage.setIsDisable("y");/* 默认为显示 */
				productImage.setCreateId(auUser.getId());
				skuProductImgService.add(productImage);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			addMessage(redirectAttributes, "添加失败");
		}

		return REDURECT_URL;
	}

	/**
	 * 修改商品
	 * 
	 * @param graphicDetails
	 *            图文详情
	 * @param skus
	 *            商品集合
	 * @param attrs
	 *            属性集合
	 * @param skuProduct
	 *            产品信息
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
	public String updateProduct(@RequestParam("graphicDetails") String graphicDetails,
			@RequestParam("skus") List<String> skus, @RequestParam("attrs") List<String> attrs, int currentPage,
			int limit, int total, SkuProductDto skuProduct, String skuNos, HttpSession session,
			RedirectAttributes redirectAttributes) {

		redirectAttributes.addAttribute("currentPage", currentPage);
		redirectAttributes.addAttribute("limit", limit);
		redirectAttributes.addAttribute("total", total);
		addMessage(redirectAttributes, "修改成功");
		try {
			AuthUser user = CMSSessionUtils.getSessionUser(session);
			String pcode = skuProduct.getCode();
			this.updateProductImage(graphicDetails, pcode, user);
			this.addProductAttr(pcode, attrs);
			String[] skuNoArr = StringUtils.split(skuNos, ",");
			this.removeSku(skuNoArr);
			List<SkuDto> skuDtos = this.getCurrentSkuDtoList(skus);
			this.addOrUpdateSkuInfo(skuProduct, skuDtos, skuNoArr, user);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			addMessage(redirectAttributes, "修改失败");
		}

		return REDURECT_URL;

	}


	/**
	 * 添加或修改商品信息
	 * 
	 * @param pcode
	 *            产品编号
	 * @param skus
	 *            商品集合
	 * 
	 */
	private void addOrUpdateSkuInfo(SkuProductDto productDto, List<SkuDto> skuDtos, String[] skuNoArr, AuthUser user) {
		List<String> removeskuNo = new ArrayList<String>();
		if (skuNoArr != null) {
			Collections.addAll(removeskuNo, skuNoArr);
		}

		for (SkuDto skuDto : skuDtos) {
			if (removeskuNo.contains(skuDto.getSkuNo())) {
				return;
			}
			String uniqueCode = RandomNumUtil.getCharacterAndNumber(20);
			String skuNo = skuDto.getSkuNo();
			Sku sku = skuInfoService.queryBySkuNo(skuNo,null);
			if (sku == null) {
				sku = new Sku();
				sku.setUniqueCode(uniqueCode);
				sku.setCreateId(user.getId());
				sku.setStatus(SkuStatusEnum.SKU_STATUS_INIT.getStrVlue());
			} else {
				uniqueCode = sku.getUniqueCode();
				sku.setUpdateId(user.getId());
			}
			sku.setStoreCode(CommonConstant.STORE_CODE_SHOP);
			sku.setBrandCode(productDto.getBrandCode());
			sku.setCategoryCode(productDto.getCategoryCode());
			sku.setSkuNo(skuNo);
			sku.setBarCode(skuDto.getBarCode());
			sku.setColor(skuDto.getColor());
			sku.setSize(skuDto.getSize());
			sku.setName(skuDto.getSkuName());
			sku.setDescription(skuDto.getSkuDescription());
			sku.setProductCode(productDto.getCode());
			sku.setQuotaNum(Integer.valueOf(skuDto.getQuotaNum()));
			skuInfoService.addOrUpdate(sku);
			SkuPrice skuPrice = priceService.findBySkuCode(uniqueCode);
			if (skuPrice == null) {
				skuPrice = new SkuPrice();
				skuPrice.setCreateId(user.getId());
				skuPrice.setSkuCode(uniqueCode);
				skuPrice.setStoreCode(CommonConstant.STORE_CODE_SHOP);
			} else {
				skuPrice.setUpdateId(user.getId());
			}

		/*	skuPrice.setCostPrice(skuDto.getCostPrice());
			skuPrice.setCurrentPrice(skuDto.getCurrentPrice());
			skuPrice.setMarketPrice(skuDto.getMarketPrice());
			skuPrice.setPrePrice(skuDto.getPrePrice());
			skuPrice.setRetailPrice(skuDto.getRetailPrice());
			skuPrice.setAladingPrice(skuDto.getAladingPrice());*/
			//TODO

			SkuExtraInfo skuExtraInfo = extraInfoService.findBySkuCode(uniqueCode);
			if (skuExtraInfo == null) {
				skuExtraInfo = new SkuExtraInfo();
				skuExtraInfo.setStoreCode(CommonConstant.STORE_CODE_SHOP);
				skuExtraInfo.setSalesVolume(0);
				skuExtraInfo.setActualSalesVolume(0);
				skuExtraInfo.setCommentCount(0);
				skuExtraInfo.setCreateId(user.getId());
				skuExtraInfo.setSkuCode(uniqueCode);
			} else {
				skuExtraInfo.setUpdateId(user.getId());
			}
			/*skuExtraInfo.setHasDiscount(StringUtils.isBlank(skuDto.getHasDiscount()) ? CommonConstant.NO : skuDto
					.getHasDiscount());
			skuExtraInfo.setIsGift(StringUtils.isBlank(skuDto.getIsGift()) ? CommonConstant.NO : skuDto.getIsGift());
			skuExtraInfo.setHasGift(StringUtils.isBlank(skuDto.getHasGift()) ? CommonConstant.NO : skuDto.getHasGift());
			skuExtraInfo.setIsVideo(StringUtils.isBlank(skuDto.getIsVideo()) ? CommonConstant.NO : skuDto.getIsVideo());
			skuExtraInfo.setVideoUrl(skuDto.getVideoUrl());*/

			SkuInventory skuInventory = inventoryService.findBySkuCode(uniqueCode);
			if (skuInventory == null) {
				skuInventory = new SkuInventory();
				skuInventory.setStoreCode(CommonConstant.STORE_CODE_SHOP);
				skuInventory.setAvailableQty(0);
				skuInventory.setSkuCode(uniqueCode);
			}
			skuInventory.setProductCode(productDto.getCode());

			skuInventory.setSkuColor(skuDto.getColor());
			skuInventory.setSkuSize(skuDto.getSize());

			priceService.addOrUpdate(skuPrice);
			extraInfoService.addOrUpdate(skuExtraInfo);
			inventoryService.addOrUpdate(skuInventory);

		}

	}

	/**
	 * 添加产品属性
	 * 
	 * @param pcode
	 *            产品编号
	 * @param attrs
	 *            属性集合 1.先删除该产品下的所以产品属性<br>
	 *            2.添加产品属性<br>
	 */
	private void addProductAttr(String pcode, List<String> attrs) {
		skuProductAttrService.delAttrByProductCode(pcode);
		List<AttrDto> attrDtos = this.getAttrDtoList(attrs);
		for (AttrDto attrDto : attrDtos) {
			SkuProductAttr attr = new SkuProductAttr();
			attr.setStoreCode(CommonConstant.STORE_CODE_SHOP);
			attr.setAttrName(attrDto.getAttrName());
			attr.setAttrValue(attrDto.getAttrValue());
			attr.setProductCode(pcode);
			attr.setShowOrder(attrDto.getShowOrder());
			skuProductAttrService.add(attr);
		}

	}

	/**
	 * 移除商品行
	 * 
	 * @param skuNos
	 *            商品编号集合
	 */
	private void removeSku(String[] skuNoArr) {
		if (skuNoArr == null) {
			return;
		}
		for (String skuNo : skuNoArr) {
			if (StringUtils.isBlank(skuNo)) {
				continue;
			}
			Sku sku = skuInfoService.queryBySkuNo(skuNo,null);
			if (ObjectUtils.notEqual(null, sku)) {
				skuInfoService.deleteById(sku.getId());
				extraInfoService.deleteBySkuCode(sku.getUniqueCode());
				SkuInventory skuInventory = inventoryService.findBySkuCode(sku.getUniqueCode());
				if (skuInventory != null) {
					inventoryService.deleteById(skuInventory.getId());
				}
			}

		}
	}

	/**
	 * 修改图片
	 * 
	 * @param graphicDetails
	 *            图片集合
	 * @param pCode
	 *            产品编号
	 */
	private void updateProductImage(String graphicDetails, String pCode, AuthUser user) {
		if (StringUtils.isBlank(graphicDetails)) {
			List<SkuProductImage> pimgs = skuProductImgService.findAllBySkuProductCode(pCode);
			for (SkuProductImage skuProductImage : pimgs) {
				FileUtils.deleteQuietly(new File(Global.getImagePath() + skuProductImage.getImgPath())); // 删除图片
			}
			skuProductImgService.deleteByProductCode(pCode);
			return;
		}
		List<String> newImages = ImagePathUtil.getImageNameList(graphicDetails);/* 上传的新图片 */
		List<SkuProductImage> beforeImages = skuProductImgService.findBySkuProductCode(pCode);/* 之前的图片 */
		List<String> beforeImageUrls = new ArrayList<String>();
		for (SkuProductImage bi : beforeImages) {
			beforeImageUrls.add(bi.getImgPath());
		}
		List<String> eqImages = ImageUploadUtil.getEqImages(beforeImageUrls, newImages);
		List<String> removeImages = ImageUploadUtil.removeImages(beforeImageUrls, eqImages);
		for (String ri : removeImages) {
			for (SkuProductImage bi : beforeImages) {
				if (StringUtils.equals(ri, bi.getImgPath())) {
					SkuProductImage i = skuProductImgService.findById(bi.getId());
					if (ObjectUtils.notEqual(null, i)) {
						skuProductImgService.deleteById(i.getId());
						// FileUtils.deleteQuietly(new
						// File(Global.IMAGE_PHPS_PATH+beforeImage.getImgUrl()));
						// //删除图片
						BatchUploadFiles.delFtpImage(BatchUploadFiles.getFileName(i.getImgPath()), FTP_PATH + "/"
								+ BatchUploadFiles.getFileBeforDic(i.getImgPath())); // FTP删除
					}
				}
			}
		}

		Set<String> newImageSet = ImageUploadUtil.getNewImageSet(newImages, eqImages);
		if (CollectionUtils.isEmpty(newImageSet)) {
			return;
		}
		for (String n : newImageSet) {
			SkuProductImage productImage = new SkuProductImage();
			productImage.setProductCode(pCode);
			productImage.setImgPath(n);
			productImage.setIsDisable("y");/* 默认为显示 */
			productImage.setCreateId(user.getId());
			skuProductImgService.add(productImage);
		}

	}

	/**
	 * 
	 * 获取商品信息集合
	 * 
	 * @param skus
	 * @version 2015-6-16
	 * @return
	 */
	private List<SkuDto> getCurrentSkuDtoList(List<String> skus) {
		List<SkuDto> skuDtos = new ArrayList<SkuDto>();
		for (String sku : skus) {
			if (StringUtils.isBlank(sku)) {
				continue;
			}
			SkuDto skuDto = new Gson().fromJson(sku, SkuDto.class);
			skuDtos.add(skuDto);
		}
		return skuDtos;
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



	/***
	 * 跳转到批量导入产品信息窗口
	 * 
	 * @return
	 */
	@RequestMapping("/batchProductModel")
	public String batchProductModel() {
		return "models/product/productExcel";
	}

	

	/**
	 * 批量产品详情图片
	 * 
	 * @param path
	 * @param model
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/batchUploadProductImage", method = RequestMethod.POST)
	public Object batchUploadProductImage(String rootPath, ModelMap model, HttpSession session) {
		model.addAttribute("info", "操作成功");
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

		try {
			AuthUser user = CMSSessionUtils.getSessionUser(session);

			FileInfo info = new FileInfo(rootPath, FTP_PATH, FTP_TYPE);

			BatchUploadFiles.getAllFiles(allFileAbs, info, results);

			Map<String, List<String>> allFiles = this.convertMap(allFileAbs);

			for (Entry<String, List<String>> entry : allFiles.entrySet()) {
				// 校验当前skuUnCode是否存在于数据库
				if (this.validSkuProImage(entry, results)) {
					tempResults = BatchUploadFiles.BatchUpload(entry.getValue(), info.getRemotePath());
					// 插入db
					this.addProductImages(tempResults, entry.getKey(), user);
					results.addAll(tempResults);
				}
			}

		} catch (Exception e) {
			model.addAttribute("info", "操作失败");
			e.printStackTrace();
		}

		return this.getBatchUploadResult(results);
	}

	private boolean validSkuProImage(Entry<String, List<String>> entry, List<UploadResult> results) {
		Integer maxCount = Integer.valueOf(globalConfig.findByCode(GlobalConfigConstant.WEB_UPPRODUCT_IMG_COUNT));
		
		
		List<Sku> currentPro = skuInfoService.queryByProductCode(entry.getKey());
		if (currentPro == null) {
			this.getproductCodeNotExist(results, entry.getValue());
			return false;
		}
		List<SkuProductImage> skuImages = skuProductImgService.findAllBySkuProductCode(currentPro.get(0).getProductCode());
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

	/**
	 * 封装产品上传图超上限结果数据
	 * 
	 * @param results
	 * @param fileabs
	 */
	private void getCountLimit(List<UploadResult> results, List<String> fileabs) {
		for (String abs : fileabs) {
			results.add(new UploadResult(Constant.UPLOAD_FAILURE, Constant.UPPRO_COUNT_LIMIT, abs.replace(
					BatchUploadFiles.getFileName(abs), ""), BatchUploadFiles.getFileName(abs)));
		}
	}

	/**
	 * 批量上传图片后返回的结果
	 * 
	 * @param results
	 * @return
	 */
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
	 * 封装商品productCode不存在的结果数据
	 * 
	 * @param results
	 * @param fileabs
	 */
	private void getproductCodeNotExist(List<UploadResult> results, List<String> fileabs) {
		for (String abs : fileabs) {
			results.add(new UploadResult(Constant.UPLOAD_FAILURE, Constant.PRODUCTCODE_NOT_EXIST, abs.replace(
					BatchUploadFiles.getFileName(abs), ""), BatchUploadFiles.getFileName(abs)));
		}
	}

	/***
	 * 批量上传图片
	 * 
	 * @param skuBrandDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("showUploadProductImageResult")
	public String initBatchProductImagesPage(@RequestParam("jsonStr") String jsonStr, ModelMap modelMap) {
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
	 * 成功上传的图片集合入库
	 * 
	 * @param results
	 * @param skuUnCode
	 */
	private void addProductImages(List<UploadResult> results, String productCode, AuthUser user) {
		int i = 0;
		for (UploadResult result : results) {
			if (Objects.equals(result.getIsSuccess(), Constant.FAILURE)) {
				continue;
			}

			SkuProductImage image = new SkuProductImage();
			image.setProductCode(productCode);
			image.setIsDisable("y");
			image.setCreateId(user.getId());
			image.setImgPath(BatchUploadFiles.getDicAbsPath(new String[] { "sku", "products" }) + "/"
					+ result.getFileNameAfter());
			image.setSortOrder(i++);
			
			try {
				skuProductImgService.add(image);
			} catch (DataAccessException e) {
				result.setIsSuccess(Constant.UPLOAD_FAILURE);
				result.setErrorDesc(Constant.OPEATION_DB_ERROR);
			}
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
			String productCode = BatchUploadFiles.getFileBeforDic(currentAbs);
			List<String> absFiles;
			if (!resultMap.containsKey(productCode)) {
				absFiles = new ArrayList<String>();
				absFiles.add(currentAbs);
			} else {
				absFiles = resultMap.get(productCode);
				absFiles.add(currentAbs);
			}

			resultMap.put(productCode, absFiles);
		}

		return resultMap;
	}

	/**
	 * 跳到产品属性导入窗口
	 * 
	 * @return
	 */
	@RequestMapping("/batchProductAttrModel")
	public String batchProductAttrModel() {
		return "models/product/productAttrExcel";
	}



	/**
	 * 查询商品和产品关键字
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findProKeywords/{code}", method = RequestMethod.GET)
	public String findSkuKeywords(@PathVariable String code, Model model) {
		SkuKeyWord word = skuKeyWordService.findByProductCode(code);
		if (word == null) {
			word = new SkuKeyWord();
			word.setProductCode(code);
		}

		model.addAttribute("word", word);

		return "models/product/proKeywordsEdit";
	}

	@ResponseBody
	@RequestMapping(value = "editProKeywords", method = RequestMethod.POST)
	public Object editSkuKeywords(HttpSession session, String productCode, String keywords) {
		try {
			SkuKeyWord keyWord = skuKeyWordService.findByProductCode(productCode);
			SkuKeyWord opeWord = new SkuKeyWord(productCode, keywords);
			/** 获取当前登录用户信息 */
			AuthUser user = CMSSessionUtils.getSessionUser(session);
			if (keyWord == null) {
				/** 如果为null则添加 */
				opeWord.setCreateId(user.getId());
				skuKeyWordService.addKey(opeWord);
			} else {
				opeWord.setUpdateId(user.getId());
				skuKeyWordService.updateKey(opeWord);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}