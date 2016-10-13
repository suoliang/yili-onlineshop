package com.fushionbaby.cms.controller.promotion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fushionbaby.act.activity.dto.CardTypeDto;
import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.model.ActCardType;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.act.activity.service.ActCardTypeService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.common.constants.PromotionConstant;
import com.fushionbaby.common.util.BasePagination;
/***
 * 
 * @description 优惠券类型
 * @author 徐培峻
 * @date 2015年7月23日上午9:48:01
 */
@Controller
@RequestMapping("/promotion")
public class ActCardTypeController extends BaseController{
	/** 优惠券类型 */
	@Autowired
	private ActCardTypeService<ActCardType> actCardTypeService;
	/** 优惠券 */
	@Autowired
	private ActCardService<ActCard> actCardService;

	private static final Log logger = LogFactory.getLog(ActCardTypeController.class);
	/** 页面*/
    private static final String PRE_="models/promotion/cardType/";
    /**列表页*/
	private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/promotion/cardTypeList";
	
	/***
	 * 优惠券类型列表
	 * @param name
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("cardTypeList")
	public Object actCardTypeList(CardTypeDto cardTypeDto,BasePagination page, Model model) {
		if (page == null) {
			page = new BasePagination();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", cardTypeDto.getCardTypeName());
		params.put("cardType", cardTypeDto.getCardType());
		params.put("timeType", cardTypeDto.getTimeType());
		params.put("useTimeFrom", cardTypeDto.getUseTimeFrom());
		params.put("useTimeTo", cardTypeDto.getUseTimeTo());
		page.setParams(params);
		page = actCardTypeService.getListPage(page);
		List<ActCardType> cardTypeList = (List<ActCardType>) page.getResult();
		model.addAttribute("cardTypeList", cardTypeList);
	    model.addAttribute("cardTypeDto",cardTypeDto);
		model.addAttribute("page", page);
		return PRE_+"cardTypeList";
	}
	
	/***
	 * 修改该优惠券，是否可用状态
	 * @param id
	 * @param status
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("updateCardTypeDisable/{id}/{disable}/{time}")
	public String updateCardTypeDisable(@PathVariable("id") Long id,@PathVariable("disable") String disable,RedirectAttributes redirectAttributes){
		try {
			ActCardType cardType=this.actCardTypeService.findById(id);
			cardType.setDisable(disable);
			this.actCardTypeService.update(cardType);
			addMessage(redirectAttributes, "修改该优惠券类型状态成功 ");
		} catch (Exception e) {
			addMessage(redirectAttributes, "修改该优惠券类型状态失败");
		}
		return REDIRECT_LIST;
	}
	
	@RequestMapping("toAdd")
	public String toAdd(){
		return PRE_+"cardTypeAdd";
	}
	
	/***
	 * 优惠券类型的添加
	 * @param count
	 * @param cardType
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("addCardType")
	public String addCardType(@RequestParam("count") int count,	ActCardType cardType,RedirectAttributes redirectAttributes) {
		try {
			if (count != 0) {
				cardType.setUseCountLimit(count);
			}
			int count_limit = cardType.getUseCountLimit();
			if (count_limit == 1) {
				cardType.setUseType(PromotionConstant.CARD_USE_TYPE_ONCE);
			} else if (count_limit > 1) {
				cardType.setUseType(PromotionConstant.CARD_USE_TYPE_OTHER);
			} else if (count_limit == -1) {
				cardType.setUseType(PromotionConstant.CARD_USE_TYPE_UNLIMIT);
			}
			actCardTypeService.add(cardType);
			addMessage(redirectAttributes, "优惠券类型添加成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "优惠券类型添加失败");
			logger.error("新增优惠券类型失败", e);
		}
		return REDIRECT_LIST;
	}
	
	
	/** 删除 */
/*	@ResponseBody
	@RequestMapping("delCardType")
	public JsonResponseModel delCardType(@RequestParam(value = "id", defaultValue = "0") Long id) {
		JsonResponseModel jsonMode = new JsonResponseModel();
		try {
			if (id == null || id == 0) {
				jsonMode.Fail("id不能为空!");
			} else {
				actCardTypeService.deleteById(id);
				*//** 删除该类型下的优惠券 *//*
				this.actCardService.deleteByTypeId(id);
				jsonMode.Success("优惠券类型删除成功!");
			}
		} catch (Exception e) {
			jsonMode.Fail("优惠券类型删除失败!");
			logger.error("优惠券类型删除失败", e);
		}
		return jsonMode;
	}*/


}
