package com.aladingshop.store.controller.sku;
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

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuBrand;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.model.SkuCategoryBrandRelation;
import com.aladingshop.sku.cms.model.SkuLabel;
import com.aladingshop.sku.cms.model.SkuLabelCategoryRelation;
import com.aladingshop.sku.cms.service.SkuBrandService;
import com.aladingshop.sku.cms.service.SkuCategoryBrandRelationService;
import com.aladingshop.sku.cms.service.SkuCategoryService;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.sku.cms.service.SkuLabelCategoryRelationService;
import com.aladingshop.sku.cms.service.SkuLabelService;
import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.auth.service.StoreAuthUserService;
import com.aladingshop.store.config.Global;
import com.aladingshop.store.dto.SkuCategoryDto;
import com.aladingshop.store.util.CMSSessionUtils;
import com.aladingshop.store.util.excel.ImportExcel;
import com.fushionbaby.cache.util.RedisUtil;
import com.fushionbaby.common.condition.sku.SkuCategoryQueryCondition;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SessionKeyConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author mengshaobo
 * 门店商品分类
 */
@Controller
@RequestMapping("storeSkuCategory")
public class SkuCategoryController {
	
	@Autowired
	private SkuInfoService skuInfoService ;
	
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
	@Autowired
	private StoreAuthUserService<StoreAuthUser> storeAuthUserService;
	
	private static RedisUtil redis = new RedisUtil();
	
	private static final Log LOGGER = LogFactory.getLog(SkuCategoryController.class);
	
	private static final String PRE_="models/sku/";
	

	/***
	 * 商品分类列表
	 * @param skuCategoryDto
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("findSkuCategoryList")
	public String skuCategoryList(SkuCategoryDto category,BasePagination page,HttpSession session,ModelMap model){
		try {
 			if (ObjectUtils.equals(page, null)) {
				page = new BasePagination();
			}
			StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
			if(ObjectUtils.equals(auUser, null)){
				return "redirect:"+Global.getAdminPath()+"/login/index";
			}
			//门店code是品牌查询的唯一参数
			String storeCode = auUser.getStoreCode();
			Map<String, String> params = new HashMap<String, String>();
			params.put("storeCode", storeCode);
			params.put("code", category.getCode());
			params.put("isShow", category.getIsShow());
			params.put("grandcategoryCode", category.getGrandcategoryCode());
			params.put("name", category.getName());
			params.put("categoryLevel", category.getCategoryLevel());
			page.setParams(params);
			
			page = skuCategoryService.getListPage(page);
			List<SkuCategoryDto> skuCategoryDtoList=new ArrayList<SkuCategoryDto>();
			//产品分类列表
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
				skuCategoryDtotmp.setName(skuCategory.getName());
				skuCategoryDtotmp.setCreateId(skuCategory.getCreateId());
				skuCategoryDtotmp.setCreateTime(skuCategory.getCreateTime());
				skuCategoryDtotmp.setKeywords(skuCategory.getKeywords());
				skuCategoryDtotmp.setShowOrder(skuCategory.getShowOrder());
				skuCategoryDtotmp.setUpdateId(skuCategory.getUpdateId());
				skuCategoryDtotmp.setUpdateTime(skuCategory.getUpdateTime());
				skuCategoryDtotmp.setVersion(skuCategory.getVersion());
				skuCategoryDtotmp.setIsShow(skuCategory.getIsShow());
				skuCategoryDtoList.add(skuCategoryDtotmp);
			}
			model.addAttribute("skuCategoryDtoList", skuCategoryDtoList);
			model.addAttribute("page", page);
			model.addAttribute("storeNumber", auUser.getStoreNumber());
			model.addAttribute("storeCode", storeCode);
			model.addAttribute("skuCategoryDto",category );
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("门店系统后台SkuCategoryController查询所有出错"+e);
		}
		return PRE_+"storeSkuCategoryList";
	}
	
	
	
	/**
	 * 跳转到添加一级页面
	 * @return
	 */
	@RequestMapping("gotoSkuCategoryAdd")
	public String gotoSkuCategoryAdd(){
		return PRE_+"skuCategoryAdd";
	}
	/**
	 * 跳转到添加子分类页面
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
			@RequestParam("oldCategoryCode") String oldCategoryCode,HttpSession session ){
		
		StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(auUser==null){
			return false;
		}
		
		SkuCategory skuCategory = skuCategoryService.findByCode(categoryCode,auUser.getStoreCode());
		if (ObjectUtils.equals(null,skuCategory.getId()) || skuCategory == null || Objects.equals(categoryCode, oldCategoryCode)) {
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
	public boolean isExistParentCategoryCode(@RequestParam("parentCategoryCode") String parentCategoryCode,HttpSession session){
		
		StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(auUser==null){
			return false;
		}
		if (StringUtils.equals(parentCategoryCode, "0")) {
			return true;
		}
		
		SkuCategory skuCategory = skuCategoryService.findByCode(parentCategoryCode,auUser.getStoreCode());
		if (ObjectUtils.equals(null,skuCategory.getId()) || skuCategory == null) {
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
			@RequestParam("parentCategoryCode") String parentCategoryCode,HttpSession session){
		StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(auUser==null){
			return "redirect:" +Global.getAdminPath() + "/login/index";
		}
		try {
			if (StringUtils.equals(parentCategoryCode, "0")) {
				return Jsonp_data.success(1);
			}
			SkuCategory skuCategory = skuCategoryService.findByCode(parentCategoryCode,auUser.getStoreCode());
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
	/**
	 * 新增分类
	 * @param jsonStr
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addSkuCategory", method = RequestMethod.POST)
	public Object addSkuCategory(String jsonStr,
			HttpSession session) {
		
		String baseNum = "00"; 
		try {
			StoreAuthUser authUser = CMSSessionUtils.getSessionUser(session);
			List<SkuCategory> skuCategorys = jsonConvertSkuCategoryList(jsonStr);
			
			for(SkuCategory  skuCategory : skuCategorys){
			
				skuCategory.setId(null);
				skuCategory.setCreateId(authUser.getId());
				skuCategory.setStoreCode(authUser.getStoreCode());
				skuCategory.setIsShow(skuCategory.getIsShow()); 
				if(skuCategory.getCategoryLevel() == 1){
					skuCategory.setCode(this.maxIndex(skuCategory.getCategoryLevel(), authUser.getStoreCode()) + "");
				}else{
					SkuCategoryQueryCondition queryCondition = new SkuCategoryQueryCondition();
					queryCondition.setGrandCode(skuCategory.getGrandcategoryCode());
					queryCondition.setStoreCode(authUser.getStoreCode());
					Integer total = skuCategoryService.queryTotalByCondition(queryCondition);
					if(total > 99){
						return Jsonp.error("一个分类下最多只能添加99个子分类");
					}
					queryCondition.setCategoryLevel(skuCategory.getCategoryLevel());
					String maxCode = skuCategoryService.findMaxCategoryCode(queryCondition);
					if(StringUtils.isBlank(maxCode)){
						skuCategory.setCode(Long.valueOf(skuCategory.getGrandcategoryCode() + baseNum) +   "1");
					}else{
						skuCategory.setCode(maxCode);
					}
				}
				
				skuCategoryService.add(skuCategory);
			
			}
			
			redis.hdel("STORE_APP_HOME", "STORE_CATEGORY_KEY_".toUpperCase() +authUser.getStoreCode());
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("添加分类信息失败:" + e);
			return Jsonp.error();
		}
		return Jsonp.success();
	}
	
	/**
	 * 获取该门店下分类数量 
	 * @param level 等级
	 * @param storeCode 编号
	 * @return
	 */
	private String maxIndex(Integer level,String storeCode){
		
		SkuCategoryQueryCondition queryCondition = new SkuCategoryQueryCondition();
		queryCondition.setCategoryLevel(level);
		queryCondition.setStoreCode(storeCode);
		
		String maxCode = skuCategoryService.findMaxCategoryCode(queryCondition);
				
		if(StringUtils.isBlank(maxCode)){
			return "1000";
		}
		
		
		return maxCode;
		
	}
/**
 * 跳转到分类修改页面
 * @param categoryId
 * @param model
 * @return
 */
	@RequestMapping("gotoSkuCategoryUpdate")
	public String gotoSkuCategoryUpdate(@RequestParam("categoryId") Long categoryId,
			ModelMap model) {
		try {
			SkuCategory skuCategory = skuCategoryService.findById(categoryId);
			model.addAttribute("skuCategory", skuCategory);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("跳转到分类修改页面失败:" + e);
		}
		return PRE_+"skuCategoryUpdate";
	}
	/**
	 * 修改分类
	 * @param jsonStr
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateSkuCategory", method = RequestMethod.POST)
	public Object updateSkuCategory(String jsonStr,HttpSession session) {
		try {
			StoreAuthUser authUser = CMSSessionUtils.getSessionUser(session);
			SkuCategory skuCategory = jsonConvertSkuCategory(jsonStr);
			SkuCategory findCategory = skuCategoryService.findById(skuCategory.getId());
			findCategory.setUpdateId(authUser.getId());
			findCategory.setStoreCode(authUser.getStoreCode());
			findCategory.setName(skuCategory.getName());
			findCategory.setShowOrder(skuCategory.getShowOrder());
			findCategory.setIsShow(skuCategory.getIsShow());
			
			skuCategoryService.update(findCategory);
			
			redis.hdel("STORE_APP_HOME", "STORE_CATEGORY_KEY_".toUpperCase() +authUser.getStoreCode());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("修改分类信息失败:" + e);
			return  Jsonp.error();
		}
		return Jsonp.success();
	}
	
	private SkuCategory jsonConvertSkuCategory(String jsonStr){
//		SkuCategory skuCategory = new Gson().fromJson(jsonStr, SkuCategory.class);
		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(jsonStr);
		SkuCategory skuCategory = new SkuCategory();
		
		if(jsonStr.contains("id")){
			String id = json.getAsJsonObject().get("id").getAsString();
			skuCategory.setId(StringUtils.isBlank(id)?null : Long.valueOf(id));
		}
		if(jsonStr.contains("categoryLevel")){
			skuCategory.setCategoryLevel(Integer.valueOf(json.getAsJsonObject().get("categoryLevel").getAsString()));
		}
		if(jsonStr.contains("code")){
			skuCategory.setCode( json.getAsJsonObject().get("code").getAsString());
		}
		if(jsonStr.contains("grandcategoryCode")){
			skuCategory.setGrandcategoryCode( json.getAsJsonObject().get("grandcategoryCode").getAsString());
		}
		if(jsonStr.contains("isShow")){
			String isShow =	json.getAsJsonObject().get("isShow").getAsString();
			skuCategory.setIsShow(StringUtils.isBlank(isShow) ? CommonConstant.YES : isShow);
		}else{
			skuCategory.setIsShow(CommonConstant.YES);
		}
		if(jsonStr.contains("keywords")){
			skuCategory.setKeywords(json.getAsJsonObject().get("keywords").getAsString());
		}
		if(jsonStr.contains("name")){
			skuCategory.setName(json.getAsJsonObject().get("name").getAsString());
		}
		if(jsonStr.contains("showOrder")){
			String showOrder = json.getAsJsonObject().get("showOrder").getAsString();
			skuCategory.setShowOrder(StringUtils.isBlank(showOrder) ? null :Integer.valueOf(showOrder));
		}
		
		
		return skuCategory;
	}
	
	
	private List<SkuCategory> jsonConvertSkuCategoryList(String jsonStr){
		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(jsonStr);
	   List<SkuCategory> categorys = new ArrayList<SkuCategory>();
		SkuCategory convertCategory = this.jsonConvertSkuCategory(jsonStr);
		categorys.add(convertCategory);
		if(jsonStr.contains("otherName") && json.getAsJsonObject().get("otherName").isJsonArray()){
			
			JsonArray arr = json.getAsJsonObject().get("otherName").getAsJsonArray();
			if(arr!=null && arr.size() > 0){
				
				for (JsonElement jsonElement : arr) {
					String name = jsonElement.getAsString();
					if(StringUtils.isBlank(name)){
						continue;
					}
					SkuCategory  newCategory = new SkuCategory();
					newCategory.setCategoryLevel(convertCategory.getCategoryLevel());
					newCategory.setIsShow(convertCategory.getIsShow());
					newCategory.setGrandcategoryCode(convertCategory.getGrandcategoryCode());
					newCategory.setName(name);
					categorys.add(newCategory);
				}
			}
			
		}
		return categorys;
		
	}
	
	/**
	 * 导入商品分类页
	 * @param model
	 * @return
	 */
	@RequestMapping("batchSkuCategory")
	public String batchSkuCategory(Model model){
		model.addAttribute("title","商品分类导入" );
		model.addAttribute("action", "storeSkuCategory/importSkuCategoryExcel");
		return "models/import/import";
	}
	
	/**
	 *  批量导入商品分类
	 * @param excelFile
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("importSkuCategoryExcel")
	public String importSkuPriceExcel(MultipartFile excelFile, ModelMap model, HttpSession session) {
		model.addAttribute("info", "导入成功");
		StoreAuthUser user = CMSSessionUtils.getSessionUser(session);
		int count = 0;
		int rowNum = 0;
		
		String baseNum="01";
		try {
			ImportExcel ei = new ImportExcel(excelFile, 0, 0);
			for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {

				Row row = ei.getRow(i);
				String skuCategoryCode = ObjectUtils.toString(ei.getCellValue(row, 0));
				if (StringUtils.isBlank(skuCategoryCode)) {
					continue;
				}
				rowNum++;
				SkuCategory skuCategory=this.skuCategoryService.findByCode(skuCategoryCode,user.getStoreCode());
				if(skuCategory==null)
				{
					skuCategory=new SkuCategory();
				}
				
				String skuCategoryName = ObjectUtils.toString(ei.getCellValue(row, 1));
				String preSkuCategoryCode = ObjectUtils.toString(ei.getCellValue(row, 2));
				String categoryLevel = ObjectUtils.toString(ei.getCellValue(row, 3));
				skuCategory.setCategoryLevel(Integer.valueOf(categoryLevel));
				skuCategory.setCreateId(user.getId());
				skuCategory.setCreateTime(new Date());
				skuCategory.setIsShow(CommonConstant.YES);
				skuCategory.setGrandcategoryCode(preSkuCategoryCode);
				skuCategory.setName(skuCategoryName);
				skuCategory.setUpdateId(user.getId());
				skuCategory.setStoreCode(user.getStoreCode());
				skuCategory.setUpdateTime(new Date());
				skuCategory.setVersion(new Date());
				
				if(skuCategory.getCategoryLevel() == 1){
					skuCategory.setCode(this.maxIndex(skuCategory.getCategoryLevel(), user.getStoreCode()) + "");
				}else{
					SkuCategoryQueryCondition queryCondition = new SkuCategoryQueryCondition();
					queryCondition.setGrandCode(skuCategory.getGrandcategoryCode());
					queryCondition.setStoreCode(user.getStoreCode());
					Integer total = skuCategoryService.queryTotalByCondition(queryCondition);
					if(total > 99){
						model.addAttribute("info","一个分类下最多只能添加99个子分类");
						break;
					}
					queryCondition.setCategoryLevel(skuCategory.getCategoryLevel());
					String maxCode = skuCategoryService.findMaxCategoryCode(queryCondition);
					if(StringUtils.isBlank(maxCode)){
						skuCategory.setCode(Long.valueOf(skuCategory.getGrandcategoryCode() + baseNum)  + "");
					}else{
						skuCategory.setCode(maxCode);
					}
				}
				
				if(skuCategory.getId()!=null){
					this.skuCategoryService.update(skuCategory);
				}else{
					this.skuCategoryService.add(skuCategory);
				}
				count++;
			}
			
			redis.hdel("STORE_APP_HOME", "STORE_CATEGORY_KEY_".toUpperCase() +user.getStoreCode());
		} catch (Exception e) {
			model.addAttribute("info", "导入失败");
			e.printStackTrace();
		}
		model.addAttribute("rowNum", rowNum);// Excel 中总记录数
		model.addAttribute("count", count);// 添加的记录数量
		model.addAttribute("title", "商品分类批量导入");
		return "models/import/importResult";
	}
	/**
	 * 删除分类
	 * @param categoryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delSkuCategory")
	public Object delSkuCategory(Long categoryId){
		
		SkuCategory skuCategory = skuCategoryService.findById(categoryId);
		
		if(skuCategory==null){
			return Jsonp.error("该分类不存在!");
		}
		if(StringUtils.isBlank(skuCategory.getStoreCode()) || StringUtils.isBlank(skuCategory.getCode())){
			return Jsonp.error("该分类数据有误，无法删除!");
		}
		
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setCategoryCode(skuCategory.getCode());
		queryCondition.setStoreCode(skuCategory.getStoreCode());
		
		List<Sku> skus = skuInfoService.queryByCondition(queryCondition);
		if(!CollectionUtils.isEmpty(skus) && skus.size()>0){
			return Jsonp.error("该分类下有商品，无法删除，请先把该分类下的商品移除");
		}
		
		List<SkuCategory> grandCategorys = skuCategoryService.findCategoryByGrandcategoryCode(skuCategory.getCode(), skuCategory.getStoreCode());
		
		if(!CollectionUtils.isEmpty(grandCategorys) && grandCategorys.size()>0){
			return Jsonp.error("该分类下有子分类，无法删除，请先把该分类下的子分类移除");
		}
		
		try {
			skuCategoryService.deleteById(categoryId);
			redis.hdel("STORE_APP_HOME", "STORE_CATEGORY_KEY_".toUpperCase() +skuCategory.getStoreCode());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return Jsonp.error();
		}
		
		return Jsonp.success();
	}
	
	
	@RequestMapping("batchAddSkuCategory")
	public Object batchAddSkuCategory(){
		
		
		
		return "models/sku/skuCategoryEditBatch";
	}
	
}
