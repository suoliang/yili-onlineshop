/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月26日上午10:40:39
 */
package com.aladingshop.store.controller.sku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuLabel;
import com.aladingshop.sku.cms.model.SkuLabelRelation;
import com.aladingshop.sku.cms.service.SkuLabelRelationService;
import com.aladingshop.sku.cms.service.SkuLabelService;
import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.config.Global;
import com.aladingshop.store.dto.SkuDto;
import com.aladingshop.store.dto.SkuLabelRelationDto;
import com.aladingshop.store.util.constant.Constant;
import com.fushionbaby.cache.util.RedisUtil;
import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SessionKeyConstant;
import com.fushionbaby.common.dto.sku.SkuLabelDto;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月26日上午10:40:39
 */
@Controller
@RequestMapping("store/skuLabel")
public class SkuLabelController extends AbstractSkuInfoController {

	
	@Autowired
	private SkuLabelService<SkuLabel> skuLabelService;
	
	@Autowired
	private SkuLabelRelationService<SkuLabelRelation> skuLabelRelationService;
	
	private static final String LABEL_TYPE = "STORE";
	
	
	private static RedisUtil redis = new RedisUtil();
	/**
	 * 商品标签列表
	 * @param labelDto
	 * @param page
	 * @param model
	 * @param session
	 * @return
	 */
	
	@RequestMapping("skuLabelList")
	public Object skuLabelList(@ModelAttribute("findForm") SkuLabelDto labelDto, 
			ModelMap model, HttpSession session){
		
		StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(auUser==null){
			return "redirect:" +Global.getAdminPath() + "/login/index";
		}
		
		
		List<SkuLabel> skuLabels =skuLabelService.findByType(LABEL_TYPE);
		model.addAttribute("skuLabels", skuLabels);
		
		return "models/sku/label/skuLabelList";
	}
	
	/**
	 * 标签下的商品列表
	 * @param skuDto
	 * @param page
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("skuListByLabel")
	public String getSkuListByLabel(SkuDto skuDto,BasePagination page, ModelMap model,
			HttpSession session) {
		
		
		String labelCode = skuDto.getLabelCode();
		
		StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(auUser==null){
			return "redirect:" +Global.getAdminPath() + "/login/index";
		}
		
		SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();
		queryCondition.setStoreCode(auUser.getStoreCode());
		queryCondition.setLabelCode(labelCode);
		queryCondition.setSkuName(skuDto.getSkuName());
		
		// 根据labelCode获取全部skuCode
		List<String> skuCodes = new ArrayList<String>();
		List<SkuLabelRelation> skuList = skuLabelRelationService.queryByCondition(queryCondition);	
		
		List<SkuLabelRelationDto> relDtos = new ArrayList<SkuLabelRelationDto>();
		if(skuList!=null && !CollectionUtils.isEmpty(skuList) && skuList.size() > 0){
			for (SkuLabelRelation skuLabelRelation : skuList) {
				skuCodes.add(skuLabelRelation.getSkuCode());
			}
			relDtos = this.getLabelSku(skuCodes, page, labelCode,auUser.getStoreCode());
		}
		SkuLabel skuLabel=skuLabelService.getByCode(labelCode);
		model.addAttribute("skuDto", skuDto);
		model.addAttribute("label", skuLabel);
		model.addAttribute("page", page);
		model.addAttribute("relDtos", relDtos);

		return "models/sku/label/skuLabelDetail";
	}
	
	
	private  List<SkuLabelRelationDto> getLabelSku(List<String> skuCodes,BasePagination page,String labelCode,String storeCode){
		
		StringBuffer sb = new StringBuffer();
		
		for (String skuCode : skuCodes) {
			sb.append("'"+skuCode+"'" + ",");
		}
		
		String reStr = sb.toString().substring(0,sb.length()-1);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("skuCodes", reStr);
		params.put("storeCode", storeCode);
		page.setParams(params);
		page = skuInfoService.getPageList(page);
		
		@SuppressWarnings("unchecked")
		List<Sku> results = (List<Sku>) page.getResult();
		List<SkuLabelRelationDto> relDtos = new ArrayList<SkuLabelRelationDto>();
		for (Sku sku : results) {
			SkuLabelRelationDto relSkuDto = new SkuLabelRelationDto();
			SkuLabelRelationQueryCondition aqueryCondition = new SkuLabelRelationQueryCondition();
			aqueryCondition.setSkuCode(sku.getUniqueCode());
			aqueryCondition.setLabelCode(labelCode);
			aqueryCondition.setStoreCode(storeCode);
			List<SkuLabelRelation> relList = skuLabelRelationService.queryByCondition(aqueryCondition);
			if(relList==null || relList.size()==0 ){
				continue;
			}
			SkuLabelRelation a = relList.get(0);
			relSkuDto.setDisable(a.getDisabled());
			relSkuDto.setShowOrder(a.getShowOrder());
			relSkuDto.setSkuCode(sku.getUniqueCode());
			relSkuDto.setSkuName(sku.getName());
			relSkuDto.setSkuNo(sku.getSkuNo());
			relDtos.add(relSkuDto);
		}
		
		return relDtos;
	}
	
	
	/***
	 * 标签下没有关联过的商品
	 * @param page
	 * @param skuDto
	 * @param model
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("addNoRelationSku")
	private Object addNoRelationSku(BasePagination page, SkuDto skuDto,ModelMap model,
			HttpSession session){
		
		String labelCode = skuDto.getLabelCode();
		StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(auUser==null){
			return "redirect:" +Global.getAdminPath() + "/login/index";
		}
		String storeCode= auUser.getStoreCode();
		
		List<Sku> skuList = new ArrayList<Sku>();
		
		List<String> skuCodes = skuLabelRelationService.findSkuCodesByLabel(labelCode,storeCode);
		String reStr = StringUtils.EMPTY;
		if(skuCodes!=null && skuCodes.size()>0){
		
		
			StringBuffer sb = new StringBuffer();
			
			for (String skuCode : skuCodes) {
				sb.append("'"+skuCode+"'" + ",");
			}
			if(sb.length()>0){
				reStr = sb.toString().substring(0,sb.length()-1);
			}
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("noSkuCodes", Objects.equals(reStr, "") ? null : reStr);
		params.put("storeCode", storeCode);
		params.put("skuNo", skuDto.getSkuNo());
		params.put("status", skuDto.getStatus());
		params.put("categoryCode", skuDto.getCategoryCode());
		params.put("name", skuDto.getSkuName());
		page.setParams(params);
		page = skuInfoService.getPageList(page);
		skuList = (List<Sku>)page.getResult();
			
		
		/** 查询出所有二级分类 */
		model.addAttribute("allCategoryJson", this.getCategoryJson(storeCode, CommonConstant.YES));
		model.addAttribute("skuList", skuList);
		model.addAttribute("page", page);
		model.addAttribute("labelCode", labelCode);
		return "models/sku/label/skuLabelRelationAdd";
	}
	/**
	 * 添加商品到标签中
	 * @param labelCode
	 * @param skuCodes
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addSkuByLabel")
	public Object addSkuByLabel(String labelCode,String skuCodes,HttpSession session){
		StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(auUser==null){
			return "redirect:" +Global.getAdminPath() + "/login/index";
		}
		String storeCode= auUser.getStoreCode();
		
		
		List<String> all = Arrays.asList(skuCodes.split(","));
		for (String currentSkuCode : all) {
			SkuLabelRelation relation = new SkuLabelRelation(labelCode, currentSkuCode, CommonConstant.YES,storeCode);
			
			SkuLabelRelation findRel = skuLabelRelationService.findBySkuCodeAndLabel(currentSkuCode, labelCode);
			if(findRel!=null){
				continue;
			}
			try {
				skuLabelRelationService.add(relation);
				
				redis.hdel("STORE_APP_HOME", "STORE_HOME_KEY_".toUpperCase() +storeCode) ;
				
			} catch (DataAccessException e) {
				e.printStackTrace();
				return Constant.FAILURE;
			}
		}

		return Constant.SUCCESS;
	}
	/***
	 * 删除商品标签关联
	 * @param labelCode
	 * @param skuCode
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteSkuLabelRelation")
	public Object deleteSkuLabelRelation(String labelCode,String skuCode,HttpSession session){
		StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(auUser==null){
			return "redirect:" +Global.getAdminPath() + "/login/index";
		}
		String storeCode= auUser.getStoreCode();
		try {
				
			SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();
			queryCondition.setLabelCode(labelCode);
			queryCondition.setSkuCode(skuCode);
			queryCondition.setStoreCode(storeCode);
			List<SkuLabelRelation> relList = skuLabelRelationService.queryByCondition(queryCondition);
			if(relList!=null && relList.size()>0){
				skuLabelRelationService.deleteById(relList.get(0).getId());
			}
			
			redis.hdel("STORE_APP_HOME", "STORE_HOME_KEY_".toUpperCase() +storeCode) ;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
		
	}
}
