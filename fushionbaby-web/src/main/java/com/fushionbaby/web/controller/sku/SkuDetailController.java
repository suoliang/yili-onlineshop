/**
 * 
 */
package com.fushionbaby.web.controller.sku;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.condition.member.MemberCommentQueryCondition;
import com.fushionbaby.common.condition.sku.SkuLinkSkusQueryCondition;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.ImageTypeConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.SelectedSkuDto;
import com.fushionbaby.common.dto.sku.SkuCombinationDto;
import com.fushionbaby.common.dto.sku.SkuDetailDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.dto.sku.web.PageSetDto;
import com.fushionbaby.common.dto.sku.web.WebSkuDetailDto;

import com.fushionbaby.common.util.jsonp.Jsonp_data;

import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;

import com.fushionbaby.facade.biz.common.member.MemberCommentFacade;
import com.fushionbaby.facade.biz.common.sku.SkuCombinationFacade;
import com.fushionbaby.facade.biz.common.sku.SkuDetailFacade;
import com.fushionbaby.facade.biz.web.sku.WebSkuFacade;
import com.fushionbaby.member.model.MemberSkuComment;
import com.fushionbaby.member.service.MemberSkuCommentService;
import com.fushionbaby.sku.model.SkuCombination;
import com.fushionbaby.sku.service.FindSkuService;
import com.fushionbaby.sku.service.SkuCombinationService;

/**
 * @author mengshaobo
 * 
 */
@Controller
@RequestMapping("/product")
public class SkuDetailController{

	@Autowired
	private FindSkuService findSkuService;
	@Autowired
	private SkuDetailFacade skuDetailFacade;
	@Autowired
	private WebSkuFacade  webSkuFacade;
	@Autowired
	private MemberCommentFacade memberCommentFacade;
	@Autowired
	private SkuCombinationFacade skuCombinationFacade;
	@Autowired
	private MemberSkuCommentService<MemberSkuComment> memberCommentService;
	@Autowired
	private SkuCombinationService skuCombinationService;
	
	/**记录日志*/
	private static final Log LOGGER = LogFactory.getLog(SkuDetailController.class);
	/**
	 * 查询商品详情信息
	 * 
	 * @param skuId
	 * @param productId
	 *            产品编号
	 * @param color
	 *            颜色
	 * @param size
	 *            尺寸
	 * @param model
	 * @return
	 */
	@RequestMapping("skuDetail")
	public String skuDetail(@RequestParam(value = "skuCode", defaultValue = "") String skuCode,
			@RequestParam(value = "productCode", defaultValue = "") String productCode,
			@RequestParam(value = "color", defaultValue = "") String color,
			@RequestParam(value = "size", defaultValue = "") String size,
			@RequestParam(value = "commentPageIndex", defaultValue = "") Integer commentPageIndex,
			ModelMap model) {		
		String nSkuCode = skuCode;
		SelectedSkuDto selectedSkuDto = new SelectedSkuDto();
		selectedSkuDto.setSkuCode(nSkuCode);
		selectedSkuDto.setProductCode(productCode);
		selectedSkuDto.setColor(color);
		selectedSkuDto.setSize(size);
		SkuDetailDto skuDetail = skuDetailFacade.getSkuDetailModel(selectedSkuDto);
		if(ObjectUtils.equals(null, skuDetail)){
			return "common/none";
		}	
		WebSkuDetailDto webSkuDetailDto =	webSkuFacade.getWebSkuDetail(skuDetail);
		model.addAttribute("skuDetail", webSkuDetailDto);

		SkuLinkSkusQueryCondition skuLinkSkusQueryCondition = new SkuLinkSkusQueryCondition();
		skuLinkSkusQueryCondition.setSkuCode(nSkuCode);
		skuLinkSkusQueryCondition.setStart(WebConstant.START_INDEX);
		skuLinkSkusQueryCondition.setLimit(WebConstant.LINK_SKU_SIZE);
		skuLinkSkusQueryCondition.setImageType(ImageTypeConstant.IMAGE_TYPE_WEB_7);
		model.addAttribute("linkSkuList", skuDetailFacade.getLinkSkus(skuLinkSkusQueryCondition));
		SkuCombinationDto skuCombination = this.getSkubinationDto(nSkuCode);
		if(ObjectUtils.notEqual(null, skuCombination.getSkuDtoList()) && skuCombination.getSkuDtoList().size() > 0 ){
			model.addAttribute("skuCombination",skuCombination);
		}
	
		return "details";
	}
	

	@ResponseBody
	@RequestMapping("getCommentInfo")
	public Object getCommentInfoWhenLoad(@RequestParam("skuCode") String skuCode,
				@RequestParam(value = "commentPageIndex", defaultValue = "") Integer commentPageIndex){
		MemberCommentQueryCondition memberCommentQueryCondition = new MemberCommentQueryCondition();
		memberCommentQueryCondition.setSkuCode(skuCode);
		memberCommentQueryCondition.setStart(WebConstant.START_INDEX);
		if(ObjectUtils.notEqual(null , commentPageIndex)){
			memberCommentQueryCondition.setStart((commentPageIndex - 1) * WebConstant.SKU_COMMENT_SIZE);
		}
		memberCommentQueryCondition.setLimit(WebConstant.SKU_COMMENT_SIZE);	
		PageSetDto  memberCommentPageset = new PageSetDto();
		memberCommentPageset.setMemberCommentDtoList(memberCommentFacade.getMemberComments(memberCommentQueryCondition));
		Integer amount = memberCommentService.getPagetotal(skuCode);
		memberCommentPageset.setAmount(amount);
		memberCommentPageset.setTotalPage((amount -1 ) / WebConstant.SKU_COMMENT_SIZE + 1);
		memberCommentPageset.setCurPage(commentPageIndex);
		return Jsonp_data.success(memberCommentPageset);
	}

	private SkuCombinationDto getSkubinationDto(String skuCode){
		List<SkuDto> skuList = skuCombinationFacade.getSkuCombinationBySkuCode(skuCode);
		
		SkuCombination skuCombination  =  skuCombinationService.queryBySkuCode(skuCode);

		return skuCombinationFacade.getSkuCombinationDto(skuList, skuCombination);
	}

	
	/**
	 * 
	 * @param combinationId 优惠商品组合编号
	 * @param model
	 * @return
	 */
	@RequestMapping("carGroup")
	public String carGroup(@RequestParam("combinationId") Long combinationId,ModelMap model){
		List<SkuDto> skuList = skuCombinationFacade.getSkuByCombinationId(combinationId);
		
		SkuCombination skuCombination  =  skuCombinationService.queryById(combinationId);
		
		SkuCombinationDto skuCombinationDto = skuCombinationFacade.getSkuCombinationDto(skuList, skuCombination);
		
		model.addAttribute("skuCombinationDto", skuCombinationDto);
		
		return "car-group";
	}
	/**
	 * 验证登录
	 * @param request
	 * @return
	 */
	@RequestMapping("checkLogin")
	@ResponseBody
	public Object checkLogin(HttpServletRequest request){
		
		String cookieValue = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
		UserDto user = ObjectUtils.equals(SessionCache.get(cookieValue), null) ? null: (UserDto) SessionCache.get(cookieValue);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		return Jsonp.success();
	}
	
	

}