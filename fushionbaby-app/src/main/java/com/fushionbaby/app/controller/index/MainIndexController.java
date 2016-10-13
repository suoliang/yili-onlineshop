package com.fushionbaby.app.controller.index;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.card.dto.BaseReqDto;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.app.dto.MainIndexResDto;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.google.gson.Gson;

/***
 * app 主页的个人中心部分接口
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月13日下午4:54:13
 */
@Controller
@RequestMapping("index")
public class MainIndexController {

	private static final Log LOGGER=LogFactory.getLog(MainIndexController.class);
	
	/**代金券*/
	@Autowired
	private ActCardService<ActCard> actCardService; 
	/**会员*/
	@Autowired
	private UserFacade userFacade;
	/**分享红包*/
	@Autowired
	private ActivityShareMemberService<ActivityShareMember> activityShareMemberService;
	
	@ResponseBody
	@RequestMapping("getMyHeadInfo")
	public Object getMyHeadInfo(@RequestParam("data") String data, @RequestParam("mac") String mac) {
		try {
			LOGGER.info("获取个人中心  配置接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			BaseReqDto reDto = gson.fromJson(data, BaseReqDto.class);
			if (CheckIsEmpty.isEmpty(reDto.getSid())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto userDto= userFacade.getLatestUserBySid(reDto.getSid());
			if (userDto == null) {
				return Jsonp.noLoginError("请先登录");
			}
			List<ActCard> actCardList=this.actCardService.findByMemberId(userDto.getMemberId());
			ActivityShareMember activityShareMember=activityShareMemberService.findByMemberId(userDto.getMemberId());
			BigDecimal redMoney=new BigDecimal(0);
			if(ObjectUtils.notEqual(null, activityShareMember))
				redMoney=activityShareMember.getExistingRedEnvelope();
			MainIndexResDto resDto=new MainIndexResDto();
			resDto.setCouponNum(String.valueOf(actCardList.size()));
			resDto.setIntegral(String.valueOf((userDto.getEpoints()==null?new BigDecimal(0):userDto.getEpoints()).setScale(0, BigDecimal.ROUND_DOWN)));
			resDto.setRedCardMoney(NumberFormatUtil.numberFormat(redMoney));
			return Jsonp_data.success(resDto);
		} catch (Exception e) {
			LOGGER.error("MainIndexController.java getMyHeadInfo 请求出错" + e);
			return Jsonp.error();
		}
	}
}
