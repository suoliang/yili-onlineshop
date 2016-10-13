package com.fushionbaby.pay.controller.app.wx.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.pay.controller.app.wx.config.AppWxConfig;
import com.fushionbaby.pay.controller.app.wx.service.AppWxService;
import com.fushionbaby.pay.controller.app.wx.util.WXUtil;
import com.fushionbaby.pay.controller.app.wx.util.XMLUtil;
import com.fushionbaby.pay.controller.util.NotifyOpeation;
import com.fushionbaby.pay.controller.util.OrderNumberUtil;
import com.fushionbaby.payment.model.PaymentAppWx;
import com.fushionbaby.payment.service.PaymentAppWxService;

/**
 * @description APP微信支付实现
 * @author 索亮
 * @date 2015年8月12日下午3:52:31
 */
@Service
public class AppWxServiceImpl extends NotifyOpeation implements AppWxService{
	private final static Log LOGGER = LogFactory.getLog(AppWxServiceImpl.class);
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private PaymentAppWxService<PaymentAppWx> paymentAppWxService;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	
	public Object getPrepayId(HttpServletRequest request,
			String sid, String orderCode,String sourceCode) throws Exception{
		UserDto user = (UserDto) SessionCache.get(sid);
		if (CheckObjectIsNull.isNull(user)) {
			return Jsonp.noLoginError("未登录");
		}
		String settleAmount = super.getOrderPrice(sid, orderCode);
		if (CheckIsEmpty.isEmpty(settleAmount)) {
			return Jsonp.error("订单不存在或已取消!");
		}
		/** 生成商户自己的订单号 */
		String orderNumber = OrderNumberUtil.generateOrdrNo();
		Long memberId = user.getMemberId();
		PaymentAppWx paymentAppWx = paymentAppWxService.getByMemberIdAndOrderCode(memberId, orderCode);
		if (paymentAppWx != null) {
			if (!StringUtils.equals(settleAmount, paymentAppWx.getSettleAmount())) {
				LOGGER.error("订单金额被修改过:orderCode" + orderCode);
				return Jsonp.error("订单金额被修改,不能支付");
			}
			if (paymentAppWx.getWxStatus() == 2 || paymentAppWx.getWxStatus() == 3 ) {
				LOGGER.error("订单已付款:orderCode" + orderCode);
				return Jsonp.error("订单处理中或已付款");
			}
			paymentAppWx.setOrderNumber(orderNumber);
			String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			paymentAppWx.setTradeTime(tradeTime);
			paymentAppWx.setSourceCode(sourceCode);
			paymentAppWx.setRemoteAddr(GetIpAddress.getIpAddr(request));
			paymentAppWxService.updateByMemberIdAndOrderCode(paymentAppWx);
			OrderFinanceUser orderFinanceUser = new OrderFinanceUser();
			orderFinanceUser.setPaymentType(PaymentTypeEnum.PAYMENT_WX_APP.getCode());
			orderFinanceUser.setOrderCode(orderCode);
			orderFinanceUser.setMemberId(memberId);
			orderFinanceUserService.updateByMemberIdAndOrderCode(orderFinanceUser);//修改付款方式
		} else{
			PaymentAppWx pay = new PaymentAppWx();
			pay.setOrderNumber(orderNumber);
			pay.setOrderCode(orderCode);
			pay.setSettleAmount(settleAmount);
			pay.setOrderDes("阿拉丁订单号:" + orderCode);
			String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			pay.setTradeTime(tradeTime);
			pay.setWxStatus(1);
			pay.setSourceCode(sourceCode);
			pay.setRemoteAddr(GetIpAddress.getIpAddr(request));
			pay.setMemberId(memberId);
			paymentAppWxService.add(pay);
		}
		
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
    	parameters.put("appid", AppWxConfig.appId);
    	parameters.put("body", "阿拉丁订单号:"+orderCode);
    	/**APP商户号*/
    	parameters.put("mch_id", AppWxConfig.mchId);
    	/**随机字符串*/
    	parameters.put("nonce_str", WXUtil.getNonceStr());
    	parameters.put("notify_url", sysmgrImportanceConfigService.findByCode(
				ImportanceConfigConstant.APPWXNOTIFYURL).getValue());
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
    	LOGGER.info("APP微信请求参数:"+requestXML);
    	String result =WXUtil.httpsRequest(AppWxConfig.UNIFIED_ORDER_URL, "POST", requestXML);
    	LOGGER.info("APP微信返回的响应信息:"+result);
    	/**解析微信返回的信息，以Map形式存储便于取值*/
    	Map<String,String> map = XMLUtil.doXMLParse(result);
    	LOGGER.info("APP微信返回码return_code:"+map.get("return_code")+"且result_code:"+map.get("result_code"));
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

	public void notifyAppWx(HttpServletRequest request,HttpServletResponse response) {
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
			LOGGER.info("APP微信返回回调XML结果:" + result);
			/**解析微信返回的信息，以Map形式存储便于取值*/
	    	Map<String,String> map = XMLUtil.doXMLParse(result);
	    	LOGGER.info("map形式存储XML结果:"+map);
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
	    		boolean flag = super.updateLocalState(memberId, orderCode, PaymentTypeEnum.PAYMENT_WX_APP.getCode());
				if (flag) {
					paymentAppWx.setWxStatus(3);
					paymentAppWxService.updateByMemberIdAndOrderCode(paymentAppWx);
					String returnXML = "<xml>"
							+"<return_code><![CDATA[SUCCESS]]></return_code>"
							+"<return_msg><![CDATA[OK]]></return_msg>"
							+"</xml>";
					out.println(returnXML);
					out.close();
					LOGGER.info("微信app+++++++++++++success 后台通知成功");
				}
	    	} else {
	    		String returnXML = "<xml>"
						+"<return_code><![CDATA[FAIL]]></return_code>"
						+"<return_msg><![CDATA[非空]]></return_msg>"
						+"</xml>";
				out.println(returnXML);
				out.close();
				LOGGER.info("APP微信回调失败:回调参数校验错误");
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("APP微信回调出错", e);
		}
	}
	
}
