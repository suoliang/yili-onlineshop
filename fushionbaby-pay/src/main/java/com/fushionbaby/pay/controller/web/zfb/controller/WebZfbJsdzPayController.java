package com.fushionbaby.pay.controller.web.zfb.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladingshop.card.model.MemberCard;
import com.aladingshop.card.service.MemberCardService;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderBaseUser;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderBaseUserService;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.pay.controller.util.NotifyOpeation;
import com.fushionbaby.pay.controller.util.OrderNumberUtil;
import com.fushionbaby.pay.controller.web.zfb.config.AlipayConfigJsdz;
import com.fushionbaby.pay.controller.web.zfb.util.AlipayNotify;
import com.fushionbaby.pay.controller.web.zfb.util.AlipaySubmit;
import com.fushionbaby.payment.model.PaymentWebZfbJsdz;
import com.fushionbaby.payment.service.PaymentWebZfbJsdzService;

/**
 * 支付宝即时到帐接口调用
 * 
 * @author 张明亮
 */
@Controller
@RequestMapping("/webzfbJsdz")
public class WebZfbJsdzPayController extends NotifyOpeation {
	private static Logger LOGGER = Logger.getLogger(WebZfbJsdzPayController.class);
	@Autowired
	private OrderBaseUserService<OrderBaseUser> orderBaseUserService;
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	@Autowired
	private MemberCardService<MemberCard> memberCardService;
	/**
	 * 交易记录service
	 */
	@Autowired
	private PaymentWebZfbJsdzService<PaymentWebZfbJsdz> paymentWebZfbJsdzService;

	/**
	 * 发出支付请求
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/pay")
	public void alipayapi(String sid, String orderCode, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String httpPrefix = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.HTTPPREFIX).getValue();
		if (StringUtils.isBlank(sid)) {
			// response.sendRedirect(httpPrefix +
			// "/pay/gotoPayErr?payErrStatus=1");
			response.sendRedirect(httpPrefix + "/login/index");
			return;// 订单sid不能为空
		}
		if (StringUtils.isBlank(orderCode)) {
			response.sendRedirect(httpPrefix + "/pay/gotoPayErr?payErrStatus=2&orderCode=" + orderCode);
			return;// 订单code不能为空
		}
		UserDto userDto = (UserDto) SessionCache.get(sid);
		if (userDto == null) {
			// response.sendRedirect(httpPrefix +
			// "/pay/gotoPayErr?payErrStatus=3");
			response.sendRedirect(httpPrefix + "/login/index");
			return;// 会员未登录
		}
		Long memberId = userDto.getMemberId();
		OrderFinanceUser orderFinanceUser = orderFinanceUserService.findByMemberIdAndOrderCode(memberId, orderCode);
		if (orderFinanceUser == null) {
			response.sendRedirect(httpPrefix + "/pay/gotoPayErr?payErrStatus=4&orderCode=" + orderCode);
			LOGGER.info("支付失败,订单" + orderCode + "在系统不存在");
			return;// 订单在系统不存在
		}

		// 商户订单号,我们自己生成的交易号
		String out_trade_no = OrderNumberUtil.generateOrdrNo();
		// 商户网站订单系统中唯一订单号，必填
		PaymentWebZfbJsdz paymentWebZfbJsdz = null;
		paymentWebZfbJsdz = paymentWebZfbJsdzService.queryByMemberIdAndOrderCode(memberId, orderCode);
		if (paymentWebZfbJsdz != null) {
			out_trade_no = paymentWebZfbJsdz.getOrderNumber();// 如果存在支付记录，就用原先的支付序列
			if ("TRADE_FINISHED".equals(paymentWebZfbJsdz.getZfbStatus())
					|| "TRADE_SUCCESS".equals(paymentWebZfbJsdz.getZfbStatus())) {
				response.sendRedirect(httpPrefix + "/pay/gotoPayErr?payErrStatus=5&orderCode=" + orderCode);
				LOGGER.info("支付被取消,订单" + orderCode + "已经付过款,不用重复支付");
				return;// 该订单已经付过款,不用重复支付
			}
		}

		// 如果支付方式,和原来的不一样,更新一下支付方式
		if (!PaymentTypeEnum.PAYMENT_ZFB_WEB_JSDZ.getCode().equals(orderFinanceUser.getPaymentType())) {
			// 更新订单采用什么支付方式,支付宝、微信、银联……
			orderFinanceUser.setPaymentType(PaymentTypeEnum.PAYMENT_ZFB_WEB_JSDZ.getCode());
			orderFinanceUserService.updateByMemberIdAndOrderCode(orderFinanceUser);
		}

		// 支付类型
		String payment_type = "1";
		// 必填，不能修改

		// 服务器异步通知页面路径
		// String notify_url = AlipayConfigJsdz.notify_url;
		// 需http://格式的完整路径，不能加?id=123这类自定义参数
		String notify_url = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.JSDZNOTIFYURL).getValue();

		// 页面跳转同步通知页面路径
		// String return_url = AlipayConfigJsdz.return_url;
		// 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
		String return_url = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.JSDZRETURNURL).getValue();

		// 卖家支付宝帐户
		String seller_email = AlipayConfigJsdz.seller_email;
		// 必填

		// 订单名称
		String subject = "阿拉丁订单号:" + orderCode;
		// 必填
		OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(memberId, orderCode);
		// 付款金额
		BigDecimal tprice = orderFeeUser.getTotalActual() == null ? new BigDecimal(0) : orderFeeUser
				.getTotalActual();
		String total_fee = NumberFormatUtil.numberFormat(tprice);
		// 必填

		// 订单描述
		// String body = new
		// String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");

		// 商品展示地址
		// String show_url = new
		// String(request.getParameter("WIDshow_url").getBytes("ISO-8859-1"),"UTF-8");
		// 需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html

		// 防钓鱼时间戳
		// String anti_phishing_key = "";
		// 若要使用请调用类文件submit中的query_timestamp函数

		// 客户端的IP地址
		// String exter_invoke_ip = "";
		// 非局域网的外网IP地址，如：221.0.0.1

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", AlipayConfigJsdz.partner);
		sParaTemp.put("_input_charset", AlipayConfigJsdz.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		// sParaTemp.put("body", body);
		// sParaTemp.put("show_url", show_url);
		// sParaTemp.put("anti_phishing_key", anti_phishing_key);
		// sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

		// start 交易记录
		if (paymentWebZfbJsdz == null) {
			PaymentWebZfbJsdz paymentWebZfbjsdz = new PaymentWebZfbJsdz();
			paymentWebZfbjsdz.setOrderNumber(out_trade_no);
			paymentWebZfbjsdz.setOrderCode(orderCode);
			DecimalFormat df = new DecimalFormat("0");
			total_fee = df.format(new BigDecimal(total_fee).multiply(new BigDecimal(100)));
			paymentWebZfbjsdz.setSettleAmount(total_fee);// 订单结算总金额
			paymentWebZfbjsdz.setOrderDes(subject);
			paymentWebZfbjsdz.setTradeTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			paymentWebZfbjsdz.setMemberId(userDto.getMemberId());
			paymentWebZfbjsdz.setZfbStatus("WAIT_BUYER_PAY");// 支付状态
			paymentWebZfbjsdz.setRemoteAddr(GetIpAddress.getIpAddr(request));
			paymentWebZfbJsdzService.add(paymentWebZfbjsdz);
		}
		// end 交易记录

		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "post", "确认");
		response.setHeader("ContentType", "text/html");
		response.setHeader("Content-Encoding", "utf-8");
		response.setCharacterEncoding("utf-8");
		String htmlTmp = "<html><head><meta charset=\"UTF-8\"><title>alading pay</title><body>" + sHtmlText
				+ "</body></html>";
		response.getWriter().println(htmlTmp);
	}

	/**
	 * 后台异步通知
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/notify")
	public void notify(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();

			// 获取支付宝POST过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "gbk");
				params.put(name, valueStr);
			}

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			// 商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

			if (AlipayNotify.verify(params)) {// 验证成功
				// ////////////////////////////////////////////////////////////////////////////////////////
				// 请在这里加上商户的业务逻辑程序代码

				PaymentWebZfbJsdz paymentWebZfbjsdz = paymentWebZfbJsdzService.queryByOrderNumber(out_trade_no);
				if (paymentWebZfbjsdz != null) {
					if ("TRADE_FINISHED".equals(paymentWebZfbjsdz.getZfbStatus())
							|| "TRADE_SUCCESS".equals(paymentWebZfbjsdz.getZfbStatus())) {
						out.println("success");
						return;
					}
				} else {
					out.println("fail");
					return;
				}
				OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(paymentWebZfbjsdz.getMemberId(), paymentWebZfbjsdz.getOrderCode());
				if (orderFeeUser != null && paymentWebZfbjsdz != null) {

					// 付款金额
					BigDecimal totalActual = orderFeeUser.getTotalActual() == null ? new BigDecimal(0)
							: orderFeeUser.getTotalActual();
					DecimalFormat df = new DecimalFormat("0");
					String totalFee = df.format(totalActual.multiply(new BigDecimal(100)));
					String settlePrice = paymentWebZfbjsdz.getSettleAmount() == null ? "0" : paymentWebZfbjsdz
							.getSettleAmount().trim();
					if (!StringUtils.equals(totalFee, settlePrice)) {
						out.println("fail");
						return;
					}
				} else {
					out.println("fail");
					return;
				}

				// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				Long memberId = paymentWebZfbjsdz.getMemberId();
				String orderCode = paymentWebZfbjsdz.getOrderCode();
				if (trade_status.equals("TRADE_FINISHED")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序

					// 注意：
					// 该种交易状态只在两种情况下出现
					// 1、开通了普通即时到账，买家付款成功后。
					// 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。

					this.paySuccess(out_trade_no, trade_no, trade_status, memberId, orderCode);
				} else if (trade_status.equals("TRADE_SUCCESS")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序

					// 注意：
					// 该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。

					this.paySuccess(out_trade_no, trade_no, trade_status, memberId, orderCode);
				}

				// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

				out.println("success"); // 请不要修改或删除

				// ////////////////////////////////////////////////////////////////////////////////////////
			} else {// 验证失败
				out.println("fail");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 同步通知,付出成功或者失败,web会员浏览器跳转到哪个页面
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/return")
	public void returnUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 获取支付宝GET过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)
			// 商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 支付宝交易号
			// String trade_no = new
			// String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			// 交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

			// 计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);
			PaymentWebZfbJsdz paymentWebZfbJsdz = paymentWebZfbJsdzService.queryByOrderNumber(out_trade_no);
			String orderCode = "";
			if (paymentWebZfbJsdz != null) {
				orderCode = paymentWebZfbJsdz.getOrderCode();
			}
			if (verify_result) {// 验证成功
				// ////////////////////////////////////////////////////////////////////////////////////////
				// 请在这里加上商户的业务逻辑程序代码

				// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序

					response.sendRedirect(sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.HTTPPREFIX)
							.getValue() + "/pay/gotoPayOk?orderCode=" + orderCode);
					return;
				}

				// 该页面可做页面美工编辑
				LOGGER.info(orderCode + "验证成功");
				// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				// ////////////////////////////////////////////////////////////////////////////////////////
			} else {
				// 该页面可做页面美工编辑
				LOGGER.info(orderCode + "验证失败");
				response.sendRedirect(sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.HTTPPREFIX)
						.getValue() + "/pay/gotoPayErr?payErrStatus=6&orderCode=" + orderCode);
				return;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private void paySuccess(String out_trade_no, String trade_no, String trade_status, long memberId, String orderCode) {
		// start 更新交易状态
		PaymentWebZfbJsdz paymentWebZfbJsdz = new PaymentWebZfbJsdz();
		paymentWebZfbJsdz.setOrderNumber(out_trade_no);
		paymentWebZfbJsdz.setZfbTradeNo(trade_no);
		paymentWebZfbJsdz.setZfbStatus(trade_status);
		paymentWebZfbJsdz.setMemberId(memberId);
		paymentWebZfbJsdz.setOrderCode(orderCode);
		paymentWebZfbJsdzService.updateByMemberIdAndOrderCode(paymentWebZfbJsdz);
		// end 更新交易状态

		// 本地系统,订单状态-审核中,财务状态已经付款;
		super.updateLocalState(memberId, orderCode, PaymentTypeEnum.PAYMENT_ZFB_WEB_JSDZ.getCode());
		if (orderCode.contains("ydb")) {
			super.createMemberCard(memberId, orderCode);
		}
	}
}