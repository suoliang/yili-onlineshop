package com.fushionbaby.web.controller.activities;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.AdvertisementConfigConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.FocusPicDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.web.OutDoorActivityDtoWeb;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.sysactivity.model.SysActivityActivities;
import com.fushionbaby.sysactivity.model.SysActivityActivitiesApply;
import com.fushionbaby.sysactivity.service.SysActivityActivitiesApplyService;
import com.fushionbaby.sysactivity.service.SysActivityActivitiesService;
import com.fushionbaby.web.controller.AbstractMainController;
import com.fushionbaby.web.util.ImageConstantWeb;

/***
 * 户外活动
 * 
 * @author glc 2014-11-19
 */
@Controller
@RequestMapping("/activity")
public class OutdoorController extends AbstractMainController {
	/** 系统活动*/
	@Autowired
	private SysActivityActivitiesService<SysActivityActivities> sysActivityActivitiesService;
	/** 申请活动*/
	@Autowired
	private SysActivityActivitiesApplyService<SysActivityActivitiesApply> sysActivityActivitiesApplyService;
	/** 记录日志 */
	private static final Log log = LogFactory.getLog(VoteController.class);
	/***
	 * 获取web页面显示列表
	 */
	@RequestMapping("/list")
	public String getList(HttpServletRequest request, ModelMap model) {
		try {
			List<FocusPicDto> pics = getPicList(AdvertisementConfigConstant.WEB_ACTIVITY);
			List<SysActivityActivities> activities = sysActivityActivitiesService.getListPageType(1, 10);
			if (pics != null && pics.size() >= 1) {
				model.addAttribute("pic", pics.get(0));
			}
			for(SysActivityActivities activitie : activities){
				activitie.setWebBannerUrl(ImageConstantWeb.SYS_ACTIVITY_INTRO_SERVER_PATH + "/" + activitie.getWebBannerUrl());
			}
			model.addAttribute("activities", activities);
		} catch (DataAccessException e) {
			log.error("户外活动列表页面加载失败", e);
		}
		return "activities/outside";
	}

	/***
	 * 详情页
	 */
	@RequestMapping("/detail")
	public String activityDetail(ModelMap model,@RequestParam(value = "id") long id) {
		try {
			List<FocusPicDto> pics = getPicList(AdvertisementConfigConstant.WEB_ACTIVITY);
			SysActivityActivities ac = sysActivityActivitiesService.findById(id);
			if (pics != null && pics.size() >= 1) {
				model.addAttribute("pic", pics.get(0));
			}
			ac.setWebIntroduceUrl(ImageConstantWeb.SYS_ACTIVITY_INTRO_SERVER_PATH + "/"
					+ ac.getWebIntroduceUrl());
			ac.setPlacePictureUrl(ImageConstantWeb.SYS_ACTIVITY_INTRO_SERVER_PATH + "/"
					+ ac.getPlacePictureUrl());
			model.addAttribute("ac", ac);
		} catch (Exception e) {
			log.error("户外活动详情页面加载失败", e);
		}
		return "activities/outside-art";
	}

	/**
	 * 户外活动报名
	 * 
	 * @param activitiesId
	 * @param activitiesName
	 * @param number
	 * @param phone
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/apply")
	public Object apply(OutDoorActivityDtoWeb outDoorDto,HttpServletRequest request) {

		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isEmpty(sid)) {
			return Jsonp.error("请先登录再报名");
		}
		if (StringUtils.isEmpty(outDoorDto.getPhone())) {
			return Jsonp.error("请输入手机号码");
		}
		if (outDoorDto.getNumber() == null || outDoorDto.getNumber() <= 0) {
			return Jsonp.error("请选择报名人数");
		}
		UserDto userDto = (UserDto) SessionCache.get(sid);
		long member_id = userDto.getMember_id();
		SysActivityActivitiesApply activityApply = sysActivityActivitiesApplyService.query(member_id, outDoorDto.getActivitiesId());
		if (activityApply != null) {
			return Jsonp.error("亲，你已经报过名了！请联系客服");
		}
		try {
			outDoorDto.setLoginName(userDto.getLogin_name());
			submitActivitiesApplyAndSave(member_id,outDoorDto);
			return Jsonp.success();
		} catch (DataAccessException e) {
			log.error("户外活动报名申请失败", e);
			return Jsonp.error("报名申请失败！");
		}
	}

	/***
	 * 添加报名信息
	 * @param member_id
	 * @param outDoorDto
	 */
	private void submitActivitiesApplyAndSave(long member_id,
			OutDoorActivityDtoWeb outDoorDto) {
		SysActivityActivitiesApply entity = new SysActivityActivitiesApply();
		entity.setMemberId(member_id);
		entity.setMemberName(outDoorDto.getLoginName());
		entity.setMemberPhone(outDoorDto.getPhone());
		entity.setApplyNumber(outDoorDto.getNumber());
		entity.setActivitiesId(outDoorDto.getActivitiesId());
		entity.setActivitiesName(outDoorDto.getActivitiesName());
		entity.setApplyTime(new Date());
		entity.setIsTouch(CommonConstant.NO);
		entity.setSourceCode(AdvertisementConfigConstant.AD_SOURCE_WEB);
		entity.setIsOk(CommonConstant.NO);
		sysActivityActivitiesApplyService.add(entity);
	}

	

}
