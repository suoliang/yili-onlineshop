package com.fushionbaby.wap.controller.pay;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.core.model.SoSalesOrderUser;
import com.fushionbaby.core.service.SoSalesOrderWebUserService;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;

/**
 * @author 张明亮
 */
@Controller
@RequestMapping("/pay")
public class PayController {
	
	/**
	 * 订单操作service
	 */
	@Autowired
	private SoSalesOrderWebUserService<SoSalesOrderUser> soSalesOrderWebService;
	
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	
	@RequestMapping("goto_pay_ok")
	public String gotoPayOk(ModelMap model,HttpServletRequest request){
		String price = request.getParameter("price");
		price = StringUtils.isBlank(price) ? "" : price;
		model.put("price", price);
		return "pay/pay-ok";
	}
	
	@RequestMapping("goto_pay_err")
	public String gotoPayErr(ModelMap model,HttpServletRequest request){
		String pay_err_status = request.getParameter("pay_err_status");
		String order_code = request.getParameter("order_code");
		String errmsg="";
		if("1".equals(pay_err_status)){
			errmsg=",登陆超时,或者未登陆(payErrorCode=3)";
		}else if("2".equals(pay_err_status)){
			errmsg=",订单code不能为空(payErrorCode=4)";
		}else if("3".equals(pay_err_status)){
			errmsg=",登陆超时,或者未登陆(payErrorCode=5)";
		}else if("4".equals(pay_err_status)){
			errmsg=",系统没有找到该订单信息(payErrorCode=6)";
		}else if("5".equals(pay_err_status)){
			errmsg=",支付被取消,订单" + order_code + "已经付过款,不用重复支付(payErrorCode=7)";
		}
		model.put("errmsg", errmsg);
		return "pay/pay-err";
	}
	
	@RequestMapping("goto_pay_page")
	public String gotoPayPage(ModelMap model,HttpServletRequest request){
		String orderCode = request.getParameter("order_code");
		String errmsg="";
		if(StringUtils.isBlank(orderCode)){
			errmsg=",订单code不能为空(payErrorCode=8)";
		}
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		UserDto user = null;
		if (StringUtils.isBlank(sid)) {
			errmsg=",登陆超时,或者未登陆(payErrorCode=1)";
		}else{
			user = (UserDto) SessionCache.get(sid);
		}
		if (user == null) {
			errmsg=",登陆超时,或者未登陆(payErrorCode=2)";
		}else{
			SoSalesOrderUser order = soSalesOrderWebService.getOrderByMemberIdAndOrderCode(user.getMember_id(), orderCode);
			if(order==null){
				errmsg=",系统没有找到该订单信息";
			}else{
				model.put("sid",sid);
				model.put("orderCode",orderCode);
				model.put("price",order.getTotalActual());
			}
		}
		String result = "pay/checkout-pay";
		if(StringUtils.isNotBlank(errmsg)){
			result = "pay/pay-err";
		}
		model.put("payZfbJsdzUrl", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYZFBJSDZURL).getValue());
		model.put("payYlUrl", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYYLURL).getValue());
		return result;
	}
}
