package com.aladingshop.web.controller.pay;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.facade.biz.common.member.UserFacade;

/**
 * 
 * @author Leon
 *
 */
@Controller
@RequestMapping("/pay")
public class PayController {

	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;

	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;

	@Autowired
	private UserFacade userFacade;

	/***
	 * 检查用户是否付款，用于进行支付成功后页面的跳转
	 * 
	 * @param sid
	 * @param orderCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getPayState")
	public Object getPayState(String sid, String orderCode) {
		UserDto user = userFacade.getLatestUserBySid(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.error();
		}
		OrderFinanceUser orderFinanceUser = orderFinanceUserService.findByMemberIdAndOrderCode(user.getMemberId(),
				orderCode);
		if (ObjectUtils.equals(orderFinanceUser, null)) {
			return Jsonp.error();
		}
		if (StringUtils.equalsIgnoreCase(orderFinanceUser.getFinanceStatus(), CommonConstant.YES)) {
			return Jsonp.success();
		}
		return Jsonp.error();
	}

	@RequestMapping(value = "gotoPayOk")
	public String gotoPayOk(String orderCode, ModelMap model, HttpServletRequest request) {

		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);

		UserDto user = userFacade.getLatestUserBySid(sid);
		OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(user.getMemberId(), orderCode);
		
		String price = orderFeeUser.getTotalActual() + "";
		price = StringUtils.isBlank(price) ? "0.00" : price;

		model.put("price", price);
		model.put("orderCode", orderCode);
		return "pay/pay-success";

	}

	/***
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("gotoPayErr")
	public String gotoPayErr(ModelMap model, HttpServletRequest request) {
		String payErrStatus = request.getParameter("payErrStatus");
		String orderCode = request.getParameter("orderCode");
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		UserDto user = userFacade.getLatestUserBySid(sid);
		
		OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(user.getMemberId(), orderCode);
		
		String price = orderFeeUser.getTotalActual() + "";
		price = StringUtils.isBlank(price) ? "0.00" : price;
		/** 银联提示信息 */
		String unionErrMsg = request.getParameter("unionErrMsg");
		if (!CheckIsEmpty.isEmpty(unionErrMsg)) {
			unionErrMsg = "WEB银联同步验签失败:" + unionErrMsg;
			model.put("errmsg", unionErrMsg);
			model.put("orderCode", orderCode);
			model.put("price", price);
			return "pay/pay-error";
		}
		String errmsg = "";
		if ("1".equals(payErrStatus)) {
			errmsg = "登录超时,或者未登录";
		} else if ("2".equals(payErrStatus)) {
			errmsg = "订单code不能为空";
		} else if ("3".equals(payErrStatus)) {
			errmsg = "登录超时,或者未登录";
		} else if ("4".equals(payErrStatus)) {
			errmsg = "系统没有找到该订单信息";
		} else if ("5".equals(payErrStatus)) {
			errmsg = "支付被取消,订单" + orderCode + "已经付过款,不用重复支付";
		} else if ("6".equals(payErrStatus)) {
			errmsg = "支付宝同步通知,订单" + orderCode + "验签失败";
		} else if ("7".equals(payErrStatus)) {
			errmsg = "WEB微信异步通知,return_code和result_code返回失败";
		}
		model.put("errmsg", errmsg);
		model.put("orderCode", orderCode);
		model.put("price", price);
		return "pay/pay-error";
	}

}
