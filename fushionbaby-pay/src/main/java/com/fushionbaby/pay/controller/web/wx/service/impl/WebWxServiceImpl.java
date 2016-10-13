package com.fushionbaby.pay.controller.web.wx.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderBaseUser;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderBaseUserService;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.pay.controller.util.NotifyOpeation;
import com.fushionbaby.pay.controller.util.OrderNumberUtil;
import com.fushionbaby.pay.controller.util.TwoDimensionCode;
import com.fushionbaby.pay.controller.web.wx.config.WebWxConfig;
import com.fushionbaby.pay.controller.web.wx.service.WebWxService;
import com.fushionbaby.pay.controller.web.wx.util.WXCommonUtil;
import com.fushionbaby.pay.controller.web.wx.util.XMLUtil;
import com.fushionbaby.payment.model.PaymentWebWx;
import com.fushionbaby.payment.service.PaymentWebWxService;

/**
 * @description WEB微信支付实现
 * @author 索亮
 * @date 2015年8月27日下午4:38:13
 */
@Service
public class WebWxServiceImpl extends NotifyOpeation implements WebWxService {

	private static final Log LOGGER = LogFactory.getLog(WebWxServiceImpl.class);
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;
	@Autowired
	private OrderBaseUserService<OrderBaseUser> orderBaseUserService;
	@Autowired
	private PaymentWebWxService<PaymentWebWx> paymentWebWxService;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;

	public void getQRCode(String sid, String orderCode, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String httpPrefix = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.HTTPPREFIX).getValue();
		/** 订单sid不能为空 */
		if (StringUtils.isBlank(sid)) {
			response.sendRedirect(httpPrefix + "/login/index");
			return;
		}
		/** 订单code不能为空 */
		if (StringUtils.isBlank(orderCode)) {
			response.sendRedirect(httpPrefix + "/pay/gotoPayErr?payErrStatus=2&orderCode=" + orderCode);
			return;
		}
		UserDto userDto = (UserDto) SessionCache.get(sid);
		/** 会员未登录 */
		if (userDto == null) {
			response.sendRedirect(httpPrefix + "/login/index");
			return;
		}
		Long memberId = userDto.getMemberId();
		OrderFinanceUser orderFinanceUser = orderFinanceUserService.findByMemberIdAndOrderCode(memberId, orderCode);
		/** 订单在系统不存在 */
		if (orderFinanceUser == null) {
			response.sendRedirect(httpPrefix + "/pay/gotoPayErr?payErrStatus=4&orderCode=" + orderCode);
			LOGGER.info("支付失败,订单" + orderCode + "在系统不存在");
			return;
		}

		// 如果支付方式,和原来的不一样,更新一下支付方式
		if (!PaymentTypeEnum.PAYMENT_WX_WEB.getCode().equals(orderFinanceUser.getPaymentType())) {
			// 更新订单采用什么支付方式,支付宝、微信、银联……
			orderFinanceUser.setPaymentType(PaymentTypeEnum.PAYMENT_WX_WEB.getCode());
			orderFinanceUserService.updateByMemberIdAndOrderCode(orderFinanceUser);
		}
		OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(memberId, orderCode);
		/** 总金额 */
		BigDecimal totalFee = ObjectUtils.equals(orderFeeUser.getTotalActual(), null) ? new BigDecimal(0)
				: orderFeeUser.getTotalActual();
		DecimalFormat df = new DecimalFormat("0");
		String settleAmount = df.format(totalFee.multiply(new BigDecimal(100)));
		/** 生成商户自己的订单号 */
		String orderNumber = OrderNumberUtil.generateOrdrNo();

		PaymentWebWx paymentWebWx = paymentWebWxService.getByMemberIdAndOrderCode(memberId, orderCode);
		if (paymentWebWx != null) {
			// 该订单已经付过款,不用重复支付
			if (paymentWebWx.getWxStatus() == 2 || paymentWebWx.getWxStatus() == 3) {
				response.sendRedirect(httpPrefix + "/pay/gotoPayErr?payErrStatus=5&orderCode=" + orderCode);
				LOGGER.info("支付被取消,订单" + orderCode + "已经付过款,不用重复支付");
				return;
			}
			paymentWebWx.setOrderNumber(orderNumber);
			paymentWebWx.setRemoteAddr(GetIpAddress.getIpAddr(request));
			paymentWebWx.setTradeTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			paymentWebWxService.updateByMemberIdAndOrderCode(paymentWebWx);
		} else {
			/** 保存WEB微信交易记录 */
			paymentWebWx = new PaymentWebWx();
			paymentWebWx.setOrderNumber(orderNumber);
			paymentWebWx.setMemberId(memberId);
			paymentWebWx.setOrderCode(orderCode);
			paymentWebWx.setSettleAmount(settleAmount);
			paymentWebWx.setTradeTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			paymentWebWx.setOrderDes("阿拉丁订单号:" + orderCode);
			paymentWebWx.setWxStatus(1);
			paymentWebWx.setRemoteAddr(GetIpAddress.getIpAddr(request));
			paymentWebWxService.add(paymentWebWx);

		}

		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", WebWxConfig.appId);
		parameters.put("body", "阿拉丁订单号:" + orderCode);
		/** 商户号 */
		parameters.put("mch_id", WebWxConfig.mchId);
		/** 随机字符串 */
		parameters.put("nonce_str", WXCommonUtil.CreateNoncestr());
		parameters.put("notify_url", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.WEBWXNOTIFYURL)
				.getValue());
		/** 商户订单号 */
		parameters.put("out_trade_no", orderNumber);
		parameters.put("spbill_create_ip", GetIpAddress.getIpAddr(request));
		/** 单位(分) */
		parameters.put("total_fee", settleAmount);
		/** 微信原生支付 */
		parameters.put("trade_type", "NATIVE");
		/** trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。 */
		parameters.put("product_id", OrderNumberUtil.generateOrdrNo());
		// parameters.put("openid", "o7W6yt9DUOBpjEYogs4by1hD_OQE");
		String sign = WXCommonUtil.createSign("UTF-8", parameters);
		parameters.put("sign", sign);
		String requestXML = WXCommonUtil.getRequestXml(parameters);
		LOGGER.info("WEB微信请求参数:" + requestXML);
		String result = WXCommonUtil.httpsRequest(WebWxConfig.UNIFIED_ORDER_URL, "POST", requestXML);
		LOGGER.info("WEB微信返回的响应信息:" + result);
		/** 解析微信返回的信息，以Map形式存储便于取值 */
		Map<String, String> map = XMLUtil.doXMLParse(result);
		LOGGER.info("WEB微信返回码return_code:" + map.get("return_code") + "且result_code:" + map.get("result_code"));
		if ("SUCCESS".equalsIgnoreCase(map.get("return_code")) && "SUCCESS".equalsIgnoreCase(map.get("result_code"))) {
			// 生成二维码图片
			TwoDimensionCode twoDimensionCode = new TwoDimensionCode();
			twoDimensionCode.encoderQRCode(map.get("code_url"), response.getOutputStream(), "png", 14);
		}
	}

	public void notifyWx(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			/** 获取微信调用我们notify_url的返回XML信息 */
			String result = new String(outSteam.toByteArray(), "utf-8");
			LOGGER.info("WEB微信返回回调XML结果:" + result);
			/** 解析微信返回的信息，以Map形式存储便于取值 */
			Map<String, String> map = XMLUtil.doXMLParse(result);
			LOGGER.info("map形式存储XML结果:" + map);
			if ("SUCCESS".equalsIgnoreCase(map.get("return_code"))
					&& "SUCCESS".equalsIgnoreCase(map.get("result_code"))) {
				/** 商户订单号 */
				String orderNumber = map.get("out_trade_no");
				PaymentWebWx paymentWebWx = paymentWebWxService.getByOrderNumber(orderNumber);
				/** 正在处理 */
				paymentWebWx.setWxStatus(2);
				paymentWebWxService.updateByMemberIdAndOrderCode(paymentWebWx);
				final Long memberId = paymentWebWx.getMemberId();
				final String orderCode = paymentWebWx.getOrderCode();
				final String settleAmount = paymentWebWx.getSettleAmount();
				if (map.get("total_fee").equalsIgnoreCase(settleAmount)) {
					paymentWebWx.setWxStatus(3);
					paymentWebWx.setWxOpenId(map.get("openid"));
					paymentWebWx.setWxTransactionId(map.get("transaction_id"));
					paymentWebWxService.updateByMemberIdAndOrderCode(paymentWebWx);
					// 本地系统,订单状态-审核中,财务状态已经付款;
					super.updateLocalState(memberId, orderCode, PaymentTypeEnum.PAYMENT_WX_WEB.getCode());
					if(orderCode.contains("ydb")){
						/** 生成用户益多宝 */
						super.createMemberCard(memberId, orderCode);	
					}
					String returnXML = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
							+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml>";
					out.println(returnXML);
					out.close();
					LOGGER.info("WEB微信回调成功");
				}
			} else {
				String returnXML = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
						+ "<return_msg><![CDATA[非空]]></return_msg>" + "</xml>";
				out.println(returnXML);
				out.close();
				LOGGER.info("WEB微信回调失败:回调参数校验错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("WEB微信回调出错", e);
		}
	}

}
