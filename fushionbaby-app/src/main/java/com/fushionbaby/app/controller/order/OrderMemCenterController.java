package com.fushionbaby.app.controller.order;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.finance.dto.OrderApplyRefundReqDto;
import com.aladingshop.finance.model.FinanceRefundApply;
import com.aladingshop.finance.service.FinanceRefundApplyService;
import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.OrderBaseUser;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.model.OrderRemindDelivery;
import com.fushionbaby.core.model.OrderTraceUser;
import com.fushionbaby.core.model.request.OrderUserReq;
import com.fushionbaby.core.model.response.EvaluateOrderUserRes;
import com.fushionbaby.core.model.response.OrderUserRes;
import com.fushionbaby.core.service.OrderBaseUserService;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.core.service.OrderLineUserService;
import com.fushionbaby.core.service.OrderRemindDeliveryService;
import com.fushionbaby.core.service.OrderTraceUserService;
import com.fushionbaby.core.vo.OrderUser;
import com.fushionbaby.core.vo.SkuByOrderLine;
import com.fushionbaby.facade.biz.common.order.OrderMemCenterFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 个人中心订单管理controller
 * 
 * @author sun tao 2015年7月14日
 */
@Controller
@RequestMapping("/order")
public class OrderMemCenterController {
	@Autowired
	private OrderBaseUserService<OrderBaseUser> baseUserService;
	@Autowired
	private OrderTraceUserService<OrderTraceUser> orderTraceUserService;
	@Autowired
	private OrderLineUserService<OrderLineUser> orderLineUserService;
	/**代金券service*/
	@Autowired
	private ActCardService<ActCard> actCardService;
	/**商品库存操作service*/
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeService;
	@Autowired
	private OrderMemCenterFacade orderMemCenterFacade;
	@Autowired
	private OrderRemindDeliveryService<OrderRemindDelivery> orderRemindDeliveryService;
	@Autowired
	private MemberService<Member> memberService;
	/** 订单财务表*/
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> financeUserService;
	/** 退款申请*/
	@Autowired
	private FinanceRefundApplyService finaceRefundApplyService;
	
	private static final Log LOGGER = LogFactory.getLog(OrderMemCenterController.class);
	/***
	 * 订单状态orderStatus
	 * orderStatus传空代表全部订单，传1代表待付款，传3代表待发货，传5待收货
	 * @param data
	 * @return
	 */
	@ResponseBody
	@RequestMapping("orderList")
	public Object listAll(@RequestParam("data") String data) {
		LOGGER.info("查询订单列表接口action--start");
		LOGGER.info("查询订单列表接口action--请求报文：{" + data + "}");
		try {
			// 解析data
			Gson gson = new Gson();
			OrderUserReq request = gson.fromJson(data,
					new TypeToken<OrderUserReq>() {
					}.getType());
			if (CheckIsEmpty.isEmpty(request.getSid())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto user = (UserDto) SessionCache.get(request.getSid());
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.noLoginError("请先登录或重新登录");
			}
			request.setSid(user.getMemberId().toString());
			OrderUserRes orderUserRes = orderMemCenterFacade.getOrderListByParam(request);
			LOGGER.info("查询订单列表接口action--end");
			
			return Jsonp_data.success(orderUserRes);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取订单列表出错"+e);
			return Jsonp.error("获取订单列表出错");
		}
	}
	
	@ResponseBody
	@RequestMapping("evaluateList")
	public Object evaluateList(@RequestParam("data") String data){
		LOGGER.info("待评价列表接口action--start");
		LOGGER.info("待评价列表接口action--请求报文：{" + data + "}");
		try {
			// 解析data
			Gson gson = new Gson();
			OrderUserReq request = gson.fromJson(data,
					new TypeToken<OrderUserReq>() {
					}.getType());
			if (CheckIsEmpty.isEmpty(request.getSid())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto user = (UserDto) SessionCache.get(request.getSid());
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.noLoginError("请先登录或重新登录");
			}
			request.setSid(user.getMemberId().toString());
			EvaluateOrderUserRes evaluateOrderUserRes = orderMemCenterFacade.getEvaluateOrderListByParam(request);
			if(evaluateOrderUserRes == null){
				evaluateOrderUserRes =  new EvaluateOrderUserRes();
			}
			if(evaluateOrderUserRes.getSkuByOrderLines() == null){
				evaluateOrderUserRes.setSkuByOrderLines(new ArrayList<SkuByOrderLine>());
			}
			LOGGER.info("待评价列表接口action--end");
			BeanNullConverUtil.nullConver(evaluateOrderUserRes);
			return Jsonp_data.success(evaluateOrderUserRes);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("待评价列表出错"+e);
			return Jsonp.error("待评价列表出错");
		}
	}
	
	/***
	 * 获取各个订单数量,包括全部数量
	 * 待付款1，待发货3，待收货5，待评价y
	 * @param data --- sid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getEachOrderQuantity")
	public Object getEachOrderQuantity(@RequestParam("data") String data) {
		LOGGER.info("查询订单数量接口action--start");
		LOGGER.info("查询订单数量接口action--请求报文：{" + data + "}");
		try {
			// 解析data
			Gson gson = new Gson();
			OrderUserReq request = gson.fromJson(data,
					new TypeToken<OrderUserReq>() {
					}.getType());
			if (CheckIsEmpty.isEmpty(request.getSid())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto user = (UserDto) SessionCache.get(request.getSid());
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.noLoginError("请先登录或重新登录");
			}
			request.setSid(user.getMemberId().toString());
			OrderUserRes orderUserRes = orderMemCenterFacade.getEachOrderQuantity(request);
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
	public Object cancelOrder(@RequestParam("data") String data) {
		LOGGER.info("取消订单状态接口action--start");
		LOGGER.info("取消订单状态接口action--请求报文：{" + data + "}");

		try {
			// 解析data
			Gson gson = new Gson();
			OrderUserReq request = gson.fromJson(data,
					new TypeToken<OrderUserReq>() {
					}.getType());
			if (CheckIsEmpty.isEmpty(request.getSid(), request.getOrderCode(),request.getOrderStatus())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto user = (UserDto) SessionCache.get(request.getSid());
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.noLoginError("请先登录或重新登录");
			}
			if (!StringUtils.equals(request.getOrderStatus(), OrderConfigServerEnum.WAITING_PAYMENT.getCode())) {
				return Jsonp.error("订单状态应为1");
			}
			String orderCode = request.getOrderCode();
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
	@RequestMapping("confirmReceipt")
	public Object confirmReceipt(@RequestParam("data") String data) {
		LOGGER.info("确认收货状态接口action--start");
		LOGGER.info("确认收货状态接口action--请求报文：{" + data + "}");

		try {
			// 解析data
			Gson gson = new Gson();
			OrderUserReq request = gson.fromJson(data,
					new TypeToken<OrderUserReq>() {
					}.getType());
			if (CheckIsEmpty.isEmpty(request.getSid(), request.getOrderCode(),request.getOrderStatus())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto user = (UserDto) SessionCache.get(request.getSid());
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.noLoginError("请先登录或重新登录");
			}
			String orderCode = request.getOrderCode();
			Long memberId = user.getMemberId();
			OrderBaseUser orderBaseUser = new OrderBaseUser();
			orderBaseUser.setOrderCode(orderCode);
			orderBaseUser.setMemberId(memberId);
			orderBaseUser.setLastReceiveTime(new Date());
			OrderTraceUser orderTraceUser = new OrderTraceUser();
			orderTraceUser.setOrderCode(orderCode);
			orderTraceUser.setMemberId(memberId);
			if (StringUtils.equals(request.getOrderStatus(), OrderConfigServerEnum.SUCCESS_SHIPPED.getCode())) {
				orderBaseUser.setOrderStatus(OrderConfigServerEnum.WEB_CONFIRM_RECEIPT.getCode());				
				orderTraceUser.setOrderStatus(OrderConfigServerEnum.WEB_CONFIRM_RECEIPT.getCode());
			} else {
				orderBaseUser.setOrderStatus(OrderConfigServerEnum.SUCCESS_TRANSACTION.getCode());				
				orderTraceUser.setOrderStatus(OrderConfigServerEnum.SUCCESS_TRANSACTION.getCode());
			}
			baseUserService.updateOrderStatus(orderBaseUser);
			orderTraceUserService.updatetTraceStatus(orderTraceUser);
			LOGGER.info("确认收货状态接口action--end");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("确认收货出错"+e);
			return Jsonp.error("确认收货出错");
		}
		return Jsonp.success();
	}
	
	@ResponseBody
	@RequestMapping("deleteOrder")
	public Object deleteOrder(@RequestParam("data") String data) {
		LOGGER.info("删除订单接口action--start");
		LOGGER.info("删除订单接口action--请求报文：{" + data + "}");

		try {
			// 解析data
			Gson gson = new Gson();
			OrderUserReq request = gson.fromJson(data,
					new TypeToken<OrderUserReq>() {
					}.getType());
			if (CheckIsEmpty.isEmpty(request.getSid(), request.getOrderCode())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto user = (UserDto) SessionCache.get(request.getSid());
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.noLoginError("请先登录或重新登录");
			}
			String orderCode = request.getOrderCode();
			Long memberId = user.getMemberId();
			OrderBaseUser orderBaseUser = new OrderBaseUser();
			orderBaseUser.setOrderCode(orderCode);
			orderBaseUser.setMemberId(memberId);
			orderBaseUser.setIsDelete(CommonConstant.YES);
			baseUserService.updateOrderStatus(orderBaseUser);
			LOGGER.info("删除订单接口action--end");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("删除订单出错"+e);
			return Jsonp.error("删除订单出错");
		}
		return Jsonp.success();
	}
	
	@ResponseBody 
	@RequestMapping("/remindDelivery")
	public Object remindDelivery(@RequestParam("sid") String sid,@RequestParam("orderCode") String orderCode) {
		LOGGER.info("提醒发货状态接口action--start");
    
		try {
			if (CheckIsEmpty.isEmpty(sid, orderCode)) {
				return Jsonp.paramError(CommonConstant.CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto user = (UserDto) SessionCache.get(sid);
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.noLoginError("请先登录或重新登录");
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
	
	
	/***
	 * 申请退款
	 * @param sid
	 * @param orderCode
	 * @return
	 */
	@ResponseBody 
	@RequestMapping("/applyRefund")
	public Object applyRefund(@RequestParam("data") String data, @RequestParam("mac") String mac) {
		try {
			LOGGER.info("申请退款接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson=new Gson();
			OrderApplyRefundReqDto reqDto=gson.fromJson(data, OrderApplyRefundReqDto.class);
			if (CheckIsEmpty.isEmpty(reqDto.getSid(),reqDto.getOrderCode())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto user = (UserDto) SessionCache.get(reqDto.getSid());
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.noLoginError("请先登录或重新登录");
			}
			
			OrderFinanceUser financeOrder=this.financeUserService.findByMemberIdAndOrderCode(user.getMemberId(), reqDto.getOrderCode());
			if(ObjectUtils.equals(null, financeOrder))
				return Jsonp.error("该订单没有付款记录");
			FinanceRefundApply apply=this.finaceRefundApplyService.findByMemberIdAndOrderCode(user.getMemberId(),reqDto.getOrderCode());
			if(apply!=null){
				return Jsonp_data.success("您已经申请过哦");
			}else{
				FinanceRefundApply refundApply=new FinanceRefundApply();
				refundApply.setCreateTime(new Date());
				refundApply.setMemberId(user.getMemberId());
				refundApply.setOrderCode(reqDto.getOrderCode());
				refundApply.setOrderPayType(financeOrder.getPaymentType()==null?"APP_ZERO":financeOrder.getPaymentType());
				refundApply.setRefundReason(reqDto.getRefundReason());
				refundApply.setOrderSource(reqDto.getSourceCode());
				refundApply.setStatus("1");
				this.finaceRefundApplyService.add(refundApply);
				return Jsonp.success();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("申请退款接口action异常"+e);
			return Jsonp.error("申请退款异常");
		}
	}
	
	/***
	 * 订单详情
	 * @param sid
	 * @param orderCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("orderInfo")
	public Object getOrderInfo(@RequestParam("data") String data) {
		LOGGER.info("订单详情接口action--start");
		LOGGER.info("订单详情接口action--请求报文：{" + data + "}");
		try {
			// 解析data
			Gson gson = new Gson();
			OrderUserReq request = gson.fromJson(data,
					new TypeToken<OrderUserReq>() {
					}.getType());
			if (CheckIsEmpty.isEmpty(request.getSid(),request.getOrderCode())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto user = (UserDto) SessionCache.get(request.getSid());
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.noLoginError("请先登录或重新登录");
			}
			OrderUser orderUser = orderMemCenterFacade.getOrderInfo(user.getMemberId(), request.getOrderCode());
			LOGGER.info("订单详情接口action--end");
			
			return Jsonp_data.success(orderUser);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("订单详情出错"+e);
			return Jsonp.error("订单详情出错");
		}
	}
	
}
