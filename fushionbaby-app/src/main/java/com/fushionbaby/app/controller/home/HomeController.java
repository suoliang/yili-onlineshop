package com.fushionbaby.app.controller.home;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import util.ImageConstantFacade;

import com.fushionbaby.app.dto.FunctionalBlockDto;
import com.fushionbaby.app.dto.HomeCategoryDto;
import com.fushionbaby.app.dto.HomeLabelDto;
import com.fushionbaby.cache.service.GlobalConfig;
import com.fushionbaby.cache.util.RedisUtil;
import com.fushionbaby.common.condition.sku.SkuLabelQueryCondition;
import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;
import com.fushionbaby.common.constants.AdvertisementConfigConstant;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.LabelConstant;
import com.fushionbaby.common.constants.PageConstant;
import com.fushionbaby.common.constants.SortTypeConstant;
import com.fushionbaby.common.dto.FocusPicDto;
import com.fushionbaby.common.dto.sku.CategoryDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.sku.BannerFacade;
import com.fushionbaby.facade.biz.common.sku.CategoryFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.sku.model.SkuCategory;
import com.fushionbaby.sku.model.SkuCategoryImage;
import com.fushionbaby.sku.model.SkuLabel;
import com.fushionbaby.sku.model.SkuLabelImage;
import com.fushionbaby.sku.service.SkuCategoryImageService;
import com.fushionbaby.sku.service.SkuCategoryService;
import com.fushionbaby.sku.service.SkuLabelImageService;
import com.fushionbaby.sku.service.SkuLabelService;
import com.fushionbaby.sysmgr.model.SysmgrAppHomeConfig;
import com.fushionbaby.sysmgr.model.SysmgrFunctionalBlock;
import com.fushionbaby.sysmgr.service.SysmgrAppHomeConfigService;
import com.fushionbaby.sysmgr.service.SysmgrFunctionalBlockService;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
/**
 * 用户信息业务相关模块
 * 
 * @author mengshaobo
 */
@Controller
@RequestMapping("/home")
public class HomeController   {
	
	@Autowired
	private BannerFacade bannerFacade;
	
	@Autowired
	private SkuFacade skuFacade;	
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	
	@Autowired
	private SkuCategoryImageService<SkuCategoryImage> skuCategoryImageService;
	
	@Autowired
	private SkuLabelImageService<SkuLabelImage> skuLabelImageService;
	
	@Autowired
	private SkuLabelService<SkuLabel> skuLabelService;
	
	
	@Autowired
	private CategoryFacade categoryFacade;
	
	@Autowired
	private GlobalConfig globalConfig;
	
	@Autowired
	private SysmgrFunctionalBlockService<SysmgrFunctionalBlock> functionalBlockService;
	
	@Autowired
	private SysmgrAppHomeConfigService<SysmgrAppHomeConfig> sysmgrAppHomeConfigService;
	
	
	/** 记录日志 */
	private static final Log LOGGER = LogFactory.getLog(HomeController.class);
	
	protected static final String SHOP = "1";
	protected static final String STORE = "2";
	
	
	private static final String APP_HOME= "APP_HOME";
	
	
	private static RedisUtil redis = new RedisUtil();
	
	/***
	 * 首页展示的商品 和 banner
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("serial")
	@ResponseBody
	@RequestMapping("/homeList")
	public Object homeList() {
		try {
			
			Map<String, Object> index = new HashMap<String, Object>();
			
			Gson gson = new Gson();
			String key = "HOME_KEY".toUpperCase();
			if (redis.hexists(APP_HOME,key)) {
				
				String jsonMap = redis.hget(APP_HOME, key);
				if(StringUtils.isNotBlank(jsonMap)){
					index = gson.fromJson(jsonMap, new TypeToken<Map<String, Object>>(){}.getType());
					return Jsonp_data.success( index);
				}
			}
			
			List<FocusPicDto> picList =  bannerFacade.getFocusPic(AdvertisementConfigConstant.APP_HOME_FOCUS);
			for (FocusPicDto focusPicDto : picList) {
				BeanNullConverUtil.nullConver(focusPicDto);
			}
			index.put("banner",picList);
			SysmgrAppHomeConfig config = sysmgrAppHomeConfigService.findByPlatfrom(1);
			if(config!=null){
				index.put("category",this.getHomeCategoryDto(config.getCategory()));
				index.put("label",this.getHomeLabelDtos(config.getLabel()));
			}
			index.put("block", this.getFunctionalBlock(SHOP));
			String thjCount = globalConfig.findByCode(GlobalConfigConstant.THJ);
			index.put(LabelConstant.THJ, this.getHomeLabelSkuList(NumberUtils.toInt(thjCount), LabelConstant.THJ,ImageStandardConstant.IMAGE_STANDARD_PC_300));
			
			String jsonStr = gson.toJson (index);
			redis.hset(APP_HOME, key, jsonStr );
			
			return  Jsonp_data.success( index);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("首页数据出错"+e);
			return Jsonp.error();
		}
	}
	
	
	private List<HomeCategoryDto> getHomeCategoryDto(String categoryCodes){
		
		List<HomeCategoryDto> categoryDtos = new ArrayList<HomeCategoryDto>();
		if(StringUtils.isBlank(categoryCodes)){
			return categoryDtos;
		}
		for (String code : StringUtils.split(categoryCodes, ",")) {
//			categoryFacade.getCategoryDto(storeCode, categoryCode);
			SkuCategory skuCategory = skuCategoryService.getCategoryByCode(null, code);
			if(skuCategory==null){
				continue;
			}
			HomeCategoryDto homeCategoryDto = new HomeCategoryDto();
			homeCategoryDto.setCode(code);
			homeCategoryDto.setName(skuCategory.getName());
			
			List<SkuCategoryImage> categoryImgs = skuCategoryImageService.findByCategoryCodeAndImageType(code, APP_HOME);
			String img = StringUtils.EMPTY;
			if(!CollectionUtils.isEmpty(categoryImgs) && categoryImgs.size() > 0){
				img =  StringUtils.trimToEmpty(categoryImgs.get(0).getImgUrl());
			}
			homeCategoryDto.setImgUrl( ImageConstantFacade.IMAGE_SERVER_ROOT_PATH +  img);
			
			categoryDtos.add(homeCategoryDto);
		}
		
		return categoryDtos;
	}
	
	private List<HomeLabelDto> getHomeLabelDtos(String labelCodes){
		List<HomeLabelDto> labelDtos = new ArrayList<HomeLabelDto>();
		
		if(StringUtils.isBlank(labelCodes)){
			return labelDtos;
		}
		
		for (String code : StringUtils.split(labelCodes, ",")) {
			HomeLabelDto homeLabelDto = new HomeLabelDto();
			SkuLabel skuLabel = skuLabelService.getByCode(code);
			if(skuLabel==null){
				continue;
			}
			homeLabelDto.setCode(code);
			homeLabelDto.setName(skuLabel.getName());

			SkuLabelQueryCondition queryCondition = new SkuLabelQueryCondition();
			queryCondition.setCode(code);
			queryCondition.setImageType(APP_HOME);
			List<SkuLabelImage> labelImgs = skuLabelImageService.findByCondition(queryCondition);
			
			String img = StringUtils.EMPTY;
			if(!CollectionUtils.isEmpty(labelImgs) && labelImgs.size() >0){
				img = StringUtils.trimToEmpty(labelImgs.get(0).getImgUrl());
				
			}
			homeLabelDto.setImgUrl( ImageConstantFacade.IMAGE_SERVER_ROOT_PATH +  img);
			
			labelDtos.add(homeLabelDto);
		}
		
		return labelDtos;
	}
	
	
	
	
	/**
	 * 获取功能栏
	 * @return
	 */
	public  List<FunctionalBlockDto> getFunctionalBlock(String type){
		
		List<SysmgrFunctionalBlock> sysmgrFunctionalBlockList  = functionalBlockService.findAllByDisable(CommonConstant.YES);
		
		List<FunctionalBlockDto> results = new ArrayList<FunctionalBlockDto>();
		Long  currentTimeMillis = System.currentTimeMillis();
		for (SysmgrFunctionalBlock sysmgrFunctionalBlock : sysmgrFunctionalBlockList) {
			
			if(sysmgrFunctionalBlock.getUseType()!= null ){
				if(StringUtils.equals(SHOP, type) && sysmgrFunctionalBlock.getUseType()==3 ){/** 类型为门店时不可用*/
					continue;
				}
				if(StringUtils.equals(STORE, type)  && sysmgrFunctionalBlock.getUseType()==2){/** 类型为商城时不可用*/
					continue;
				}
			}
			Date startTime = sysmgrFunctionalBlock.getStartTime();
			Date endTime = sysmgrFunctionalBlock.getEndTime();
			if(startTime!=null &&  currentTimeMillis < startTime.getTime() ){
				continue;
			}
			if(endTime!=null && currentTimeMillis > endTime.getTime()){
				continue;
			}
			
			
			results.add(this.getFunctionalBlockDto(sysmgrFunctionalBlock));
			
		}
		return results;
	}
	
	
	/***
	 * 给功能块参数赋值
	 * @param sysmgrFunctionalBlock
	 * @return
	 */
	public FunctionalBlockDto getFunctionalBlockDto(SysmgrFunctionalBlock sysmgrFunctionalBlock){
		
		FunctionalBlockDto result = new FunctionalBlockDto(); 
		result.setCode(sysmgrFunctionalBlock.getCode());
		result.setIcon(ImageConstantFacade.IMAGE_SERVER_ROOT_PATH +"/"+ StringUtils.trimToEmpty(sysmgrFunctionalBlock.getIcon()));
		result.setLinkUrl(StringUtils.trimToEmpty(sysmgrFunctionalBlock.getLinkUrl()));
		result.setName(sysmgrFunctionalBlock.getName());
		return result;
	}
	
	/**
	 * 获取标题图片
	 * @param adCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBanner")
	public Object list(String adCode){

		List<FocusPicDto> picList = bannerFacade.getFocusPic(adCode);
		
		for (FocusPicDto pic : picList) {
			try {
				URL	url = new URL(pic.getPicturePath());
				InputStream 	is = url.openStream();
				BufferedImage 	img = ImageIO.read(is);
				pic.setHeight(img.getHeight() + "");
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return Jsonp_data.success(picList);
	}
	
	/**
	 * 获取首页标签下的商品集合
	 * @param count 显示数量
	 * @param labelCode 标签编号
	 * @param imageStandardCode 图片尺寸
	 * @return
	 */
    private List<SkuDto> getHomeLabelSkuList(Integer count,String labelCode,String imageStandardCode){
		
		SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();	
		
		queryCondition.setStart((AppConstant.START_INDEX - 1) * count);
		queryCondition.setLimit(count);
		queryCondition.setSortParam(SortTypeConstant.DEFAULT);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setLabelCode(labelCode);
		queryCondition.setImageStandardCode(imageStandardCode);
		queryCondition.setAppHome(CommonConstant.YES);
		queryCondition.setDisabled(CommonConstant.YES);
		
		List<SkuDto> labelSkus = skuFacade.getLabelSkuList(queryCondition);
		
		return  labelSkus;
	}
	
	
/**
 * 获取标签下的商品
 * @param labelCode 标签编号
 * @param pageIndex 当前页数
 * @param pageSize 总页数
 * @param sortParam 排序的参数（销量，价格，最新，综合）
 * @param sortType 升序或降序
 * @return
 */
	@ResponseBody
	@RequestMapping("findSkuByLable")
	public Object findSkuByLable(String labelCode,
			@RequestParam(value="storeCode",defaultValue="")String storeCode,
			@RequestParam(value="pageIndex",defaultValue=PageConstant.APP_PAGE_INDEX+"")Integer index,
			@RequestParam(value="pageSize",defaultValue=PageConstant.APP_PAGE_SIZE+"")Integer size,
			@RequestParam(value = "sortParam", defaultValue = SortTypeConstant.DEFAULT + "") String sortParam,
			@RequestParam(value = "sortType", defaultValue = "") String sortType){
		
		List<SkuDto> labelSkus = null;
		try {
		
			SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();	
			
			
			queryCondition.setStoreCode(storeCode);
			queryCondition.setStart((index - 1) * size);
			queryCondition.setLimit(size);
			queryCondition.setSortParam(SortTypeConstant.DEFAULT);
			queryCondition.setSortType(sortType);
			queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
			queryCondition.setLabelCode(labelCode);
			queryCondition.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_PC_300);
			
			labelSkus = skuFacade.getLabelSkuList(queryCondition);
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			return Jsonp.error("获取标签下的商品时出现错误！");
		}
		return Jsonp_data.success(labelSkus);
		
	}
	

	/***
	 * 获取品牌列表
	 * 
	
	@ResponseBody
	@RequestMapping("/brandList")
	public Object brandList() {
		
		SkuBrandQueryCondition queryCondition = new SkuBrandQueryCondition();
		queryCondition.setIsShow(CommonConstant.YES);
		try {
			List<SkuBrandDto> dtos = skuFacade.findBrandList(queryCondition);
			for(SkuBrandDto skuBrand : dtos){
				skuBrand.setBrandLogoAppUrl(ImageConstantApp.IMAGE_SERVER_ROOT_PATH + "/" +skuBrand.getBrandLogoAppUrl() );
			}
			return Jsonp_data.success(dtos);
		} catch (Exception e) {
			LOGGER.error("品牌数据出错"+e.getMessage());
			return Jsonp.error();
		}
	} */

	/***
	 * 获取分类列表
	 * 
	 */
	@SuppressWarnings("serial")
	@ResponseBody
	@RequestMapping("/categoryList")
	public Object categoryList(@RequestParam(value="storeCode",defaultValue="") String storeCode) {
		try {
			List<CategoryDto> dtos = new ArrayList<CategoryDto>();
			Gson gson = new Gson();
			String key = "HOME_CATEGORY_KEY".toUpperCase();
			if (redis.hexists(APP_HOME,key)) {
				String jsonMap = redis.hget(APP_HOME, key);
				if(StringUtils.isNotBlank(jsonMap)){
					dtos = gson.fromJson(jsonMap, new TypeToken<List<CategoryDto>>(){}.getType());
					return Jsonp_data.success( dtos);
				}
			}
			
			dtos = categoryFacade.findAllCategory(storeCode,true,CommonConstant.YES);
			String jsonStr = gson.toJson (dtos);
			redis.hset(APP_HOME, key, jsonStr );
			
			return Jsonp_data.success(dtos);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return Jsonp.error();
		}
	}
	/**
	 * 首页弹窗
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/indexBombox")
	public Object indexBombox(){
		
//		List<FocusPicDto> picList =  bannerFacade.getFocusPic(AdvertisementConfigConstant.APP_HOME_BOMB_BOX);
//		if(!CollectionUtils.isEmpty(picList) && picList.size() > 0)	{
//			return Jsonp_data.success(picList.get(0));
//		}
//		FocusPicDto result =   new FocusPicDto();
//		BeanNullConverUtil.nullConver(result);
		String result = CommonConstant.YES;
		
		return Jsonp_data.success(result);
		
	}
	

}
