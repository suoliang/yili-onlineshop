/**
 * aladingshop.com/ 上海一里网络科技有限公司
 */
package com.fushionbaby.app.controller.sku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.app.dto.VipSkuDto;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.PageConstant;
import com.fushionbaby.common.constants.SortTypeConstant;
import com.fushionbaby.common.dto.FocusPicDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.dto.sku.web.PageSetDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.sku.BannerFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.sku.model.SkuCategory;
import com.fushionbaby.sku.model.SkuCategoryImage;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.model.SkuLabel;
import com.fushionbaby.sku.model.SkuLabelCategoryRelation;
import com.fushionbaby.sku.model.SkuLabelImage;
import com.fushionbaby.sku.service.SkuCategoryImageService;
import com.fushionbaby.sku.service.SkuCategoryService;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuLabelCategoryRelationService;
import com.fushionbaby.sku.service.SkuLabelImageService;
import com.fushionbaby.sku.service.SkuLabelRelationService;
import com.fushionbaby.sku.service.SkuLabelService;
import com.fushionbaby.sku.service.SkuService;

/**
 * @description VIP会员专区
 * @author 孟少博
 * @date 2015年10月28日上午11:57:41
 */
@Controller
@RequestMapping("vip/sku/")
public class VipSkuController {

	
	//private static final Log LOGGER = LogFactory.getLog(VipSkuController.class);
	
	@Autowired
	private SkuLabelCategoryRelationService<SkuLabelCategoryRelation> skuLabelCategoryRelationService;
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	
	@Autowired
	private SkuFacade skuFacade;
	@Autowired
	private SkuService skuService;
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	@Autowired
	private BannerFacade bannerFacade;
	
	@Autowired
	private SkuCategoryImageService<SkuCategoryImage> skuCategoryImageService;
	
	@Autowired
	private SkuLabelService<SkuLabel> skuLabelService;
	@Autowired
	private SkuLabelRelationService skuLabelRelationService;
	@Autowired
	private SkuLabelImageService<SkuLabelImage> skuLabelImageService;
	
	
	private static final String VIP="VIP";
	
	
	private static final String VIP_BANNER="VIP_BANNER";
	/**
	 * 首页
	 * @return
	 */
	

	@ResponseBody
	@RequestMapping("index")
	public Object index(){
		
		List<SkuLabel> labelList = skuLabelService.getByType(VIP);
		List<VipSkuDto> vipSkuList = new ArrayList<VipSkuDto>();
		for (SkuLabel skuLabel : labelList) {
			String labelCode =  skuLabel.getCode();
			if(StringUtils.isBlank(labelCode)){
				continue;
			}
			VipSkuDto vipSku = new VipSkuDto();
			vipSku.setCategoryCode(skuLabel.getCode());
			vipSku.setCategoryName(skuLabel.getName());
			vipSku.setSkuList(this.getMemberSkuListByLabelCode(skuLabel.getCode(), PageConstant.APP_PAGE_INDEX, 6));
			vipSku.setCategoryBanner(bannerFacade.getLabelBanner(labelCode, VIP));
			vipSkuList.add(vipSku);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("banner",this.getVipBanner(VIP_BANNER));
		map.put("vipCategorys", vipSkuList);
		return Jsonp_data.success(map) ;
	}
	
	/***
	 * 根据banner编号获取便签的广告图
	 * @param adCode
	 * @return
	 */
	private FocusPicDto getVipBanner(String adCode){
		
		List<FocusPicDto> pics = bannerFacade.getFocusPic(adCode);
		FocusPicDto pic = null;
		if(CollectionUtils.isEmpty(pics)){
			pic = new FocusPicDto();
		}else{
			pic = pics.get(0);
		}
		BeanNullConverUtil.nullConver(pic);		
		return pic;
	} 

	
	private List<SkuDto> getMemberSkuListByLabelCode(String labelCode,Integer curPage,Integer limit){
		
		
		SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();	
		
		queryCondition.setStart((curPage-1) * limit);
		queryCondition.setLimit(limit);
		queryCondition.setSortParam(SortTypeConstant.DEFAULT);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setLabelCode(labelCode);
		queryCondition.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_PC_300);
		queryCondition.setDisabled(CommonConstant.YES);
		
		List<SkuDto> labelSkus = skuFacade.getLabelSkuList(queryCondition);
		if(CollectionUtils.isEmpty(labelSkus)){
			return new ArrayList<SkuDto>();
		}
		
		return  labelSkus;
	}
	
	
	
	
	
	/**
	 * 查看更多
	 * @return
	 */
	@ResponseBody
	@RequestMapping("more")
	public Object more(@RequestParam("categoryCode") String labelCode,
			@RequestParam(value="pageIndex",defaultValue=PageConstant.APP_PAGE_INDEX+"") Integer curPage,
			@RequestParam(value="pageSize",defaultValue=PageConstant.APP_PAGE_SIZE+"") Integer limit){
		
		List<SkuDto> skuList = this.getMemberSkuListByLabelCode(labelCode, curPage, limit);
		
		
		return Jsonp_data.success(skuList);
	}
}
