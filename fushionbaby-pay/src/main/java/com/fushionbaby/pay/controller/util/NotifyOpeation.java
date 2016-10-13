package com.fushionbaby.pay.controller.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.card.model.MemberCard;
import com.aladingshop.card.model.MemberCardConfig;
import com.aladingshop.card.service.MemberCardConfigService;
import com.aladingshop.card.service.MemberCardService;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.constants.alabao.AlabaoOrderConstant;
import com.fushionbaby.common.constants.alabao.MemberCardConstant;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderBaseUser;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.service.OrderBaseUserService;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.core.service.OrderLineUserService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sms.model.Email;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.model.SmsTypeConfig;
import com.fushionbaby.sms.service.EmailService;
import com.fushionbaby.sms.service.SmsService;
import com.fushionbaby.sms.service.SmsTypeConfigService;

/**
 * @description 调用第三方支付成功后的处理
 * @author 孙涛
 * @date 2015年9月15日上午9:55:28
 */
@Service
public class NotifyOpeation {
	@Autowired
	private MemberCardService<MemberCard> memberCardService;
	/** 订单金融 */
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;
	@Autowired
	private OrderBaseUserService<OrderBaseUser> orderBaseUserService;
	/** 订单行 */
	@Autowired
	private OrderLineUserService<OrderLineUser> orderLineUserService;
	/** 会员 */
	@Autowired
	private MemberService<Member> memberService;
	/** 益多宝卡配置 */
	@Autowired
	private MemberCardConfigService<MemberCardConfig> memberCardConfigService;
	/** 短信 */
	@Autowired
	private SmsService<Sms> smsService;
	/** 短信类型 */
	@Autowired
	private SmsTypeConfigService<SmsTypeConfig> smsTypeConfigService;
	/** 邮件 */
	@Autowired
	private EmailService<Email> emailService;
	/** 如意宝用户 */
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;

	/***
	 * 订单（购买益多宝）支付成功之后进行用户益多宝的绑定
	 * 
	 * @param memberId
	 * @param orderCode
	 * @return
	 */
	protected boolean createMemberCard(Long memberId, String orderCode) {
		try {
			OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(memberId, orderCode);
			/** 如意宝账户和会员绑定，注册者均为手机用户 */
			AlabaoAccount alabao = this.alabaoAccountService.findByMemberId(memberId);
			MemberCard memberCard = new MemberCard();
			String cardNo = memberId + "" + RandomNumUtil.getNumber(13 - memberId.toString().length());
			memberCard.setMemberId(memberId);
			memberCard.setAcount(alabao.getAccount());
			memberCard.setCode(RandomNumUtil.getCharacterAndNumber(20));
			memberCard.setCardNo(cardNo);
			String password = memberId.toString() + RandomNumUtil.getNumber(2);
			memberCard.setPassword(MD5Util.MD5(password));
			String cardConfigId = orderCode.substring(0, orderCode.indexOf(AlabaoOrderConstant.ALABAO_ORDER_SIGN));
		
			memberCard.setCardConfigId(Long.valueOf(cardConfigId));
			memberCard.setTotalCorpus(orderFeeUser.getTotalActual());
			memberCard.setTotalRebate(new BigDecimal(0));
			memberCard.setStatus(MemberCardConstant.STATUS_WAIT);
			memberCard.setCreateTime(new Date());
			memberCard.setUpdateTime(new Date());
			this.memberCardService.add(memberCard);
			String phone = alabao.getMobilePhone();
			/** 发送购卡成功短信 */
			String content = smsTypeConfigService.findById(SmsConstant.SMS_TYPE_YIDUOBAO_ID).getSmsTemplate();
			content = content
					.replace(SmsConstant.SMS_TEMLATE_FACEVALUE,
							NumberFormatUtil.numberFormat(orderFeeUser.getTotalActual()))
					.replace(SmsConstant.SMS_TEMPLATE_CODE, cardNo);
			smsService.sendSmsYiDuoBaoCode(content, phone, SourceConstant.WEB_CODE, SmsConstant.SMS_TYPE_YIDUOBAO_ID);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/***
	 * @description 获取订单的价格,此处传sid方便区分用户
	 * @param sid 用户登录APP的标识
	 * @param orderCode 订单号
	 * @return
	 */
	protected String getOrderPrice(String sid,String orderCode){
		UserDto user = (UserDto) SessionCache.get(sid);
		if (ObjectUtils.equals(user, null)) {
			return null;
		}
		OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(user.getMemberId(), orderCode);
		if (ObjectUtils.equals(orderFeeUser, null)) {
			return null;
		}
		DecimalFormat df = new DecimalFormat("0");
		BigDecimal totalActual = new BigDecimal(df.format(orderFeeUser.getTotalActual().multiply(
				new BigDecimal(100))));
		return totalActual.toString();
	}
	
	/**
	 * 更新本地系统的订单状态，财务改为已付款
	 * 
	 * @param memberId
	 * @param orderCode
	 * @param paymemberType
	 * @return
	 */
	protected boolean updateLocalState(Long memberId, String orderCode, String paymemberType) {
		try {
			// 本地系统,订单状态-审核中,财务状态已经付款;
			OrderFinanceUser orderFinanceUser = orderFinanceUserService.findByMemberIdAndOrderCode(memberId, orderCode);
			orderFinanceUser.setPaymentType(paymemberType);
			orderFinanceUser.setFinanceStatus(CommonConstant.YES);
			orderFinanceUser.setPaymentCompleteTime(new Date());
			orderFinanceUserService.updateByMemberIdAndOrderCode(orderFinanceUser);
			
			OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(memberId, orderCode);
			OrderBaseUser orderBaseUser = new OrderBaseUser();
			orderBaseUser.setOrderCode(orderCode);
			orderBaseUser.setMemberId(memberId);
			String isOpenOrderAudit = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.IS_OPEN_ORDER_AUDIT).getValue();
			String orderStatus = OrderConfigServerEnum.AUDIT.getCode();
			if (StringUtils.equalsIgnoreCase(CommonConstant.YES, isOpenOrderAudit)) {
				/** 此处需求:一次性购200以上就要审核，200以下和购买2次以上不审 */
				List<OrderFinanceUser> list = orderFinanceUserService.findByMIdAndStatus(memberId, CommonConstant.YES);
				BigDecimal totalActual = orderFeeUser.getTotalActual();
				if (list.size()>2 && totalActual.compareTo(BigDecimal.valueOf(200))<0) {
					orderStatus = OrderConfigServerEnum.SUCCESS_AUDIT.getCode();
				}
			}
			orderBaseUser.setOrderStatus(orderStatus);
			orderBaseUserService.updateOrderStatus(orderBaseUser);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 使用如意宝支付时,更新费用表中如意宝的优惠价格
	 * @param memberId
	 * @param orderCode
	 * @param alabaoCheapAmount
	 * @param afDiscount
	 * @return
	 */
	protected boolean updateOrderFee(Long memberId, String orderCode,BigDecimal alabaoCheapAmount,BigDecimal afDiscount) {
		try {
			OrderFeeUser orderFeeUser = new OrderFeeUser();
			orderFeeUser.setMemberId(memberId);
			orderFeeUser.setOrderCode(orderCode);
			orderFeeUser.setAlabaoCheapAmount(alabaoCheapAmount);
			orderFeeUser.setTotalActual(afDiscount);
			orderFeeUserService.updateMIdAndOrdCode(orderFeeUser);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
