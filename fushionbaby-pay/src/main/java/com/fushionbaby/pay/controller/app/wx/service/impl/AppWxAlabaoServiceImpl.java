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
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.pay.controller.app.wx.config.AppWxConfig;
import com.fushionbaby.pay.controller.app.wx.service.AppWxAlabaoService;
import com.fushionbaby.pay.controller.app.wx.util.WXUtil;
import com.fushionbaby.pay.controller.app.wx.util.XMLUtil;
import com.fushionbaby.pay.controller.util.OrderNumberUtil;
import com.fushionbaby.payment.model.PaymentAppWx;
import com.fushionbaby.payment.service.PaymentAppWxService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class AppWxAlabaoServiceImpl implements AppWxAlabaoService {
	
	private static final Log LOGGER = LogFactory.getLog(AppWxAlabaoServiceImpl.class);
	
	@Autowired
	private AlabaoOrderService<AlabaoOrder> alabaoOrderService;
	@Autowired
	private PaymentAppWxService<PaymentAppWx> paymentAppWxService;
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	
	
	public Object getAlabaoPrepayId(HttpServletRequest request, String data,
			String mac) throws Exception {
		LOGGER.info("如意宝微信支付接口action--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}
		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(data);
		String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
		String orderCode = json.getAsJsonObject().get("orderCode").getAsString();
		String sourceCode = json.getAsJsonObject().get("sourceCode").getAsString();
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
		PaymentAppWx paymentAppWx = paymentAppWxService.getByMemberIdAndOrderCode(memberId, orderCode);
		if (paymentAppWx != null) {
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
			paymentAppWx.setOrderDes("如意宝订单号:"+orderCode);
			String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			paymentAppWx.setTradeTime(tradeTime);
			paymentAppWx.setWxStatus(1);
			paymentAppWx.setSourceCode(sourceCode);
			paymentAppWx.setRemoteAddr(GetIpAddress.getIpAddr(request));
			paymentAppWx.setOrderCode(orderCode);
			paymentAppWx.setMemberId(memberId);
			paymentAppWxService.updateByMemberIdAndOrderCode(paymentAppWx);
		} else {
			paymentAppWx = new PaymentAppWx();
			paymentAppWx.setOrderNumber(orderNumber);
			paymentAppWx.setOrderCode(orderCode);
			paymentAppWx.setSettleAmount(settleAmount);
			paymentAppWx.setOrderDes("如意宝订单号:"+orderCode);
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
    	parameters.put("body", "如意宝订单号:"+orderCode);
    	/**APP商户号*/
    	parameters.put("mch_id", AppWxConfig.mchId);
    	/**随机字符串*/
    	parameters.put("nonce_str", WXUtil.getNonceStr());
    	parameters.put("notify_url", sysmgrImportanceConfigService.findByCode(
				ImportanceConfigConstant.ALABAOAPPWXNOTIFYURL).getValue());
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
    	LOGGER.info("如意宝APP微信请求参数:"+requestXML);
    	String result =WXUtil.httpsRequest(AppWxConfig.UNIFIED_ORDER_URL, "POST", requestXML);
    	LOGGER.info("如意宝APP微信返回的响应信息:"+result);
    	/**解析微信返回的信息，以Map形式存储便于取值*/
    	Map<String,String> map = XMLUtil.doXMLParse(result);
    	LOGGER.info("如意宝APP微信返回码return_code:"+map.get("return_code")+"且result_code:"+map.get("result_code"));
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
	
	public void notifyAlabaoAppWx(HttpServletRequest request,
			HttpServletResponse response) {
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
			LOGGER.info("如意宝APP微信返回回调XML结果:" + result);
			/**解析微信返回的信息，以Map形式存储便于取值*/
	    	Map<String,String> map = XMLUtil.doXMLParse(result);
	    	LOGGER.info("如意宝map形式存储XML结果:"+map);
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
				AlabaoOrder alabaoOrder = new AlabaoOrder();
				alabaoOrder.setMemberId(memberId);
				alabaoOrder.setOrderCode(orderCode);
				/**已付款*/
				alabaoOrder.setAlabaoStatus(CommonConstant.YES);
				alabaoOrder.setPaymentType(PaymentTypeEnum.PAYMENT_WX_APP.getCode());
				alabaoOrderService.updateByMemberIdOrderCode(alabaoOrder);
				/**如意宝账户主表金额更新-添加--现有的加转入的*/
				AlabaoAccount alabaoAccount = alabaoAccountService.findByMemberId(memberId);
				String settleAmount = map.get("total_fee");//分
				DecimalFormat dftotalFee = new DecimalFormat("0.00");
				String totalFee = dftotalFee.format(new BigDecimal(settleAmount)
						.multiply(BigDecimal.valueOf(0.01)));
				BigDecimal total_fee=new BigDecimal(totalFee);
				/**如意宝转入记录添加*/
				AlabaoShiftToRecord alabaoShiftToRecord = new AlabaoShiftToRecord();
				alabaoShiftToRecord.setAccount(alabaoAccount.getAccount());
				alabaoShiftToRecord.setMemberId(memberId);
				alabaoShiftToRecord.setShiftToAccountType(AlabaoPayTypeConstant.ALABAOAPPWX);//微信
				alabaoShiftToRecord.setTransferMoney(new BigDecimal(totalFee));
				alabaoShiftToRecord.setSerialNum(orderNumber);
				
				LOGGER.info(DateFormat.dateToString(new Date())+"***************如意消费卡支付*********** 账号为"+alabaoAccount.getAccount()+"的转入前后的账户余额分别为："+alabaoAccount.getBalance().add(total_fee)+"和"+alabaoAccount.getBalance()+"转入的金额为："+total_fee);
				alabaoShiftToRecord.setAfterChangeMoney(alabaoAccount.getBalance().add(total_fee));
				alabaoShiftToRecord.setBeforeChangeMoney(alabaoAccount.getBalance());
				alabaoShiftToRecordService.add(alabaoShiftToRecord);
				/**更新微信支付状态信息*/
				paymentAppWx.setWxStatus(3);
				paymentAppWxService.updateByMemberIdAndOrderCode(paymentAppWx);
				
				LOGGER.info("如意宝账户现有金额:"+alabaoAccount.getBalance()+"转入金额"+totalFee);
				alabaoAccount.setBalance(alabaoAccount.getBalance().add(total_fee));
				alabaoAccountService.updateByMemberId(alabaoAccount);
				
				String returnXML = "<xml>"
						+"<return_code><![CDATA[SUCCESS]]></return_code>"
						+"<return_msg><![CDATA[OK]]></return_msg>"
						+"</xml>";
				out.println(returnXML);
				out.close();
				LOGGER.info("微信app+++++++++++++success 后台通知成功");
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
