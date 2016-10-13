/**
 * 
 */
package com.fushionbaby.pay.controller.app.union.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
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
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.pay.controller.app.union.conf.UpmpConfig;
import com.fushionbaby.pay.controller.app.union.service.AppUnionService;
import com.fushionbaby.pay.controller.app.union.util.DemoBase;
import com.fushionbaby.pay.controller.util.OrderNumberUtil;
import com.fushionbaby.payment.model.PaymentAppUnion;
import com.fushionbaby.payment.service.PaymentAppUnionService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;

/**
 * @description 类描述...
 * @author 索亮
 * @date 2015年8月11日下午6:13:42
 */
@Service
public class AppUnionServiceImpl extends DemoBase implements AppUnionService {
	private final static Log LOGGER = LogFactory.getLog(AppUnionServiceImpl.class);
	@Autowired
	private PaymentAppUnionService<PaymentAppUnion> paymentAppUnionService;
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	
	public Object getTN(HttpServletRequest request, String sid,
			String orderCode, String sourceCode) {
		String settleAmount = super.getOrderPrice(sid, orderCode);
		if (CheckIsEmpty.isEmpty(settleAmount)) {
			return Jsonp.error("订单不存在或已取消!");
		}
		/** 生成商户自己的订单号 */
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
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码 默认"UTF-8"
		data.put("encoding", "UTF-8");
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 01-消费
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", "");
		// 后台通知地址
		data.put("backUrl", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.APPUNIONBACKURL).getValue());
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", UpmpConfig.MER_ID);
		// 商户订单号，8-40位数字字母
		data.put("orderId", orderNumber);
		String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		// 订单发送时间，取系统时间
		data.put("txnTime", tradeTime);
		// 交易金额，单位分
		data.put("txnAmt", settleAmount);
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");
		// 订单描述，可不上送，上送时控件中会显示该信息
		data.put("orderDesc", "阿拉丁订单号:"+orderCode);

		data = signData(data);

		// 交易请求url 从配置文件读取
		String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();

		Map<String, String> resmap = submitUrl(data, requestAppUrl);
		LOGGER.info("请求报文=["+data.toString()+"]");
		LOGGER.info("应答报文=["+resmap.toString()+"]");
		if (resmap != null && resmap.get("respCode") != null && resmap.get("respCode").equals("00")) {
			LOGGER.info("银联APP订单推送签名验证成功======tn: " + resmap.get("tn"));
			UserDto user = (UserDto) SessionCache.get(sid);
			Long memberId = user.getMemberId();
			PaymentAppUnion paymentAppUnion = paymentAppUnionService.getByMemberIdAndOrderCode(memberId, orderCode);
			if (paymentAppUnion != null) {
				if (!paymentAppUnion.getSettleAmount().equals(settleAmount)) {
					LOGGER.error("订单金额被修改过: orderCode" + orderCode);
					return Jsonp.error("订单金额被修改过");
				}
				if (paymentAppUnion.getUnionStatus() == 1) {
					//if((System.currentTimeMillis() - paymentAppUnion.getCreateTime().getTime()) / 1000 <= 60 * 90) {
					//	  return Jsonp_data.success(paymentAppUnion.getTn());
					//}
					paymentAppUnion.setOrderNumber(orderNumber);
					paymentAppUnion.setSourceCode(sourceCode);
					paymentAppUnion.setTradeTime(tradeTime);
					paymentAppUnion.setRemoteAddr(GetIpAddress.getIpAddr(request));
					paymentAppUnionService.updateByMemberIdAndOrderCode(paymentAppUnion);
					OrderFinanceUser orderFinanceUser = new OrderFinanceUser();
					orderFinanceUser.setPaymentType(PaymentTypeEnum.PAYMENT_ZXYL_APP.getCode());
					orderFinanceUser.setOrderCode(orderCode);
					orderFinanceUser.setMemberId(memberId);
					orderFinanceUserService.updateByMemberIdAndOrderCode(orderFinanceUser);//修改付款方式
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
			payUnion.setOrderDes("阿拉丁订单号:" + orderCode);
			payUnion.setTradeTime(tradeTime);
			payUnion.setUnionStatus(1);
			payUnion.setRemoteAddr(GetIpAddress.getIpAddr(request));
			paymentAppUnionService.add(payUnion);
			
			return Jsonp_data.success(resmap.get("tn"));
		} else if(resmap != null && resmap.get("respCode") != null) {
			LOGGER.info("银联APP订单推送签名验证失败======"+resmap.get("respMsg"));
			return Jsonp.error("银联APP签名返回码"+resmap.get("respCode")+"有误");
		} else {
			LOGGER.info("银联APP订单支付请求tn号失败======resmap.get('respCode')没有获取到");
			return Jsonp.error("银联APP订单支付请求tn号失败");
		}
	}
	
	public void notifyBack(HttpServletRequest request) throws Exception {
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
			/** 正在处理 */
			payStatus.setUnionStatus(2);
			payStatus.setTn(valideData.get("queryId"));//交易流水号
			paymentAppUnionService.updateByMemberIdAndOrderCode(payStatus);
			String orderAmount = payStatus.getSettleAmount();
			if(orderAmount.equals(valideData.get("settleAmt"))) {
				//业务逻辑代码开始
				boolean flag = super.updateLocalState(memberId, orderCode, PaymentTypeEnum.PAYMENT_ZXYL_APP.getCode());
				if (flag) {
					payStatus.setUnionStatus(3);
					paymentAppUnionService.updateByMemberIdAndOrderCode(payStatus);
				}
				LOGGER.info("========银联支付交易成功  orderNumber: " + orderNumber);
			} else {
				//金额不正确
				payStatus.setUnionStatus(4);
				paymentAppUnionService.updateByMemberIdAndOrderCode(payStatus);
				LOGGER.info("========银联支付交易成功  金额有错误 (银联交易金额: " + valideData.get("settleAmt") + "  订单金额:" + orderAmount + ") orderNumber: " + orderNumber);
			}
			LogUtil.writeLog("验证签名结果[成功].");
			LOGGER.info("后台回调请求验证签名结果[成功]");
		}
		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
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
