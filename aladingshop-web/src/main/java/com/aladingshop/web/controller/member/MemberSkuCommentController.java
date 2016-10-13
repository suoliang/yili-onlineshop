package com.aladingshop.web.controller.member;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.condition.member.MemberCommentQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.MemberCommentDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.facade.biz.common.member.MemberCommentFacade;
import com.fushionbaby.facade.biz.common.order.OrderMemCenterFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.member.model.MemberSkuComment;
import com.google.gson.Gson;

/***
 * 会员评论（针对具体商品）
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月25日下午2:34:51
 */
@Controller
@RequestMapping("/memberComment")
public class MemberSkuCommentController {
	
	private static final Log CommentLog=LogFactory.getLog(MemberSkuCommentController.class);

	@Autowired
	private SkuFacade  skuFacade;
	
	@Autowired
	private MemberCommentFacade memberCommentFacade;
	
	@Autowired
	private OrderMemCenterFacade orderMemCenterFacade;
	
	@RequestMapping("/evaluating.htm/{skuCode}/{orderLineId}")
	public String goToEvaluate(@PathVariable("skuCode") String skuCode,@PathVariable("orderLineId") String orderLineId,HttpServletRequest request,Model model){
		String sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
		UserDto user = (UserDto) SessionCache.get(sid);
		if (user == null) {
			return  "login/login";
		}
	   SkuDto skuDto=this.skuFacade.findBySkuCode(skuCode);
	   MemberCommentQueryCondition queryCondition=new MemberCommentQueryCondition();
	   queryCondition.setSkuCode(skuCode);
	   List<MemberCommentDto> MemberSkuCommentList=this.memberCommentFacade.getMemberComments(queryCondition);
	   DecimalFormat   df=new DecimalFormat("#.00");
	   double percent=100.00d;
	   if(MemberSkuCommentList.size()>0){
		   int allScore=MemberSkuCommentList.size()*5;
		   int totalScore=0; 
		   for (MemberCommentDto memberCommentDto : MemberSkuCommentList) {
			  int score=memberCommentDto.getScore();
			      totalScore=totalScore+score;
		   }
		   percent=percent*totalScore/allScore;
	   }
	   percent=Double.valueOf(df.format(percent));
	   model.addAttribute("skuDto",skuDto);
	   model.addAttribute("percent", percent);
	   model.addAttribute("orderLineId", orderLineId);
		CommentLog.info("web:MemberSkuCommentController.java 记录开始");
		return "membercenter/evaluating";
	}
	
	
	
	/***
	 * 提交评论
	 * @param skuCode
	 * @param score
	 * @param commentContent
	 * @param orderLineId
	 * @param isAnonymous
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/submitComment")
	public Object submitComment(@RequestParam("skuCode") String skuCode,
			@RequestParam("score") Integer score,
			@RequestParam("commentContent") String commentContent,
			@RequestParam("orderLineId") String orderLineId,
			@RequestParam("isAnonymous") String isAnonymous,
			HttpServletRequest request){
		String sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
		UserDto user = (UserDto) SessionCache.get(sid);
		if (user == null) {
			return new Gson().toJson(false);
		}
		String commentLevel=String.valueOf(1);
		if(score<3){
			commentLevel=String.valueOf(3);
		}
		if(score==3){
			commentLevel=String.valueOf(2);
		}
		SkuDto sku=this.skuFacade.findBySkuCode(skuCode);
		OrderLineUser orderLine=this.orderMemCenterFacade.findByOrderLineId(orderLineId,CommonConstant.NO);
		if(ObjectUtils.equals(sku, null)||ObjectUtils.equals(orderLine, null))
		  return new Gson().toJson(false);
		MemberSkuComment skuComment=new MemberSkuComment();
		skuComment.setCommentContent(commentContent);
		skuComment.setCommentLevel(commentLevel);
		skuComment.setCreateTime(new Date());
		skuComment.setDisable(CommonConstant.NO);
		skuComment.setIpAddress(GetIpAddress.getIpAddr(request));
		skuComment.setIsAnonymous(isAnonymous=CommonConstant.YES.equals(isAnonymous)?CommonConstant.YES:CommonConstant.NO);
		skuComment.setMemberId(user.getMemberId());
		skuComment.setMemberName(user.getLoginName());
		skuComment.setOrderCode(orderLine.getOrderCode());
		skuComment.setOrderLineId(Long.valueOf(orderLineId));
		skuComment.setProductCode(sku.getProductCode());
		skuComment.setScore(score);
		skuComment.setSkuCode(skuCode);
		skuComment.setSkuColor(sku.getColor());
		skuComment.setSkuSize(sku.getSize());
		skuComment.setSourceCode(Integer.valueOf(SourceConstant.WEB_CODE));
		skuComment.setStatus(CommonConstant.YES);
		this.memberCommentFacade.submitComment(skuComment);
		this.orderMemCenterFacade.findByOrderLineId(orderLineId,CommonConstant.YES);
		return new Gson().toJson(true);
 	}
	
	
}
