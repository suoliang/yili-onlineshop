package com.fushionbaby.pay.controller.app.wx.service.impl;

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

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.aladingshop.card.model.MemberCardOrder;
import com.aladingshop.card.service.MemberCardOrderService;
import com.fushionbaby.cache.util.AlabaoSessionCache;
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
import com.fushionbaby.pay.controller.app.wx.config.AppWxConfig;
import com.fushionbaby.pay.controller.app.wx.service.AppWxYiduobaoService;
import com.fushionbaby.pay.controller.app.wx.util.WXUtil;
import com.fushionbaby.pay.controller.app.wx.util.XMLUtil;
import com.fushionbaby.pay.controller.util.NotifyOpeation;
import com.fushionbaby.pay.controller.util.OrderNumberUtil;
import com.fushionbaby.payment.model.PaymentAppWx;
import com.fushionbaby.payment.service.PaymentAppWxService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class AppWxYiduobaoServiceImpl extends NotifyOpeation implements AppWxYiduobaoService {
	
	private static final Log LOGGER = LogFactory.getLog(AppWxYiduobaoServiceImpl.class);
	/**阿拉丁卡购卡订单*/
	@Autowired
	private MemberCardOrderService<MemberCardOrder> memberCardOrderService;
	/** app 微信支付*/ 
	@Autowired
	private PaymentAppWxService<PaymentAppWx> paymentAppWxService;
	/** 订单财务表*/
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	/** 重要配置*/
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	/** 阿拉丁卡账户*/
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	/** 阿拉丁卡转入记录*/
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	
	
	public Object getYiduobaoPrepayId(HttpServletRequest request, String data,String mac) throws Exception {
		LOGGER.info("阿拉丁卡微信支付接口action--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}
		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(data);
		String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
		String orderCode = json.getAsJsonObject().get("orderCode").getAsString();
		String sourceCode = json.getAsJsonObject().get("sourceCode").getAsString();
		/**得到如意宝账号*/
		AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
		if (ObjectUtils.equals(alabaoUserDto, null)) {
			return Jsonp.noLoginError("未登录或重新登录");
		}
		Long memberId = alabaoUserDto.getMemberId();
		/**查询是否存在该订单*/
		MemberCardOrder memberCardOrder = memberCardOrderService.findByMemberIdOrderCode(memberId, orderCode);
		if (ObjectUtils.equals(memberCardOrder, null)) {
			return Jsonp.error("无此记录");
		}
		DecimalFormat df = new DecimalFormat("0");
		String settleAmount = new BigDecimal(df.format(memberCardOrder.getTotalActual().multiply(new BigDecimal(100)))).toString();
		//生成交易的订单编号
		String orderNumber = OrderNumberUtil.generateOrdrNo();
		/**微信支付记录*/
		PaymentAppWx paymentAppWx = paymentAppWxService.getByMemberIdAndOrderCode(memberId, orderCode);
		if (paymentAppWx != null) {/** 二次支付问题*/
			if (!paymentAppWx.getSettleAmount().equals(settleAmount)) {
				LOGGER.error("订单金额被修改过: orderCode" + orderCode);
				return Jsonp.error("订单金额被修改,不能支付");
			}
			if (paymentAppWx.getWxStatus() == 2 || paymentAppWx.getWxStatus() == 3 ) {
				LOGGER.error("订单已付款:orderCode" + orderCode);
				return Jsonp.error("订单处理中或已付款");
			}
			paymentAppWx.setOrderNumber(orderNumber);
			paymentAppWx.setSettleAmount(settleAmount);
			paymentAppWx.setOrderDes("阿拉丁卡订单号:"+orderCode);
			String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			paymentAppWx.setTradeTime(tradeTime);
			paymentAppWx.setWxStatus(1);
			paymentAppWx.setSourceCode(sourceCode);
			paymentAppWx.setRemoteAddr(GetIpAddress.getIpAddr(request));
			paymentAppWx.setOrderCode(orderCode);
			paymentAppWx.setMemberId(memberId);
			paymentAppWxService.updateByMemberIdAndOrderCode(paymentAppWx);
			OrderFinanceUser orderFinanceUser = new OrderFinanceUser();
			orderFinanceUser.setPaymentType(PaymentTypeEnum.PAYMENT_WX_APP.getCode());
			orderFinanceUser.setOrderCode(orderCode);
			orderFinanceUser.setMemberId(memberId);
			orderFinanceUserService.updateByMemberIdAndOrderCode(orderFinanceUser);//修改付款方式
		} else {
			paymentAppWx = new PaymentAppWx();
			paymentAppWx.setOrderNumber(orderNumber);
			paymentAppWx.setOrderCode(orderCode);
			paymentAppWx.setSettleAmount(settleAmount);
			paymentAppWx.setOrderDes("阿拉丁卡订单号:"+orderCode);
			String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			paymentAppWx.setTradeTime(tradeTime);
			paymentAppWx.setWxStatus(1);
			paymentAppWx.setSourceCode(sourceCode);
			paymentAppWx.setRemoteAddr(GetIpAddress.getIpAddr(request));
			paymentAppWx.setMemberId(memberId);
			paymentAppWxService.add(paymentAppWx);
		}
		
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
    	parameters.put("appid", AppWxConfig.appId);
    	parameters.put("body", "阿拉丁卡订单号:"+orderCode);
    	/**APP商户号*/
    	parameters.put("mch_id", AppWxConfig.mchId);
    	/**随机字符串*/
    	parameters.put("nonce_str", WXUtil.getNonceStr());
    	parameters.put("notify_url", sysmgrImportanceConfigService.findByCode(
				ImportanceConfigConstant.YIDUOBAO_APP_WX_NOTIFY_URL).getValue());
    	/** 商户订单号 */
    	parameters.put("out_trade_no", orderNumber);
    	parameters.put("spbill_create_ip",GetIpAddress.getIpAddr(request));
    	/**单位(分)*/
    	parameters.put("total_fee", settleAmount);
    	/**微信交易类型*/
    	parameters.put("trade_type", "APP");
    	String sign = WXUtil.createSign("UTF-8", parameters);
    	parameters.put("sign", sign);
    	String requestXML = WXUtil.getRequestXml(parameters);
    	LOGGER.info("阿拉丁卡APP微信请求参数:"+requestXML);
    	String result =WXUtil.httpsRequest(AppWxConfig.UNIFIED_ORDER_URL, "POST", requestXML);
    	LOGGER.info("阿拉丁卡APP微信返回的响应信息:"+result);
    	/**解析微信返回的信息，以Map形式存储便于取值*/
    	Map<String,String> map = XMLUtil.doXMLParse(result);
    	LOGGER.info("阿拉丁卡APP微信返回码return_code:"+map.get("return_code")+"且result_code:"+map.get("result_code"));
    	if ("SUCCESS".equalsIgnoreCase(map.get("return_code")) && "SUCCESS".equalsIgnoreCase(map.get("result_code"))) {
    		SortedMap<Object,Object> return2APPMap = new TreeMap<Object,Object>();
    		return2APPMap.put("appid", map.get("appid"));
    		return2APPMap.put("partnerid", map.get("mch_id"));
    		return2APPMap.put("prepayid", map.get("prepay_id"));
    		return2APPMap.put("package", "Sign=WXPay");
    		return2APPMap.put("noncestr", map.get("nonce_str"));
    		return2APPMap.put("timestamp", WXUtil.getTimeStamp());
    		String return2APPSign = WXUtil.createSign("UTF-8", return2APPMap);
    		return2APPMap.put("sign", return2APPSign);
    		return Jsonp_data.success(return2APPMap);
    	}
		return Jsonp.error("微信返回数据result_code失败");
	}
	
	public void notifyYiduobaoAppWx(HttpServletRequest request,	HttpServletResponse response) {
		try {
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
			LOGGER.info("阿拉丁卡APP微信返回回调XML结果:" + result);
			/**解析微信返回的信息，以Map形式存储便于取值*/
	    	Map<String,String> map = XMLUtil.doXMLParse(result);
	    	LOGGER.info("阿拉丁卡map形式存储XML结果:"+map);
	    	PrintWriter out = response.getWriter();
	    	if ("SUCCESS".equalsIgnoreCase(map.get("return_code")) && "SUCCESS".equalsIgnoreCase(map.get("result_code"))) {
	    		/** 商户订单号 */
	    		String orderNumber = map.get("out_trade_no");
	    		PaymentAppWx paymentAppWx = paymentAppWxService.getByOrderNumber(orderNumber);
	    		if (paymentAppWx.getWxStatus() == 2 || paymentAppWx.getWxStatus() == 3 ) {
					return;
				}
	    		paymentAppWx.setWxStatus(2);
	    		paymentAppWx.setWxOpenId(map.get("openid"));
	    		paymentAppWx.setWxTransactionId(map.get("transaction_id"));
	    		paymentAppWxService.updateByMemberIdAndOrderCode(paymentAppWx);
	    		final Long memberId = paymentAppWx.getMemberId();
	    		final String orderCode = paymentAppWx.getOrderCode();
	    		
	    		//业务逻辑代码开始
	    		/** 阿拉丁卡订单支付完成之后，订单状态变化*/
				MemberCardOrder memberCardOrder = new MemberCardOrder();
				memberCardOrder.setMemberId(memberId);
				memberCardOrder.setOrderCode(orderCode);
				/**已付款*/
				memberCardOrder.setFinanceStatus(CommonConstant.YES);
				memberCardOrder.setPayType(PaymentTypeEnum.PAYMENT_WX_APP.getCode());
				memberCardOrder.setUpdateTime(new Date());
				memberCardOrder.setPayCompleteTime(new Date());
				memberCardOrderService.updateByMemberIdOrderCode(memberCardOrder);
				/** 生成益多宝卡号密码等信息*/
				super.createMemberCard(memberId, orderCode);

				/**更新微信支付状态信息*/
				paymentAppWx.setWxStatus(3);
				paymentAppWxService.updateByMemberIdAndOrderCode(paymentAppWx);
				String returnXML = "<xml>"
						+"<return_code><![CDATA[SUCCESS]]></return_code>"
						+"<return_msg><![CDATA[OK]]></return_msg>"
						+"</xml>";
				out.println(returnXML);
				out.close();
				LOGGER.info("微信 阿拉丁卡 微信支付app+++++++++++++success 后台通知成功");
	    	} else {
	    		String returnXML = "<xml>"
						+"<return_code><![CDATA[FAIL]]></return_code>"
						+"<return_msg><![CDATA[非空]]></return_msg>"
						+"</xml>";
				out.println(returnXML);
				out.close();
				LOGGER.info("APP 阿拉丁卡 微信支付回调失败:回调参数校验错误");
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("APP 阿拉丁卡 微信支付 回调出错", e);
		}
	}
	
}
