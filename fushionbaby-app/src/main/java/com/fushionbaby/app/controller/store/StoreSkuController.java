/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月12日下午3:39:27
 */
package com.fushionbaby.app.controller.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import util.ImageConstantFacade;

import com.fushionbaby.app.dto.store.StoreCategoryDto;
import com.fushionbaby.app.dto.store.StoreLabelDto;
import com.fushionbaby.app.dto.store.StoreSkuDto;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.util.RedisUtil;
import com.fushionbaby.common.condition.sku.SkuCategoryQueryCondition;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;
import com.fushionbaby.common.constants.AdvertisementConfigConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.PageConstant;
import com.fushionbaby.common.constants.SortTypeConstant;
import com.fushionbaby.common.dto.FocusPicDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.CartRedisUserService;
import com.fushionbaby.facade.biz.common.sku.BannerFacade;
import com.fushionbaby.facade.biz.common.sku.CategoryFacade;
import com.fushionbaby.member.model.MemberBrowesStoreHistory;
import com.fushionbaby.member.service.MemberBrowesStoreHistoryService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuCategory;
import com.fushionbaby.sku.model.SkuLabel;
import com.fushionbaby.sku.service.SkuCategoryService;
import com.fushionbaby.sku.service.SkuLabelRelationService;
import com.fushionbaby.sku.service.SkuLabelService;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月12日下午3:39:27
 */
@Controller
@RequestMapping("store/sku")
public class StoreSkuController extends BaseStoreController {
	private static final Log LOGGER = LogFactory.getLog(StoreSkuController.class);
	private static final String LABEL_TYPE="STORE";
	
	@Autowired
	private BannerFacade bannerFacade;
	
	@Autowired
	private SkuLabelService<SkuLabel> skuLabelService;
	
	@Autowired
	private SkuLabelRelationService skuLabelRelationService;
	
	@Autowired
	private CartRedisUserService<ShoppingCartSku> cartRedisService;
	
	@Autowired
	private CategoryFacade categoryFacade;
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	@Autowired
	private MemberBrowesStoreHistoryService<MemberBrowesStoreHistory> memberBrowesStoreHistoryService;
	
	private static RedisUtil redis = new RedisUtil();
	
	private static final String STORE_APP_HOME="STORE_APP_HOME";
	/**
	 * 商品查询
	 * @param searchName 
	 * @param storeCode 
	 * @param curPage
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping("storeSkuSearch")
	public Object storeSkuSearch(String searchName,
			@RequestParam(value="storeCode",defaultValue=StringUtils.EMPTY) String storeCode,
			@RequestParam(value="pageIndex",defaultValue=PageConstant.APP_PAGE_INDEX+"")Integer curPage,
			@RequestParam(value="pageSize",defaultValue=PageConstant.APP_PAGE_SIZE+"")Integer limit) {
		List<StoreSkuDto> storeSkuDtoList = new ArrayList<StoreSkuDto>();
		if(StringUtils.isBlank(searchName)){
			return Jsonp_data.success(storeSkuDtoList);
		}
		
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setStart((curPage - 1) * limit);
		queryCondition.setLimit(limit);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setStoreCode(storeCode);
		queryCondition.setName(searchName);
		
		List<Sku> skuList = skuService.queryByCondition(queryCondition);
		
		for (Sku sku : skuList) {
			storeSkuDtoList.add(this.getStoreSkuDto(sku));
		}
		
		return Jsonp_data.success(storeSkuDtoList);
	}
	
	/**
	 * 进入小区首页
	 * @param sid
	 * @param storeCode 门店编号
	 * @return
	 */
	@SuppressWarnings({ "static-access", "serial" })
	@ResponseBody
	@RequestMapping("enterStoreHome")
	public Object enterStoreHome(String visitKey,String sid,String storeCode){
		
		//清除购物车
		if(StringUtils.isBlank(sid) && StringUtils.isNotBlank(visitKey)){
			
			List<ShoppingCartSku> cartSkuList = cartRedisService.getListPage(visitKey, 0, -1);
			
			for (ShoppingCartSku shoppingCartSku : cartSkuList) {
				if(StringUtils.isNotBlank(shoppingCartSku.getStoreCode()) && !StringUtils.equals(shoppingCartSku.getStoreCode(),storeCode)){
					cartRedisService.delCartItem(visitKey, shoppingCartSku.getId());
				}
			}
		}
		/**用户已登录时，更新浏览门店记录表*/
		if(StringUtils.isNotBlank(sid)){
			UserDto userDto = (UserDto) SessionCache.get(sid);
			if(userDto != null){
				MemberBrowesStoreHistory memberBrowesStoreHistory = memberBrowesStoreHistoryService.
									findByAccountAndStoreCode(userDto.getAccount(), storeCode);
				if(memberBrowesStoreHistory != null){
					/**用户访问过该门店，则在访问次数上+1，并更新访问时间*/
					memberBrowesStoreHistory.setBrowesCount(memberBrowesStoreHistory.getBrowesCount() + 1);
					memberBrowesStoreHistoryService.update(memberBrowesStoreHistory);
				}else{
					/**用户没有访问过该门店，则设定各参数，创建访问记录*/
					memberBrowesStoreHistory = new MemberBrowesStoreHistory();
					memberBrowesStoreHistory.setBrowesCount(1);
					memberBrowesStoreHistory.setMemberAccount(userDto.getAccount());
					memberBrowesStoreHistory.setMemberId(userDto.getMemberId());
					memberBrowesStoreHistory.setStoreCode(storeCode);
					memberBrowesStoreHistoryService.add(memberBrowesStoreHistory);
				}
				
				}
			}
		
		Map<String, Object> index = new HashMap<String, Object>();
		Gson gson = new Gson();
		String key = "STORE_HOME_KEY_".toUpperCase() +storeCode;
		if (redis.hexists(STORE_APP_HOME,key)) {
			
			String jsonMap = redis.hget(STORE_APP_HOME,key);
			if(StringUtils.isNotBlank(jsonMap)){
				index = gson.fromJson(jsonMap, new TypeToken<Map<String, Object>>(){}.getType());
				return Jsonp_data.success( index);
			}
		}
		List<FocusPicDto> picList =  bannerFacade.getFocusPic(AdvertisementConfigConstant.APP_STORE_HOME_FOCUS);
		for (FocusPicDto focusPicDto : picList) {
			BeanNullConverUtil.nullConver(focusPicDto);
		}
		index.put("shopHours", super.getStoreConfigDto(storeCode).getShopHours());
		index.put("banner",picList);
		index.put("block",  super.getFunctionalBlock(super.STORE));
		index.put("sku", this.getStoreLabel(storeCode));
		String jsonStr = gson.toJson (index);
		redis.hset(STORE_APP_HOME, key, jsonStr);
		
		return Jsonp_data.success(index);
	}

	
	/**
	 * 获取门店的标签集合
	 * @param storeCode 门店编号
	 * @return
	 */
	private List<StoreLabelDto> getStoreLabel(String storeCode){
		
		
		List<StoreLabelDto> storeLabelDtoList = new ArrayList<StoreLabelDto>();
		if(StringUtils.isBlank(storeCode)){
			return storeLabelDtoList;
		}
		List<SkuLabel> listList = skuLabelService.getByType(LABEL_TYPE);
		if(CollectionUtils.isEmpty(listList)){
			return storeLabelDtoList;
		}
		
		for (SkuLabel skuLabel : listList) {
			
			List<StoreSkuDto> storeSkuList = this.getHomeLabelSkuList(skuLabel.getCode(),storeCode);
			if(storeSkuList==null){
				continue;
			}
			StoreLabelDto labelDto = new StoreLabelDto();
			labelDto.setLabelCode(skuLabel.getCode());
			labelDto.setLabelName(skuLabel.getName());
			labelDto.setStoreSkuList(storeSkuList);
			storeLabelDtoList.add(labelDto);
		}
		
		return storeLabelDtoList;
	}
	
	
	/**
	 * 获取首页标签下的商品集合
	 * @param count 显示数量
	 * @param labelCode 标签编号
	 * @param imageStandardCode 图片尺寸
	 * @return
	 */
    private List<StoreSkuDto> getHomeLabelSkuList(String labelCode,String storeCode){
		
    	List<StoreSkuDto> storeSkuDtoList = new ArrayList<StoreSkuDto>();
		SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();	
		
		queryCondition.setSortParam(SortTypeConstant.DEFAULT);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setLabelCode(labelCode);
		queryCondition.setDisabled(CommonConstant.YES);
		queryCondition.setStoreCode(storeCode);
		
		List<String> relUnCodes = skuLabelRelationService.querySkuUnCodeListByLabel(queryCondition);
		if (CollectionUtils.isEmpty(relUnCodes)) {
			return null;
		}
		List<Sku> skus = skuService.querySkuListByLabelRel(relUnCodes,storeCode);
		if(CollectionUtils.isEmpty(skus)){
			return null;
		}
		
		for (Sku sku : skus) {
			storeSkuDtoList.add(this.getStoreSkuDto(sku));
		}
		
		return  storeSkuDtoList;
	}
    
    
    /**
     * 获取分类列表
     * @param storeCode 分类编号
     * @return
     */
   @SuppressWarnings("serial")
   @ResponseBody
   @RequestMapping("getCategoryList")
   public Object getCategoryList(@RequestParam(value="storeCode") String storeCode){
	   
		List<StoreCategoryDto>  storeCategorys = new ArrayList<StoreCategoryDto>();
		if(StringUtils.isBlank(storeCode)){
		    return Jsonp_data.success( storeCategorys);
		}
		
		
		Gson gson = new Gson();
		String key = "STORE_CATEGORY_KEY_".toUpperCase() +storeCode;
		if (redis.hexists(STORE_APP_HOME,key)) {
			
			String jsonMap = redis.hget(STORE_APP_HOME,key);
			if(StringUtils.isNotBlank(jsonMap)){
				storeCategorys = gson.fromJson(jsonMap, new TypeToken<List<StoreCategoryDto>>(){}.getType());
				return Jsonp_data.success( storeCategorys);
			}
		}
		
	    SkuCategoryQueryCondition queryCondition = new SkuCategoryQueryCondition();
	    queryCondition.setStoreCode(storeCode);
		queryCondition.setCategoryLevel(1);
		queryCondition.setIsShow(CommonConstant.YES);
		List<SkuCategory> firstCategorys = skuCategoryService.findByCondition(queryCondition);
		storeCategorys = this.getStoreCategoryDto(storeCode, firstCategorys);
		
		String jsonStr = gson.toJson (storeCategorys);
		redis.hset(STORE_APP_HOME, key, jsonStr );
	    return Jsonp_data.success(storeCategorys);
   }
   
   /**
    * 
    * @param storeCode
    * @param categorys
    * @return
    */
	private List<StoreCategoryDto> getStoreCategoryDto(String storeCode,List<SkuCategory> categorys){
		
		List<StoreCategoryDto> cgdtos = new ArrayList<StoreCategoryDto>();
		for (SkuCategory skuCategory : categorys) {
			if (StringUtils.equals(skuCategory.getIsShow(), CommonConstant.NO)) {
				continue;
			}
			String code = skuCategory.getCode();
			StoreCategoryDto c = new StoreCategoryDto();
			c.setName(skuCategory.getName());
			c.setCode(code);
			c.setLevel(skuCategory.getCategoryLevel());
			c.setChildCategory(this.findChildCategorys(storeCode,code));
			if(StringUtils.isBlank( skuCategory.getCategoryLogoUrl()) && c.getChildCategory().size()==0){
				SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
				queryCondition.setCategoryCode(c.getCode());
				queryCondition.setStart(0);
				queryCondition.setLimit(1);
				queryCondition.setStoreCode(storeCode);
				List<Sku> skuList = skuService.queryByCondition(queryCondition);
				if(skuList!=null && skuList.size() > 0){
					c.setLogoUrl( imageProcess.getThumImagePath(skuList.get(0).getUniqueCode(),ImageStandardConstant.IMAGE_STANDARD_PC_300 ));
				}else{
					c.setLogoUrl(StringUtils.EMPTY);
				}
			}else{
				c.setLogoUrl(StringUtils.isNotBlank(skuCategory.getCategoryLogoUrl()) ? 
							ImageConstantFacade.IMAGE_SERVER_ROOT_PATH +"/"+ skuCategory.getCategoryLogoUrl() : "");
			}
			cgdtos.add(c);
		}
		
		return cgdtos;
	}
	/**
	 * 查询子分类
	 * @param storeCode 门店编号
	 * @param categoryCode 分类编号
	 * @return
	 */
	private List<StoreCategoryDto> findChildCategorys(String storeCode,String categoryCode){
		List<SkuCategory> childCategorys = skuCategoryService.findCategoryByGrandcategoryCode(storeCode,categoryCode);
				return this.getStoreCategoryDto(storeCode,childCategorys);
		
	}
	/**
	 * 分类下的商品集合
	 * @param storeCode 门店编号
	 * @param categoryCode 分类编号
	 * @param curPage
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping("categorySkuList")
	public Object categorySkuList(String storeCode,String categoryCode,
			@RequestParam(value="pageIndex",defaultValue=PageConstant.APP_PAGE_INDEX+"")Integer curPage,
			@RequestParam(value="pageSize",defaultValue=PageConstant.APP_PAGE_SIZE+"")Integer limit){
		
		
		List<StoreSkuDto> storeSkuDtoList = new ArrayList<StoreSkuDto>();
		
		List<String> categoryCodes = null;
		try {
			categoryCodes = skuCategoryService.getLowestChildCategorysByCode(storeCode,categoryCode);
		} catch (Exception e) {
			LOGGER.error("查询商品分类时出现异常，返回结果是：null！" + e.getMessage());
		}
		if(categoryCodes==null){
			return Jsonp_data.success(storeSkuDtoList);
		}
		
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setStoreCode(storeCode);
		queryCondition.setStart((curPage - 1) * limit);
		queryCondition.setLimit(limit);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setCategoryCodes(categoryCodes);

		List<Sku> skuList = skuService.queryByCondition(queryCondition);
		for (Sku sku : skuList) {
			storeSkuDtoList.add(this.getStoreSkuDto(sku));
		}
		
		return Jsonp_data.success(storeSkuDtoList);
	}
    
}
