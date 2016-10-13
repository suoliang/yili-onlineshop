package com.fushionbaby.pay.controller.app.alabao.service.impl;

import java.math.BigDecimal;
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
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrAlabaoConfig;
import com.fushionbaby.config.model.SysmgrImportanceConfig;
import com.fushionbaby.config.service.SysmgrAlabaoConfigService;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.pay.controller.app.alabao.model.AlabaoPayModel;
import com.fushionbaby.pay.controller.app.alabao.service.AppAlabaoService;
import com.fushionbaby.pay.controller.util.NotifyOpeation;
import com.fushionbaby.payment.model.PaymentAppAlabao;
import com.fushionbaby.payment.service.PaymentAppAlabaoService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class AppAlabaoServiceImpl extends NotifyOpeation implements AppAlabaoService {
	
	private static final Log LOGGER = LogFactory.getLog(AppAlabaoServiceImpl.class);
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	@Autowired
	private SysmgrAlabaoConfigService<SysmgrAlabaoConfig> alabaoConfigService;
	@Autowired
	private PaymentAppAlabaoService<PaymentAppAlabao> paymentAppAlabaoService;
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private AlabaoConsumeRecordService<AlabaoConsumeRecord> alabaoConsumeRecordService;
	@Autowired
	private SmsService<Sms> smsService;
	
	
	public Object getAlabaoPayModel(HttpServletRequest request, String data,
			String mac) throws Exception {
		LOGGER.info("如意宝支付Model接口action--请求报文：{" + data + "}");
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
		
		OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(user.getMemberId(), orderCode);
		if (ObjectUtils.equals(orderFeeUser, null)) {
			return Jsonp.error("费用查询失败");
		}
		/**订单总计原价*/
		BigDecimal totalActual = orderFeeUser.getTotalActual();
		
		
		if (ObjectUtils.equals(totalActual, null)) {
			return Jsonp.error("订单不存在或已取消!");
		}
		AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(alabaoUserDto.getAccount());
		
		SysmgrAlabaoConfig alabaoConfig = alabaoConfigService.findByMinMaxValue(NumberFormatUtil.numberFormat(alabaoAccount.getBalance()));
		/**描述*/
		String remark = "";
		/**折扣*/
		String discount = "";
		/**对象不存在九折优惠*/
		if (ObjectUtils.equals(alabaoConfig, null)) {
			SysmgrImportanceConfig importanceConfig = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.ALABAO_PAY_DISCOUNT);
			discount = importanceConfig.getValue();
			remark = importanceConfig.getRemark();
		} else {
			discount = alabaoConfig.getDiscountValue();
			remark = alabaoConfig.getRemark();
		}
		
		BigDecimal afDiscount = totalActual.multiply(new BigDecimal(discount));
		/**打折后的总价*/
		String settleAmount = NumberFormatUtil.numberFormat(afDiscount);
		Long memberId = user.getMemberId();
		PaymentAppAlabao appAlabao = paymentAppAlabaoService.getByMemberIdAndOrderCode(memberId, orderCode);
		if (appAlabao != null) {
			if (appAlabao.getAlabaoStatus() == 2 || appAlabao.getAlabaoStatus() == 3 ) {
				LOGGER.error("订单已付款:orderCode" + orderCode);
				return Jsonp.error("订单处理中或已付款");
			}
			String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			appAlabao.setTradeTime(tradeTime);
			appAlabao.setSourceCode(sourceCode);
			appAlabao.setRemoteAddr(GetIpAddress.getIpAddr(request));
			appAlabao.setAccount(alabaoUserDto.getAccount());
			appAlabao.setSettleAmount(settleAmount);
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
			paymentAppAlabao.setOrderDes("阿拉丁订单号:" + orderCode);
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
		alabaoPayModel.setPayDiscountDesc(remark);
		return Jsonp_data.success(alabaoPayModel);
	}

	public Object alabaoPay(String data, String mac) throws Exception {
		LOGGER.info("如意宝支付Pay接口action--请求报文：{" + data + "}");
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
		OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(user.getMemberId(), orderCode);
		if (ObjectUtils.equals(orderFeeUser, null)) {
			return Jsonp.error("费用查询失败");
		}
		/**订单总计原价*/
		BigDecimal totalActual = orderFeeUser.getTotalActual();
		if (ObjectUtils.equals(totalActual, null)) {
			return Jsonp.error("订单不存在或已取消!");
		}
		Long memberId = user.getMemberId();
		PaymentAppAlabao appAlabao = paymentAppAlabaoService.getByMemberIdAndOrderCode(memberId, orderCode);
		
		BigDecimal afDiscount = new BigDecimal(appAlabao.getSettleAmount());
		/**如意宝折扣差价*/
		BigDecimal alabaoCheapAmount = totalActual.subtract(afDiscount);
		/**打折后的总价*/
		String settleAmount = NumberFormatUtil.numberFormat(afDiscount);
		
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
		if (alabaoAccount.getBalance().compareTo(afDiscount) < 0) {
			return Jsonp.error("您的余额不足");
		}
		BigDecimal old_money=alabaoAccount.getBalance();
		/**修改订单状态*/
		super.updateLocalState(memberId, orderCode, PaymentTypeEnum.PAYMENT_ALABAO_APP.getCode());
		/**修改订单费用如意宝折扣,订单财务实付金额*/
		super.updateOrderFee(memberId, orderCode, alabaoCheapAmount,afDiscount);
		/**修改支付表状态*/
		appAlabao.setAlabaoStatus(3);
		paymentAppAlabaoService.updateByMemberIdAndOrderCode(appAlabao);
		/**消费后的余额*/
		BigDecimal balance = alabaoAccount.getBalance().subtract(afDiscount);
		alabaoAccount.setBalance(balance);
		alabaoAccountService.updateByAccount(alabaoAccount);
		/**消费记录添加*/
		AlabaoConsumeRecord alabaoConsumeRecord = new AlabaoConsumeRecord();
		alabaoConsumeRecord.setAccount(alabaoAccount.getAccount());
		/**此处memberId必须从如意宝账户里取*/
		alabaoConsumeRecord.setMemberId(alabaoUserDto.getMemberId());
		alabaoConsumeRecord.setOrderCode(orderCode);
		alabaoConsumeRecord.setConsumeMoney(afDiscount);
		alabaoConsumeRecord.setIsSuccess(CommonConstant.YES);
		/**添加消费前后数据*/
		alabaoConsumeRecord.setBeforeChangeMoney(old_money);
		alabaoConsumeRecord.setAfterChangeMoney(balance);
		alabaoConsumeRecordService.add(alabaoConsumeRecord);
		LOGGER.info(DateFormat.dateToString(new Date())+"***************如意消费卡支付*********** 账号为"+alabaoAccount.getAccount()+"的消费前后的账户余额分别为："+old_money+"和"+balance);
		SysmgrImportanceConfig lowestMoney = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.APP_ORDER_LOWEST_MONEY);
		if(new BigDecimal(lowestMoney.getValue()).compareTo(new BigDecimal(settleAmount))<=0)
			smsService.orderTradeNotify(settleAmount,alabaoUserDto.getAccount(),appAlabao.getSourceCode());
		return Jsonp.success();
	}
	
}
