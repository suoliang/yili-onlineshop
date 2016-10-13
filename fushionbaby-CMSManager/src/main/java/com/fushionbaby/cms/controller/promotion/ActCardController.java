package com.fushionbaby.cms.controller.promotion;
import java.util.Date;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fushionbaby.act.activity.dto.CardDto;
import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.model.ActCardType;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.act.activity.service.ActCardTypeService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.common.constants.CmsConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.PromotionConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.RandomNumUtil;

/***
 * 优惠券
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/promotion")
public class ActCardController extends BaseController {
	/** 优惠券 */
	@Autowired
	private ActCardService<ActCard> cardService;
	/** 优惠券类型*/
	@Autowired
	private ActCardTypeService<ActCardType> cardTypeService;
	private static final Log logger = LogFactory.getLog(ActCardController.class);
	/** 页面*/
    private static final String PRE_="models/promotion/card/";
	
    /**列表页*/
	private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/promotion/cardList";
	
	
	
	/***
	 * 优惠券的列表
	 * 
	 * @param cardNo
	 * @param useType
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("cardList")
	public String findAll(CardDto cardDto,BasePagination page, Model model) {
		if (page == null) {
			page = new BasePagination();
		}
		/** 设置分页的参数 */
		Map<String, String> params = new HashMap<String, String>();
		params.put("cardNo", cardDto.getCardNo());
		params.put("useType", cardDto.getUseType());
		params.put("useTimeFrom", cardDto.getUseTimeFrom());
		params.put("useTimeTo", cardDto.getUseTimeTo());
		params.put("password", cardDto.getPassword());
		page.setParams(params);
		page = cardService.getListPage(page);
		/** 获得分页的结果 */
		List<ActCard> cardList = (List<ActCard>) page.getResult();
		for (ActCard ac : cardList) {
			//Integer maxCount = this.cardTypeService.findLimitCountById(ac.getCardTypeId());
			ActCardType cardType=this.cardTypeService.findById(ac.getCardTypeId());
			ac.setUseStatus("1");
			if(cardType!=null){
				ac.setMaxCount(cardType.getUseCountLimit());
				ac.setCardTypeName(cardType.getName());
				/** 设置优惠券 状态*/
				if(ac.getUsedTime()!=null)
					ac.setUseStatus("2");
				else if(cardType.getBeginTime().after(new Date())||cardType.getEndTime().before(new Date()))
				   ac.setUseStatus("3");
				else if(CommonConstant.YES.equals(cardType.getDisable()))
				  ac.setUseStatus("4");
			}
		}
		model.addAttribute("cardList", cardList);
		model.addAttribute("cardDto", cardDto);
		model.addAttribute("page", page);
		return PRE_+"cardList";
	}
	/***
	 * 优惠券删除
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping("deleteCard/{id}/{time}")
	public String deleteCard(@PathVariable("id") Long id,RedirectAttributes redirectAttributes){
	try {
		this.cardService.deleteById(id);
		addMessage(redirectAttributes, "优惠券删除成功");
	} catch (Exception e) {
		addMessage(redirectAttributes, "优惠券删除失败");
	}    
		return REDIRECT_LIST;
	}
	
	
	/***
	 * 跳转到生成优惠券页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("createCard")
	public String creatCard(Model model) {
		try {
			List<ActCardType> cardTypeList = this.cardTypeService.findAll();
			model.addAttribute("cardTypeList", cardTypeList);
		} catch (Exception e) {
			logger.error("跳转到生成优惠券页面失败", e);
		}
		return PRE_+"cardCreate";
	}
	/***
	 * 生成优惠券
	 * 
	 * @param typeId
	 * @param num
	 * @return
	 */
	@RequestMapping("addCard/{typeId}/{num}/{isUse}/{time}")
	public String addCard(@PathVariable("typeId") Long typeId,
			@PathVariable("num") Integer num,
			@PathVariable("isUse") String isUse,RedirectAttributes redirectAttributes){
		ActCardType cardType = this.cardTypeService.findById(typeId);
		/** 如果是无限次使用的卡，则只生成一个卡号 */
		if (PromotionConstant.CARD_USE_TYPE_UNLIMIT.equals(cardType.getUseType())) {
		         createCardWhenCardTypeIsNoLimit(typeId,isUse,redirectAttributes);
		}else{
			createCardWhenCardTypeIsOnceOrMore(cardType.getUseType(),typeId,num,isUse);
		}
	    return REDIRECT_LIST;
	    }
		
	/***
	 * 生成优惠券时，卡的类型为无限制使用
	 * @param typeId
	 * @param redirectAttributes 
	 * @return
	 */
	private void createCardWhenCardTypeIsNoLimit(Long typeId,String isUse, RedirectAttributes redirectAttributes) {
		try {
			ActCard card = new ActCard();
		    card.setCardNo(CmsConstant.ACT_CARD_PRFFIX+ RandomNumUtil.getRandom(RandomNumUtil.NUM, 10));
			if(CommonConstant.YES.equals(isUse))
		    card.setPassword(RandomNumUtil.getNumber(8));
		    card.setCardTypeId(typeId);
		    card.setUseType(PromotionConstant.CARD_USE_TYPE_UNLIMIT);
			this.cardService.add(card);
			addMessage(redirectAttributes, "优惠券生成成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "优惠券生成失败");
			logger.error("cms:ActCardController.java  无限制使用优惠券生成失败",e);
		}
	
	}

	/***
	 * 生成多张优惠券卡，参数有数量，卡的类型，以及卡的使用类型
	 * @param useTypes
	 * @param typeId
	 * @param num
	 */
	private void createCardWhenCardTypeIsOnceOrMore(String useTypes, Long typeId, Integer num,String isUse) {
		try {
			for (int i = 1; i <= num; i++) {
				ActCard card = new ActCard();
				/** 生成的优惠券编号 */
				card.setCardNo(RandomNumUtil.getCharacterAndNumber(5));
				if(CommonConstant.YES.equals(isUse))
				card.setPassword( RandomNumUtil.getNumber(5));
				card.setCardTypeId(typeId);
				card.setUseType(useTypes);
				this.cardService.add(card);
		  }
			
		} catch (Exception e) {
			logger.error("cms:ActCardController.java 优惠券生成失败", e);
		}
	}

	/***
	 * 使用优惠券（注销使用）
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("useCard/{id}/{time}")
	public String useCard(@PathVariable("id") Long id,RedirectAttributes redirectAttributes){
	try {
		ActCard card=this.cardService.findById(id);
		card.setUseCount(card.getUseCount()+1);
		card.setUsedTime(new Date());
		this.cardService.update(card);
		addMessage(redirectAttributes, "优惠券注销使用成功");
	} catch (Exception e) {
		addMessage(redirectAttributes, "优惠券注销使用失败");
	}    
		return REDIRECT_LIST;
	}
}
