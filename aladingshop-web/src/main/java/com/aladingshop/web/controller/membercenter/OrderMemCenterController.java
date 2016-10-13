package com.aladingshop.web.controller.membercenter;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.web.enums.OrderEnum;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.core.model.request.OrderUserReq;
import com.fushionbaby.core.model.response.EvaluateOrderUserRes;
import com.fushionbaby.core.model.response.OrderUserRes;
import com.fushionbaby.core.vo.OrderUser;
import com.fushionbaby.core.vo.SkuByOrderLine;
import com.fushionbaby.facade.biz.common.order.OrderMemCenterFacade;

@Controller
@RequestMapping("/order")
public class OrderMemCenterController {
	private static final Log LOGGER = LogFactory.getLog(OrderMemCenterController.class);
	@Autowired
	private OrderMemCenterFacade orderMemCenterFacade;

	/**
	 * 全部订单，待付款，待发货，待收货
	 * 
	 * @param request
	 * @param curPage
	 * @param orderStatus
	 * @param model
	 * @return
	 */
	@RequestMapping("orderList")
	public String orderList(HttpServletRequest request,
			@RequestParam(value = "curPage", defaultValue = WebConstant.DEFAULT_PAGE_INDEX + "") Integer curPage,
			@RequestParam(value = "orderStatus", defaultValue = "0") String orderStatus, String t, ModelMap model) {
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if (CheckObjectIsNull.isNull(user)) {
				return "error";
			}
			OrderUserReq orderUserReq = new OrderUserReq();
			orderUserReq.setOrderStatus(orderStatus);
			orderUserReq.setOrderCode("");
			orderUserReq.setSid(user.getMemberId().toString());
			orderUserReq.setPageIndex(curPage);
			orderUserReq.setPageSize(3);
			OrderUserRes orderUserRes = orderMemCenterFacade.getOrderListByParam(orderUserReq);
			model.addAttribute("orderUserRes", orderUserRes);
			Integer pageSize = (orderUserRes.getTotal() - 1) / 3 + 1;
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("curPage", curPage);
			model.addAttribute("sid", sid);
			model.addAttribute("orderStatus", orderStatus);
			model.addAttribute("time", new Date().getTime());// 防止缓存
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("个人中心订单列表出错" + e);
		}
		LOGGER.info("查询订单列表接口action--end");

		return "membercenter/orderAll";
	}

	/**
	 * 待评价
	 * 
	 * @param request
	 * @param curPage
	 * @param model
	 * @return
	 */
	@RequestMapping("evaluateList")
	public String evaluateList(HttpServletRequest request,
			@RequestParam(value = "curPage", defaultValue = WebConstant.DEFAULT_PAGE_INDEX + "") Integer curPage,
			String t, ModelMap model) {
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if (CheckObjectIsNull.isNull(user)) {
				return "error";
			}
			OrderUserReq orderUserReq = new OrderUserReq();
			orderUserReq.setOrderStatus("");
			orderUserReq.setOrderCode("");
			orderUserReq.setSid(user.getMemberId().toString());
			orderUserReq.setPageIndex(curPage);
			orderUserReq.setPageSize(3);
			orderUserReq.setEvaluateStatus(CommonConstant.YES);
			EvaluateOrderUserRes evaluateOrderUserRes = orderMemCenterFacade.getEvaluateOrderListByParam(orderUserReq);
			if (evaluateOrderUserRes == null) {
				evaluateOrderUserRes = new EvaluateOrderUserRes();
			}
			if (evaluateOrderUserRes.getSkuByOrderLines() == null) {
				evaluateOrderUserRes.setSkuByOrderLines(new ArrayList<SkuByOrderLine>());
			}
			BeanNullConverUtil.nullConver(evaluateOrderUserRes);
			model.addAttribute("evaluateOrderUserRes", evaluateOrderUserRes);
			Integer pageSize = (evaluateOrderUserRes.getTotal() - 1) / 3 + 1;
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("curPage", curPage);
			model.addAttribute("orderStatus", "6");
			model.addAttribute("time", new Date().getTime());// 防止缓存
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("待评价列表出错" + e);
		}
		return "membercenter/orderToAssess";
	}

	/**
	 * 订单详情
	 * 
	 * @param request
	 * @param orderCode
	 * @param model
	 * @return
	 */
	@RequestMapping("orderInfo")
	public String orderInfo(HttpServletRequest request, @RequestParam("orderCode") String orderCode, String t,
			ModelMap model) {
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if (CheckObjectIsNull.isNull(user)) {
				return "error";
			}
			OrderUser orderUser = orderMemCenterFacade.getOrderInfo(user.getMemberId(), orderCode);
			model.addAttribute("sid", sid);
			model.addAttribute("orderUser", orderUser);
			model.addAttribute("status", OrderEnum.parseCode(orderUser.getOrderInfo()));
			model.addAttribute("time", new Date().getTime());// 防止缓存
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("订单详情出错" + e);
		}
		return "membercenter/orderDetail";
	}

	@ResponseBody
	@RequestMapping("cancelOrder")
	public Object cancelOrder(HttpServletRequest request, @RequestParam("orderCode") String orderCode) {
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.error("未登录");
			}
			orderMemCenterFacade.cancelOrder(user.getMemberId(), orderCode);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("取消订单出错" + e);
			Jsonp.error("取消订单出错");
		}
		return Jsonp.success();
	}
}
