package com.fushionbaby.app.controller.order;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.CommentLevelEnum;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.service.OrderLineUserService;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberSkuComment;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.member.service.MemberSkuCommentService;
import com.fushionbaby.sku.model.Sku;
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

	@Autowired
	private UserFacade userFacade;
	
	/** 商品评价service */
	@Autowired
	private MemberSkuCommentService<MemberSkuComment> skuCommentService;

	/**
	 * 会员操作service
	 */
	@Autowired
	private MemberService<Member> memberService;
	
	/**
	 * 订单行信息查询service
	 */
	@Autowired
	private OrderLineUserService<OrderLineUser> soSoLineService;
	
	@Autowired
	private SkuService skuService;
	
	
	private static final Log logg=LogFactory.getLog(OrderMemberSkuCommentController.class);
	
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
	@ResponseBody
	@RequestMapping("commitComment")
	public Object commitComment(@RequestParam("sid") String sid,
			@RequestParam("skuCode") String skuCode,
			@RequestParam("content") String content,
			@RequestParam("sourceCode") String sourceCode,
			@RequestParam(value="skuColor",defaultValue="") String skuColor,
			@RequestParam(value="skuSize",defaultValue="") String skuSize,
			@RequestParam("orderCode") String orderCode,
			@RequestParam("orderLineId") String orderLineId,
			@RequestParam(value="score",defaultValue="5") Integer score,
			@RequestParam("isAnonymous") String isAnonymous) {
		try {
			
			boolean result=	CheckIsEmpty.isEmpty(sid, skuCode,isAnonymous,orderLineId,orderCode,sourceCode);
			if(result){
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto user = userFacade.getLatestUserBySid(sid);
			if (ObjectUtils.equals(user, null)) {
				return Jsonp.error(CommonMessage.NO_LOGIN);
			}
			Sku sku = skuService.queryBySkuCode(skuCode);
			String productCode =  sku.getProductCode();
			Long memberId = user.getMemberId();
			MemberSkuComment skuComment = new MemberSkuComment();
			skuComment.setProductCode(productCode);
			skuComment.setMemberId(memberId);
			skuComment.setMemberName(user.getLoginName());
			skuComment.setSourceCode(Integer.valueOf(sourceCode));
			if(StringUtils.isNotBlank(content)){
				skuComment.setCommentContent(content);
			}
			skuComment.setIpAddress(AppConstant.MOBILEPHONE_IP_ADDRESS);
			skuComment.setStatus(CommonConstant.YES);// 已经评价
			skuComment.setOrderCode(orderCode);
			skuComment.setOrderLineId(Long.valueOf(orderLineId));
			skuComment.setDisable(CommonConstant.YES);// 评论后显示
			skuComment.setScore(score);
			skuComment.setSkuCode(skuCode);
			skuComment.setSkuColor(skuColor);
			skuComment.setSkuSize(skuSize);
			skuComment.setIsAnonymous(isAnonymous);
			
			if(score>3){
				skuComment.setCommentLevel(CommentLevelEnum.GOOD.getCode());
			}else if(score>1){
				skuComment.setCommentLevel(CommentLevelEnum.SECONDARY.getCode());
			}else{
				skuComment.setCommentLevel(CommentLevelEnum.LOWEST.getCode());
			}
			skuCommentService.add(skuComment);
			soSoLineService.updateIsComment(Long.valueOf(orderLineId),CommonConstant.YES);// 修改订单为已经评论

			return Jsonp.success();
		} catch (Exception e) {
			logg.error("OrderMemberSkuCommentController.java  提交订单评论出错", e);
			return Jsonp.error();
		}
	}
	
	
	
}
