/**aldingshop.com 上海一里网络科技有限公司
 */
package com.aladingshop.wap.controller.activity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.model.ActivityShareRegisterRecord;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.aladingshop.periodactivity.service.ActivityShareRegisterRecordService;
import com.aladingshop.wap.util.RedToAlbConstantWap;
import com.aladingshop.wap.vo.RedEnvelopeConsumeDto;
import com.aladingshop.wap.vo.RedEnvelopeDetailDto;
import com.aladingshop.wap.vo.RedEnvelopeGainDto;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.service.GlobalConfig;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.AlabaoPayTypeConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.enums.RedEnvelopeUseStatusEnum;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @description 红包详情
 * @author 孟少博
 * @date 2015年10月22日下午4:32:58
 */
@Controller
@RequestMapping("redEnvelope")
public class RedEnvelopeController {

	private static final Log LOGGER = LogFactory
			.getLog(RedEnvelopeController.class);

	@Autowired
	private ActivityShareMemberService<ActivityShareMember> activityShareMemberService;
	@Autowired
	private ActivityShareRegisterRecordService<ActivityShareRegisterRecord> activityShareRegisterRecordService;
	@Autowired
	private ActivityRedEnvlopeUseRecordService<ActivityRedEnvlopeUseRecord> activityRedEnvlopeUseRecordService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	@Autowired
	private GlobalConfig globalConfig;

	/**
	 * 获取详情记录
	 * 
	 * @param sid
	 * @return
	 */
	@RequestMapping("detailRecord")
	public ModelAndView myRedEnvlopeDetailRecord(HttpServletRequest request) {
		RedEnvelopeDetailDto redEnvelopeDetailDto = new RedEnvelopeDetailDto();

		String sid = CookieUtil.getCookieValue(request,
				CookieConstant.COOKIE_SID);
		if (StringUtils.isBlank(sid)) {
			return new ModelAndView("redirect:/login/index");
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		Long memberId = user.getMemberId();
		redEnvelopeDetailDto.setMemberId(memberId);
		ActivityShareMember activityShareMember = activityShareMemberService
				.findByMemberId(memberId);

		if (activityShareMember != null) {
			redEnvelopeDetailDto.setGainAmountCount(activityShareMember
					.getGrandGainRedEnvelope());
			redEnvelopeDetailDto.setRedBalance(activityShareMember
					.getExistingRedEnvelope());
		}
		redEnvelopeDetailDto.setGainRecord(this.getGainDtoList(memberId));

		List<RedEnvelopeConsumeDto> consumeDtoList = this
				.getConsumeDtoList(memberId);
		BigDecimal consumeAmount = new BigDecimal(0);
		for (RedEnvelopeConsumeDto redEnvelopeConsumeDto : consumeDtoList) {
			if (redEnvelopeConsumeDto.getConsumeAmount() == null) {
				continue;
			}
			consumeAmount = consumeAmount.add(redEnvelopeConsumeDto
					.getConsumeAmount());
		}
		redEnvelopeDetailDto.setConsumeRecord(consumeDtoList);
		redEnvelopeDetailDto.setConsumeAmount(consumeAmount);
		ModelAndView model = new ModelAndView();
		model.addObject("result", redEnvelopeDetailDto);
		model.setViewName("bonus/spe-bonus-detail");

		return model;
	}

	/**
	 * 获取收益记录
	 * 
	 * @param memberId
	 * @return
	 */
	private List<RedEnvelopeGainDto> getGainDtoList(Long memberId) {

		List<RedEnvelopeGainDto> gainDtoList = new ArrayList<RedEnvelopeGainDto>();

		List<ActivityShareRegisterRecord> registerRecords = activityShareRegisterRecordService
				.findByMemberSharesId(memberId);
		if (!CollectionUtils.isEmpty(registerRecords)) {
			for (ActivityShareRegisterRecord activityShareRegisterRecord : registerRecords) {
				RedEnvelopeGainDto gainDto = new RedEnvelopeGainDto();
				gainDto.setCreateTime(DateFormatUtils.format(
						activityShareRegisterRecord.getCreateTime(),
						"yyyy-MM-dd HH:mm:ss"));
				gainDto.setGainAmount(activityShareRegisterRecord
						.getGainRedEnvelope());
				gainDto.setRegisterMemberId(activityShareRegisterRecord
						.getMemberRegisterId());
				Member member = memberService
						.findById(activityShareRegisterRecord
								.getMemberRegisterId());
				gainDto.setContent(member != null ? "用户  "
						+ member.getLoginName() + " 注册" : StringUtils.EMPTY);
				gainDtoList.add(gainDto);
			}
		}
		ActivityShareRegisterRecord registerRecord = activityShareRegisterRecordService
				.findBymemberRegisterId(memberId);
		if (registerRecord == null) {
			return gainDtoList;
		}
		RedEnvelopeGainDto gainDto = new RedEnvelopeGainDto();
		gainDto.setCreateTime(DateFormatUtils.format(
				registerRecord.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		gainDto.setGainAmount(registerRecord.getGainRedEnvelope());
		gainDto.setRegisterMemberId(registerRecord.getMemberRegisterId());
		Member member = memberService.findById(registerRecord
				.getMemberRegisterId());
		gainDto.setContent(member != null ? "我的账号  " + member.getLoginName()
				+ " 注册" : StringUtils.EMPTY);
		gainDtoList.add(gainDto);

		Collections.sort(gainDtoList, new Comparator<RedEnvelopeGainDto>() {
			public int compare(RedEnvelopeGainDto o1, RedEnvelopeGainDto o2) {

				try {
					Date time1 = DateUtils.parseDate(o1.getCreateTime(),
							"yyyy-MM-dd HH:mm:ss");
					Date time2 = DateUtils.parseDate(o2.getCreateTime(),
							"yyyy-MM-dd HH:mm:ss");
					return time2.compareTo(time1);

				} catch (ParseException e) {
					e.printStackTrace();
				}

				return 0;
			}

		});

		return gainDtoList;
	}

	/**
	 * 获取消费记录
	 * 
	 * @param memberId
	 * @return
	 */
	private List<RedEnvelopeConsumeDto> getConsumeDtoList(Long memberId) {
		List<RedEnvelopeConsumeDto> consumeDtoList = new ArrayList<RedEnvelopeConsumeDto>();
		List<ActivityRedEnvlopeUseRecord> useRecordList = activityRedEnvlopeUseRecordService
				.findByMemberId(memberId);
		for (ActivityRedEnvlopeUseRecord activityRedEnvlopeUseRecord : useRecordList) {
			if (StringUtils.equals(activityRedEnvlopeUseRecord.getUseStatus()
					.toString(), RedEnvelopeUseStatusEnum.MARCH.getCode())
					|| StringUtils.equals(activityRedEnvlopeUseRecord
							.getUseStatus().toString(),
							RedEnvelopeUseStatusEnum.SUCCESS.getCode())) {
				RedEnvelopeConsumeDto consumeDto = new RedEnvelopeConsumeDto();
				consumeDto.setOrderCode(activityRedEnvlopeUseRecord
						.getOrderCode());
				consumeDto.setCreateTime(DateFormatUtils.format(
						activityRedEnvlopeUseRecord.getCreateTime(),
						"yyyy-MM-dd HH:mm:ss"));
				consumeDto.setConsumeAmount(activityRedEnvlopeUseRecord
						.getRedEnvelopeAmount());
				consumeDto.setContent(StringUtils.trimToEmpty("订单 "
						+ consumeDto.getOrderCode() + " 消费"));
				consumeDtoList.add(consumeDto);
			}
		}
		consumeDtoList.addAll(this.getTransferConsumeDtoList(memberId));
		Collections.sort(consumeDtoList,
				new Comparator<RedEnvelopeConsumeDto>() {
					public int compare(RedEnvelopeConsumeDto o1,
							RedEnvelopeConsumeDto o2) {

						try {
							Date time1 = DateUtils.parseDate(
									o1.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
							Date time2 = DateUtils.parseDate(
									o2.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
							return time2.compareTo(time1);

						} catch (ParseException e) {
							e.printStackTrace();
						}

						return 0;
					}

				});

		return consumeDtoList;
	}

	/** 获取转出金额 */
	private List<RedEnvelopeConsumeDto> getTransferConsumeDtoList(Long memberId) {

		List<RedEnvelopeConsumeDto> consumeDtoList = new ArrayList<RedEnvelopeConsumeDto>();

		List<AlabaoShiftToRecord> recordList = alabaoShiftToRecordService
				.findAllByMemberId(memberId);
		for (AlabaoShiftToRecord alabaoShiftToRecord : recordList) {
			if (StringUtils.equals(alabaoShiftToRecord.getShiftToAccountType(),
					AlabaoPayTypeConstant.ALABAOAPPRED)) {
				RedEnvelopeConsumeDto consumeDto = new RedEnvelopeConsumeDto();
				consumeDto.setCreateTime(DateFormatUtils.format(
						alabaoShiftToRecord.getCreateTime(),
						"yyyy-MM-dd HH:mm:ss"));
				consumeDto.setConsumeAmount(alabaoShiftToRecord
						.getTransferMoney());
				consumeDto.setContent(StringUtils.trimToEmpty("转入如意消费卡 "));
				consumeDto.setOrderCode(StringUtils.EMPTY);
				consumeDtoList.add(consumeDto);
			}
		}

		return consumeDtoList;
	}

	/**
	 * 去转账页
	 * 
	 * @param sid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("goRdAlb")
	public Object goRdAlb(String sid) {
		if (StringUtils.isBlank(sid)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		/** APP会员id */
		Long memberId = user.getMemberId();

		ActivityShareMember activityShareMember = activityShareMemberService
				.findByMemberId(memberId);
		String redAmount = "0";
		if (activityShareMember != null
				&& activityShareMember.getExistingRedEnvelope() != null) {
			redAmount = NumberFormatUtil.numberFormat(activityShareMember
					.getExistingRedEnvelope());
		}

		String rnAlbDates = globalConfig
				.findByCode(GlobalConfigConstant.RN_ALB_DATE);
		String[] rnAlbDateArray = StringUtils.split(rnAlbDates, ",");

		StringBuffer rnAlbDateStr = new StringBuffer();
		if (rnAlbDateArray == null || rnAlbDateArray.length < 1) {
			rnAlbDateStr.append("任意一天");

		}
		for (int i = 0; i < rnAlbDateArray.length; i++) {
			rnAlbDateStr.append(rnAlbDateArray[i] + "号");
			if (i != rnAlbDateArray.length - 1) {
				rnAlbDateStr.append("、");
			}
		}
		String rullRnAlb = globalConfig
				.findByCode(GlobalConfigConstant.FULL_RN_ALB);
		String ruleContent = RedToAlbConstantWap.RULE_CONTENT;
		String[] rules = StringUtils.split(ruleContent, "|");

		rules[0] = StringUtils.replace(rules[0], "{1}",
				StringUtils.isBlank(rullRnAlb) ? "0.01" : rullRnAlb);
		rules[1] = StringUtils
				.replace(rules[1], "{2}", rnAlbDateStr.toString());

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("redAmount", redAmount);
		data.put("rules", rules);

		return Jsonp_data.success(data);
	}

	/**
	 * 红包转入如意卡
	 * 
	 * @param sid
	 *            用户SID
	 * @param alabaoSid
	 *            如意消费卡SID
	 * @param amount
	 *            转入金额
	 * @return
	 */
	@ResponseBody
	@RequestMapping("rdAlbShift")
	public Object checkRed(@RequestParam(value = "data") String data,
			@RequestParam(value = "mac") String mac) {
		LOGGER.info("验证转出有效性如意宝接口action--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}
		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(data);
		String sid = json.getAsJsonObject().get("sid").getAsString();
		String alabaoSid = json.getAsJsonObject().get("alabaoSid")
				.getAsString();
		String amount = json.getAsJsonObject().get("amount").getAsString();

		String rullRnAlb = globalConfig
				.findByCode(GlobalConfigConstant.FULL_RN_ALB);

		BigDecimal rullAmount = StringUtils.isBlank(rullRnAlb) ? BigDecimal
				.valueOf(0.01) : new BigDecimal(rullRnAlb);

		if (new BigDecimal(amount).compareTo(rullAmount) == -1) {
			return Jsonp.newInstance("1", "转入金额必须最少" + rullAmount + "元");
		}
		if (StringUtils.isBlank(sid)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache
				.get(alabaoSid);
		if (ObjectUtils.equals(alabaoUserDto, null)) {
			return Jsonp.noLoginError("如意消费卡未登录或重新登录");
		}

		AlabaoAccount alabaoAccount = alabaoAccountService
				.findById(alabaoUserDto.getAlabaoId());
		if (ObjectUtils.equals(alabaoAccount, null)) {
			return Jsonp.error("无此记录");
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		/** APP会员id */
		Long memberId = user.getMemberId();
		ActivityShareMember activityShareMember = activityShareMemberService
				.findByMemberId(memberId);
		if (activityShareMember == null) {
			return Jsonp.error();
		}
		if (activityShareMember.getExistingRedEnvelope() == null) {
			return Jsonp.error();
		}
		if (activityShareMember.getExistingRedEnvelope().compareTo(rullAmount) == -1) {
			return Jsonp.error();
		}
		if (!checkRule()) {
			return Jsonp.newInstance("1", "不符合规则");
		}
		if (activityShareMember.getExistingRedEnvelope().compareTo(
				new BigDecimal(amount)) == -1) {
			return Jsonp.newInstance("1", "不符合规则");
		}

		AlabaoShiftToRecord alabaoShiftToRecord = new AlabaoShiftToRecord();
		alabaoShiftToRecord.setAccount(alabaoAccount.getAccount());
		/** 必须是如意宝的memberId */
		alabaoShiftToRecord.setMemberId(alabaoAccount.getMemberId());
		alabaoShiftToRecord
				.setShiftToAccountType(AlabaoPayTypeConstant.ALABAOAPPRED);
		alabaoShiftToRecord.setTransferMoney(new BigDecimal(amount));
		alabaoShiftToRecordService.add(alabaoShiftToRecord);

		LOGGER.info("如意宝账户现有金额:" + alabaoAccount.getBalance() + "转入金额" + amount);
		alabaoAccount.setBalance(alabaoAccount.getBalance().add(
				new BigDecimal(amount)));
		alabaoAccountService.updateByMemberId(alabaoAccount);

		activityShareMember.setExistingRedEnvelope(activityShareMember
				.getExistingRedEnvelope().subtract(new BigDecimal(amount)));
		activityShareMemberService.update(activityShareMember);

		return Jsonp.success();
	}

	/**
	 * 验证规则
	 * 
	 * @return
	 */
	private boolean checkRule() {

		int date = Calendar.getInstance().get(Calendar.DATE);

		String rnAlbDates = globalConfig
				.findByCode(GlobalConfigConstant.RN_ALB_DATE);
		if (StringUtils.isBlank(rnAlbDates)) {
			return true;
		}
		String[] rnAlbDateArray = StringUtils.split(rnAlbDates, ",");
		for (String rnAlbDate : rnAlbDateArray) {
			if (date == Integer.parseInt(rnAlbDate)) {
				return true;
			}
		}

		return false;

	}

}
