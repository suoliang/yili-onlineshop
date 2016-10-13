/**
 * 
 */
package com.aladingshop.web.controller.sku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.EmptyValidUtils;

import com.aladingshop.web.util.Constant;
import com.aladingshop.web.vo.MemberCommentVo;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.service.GlobalConfig;
import com.fushionbaby.common.condition.member.MemberCommentQueryCondition;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.LabelConstant;
import com.fushionbaby.common.constants.SortTypeConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.MemberCommentDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.CategoryDto;
import com.fushionbaby.common.dto.sku.LabelCategoryRelationDto;
import com.fushionbaby.common.dto.sku.SelectedSkuDto;
import com.fushionbaby.common.dto.sku.SkuDetailDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.member.MemberCommentFacade;
import com.fushionbaby.facade.biz.common.member.MemberFacade;
import com.fushionbaby.facade.biz.common.sku.CategoryFacade;
import com.fushionbaby.facade.biz.common.sku.MemberSkuCollectFacade;
import com.fushionbaby.facade.biz.common.sku.SkuDetailFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.facade.image.ImageProcess;
import com.fushionbaby.sku.model.SkuKeyWord;

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

	/**
	 * 初始化商品详情信息页面
	 * 
	 * @param skuCode
	 *            商品编号
	 * @param model
	 * @return
	 */
	@RequestMapping("skuDetail")
	public String initSkuDetail(@RequestParam(value = "skuCode", defaultValue = "") String skuCode,
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

			/** 获取当前商品所有分类 */
			List<CategoryDto> categoryDtos = categoryFacade.findParentCategory(StringUtils.EMPTY,skuDetail.getCategoryCode());
			map.addAttribute("catagorys", categoryDtos);

			/** 获取同类商品 */
			this.getSameSkus(skuDetail);

			/** 获取相关搜索关键词以及相关精品推荐 */
			List<SkuDto> tjskuDtos = this.getRecommendSkus(skuDetail);
			map.addAttribute("tjskus", tjskuDtos);
			SkuKeyWord word = skuDetailFacade.findByProductCode(skuDetail.getSelectedSku().getProductCode());
			map.addAttribute("keys",
					com.aladingshop.web.util.StringUtils.arrayToList(word != null ? word.getKeyWords() : null));
			/** 获取相关评论 */
			commentVo = (MemberCommentVo) this.getComment(skuCode, null);

			// 获取当前登录用户
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
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
	@ResponseBody
	@RequestMapping(value = "commentSkipPage", method = RequestMethod.POST)
	public Object commentPageSkip(@RequestParam("level") Integer level, @RequestParam("target") Integer target,
			@RequestParam("skuCode") String skuCode, HttpServletResponse response) {
		MemberCommentQueryCondition queryCondition = new MemberCommentQueryCondition();
		queryCondition.setSkuCode(skuCode);
		queryCondition.setStart((target - 1) * WebConstant.SKU_COMMENT_SIZE);
		queryCondition.setLimit(WebConstant.SKU_COMMENT_SIZE);
		queryCondition.setLevel(level == 0 ? null : String.valueOf(level));

		return Jsonp_data.success(this.getComment(null, queryCondition));
	}

	/**
	 * 点击收藏
	 * 
	 * @param skuCode
	 * @param type
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "collect", method = RequestMethod.POST)
	public Object memberCollect(@RequestParam("skuCodes") String skuCodes, @RequestParam("type") String type,
			HttpServletRequest request) {
		/** 校验当前用户是否登录 */
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		UserDto user = (UserDto) SessionCache.get(sid);
		if (user == null) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}

		String[] skuCodeArr = skuCodes.split(",");
		if (skuCodeArr.length < 1) {
			return Jsonp.error();
		}

		boolean flag = false;

		for (String skuCode : skuCodeArr) {

			if (StringUtils.equals(type, WebConstant.OPEATION_ADD)) {
				Integer addResult = memberSkuCollectFacade.addSkuCollect(skuCode, user);
				if (addResult == AppConstant.HAS_ADD || addResult == AppConstant.SUCCESS_ADD) {
					// return Jsonp.success();
					flag = true;
				} else if (addResult == AppConstant.NOT_FOUND_SKU) {
					return Jsonp.error();
				}
			} else if (StringUtils.equals(type, WebConstant.OPEATION_DEL)
					&& memberSkuCollectFacade.dropCollectBySkuCode(skuCode, user)) {
				flag = true;
				;
			}

		}
		if (flag == true) {
			return Jsonp.success();
		}
		return Jsonp.error();
	}

	/**
	 * 获取相关搜索关键词以及相关精品推荐商品
	 * 
	 * @param skuDetail
	 */
	private List<SkuDto> getRecommendSkus(SkuDetailDto skuDetail) {
		List<SkuDto> results = new ArrayList<SkuDto>();
		/** 获取精品推荐商品列表 */
		String jptjCount = globalConfig.findByCode(GlobalConfigConstant.WEB_JPTJ);
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setLabelCode(LabelConstant.JPTJ);
		queryCondition.setCategoryCode(skuDetail.getCategoryCode());
		queryCondition.setStart((WebConstant.START_INDEX - 1) * NumberUtils.toInt(jptjCount));
		queryCondition.setLimit(NumberUtils.toInt(jptjCount));
		queryCondition.setSortParam(SortTypeConstant.DEFAULT);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_APP_190);
		List<LabelCategoryRelationDto> relList = skuFacade.getSkuListByLabelCategory(queryCondition);
		/** 根据分类获取分类对象 */
		CategoryDto categoryDto = categoryFacade.getCategoryDto(StringUtils.EMPTY,skuDetail.getCategoryCode());
		String parentCode = categoryDto.getParentCode();
		for (LabelCategoryRelationDto labCateRelDto : relList) {
			if (Objects.equals(labCateRelDto.getCategoryCode(), parentCode)) {
				results = labCateRelDto.getSkuDtoList();
				break;
			}
		}

		/** 如果当前分类下无精品商品，则默认推第精品推荐标签下第一个分类商品列表 */
		if (EmptyValidUtils.arrayIsEmpty(results) && !EmptyValidUtils.arrayIsEmpty(relList)) {
			return relList.get(0).getSkuDtoList();
		}

		return results;
	}

	private Object getComment(String skuCode, MemberCommentQueryCondition queryCondition) {
		if (queryCondition != null) {
			/** 评论翻页结果 */
			return commentFacade.getMemberComments(queryCondition);
		}

		MemberCommentVo commentVo = new MemberCommentVo();
		queryCondition = new MemberCommentQueryCondition();
		queryCondition.setSkuCode(skuCode);
		List<MemberCommentDto> all = commentFacade.getMemberComments(queryCondition);
		if (EmptyValidUtils.arrayIsEmpty(all)) {
			/** 如果评论为空则直接返回 */
			return null;
		}

		/** 查询不同类型评论相关量、总页数 */
		commentVo.setAllCommentCount(all.size());
		commentVo.setAllPage((all.size() - 1) / WebConstant.SKU_COMMENT_SIZE + 1);
		for (MemberCommentDto comment : all) {
			if (Objects.equals(comment.getCommentLevel(), Constant.COMMENT_HIGHT)) {
				commentVo.setGoodCommentCount(commentVo.getGoodCommentCount() + 1);
			} else if (Objects.equals(comment.getCommentLevel(), Constant.COMMENT_MIDDLE)) {
				commentVo.setMidCommentCount(commentVo.getMidCommentCount() + 1);
			} else if (Objects.equals(comment.getCommentLevel(), Constant.COMMENT_LOW)) {
				commentVo.setBadCommentCount(commentVo.getBadCommentCount() + 1);
			}
		}

		commentVo.setGoodPage((commentVo.getGoodCommentCount() - 1) / WebConstant.SKU_COMMENT_SIZE + 1);
		commentVo.setMidPage((commentVo.getMidCommentCount() - 1) / WebConstant.SKU_COMMENT_SIZE + 1);
		commentVo.setBadPage((commentVo.getBadCommentCount() - 1) / WebConstant.SKU_COMMENT_SIZE + 1);

		queryCondition.setStart(WebConstant.START_INDEX - 1);
		queryCondition.setLimit(WebConstant.SKU_COMMENT_SIZE);
		/** 全部评论firstPage **/
		commentVo.setAllComment(commentFacade.getMemberComments(queryCondition));

		queryCondition.setLevel(Constant.COMMENT_HIGHT);
		/** 好评firstPage */
		commentVo.setGoodComment(commentFacade.getMemberComments(queryCondition));

		queryCondition.setLevel(Constant.COMMENT_MIDDLE);
		/** 中评firstPage */
		commentVo.setMidComment(commentFacade.getMemberComments(queryCondition));

		queryCondition.setLevel(Constant.COMMENT_LOW);
		/** 差评firstPage */
		commentVo.setBadComment(commentFacade.getMemberComments(queryCondition));

		return commentVo;
	}

	/** 获取同类商品 */
	private void getSameSkus(SkuDetailDto skuDetail) throws Exception {
		/** 获取同类相关商品列表 */
		List<SkuDto> linkSkuList = skuDetailFacade.getLinkSkus(skuDetail.getSelectedSku().getSkuCode());
		if (linkSkuList == null || CollectionUtils.isEmpty(linkSkuList)) {
			skuDetail.setLinkSkus(Collections.<SkuDto> emptyList());
		} else {
			skuDetail.setLinkSkus(linkSkuList);
		}
	}
}
