package com.aladingshop.wap.controller.membercenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.service.GlobalConfig;

import util.ImageConstantFacade;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.OrderConfigClientEnum;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.OrderBaseUser;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.model.OrderMemberAddressUser;
import com.fushionbaby.core.model.OrderRemindDelivery;
import com.fushionbaby.core.model.OrderTraceUser;
import com.fushionbaby.core.model.OrderTransUser;
import com.fushionbaby.core.model.request.OrderUserReq;
import com.fushionbaby.core.model.response.EvaluateOrderUserRes;
import com.fushionbaby.core.model.response.OrderUserRes;
import com.fushionbaby.core.service.OrderBaseUserService;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.core.service.OrderLineUserService;
import com.fushionbaby.core.service.OrderMemberAddressUserService;
import com.fushionbaby.core.service.OrderRemindDeliveryService;
import com.fushionbaby.core.service.OrderTraceUserService;
import com.fushionbaby.core.service.OrderTransUserService;
import com.fushionbaby.core.vo.OrderUser;
import com.fushionbaby.core.vo.SkuByOrderLine;
import com.fushionbaby.facade.biz.common.order.OrderMemCenterFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.SkuImage;
import com.fushionbaby.sku.service.SkuImageService;
import com.google.common.base.Objects;

/**
 * 个人中心订单管理controller
 * 
 * @author sun tao 2015年7月14日
 */
@Controller
@RequestMapping("/order")
public class OrderMemCenterController {
	@Autowired
	private OrderBaseUserService<OrderBaseUser> orderBaseUserService;
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;
	@Autowired
	private OrderTransUserService<OrderTransUser> orderTransUserService;
	@Autowired
	private OrderTraceUserService<OrderTraceUser> orderTraceUserService;
	@Autowired
	private OrderMemberAddressUserService<OrderMemberAddressUser> orderMemberAddressUserService;
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaConfigService;
	@Autowired
	private GlobalConfig globalConfig;
	@Autowired
	private SkuImageService<SkuImage> skuImageService;
	@Autowired
	private OrderLineUserService<OrderLineUser> orderLineUserService;
	@Autowired
	private OrderMemCenterFacade orderMemCenterFacade;
	@Autowired
	private OrderRemindDeliveryService<OrderRemindDelivery> orderRemindDeliveryService;
	@Autowired
	private MemberService<Member> memberService;
	
	private static final Log LOGGER = LogFactory
			.getLog(OrderMemCenterController.class);
	/***
	 * 订单状态orderStatus
	 * orderStatus传空代表全部订单，传1代表待付款，传3代表待发货，传5待收货
	 * @param data
	 * @return
	 */
	@RequestMapping("orderList")
	public String listAll(HttpServletRequest request,Model model,
			@RequestParam(value="orderStatus" ,defaultValue = "") String orderStatus) {
		LOGGER.info("查询订单列表接口action--start");
		try {
			String sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if(user == null || user.getMemberId() == null){
				return "redirect:/login/index" ;
			}
			long memberId=user.getMemberId();
			
			OrderUserRes res = new OrderUserRes();
			List<OrderUser> orderUserList = new ArrayList<OrderUser>();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberId);
			paramMap.put("orderStatus",
					Objects.equal(orderStatus, "0") ? null
							: orderStatus);
			List<OrderBaseUser> currentPageOrders = orderBaseUserService
					.getListPage(paramMap);
			// 遍历集合获取其它数据
			for (OrderBaseUser ub : currentPageOrders) {
				// 根据当前订单base信息获取财务、费用相关信息
				if (StringUtils.isNotBlank(ub.getOrderCode().toString())) {
					paramMap.put("orderCode", ub.getOrderCode());
				}
				OrderFinanceUser orderFinanceUser = orderFinanceUserService
						.findByParam(paramMap);
				OrderFeeUser orderFeeUser = orderFeeUserService
						.findByParam(paramMap);
				OrderTransUser transUser = orderTransUserService
						.findByParam(paramMap);
				/** 出现null数据订单数据不对应 */
				if (CheckObjectIsNull.isNull(orderFinanceUser, orderFeeUser,transUser)) {
					continue;
				}
				OrderMemberAddressUser addressUser = orderMemberAddressUserService
						.getOrderAddress(ub.getOrderCode());

				OrderUser orderUser = getOrderUserInfo(ub, orderFinanceUser,
						orderFeeUser, transUser, addressUser);
				Long orderLineTotalQuantity=Long.parseLong("0");
				for(SkuByOrderLine skuByOrderLine:orderUser.getSkuByOrderLines()){
					orderLineTotalQuantity+=skuByOrderLine.getQuantity();
				}
				orderUser.setOrderLineTotalQuantity(orderLineTotalQuantity);
				BeanNullConverUtil.nullConver(orderUser);
				orderUserList.add(orderUser);
			}
			res.setOrderList(orderUserList);
			model.addAttribute("orderUserRes", res);
			model.addAttribute("sid", sid);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取订单列表出错"+e);
		}
		return "membercenter/order";
	}
	
	@RequestMapping("orderDetail")
	public String orderDetail(HttpServletRequest request,Model model,
			@RequestParam(value="orderCode" ,defaultValue = "") String orderCode) {
		LOGGER.info("订单详情接口action--start");
		try {
			String sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if(user == null || user.getMemberId() == null){
				return "redirect:/login/index" ;
			}
			long memberId=user.getMemberId();
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberId", memberId);
			paramMap.put("orderCode", orderCode);
			OrderBaseUser orderBaseUser = orderBaseUserService.findObjectByMemIdAndOrdCode(memberId, orderCode);
			// 遍历集合获取其它数据
			// 根据当前订单base信息获取财务、费用相关信息
			OrderFinanceUser orderFinanceUser = orderFinanceUserService
					.findByParam(paramMap);
			OrderFeeUser orderFeeUser = orderFeeUserService
					.findByParam(paramMap);
			OrderTransUser transUser = orderTransUserService
					.findByParam(paramMap);
			
			OrderMemberAddressUser addressUser = orderMemberAddressUserService
					.getOrderAddress(orderCode);

			OrderUser orderUser = getOrderUserInfo(orderBaseUser, orderFinanceUser,
					orderFeeUser, transUser, addressUser);
			Long orderLineTotalQuantity=Long.parseLong("0");
			for(SkuByOrderLine skuByOrderLine:orderUser.getSkuByOrderLines()){
				orderLineTotalQuantity+=skuByOrderLine.getQuantity();
			}
			orderUser.setOrderLineTotalQuantity(orderLineTotalQuantity);
			BeanNullConverUtil.nullConver(orderUser);
			model.addAttribute("orderUser", orderUser);
			model.addAttribute("sid", sid);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取订单详情出错"+e);
		}
		return "membercenter/order-detail";
	}
	
	
	@RequestMapping("evaluateList")
	public Object evaluateList(HttpServletRequest request,Model model,
			@RequestParam(value="evaluateStatus",defaultValue = "") String evaluateStatus){
		LOGGER.info("待评价列表接口action--start");
		try {
			String sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if(user == null || user.getMemberId() == null){
				return "redirect:/login/index" ;
			}
			OrderUserReq orderUserReq = new OrderUserReq();
			orderUserReq.setSid(user.getMemberId().toString());
			orderUserReq.setEvaluateStatus(evaluateStatus);
			EvaluateOrderUserRes evaluateOrderUserRes = orderMemCenterFacade.getEvaluateOrderListByParam(orderUserReq);
			if(evaluateOrderUserRes == null){
				evaluateOrderUserRes =  new EvaluateOrderUserRes();
			}
			if(evaluateOrderUserRes.getSkuByOrderLines() == null){
				evaluateOrderUserRes.setSkuByOrderLines(new ArrayList<SkuByOrderLine>());
			}
			LOGGER.info("待评价列表接口action--end");
			BeanNullConverUtil.nullConver(evaluateOrderUserRes);
			model.addAttribute("evaluateOrderUserRes",evaluateOrderUserRes);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("待评价列表出错"+e);
			return Jsonp.error("待评价列表出错");
		}
		return "membercenter/evaluateOrder";
	}
	
	/**
	 * 获取需要的订单信息,其它额外信息往此处添加
	 * @param orderBaseUser
	 * @param orderFinanceUser
	 * @param orderFeeUser
	 * @param transUser
	 * @param addressUser
	 * @return
	 */
	private OrderUser getOrderUserInfo(OrderBaseUser orderBaseUser,
			OrderFinanceUser orderFinanceUser, OrderFeeUser orderFeeUser,
			OrderTransUser transUser, OrderMemberAddressUser addressUser) {
		OrderUser orderUser = new OrderUser();
		orderUser.setOrderStatus(orderBaseUser.getOrderStatus());
		orderUser.setOrderInfo(OrderConfigClientEnum.parseCode(orderBaseUser
				.getOrderStatus()));
		orderUser.setOrderCode(orderBaseUser.getOrderCode());
		orderUser.setMemo(orderBaseUser.getMemo() == null ? StringUtils.EMPTY
				: orderBaseUser.getMemo());
		orderUser.setSourceCode(orderBaseUser.getSourceCode());
		/**审核不通过原因*/
		orderUser.setFailReason(orderBaseUser.getAuditFailReason());
		String webPaymentType = "";
		String appPaymentType = "";
		if(StringUtils.isEmpty(orderFinanceUser.getPaymentType())){
			webPaymentType = PaymentTypeEnum.PAYMENT_ZFB_WEB_JSDZ.getCode();
			appPaymentType = PaymentTypeEnum.PAYMENT_ZFB_APP.getCode();
		}else{
			String paymentType = orderFinanceUser.getPaymentType();
			if (paymentType.contains("ZFB")) {
				webPaymentType = PaymentTypeEnum.PAYMENT_ZFB_WEB_JSDZ.getCode();
				appPaymentType = PaymentTypeEnum.PAYMENT_ZFB_APP.getCode();
			} else if(paymentType.contains("WX")){
				webPaymentType = PaymentTypeEnum.PAYMENT_WX_WEB.getCode();
				appPaymentType = PaymentTypeEnum.PAYMENT_WX_APP.getCode();
			} else if(paymentType.contains("ZXYL")){
				webPaymentType = PaymentTypeEnum.PAYMENT_ZXYL_WEB.getCode();
				appPaymentType = PaymentTypeEnum.PAYMENT_ZXYL_APP.getCode();
			}
		}
		orderUser.setAppPaymentType(appPaymentType);
		orderUser.setWebPaymentType(webPaymentType);
		orderUser.setActualTransferFee(NumberFormatUtil
				.numberFormat(orderFeeUser.getActualTransferFee()));
		orderUser.setCardAmount(NumberFormatUtil.numberFormat(orderFeeUser
				.getCardAmount()));
		orderUser.setEpointsMoney(NumberFormatUtil
				.numberFormat(orderFeeUser.getEpointsRedeemMoney()));
		orderUser.setPaymentTotalActual(NumberFormatUtil
				.numberFormat(orderFeeUser.getTotalActual()));
		orderUser
				.setCreateOrderTime(orderBaseUser.getCreateTime() == null ? StringUtils.EMPTY
						: DateFormat.dateToString(orderBaseUser.getCreateTime()));
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(DateFormat.stringToDate(orderUser.getCreateOrderTime()));// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, +1); // 设置为后一天
		orderUser.setOrderEndTime(DateFormat.dateToString(calendar.getTime()));
		orderUser.setPaymentCompleteTime(orderFinanceUser
				.getPaymentCompleteTime() == null ? StringUtils.EMPTY
				: DateFormat.dateToString(orderFinanceUser
						.getPaymentCompleteTime()));
		orderUser.setTransStatus(transUser.getTransStatus());
		orderUser
				.setDeliveryTime(transUser.getDeliveryTime() == null ? StringUtils.EMPTY
						: DateFormat.dateToString(transUser
								.getDeliveryTime()));
		orderUser.setLastReceiveTime(orderBaseUser.getLastReceiveTime()==null ? StringUtils.EMPTY
						: DateFormat.dateToString(orderBaseUser.getLastReceiveTime()));
		orderUser.setReceiver(addressUser==null?StringUtils.EMPTY:addressUser.getReceiver());
		orderUser.setReceiverMobile(addressUser==null?StringUtils.EMPTY:addressUser.getReceiverMobile());
		String province = memberAreaConfigService
				.getNameByAreaCode(addressUser==null?StringUtils.EMPTY:addressUser.getProvince());
		orderUser.setProvince(province==null?StringUtils.EMPTY:province);
		String city = memberAreaConfigService.getNameByAreaCode(addressUser==null?StringUtils.EMPTY:addressUser.getCity());
		orderUser.setCity(city==null?StringUtils.EMPTY:city);
		String district = memberAreaConfigService.getNameByAreaCode(addressUser==null?StringUtils.EMPTY:addressUser.getDistrict());
		orderUser.setDistrict(district==null?StringUtils.EMPTY:district);
		orderUser.setAddress(addressUser==null?StringUtils.EMPTY:addressUser.getAddress());
		orderUser.setCustomerServicePhone(globalConfig
				.findByCode(GlobalConfigConstant.CUSTOMER_SERVICE_PHONE));
		// 获取商品集合
		orderUser.setSkuByOrderLines(getSkusByCurrentOrder(orderBaseUser
				.getOrderCode()));
		return orderUser;
	}
	
	private List<SkuByOrderLine> getSkusByCurrentOrder(String orderCode) {
		List<SkuByOrderLine> results = new ArrayList<SkuByOrderLine>();
		// 根据当前订单信息获取商品行集合
		List<OrderLineUser> allLine = orderLineUserService.findByOrderCode(
				orderCode, null);
		if(orderCode.contains("ydb")){
			SkuByOrderLine skuByOrderLineCard = new SkuByOrderLine();
			String configId=orderCode.substring(0, orderCode.indexOf("ydb"));
			skuByOrderLineCard.setCreateOrderTime(allLine.get(0).getCreateTime() == null ? StringUtils.EMPTY
					: DateFormat.dateToString(allLine.get(0).getCreateTime()));
			skuByOrderLineCard.setSkuImg(ImageConstantFacade.YIDUOBAO_PICTURE_SERVER_PATH+configId+".jpg");
			skuByOrderLineCard.setSkuName("阿拉丁玛特益多宝");
			skuByOrderLineCard.setSkuPrice(allLine.get(0).getTotalActual().toString());
			skuByOrderLineCard.setLineTotalPrice(allLine.get(0).getTotalActual().toString());
			skuByOrderLineCard.setOrderLineId(allLine.get(0).getId());
			skuByOrderLineCard.setOrderCode(allLine.get(0).getOrderCode());
			skuByOrderLineCard.setSkuCode(allLine.get(0).getSkuCode());
			skuByOrderLineCard.setQuantity(allLine.get(0).getQuantity());
			BeanNullConverUtil.nullConver(skuByOrderLineCard);
			results.add(skuByOrderLineCard);
			return results;
		}
		// 封装信息
		for (OrderLineUser oline : allLine) {
			SkuByOrderLine skuByOrderLine = new SkuByOrderLine();
			// 商品图片还需关联其他表
			List<SkuImage> images = skuImageService.findBySkuCode(oline
					.getSkuCode());
			if (images != null && images.size() != 0)
				skuByOrderLine
						.setSkuImg(ImageConstantFacade.IMAGE_SERVER_ROOT_PATH
								+ images.get(0).getImgUrl());
			skuByOrderLine.setSkuName(oline.getSkuName());
			skuByOrderLine.setSkuPrice(NumberFormatUtil.numberFormat(oline
					.getUnitPrice()));
			skuByOrderLine.setSkuColor(oline.getColor());
			skuByOrderLine.setSkuSize(oline.getSize());
			skuByOrderLine.setIsComment(oline.getIsComment());
			skuByOrderLine.setQuantity(oline.getQuantity());
			skuByOrderLine.setOrderLineId(oline.getId());
			skuByOrderLine.setOrderCode(oline.getOrderCode());
			skuByOrderLine.setSkuCode(oline.getSkuCode());
			skuByOrderLine
					.setCreateOrderTime(oline.getCreateTime() == null ? StringUtils.EMPTY
							: DateFormat.dateToString(oline.getCreateTime()));
			skuByOrderLine.setLineTotalPrice(NumberFormatUtil
					.numberFormat(oline.getTotalPrice()));
			BeanNullConverUtil.nullConver(skuByOrderLine);
			results.add(skuByOrderLine);
		}
		return results;
	}
	
	/***
	 * 获取各个订单数量,包括全部数量
	 * 待付款1，待发货3，待收货5，待评价y
	 * @param data --- sid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getEachOrderQuantity")
	public Object getEachOrderQuantity(HttpServletRequest request) {
		LOGGER.info("查询订单数量接口action--start");
		try {
			String sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if(user == null || user.getMemberId() == null){
				return "redirect:/login/index" ;
			}
			OrderUserReq req=new OrderUserReq();
			req.setSid(user.getMemberId().toString());
			OrderUserRes orderUserRes = orderMemCenterFacade.getEachOrderQuantity(req);
			LOGGER.info("查询订单数量接口action--end");
			return Jsonp_data.success(orderUserRes);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取订单数量出错"+e);
			return Jsonp.error("获取订单数量出错");
		}
	}	
	
	@ResponseBody
	@RequestMapping("cancelOrder")
	public Object cancelOrder(HttpServletRequest request, @RequestParam("orderCode") String orderCode) {
		LOGGER.info("取消订单状态接口action--start");
		try {
			String sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if(user == null || user.getMemberId() == null){
				return "redirect:/login/index" ;
			}
			Long memberId = user.getMemberId();
			orderMemCenterFacade.cancelOrder(memberId, orderCode);
			LOGGER.info("取消订单状态接口action--end");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("取消订单出错"+e);
			return Jsonp.error("取消订单出错");
		}
		return Jsonp.success();
	}
	
	
	@ResponseBody 
	@RequestMapping("remindDelivery")
	public Object remindDelivery(HttpServletRequest request,@RequestParam("orderCode") String orderCode) {
		LOGGER.info("提醒发货状态接口action--start");
		try {
			String sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if(user == null || user.getMemberId() == null){
				return "redirect:/login/index" ;
			}
			Long memberId=user.getMemberId();
			
			OrderRemindDelivery orderRemindDelivery=orderRemindDeliveryService.findByMemberIdAndOrderCode(memberId, orderCode);
			if(ObjectUtils.notEqual(null, orderRemindDelivery)){
				orderRemindDelivery.setCount(orderRemindDelivery.getCount()+1);
				orderRemindDeliveryService.updateByMemberIdAndOrderCode(orderRemindDelivery);
			}else{
				OrderRemindDelivery remindDelivery=new OrderRemindDelivery();
				remindDelivery.setMemberId(memberId);
				remindDelivery.setOrderCode(orderCode);
				remindDelivery.setCount(1);
				remindDelivery.setMemberName(user.getLoginName());
				orderRemindDeliveryService.add(remindDelivery);
			}
			LOGGER.info("提醒发货状态接口action--end");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("提醒发货状态出错"+e);
			return Jsonp.error("提醒发货状态出错");
		}
		return Jsonp.success();
	}
	
	@ResponseBody
	@RequestMapping("confirmReceipt")
	public Object confirmReceipt(HttpServletRequest request,@RequestParam("orderCode") String orderCode) {
		LOGGER.info("确认收货状态接口action--start");
		try {
			String sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if(user == null || user.getMemberId() == null){
				return "redirect:/login/index" ;
			}
			Long memberId=user.getMemberId();
			
			OrderBaseUser orderBaseUser = new OrderBaseUser();
			orderBaseUser.setOrderCode(orderCode);
			orderBaseUser.setMemberId(memberId);
			orderBaseUser.setOrderStatus(OrderConfigServerEnum.WEB_CONFIRM_RECEIPT.getCode());
			orderBaseUser.setLastReceiveTime(new Date());
			orderBaseUserService.updateOrderStatus(orderBaseUser);
			OrderTraceUser orderTraceUser = new OrderTraceUser();
			orderTraceUser.setOrderCode(orderCode);
			orderTraceUser.setMemberId(memberId);
			orderTraceUser.setOrderStatus(OrderConfigServerEnum.WEB_CONFIRM_RECEIPT.getCode());
			orderTraceUserService.updatetTraceStatus(orderTraceUser);
			LOGGER.info("确认收货状态接口action--end");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("确认收货出错"+e);
			return Jsonp.error("确认收货出错");
		}
		return Jsonp.success();
	}
	
	
//	@ResponseBody
//	@RequestMapping("deleteOrder")
//	public Object deleteOrder(@RequestParam("data") String data) {
//		LOGGER.info("删除订单接口action--start");
//		LOGGER.info("删除订单接口action--请求报文：{" + data + "}");
//
//		try {
//			// 解析data
//			Gson gson = new Gson();
//			OrderUserReq request = gson.fromJson(data,
//					new TypeToken<OrderUserReq>() {
//					}.getType());
//			if (CheckIsEmpty.isEmpty(request.getSid(), request.getOrderCode())) {
//				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
//			}
//			UserDto user = (UserDto) SessionCache.get(request.getSid());
//			if (CheckObjectIsNull.isNull(user)) {
//				return Jsonp.noLoginError("请先登录或重新登录");
//			}
//			String orderCode = request.getOrderCode();
//			Long memberId = user.getMemberId();
//			OrderBaseUser orderBaseUser = new OrderBaseUser();
//			orderBaseUser.setOrderCode(orderCode);
//			orderBaseUser.setMemberId(memberId);
//			orderBaseUser.setIsDelete(CommonConstant.YES);
//			orderBaseUserService.updateOrderStatus(orderBaseUser);
//			LOGGER.info("删除订单接口action--end");
//		} catch (Exception e) {
//			e.printStackTrace();
//			LOGGER.error("删除订单出错"+e);
//			return Jsonp.error("删除订单出错");
//		}
//		return Jsonp.success();
//	}
	
	
}
