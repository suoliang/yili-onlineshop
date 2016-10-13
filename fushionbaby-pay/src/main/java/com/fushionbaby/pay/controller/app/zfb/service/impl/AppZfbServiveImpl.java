package com.fushionbaby.pay.controller.app.zfb.service.impl;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.pay.controller.app.zfb.config.AlipayConfig;
import com.fushionbaby.pay.controller.app.zfb.model.AlipayModel;
import com.fushionbaby.pay.controller.app.zfb.service.AppZfbService;
import com.fushionbaby.pay.controller.app.zfb.sign.RSA;
import com.fushionbaby.pay.controller.app.zfb.util.AlipayCore;
import com.fushionbaby.pay.controller.app.zfb.util.AlipayNotify;
import com.fushionbaby.pay.controller.util.NotifyOpeation;
import com.fushionbaby.pay.controller.util.OrderNumberUtil;
import com.fushionbaby.payment.model.PaymentAppZfb;
import com.fushionbaby.payment.service.PaymentAppZfbService;

@Service
public class AppZfbServiveImpl extends NotifyOpeation implements AppZfbService {

	private final static Log LOGGER = LogFactory.getLog(AppZfbServiveImpl.class);

	/** 交易成功 */
	private final static String tradeFinished = "TRADE_FINISHED";
	/** 支付成功 */
	private final static String tradeSuccess = "TRADE_SUCCESS";

	// 支付宝支付状态 1：未支付 2：正在处理中 3：支付成功 4：业务处理失败
	/** 支付宝(正在处理中)状态 2 */
	private final static int ZFB_STATUS_DISPOSE = 2;
	/** 支付宝(支付成功 )状态 3 */
	private final static int ZFB_STATUS_SUCCESS = 3;
	/** 支付宝(业务处理失败)状态 4 */
	private final static int ZFB_STATUS_FAIL = 4;

	@Autowired
	private PaymentAppZfbService<PaymentAppZfb> paymentAppZfbService;
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;

	public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = null;
		try {
			Map<String, String> params = new HashMap<String, String>();
			Map<String,String[]> requestParams = request.getParameterMap();
			Iterator<?> iter = requestParams.keySet().iterator();
			while (iter.hasNext()) {
				String name = (String) iter.next();
				String[] values = requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr
							+ values[i] + ",";
				}
				params.put(name, valueStr);
			}
			/** 索亮150727处理start */
			String content = AlipayCore.createLinkString(AlipayCore.paraFilter(params));
			String sign = RSA.sign(content, AlipayConfig.private_key, "utf-8");
			params.put("sign", sign);
			/** 索亮150727处理end */
			out = response.getWriter();
			// 商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			// 交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

			if (AlipayNotify.verify(params)) {

				// 1.根据orderNumber获取订单状态(订单金额,orderCode)进行判断,若已经处理成功，则直接返回success
				PaymentAppZfb appZfb = paymentAppZfbService.getByOrderNumber(out_trade_no);
				if (appZfb == null) {
					out.println("fail");
					return;
				}
				if (appZfb.getZfbStatus() == ZFB_STATUS_SUCCESS) {
					out.println("success");
					return;
				}
				// 2.若正在处理则返回fail
				if (appZfb.getZfbStatus() == ZFB_STATUS_DISPOSE) {
					out.println("fail");
					return;
				}

				if (trade_status.equals(tradeFinished) || trade_status.equals(tradeSuccess)) {

					appZfb.setZfbStatus(ZFB_STATUS_DISPOSE);
					appZfb.setZfbTradeNo(trade_no);
					paymentAppZfbService.updateByMemberIdAndOrderCode(appZfb);

					// 2.将orderCode,支付类型（微信APP）两个参数传递给后台，调用后台业务处理逻辑（3次调用）,业务处理成功后返回success
					final String orderNumber2 = out_trade_no;
					final String orderCode = appZfb.getOrderCode();
					final Long memberId = appZfb.getMemberId();
					//业务逻辑代码开始
					boolean flag = super.updateLocalState(memberId, orderCode, PaymentTypeEnum.PAYMENT_ZFB_APP.getCode());
					if (flag) {
						appZfb.setOrderNumber(orderNumber2);
						appZfb.setZfbStatus(ZFB_STATUS_SUCCESS);
						paymentAppZfbService.updateByMemberIdAndOrderCode(appZfb);
						LOGGER.error("支付宝app+++++++++++++success 后台通知成功");
						out.println("success");
						return;
					} else {
						appZfb.setOrderNumber(orderNumber2);
						appZfb.setZfbStatus(ZFB_STATUS_FAIL);
						paymentAppZfbService.updateByMemberIdAndOrderCode(appZfb);
						return;
					}
					
				}
				out.println("fail");
			} else {
				LOGGER.error("支付宝app+++++++++++++success 后台验签失败");
				out.println("fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public AlipayModel getTradeInfo(HttpServletRequest request,String sid, String orderCode, String sourceCode,String appId) throws Exception {
		PaymentAppZfb paymentAppZfb = getTradeData(request,sid,orderCode,sourceCode,appId);
		if (paymentAppZfb == null) {
			return null;
		}
		DecimalFormat df = new DecimalFormat("0.00");
		String totalFee = df.format(new BigDecimal(paymentAppZfb.getSettleAmount())
				.multiply(BigDecimal.valueOf(0.01)));
		String notifyUrl = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.APPZFBNOTIFYURL).getValue();
		Map<String, String> map = new HashMap<String, String>();
		map.put("service", "\"" + "mobile.securitypay.pay" + "\"");
		map.put("partner", "\"" + AlipayConfig.partner + "\"");
		map.put("_input_charset", "\"" + "utf-8" + "\"");
		map.put("notify_url", "\"" + notifyUrl + "\"");
		map.put("out_trade_no", "\"" + paymentAppZfb.getOrderNumber() + "\"");
		map.put("subject", "\""+paymentAppZfb.getOrderDes()+ "\"");
		map.put("payment_type", "\"1\"");
		map.put("seller_id", "\"" + AlipayConfig.seller_id + "\"");// 卖家支付宝账号
		map.put("total_fee", "\"" + totalFee + "\"");
		map.put("body", "\""+paymentAppZfb.getOrderDes()+"\"");
		map.put("return_url", "\"" + AlipayConfig.return_url + "\"");
		map.put("it_b_pay", "\"30m\"");

		
		String content = AlipayCore.createLinkString(AlipayCore.paraFilter(map));
		String sign = RSA.sign(content, AlipayConfig.private_key, "utf-8");
		sign = URLEncoder.encode(sign, "utf-8");

		AlipayModel model = new AlipayModel();
		model.setService("mobile.securitypay.pay");
		model.setSign(sign);
		model.setNotify_url(notifyUrl);
		model.setApp_id(appId);
		// model.setAppenv(map.get("appenv"));
		model.setOut_trade_no(paymentAppZfb.getOrderNumber());
		model.setSubject(paymentAppZfb.getOrderDes());
		model.setSeller_id(AlipayConfig.seller_id);
		model.setTotal_fee(totalFee);
		model.setBody(paymentAppZfb.getOrderDes());
		model.setIt_b_pay("30m");
		return model;
	}

	private PaymentAppZfb getTradeData(HttpServletRequest request,String sid, String orderCode, String sourceCode,
			 String appId) throws Exception {
		String settleAmount = "1";
		UserDto userDto = null;
		try {
			userDto = (UserDto) SessionCache.get(sid);
			if (userDto == null) {
				return null;
			}
			settleAmount = super.getOrderPrice(sid, orderCode);
			if (CheckIsEmpty.isEmpty(settleAmount)) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取财务金额出错" + e);
			return null;
		}
		//生成交易的订单编号
		String orderNumber = OrderNumberUtil.generateOrdrNo();
		Long memberId = userDto.getMemberId();
		/** 判断付款类型表有没有数据*/
		PaymentAppZfb tmp = paymentAppZfbService.getByMemberIdAndOrderCode(memberId,orderCode);
		if (tmp != null) {
			if (!tmp.getSettleAmount().equals(settleAmount)) {
				LOGGER.error("订单金额被修改过: orderCode" + orderCode);
				return null;
			}
			tmp.setOrderNumber(orderNumber);
			tmp.setSettleAmount(settleAmount);
			tmp.setOrderDes("阿拉丁订单号:"+orderCode);
			String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			tmp.setTradeTime(tradeTime);
			tmp.setZfbStatus(1);
			tmp.setSourceCode(sourceCode);
			tmp.setRemoteAddr(GetIpAddress.getIpAddr(request));
			tmp.setOrderCode(orderCode);
			tmp.setMemberId(memberId);
			paymentAppZfbService.updateByMemberIdAndOrderCode(tmp);
			OrderFinanceUser orderFinanceUser = new OrderFinanceUser();
			orderFinanceUser.setPaymentType(PaymentTypeEnum.PAYMENT_ZFB_APP.getCode());
			orderFinanceUser.setOrderCode(orderCode);
			orderFinanceUser.setMemberId(memberId);
			orderFinanceUserService.updateByMemberIdAndOrderCode(orderFinanceUser);//修改付款方式
			return tmp;
			
		}
		PaymentAppZfb pay = new PaymentAppZfb();
		pay.setOrderNumber(orderNumber);
		pay.setOrderCode(orderCode);
		pay.setSettleAmount(settleAmount);
		pay.setOrderDes("阿拉丁订单号:"+orderCode);
		String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		pay.setTradeTime(tradeTime);
		pay.setZfbStatus(1);
		pay.setSourceCode(sourceCode);
		pay.setRemoteAddr(GetIpAddress.getIpAddr(request));
		pay.setMemberId(memberId);
		paymentAppZfbService.add(pay);
		return pay;
	}
}
