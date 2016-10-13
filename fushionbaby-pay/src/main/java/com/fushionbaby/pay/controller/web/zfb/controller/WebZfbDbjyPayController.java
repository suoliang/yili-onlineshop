package com.fushionbaby.pay.controller.web.zfb.controller;
/*package com.fushionbaby.pay.controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
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

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.LogisticsTypeEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.core.model.SoSalesOrderUser;
import com.fushionbaby.core.model.OrderMemberAddressUser;
import com.fushionbaby.core.service.SoSalesOrderWebUserService;
import com.fushionbaby.core.service.OrderMemberAddressUserService;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.pay.controller.util.OrderNumberUtil;
import com.fushionbaby.pay.controller.web.zfb.config.AlipayConfig;
import com.fushionbaby.pay.controller.web.zfb.util.AlipayNotify;
import com.fushionbaby.pay.controller.web.zfb.util.AlipaySubmit;
import com.fushionbaby.payment.model.PaymentWebZfbDbjy;
import com.fushionbaby.payment.service.PaymentWebZfbDbjyService;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;

*//**
 * web支付宝,支付
 * 
 * @author 张明亮
 *//*
@Controller
@RequestMapping("/webzfb_dbjy")
public class WebZfbDbjyPayController {
	private static Logger logger = Logger.getLogger(WebZfbDbjyPayController.class);
	*//**
	 * 订单操作service
	 *//*
	@Autowired
	private SoSalesOrderWebUserService<SoSalesOrderUser> soSalesOrderWebService;

	*//**
	 * 订单收货地址service
	 *//*
	@Autowired
	private OrderMemberAddressUserService<OrderMemberAddressUser> soSoMemberWebService;
	
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;

	*//**
	 * 地区字典service
	 *//*
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;

	*//**
	 * 交易记录service
	 *//*
	@Autowired
	private PaymentWebZfbDbjyService paymentWebZfbDbjyService;

	*//**
	 * 发出支付请求
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *//*
	@RequestMapping("/pay")
	public void alipayapi(String sid, String orderCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (StringUtils.isBlank(sid)) {
			logger.error("订单sid不能为空,订单code="+orderCode);
			response.sendRedirect(sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.HTTPPREFIX).getValue() + "/pay/goto_pay_err.do?pay_err_status=1");
			return;// 订单sid不能为空
		}
		if (StringUtils.isBlank(orderCode)) {
			logger.error("订单code不能为空,sid="+sid);
			response.sendRedirect(sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.HTTPPREFIX).getValue() + "/pay/goto_pay_err.do?pay_err_status=2");
			return;// 订单code不能为空
		}
		UserDto userDto = (UserDto) SessionCache.get(sid);
		if (userDto == null) {
			logger.error("会员未登录或者登陆超时,订单code="+orderCode+",sid="+sid);
			response.sendRedirect(sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.HTTPPREFIX).getValue() + "/pay/goto_pay_err.do?pay_err_status=3");
			return;// 会员未登录
		}

		SoSalesOrderUser order = soSalesOrderWebService.getOrderByMemberIdAndOrderCode(userDto.getMemberId(), orderCode);
		if (order == null) {
			response.sendRedirect(sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.HTTPPREFIX).getValue() + "/pay/goto_pay_err.do?pay_err_status=4");
			logger.info("支付失败,订单" + orderCode + "在系统不存在,sid="+sid);
			return;// 订单在系统不存在
		}
		
		// 商户订单号,我们自己生成的交易号
		String out_trade_no = OrderNumberUtil.generateOrdrNo();
		// 商户网站订单系统中唯一订单号，必填
		
		PaymentWebZfbDbjy PaymentWebZfbDbjy = null;
		PaymentWebZfbDbjy = paymentWebZfbDbjyService.queryBySoCode(orderCode);
		if (PaymentWebZfbDbjy != null) {
			out_trade_no=PaymentWebZfbDbjy.getOrderNumber();
			if(PaymentWebZfbDbjy.getZfbStatus()>=2){
				response.sendRedirect(sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.HTTPPREFIX).getValue() + "/pay/goto_pay_err.do?pay_err_status=5&order_code="+orderCode);
				logger.info("支付被取消,订单" + orderCode + "已经付过款,不用重复支付sid="+sid);
				return;//该订单已经付过款,不用重复支付
			}
		}
		
		//如果支付方式,和原来的不一样,更新一下支付方式
		if(!PaymentTypeEnum.PAYMENT_ZFB_WEB_DBJY.getCode().equals(order.getPaymentType())){
			//更新订单采用什么支付方式,支付宝、微信、银联……
			soSalesOrderWebService.updatePaymentType(
					userDto.getMemberId(), orderCode, PaymentTypeEnum.PAYMENT_ZFB_WEB_DBJY.getCode());
		}

		// 支付类型
		String payment_type = "1";
		// 必填，不能修改

		// 服务器异步通知页面路径
		String notify_url = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.DBJYNOTIFYURL).getValue();
		// 需http://格式的完整路径，不能加?id=123这类自定义参数

		// 页面跳转同步通知页面路径
		String return_url = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.DBJYRETURNURL).getValue();
		// 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

		// 卖家支付宝帐户
		String seller_email = AlipayConfig.seller_email;
		// 必填

		// 订单名称
		String subject = "Fushionbaby订单号:"+orderCode;
		// 必填

		// 商品数量
		String quantity = "1";
		// 必填，建议默认为1，不改变值，把一次交易看成是一次下订单而非购买一件商品

		// 物流类型
		String logistics_type = "EXPRESS";
		// 必填，三个值可选：EXPRESS（快递）、POST（平邮）、EMS（EMS）

		// 物流支付方式
		String logistics_payment = "BUYER_PAY";
		// 必填，两个值可选：SELLER_PAY（卖家承担运费）、BUYER_PAY（买家承担运费

		// 物流费用
		//BigDecimal fee = order.getActualTransferFee() == null ? new BigDecimal(0) : order.getActualTransferFee();
		//String logistics_fee = NumberFormatUtil.numberFormat(fee);
		String logistics_fee = "0";
		// 必填，即运费

		// 付款金额,总价减去运费
		BigDecimal tprice = order.getTotalActual() == null ? new BigDecimal(0) : order.getTotalActual();
		String price = NumberFormatUtil.numberFormat(tprice);
		// 必填

		// 订单描述
		// String body = new
		// String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");

		// 商品展示地址
		// String show_url = new
		// String(request.getParameter("WIDshow_url").getBytes("ISO-8859-1"),"UTF-8");
		// 需以http://开头的完整路径，如：http://www.商户网站.com/myorder.html

		OrderMemberAddressUser addressObj = soSoMemberWebService.getOrderAddress(orderCode);

		if (addressObj == null) {
			return;// 订单收货地址不存在
		}

		// 收货人姓名
		String receive_name = addressObj.getReceiver();
		// 如：张三

		// 收货人地址
		String provinceName = memberAreaService.getByAreaCode(addressObj.getProvince());
		String cityName = memberAreaService.getByAreaCode(addressObj.getCity());
		String districtName = memberAreaService.getByAreaCode(addressObj.getDistrict());
		String addressTmp = provinceName + cityName + districtName + addressObj.getAddress();
		String receive_address = addressTmp;
		// 如：XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号

		// 收货人邮编
		String receive_zip = addressObj.getZipcode();
		// 如：123456

		// 收货人电话号码
		String receive_phone = addressObj.getReceiverPhone();
		// 如：0571-88158090

		// 收货人手机号码
		String receive_mobile = addressObj.getReceiverMobile();
		// 如：13312341234

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_partner_trade_by_buyer");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("price", price);
		sParaTemp.put("quantity", quantity);
		sParaTemp.put("logistics_fee", logistics_fee);
		sParaTemp.put("logistics_type", logistics_type);
		sParaTemp.put("logistics_payment", logistics_payment);
		// sParaTemp.put("body", body);
		// sParaTemp.put("show_url", show_url);
		sParaTemp.put("receive_name", receive_name);
		sParaTemp.put("receive_address", receive_address);
		sParaTemp.put("receive_zip", receive_zip);
		sParaTemp.put("receive_phone", receive_phone);
		sParaTemp.put("receive_mobile", receive_mobile);

		// start 交易记录
		if(PaymentWebZfbDbjy == null){
			PaymentWebZfbDbjy paymentWebZfbDbjy = new PaymentWebZfbDbjy();
			paymentWebZfbDbjy.setOrderNumber(out_trade_no);
			paymentWebZfbDbjy.setSoCode(orderCode);
			paymentWebZfbDbjy.setTotalAmount(price);// 订单结算总金额
			paymentWebZfbDbjy.setLogisticsType(logistics_type);
			paymentWebZfbDbjy.setLogisticsPayment(logistics_payment);
			paymentWebZfbDbjy.setLogisticsFee(logistics_fee);
			paymentWebZfbDbjy.setNoFreightAmount(price);
			paymentWebZfbDbjy.setSoDes(subject);
			paymentWebZfbDbjy.setMemberId(userDto.getMemberId());
			paymentWebZfbDbjy.setRemoteAddr(GetIpAddress.getIpAddr(request));
			paymentWebZfbDbjyService.add(paymentWebZfbDbjy);
		}
		// end 交易记录

		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "post", "确认");
		response.setHeader("ContentType", "text/html");
		response.setHeader("Content-Encoding", "utf-8");
		response.setCharacterEncoding("utf-8");
		String htmlTmp = "<html><head><meta charset=\"UTF-8\"><title>fushionbaby pay</title><body>" + sHtmlText + "</body></html>";
		response.getWriter().println(htmlTmp);
	}

	*//**
	 * 后台异步通知
	 * 
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping("/notify")
	public void notify(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();

			// 获取支付宝POST过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
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

				PaymentWebZfbDbjy paymentWebZfbDbjy = null;
				paymentWebZfbDbjy = paymentWebZfbDbjyService.queryByOrderNumber(out_trade_no);

				SoSalesOrderUser order = null;
				if (paymentWebZfbDbjy != null) {
					order = soSalesOrderWebService.getOrderByMemberIdAndOrderCode(paymentWebZfbDbjy.getMemberId(), paymentWebZfbDbjy.getSoCode());
					if (order != null) {

						// 物流费用
						BigDecimal fee = order.getActualTransferFee() == null ? new BigDecimal(0) : order.getActualTransferFee();

						// 付款金额,总价减去运费
						BigDecimal tprice = order.getTotalActual() == null ? new BigDecimal(0) : order.getTotalActual().subtract(fee);

						String sprice = paymentWebZfbDbjy.getNoFreightAmount() == null ? "0" : paymentWebZfbDbjy.getNoFreightAmount().trim();
						Double zfprice = Double.valueOf(sprice);
						Double oprice = tprice.doubleValue();

						if (zfprice >= oprice) {
							// ////////////////////////////////////////////////////////////////////////////////////////
							// 请在这里加上商户的业务逻辑程序代码

							// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

							if (trade_status.equals("WAIT_BUYER_PAY")) {
								// 该判断表示买家已在支付宝交易管理中产生了交易记录，但没有付款

								// 判断该笔订单是否在商户网站中已经做过处理
								// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
								// 如果有做过处理，不执行商户的业务程序

								if (paymentWebZfbDbjy.getZfbStatus() < 1) {
									Long memberId = paymentWebZfbDbjy.getMemberId();
									
									// start 更新交易状态
									PaymentWebZfbDbjy paymentWebStatus = new PaymentWebZfbDbjy();
									paymentWebStatus.setOrderNumber(out_trade_no);
									paymentWebStatus.setMemberId(memberId);;
									paymentWebStatus.setZfbTradeNo(trade_no);
									paymentWebStatus.setZfbStatus(1);
									paymentWebZfbDbjyService.updateByOrderNumberAndMemberId(paymentWebStatus);
									// end 更新交易状态

									String orderCode = paymentWebStatus.getSoCode();
									// 本地系统订单状态,等待支付;财务状态-未付款,物流状态-未发货
									soSalesOrderWebService.updateWaitingPayment(memberId, orderCode);
									soSalesOrderWebService.updateFinanceStatusUnpaid(memberId, orderCode);
									soSalesOrderWebService.updateTransStatusNo(memberId, orderCode);
								} else {
									// 处理过,不做任何处理
								}

								out.println("success"); // 请不要修改或删除
							} else if (trade_status.equals("WAIT_SELLER_SEND_GOODS")) {
								// 该判断表示买家已在支付宝交易管理中产生了交易记录且付款成功，但卖家没有发货

								// 判断该笔订单是否在商户网站中已经做过处理
								// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
								// 如果有做过处理，不执行商户的业务程序

								if (paymentWebZfbDbjy.getZfbStatus() < 2) {

									// start 更新交易状态
									PaymentWebZfbDbjy paymentWebStatus = new PaymentWebZfbDbjy();
									paymentWebStatus.setOrderNumber(out_trade_no);
									paymentWebStatus.setZfbTradeNo(trade_no);
									paymentWebStatus.setZfbStatus(2);
									paymentWebZfbDbjyService.updateByOrderNumberAndMemberId(paymentWebStatus);
									// end 更新交易状态

									Long memberId = paymentWebZfbDbjy.getMemberId();
									String orderCode = paymentWebZfbDbjy.getSoCode();
									// 本地系统,订单状态-审核中,财务状态已经付款;物流状态-未发货
									soSalesOrderWebService.updateAudit(memberId, orderCode);
									soSalesOrderWebService.updateFinanceStatusPaid(memberId, orderCode);
									soSalesOrderWebService.updateTransStatusNo(memberId, orderCode);
								} else {
									// 处理过,不做任何处理
								}

								out.println("success"); // 请不要修改或删除
							} else if (trade_status.equals("WAIT_BUYER_CONFIRM_GOODS")) {
								// 该判断表示卖家已经发了货，但买家还没有做确认收货的操作

								// 判断该笔订单是否在商户网站中已经做过处理
								// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
								// 如果有做过处理，不执行商户的业务程序

								if (paymentWebZfbDbjy.getZfbStatus() < 3) {

									// start 更新交易状态
									PaymentWebZfbDbjy paymentWebStatus = new PaymentWebZfbDbjy();
									paymentWebStatus.setOrderNumber(out_trade_no);
									paymentWebStatus.setZfbTradeNo(trade_no);
									paymentWebStatus.setZfbStatus(3);
									paymentWebZfbDbjyService.updateByOrderNumberAndMemberId(paymentWebStatus);
									// end 更新交易状态

									Long memberId = paymentWebZfbDbjy.getMemberId();
									String orderCode = paymentWebZfbDbjy.getSoCode();
									// 本地系统,订单状态,已发货;财务状态-已付款,物流状态,已发货
									soSalesOrderWebService.updateSuccessShipped(memberId, orderCode);
									soSalesOrderWebService.updateFinanceStatusPaid(memberId, orderCode);
									soSalesOrderWebService.updateTransStatusYes(memberId, orderCode);
								} else {
									// 处理过,不做任何处理
								}

								out.println("success"); // 请不要修改或删除
							} else if (trade_status.equals("TRADE_FINISHED")) {
								// 该判断表示买家已经确认收货，这笔交易完成

								// 判断该笔订单是否在商户网站中已经做过处理
								// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
								// 如果有做过处理，不执行商户的业务程序

								if (paymentWebZfbDbjy.getZfbStatus() < 4) {

									// start 更新交易状态
									PaymentWebZfbDbjy paymentWebStatus = new PaymentWebZfbDbjy();
									paymentWebStatus.setOrderNumber(out_trade_no);
									paymentWebStatus.setZfbTradeNo(trade_no);
									paymentWebStatus.setZfbStatus(4);
									paymentWebZfbDbjyService.updateByOrderNumberAndMemberId(paymentWebStatus);
									// end 更新交易状态

									Long memberId = paymentWebZfbDbjy.getMemberId();
									String orderCode = paymentWebZfbDbjy.getSoCode();
									// 本地系统,订单状态,交易完成;财务状态-已付款;物流状态-已发货
									soSalesOrderWebService.updateSuccessTransaction(memberId, orderCode);
									soSalesOrderWebService.updateFinanceStatusPaid(memberId, orderCode);
									soSalesOrderWebService.updateTransStatusYes(memberId, orderCode);
								} else {
									// 处理过,不做任何处理
								}
								out.println("success"); // 请不要修改或删除
							} else {
								out.println("success"); // 请不要修改或删除
							}

							// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
							logger.info("success 后台通知成功");
							// ////////////////////////////////////////////////////////////////////////////////////////
						} else {
							out.println("fail");
						}
					} else {
						out.println("fail");
					}
				} else {
					out.println("fail");
				}

			} else {// 验证失败
				logger.info("fail 支付失败");
				out.println("fail");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	*//**
	 * 同步通知,付出成功或者失败,web会员浏览器跳转到哪个页面
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *//*
	@RequestMapping("/return")
	public void returnUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			PrintWriter out = response.getWriter();

			// 获取支付宝GET过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
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
			//String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

			// 计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);

			PaymentWebZfbDbjy paymentWebStatus = paymentWebZfbDbjyService.queryByOrderNumber(out_trade_no);

			if (verify_result) {// 验证成功
				// ////////////////////////////////////////////////////////////////////////////////////////
				// 请在这里加上商户的业务逻辑程序代码

				// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

				if (trade_status.equals("WAIT_SELLER_SEND_GOODS")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序

					String priceTmp = "";
					if (paymentWebStatus != null) {
						priceTmp = paymentWebStatus.getNoFreightAmount();
					}
					priceTmp = StringUtils.isBlank(priceTmp) ? "" : priceTmp;
					response.sendRedirect(sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.HTTPPREFIX).getValue() + "/pay/goto_pay_ok.do?price=" + priceTmp);
					return;
				}

				// 该页面可做页面美工编辑
				// out.println("<html><head><meta charset=\"UTF-8\"><title>fushionbaby pay</title><body>"+logWebPayStatus.getSoCode()+"验证成功<br /></body></html>");

				logger.error(paymentWebStatus.getSoCode() + "验证成功");

				// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

				// ////////////////////////////////////////////////////////////////////////////////////////
			} else {
				// 该页面可做页面美工编辑
				// out.println(logWebPayStatus.getSoCode()+"验证失败");
				logger.error(paymentWebStatus.getSoCode() + "验证失败");
				response.sendRedirect(sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.HTTPPREFIX).getValue() + "/pay/goto_pay_err.do");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 确认发货接口
	 * 
	 * @param orderCode
	 *            订单编号
	 * @param logisticsName
	 *            物流公司名称
	 * @param invoiceNo
	 *            物流单号
	 * @return
	 *//*
	@RequestMapping("send")
	public void sendGoods(String orderCode, String logisticsName, String invoiceNo, HttpServletResponse response) {

		try {
			logisticsName = URLDecoder.decode(logisticsName, "utf-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}

		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		PaymentWebZfbDbjy paymentWebStatus = paymentWebZfbDbjyService.queryBySoCode(orderCode);
		if (paymentWebStatus == null) {
			out.print(StringUtils.EMPTY);
		}
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "send_goods_confirm_by_platform");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("trade_no", paymentWebStatus.getZfbTradeNo());
		sParaTemp.put("logistics_name", logisticsName);
		sParaTemp.put("invoice_no", invoiceNo);
		sParaTemp.put("transport_type", LogisticsTypeEnum.EXPRESS.toString());
		String sHtmlText = null;
		try {
			sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(StringUtils.EMPTY);
		}
		out.print(sHtmlText);
	}
}
*/