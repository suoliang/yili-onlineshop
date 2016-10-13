package com.fushionbaby.wap.controller.activities;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.AdvertisementConfigConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.FocusPicDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.web.PageSetDto;
import com.fushionbaby.common.dto.sysactivity.SysactivityFamilyCommentDto;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.sysactivity.condition.SysActivityFamilyCommentQueryCondition;
import com.fushionbaby.sysactivity.model.SysActivityFamily;
import com.fushionbaby.sysactivity.model.SysActivityFamilyComment;
import com.fushionbaby.sysactivity.service.SysActivityFamilyCommentService;
import com.fushionbaby.sysactivity.service.SysActivityFamilyService;
import com.fushionbaby.wap.controller.AbstractMainController;
import com.fushionbaby.wap.dto.sysactivity.SysactivityFamilyDto;
import com.fushionbaby.wap.util.ImageConstantWap;

/**
 * @author mengshaobo 亲子活动详情
 */
@Controller
@RequestMapping("/parchiart")
public class FamilyController extends AbstractMainController {

	@Autowired
	private SysActivityFamilyCommentService<SysActivityFamilyComment> sysActivityFamilyCommentService;
	@Autowired
	private SysActivityFamilyService<SysActivityFamily> sysActivityFamilyService;
	/** 日志*/
	private static final Log log=LogFactory.getLog(FamilyController.class);

	/**
	 * 初始化 亲子活动页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("children")
	public String children(ModelMap model) {
		List<FocusPicDto> pics = this.getPicList(AdvertisementConfigConstant.WEB_FAMILY);
		List<SysActivityFamily> familyList = sysActivityFamilyService.findAll();
		List<SysactivityFamilyDto> familyDtos = new ArrayList<SysactivityFamilyDto>();
		for (SysActivityFamily family : familyList) {
			SysactivityFamilyDto familyDto= createFamilyDto(family);
			familyDtos.add(familyDto);
		}
		if (pics != null && pics.size() >= 1) {
				model.addAttribute("pic", pics.get(0));
		}
		model.addAttribute("familyList", familyDtos);
		return "activities/children";
	}

	/***
	 * 在循环中设置familyDto的值
	 * @param family
	 * @return
	 */
	private SysactivityFamilyDto createFamilyDto(SysActivityFamily family) {
		SysactivityFamilyDto familyDto = new SysactivityFamilyDto();
		familyDto.setBannerUrl(ImageConstantWap.SYS_ACTIVITY_FAMILY_SERVER_PATH + "/"
				+ family.getWebBannerUrl());
		familyDto.setTitle(family.getTitle());
		familyDto.setFamilyId(family.getId());
		return familyDto;
	}

	/**
	 * 分页查询活动评论信息
	 * 
	 * @param curPage
	 *            当前页数
	 * @param familyId
	 *            文章编号
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("comment")
	public Object comment(
			@RequestParam(value = "cur_page", defaultValue = WebConstant.DEFAULT_PAGE_INDEX + "") Integer curPage,
			@RequestParam("familyId") Long familyId) {
		SysActivityFamilyCommentQueryCondition queryCondition = setCommentQueryCondition(curPage,familyId);
		BasePagination page = sysActivityFamilyCommentService.getListPage(queryCondition);
		@SuppressWarnings("unchecked")
		List<SysActivityFamilyComment> commentList = (List<SysActivityFamilyComment>) page
				.getResult();
		PageSetDto pageSet = new PageSetDto();
		pageSet.setAmount(page.getTotal());
		pageSet.setCurPage(curPage);
		pageSet.setTotalPageByAmount(page.getTotal());
		pageSet.setCommentList(this.getCommentList(commentList));
		return Jsonp_data.success(pageSet);
	}

	/***
	 * 设置评论的查询条件
	 * @param curPage
	 * @param familyId
	 * @return
	 */
	private SysActivityFamilyCommentQueryCondition setCommentQueryCondition(
			Integer curPage, Long familyId) {
		SysActivityFamilyCommentQueryCondition queryCondition = new SysActivityFamilyCommentQueryCondition();
		queryCondition.setLimit(WebConstant.CHIDREN_COMMENT_SIZE);
		queryCondition.setStart((curPage - 1) * WebConstant.CHIDREN_COMMENT_SIZE);
		queryCondition.setArticleId(familyId);
		return queryCondition;
	}

	/***
	 * 获得页面显示的评论信息
	 * 
	 * @param commentList
	 * @return
	 */
	private List<SysactivityFamilyCommentDto> getCommentList(
			List<SysActivityFamilyComment> commentList) {
		List<SysactivityFamilyCommentDto> commentDtos = new ArrayList<SysactivityFamilyCommentDto>();
		if (!CollectionUtils.isEmpty(commentList)) {
			for (SysActivityFamilyComment comment : commentList) {
				SysactivityFamilyCommentDto commentDto = new SysactivityFamilyCommentDto();
				commentDto.setCommentTime(DateFormat.timeToHHmmString(comment.getCommentTime()));
				commentDto.setCommentDate(DateFormat.dateTimeToDateString(comment.getCommentTime()));
				commentDto.setContent(comment.getCommentContent());
				commentDto.setNickName(comment.getCommentNickname());
				commentDtos.add(commentDto);
			}

		}
		return commentDtos;
	}

	/**
	 * 通过亲子活动号获取文章内容
	 * 
	 * @param familyId
	 *            亲子活动号
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("article-content")
	public String getArtcleContent(@RequestParam("familyId") Long familyId,
			HttpServletRequest request, ModelMap model) {
		UserDto userDto = null;
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			userDto = (UserDto) SessionCache.get(sid);
		} catch (Exception e) {
			log.error("获取亲子课程文章内容失败", e);
		}
		SysActivityFamily family = sysActivityFamilyService.findById(familyId);
		if (StringUtils.isNotBlank(family.getWebBannerUrl())) {
			model.addAttribute("pic",
					ImageConstantWap.SYS_ACTIVITY_FAMILY_SERVER_PATH + "/" + family.getWebBannerUrl());
		}

		model.addAttribute("user", userDto);
		model.addAttribute("centent", family.getContent());
		model.addAttribute("title", family.getTitle());
		model.addAttribute("familyId", familyId);
		return "activities/par-chi-art";
	}

	/**
	 * 添加评论信息
	 * 
	 * @param familyId
	 *            亲子活动文章号
	 * @param memberId
	 *            会员号
	 * @param userName
	 *            会员名字
	 * @param content
	 *            评论内容
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addComment")
	public Object addComment(SysactivityFamilyDto familyDto){
		
		SysActivityFamilyComment comment = new SysActivityFamilyComment();
		comment.setArticleId(familyDto.getFamilyId());
		comment.setMemberId(familyDto.getMemberId());
		comment.setCommentContent(familyDto.getContent());
		comment.setCommentNickname(familyDto.getUserName());
		try {
			sysActivityFamilyCommentService.add(comment);
		} catch (DataAccessException e) {
			Jsonp.error();
			log.error("亲子课程添加评论失败", e);
		}
		return this.comment(WebConstant.DEFAULT_PAGE_INDEX, familyDto.getFamilyId());
	}
}
