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

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoOrder;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoOrderService;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.AlabaoPayTypeConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.pay.controller.app.zfb.config.AlipayConfig;
import com.fushionbaby.pay.controller.app.zfb.model.AlipayModel;
import com.fushionbaby.pay.controller.app.zfb.service.AppZfbAlabaoService;
import com.fushionbaby.pay.controller.app.zfb.sign.RSA;
import com.fushionbaby.pay.controller.app.zfb.util.AlipayCore;
import com.fushionbaby.pay.controller.app.zfb.util.AlipayNotify;
import com.fushionbaby.pay.controller.util.OrderNumberUtil;
import com.fushionbaby.payment.model.PaymentAppZfb;
import com.fushionbaby.payment.service.PaymentAppZfbService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class AppZfbAlabaoServiceImpl implements AppZfbAlabaoService {
	
	private static final Log LOGGER = LogFactory.getLog(AppZfbAlabaoServiceImpl.class);
	/** 交易成功 */
	private final static String tradeFinished = "TRADE_FINISHED";
	/** 支付成功 */
	private final static String tradeSuccess = "TRADE_SUCCESS";

	// 支付宝支付状态 1：未支付 2：正在处理中 3：支付成功 4：业务处理失败
	/** 支付宝(正在处理中)状态 2 */
	private final static int ZFB_STATUS_DISPOSE = 2;
	/** 支付宝(支付成功 )状态 3 */
	private final static int ZFB_STATUS_SUCCESS = 3;

	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	@Autowired
	private AlabaoOrderService<AlabaoOrder> alabaoOrderService;
	@Autowired
	private PaymentAppZfbService<PaymentAppZfb> paymentAppZfbService;
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	
	/**
	 * 
	 * @param request
	 * @param alabaoSid
	 * @param orderCode
	 * @param sourceCode
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public Object getAlabaoTradeInfo(HttpServletRequest request,
			String data,String mac)
			throws Exception {
		try {
			LOGGER.info("如意宝支付宝支付接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
			String orderCode = json.getAsJsonObject().get("orderCode").getAsString();
			String sourceCode = json.getAsJsonObject().get("sourceCode").getAsString();
			String appId = json.getAsJsonObject().get("appId").getAsString();
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			Long memberId = alabaoUserDto.getMemberId();
			AlabaoOrder alabaoOrder = alabaoOrderService.findByMemberIdOrderCode(memberId, orderCode);
			if (ObjectUtils.equals(alabaoOrder, null)) {
				return Jsonp.error("无此记录");
			}
			DecimalFormat df = new DecimalFormat("0");
			String settleAmount = new BigDecimal(df.format(alabaoOrder.getTotalActual().multiply(
					new BigDecimal(100)))).toString();
			//生成交易的订单编号
			String orderNumber = OrderNumberUtil.generateOrdrNo();
			/** 判断付款类型表有没有数据*/
			PaymentAppZfb tmp = paymentAppZfbService.getByMemberIdAndOrderCode(memberId,orderCode);
			if (tmp != null) {
				if (!tmp.getSettleAmount().equals(settleAmount)) {
					LOGGER.error("订单金额被修改过: orderCode" + orderCode);
					return Jsonp.error("订单金额被修改,不能支付");
				}
				tmp.setOrderNumber(orderNumber);
				tmp.setSettleAmount(settleAmount);
				tmp.setOrderDes("如意宝订单号:"+orderCode);
				String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				tmp.setTradeTime(tradeTime);
				tmp.setZfbStatus(1);
				tmp.setSourceCode(sourceCode);
				tmp.setRemoteAddr(GetIpAddress.getIpAddr(request));
				tmp.setOrderCode(orderCode);
				tmp.setMemberId(memberId);
				paymentAppZfbService.updateByMemberIdAndOrderCode(tmp);
			} else {
				tmp = new PaymentAppZfb();
				tmp.setOrderNumber(orderNumber);
				tmp.setOrderCode(orderCode);
				tmp.setSettleAmount(settleAmount);
				tmp.setOrderDes("如意宝订单号:"+orderCode);
				String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				tmp.setTradeTime(tradeTime);
				tmp.setZfbStatus(1);
				tmp.setSourceCode(sourceCode);
				tmp.setRemoteAddr(GetIpAddress.getIpAddr(request));
				tmp.setMemberId(memberId);
				paymentAppZfbService.add(tmp);
			}
			
			DecimalFormat dftotalFee = new DecimalFormat("0.00");
			String totalFee = dftotalFee.format(new BigDecimal(tmp.getSettleAmount())
					.multiply(BigDecimal.valueOf(0.01)));
			String notifyUrl = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.ALABAOAPPZFBNOTIFYURL).getValue();
			Map<String, String> map = new HashMap<String, String>();
			map.put("service", "\"" + "mobile.securitypay.pay" + "\"");
			map.put("partner", "\"" + AlipayConfig.partner + "\"");
			map.put("_input_charset", "\"" + "utf-8" + "\"");
			map.put("notify_url", "\"" + notifyUrl + "\"");
			map.put("out_trade_no", "\"" + tmp.getOrderNumber() + "\"");
			map.put("subject", "\""+tmp.getOrderDes()+ "\"");
			map.put("payment_type", "\"1\"");
			map.put("seller_id", "\"" + AlipayConfig.seller_id + "\"");// 卖家支付宝账号
			map.put("total_fee", "\"" + totalFee + "\"");
			map.put("body", "\""+tmp.getOrderDes()+"\"");
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
			model.setOut_trade_no(tmp.getOrderNumber());
			model.setSubject(tmp.getOrderDes());
			model.setSeller_id(AlipayConfig.seller_id);
			model.setTotal_fee(totalFee);
			model.setBody(tmp.getOrderDes());
			model.setIt_b_pay("30m");
			return Jsonp_data.success(model);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取数据出错", e);
			return Jsonp.error("获取数据出错");
		}
	}
	
	public void alabaoNotify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
			// 支付宝交易金额
			String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");
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
					AlabaoOrder alabaoOrder = new AlabaoOrder();
					alabaoOrder.setMemberId(memberId);
					alabaoOrder.setOrderCode(orderCode);
					/**已付款*/
					alabaoOrder.setAlabaoStatus(CommonConstant.YES);
					alabaoOrder.setPaymentType(PaymentTypeEnum.PAYMENT_ZFB_APP.getCode());
					alabaoOrderService.updateByMemberIdOrderCode(alabaoOrder);
					/**如意宝账户主表金额更新-添加--现有的加转入的*/
					AlabaoAccount alabaoAccount = alabaoAccountService.findByMemberId(memberId);
					
					
					BigDecimal totalFee=new BigDecimal(total_fee);
					/**如意宝转入记录添加*/
					saveShiftToRecord(orderNumber2, memberId, alabaoAccount,totalFee);
					
					LOGGER.info("如意宝账户现有金额:"+alabaoAccount.getBalance()+"转入金额"+total_fee);
					alabaoAccount.setBalance(alabaoAccount.getBalance().add(totalFee));
					alabaoAccountService.updateByMemberId(alabaoAccount);
					/**更新支付表状态信息*/
					appZfb.setOrderNumber(orderNumber2);
					appZfb.setZfbStatus(ZFB_STATUS_SUCCESS);
					paymentAppZfbService.updateByMemberIdAndOrderCode(appZfb);
					LOGGER.info("支付宝app+++++++++++++success 后台通知成功");
					out.println("success");
					return;
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

	private void saveShiftToRecord(final String orderNumber2,final Long memberId, AlabaoAccount alabaoAccount,
			BigDecimal totalFee) {
		AlabaoShiftToRecord alabaoShiftToRecord = new AlabaoShiftToRecord();
		alabaoShiftToRecord.setAccount(alabaoAccount.getAccount());
		alabaoShiftToRecord.setMemberId(memberId);
		alabaoShiftToRecord.setShiftToAccountType(AlabaoPayTypeConstant.ALABAOAPPZFB);//支付宝
		alabaoShiftToRecord.setTransferMoney(totalFee);
		alabaoShiftToRecord.setSerialNum(orderNumber2);
		alabaoShiftToRecord.setAfterChangeMoney(alabaoAccount.getBalance().add(totalFee));
		alabaoShiftToRecord.setBeforeChangeMoney(alabaoAccount.getBalance());
		alabaoShiftToRecordService.add(alabaoShiftToRecord);
	}
	
}
