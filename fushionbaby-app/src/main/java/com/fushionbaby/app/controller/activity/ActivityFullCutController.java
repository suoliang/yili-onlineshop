/**
 * app.aladingshop.com 上海一里网络科技有限公司
 */
package com.fushionbaby.app.controller.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.app.dto.ActivityFullCutAreaDto;
import com.fushionbaby.app.dto.ActivityFullCutDto;
import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.SortTypeConstant;
import com.fushionbaby.common.dto.FocusPicDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.sku.BannerFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.sku.model.SkuLabel;
import com.fushionbaby.sku.service.SkuLabelService;
	
/**
 * @description 满减活动
 * @author 孟少博
 * @date 2015年10月20日下午5:54:00
 */
@Controller
@RequestMapping("activity/fullCut")
public class ActivityFullCutController {


	
	/** 双11活动的标签编号前缀*/
	private static final String ACTIVITY_TYPE = "ACTIVITY_11";
	
	
	/** 双11活动广告图*/
	private static final String ACTIVITY_BANNER = "ACTIVITY_11_BANNER";
	
	/** 双11活动规则*/
	private static final String ACTIVITY_RULE = "ACTIVITY_11_RULE";
	
	@Autowired
	private SkuLabelService<SkuLabel> skuLabelService;
	@Autowired
	private SkuFacade skuFacade;
	@Autowired
	private BannerFacade bannerFacade;
	/**
	 * 满减活动打开首页
	 * @return
	 */
	@ResponseBody
	@RequestMapping("index")
	public Object fullCutIndex(){
		
//		List<String> labelCodeList =  this.getActivityLabel(ACTIVITY_TYPE +"_");
		
		List<String> labelCodeList =  this.getActivityLabel();
		ActivityFullCutDto activityFullCutDto = new ActivityFullCutDto();
		
		activityFullCutDto.setBanner(this.getLabelBanner(ACTIVITY_BANNER));
		
		activityFullCutDto.setRule(this.getLabelBanner(ACTIVITY_RULE));
		
		List<ActivityFullCutAreaDto> fullCutAreaDtoList = new ArrayList<ActivityFullCutAreaDto>();
		
		for (String labelCode : labelCodeList) {
			
			ActivityFullCutAreaDto activityFullCutAreaDto = new ActivityFullCutAreaDto();
			
			SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();	
			queryCondition.setStart(0);
			queryCondition.setLimit(6);
			queryCondition.setSortParam(SortTypeConstant.DEFAULT);
			queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
			queryCondition.setLabelCode(labelCode);
			queryCondition.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_PC_300);
			
			List<SkuDto> skuList = skuFacade.getLabelSkuList(queryCondition);
			
			activityFullCutAreaDto.setSkuList(skuList);
			
			activityFullCutAreaDto.setLabelBanner(this.getLabelBanner(labelCode));
			SkuLabel skuLabel = skuLabelService.getByCode(labelCode);
			activityFullCutAreaDto.setTitle(skuLabel!=null && skuLabel.getName()!=null ? skuLabel.getName():StringUtils.EMPTY);
			fullCutAreaDtoList.add(activityFullCutAreaDto);
		}
		
		activityFullCutDto.setAreas(fullCutAreaDtoList);
		
		return   Jsonp_data.success(activityFullCutDto);
	}
	
	
	/**
	 * 双11活动标签编号
	 * @return
	 */
	private List<String> getActivityLabel(){
		
		List<String> codeList = new ArrayList<String>();
		
		List<SkuLabel> labelList = skuLabelService.getByType(ACTIVITY_TYPE);
		
		for (SkuLabel skuLabel : labelList) {
			codeList.add(skuLabel.getCode());
		}
		
		return codeList;
	}
	
	/***
	 * 根据banner编号获取便签的广告图
	 * @param adCode
	 * @return
	 */
	private FocusPicDto getLabelBanner(String adCode){
		FocusPicDto pic = null;
		List<FocusPicDto> picList = bannerFacade.getFocusPic(adCode);
		if(picList==null || picList.size()<=0){
			pic	 = new FocusPicDto();
			BeanNullConverUtil.nullConver(pic);
			return pic;
		}
		pic = picList.get(0);
		BeanNullConverUtil.nullConver(pic);		
		return pic;
	}
	
}
