package com.fushionbaby.pay.controller.app.union.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.card.model.MemberCardOrder;
import com.aladingshop.card.service.MemberCardOrderService;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.enums.OrderConfigClientEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.pay.controller.app.union.conf.UpmpConfig;
import com.fushionbaby.pay.controller.app.union.service.AppUnionYiduobaoService;
import com.fushionbaby.pay.controller.app.union.util.DemoBase;
import com.fushionbaby.pay.controller.util.OrderNumberUtil;
import com.fushionbaby.payment.model.PaymentAppUnion;
import com.fushionbaby.payment.service.PaymentAppUnionService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
/***
 * 阿拉丁卡订单  使用银联支付
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月30日下午4:39:36
 */
@Service
public class AppUnionYiduobaoServiceImpl  extends DemoBase  implements AppUnionYiduobaoService {

	
	private static final Log LOGGER=LogFactory.getLog(AppUnionYiduobaoServiceImpl.class);
	
	/**阿拉丁卡订单*/
	@Autowired
	private MemberCardOrderService<MemberCardOrder> memberCardOrderService;
	/**重要配置*/
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	/** 银联支付记录表*/
	@Autowired
	private PaymentAppUnionService<PaymentAppUnion> paymentAppUnionService;
	
	public Object getYiduobaoUnionTn(HttpServletRequest request, String data,String mac) {
		LOGGER.info("阿拉丁卡银联支付接口action--请求报文：{" + data + "}");
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
		MemberCardOrder cardOrder = memberCardOrderService.findByMemberIdOrderCode(memberId, orderCode);
		if (ObjectUtils.equals(cardOrder, null)) {
			return Jsonp.error("无此阿拉丁卡订单记录");
		}
		DecimalFormat df = new DecimalFormat("0");
		String settleAmount = new BigDecimal(df.format(cardOrder.getTotalActual().multiply(new BigDecimal(100)))).toString();
		//生成交易的订单编号
		String orderNumber = OrderNumberUtil.generateOrdrNo();
		/**
		 * 参数初始化
		 * 在java main 方式运行时必须每次都执行加载
		 * 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		/**
		 * 组装请求报文
		 */
		Map<String, String> map = new HashMap<String, String>();
		// 版本号
		map.put("version", "5.0.0");
		// 字符集编码 默认"UTF-8"
		map.put("encoding", "UTF-8");
		// 签名方法 01 RSA
		map.put("signMethod", "01");
		// 交易类型 01-消费
		map.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		map.put("txnSubType", "01");
		// 业务类型
		map.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		map.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		map.put("frontUrl", "");
		// 后台通知地址
		map.put("backUrl", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.YIDUOBAO_APP_UNION_NOTIFY_URL).getValue());
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		map.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		map.put("merId", UpmpConfig.MER_ID);
		// 商户订单号，8-40位数字字母
		map.put("orderId", orderNumber);
		String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		// 订单发送时间，取系统时间
		map.put("txnTime", tradeTime);
		// 交易金额，单位分
		map.put("txnAmt", settleAmount);
		// 交易币种
		map.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");
		// 订单描述，可不上送，上送时控件中会显示该信息
		map.put("orderDesc", "阿拉丁卡订单号:"+orderCode);

		map = signData(map);

		// 交易请求url 从配置文件读取
		String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();

		Map<String, String> resmap = submitUrl(map, requestAppUrl);
		LOGGER.info("请求报文=["+map.toString()+"]");
		LOGGER.info("应答报文=["+resmap.toString()+"]");
		if (resmap != null && resmap.get("respCode") != null && resmap.get("respCode").equals("00")) {
			PaymentAppUnion paymentAppUnion = paymentAppUnionService.getByMemberIdAndOrderCode(memberId, orderCode);
			if (ObjectUtils.notEqual(paymentAppUnion, null)) {
				if (!paymentAppUnion.getSettleAmount().equals(settleAmount)) {
					LOGGER.error("订单金额被修改过: orderCode" + orderCode);
					return Jsonp.error("订单金额被修改,不能支付");
				}
				if (paymentAppUnion.getUnionStatus() == 1) {
					paymentAppUnion.setOrderNumber(orderNumber);
					paymentAppUnion.setSourceCode(sourceCode);
					paymentAppUnion.setTradeTime(tradeTime);
					paymentAppUnion.setRemoteAddr(GetIpAddress.getIpAddr(request));
					paymentAppUnionService.updateByMemberIdAndOrderCode(paymentAppUnion);
					return Jsonp_data.success(resmap.get("tn"));
				} 
				return Jsonp_data.success("订单正在处理中或已处理完毕无需再支付!");
			}
			PaymentAppUnion payUnion = new PaymentAppUnion();
			payUnion.setOrderNumber(orderNumber);
			payUnion.setOrderCode(orderCode);
			payUnion.setMemberId(memberId);
			payUnion.setSourceCode(sourceCode);
			payUnion.setSettleAmount(settleAmount);
			payUnion.setOrderDes("阿拉丁卡订单号:" + orderCode);
			payUnion.setTradeTime(tradeTime);
			payUnion.setUnionStatus(1);
			payUnion.setRemoteAddr(GetIpAddress.getIpAddr(request));
			paymentAppUnionService.add(payUnion);
			
			return Jsonp_data.success(resmap.get("tn"));
		}
		LOGGER.info("银联APP订单支付请求tn号失败======resmap.get('respCode')没有获取到");
		return Jsonp.error("银联APP订单支付请求tn号失败");
	}

	
	
	
	public void YiduobaoUnionNotify(HttpServletRequest request,	HttpServletResponse response) {
		try {
			LogUtil.writeLog("BackRcvResponse接收后台通知开始");
			/**
			 * 参数初始化
			 * 在java main 方式运行时必须每次都执行加载
			 * 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
			 */
			SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
			request.setCharacterEncoding("ISO-8859-1");
			String encoding = request.getParameter(SDKConstants.param_encoding);
			// 获取请求参数中所有的信息
			Map<String, String> reqParam = getAllRequestParam(request);
			LOGGER.info("后台回调请求参数报文"+reqParam);
			// 打印请求报文
			LogUtil.printRequestLog(reqParam);

			Map<String, String> valideData = null;
			if (null != reqParam && !reqParam.isEmpty()) {
				Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
				valideData = new HashMap<String, String>(reqParam.size());
				while (it.hasNext()) {
					Entry<String, String> e = it.next();
					String key = (String) e.getKey();
					String value = (String) e.getValue();
					value = new String(value.getBytes("ISO-8859-1"), encoding);
					valideData.put(key, value);
				}
			}
			LOGGER.info("后台回调请求验签数据"+valideData);
			// 验证签名
			if (!SDKUtil.validate(valideData, encoding)) {
				LogUtil.writeLog("验证签名结果[失败].");
				LOGGER.info("后台回调请求验证签名结果[失败]");
			} else {
				LOGGER.info("商户订单号"+valideData.get("orderId")); //其他字段也可用类似方式获取
				LOGGER.info("后台回调验签数据返回码respCode"+valideData.get("respCode"));
				String orderNumber = valideData.get("orderId");
				PaymentAppUnion payStatus = paymentAppUnionService.getByOrderNumber(orderNumber);
				final Long memberId = payStatus.getMemberId();
				final String orderCode = payStatus.getOrderCode();
				//业务逻辑代码开始  更新阿拉丁卡订单
				MemberCardOrder cardOrder = this.memberCardOrderService.findByMemberIdOrderCode(memberId, orderCode);
				cardOrder.setMemberId(memberId);
				cardOrder.setOrderCode(orderCode);
				cardOrder.setPayType(PaymentTypeEnum.PAYMENT_ZXYL_APP.getCode());
				cardOrder.setUpdateTime(new Date());
				/** 正在处理 */
				payStatus.setUnionStatus(2);
				payStatus.setTn(valideData.get("queryId"));//交易流水号
				paymentAppUnionService.updateByMemberIdAndOrderCode(payStatus);
				String orderAmount = payStatus.getSettleAmount();
				if(orderAmount.equals(valideData.get("settleAmt"))) {
					payStatus.setUnionStatus(3);
					paymentAppUnionService.updateByMemberIdAndOrderCode(payStatus);
					cardOrder.setOrderStatus(OrderConfigClientEnum.SUCCESS_TRANSACTION.getCode());
					cardOrder.setFinanceStatus(CommonConstant.YES);
					cardOrder.setPayCompleteTime(new Date());
					/** 生成阿拉丁卡卡号密码等信息*/
					super.createMemberCard(memberId, orderCode);
					LOGGER.info("========银联支付交易成功  orderNumber: " + orderNumber);
				} else {
					//金额不正确
					payStatus.setUnionStatus(4);
					paymentAppUnionService.updateByMemberIdAndOrderCode(payStatus);
					cardOrder.setOrderStatus(OrderConfigClientEnum.WAITING_PAYMENT.getCode());
					cardOrder.setFinanceStatus(CommonConstant.NO);
					LOGGER.info("========银联支付交易成功  金额有错误 (银联交易金额: " + valideData.get("settleAmt") + "  订单金额:" + orderAmount + ") orderNumber: " + orderNumber);
				}
				memberCardOrderService.updateByMemberIdOrderCode(cardOrder);
				LogUtil.writeLog("验证签名结果[成功].");
				LOGGER.info("后台回调请求验证签名结果[成功]");
			}
			LogUtil.writeLog("BackRcvResponse接收后台通知结束");
		} catch (Exception e) {
			LOGGER.error("支付服务  阿拉丁卡使用银联支付 AppUnionYiduobaoServiceImpl.java 异常", e);
		}
	}
	
	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				//System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
}
