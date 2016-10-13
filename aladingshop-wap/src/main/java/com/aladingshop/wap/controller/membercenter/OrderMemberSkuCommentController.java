package com.aladingshop.wap.controller.membercenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import util.ImageConstantFacade;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.service.OrderLineUserService;
import com.fushionbaby.member.model.MemberSkuComment;
import com.fushionbaby.member.service.MemberSkuCommentService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuImage;
import com.fushionbaby.sku.service.SkuImageService;
import com.fushionbaby.sku.service.SkuService;

/***
 * 
 * @author xupeijun
 * 
 * app 订单会员评论
 *
 */
@Controller
@RequestMapping("/order")
public class OrderMemberSkuCommentController {

	
	/** 商品评价service */
	@Autowired
	private MemberSkuCommentService<MemberSkuComment> skuCommentService;

	/**
	 * 订单行信息查询service
	 */
	@Autowired
	private OrderLineUserService<OrderLineUser> orderLineUserService;

	@Autowired
	private SkuService skuService;
	
	@Autowired
	private SkuImageService<SkuImage> skuImageService;
	
	private static final Log logg=LogFactory.getLog(OrderMemberSkuCommentController.class);
	
	@RequestMapping("/toEvaluate")
	public String goToEvaluate(@RequestParam("skuCode") String skuCode,@RequestParam("orderLineId") Long orderLineId,HttpServletRequest request,Model model){
		String sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
		UserDto user = (UserDto) SessionCache.get(sid);
		if(user == null || user.getMemberId() == null){
			return "redirect:/login/index" ;
		}
		Sku sku = skuService.queryBySkuCode(skuCode);
		List<SkuImage> images = skuImageService.findBySkuCode(skuCode);
		if (images != null && images.size() != 0) {
			model.addAttribute("skuImage",ImageConstantFacade.IMAGE_SERVER_ROOT_PATH
							+ images.get(0).getImgUrl());
		}
		OrderLineUser orderLineUser=orderLineUserService.findById(orderLineId);
		model.addAttribute("sku", sku);
		model.addAttribute("orderLineUser", orderLineUser);
	   return "membercenter/ranking";
	}
	
	
	/***
	 * 会员提交商品评价
	 * @param sid
	 * @param skuCode
	 * @param content 非必须
	 * @param sourceCode
	 * @param skuColor
	 * @param skuSize
	 * @param orderCode
	 * @param orderLineId
	 * @param score
	 * @param isAnonymous
	 * @param commentLevel
	 * @return
	 */
	@RequestMapping("commitComment")
	public String commitComment(HttpServletRequest request,
			@RequestParam("skuCode") String skuCode,
			@RequestParam("content") String content,
			@RequestParam(value="skuColor",defaultValue="") String skuColor,
			@RequestParam(value="skuSize",defaultValue="") String skuSize,
			@RequestParam("orderCode") String orderCode,
			@RequestParam("orderLineId") String orderLineId,
			@RequestParam(value="commentLevel") String commentLevel,
			@RequestParam(value="isAnonymous",defaultValue="") String isAnonymous) {
		try {
			String sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if(user == null || user.getMemberId() == null){
				return "redirect:/login/index" ;
			}
			Sku sku = skuService.queryBySkuCode(skuCode);
			String productCode =  sku.getProductCode();
			Long memberId = user.getMemberId();
			MemberSkuComment skuComment = new MemberSkuComment();
			skuComment.setProductCode(productCode);
			skuComment.setMemberId(memberId);
			skuComment.setMemberName(user.getLoginName());
			skuComment.setSourceCode(Integer.valueOf(SourceConstant.WAP_CODE));
			if(StringUtils.isNotBlank(content)){
				skuComment.setCommentContent(content);
			}
			skuComment.setIpAddress(AppConstant.MOBILEPHONE_IP_ADDRESS);
			skuComment.setStatus(CommonConstant.YES);// 已经评价
			skuComment.setOrderCode(orderCode);
			skuComment.setOrderLineId(Long.valueOf(orderLineId));
			skuComment.setDisable(CommonConstant.YES);// 评论后显示
			skuComment.setCommentLevel(commentLevel);
			skuComment.setSkuCode(skuCode);
			skuComment.setSkuColor(skuColor);
			skuComment.setSkuSize(skuSize);
			isAnonymous="on".equals(isAnonymous)?"y":"n";
			skuComment.setIsAnonymous(isAnonymous);
			skuCommentService.add(skuComment);
			orderLineUserService.updateIsComment(Long.valueOf(orderLineId),CommonConstant.YES);// 修改订单为已经评论

		} catch (Exception e) {
			logg.error("OrderMemberSkuCommentController.java  提交订单评论出错", e);
		}
		return "redirect:/order/evaluateList?status=4&evaluateStatus=n";
	}
	
	
	
}
