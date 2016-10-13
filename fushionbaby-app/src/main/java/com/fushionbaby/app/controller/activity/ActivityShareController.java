/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月25日下午3:38:32
 */
package com.fushionbaby.app.controller.activity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.model.ActivityShareRegisterRecord;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.aladingshop.periodactivity.service.ActivityShareRegisterRecordService;
import com.fushionbaby.app.util.ShareConstantApp;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant.CommonCode;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.facade.biz.common.coupon.MemberCouponFacade;
import com.fushionbaby.facade.biz.common.sku.BannerFacade;
import com.fushionbaby.facade.biz.common.sku.SkuDetailFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.model.MemberSkuShareContent;
import com.fushionbaby.member.model.MemberTelephone;
import com.fushionbaby.member.service.MemberExtraInfoService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.member.service.MemberTelephoneService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @description 活动，分享送红包	
 * @author 孟少博
 * @date 2015年9月25日下午3:38:32
 */
@Controller
@RequestMapping("activity/share")
public class ActivityShareController {
	
	private static final Log LOGGER = LogFactory.getLog(ActivityShareController.class);

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
	
	/**注入会员手机号码*/
	@Autowired
	private MemberTelephoneService<MemberTelephone> memberTelephoneService;
	
	/***注入额外会员信息***/
	@Autowired
	private MemberExtraInfoService<MemberExtraInfo> memberExtraInfoService;
	 
	@Autowired
	private MemberCouponFacade memberCouponFacade;
	/** 红包金额常量*/
	private static final String RED_ENVELOPE_AMOUNT = "RED_ENVELOPE_AMOUNT";
	
	/**
	 * 赚红包分享
	 * @param sid
	 * @param shareContent
	 * @param sourceCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("go")
	public Object share(@RequestParam(value="sid",defaultValue="") String sid,
			@RequestParam(value=" ",defaultValue="") String shareContent,
			@RequestParam("sourceCode") String sourceCode){
		try {
			String shareUrl = "";
			
			Jsonp_data obj = (Jsonp_data)this.getInviteCode(sid);
			if(!StringUtils.equals(obj.getResponseCode(),CommonCode.SUCCESS_CODE)){
				return obj;
			}
			
			SysmgrGlobalConfig config  = configService.findByCode(RED_ENVELOPE_AMOUNT);
			String title =  "下载APP，注册输入邀请码："+obj.getData().toString()+"，立得"+config.getValue()+"元哦！";;
			String text =   ShareConstantApp.TEXT; 
			String inviteCode = obj.getData().toString();
			if(StringUtils.equals(SourceConstant.ANDROID_CODE, sourceCode)){
				shareUrl = ShareConstantApp.UPLOAD_URL_ANDROID + "?inviteCode="+inviteCode;
				
			}else if(StringUtils.equals(SourceConstant.IOS_CODE, sourceCode)){
				shareUrl = ShareConstantApp.UPLOAD_URL_IOS+ "?inviteCode="+inviteCode;
			}
			
			MemberSkuShareContent memberSkuShare = new MemberSkuShareContent();
			memberSkuShare.setSourceCode(sourceCode);
			memberSkuShare.setShareContent(shareContent);
			memberSkuShare.setShareUrl(shareUrl);
			Map<String, String> result = new HashMap<String, String>();
			if(StringUtils.isNotBlank(sid)){
				UserDto user = (UserDto) SessionCache.get(sid);
				memberSkuShare.setMemberId(user.getMemberId());
				skuDetailFacade.addmemberSkuShare(user, memberSkuShare);	
			}
			result.put("title",title);
			result.put("shareUrl", shareUrl);
			result.put("text", text);
			return Jsonp_data.success(result);
		} catch (DataAccessException e) {
			return Jsonp.error();
		}
	}
	
	
	
	
	/**
	 * 获取邀请码，用户第一次登录时获取。
	 * @param sid
	 * @return
	 */
	@ResponseBody
	public Object getInviteCode(String sid){
		
		if (StringUtils.isBlank(sid)) {
			return Jsonp_data.newInstance("201",StringUtils.EMPTY ,"未登录，无法获取邀请码！");
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		Long memberId = user.getMemberId();
		
		ActivityShareMember	activityShareMemberByMemberId = activityShareMemberService.findByMemberId(memberId);
		if(activityShareMemberByMemberId!=null && StringUtils.isNotBlank(activityShareMemberByMemberId.getInviteCode())){
			return  Jsonp_data.newInstance("0", activityShareMemberByMemberId.getInviteCode(), "邀请码已经获得过，无需重新生成！");
		}
		String inviteCode = this.randomGetInviteCode();
		
		if(activityShareMemberByMemberId!=null && StringUtils.isBlank(activityShareMemberByMemberId.getInviteCode())){
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
	 * @return
	 */
	private String randomGetInviteCode(){
		String inviteCode = StringUtils.upperCase(RandomNumUtil.getCharacterAndNumber(5), Locale.ENGLISH);
		boolean flag = true;
		while(flag){
			ActivityShareMember activityShareMemberResult = activityShareMemberService.findByInviteCode(inviteCode);
			if(ObjectUtils.equals(null, activityShareMemberResult)){
				flag = false;
				break;
			}
			inviteCode = StringUtils.upperCase( RandomNumUtil.getCharacterAndNumber(5),Locale.ENGLISH);
		}
		return inviteCode;		
	}
	
	/**
	 * 验证邀请码是否存在
	 * @param inviteCode 邀请码
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("checkInviteCode")
	public Object checkInviteCode(String inviteCode){
		if(StringUtils.isBlank(inviteCode)){
			return Jsonp.newInstance("1", "该邀请码为空！");
		}
		ActivityShareMember activityShareMember = activityShareMemberService.findByInviteCode(StringUtils.upperCase(inviteCode,Locale.ENGLISH));
		if(activityShareMember==null){
			return Jsonp.newInstance("2", "该邀请码不存在");
		}
		
		return Jsonp.success();
		
	}
	
	
	/**
	 * 邀请码注册用户
	 * @param phone 当前注册的用户
	 * @param smsCode 验证码
	 * @param password
	 * @param registerCode
	 * @param sourceCode
	 * @param inviteCode 邀请码
	 * @param signID 唯一标识码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("shareRegister")
	public Object shareRegister(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac,
			HttpServletRequest request){
		LOGGER.info("邀请码注册接口--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}
		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(data);
		String phone = json.getAsJsonObject().get("phone").getAsString();
		String smsCode = json.getAsJsonObject().get("smsCode").getAsString();
		String password = json.getAsJsonObject().get("password").getAsString();
		String registerCode = json.getAsJsonObject().get("registerCode").getAsString();
		String inviteCode = json.getAsJsonObject().get("inviteCode").getAsString();
		String sourceCode = json.getAsJsonObject().get("sourceCode").getAsString();
		String signId = json.getAsJsonObject().get("signID").getAsString();
		
		if (CheckIsEmpty.isEmpty(phone,  password,  sourceCode,signId)) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		Integer signCount = activityShareRegisterRecordService.getTotalBySignId(signId);
		if(signCount!=null && signCount > 3){
			return Jsonp.newInstance("6", "该设备注册超过上线，注册失败");
		}
		
		if (memberService.findByLoginName(phone) != null) {
			return Jsonp.newInstance("7","此手机号已注册,您可以直接登录!");
		}
		/**获得DataCache里的验证码的值*/
		String randomSmsCode = (String) DataCache.get(registerCode);
		if (!StringUtils.equals(smsCode,randomSmsCode)) {
			return Jsonp.newInstance("8","验证码输入有误!");
		}
		
		if(StringUtils.isBlank(inviteCode)){
			return Jsonp.newInstance("1", "该邀请码为空！");
		}
		
		ActivityShareMember activityShareMember = activityShareMemberService.findByInviteCode(StringUtils.upperCase(inviteCode,Locale.ENGLISH));
		if(activityShareMember==null){
			return Jsonp.newInstance("2", "该邀请码不存在");
		}
		
		SysmgrGlobalConfig config  = configService.findByCode(RED_ENVELOPE_AMOUNT);
		if(config==null || StringUtils.isBlank(config.getValue())  || !NumberUtils.isNumber(config.getValue())){
			return Jsonp.newInstance("3", "没有配置红包金额");
		}
		BigDecimal amount = new BigDecimal(config.getValue());
		
		/**添加到会员表*/
		Member member = memberService.addMember(phone,password,sourceCode,request);
		Long memberId = member.getId();
		
		
		ActivityShareMember findActivityShareMember = activityShareMemberService.findByMemberId(memberId);
		ActivityShareRegisterRecord activityShareRegisterRecord = activityShareRegisterRecordService.findBymemberRegisterId(memberId);
		if(findActivityShareMember!=null || activityShareRegisterRecord != null){
			return Jsonp.newInstance("4", "该用户已经注册过,不是新注册的用户。");
		}
		
		/*添加到会员手机表*/
		memberTelephoneService.addMemberPhone(memberId, phone);
		/*添加到额外会员表*/
		memberExtraInfoService.addMemberExtraInfo(memberId);
		
		/* 添加通过邀请码注册的用户记录*/
		activityShareRegisterRecord = new ActivityShareRegisterRecord();
		activityShareRegisterRecord.setMemberSharesId(activityShareMember.getMemberId());
		activityShareRegisterRecord.setMemberRegisterId(memberId);
		activityShareRegisterRecord.setGainRedEnvelope(amount);
		activityShareRegisterRecordService.add(activityShareRegisterRecord);
		
		/* 添加新用户在活动分享表中的信息*/
		ActivityShareMember newActivityShareMember = new ActivityShareMember();
		newActivityShareMember.setMemberId(memberId);
		newActivityShareMember.setGrandGainRedEnvelope(amount);
		newActivityShareMember.setExistingRedEnvelope(amount);
		 if(activityShareMemberService.findByMemberId(memberId)!=null){
			 activityShareMemberService.update(newActivityShareMember);
		 }else{
			 activityShareMemberService.add(newActivityShareMember);
		 }

		/* 修改该邀请码所属的用户累计红包数量和可以红包数量*/
		activityShareMember.setGrandGainRedEnvelope(activityShareMember.getGrandGainRedEnvelope().add(amount));
		activityShareMember.setExistingRedEnvelope(activityShareMember.getExistingRedEnvelope().add(amount));
		activityShareMemberService.update(activityShareMember);
		LOGGER.info("新会员注册 "+member.getId()+"赠送优惠券十元活动  begin");
		memberCouponFacade.registerSendCoupon(member.getId());
		LOGGER.info("新会员注册"+member.getId()+" 赠送优惠券十元活动  end");
		
		
		/**销毁注册标识码*/
		DataCache.remove(registerCode);
		return Jsonp.success();
	}
	
	
	/***
	 * 注册测试用
	 * @param phone
	 * @param smsCode
	 * @param password
	 * @param registerCode
	 * @param sourceCode
	 * @param signId
	 * @param inviteCode
	 * @param request
	 * @return
	 */
	@Deprecated
	@ResponseBody
	@RequestMapping("shareRegister2")
	public Object shareRegister(@RequestParam(value = "phone",defaultValue = "")String phone,
			@RequestParam(value = "smsCode",defaultValue = "")String smsCode,
			@RequestParam(value = "password",defaultValue = "")String password,
			@RequestParam(value = "registerCode",defaultValue = "")String registerCode,
			@RequestParam(value="sourceCode",defaultValue="")String sourceCode,
			@RequestParam(value="signID") String signId,
			String inviteCode,HttpServletRequest request){
		
		Integer signCount = activityShareRegisterRecordService.getTotalBySignId(signId);
		if(signCount!=null && signCount > 3){
			return Jsonp.newInstance("6", "该设备注册超过上线，注册失败");
		}
		if (CheckIsEmpty.isEmpty(phone,  password,  sourceCode)) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		if (memberService.findByLoginName(phone) != null) {
			return Jsonp.error("此手机号已注册,您可以直接登录!");
		}
		String randomSmsCode = (String) DataCache.get(registerCode);
		if (!StringUtils.equals(smsCode,randomSmsCode)) {
			return Jsonp.error("验证码输入有误!");
		}
		
		if(StringUtils.isBlank(inviteCode)){
			return Jsonp.newInstance("1", "该邀请码为空！");
		}
		
		ActivityShareMember activityShareMember = activityShareMemberService.findByInviteCode(StringUtils.upperCase(inviteCode,Locale.ENGLISH));
		if(activityShareMember==null){
			return Jsonp.newInstance("2", "该邀请码不存在");
		}
		
		SysmgrGlobalConfig config  = configService.findByCode(RED_ENVELOPE_AMOUNT);
		if(config==null || StringUtils.isBlank(config.getValue())  || !NumberUtils.isNumber(config.getValue())){
			return Jsonp.newInstance("3", "没有配置红包金额");
		}
		BigDecimal amount = new BigDecimal(config.getValue());
		
		/**添加到会员表*/
		Member member = memberService.addMember(phone,password,sourceCode,request);
		Long memberId = member.getId();
		
		
		ActivityShareMember findActivityShareMember = activityShareMemberService.findByMemberId(memberId);
		ActivityShareRegisterRecord activityShareRegisterRecord = activityShareRegisterRecordService.findBymemberRegisterId(memberId);
		if(findActivityShareMember!=null || activityShareRegisterRecord != null){
			return Jsonp.newInstance("4", "该用户已经注册过,不是新注册的用户。");
		}
		
		memberTelephoneService.addMemberPhone(memberId, phone);
		memberExtraInfoService.addMemberExtraInfo(memberId);
		
		activityShareRegisterRecord = new ActivityShareRegisterRecord();
		activityShareRegisterRecord.setMemberSharesId(activityShareMember.getMemberId());
		activityShareRegisterRecord.setMemberRegisterId(memberId);
		activityShareRegisterRecord.setGainRedEnvelope(amount);
		activityShareRegisterRecordService.add(activityShareRegisterRecord);
		
		ActivityShareMember newActivityShareMember = new ActivityShareMember();
		newActivityShareMember.setMemberId(memberId);
		newActivityShareMember.setGrandGainRedEnvelope(amount);
		newActivityShareMember.setExistingRedEnvelope(amount);
		 if(activityShareMemberService.findByMemberId(memberId)!=null){
			 activityShareMemberService.update(newActivityShareMember);
		 }else{
			 activityShareMemberService.add(newActivityShareMember);
		 }

		activityShareMember.setGrandGainRedEnvelope(activityShareMember.getGrandGainRedEnvelope().add(amount));
		activityShareMember.setExistingRedEnvelope(activityShareMember.getExistingRedEnvelope().add(amount));
		activityShareMemberService.update(activityShareMember);
		return Jsonp.success();
	}
	
	/**
	 * 我的红包
	 * @param sid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("myRedEnvlopeAmount")
	public Object myRedEnvlopeAmount(String sid){
		
		if (StringUtils.isBlank(sid)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		Long memberId = user.getMemberId();
		ActivityShareMember	activityShareMember= activityShareMemberService.findByMemberId(memberId);
		if(activityShareMember == null){
			activityShareMember = new ActivityShareMember();
 		}
		
		BeanNullConverUtil.nullConver(activityShareMember);
		String existingRedEnvelope  = NumberFormatUtil.numberFormat(activityShareMember.getExistingRedEnvelope());
		activityShareMember.setExistingRedEnvelope(new BigDecimal(existingRedEnvelope) );
		
		return  Jsonp_data.success( activityShareMember);
	}
	
	
	
}
