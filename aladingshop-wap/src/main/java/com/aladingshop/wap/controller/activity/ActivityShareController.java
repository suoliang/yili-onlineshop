/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月25日下午3:38:32
 */
package com.aladingshop.wap.controller.activity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.model.ActivityShareRegisterRecord;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.aladingshop.periodactivity.service.ActivityShareRegisterRecordService;
import com.aladingshop.wap.util.ShareConstantWap;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant.CommonCode;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.facade.biz.common.sku.BannerFacade;
import com.fushionbaby.facade.biz.common.sku.SkuDetailFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.model.MemberSkuShareContent;
import com.fushionbaby.member.model.MemberTelephone;
import com.fushionbaby.member.service.MemberExtraInfoService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.member.service.MemberTelephoneService;

/**
 * @description 活动，分享送红包
 * @author 孟少博
 * @date 2015年9月25日下午3:38:32
 */
@Controller
@RequestMapping("activity/share")
public class ActivityShareController {

	@Autowired
	private ActivityShareMemberService<ActivityShareMember> activityShareMemberService;
	@Autowired
	private ActivityShareRegisterRecordService<ActivityShareRegisterRecord> activityShareRegisterRecordService;
	@Autowired
	private ActivityRedEnvlopeUseRecordService<ActivityRedEnvlopeUseRecord> activityRedEnvlopeUseRecordService;
	@Autowired
	private SysmgrGlobalConfigService configService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private BannerFacade bannerFacade;
	@Autowired
	private SkuDetailFacade skuDetailFacade;

	/** 注入会员手机号码 */
	@Autowired
	private MemberTelephoneService<MemberTelephone> memberTelephoneService;

	/*** 注入额外会员信息 ***/
	@Autowired
	private MemberExtraInfoService<MemberExtraInfo> memberExtraInfoService;

	/** 红包金额常量 */
	private static final String RED_ENVELOPE_AMOUNT = "RED_ENVELOPE_AMOUNT";

	/**
	 * 跳转到红包首页
	 * 
	 * @param inviteCode
	 *            邀请码
	 * @return
	 */
	@RequestMapping("bonusIndex")
	public ModelAndView bonusIndexRequest(HttpServletRequest request,
			ModelMap map) {
		String sid = CookieUtil.getCookieValue(request,
				CookieConstant.COOKIE_SID);
		if (StringUtils.isBlank(sid)) {
			return new ModelAndView("redirect:/login/index");
		}

		return new ModelAndView("bonus/spe-bonus");
	}

	/**
	 * 赚红包分享
	 * 
	 * @param sid
	 * @param shareContent
	 * @param sourceCode
	 * @return
	 */
	@RequestMapping("shareBonus")
	public ModelAndView share(HttpServletRequest request, ModelAndView model) {
		try {
			String sid = CookieUtil.getCookieValue(request,
					CookieConstant.COOKIE_SID);
			Jsonp_data obj = (Jsonp_data) this.getInviteCode(sid);
			if (!StringUtils.equals(obj.getResponseCode(),
					CommonCode.SUCCESS_CODE)) {
				return new ModelAndView("redirect:/login/index");
			}

			SysmgrGlobalConfig config = configService
					.findByCode(RED_ENVELOPE_AMOUNT);
			String title = String.format(ShareConstantWap.SHARECONTENT, obj
					.getData().toString(), config.getValue());

			MemberSkuShareContent memberSkuShare = new MemberSkuShareContent();
			memberSkuShare.setSourceCode(SourceConstant.WAP_CODE);
			memberSkuShare.setShareContent(ShareConstantWap.TITLE);
			memberSkuShare.setShareUrl(String.format(ShareConstantWap.URL, obj
					.getData().toString()));

			if (StringUtils.isNotBlank(sid)) {
				UserDto user = (UserDto) SessionCache.get(sid);
				memberSkuShare.setMemberId(user.getMemberId());
				skuDetailFacade.addmemberSkuShare(user, memberSkuShare);
			}
			model.addObject("title", title);
			model.addObject("share", memberSkuShare);
			model.setViewName("bonus/spe-bonus-earn");
			return model;
		} catch (DataAccessException e) {
			model.addObject("errorMsg", "请求出错，请重试");
			model.setViewName("error");
			return model;
		}
	}

	/**
	 * 获取邀请码，用户第一次登录时获取。
	 * 
	 * @param sid
	 * @return
	 */
	@ResponseBody
	public Object getInviteCode(String sid) {

		if (StringUtils.isBlank(sid)) {
			return Jsonp_data.newInstance("201", StringUtils.EMPTY,
					"未登录，无法获取邀请码！");
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		Long memberId = user.getMemberId();

		ActivityShareMember activityShareMemberByMemberId = activityShareMemberService
				.findByMemberId(memberId);
		if (activityShareMemberByMemberId != null
				&& StringUtils.isNotBlank(activityShareMemberByMemberId
						.getInviteCode())) {
			return Jsonp_data.newInstance("0",
					activityShareMemberByMemberId.getInviteCode(),
					"邀请码已经获得过，无需重新生成！");
		}
		String inviteCode = this.randomGetInviteCode();

		if (activityShareMemberByMemberId != null
				&& StringUtils.isBlank(activityShareMemberByMemberId
						.getInviteCode())) {
			activityShareMemberByMemberId.setInviteCode(inviteCode);
			activityShareMemberService.update(activityShareMemberByMemberId);
			return Jsonp_data.success(inviteCode);
		}
		ActivityShareMember activityShareMember = new ActivityShareMember();
		activityShareMember.setMemberId(memberId);
		activityShareMember.setInviteCode(inviteCode);
		activityShareMember.setExistingRedEnvelope(BigDecimal.valueOf(0));
		activityShareMember.setGrandGainRedEnvelope(BigDecimal.valueOf(0));
		activityShareMemberService.add(activityShareMember);

		return Jsonp_data.success(inviteCode);

	}

	/**
	 * 随机生成邀请码
	 * 
	 * @return
	 */
	private String randomGetInviteCode() {
		String inviteCode = StringUtils.upperCase(
				RandomNumUtil.getCharacterAndNumber(5), Locale.ENGLISH);
		boolean flag = true;
		while (flag) {
			ActivityShareMember activityShareMemberResult = activityShareMemberService
					.findByInviteCode(inviteCode);
			if (ObjectUtils.equals(null, activityShareMemberResult)) {
				flag = false;
				break;
			}
			inviteCode = StringUtils.upperCase(
					RandomNumUtil.getCharacterAndNumber(5), Locale.ENGLISH);
		}
		return inviteCode;
	}

	/**
	 * 邀请码注册用户
	 * 
	 * @param sid
	 *            当前住的用户
	 * @param inviteCode
	 *            邀请码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("shareRegister")
	public Object shareRegister(
			@RequestParam(value = "phone", defaultValue = "") String phone,
			@RequestParam(value = "smsCode", defaultValue = "") String smsCode,
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "registerCode", defaultValue = "") String registerCode,
			String inviteCode, HttpServletRequest request) {

		if (CheckIsEmpty.isEmpty(phone, password)) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		if (memberService.findByLoginName(phone) != null) {
			return Jsonp.error("此手机号已注册,您可以直接登录!");
		}
		/** 获得DataCache里的验证码的值 */
		String randomSmsCode = (String) DataCache.get(registerCode);
		if (!StringUtils.equals(smsCode, randomSmsCode)) {
			return Jsonp.error("验证码输入有误!");
		}

		if (StringUtils.isBlank(inviteCode)) {
			return Jsonp.newInstance("1", "该邀请码为空！");
		}

		ActivityShareMember activityShareMember = activityShareMemberService
				.findByInviteCode(StringUtils.upperCase(inviteCode,
						Locale.ENGLISH));
		if (activityShareMember == null) {
			return Jsonp.newInstance("2", "该邀请码不存在");
		}

		SysmgrGlobalConfig config = configService
				.findByCode(RED_ENVELOPE_AMOUNT);
		if (config == null || StringUtils.isBlank(config.getValue())
				|| !NumberUtils.isNumber(config.getValue())) {
			return Jsonp.newInstance("3", "没有配置红包金额");
		}
		BigDecimal amount = new BigDecimal(config.getValue());

		/** 添加到会员表 */
		Member member = memberService.addMember(phone, password,
				SourceConstant.WAP_CODE, request);
		Long memberId = member.getId();

		ActivityShareMember findActivityShareMember = activityShareMemberService
				.findByMemberId(memberId);
		ActivityShareRegisterRecord activityShareRegisterRecord = activityShareRegisterRecordService
				.findBymemberRegisterId(memberId);
		if (findActivityShareMember != null
				|| activityShareRegisterRecord != null) {
			return Jsonp.newInstance("4", "该用户已经注册过,不是新注册的用户。");
		}

		/* 添加到会员手机表 */
		memberTelephoneService.addMemberPhone(memberId, phone);
		/* 添加到额外会员表 */
		memberExtraInfoService.addMemberExtraInfo(memberId);

		/* 添加通过邀请码注册的用户记录 */
		activityShareRegisterRecord = new ActivityShareRegisterRecord();
		activityShareRegisterRecord.setMemberSharesId(activityShareMember
				.getMemberId());
		activityShareRegisterRecord.setMemberRegisterId(memberId);
		activityShareRegisterRecord.setGainRedEnvelope(amount);
		activityShareRegisterRecordService.add(activityShareRegisterRecord);

		/* 添加新用户在活动分享表中的信息 */
		ActivityShareMember newActivityShareMember = new ActivityShareMember();
		newActivityShareMember.setMemberId(memberId);
		newActivityShareMember.setGrandGainRedEnvelope(amount);
		newActivityShareMember.setExistingRedEnvelope(amount);
		if (activityShareMemberService.findByMemberId(memberId) != null) {
			activityShareMemberService.update(newActivityShareMember);
		} else {
			activityShareMemberService.add(newActivityShareMember);
		}

		/* 修改该邀请码所属的用户累计红包数量和可以红包数量 */
		activityShareMember.setGrandGainRedEnvelope(activityShareMember
				.getGrandGainRedEnvelope().add(amount));
		activityShareMember.setExistingRedEnvelope(activityShareMember
				.getExistingRedEnvelope().add(amount));
		activityShareMemberService.update(activityShareMember);
		return Jsonp.success();
	}

	/**
	 * 我的红包
	 * 
	 * @param sid
	 * @return
	 */
	@RequestMapping("myRedEnvlopeAmount")
	public ModelAndView myRedEnvlopeAmount(HttpServletRequest request) {
		String sid = CookieUtil.getCookieValue(request,
				CookieConstant.COOKIE_SID);
		if (StringUtils.isBlank(sid)) {
			return new ModelAndView("redirect:/login/index");
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		Long memberId = user.getMemberId();
		ActivityShareMember activityShareMember = activityShareMemberService
				.findByMemberId(memberId);
		if (activityShareMember == null) {
			activityShareMember = new ActivityShareMember();
		}

		BeanNullConverUtil.nullConver(activityShareMember);
		String existingRedEnvelope = NumberFormatUtil
				.numberFormat(activityShareMember.getExistingRedEnvelope());
		activityShareMember.setExistingRedEnvelope(new BigDecimal(
				existingRedEnvelope));

		ModelAndView model = new ModelAndView();
		model.addObject("result", activityShareMember);
		model.setViewName("bonus/spe-bonus");

		return model;
	}

	/**
	 * 红包使用
	 * 
	 * @sid
	 * @param redAmount
	 *            红包使用金额
	 * @param totalAmount
	 *            总价格
	 * @return
	 */
	@ResponseBody
	@RequestMapping("redEnvlopeUse")
	public Object rednvlopeUse(String sid, BigDecimal redAmount,
			BigDecimal totalAmount) {
		UserDto user = (UserDto) SessionCache.get(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.error(CommonMessage.NO_LOGIN);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		BigDecimal realityAmount = new BigDecimal(0);

		if (totalAmount.compareTo(BigDecimal.valueOf(0)) == 0) {
			return Jsonp.newInstance("2", "合计金额是0，不需要再使用了。");
		}
		ActivityShareMember activityShareMember = activityShareMemberService
				.findByMemberId(user.getMemberId());
		redAmount = activityShareMember.getExistingRedEnvelope();
		BigDecimal useAmount = redAmount.compareTo(totalAmount) > -1 ? totalAmount
				: redAmount;
		realityAmount = totalAmount.subtract(useAmount);
		result.put("useAmount", useAmount);

		result.put("balanceAmount", redAmount.subtract(useAmount));

		result.put("realityAmount", realityAmount);

		return Jsonp_data.success(result);
	}

}
