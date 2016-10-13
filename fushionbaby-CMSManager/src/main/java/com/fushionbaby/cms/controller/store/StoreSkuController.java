package com.fushionbaby.cms.controller.store;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.model.SkuExtraInfo;
import com.aladingshop.sku.cms.model.SkuImage;
import com.aladingshop.sku.cms.model.SkuInventory;
import com.aladingshop.sku.cms.model.SkuLabel;
import com.aladingshop.sku.cms.model.SkuLabelRelation;
import com.aladingshop.sku.cms.model.SkuPrice;
import com.aladingshop.sku.cms.service.SkuCategoryService;
import com.aladingshop.sku.cms.service.SkuExtraInfoService;
import com.aladingshop.sku.cms.service.SkuImageService;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.sku.cms.service.SkuInventoryService;
import com.aladingshop.sku.cms.service.SkuLabelRelationService;
import com.aladingshop.sku.cms.service.SkuLabelService;
import com.aladingshop.sku.cms.service.SkuPriceService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.excel.ImportExcel;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.RandomNumUtil;

/**
 * 
 * @description 类描述...
 * @author 孟少博
 * @date 2016年1月5日上午11:46:50
 */
@Controller
@RequestMapping("/store/sku")
public class StoreSkuController extends BaseController{

	@Autowired
	private SkuInfoService skuInfoService;
	
	@Autowired
	private SkuPriceService<SkuPrice> skuPriceService;
	
	@Autowired
	private  SkuExtraInfoService<SkuExtraInfo> extraInfoService;
	
	@Autowired
	private SkuInventoryService<SkuInventory> inventoryService;
	
	@Autowired
	private SkuImageService<SkuImage> skuImageService;
	
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	
	@Autowired
	private SkuLabelRelationService<SkuLabelRelation>  skuLabelRelationService;
	
	@Autowired
	private SkuLabelService<SkuLabel> skuLabelService;
	
	private final static String STORE = "STORE";
	
	
	/**
	 * 进入批量导入商品页
	 * @param storeCode
	 * @param model
	 * @return
	 */
	@RequestMapping("batchSkuModel")
	public Object batchSkuModel(String storeCode,Model model){
		
		model.addAttribute("storeCode", storeCode);
		
		return "models/store/importSku";
	}
	/**
	 * 批量添加商品
	 * @param addNum
	 * @param storeCode
	 * @param excelPath
	 * @param model
	 * @param session
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "/batchAddSku", method = RequestMethod.POST)
	public String batchAddSku(Integer addNum,String storeCode,MultipartFile excelPath, ModelMap model, HttpSession session) throws Throwable {
		
		model.addAttribute("info", "操作完成");
		if(excelPath==null){
			
			model.addAttribute("info", "没有导入的数据");
			return "models/store/importResult";
		}
		
		int goRow = 0;
		
		Set<String> categorySet = new HashSet<String>(); 
		try {
			ImportExcel ei = new ImportExcel(excelPath, 0, 0);
			int rowNum = ei.getLastDataRowNum();
			List<Integer> rnums =   this.randomNum(rowNum,addNum);
			
			for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {
				if(!rnums.contains(i)){
					continue;
				}
				goRow = i;
				Row row = ei.getRow(i);
				String skuName = ObjectUtils.toString(ei.getCellValue(row, 0));
				String imgUrl = ObjectUtils.toString(ei.getCellValue(row, 1));
				if(StringUtils.isNotBlank(imgUrl)){
					imgUrl = imgUrl.replace("sku", "sku-test") ;
				}
				
				
			    String currentPrice = ObjectUtils.toString(ei.getCellValue(row, 2));
			    String categoryCode = ObjectUtils.toString(ei.getCellValue(row, 3));
			    if(StringUtils.isNotBlank(categoryCode) && categoryCode.contains(".")){
			    	categoryCode = 	categoryCode.substring(0,categoryCode.indexOf("."));
			    }
			    
			    if(StringUtils.isBlank(skuName)  || StringUtils.isBlank(currentPrice) ){
			    	continue;
			    }
			    if(StringUtils.isNotBlank(categoryCode)){
			    	categorySet.add(categoryCode);
			    }
			    String uniqueCode = RandomNumUtil.getCharacterAndNumber(20);
			    Sku sku = new Sku();
			    sku.setName(skuName);
			    sku.setStoreCode(storeCode);
			    sku.setQuotaNum(0);
			    sku.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
			    sku.setUniqueCode(uniqueCode);
			    sku.setCategoryCode(categoryCode);
			    skuInfoService.addOrUpdate(sku);
			    SkuPrice skuPrice = new SkuPrice();
			    skuPrice.setSkuCode(sku.getUniqueCode());
			    skuPrice.setStoreCode(storeCode);
			    skuPrice.setCurrentPrice(new BigDecimal( currentPrice));
			    skuPriceService.add(skuPrice);
			    this.addInitExtraInfo(uniqueCode,storeCode);
			    this.addInitInventory(sku);
			    SkuImage skuImage = new SkuImage();
			    skuImage.setStoreCode(storeCode);
			    skuImage.setImgUrl(imgUrl);
			    skuImage.setSkuCode(uniqueCode);
			    skuImageService.add(skuImage);
			}
			
		
		}catch (Exception e) {
			model.addAttribute("info", "操作失败,第"+(goRow+1)+"行数据出错");
			e.printStackTrace();
		}finally{
			this.createCategory(new ArrayList<String>(categorySet), storeCode);
			this.addLabelSku(storeCode);
		}
		
		return "models/store/importResult";
	}
	
	private void addLabelSku(String storeCode){
		
		BasePagination page = new BasePagination();
		page.setCurrentPage(1);
		page.setLimit(10);
		Map<String, String> params = new HashMap<String, String>();
		params.put("storeCode", storeCode);
		page.setParams(params);
		
		BasePagination result = skuInfoService.getPageList(page);
		if(result==null || CollectionUtils.isEmpty(result.getResult())){
			return ;
		}
		@SuppressWarnings("unchecked")
		List<Sku> skuList = (List<Sku>)result.getResult();
		
		List<SkuLabel> skuLabels = skuLabelService.findByType(STORE);
		if(skuLabels==null || skuLabels.size()<1){
			return ;
		}
		
		for (Sku sku : skuList) {
			SkuLabelRelation skuLabelRelation = new SkuLabelRelation();
			skuLabelRelation.setSkuCode(sku.getUniqueCode());
			skuLabelRelation.setStoreCode(storeCode);
			skuLabelRelation.setLabelCode(skuLabels.get(0).getCode());
			skuLabelRelation.setDisabled(CommonConstant.YES);
			skuLabelRelationService.add(skuLabelRelation);
		}
				
	}
	
	/**
	 * 创建分类
	 * @param categoryCodeList 分类编号集合
	 * @param storeCode 门店编号
	 */
	private void createCategory(List<String> categoryCodeList,String storeCode){
		if(CollectionUtils.isEmpty(categoryCodeList)){
			return;
		}
		
		for (String code : categoryCodeList) {
			String categoryCode = null;
			if(code.contains(".")){
				categoryCode = code.substring(0,code.indexOf("."));
			}else{
				categoryCode = code;
			}
			SkuCategory skuCategory  = skuCategoryService.findByCode(categoryCode, null);
			if(skuCategory==null || skuCategory.getCategoryLevel()==null){
				continue;
			}
			if( StringUtils.isNotBlank(skuCategory.getCode())){
				skuCategory.setId(null);
				skuCategory.setIsShow(CommonConstant.YES);
				skuCategory.setStoreCode(storeCode);
				skuCategoryService.add(skuCategory);
				if(skuCategory.getCategoryLevel()>1){
					this.createParentCategory(skuCategory.getGrandcategoryCode(), storeCode);
				}
			}
		}
		
	}
	/***
	 * 创建父分类
	 * @param grandCateoryCode 父分类编号
	 * @param storeCode 门店编号
	 */
	private void createParentCategory(String grandCateoryCode,String storeCode){
		
		SkuCategory grandCategory = skuCategoryService.findByCode(grandCateoryCode, storeCode);
		if(grandCategory ==null || grandCategory.getId()== null || StringUtils.isBlank(grandCategory.getCode())){
			SkuCategory grandCategoryStore = skuCategoryService.findByCode(grandCateoryCode, null);
			if(grandCategoryStore!=null && StringUtils.isNotBlank(grandCategoryStore.getCode())){
				grandCategoryStore.setId(null);
				grandCategoryStore.setStoreCode(storeCode);
				grandCategoryStore.setIsShow(CommonConstant.YES);
				skuCategoryService.add(grandCategoryStore);
				if(grandCategoryStore.getCategoryLevel()!=null && grandCategoryStore.getCategoryLevel()!=1){
					this.createParentCategory(grandCategoryStore.getGrandcategoryCode(), storeCode);
				}
			}
		}
		
	}
	
	

	private void addInitExtraInfo(String skuUnCode,String storeCode) {
		SkuExtraInfo skuExtraInfo = extraInfoService.findBySkuCode(skuUnCode);
		if (skuExtraInfo != null) {
			return;
		}
		skuExtraInfo = new SkuExtraInfo();
		BeanNullConverUtil.nullConver(skuExtraInfo);
		skuExtraInfo.setId(null);
		skuExtraInfo.setHasDiscount(CommonConstant.NO);
		skuExtraInfo.setHasGift(CommonConstant.NO);
		skuExtraInfo.setIsGift(CommonConstant.NO);
		skuExtraInfo.setIsVideo(CommonConstant.NO);
		skuExtraInfo.setSkuCode(skuUnCode);
		skuExtraInfo.setStoreCode(storeCode);
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
		skuInventory.setAvailableQty(100);
		skuInventory.setSkuName(sku.getName());
		skuInventory.setSkuCode(sku.getUniqueCode());
		skuInventory.setStoreCode(sku.getStoreCode());
		inventoryService.add(skuInventory);
	}
	
	/**
	 * 随机出100条数据
	 * @param num
	 * @return
	 */
	private List<Integer> randomNum(int num,Integer addNum){
		
		int imNum = 10;
		if(addNum!=null && addNum!=0){
			imNum = addNum;
		}
		List<Integer> nums = new ArrayList<Integer>();
		
		int maxNum = (num<imNum?num:imNum);
		
		while(true)	    {
			int rnum =  (int) (Math.random()*maxNum+1);
			if(nums.contains(rnum)){
				continue;
			}
			nums.add(rnum);
			if(nums.size()>=maxNum){
				break;
			}
	    }
		
		Collections.sort(nums);
		return nums;
	}
	
}
