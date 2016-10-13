package com.fushionbaby.pay.controller.app.alabao.service.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoConsumeRecord;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoConsumeRecordService;
import com.aladingshop.card.model.MemberCardOrder;
import com.aladingshop.card.service.MemberCardOrderService;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.enums.OrderConfigClientEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrImportanceConfig;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.pay.controller.app.alabao.model.AlabaoPayModel;
import com.fushionbaby.pay.controller.app.alabao.service.AppAlabaoYiduobaoService;
import com.fushionbaby.pay.controller.util.NotifyOpeation;
import com.fushionbaby.payment.model.PaymentAppAlabao;
import com.fushionbaby.payment.service.PaymentAppAlabaoService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/***
 *  阿拉丁卡订单用 如意宝支付
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月26日上午9:51:31
 */
@Service
public class AppAlabaoYiduobaoServiceImpl extends NotifyOpeation implements AppAlabaoYiduobaoService {

	/**订单财务*/
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	/** 系统重要配置*/
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	/** 如意宝*/
	@Autowired
	private PaymentAppAlabaoService<PaymentAppAlabao> paymentAppAlabaoService;
	/**阿拉宝账户*/
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	/** 阿拉丁订单*/
	@Autowired
	private MemberCardOrderService<MemberCardOrder> memberCardOrderService;
	/**如意宝消费记录*/
	@Autowired
	private AlabaoConsumeRecordService<AlabaoConsumeRecord> alabaoConsumeRecordService;
	@Autowired
	private SmsService<Sms> smsService;
	
	/**订单金额表*/
	@Autowired
	private OrderFeeUserService<OrderFeeUser> feeUserService;
	
	private static final Log LOGGER=LogFactory.getLog(AppAlabaoYiduobaoServiceImpl.class);
	public Object getAlabaoYiduobaoPayModel(HttpServletRequest request,String data, String mac) {
		
		LOGGER.info(" 阿拉丁卡  如意宝支付Model接口action--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}
		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(data);
		String sid = json.getAsJsonObject().get("sid").getAsString();
		String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
		String orderCode = json.getAsJsonObject().get("orderCode").getAsString();
		String sourceCode = json.getAsJsonObject().get("sourceCode").getAsString();
		UserDto user = (UserDto) SessionCache.get(sid);
		if (CheckObjectIsNull.isNull(user)) {
			return Jsonp.noLoginError("APP未登录");
		}
		AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
		if (ObjectUtils.equals(alabaoUserDto, null)) {
			return Jsonp.noLoginError("如意未登录或重新登录");
		}
		
		OrderFeeUser fee=this.feeUserService.findByMIdAndOrdCode(alabaoUserDto.getMemberId(), orderCode);
		if (ObjectUtils.equals(fee, null)) {
			return Jsonp.error("费用查询失败");
		}
		/**订单总计原价*/
		BigDecimal totalActual = fee.getTotalActual();
		if (ObjectUtils.equals(totalActual, null)) {
			return Jsonp.error("订单不存在或已取消!");
		}
		String settleAmount = NumberFormatUtil.numberFormat(totalActual);
		Long memberId = alabaoUserDto.getMemberId();
		PaymentAppAlabao appAlabao = paymentAppAlabaoService.getByMemberIdAndOrderCode(memberId, orderCode);
		if (appAlabao != null) {
			if (!StringUtils.equals(settleAmount, appAlabao.getSettleAmount())) {
				LOGGER.error("订单金额被修改过:orderCode" + orderCode);
				return Jsonp.error("订单金额被修改,不能支付");
			}
			if (appAlabao.getAlabaoStatus() == 2 || appAlabao.getAlabaoStatus() == 3 ) {
				LOGGER.error("订单已付款:orderCode" + orderCode);
				return Jsonp.error("订单处理中或已付款");
			}
			String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			appAlabao.setTradeTime(tradeTime);
			appAlabao.setSourceCode(sourceCode);
			appAlabao.setRemoteAddr(GetIpAddress.getIpAddr(request));
			appAlabao.setAccount(alabaoUserDto.getAccount());
			paymentAppAlabaoService.updateByMemberIdAndOrderCode(appAlabao);
			OrderFinanceUser orderFinanceUser = new OrderFinanceUser();
			orderFinanceUser.setPaymentType(PaymentTypeEnum.PAYMENT_ALABAO_APP.getCode());
			orderFinanceUser.setOrderCode(orderCode);
			orderFinanceUser.setMemberId(memberId);
			orderFinanceUserService.updateByMemberIdAndOrderCode(orderFinanceUser);//修改付款方式
		} else{
			PaymentAppAlabao paymentAppAlabao = new PaymentAppAlabao();
			paymentAppAlabao.setOrderCode(orderCode);
			paymentAppAlabao.setSettleAmount(settleAmount);
			paymentAppAlabao.setOrderDes("阿拉丁卡订单号:" + orderCode);
			String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			paymentAppAlabao.setTradeTime(tradeTime);
			paymentAppAlabao.setAlabaoStatus(1);
			paymentAppAlabao.setSourceCode(sourceCode);
			paymentAppAlabao.setRemoteAddr(GetIpAddress.getIpAddr(request));
			paymentAppAlabao.setMemberId(memberId);
			paymentAppAlabao.setAccount(alabaoUserDto.getAccount());
			paymentAppAlabaoService.add(paymentAppAlabao);
		}
		AlabaoPayModel alabaoPayModel = new AlabaoPayModel();
		alabaoPayModel.setOrderDesc("阿拉丁订单号:" + orderCode);
		alabaoPayModel.setOriginalPrice(NumberFormatUtil.numberFormat(totalActual));
		alabaoPayModel.setActualMoney(settleAmount);
		//alabaoPayModel.setPayDiscountDesc(importanceConfig.getRemark());
		alabaoPayModel.setPayDiscountDesc("");
		return Jsonp_data.success(alabaoPayModel);
	}

	public Object alabaoPay(String data, String mac) throws RemoteException {
		
		LOGGER.info("阿拉丁卡订单  如意宝支付确认 接口action--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}
		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(data);
		String sid = json.getAsJsonObject().get("sid").getAsString();
		String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
		String orderCode = json.getAsJsonObject().get("orderCode").getAsString();
		String payPassword = json.getAsJsonObject().get("payPassword").getAsString();
		UserDto user = (UserDto) SessionCache.get(sid);
		if (CheckObjectIsNull.isNull(user)) {
			return Jsonp.noLoginError("APP未登录");
		}
		AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
		if (ObjectUtils.equals(alabaoUserDto, null)) {
			return Jsonp.noLoginError("如意未登录或重新登录");
		}
		OrderFeeUser fee=this.feeUserService.findByMIdAndOrdCode(alabaoUserDto.getMemberId(), orderCode);
		if (ObjectUtils.equals(fee, null)) {
			return Jsonp.error("费用查询失败");
		}
		/**订单总计原价*/
		BigDecimal totalActual = fee.getTotalActual();
		if (ObjectUtils.equals(totalActual, null)) {
			return Jsonp.error("订单不存在或已取消!");
		}
		String settleAmount = NumberFormatUtil.numberFormat(totalActual);
		Long memberId = alabaoUserDto.getMemberId();
		PaymentAppAlabao appAlabao = paymentAppAlabaoService.getByMemberIdAndOrderCode(memberId, orderCode);
		if (!StringUtils.equals(settleAmount, appAlabao.getSettleAmount())) {
			LOGGER.error("订单金额被修改过:orderCode" + orderCode);
			return Jsonp.error("订单金额被修改,不能支付");
		}
		if (appAlabao.getAlabaoStatus() == 2 || appAlabao.getAlabaoStatus() == 3 ) {
			LOGGER.error("订单已付款:orderCode" + orderCode);
			return Jsonp.error("订单处理中或已付款");
		}
		AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(alabaoUserDto.getAccount());
		if (ObjectUtils.equals(alabaoAccount, null)) {
			return Jsonp.error("无如意账户");
		}
		if (!StringUtils.equals(alabaoAccount.getPayPassword(), MD5Util.MD5(payPassword+MD5Util.getPasswordkey()))) {
			return Jsonp.acountFailureError("密码输入错误");
		}
		//余额不足
		if (alabaoAccount.getBalance().compareTo(totalActual) < 0) {
			return Jsonp.error("您的余额不足");
		}
		BigDecimal old_money=alabaoAccount.getBalance();
		/** 生成阿拉丁卡*/
		super.createMemberCard(memberId, orderCode);
		/**修改支付表状态*/
		appAlabao.setAlabaoStatus(3);
		paymentAppAlabaoService.updateByMemberIdAndOrderCode(appAlabao);
		/**修改订单状态*/
		saveMemberCardOrder(orderCode, memberId);
		/**如意宝账号余额变化*/
		BigDecimal balance = alabaoAccount.getBalance().subtract(totalActual);
		alabaoAccount.setBalance(balance);
		alabaoAccountService.updateByAccount(alabaoAccount);
		/**消费记录添加*/
		saveConsumeRecord(orderCode, alabaoUserDto, totalActual, alabaoAccount,	old_money, balance);
		
		SysmgrImportanceConfig lowestMoney = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.APP_ORDER_LOWEST_MONEY);
		if(new BigDecimal(lowestMoney.getValue()).compareTo(new BigDecimal(settleAmount))<=0)
			smsService.orderTradeNotify(settleAmount,alabaoUserDto.getAccount(),appAlabao.getSourceCode());
		
		return Jsonp.success();
		
	}

	/***
	 * 更新 阿拉丁 订单状态
	 * @param orderCode
	 * @param memberId
	 */
	private void saveMemberCardOrder(String orderCode, Long memberId) {
		MemberCardOrder cardOrder=this.memberCardOrderService.findByMemberIdOrderCode(memberId, orderCode);
		cardOrder.setMemberId(memberId);
		cardOrder.setOrderCode(orderCode);
		cardOrder.setFinanceStatus(CommonConstant.YES);
		cardOrder.setPayType(PaymentTypeEnum.PAYMENT_ALABAO_APP.getCode());
		cardOrder.setPayCompleteTime(new Date());
		cardOrder.setUpdateTime(new Date());
		cardOrder.setOrderStatus(OrderConfigClientEnum.SUCCESS_TRANSACTION.getCode());
		memberCardOrderService.updateByMemberIdOrderCode(cardOrder);
	}

	/***
	 * 保存消费记录
	 * @param orderCode
	 * @param alabaoUserDto
	 * @param totalActual
	 * @param alabaoAccount
	 * @param old_money
	 * @param balance
	 */
	private void saveConsumeRecord(String orderCode,AlabaoUserDto alabaoUserDto, BigDecimal totalActual,
			AlabaoAccount alabaoAccount, BigDecimal old_money,BigDecimal balance) {
		AlabaoConsumeRecord alabaoConsumeRecord = new AlabaoConsumeRecord();
		alabaoConsumeRecord.setAccount(alabaoAccount.getAccount());
		/**此处memberId必须从如意宝账户里取*/
		alabaoConsumeRecord.setMemberId(alabaoUserDto.getMemberId());
		alabaoConsumeRecord.setOrderCode(orderCode);
		alabaoConsumeRecord.setConsumeMoney(totalActual);
		alabaoConsumeRecord.setIsSuccess(CommonConstant.YES);
		/**添加消费前后数据*/
		alabaoConsumeRecord.setBeforeChangeMoney(old_money);
		alabaoConsumeRecord.setAfterChangeMoney(balance);
		alabaoConsumeRecordService.add(alabaoConsumeRecord);
	}


	
}
