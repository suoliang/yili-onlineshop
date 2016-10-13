package com.aladingshop.web.controller.pay.union.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aladingshop.web.controller.pay.union.config.WebUnionConfig;
import com.aladingshop.web.controller.pay.union.service.WebUnionService;
import com.aladingshop.web.controller.pay.union.util.DemoBase;
import com.aladingshop.web.util.OrderNumberUtil;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderBaseUser;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.service.OrderBaseUserService;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.core.service.OrderLineUserService;
import com.fushionbaby.facade.biz.common.member.MemberFacade;
import com.fushionbaby.facade.biz.common.yiduobao.YiDuoBaoCardFacade;
import com.fushionbaby.payment.model.PaymentWebUnion;
import com.fushionbaby.payment.service.PaymentWebUnionService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;

/***
 * 
 * @description WEB银联支付实现
 * @author 索亮
 * @date 2015年8月24日下午5:02:45
 */
@Service
public class WebUnionServiceImpl extends DemoBase implements WebUnionService {

	private final static Log LOGGER = LogFactory.getLog(WebUnionServiceImpl.class);
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;
	@Autowired
	private OrderBaseUserService<OrderBaseUser> orderBaseUserService;
	@Autowired
	private PaymentWebUnionService<PaymentWebUnion> paymentWebUnionService;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	/** 订单行 */
	@Autowired
	private OrderLineUserService<OrderLineUser> orderLineUserService;
	@Autowired
	private MemberFacade memberFacade;
	@Autowired
	private YiDuoBaoCardFacade yiDuoBaoCardFacade;

	public void createHtml(String sid, String orderCode, HttpServletRequest request, HttpServletResponse response)
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
		if (!PaymentTypeEnum.PAYMENT_ZXYL_WEB.getCode().equals(orderFinanceUser.getPaymentType())) {
			// 更新订单采用什么支付方式,支付宝、微信、银联……
			orderFinanceUser.setPaymentType(PaymentTypeEnum.PAYMENT_ZXYL_WEB.getCode());
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

		/** 商户网站订单系统中唯一订单号,必填 */
		PaymentWebUnion webUnion = paymentWebUnionService.getByMemberIdAndOrderCode(memberId, orderCode);
		if (webUnion != null) {
			orderNumber = webUnion.getOrderNumber();// 如果存在支付记录，就用原先的支付序列
			// 该订单已经付过款,不用重复支付
			if (webUnion.getUnionStatus() == 2 || webUnion.getUnionStatus() == 3) {
				response.sendRedirect(httpPrefix + "/pay/gotoPayErr?payErrStatus=5&orderCode=" + orderCode);
				LOGGER.info("支付被取消,订单" + orderCode + "已经付过款,不用重复支付");
				return;
			}
		} else {
			/** 保存WEB银联交易记录 */
			webUnion = new PaymentWebUnion();
			webUnion.setOrderNumber(orderNumber);
			webUnion.setMemberId(memberId);
			webUnion.setOrderCode(orderCode);
			webUnion.setSettleAmount(settleAmount);
			webUnion.setTradeTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			webUnion.setOrderDes("阿拉丁订单号:" + orderCode);
			webUnion.setUnionStatus(1);
			webUnion.setRemoteAddr(GetIpAddress.getIpAddr(request));
			paymentWebUnionService.add(webUnion);

		}

		/**
		 * 参数初始化 在java main 方式运行时必须每次都执行加载 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
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
		data.put("channelType", "07");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.YLFRONTURL).getValue());
		// 后台通知地址
		data.put("backUrl", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.YLBACKURL).getValue());
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", WebUnionConfig.merId);
		// 商户订单号，8-40位数字字母
		data.put("orderId", orderNumber);
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，单位分
		data.put("txnAmt", settleAmount);
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");
		// 订单描述，可不上送，上送时控件中会显示该信息
		data.put("orderDesc", "阿拉丁订单号:" + orderCode);

		Map<String, String> submitFromData = signData(data);
		LOGGER.info("WEB银联data签名后的map对象:" + submitFromData);
		// 交易请求url 从配置文件读取
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
		LOGGER.info("WEB银联交易请求url:" + requestFrontUrl);
		/**
		 * 创建表单
		 */
		String html = createHtml(requestFrontUrl, submitFromData);
		LOGGER.info("WEB银联生成表单html:" + html);
		response.getWriter().write(html);
	}

	public void frontReturn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");
		/**
		 * 参数初始化 在java main 方式运行时必须每次都执行加载 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		request.setCharacterEncoding("ISO-8859-1");
		String encoding = request.getParameter(SDKConstants.param_encoding);
		LogUtil.writeLog("返回报文中encoding=[" + encoding + "]");
		LOGGER.info("返回报文中encoding=[" + encoding + "]");
		// String pageResult = "";
		// if ("UTF-8".equalsIgnoreCase(encoding)) {
		// pageResult = "/utf8_result.jsp";
		// } else {
		// pageResult = "/gbk_result.jsp";
		// }
		Map<String, String> respParam = getAllRequestParam(request);

		// 打印请求报文
		LogUtil.printRequestLog(respParam);
		LOGGER.info("WEB银联请求参数报文:" + respParam);

		Map<String, String> valideData = null;
		StringBuffer page = new StringBuffer();
		if (null != respParam && !respParam.isEmpty()) {
			Iterator<Entry<String, String>> it = respParam.entrySet().iterator();
			valideData = new HashMap<String, String>(respParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes("ISO-8859-1"), encoding);
				page.append("<tr><td width=\"30%\" align=\"right\">" + key + "(" + key + ")</td><td>" + value
						+ "</td></tr>");
				valideData.put(key, value);
			}
		}
		String orderNumber = valideData.get("orderId");
		LOGGER.info("WEB银联前台回调商户订单号:" + orderNumber);
		PaymentWebUnion webUnion = paymentWebUnionService.getByOrderNumber(orderNumber);
		final String orderCode = webUnion.getOrderCode();
		if (!SDKUtil.validate(valideData, encoding)) {
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
			LogUtil.writeLog("验证签名结果[失败].");
			LOGGER.info("WEB银联验证签名结果[失败]." + page);
			response.sendRedirect(sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.HTTPPREFIX)
					.getValue()
					+ "/pay/gotoPayErr?unionErrMsg="
					+ valideData.get("respMsg")
					+ "&orderCode="
					+ orderCode);
		} else {
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
			LogUtil.writeLog("验证签名结果[成功].");
			LOGGER.info("WEB银联验证签名结果[成功]." + page);
			response.sendRedirect(sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.HTTPPREFIX)
					.getValue() + "/pay/gotoPayOk?orderCode=" + orderCode);
		}
		// request.setAttribute("result", page.toString());
		// request.getRequestDispatcher(pageResult).forward(request, response);

		LogUtil.writeLog("FrontRcvResponse前台接收报文返回结束");

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
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (res.get(en) == null || "".equals(res.get(en))) {
					// System.out.println("======为空的字段名===="+en);
					res.remove(en);
				}
			}
		}
		return res;
	}

	public void notifyUnion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LogUtil.writeLog("BackRcvResponse接收后台通知开始");
		/**
		 * 参数初始化 在java main 方式运行时必须每次都执行加载 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		request.setCharacterEncoding("ISO-8859-1");
		String encoding = request.getParameter(SDKConstants.param_encoding);
		// 获取请求参数中所有的信息
		Map<String, String> reqParam = getAllRequestParam(request);
		LOGGER.info("WEB银联后台回调请求参数报文:" + reqParam);
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
		LOGGER.info("WEB银联后台回调请求验签数据:" + valideData);
		// 验证签名
		if (!SDKUtil.validate(valideData, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			LOGGER.info("WEB银联后台回调请求验证签名结果[失败]");
		} else {
			LOGGER.info("WEB银联商户订单号:" + valideData.get("orderId")); // 其他字段也可用类似方式获取
			LOGGER.info("WEB银联后台回调验签数据返回码respCode:" + valideData.get("respCode"));
			String orderNumber = valideData.get("orderId");
			PaymentWebUnion webUnion = paymentWebUnionService.getByOrderNumber(orderNumber);
			final Long memberId = webUnion.getMemberId();
			final String orderCode = webUnion.getOrderCode();
			/** 正在处理 */
			webUnion.setUnionStatus(2);
			paymentWebUnionService.updateByMemberIdAndOrderCode(webUnion);
			String orderAmount = webUnion.getSettleAmount();
			if (orderAmount.equals(valideData.get("settleAmt"))) {
				webUnion.setUnionStatus(3);
				webUnion.setTn(valideData.get("queryId"));// tn号
				paymentWebUnionService.updateByMemberIdAndOrderCode(webUnion);
				
				// 本地系统,订单状态-审核中,财务状态已经付款;
				OrderFinanceUser orderFinanceUser = orderFinanceUserService.findByMemberIdAndOrderCode(memberId, orderCode);
				orderFinanceUser.setPaymentType(PaymentTypeEnum.PAYMENT_ZXYL_WEB.getCode());
				orderFinanceUser.setFinanceStatus(CommonConstant.YES);
				orderFinanceUser.setPaymentCompleteTime(new Date());
				orderFinanceUserService.updateByMemberIdAndOrderCode(orderFinanceUser);
				
				OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(memberId, orderCode);
				OrderBaseUser orderBaseUser = new OrderBaseUser();
				orderBaseUser.setOrderCode(orderCode);
				orderBaseUser.setMemberId(memberId);
				/** 此处需求:一次性购200以上就要审核，200以下和购买2次以上不审 */
				List<OrderFinanceUser> list = orderFinanceUserService.findByMIdAndStatus(memberId, CommonConstant.YES);
				BigDecimal totalActual = orderFeeUser.getTotalActual();
				if (CollectionUtils.isEmpty(list)) {
					orderBaseUser.setOrderStatus(OrderConfigServerEnum.AUDIT.getCode());				
				} else if (list.size()>2 && totalActual.compareTo(BigDecimal.valueOf(200))<0) {
					orderBaseUser.setOrderStatus(OrderConfigServerEnum.SUCCESS_AUDIT.getCode());
				} else {
					orderBaseUser.setOrderStatus(OrderConfigServerEnum.AUDIT.getCode());
				}
				orderBaseUserService.updateOrderStatus(orderBaseUser);
			}
			LOGGER.info("WEB银联验证签名结果[成功].");
			LogUtil.writeLog("验证签名结果[成功].");
		}

		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
	}
}
