/**
 * 
 */
package com.aladingshop.wap.controller.sku;

import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import util.EmptyValidUtils;

import com.aladingshop.wap.util.Constant;
import com.aladingshop.wap.vo.MemberCommentVo;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.service.GlobalConfig;
import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.condition.member.MemberCommentQueryCondition;
import com.fushionbaby.common.constants.CartConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.dto.MemberCommentDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.SelectedSkuDto;
import com.fushionbaby.common.dto.sku.SkuDetailDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartAddFacade;
import com.fushionbaby.facade.biz.common.member.MemberCommentFacade;
import com.fushionbaby.facade.biz.common.member.MemberFacade;
import com.fushionbaby.facade.biz.common.sku.CategoryFacade;
import com.fushionbaby.facade.biz.common.sku.MemberSkuCollectFacade;
import com.fushionbaby.facade.biz.common.sku.SkuDetailFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.facade.image.ImageProcess;

/**
 * @description 商品control
 * @author 孙涛
 * @date 2015年8月7日下午4:21:51
 */
@Controller
@RequestMapping("/sku")
public class SkuDetailController {
	private static Log LOGGER = LogFactory.getLog(SkuDetailController.class);
	@Autowired
	private SkuFacade skuFacade;
	@Autowired
	private SkuDetailFacade skuDetailFacade;
	@Autowired
	private MemberFacade memberFacade;
	@Autowired
	private MemberSkuCollectFacade memberSkuCollectFacade;
	@Autowired
	private MemberCommentFacade commentFacade;
	@Autowired
	private CategoryFacade categoryFacade;
	@Autowired
	private GlobalConfig globalConfig;
	@Autowired
	private ImageProcess imageProcess;
	@Autowired
	private ShoppingCartAddFacade addFacade;

	/**
	 * 初始化商品详情信息页面
	 * 
	 * @param skuCode
	 *            商品编号
	 * @param model
	 * @return
	 */
	@RequestMapping("skuDetail")
	public String initSkuDetail(
			@RequestParam(value = "skuCode", defaultValue = "") String skuCode,
			HttpServletRequest request, ModelMap map) {
		if (StringUtils.isBlank(skuCode)) {
			return "error";
		}

		SkuDetailDto skuDetail = null;
		MemberCommentVo commentVo = new MemberCommentVo();
		try {
			SelectedSkuDto selectedSkuDto = new SelectedSkuDto();
			selectedSkuDto.setSkuCode(skuCode);
			skuDetail = skuDetailFacade.getSkuDetailModel(selectedSkuDto);
			if (ObjectUtils.notEqual(null, skuDetail)) {
				skuDetail.setSkuImages(imageProcess.getThumImageList(skuCode,
						ImageStandardConstant.IMAGE_STANDARD_PC_800));
			}

			/** 猜你喜欢 */
			this.getSameSkus(skuDetail);
			/** 获取相关评论 */
			commentVo = (MemberCommentVo) this.getComment(skuCode, null);

			// 获取当前登录用户
			String sid = CookieUtil.getCookieValue(request,
					CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if (user != null) {
				skuDetailFacade.addOrUpdateBrowseHistories(user, skuCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取商品详情异常" + e.getMessage());
			map.addAttribute("ERROR", "获取商品详情异常");
		}
		map.addAttribute("result", skuDetail);
		map.addAttribute("mecoment", commentVo);
		/** 获取当前购物车总数 */
		Integer cartCount = this.getCardCount(request);
		map.addAttribute("cartCount", String.valueOf(cartCount));

		return "sku/detail";
	}

	@RequestMapping("skuDetailStatic")
	public ModelAndView initDetailStatic(HttpServletRequest request) {
		/** 获取当前页是否已静态化标识 */
		String flag = String.valueOf(request.getAttribute("flag"));
		String skuCode = request.getParameter("skuCode");
		if (StringUtils.isNotBlank(flag) && Objects.equals(flag, "true")) {
			/** 如果已存在，直接跳html */
			return new ModelAndView("redirect:/detail/" + skuCode + ".html");
		}

		return new ModelAndView("redirect:/sku/skuDetail?skuCode=" + skuCode);
	}

	/**
	 * 评论翻页
	 * 
	 * @param level
	 * @param target
	 * @param skuCode
	 * @param response
	 * @return
	 */
	// @ResponseBody
	// @RequestMapping(value = "commentSkipPage", method = RequestMethod.POST)
	// public Object commentPageSkip(@RequestParam("level") Integer level,
	// @RequestParam("target") Integer target,
	// @RequestParam("skuCode") String skuCode,
	// HttpServletResponse response) {
	// MemberCommentQueryCondition queryCondition = new
	// MemberCommentQueryCondition();
	// queryCondition.setSkuCode(skuCode);
	// queryCondition.setStart((target - 1) * WebConstant.SKU_COMMENT_SIZE);
	// queryCondition.setLimit(WebConstant.SKU_COMMENT_SIZE);
	// queryCondition.setLevel(level == 0 ? null : String.valueOf(level));
	//
	// return Jsonp_data.success(this.getComment(null, queryCondition));
	// }

	@RequestMapping("skuRank")
	public String initskuRank(@RequestParam("skuCode") String skuCode,
			ModelMap map) {
		// 根据skuCode获取商品评论
		MemberCommentVo commentVo = (MemberCommentVo) this.getComment(skuCode,
				null);
		map.addAttribute("mecoment", commentVo);
		return "sku/rank";
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

	/** 获取同类商品 */
	private void getSameSkus(SkuDetailDto skuDetail) throws Exception {
		/** 获取同类相关商品列表 */
		List<SkuDto> linkSkuList = skuDetailFacade.getLinkSkus(skuDetail
				.getSelectedSku().getSkuCode());
		if (linkSkuList == null || CollectionUtils.isEmpty(linkSkuList)) {
			skuDetail.setLinkSkus(Collections.<SkuDto> emptyList());
		} else {
			skuDetail.setLinkSkus(linkSkuList);
		}
	}

	private Integer getCardCount(HttpServletRequest request) {
		String vkey = CookieUtil.getCookieValue(request,
				CartConstant.COOKIE_VISIT_KEY);
		String sid = CookieUtil.getCookieValue(request,
				CookieConstant.COOKIE_SID);

		if (StringUtils.isBlank(vkey) && StringUtils.isBlank(sid)) {
			return null;
		}
		UserDto user = null;
		if (StringUtils.isNotBlank(sid)) {
			user = (UserDto) SessionCache.get(sid);
		}
		Integer pnum = 0;
		if (StringUtils.isNotBlank(vkey) && ObjectUtils.equals(null, user)) {
			ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
			shoppingCartBo.setVisitKey(vkey);
			pnum = addFacade.getRedisCount(shoppingCartBo);
		} else if (ObjectUtils.notEqual(null, user)) {
			ShoppingCartQueryCondition cartQueryCondition = new ShoppingCartQueryCondition();
			cartQueryCondition.setIsSelected(CommonConstant.YES);
			cartQueryCondition.setMemberId(user.getMemberId());
			pnum = addFacade.getSelectedCartSkuCountByMemberId(cartQueryCondition);
		}

		return pnum;
	}
}
