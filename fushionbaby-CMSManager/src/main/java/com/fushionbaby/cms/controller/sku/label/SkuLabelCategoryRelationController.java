package com.fushionbaby.cms.controller.sku.label;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.dto.SkuLabelCategoryDto;
import com.aladingshop.sku.cms.model.SkuLabelCategoryRelation;
import com.aladingshop.sku.cms.service.SkuLabelCategoryRelationService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.CheckObjectIsNull;

/**
 * @description 标签和分类的关联
 * @author 索亮
 * @date 2015年7月27日下午8:00:39
 */
@Controller
@RequestMapping("/skuLabel")
public class SkuLabelCategoryRelationController extends BaseController {
	private static final Log LOGGER = LogFactory
			.getLog(SkuLabelCategoryRelationController.class);
	@Autowired
	private SkuLabelCategoryRelationService<SkuLabelCategoryRelation> labelCategoryRelationService;
	
	/**
	 * @param labelCategoryDto
	 * @return
	 */
	private Map<String, String> setParamByLabelCategoryDto(SkuLabelCategoryDto labelCategoryDto) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("labelCode", labelCategoryDto.getLabelCode());
		params.put("categoryCode", labelCategoryDto.getCategoryCode());
		params.put("disable", labelCategoryDto.getDisable());
		return params;
	}
	
	@RequestMapping("labelCategoryList")
	public String labelCategoryList(SkuLabelCategoryDto labelCategoryDto,
			BasePagination page,ModelMap model){
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = setParamByLabelCategoryDto(labelCategoryDto);
			page.setParams(params);
			page = labelCategoryRelationService.getListPage(page);
			model.addAttribute("page", page);
			model.addAttribute("labelCategoryDto", labelCategoryDto);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("标签分类关联列表出错"+e);
		}
		return "models/sku/skuLabel/skuLabelCategoryConfigList";
	}
	
	@ResponseBody
	@RequestMapping("addNotRelationLabelCategory")
	public String addNotRelationLabelCategory(String categoryCode,
			String labelCode,HttpSession session){
		try {
			SkuLabelCategoryRelation slcc = new SkuLabelCategoryRelation();
			slcc.setCreateId(CMSSessionUtils.getSessionUser(session).getId());
			slcc.setDisable(CommonConstant.YES);
			slcc.setLabelCode(labelCode);
			slcc.setCategoryCode(categoryCode);
			labelCategoryRelationService.add(slcc);
		} catch (Exception e) {
			LOGGER.error("添加关联分类失败:" + e);
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}
	
	@RequestMapping("gotoEditLabelCate")
	public String gotoEditLabelCate(Long labelCategoryId,
			ModelMap model, RedirectAttributes redirectAttributes){
		SkuLabelCategoryRelation labelCategoryRelation = labelCategoryRelationService.findObjectById(labelCategoryId);
		if (CheckObjectIsNull.isNull(labelCategoryRelation)) {
			addMessage(redirectAttributes, "数据异常");
			return "redirect:" + Global.getAdminPath() + "/sku/labelCategoryList";
		}
		model.addAttribute("labelCategoryRelation", labelCategoryRelation);
		return "models/sku/skuLabel/skuLabelCategoryModify";
	}
	
	@ResponseBody
	@RequestMapping("editLabelCate")
	public String editLabelCate(Long id,int sortOrder,String disable,
			ModelMap model, RedirectAttributes redirectAttributes){
		try {
			SkuLabelCategoryRelation labelCategoryRelation = new SkuLabelCategoryRelation();
			labelCategoryRelation.setId(id);
			labelCategoryRelation.setSortOrder(sortOrder);
			labelCategoryRelation.setDisable(disable);
			labelCategoryRelationService.update(labelCategoryRelation);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("修改标签关联分类异常");
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}
	
	@ResponseBody
	@RequestMapping("deleteLabelCategoryById")
	public String deleteLabelCategoryById(Long id){
		try {
			labelCategoryRelationService.deleteById(id);
		} catch (Exception e) {
			LOGGER.error("删除关联分类失败:" + e);
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}
	
	@ResponseBody
	@RequestMapping("deleteRelationLabelCategory")
	public String deleteRelationLabelCategory(String categoryCode,
			String labelCode){
		try {
			labelCategoryRelationService.delete(labelCode, categoryCode);
		} catch (Exception e) {
			LOGGER.error("取消关联分类失败:" + e);
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}
	
	@ResponseBody
	@RequestMapping("changeIsDisable")
	public String changeIsDisable(Long id,
			String disable,HttpSession session){
		try {
			SkuLabelCategoryRelation slcc = new SkuLabelCategoryRelation();
			slcc.setId(id);
			slcc.setUpdateId(CMSSessionUtils.getSessionUser(session).getId());
			slcc.setDisable(disable);
			labelCategoryRelationService.update(slcc);
		} catch (Exception e) {
			LOGGER.error("修改状态失败:" + e);
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}
	
}
