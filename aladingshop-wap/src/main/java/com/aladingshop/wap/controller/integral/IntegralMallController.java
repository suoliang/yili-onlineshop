package com.aladingshop.wap.controller.integral;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import util.EmptyValidUtils;

import com.aladingshop.wap.util.Constant;
import com.aladingshop.wap.vo.MemberCommentVo;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.condition.member.MemberCommentQueryCondition;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.dto.MemberCommentDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.SelectedSkuDto;
import com.fushionbaby.common.dto.sku.SkuDetailDto;
import com.fushionbaby.common.enums.SkuEpointLabelCodeEnum;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.facade.biz.common.member.MemberCommentFacade;
import com.fushionbaby.facade.biz.common.sku.SkuDetailFacade;
import com.fushionbaby.facade.image.ImageProcess;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.SkuEpoint;
import com.fushionbaby.sku.service.SkuEpointService;

/**
 * @description 积分商城 -- WAP端和APP端通用
 * @author 索亮
 * @date 2016年1月25日上午9:41:06
 */
@Controller
@RequestMapping("/integral")
public class IntegralMallController {
	/**注入日志*/
	private static final Log LOGGER = LogFactory.getLog(IntegralMallController.class);
	
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private SkuEpointService skuEpointService;
	@Autowired
	private ImageProcess imageProcess;
	@Autowired
	private SkuDetailFacade skuDetailFacade;
	@Autowired
	private MemberCommentFacade commentFacade;
	
	/***
	 * 
	 * @param sid  登录用户标识
	 * @param type 类型-用于判断是不是APP(传值为APP)端请求的
	 * @return
	 */
	@RequestMapping("homeShow")
	public String homeShow(HttpServletRequest request,
			@RequestParam(value="sid",defaultValue="")String sid,
			@RequestParam(value="type",defaultValue="")String type,
			ModelMap model){
		try {
			if (StringUtils.isBlank(sid)) {
				sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
			}
			UserDto user = (UserDto) SessionCache.get(sid);
			if (ObjectUtils.equals(user, null)) {
				return "login/login";
			}
			List<SkuEpoint> sellList = skuEpointService.findByLabelCode(SkuEpointLabelCodeEnum.HOT_EXCHANGE.getCode());
			for (SkuEpoint skuEpoint : sellList) {
				skuEpoint.setImgPath(imageProcess.getOrigImagePath(skuEpoint.getSkuCode()));
			}
			
			Long memberId = user.getMemberId();
			Member member = memberService.findById(memberId);
			
			model.addAttribute("sellList", sellList);
			model.addAttribute("user", user);
			model.addAttribute("member", member);
			model.addAttribute("type", type);
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("积分商城显示出错", e);
		}
		return "point/point-reward";
	}
	
	/***
	 * 我能兑换
	 * @param sid  登录用户标识
	 * @param type 类型-用于判断是不是APP(传值为APP)端请求的
	 * @return
	 */
	@RequestMapping("exChange")
	public String iCanExchange(
			HttpServletRequest request,
			@RequestParam(value="sid",defaultValue="")String sid,
			@RequestParam(value="type",defaultValue="")String type,
			ModelMap model){
		try {
			if (StringUtils.isBlank(sid)) {
				sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
			}
			UserDto user = (UserDto) SessionCache.get(sid);
			if (ObjectUtils.equals(user, null)) {
				return "login/login";
			}
			List<SkuEpoint> exChangeList = skuEpointService.findByLabelCode(SkuEpointLabelCodeEnum.COMMON_EXCHANGE.getCode());
			for (SkuEpoint skuEpoint : exChangeList) {
				skuEpoint.setImgPath(imageProcess.getOrigImagePath(skuEpoint.getSkuCode()));
			}
			model.addAttribute("exChangeList", exChangeList);
			model.addAttribute("user",user);
			model.addAttribute("type", type);
		} catch (Exception e) {
			LOGGER.error("我能兑换模块出错", e);
		}
		return "point/point-to-cash";
	}
	
	
	/***
	 * 跳转到积分规则页面 
	 */
	@RequestMapping("jumpToRule")
	public String jumpToRule(){
		return "point/point-rule";
	}
	
	
	/**
	 * 初始化商品详情信息页面
	 * 
	 * @param skuCode
	 *            商品编号
	 * @param model
	 * @return
	 */
	@RequestMapping("pointSkuDetail")
	public String initSkuDetail(
			@RequestParam(value = "skuCode", defaultValue = "") String skuCode,
			@RequestParam(value = "sid", defaultValue = "") String sid,
			HttpServletRequest request, ModelMap model) {
		if (StringUtils.isBlank(skuCode)) {
			model.addAttribute("errorMsg", "商品编号为空");
			return "error";
		}
		if (StringUtils.isBlank(sid)) {
			sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
		}
		SkuDetailDto skuDetail = null;
		MemberCommentVo commentVo = new MemberCommentVo();
		try {
			SelectedSkuDto selectedSkuDto = new SelectedSkuDto();
			selectedSkuDto.setSkuCode(skuCode);
			skuDetail = skuDetailFacade.getSkuDetailModel(selectedSkuDto);
			if (ObjectUtils.notEqual(null, skuDetail)) {
				skuDetail.setSkuImages(imageProcess.getThumImageList(skuCode,ImageStandardConstant.IMAGE_STANDARD_PC_800));
				skuDetail.setPriceIntegral(skuEpointService.findBySkuCode(skuCode).getNeedEpoint().toString());
			}

			/** 获取相关评论 */
			commentVo = (MemberCommentVo) this.getComment(skuCode, null);

			/** 获取当前登录用户 */
			UserDto user = (UserDto) SessionCache.get(sid);
			if (user != null) {
				skuDetailFacade.addOrUpdateBrowseHistories(user, skuCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取商品详情异常" + e.getMessage());
			model.addAttribute("ERROR", "获取商品详情异常");
		}
		model.addAttribute("result", skuDetail);
		model.addAttribute("skuCode", skuCode);
		model.addAttribute("sid", sid);
		model.addAttribute("mecoment", commentVo);

		return "point/point-detail";
	}
	
	private Object getComment(String skuCode,
			MemberCommentQueryCondition queryCondition) {
		if (queryCondition != null) {
			/** 评论翻页结果 */
			return commentFacade.getMemberComments(queryCondition);
		}

		MemberCommentVo commentVo = new MemberCommentVo();
		queryCondition = new MemberCommentQueryCondition();
		queryCondition.setSkuCode(skuCode);
		List<MemberCommentDto> all = commentFacade
				.getMemberComments(queryCondition);
		if (EmptyValidUtils.arrayIsEmpty(all)) {
			/** 如果评论为空则直接返回 */
			return null;
		}

		List<MemberCommentDto> goods = new ArrayList<MemberCommentDto>();
		List<MemberCommentDto> mids = new ArrayList<MemberCommentDto>();
		List<MemberCommentDto> bads = new ArrayList<MemberCommentDto>();

		/** 查询不同类型评论相关量、总页数 */
		for (MemberCommentDto comment : all) {
			if (Objects.equals(comment.getCommentLevel(),
					Constant.COMMENT_HIGHT)) {
				goods.add(comment);
			} else if (Objects.equals(comment.getCommentLevel(),
					Constant.COMMENT_MIDDLE)) {
				mids.add(comment);
			} else if (Objects.equals(comment.getCommentLevel(),
					Constant.COMMENT_LOW)) {
				bads.add(comment);
			}
		}

		commentVo.setAllCommentCount(all.size());
		commentVo.setAllComment(all);
		commentVo.setGoodComment(goods);
		commentVo.setGoodCommentCount(goods.size());
		commentVo.setMidComment(mids);
		commentVo.setMidCommentCount(mids.size());
		commentVo.setBadComment(bads);
		commentVo.setBadCommentCount(bads.size());

		return commentVo;
	}
	
}
