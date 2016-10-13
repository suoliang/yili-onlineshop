package com.fushionbaby.cms.controller.order;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.aladingshop.refund.service.RefundService;
import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.auth.constant.CMSUserOrderConstant;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.auth.model.AuthUserOrderRelation;
import com.fushionbaby.auth.service.AuthUserOrderRelationService;
import com.fushionbaby.auth.service.AuthUserService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.controller.order.excel.OrderStatisticsExcelReport;
import com.fushionbaby.cms.dto.OrderLineDto;
import com.fushionbaby.cms.dto.OrderLineLineDto;
import com.fushionbaby.cms.dto.PaymentDetailDto;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.constants.store.StoreConstant;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.service.SysmgrExpressConfigService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.order.dto.OrderBaseDto;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.model.OrderFee;
import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.model.OrderLine;
import com.fushionbaby.order.model.OrderMemberAddress;
import com.fushionbaby.order.model.OrderRemindDelivery;
import com.fushionbaby.order.model.OrderTrace;
import com.fushionbaby.order.model.OrderTrans;
import com.fushionbaby.order.service.OrderBaseService;
import com.fushionbaby.order.service.OrderCardRelationService;
import com.fushionbaby.order.service.OrderFeeService;
import com.fushionbaby.order.service.OrderFinanceService;
import com.fushionbaby.order.service.OrderLineService;
import com.fushionbaby.order.service.OrderMemberAddressService;
import com.fushionbaby.order.service.OrderRemindDeliveryService;
import com.fushionbaby.order.service.OrderTraceService;
import com.fushionbaby.order.service.OrderTransService;
import com.fushionbaby.payment.model.PaymentAppAlabao;
import com.fushionbaby.payment.model.PaymentAppUnion;
import com.fushionbaby.payment.model.PaymentAppWx;
import com.fushionbaby.payment.model.PaymentAppZfb;
import com.fushionbaby.payment.model.PaymentWapZfbJsdz;
import com.fushionbaby.payment.model.PaymentWebUnion;
import com.fushionbaby.payment.model.PaymentWebWx;
import com.fushionbaby.payment.model.PaymentWebZfbJsdz;
import com.fushionbaby.payment.service.PaymentAppAlabaoService;
import com.fushionbaby.payment.service.PaymentAppUnionService;
import com.fushionbaby.payment.service.PaymentAppWxService;
import com.fushionbaby.payment.service.PaymentAppZfbService;
import com.fushionbaby.payment.service.PaymentWapZfbJsdzService;
import com.fushionbaby.payment.service.PaymentWebUnionService;
import com.fushionbaby.payment.service.PaymentWebWxService;
import com.fushionbaby.payment.service.PaymentWebZfbDbjyService;
import com.fushionbaby.payment.service.PaymentWebZfbJsdzService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/order")
public class OrderBaseController extends BaseController {

	private static final Log LOGGER = LogFactory.getLog(OrderBaseController.class);
	@Autowired
	private OrderBaseService<OrderBase> orderBaseService;

	@Autowired
	private OrderTransService<OrderTrans> orderTransService;

	@Autowired
	private OrderLineService<OrderLine> orderLineService;

	@Autowired
	private OrderFinanceService<OrderFinance> orderFinanceService;

	@Autowired
	private OrderFeeService<OrderFee> orderFeeService;

	@Autowired
	private OrderMemberAddressService<OrderMemberAddress> orderMemberAddressService;

	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaConfigService;
	
	@Autowired
	private SkuInfoService skuInfoService;
	
	@Autowired
	private SmsService<Sms> smsService;
	
	@Autowired
	private MemberService<Member> memberService;
	
	@Autowired
	private AuthUserService<AuthUser> authUserService;
	
	@Autowired
	private PaymentWebZfbJsdzService<PaymentWebZfbJsdz> paymentWebZfbJsdzService;
	
	@Autowired
	private PaymentWapZfbJsdzService<PaymentWapZfbJsdz> paymentWapZfbJsdzService;
	
	@Autowired
	private PaymentAppZfbService<PaymentAppZfb> paymentAppZfbService;
	
	@Autowired
	private PaymentWebUnionService<PaymentWebUnion> paymentWebUnionService;
	
	@Autowired
	private PaymentWebZfbDbjyService paymentWebZfbDbjyService;
	
	@Autowired
	private PaymentAppUnionService<PaymentAppUnion> paymentAppUnionService;
	
	@Autowired
	private PaymentWebWxService<PaymentWebWx> paymentWebWxService;
	
	@Autowired
	private PaymentAppWxService<PaymentAppWx> paymentAppWxService;

	@Autowired
	private PaymentAppAlabaoService<PaymentAppAlabao> paymentAppAlabaoService;
	/**红包使用记录*/
	@Autowired
	private ActivityRedEnvlopeUseRecordService<ActivityRedEnvlopeUseRecord> activityRedEnvlopeUseRecordService;
	
	@Autowired
	private OrderCardRelationService orderCardRelationService;
	
	@Autowired
	private OrderRemindDeliveryService<OrderRemindDelivery> orderRemindDeliveryService;
	@Autowired
	private OrderTraceService<OrderTrace> orderTraceService;
	@Autowired
	private RefundService refundSerivce;
	
	/** 快递公司服务 */
    @Autowired
    private SysmgrExpressConfigService sysmgrExpressConfigService;
    
    /** 用户和订单关联*/
    @Autowired
    private AuthUserOrderRelationService<AuthUserOrderRelation> authUserOrderRelationService;
    
    
    /**门店*/
    @Autowired
    private StoreService<Store> storeService;
	/***
	 * 设置查询参数
	 * 
	 * @param orderBaseDto
	 * @return
	 */
	private Map<String, String> setParamByOrderDto(OrderBaseDto orderBaseDto) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderCode", orderBaseDto.getOrderCode());
		params.put("memberName", orderBaseDto.getMemberName());
		params.put("orderStatus", orderBaseDto.getOrderStatus());
		params.put("createTimeFrom", orderBaseDto.getCreateTimeFrom());
		params.put("createTimeTo", orderBaseDto.getCreateTimeTo());
		params.put("orderType", orderBaseDto.getOrderType());
		if(orderBaseDto.getStoreCode() == null || orderBaseDto.getStoreCode() == ""){
			orderBaseDto.setStoreCode(StoreConstant.STORE_CODE);
		}
		params.put("storeCode", orderBaseDto.getStoreCode());
		return params;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryOrderBaseList")
	public String findOrderByOrderInfo(OrderBaseDto orderBaseDto,BasePagination page, ModelMap model,HttpSession session) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			
			Map<String, String> params = setParamByOrderDto(orderBaseDto);
			page.setParams(params);
			AuthUser user=CMSSessionUtils.getSessionUser(session);
			if(CMSUserOrderConstant.LEVEL_TWO.equals(user.getLevel())){
				List<String> orderCodeList=authUserOrderRelationService.findOrderCodeListByUserId(user.getId(),user.getLoginName());
				page=orderBaseService.findUserOrderBaseListByPage(page,orderCodeList);
			}
			if(CMSUserOrderConstant.LEVEL_ONE.equals(user.getLevel())){
				page = orderBaseService.findOrderBaseListByPage(page);
			}
			List<OrderBase> orderBaseList=(List<OrderBase>) page.getResult();
		     
	         /**后台登陆用户会员等级标志  1 所有订单可处理  2 部分订单*/
			model.addAttribute("level", user.getLevel());
			model.addAttribute("page", page);
			model.addAttribute("orderBaseDto", orderBaseDto);
			// 获取到订单状态Map
			model.addAttribute("orderStateMap",OrderConfigServerEnum.getOrderConfigServerMap());
			// 获取到支付状态Map
			model.addAttribute("paymentStateMap",PaymentTypeEnum.getPaymentTypeMap());
			model.addAttribute("orderBaseList",orderBaseList);
			
			model.addAttribute("storeMap", this.getStoreMap());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("订单列表查询失败" + e.getMessage(), e);
		}
		return "models/order/orderBaseList";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryOrderBaseList/{orderStatus}")
	public String findOrderByOrderStatus(OrderBaseDto orderBaseDto,@PathVariable String orderStatus,
			BasePagination page, ModelMap model,HttpSession session) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			orderBaseDto.setOrderStatus(orderStatus);
            Map<String, String> params = setParamByOrderDto(orderBaseDto);
            page.setParams(params);
        	AuthUser user=CMSSessionUtils.getSessionUser(session);
			if(CMSUserOrderConstant.LEVEL_TWO.equals(user.getLevel())){
				List<String> orderCodeList=authUserOrderRelationService.findOrderCodeListByUserId(user.getId(),user.getLoginName());
				page=orderBaseService.findUserOrderBaseListByPage(page,orderCodeList);
			}
			if(CMSUserOrderConstant.LEVEL_ONE.equals(user.getLevel())){
				page = orderBaseService.findOrderBaseListByPage(page);
			}
            List<OrderBase> orderBaseList= (List<OrderBase>) page.getResult();
            
            model.addAttribute("page", page);
			model.addAttribute("orderBaseDto", orderBaseDto);
			// 获取到订单状态Map
			model.addAttribute("orderStateMap",
					OrderConfigServerEnum.getOrderConfigServerMap());
			// 获取到支付状态Map
			model.addAttribute("paymentStateMap",
					PaymentTypeEnum.getPaymentTypeMap());
			model.addAttribute("orderBaseList",orderBaseList);
			model.addAttribute("orderStatus",orderStatus);
			
			model.addAttribute("storeMap", this.getStoreMap());
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("订单列表查询失败" + e.getMessage(), e);
		}
		return "models/order/orderList";
	}
	
	
	
	
	@RequestMapping(value = "/orderBaseDetails/{memberId}/{orderCode}", method = RequestMethod.GET)
	public String orderBaseDetails(@PathVariable Long memberId,
			@PathVariable String orderCode, ModelMap model) {
		OrderBase orderBase = orderBaseService.findByMemberIdAndOrderCode(
				memberId, orderCode);
		OrderTrans orderTrans = orderTransService.findByMemberIdAndOrderCode(
				memberId, orderCode);
		OrderFinance orderFinance = orderFinanceService
				.findByMemberIdAndOrderCode(memberId, orderCode);
		orderBase.setPaymentType(orderFinance.getPaymentType());
		orderBase.setIsInvoice(orderFinance.getIsInvoice());
		orderBase.setTransStatus(orderTrans.getTransStatus());
		OrderFee orderFee = orderFeeService.findByMemberIdAndOrderCode(
				memberId, orderCode);
		/**添加使用红包记录 begin*/
		ActivityRedEnvlopeUseRecord redRecord=    activityRedEnvlopeUseRecordService.findByOrderCode(orderCode);
		if(redRecord!=null)
			orderFee.setRedEnvelopeAmountTotal(redRecord.getRedEnvelopeAmount());
		/**添加使用红包记录 end*/
		List<OrderLine> orderLineList = orderLineService.findByOrderCode(
				orderCode, null);
		/** 订单行的商品总金额*/
		BigDecimal totalSkuPrice=new BigDecimal(0);
		for(OrderLine orderLine:orderLineList){
			String uiqueSkuCode=orderLine.getSkuCode();
			Sku sku=skuInfoService.queryByUniqueCode(uiqueSkuCode);
			if(sku!=null){
				/** 商品编码*/
				orderLine.setSkuCode(sku.getSkuNo());
				/**商品条形码*/
				orderLine.setSkuNo(sku.getBarCode());
			}
			/***/
			totalSkuPrice=totalSkuPrice.add(orderLine.getTotalPrice());
		}
		orderBase.setTotalAfDiscount(totalSkuPrice);
		OrderMemberAddress orderMemberAddress = orderMemberAddressService.findByMemberIdAndOrderCode(memberId, orderCode);
		getAddressNameByAreaCode(orderMemberAddress, model);
		PaymentDetailDto paymentDetailDto=new PaymentDetailDto();
		if(PaymentTypeEnum.PAYMENT_ZFB_WEB_JSDZ.getCode().equals(orderFinance.getPaymentType())){
			PaymentWebZfbJsdz paymentDetailWebZfb=paymentWebZfbJsdzService.queryByMemberIdAndOrderCode(memberId, orderCode);
			paymentDetailDto.setOrderNumber(paymentDetailWebZfb.getOrderNumber());
			paymentDetailDto.setTradeNo(paymentDetailWebZfb.getZfbTradeNo());
		}else if(PaymentTypeEnum.PAYMENT_ZFB_APP.getCode().equals(orderFinance.getPaymentType())){
			PaymentAppZfb paymentDetailAppZfb=paymentAppZfbService.getByMemberIdAndOrderCode(memberId, orderCode);
			paymentDetailDto.setOrderNumber(paymentDetailAppZfb.getOrderNumber());
			paymentDetailDto.setTradeNo(paymentDetailAppZfb.getZfbTradeNo());
		}else if(PaymentTypeEnum.PAYMENT_WX_WEB.getCode().equals(orderFinance.getPaymentType())){
			PaymentWebWx paymentWebWx=paymentWebWxService.getByMemberIdAndOrderCode(memberId, orderCode);
			paymentDetailDto.setOrderNumber(paymentWebWx.getOrderNumber());
			paymentDetailDto.setTradeNo(paymentWebWx.getWxTransactionId());
		}else if(PaymentTypeEnum.PAYMENT_WX_APP.getCode().equals(orderFinance.getPaymentType())){
			PaymentAppWx paymentAppWx=paymentAppWxService.getByMemberIdAndOrderCode(memberId, orderCode);
			paymentDetailDto.setOrderNumber(paymentAppWx.getOrderNumber());
			paymentDetailDto.setTradeNo(paymentAppWx.getWxTransactionId());
		}else if(PaymentTypeEnum.PAYMENT_ZXYL_WEB.getCode().equals(orderFinance.getPaymentType())){
			PaymentWebUnion paymentWebUnion=paymentWebUnionService.getByMemberIdAndOrderCode(memberId, orderCode);
			paymentDetailDto.setOrderNumber(paymentWebUnion.getOrderNumber());
			paymentDetailDto.setTradeNo(paymentWebUnion.getTn());
		}else if(PaymentTypeEnum.PAYMENT_ZXYL_APP.getCode().equals(orderFinance.getPaymentType())){
			PaymentAppUnion paymentAppUnion=paymentAppUnionService.getByMemberIdAndOrderCode(memberId, orderCode);
			paymentDetailDto.setOrderNumber(paymentAppUnion.getOrderNumber());
			paymentDetailDto.setTradeNo(paymentAppUnion.getTn());
		}
		model.addAttribute("paymentDetailDto", paymentDetailDto);
		model.addAttribute("orderBase", orderBase);
		// 获取到订单状态Map
		model.addAttribute("orderStateMap",
				OrderConfigServerEnum.getOrderConfigServerMap());
		// 获取到支付状态Map
		model.addAttribute("paymentStateMap",
				PaymentTypeEnum.getPaymentTypeMap());
		// 获取到来源状态Map
		model.addAttribute("sourceMap", SourceConstant.getSourceArray());
		model.addAttribute("orderTrans", orderTrans);
		model.addAttribute("orderFinance", orderFinance);
		model.addAttribute("orderFee", orderFee);
		model.addAttribute("orderLineList", orderLineList);
		model.addAttribute("orderMemberAddress", orderMemberAddress);
		return "models/order/orderBaseDetail";
	}

	private void getAddressNameByAreaCode(
			OrderMemberAddress orderMemberAddress, ModelMap model) {
		String province = memberAreaConfigService
				.getNameByAreaCode(orderMemberAddress.getProvince());
		String city = memberAreaConfigService.getNameByAreaCode(orderMemberAddress
				.getCity());
		String district = memberAreaConfigService
				.getNameByAreaCode(orderMemberAddress.getDistrict());
		model.addAttribute("province", province);
		model.addAttribute("city", city);
		model.addAttribute("district", district);
	}

	/**
	 * 前端更新订单状态请求处理
	 * 审核通过 发货 cms确认收货 用户拒收
	 * @param state
	 * @param memberId
	 * @param orderCode
	 * @throws RemoteException 
	 */
	@ResponseBody
	@RequestMapping(value = "/updateOrderStatus")
	public Object updateOrderStatus(@RequestParam("state") String state,
			@RequestParam("memberId") Long memberId,
			@RequestParam("orderCode") String orderCode,
			HttpSession session) throws RemoteException {
		OrderBase base = new OrderBase();
		base.setMemberId(memberId);
		base.setOrderCode(orderCode);
		base.setUpdateId(CMSSessionUtils.getSessionUser(session).getId());
		if (StringUtils.equals(state, OrderConfigServerEnum.CMS_CONFIRM_RECEIPT.getCode())) {
			OrderBase orderBase = orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
			if (orderBase.getLastReceiveTime() == null) {
				base.setLastReceiveTime(new Date());
				base.setOrderStatus(state);
			}
			if (StringUtils.equals(orderBase.getOrderStatus(), OrderConfigServerEnum.WEB_CONFIRM_RECEIPT.getCode())) {
				base.setOrderStatus(OrderConfigServerEnum.SUCCESS_TRANSACTION.getCode());
			}
		}else if(StringUtils.equals(state, OrderConfigServerEnum.USER_REJECT.getCode())){
			base.setOrderStatus(state);
			base.setIsRefused(CommonConstant.YES);
		}else if(StringUtils.equals(state, OrderConfigServerEnum.SUCCESS_SHIPPED.getCode())){
			OrderTrans orderTrans=orderTransService.findByMemberIdAndOrderCode(memberId, orderCode);
			if(ObjectUtils.equals(null, orderTrans)||StringUtil.isBlank(orderTrans.getTransCode())
					||StringUtil.isBlank(orderTrans.getTransName())){
				return "transInfoRequired";
			}
			base.setOrderStatus(state);
			OrderMemberAddress orderMemberAddress=orderMemberAddressService.findByMemberIdAndOrderCode(memberId, orderCode);
			Member member = memberService.findById(memberId);
			if(ObjectUtils.notEqual(null, orderMemberAddress)&&ObjectUtils.notEqual(null, member)){
				smsService.sendSmsDelivery(orderCode, orderTrans.getTransName(), orderTrans.getTransCode(), orderMemberAddress.getReceiverMobile(), SourceConstant.CMS_CODE, member.getLoginName());
			}
			
		}else{
			base.setOrderStatus(state);
		}
		try {
			orderBaseService.update(base);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("响应前端页面更新订单状态失败，失败原因：" + e);
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}
	
	@RequestMapping("gotoOrderAuditFail")
	public String gotoOrderAuditFail(
			Long memberId,String orderCode,ModelMap model){
		OrderBase orderBase = orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
		model.addAttribute("orderBase", orderBase);
		return "models/order/orderAuditFail";
	}
	
	/**
	 * 订单审核不通过
	 * @param memberId
	 * @param orderCode
	 */
	@ResponseBody
	@RequestMapping(value = "/orderAuditFail")
	public Object updateOrderAuditFail(String jsonStr,HttpSession session) {
		try {
			// 解析data
			Gson gson = new Gson();
			OrderBase orderBase = gson.fromJson(jsonStr,
					new TypeToken<OrderBase>() {
			}.getType());
			orderBase.setUpdateId(CMSSessionUtils.getSessionUser(session).getId());
			orderBase.setOrderStatus(OrderConfigServerEnum.FAIL_AUDIT.getCode());
			orderBaseService.update(orderBase);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("响应前端页面审核不通过失败，失败原因：" + e);
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}
	
	@RequestMapping("gotoOrderTransUpdate")
	public String gotoOrderTransUpdate(
			Long memberId,String orderCode,ModelMap model){
		OrderTrans orderTrans = orderTransService.findByMemberIdAndOrderCode(
				memberId, orderCode);
		model.addAttribute("orderTrans", orderTrans);
		
		model.addAttribute("orderExpressMap",sysmgrExpressConfigService.findAll());
		return "models/order/orderTransUpdate";
	}
	
	@ResponseBody
	@RequestMapping("updateOrderTrans")
	public Object updateOrderTrans(String jsonStr,HttpSession session){
		try {
			// 解析data
			Gson gson = new Gson();
			OrderTrans orderTrans = gson.fromJson(jsonStr,
					new TypeToken<OrderTrans>() {
					}.getType());
			orderTrans.setDeliveryTime(new Date());
			orderTrans.setUpdateId(CMSSessionUtils.getSessionUser(session).getId());
			orderTransService.updateByMemberIdAndOrderCode(orderTrans);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("物流修改失败"+e);
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}

	@RequestMapping(value = "/orderBasePrint/{memberId}/{orderCode}", method = RequestMethod.GET)
	public String orderBasePrint(@PathVariable Long memberId,
			@PathVariable String orderCode, ModelMap model) {
		OrderBase orderBase = orderBaseService.findByMemberIdAndOrderCode(
				memberId, orderCode);
		OrderTrans orderTrans = orderTransService.findByMemberIdAndOrderCode(
				memberId, orderCode);
		OrderFinance orderFinance = orderFinanceService
				.findByMemberIdAndOrderCode(memberId, orderCode);
		orderBase.setPaymentType(orderFinance.getPaymentType());
		orderBase.setIsInvoice(orderFinance.getIsInvoice());
		orderBase.setTransStatus(orderTrans.getTransStatus());
		OrderFee orderFee = orderFeeService.findByMemberIdAndOrderCode(
				memberId, orderCode);
		List<OrderLine> orderLineList = orderLineService.findByOrderCode(
				orderCode, null);
		Integer totalQuantity=0;
		BigDecimal totalPrice=BigDecimal.valueOf(0);
		List<OrderLineDto> orderLineDtoList = new ArrayList<OrderLineDto>();
		for(OrderLine orderLine : orderLineList){
			OrderLineDto lineDtotmp = new OrderLineDto();
			Sku skutmp = skuInfoService.queryByUniqueCode(orderLine.getSkuCode());
			lineDtotmp.setBarCode(skutmp.getBarCode());
			lineDtotmp.setSkuName(orderLine.getSkuName());
			lineDtotmp.setUnitPrice(orderLine.getUnitPrice());
			lineDtotmp.setQuantity(orderLine.getQuantity());
			lineDtotmp.setTotalPrice(orderLine.getTotalPrice());
			totalQuantity+=orderLine.getQuantity();
			totalPrice=totalPrice.add(orderLine.getTotalPrice());
			orderLineDtoList.add(lineDtotmp);
		}
		OrderMemberAddress orderMemberAddress = orderMemberAddressService
				.findByOrderCode(orderCode);
		getAddressNameByAreaCode(orderMemberAddress, model);
		ActivityRedEnvlopeUseRecord activityRedEnvlopeUseRecord =activityRedEnvlopeUseRecordService.findByOrderCode(orderCode);
		model.addAttribute("redEnvlopeUseAmount", ObjectUtils.equals(null, activityRedEnvlopeUseRecord)?"":activityRedEnvlopeUseRecord.getRedEnvelopeAmount());
		model.addAttribute("totalQuantity", totalQuantity);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("orderBase", orderBase);
		model.addAttribute("orderTrans", orderTrans);
		model.addAttribute("orderFinance", orderFinance);
		model.addAttribute("orderFee", orderFee);
		model.addAttribute("orderLineDtoList", orderLineDtoList);
		model.addAttribute("orderMemberAddress", orderMemberAddress);
		// 获取到支付状态Map
		model.addAttribute("paymentStateMap",
				PaymentTypeEnum.getPaymentTypeMap());
		return "models/order/orderBasePrint";
	}

	/**设置查询参数*/
	private Map<String, String> setParamByDto(OrderLineLineDto orderLineLineDto){
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderCode", orderLineLineDto.getOrderCode());
		params.put("skuNo", orderLineLineDto.getSkuNo());
		params.put("skuName", orderLineLineDto.getSkuName());
		params.put("createTimeFrom", orderLineLineDto.getCreateTimeFrom());
		params.put("createTimeTo", orderLineLineDto.getCreateTimeTo());
		return params;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("queryOrderLineList")
	public String findList(OrderLineLineDto orderLineLineDto,BasePagination page,
							HttpServletRequest request,ModelMap model){
		try {
			if(page == null){
				page = new BasePagination();
			}
			Map<String, String> params= setParamByDto(orderLineLineDto);
			params.put("orderCode",orderLineLineDto.getOrderCode());
			params.put("skuNo",orderLineLineDto.getSkuNo());
			params.put("skuName",orderLineLineDto.getSkuName());
			params.put("createTimeFrom",orderLineLineDto.getCreateTimeFrom());
			params.put("createTimeTo", orderLineLineDto.getCreateTimeTo());
			params.put("storeCode", orderLineLineDto.getStoreCode());
			String skuNo=orderLineLineDto.getSkuNo();
			if(StringUtils.isNotBlank(skuNo)){
				Sku skuInfotmp=skuInfoService.queryBySkuNo(skuNo,null);
				if(ObjectUtils.notEqual(null, skuInfotmp)){
					String skuCode=skuInfotmp.getUniqueCode();
					params.put("skuCode",skuCode);
				}else{
					model.addAttribute("page", page);
					model.addAttribute("orderLineLineDto", orderLineLineDto);
					model.addAttribute("orderLineList",new ArrayList<OrderLine>());
					return "models/order/orderLineList";
				}
			}

			params.put("skuName",orderLineLineDto.getSkuName());
			page.setParams(params);
			page = orderLineService.getListPage(page);
			model.addAttribute("page", page);
			model.addAttribute("orderLineLineDto", orderLineLineDto);
			List<OrderLine> orderLineList=(ArrayList<OrderLine>) page.getResult();
			for(OrderLine orderLinetmp: orderLineList){
				Sku skutmp=skuInfoService.queryByUniqueCode(orderLinetmp.getSkuCode());
				if(skutmp!=null)
				orderLinetmp.setSkuNo(skutmp.getSkuNo());
			}
			model.addAttribute("orderLineList",orderLineList);
			//分页结果对象
			page = orderLineService.getListPage(page);
			List<OrderLine> orderLine = (List<OrderLine>) page.getResult(); 
			model.addAttribute("orderLine", orderLine);
			model.addAttribute("page", page);
			model.addAttribute("orderLineLineDto",orderLineLineDto);
			return "models/order/orderLineList";
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("订单列表查询失败" + e.getMessage(), e);
		}
		return "models/order/orderLineList";
	}

	/**
	 * 设置页面 展示门店信息
	 * @param storeCode
	 * @param model
	 */
//	private void setStoreInfo(String storeCode, ModelMap model) {
//		model.addAttribute("storeList", storeService.findAll());
//		Store store=this.storeService.findByCode(storeCode==null?StoreConstant.STORE_CODE:storeCode);
//		model.addAttribute("storeName", store==null?StoreConstant.STORE_NAME:store.getName());
//	}
	
	@RequestMapping(value = "/toUpdateOrderDetails/{memberId}/{orderCode}", method = RequestMethod.GET)
	public String toUpdateOrderDetails(@PathVariable Long memberId,
			@PathVariable String orderCode, ModelMap model,HttpSession session) {
		OrderBase orderBase = orderBaseService.findByMemberIdAndOrderCode(
				memberId, orderCode);
		
		OrderMemberAddress orderMemberAddress = orderMemberAddressService.findByMemberIdAndOrderCode(memberId, orderCode);
		getAddressNameByAreaCode(orderMemberAddress, model);
		OrderFinance orderFinance=orderFinanceService.findByMemberIdAndOrderCode(memberId, orderCode);
		Long currentUserId=CMSSessionUtils.getSessionUser(session).getId();
		AuthUser authUser=authUserService.isSystemUser(currentUserId);
		boolean isSystemUser=ObjectUtils.equals(null, authUser)?false:true;
		model.addAttribute("isSystemUser", isSystemUser);
		model.addAttribute("orderFinance", orderFinance);
		model.addAttribute("orderBase", orderBase);
		model.addAttribute("orderMemberAddress", orderMemberAddress);
		return "models/order/updateOrderDetails";
	}

	@RequestMapping(value = "/updateOrderDetails", method = RequestMethod.POST)
	public String updateOrderDetails(@RequestParam Long memberId,RedirectAttributes redirectAttributes,
			@RequestParam String orderCode,@RequestParam String province,@RequestParam String city,@RequestParam BigDecimal paymentTotalActual,
			@RequestParam String district,@RequestParam String address,@RequestParam String memo,@RequestParam String receiver,@RequestParam String receiverMobile,
			ModelMap model) {
		OrderBase orderBase = orderBaseService.findByMemberIdAndOrderCode(
				memberId, orderCode);
		orderBase.setMemo(memo);
		orderBaseService.update(orderBase);
		OrderMemberAddress orderMemberAddress = orderMemberAddressService.findByMemberIdAndOrderCode(memberId, orderCode);
		orderMemberAddress.setProvince(province);
		orderMemberAddress.setCity(city);
		orderMemberAddress.setDistrict(district);
		orderMemberAddress.setAddress(address);
		orderMemberAddress.setReceiver(receiver);
		orderMemberAddress.setReceiverMobile(receiverMobile);
		orderMemberAddressService.updateByOrderCodeAddress(orderMemberAddress);
		OrderFinance orderFinance=orderFinanceService.findByMemberIdAndOrderCode(memberId, orderCode);
		orderFinance.setPaymentTotalActual(paymentTotalActual);
		orderFinanceService.updateByMemberIdAndOrderCode(orderFinance);
		redirectAttributes.addFlashAttribute("message", "修改成功！");
		return "redirect:" + Global.getAdminPath()
				+ "/order/queryOrderBaseList";
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateOrderType", method = RequestMethod.POST)
	public Object updateOrderDetails(@RequestParam Long memberId,@RequestParam Integer orderType,
			@RequestParam String orderCode,RedirectAttributes redirectAttributes,
			ModelMap model) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			OrderBase orderBase = orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
			orderBase.setOrderType(orderType);
			orderBaseService.update(orderBase);
			jsonModel.Success("订单类型修改成功!");
		} catch (Exception e) {
			jsonModel.Fail("订单类型修改失败!");
		}
		return jsonModel;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/orderStatistics/{isFirst}")
	public String orderStatistics(OrderBaseDto orderBaseDto,@PathVariable String isFirst,
			BasePagination page, ModelMap model) {
		try {
			//第一次请求设置默认时间
			if("y".equals(isFirst)){
				Date date=new Date();
				DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				String currentDate=format.format(date); 
				orderBaseDto.setCreateTimeTo(currentDate);
				Calendar cal=Calendar.getInstance();//获取当前日期
		        cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		        String firstDay = format.format(cal.getTime());
		        orderBaseDto.setCreateTimeFrom(firstDay);
			}
			
			BasePagination basePage = null;
			if (page == null) {
				basePage = new BasePagination();
			}else{
				basePage = page;
			}
            Map<String, String> params = new HashMap<String, String>();
            params.put("orderStatus", OrderConfigServerEnum.SUCCESS_TRANSACTION.getCode());
            params.put("createTimeFrom", orderBaseDto.getCreateTimeFrom());
    		params.put("createTimeTo", orderBaseDto.getCreateTimeTo());
    		params.put("paymentType", orderBaseDto.getPaymentType());
            basePage.setParams(params);
            BasePagination pageResult = orderBaseService.findListPageStatisticsUse(basePage);
			model.addAttribute("page", page);
			model.addAttribute("orderBaseDto", orderBaseDto);
			// 获取到订单状态Map
			model.addAttribute("orderStateMap",
					OrderConfigServerEnum.getOrderConfigServerMap());
			// 获取到支付状态Map
			model.addAttribute("paymentStateMap",
					PaymentTypeEnum.getPaymentTypeMap());
			
			List<OrderBase> orderBaseList=(ArrayList<OrderBase>) pageResult.getResult();
			for(OrderBase orderBase:orderBaseList){
				Long memberId=orderBase.getMemberId();
				String orderCode=orderBase.getOrderCode();
				OrderFee orderFee=orderFeeService.findByMemberIdAndOrderCode(memberId, orderCode);
				if(!orderFee.equals(null)){
					BigDecimal totalActual=orderFee.getTotalActual()==null?BigDecimal.valueOf(0, 2):orderFee.getTotalActual();
					BigDecimal totalAfDiscount=orderFee.getTotalAfDiscount()==null?BigDecimal.valueOf(0, 2):orderFee.getTotalAfDiscount();
					BigDecimal redEnvelopeAmount=orderFee.getRedEnvelopeAmountTotal()==null?BigDecimal.valueOf(0, 2):orderFee.getRedEnvelopeAmountTotal();
					BigDecimal cardAmount=orderFee.getCardAmount()==null?BigDecimal.valueOf(0, 2):orderFee.getCardAmount();
					BigDecimal epointsRedeemMoney=orderFee.getEpointsRedeemMoney()==null?BigDecimal.valueOf(0, 2):orderFee.getEpointsRedeemMoney();
					BigDecimal actualTransferFee=orderFee.getActualTransferFee()==null?BigDecimal.valueOf(0, 2):orderFee.getActualTransferFee();
					orderBase.setTotalActual(totalActual);
					orderBase.setTotalAfDiscount(totalAfDiscount);
					orderBase.setRedEnvelopeAmount(redEnvelopeAmount);
					orderBase.setCardAmount(cardAmount);
					orderBase.setEpointsRedeemMoney(epointsRedeemMoney);
					orderBase.setActualTransferFee(actualTransferFee);
				}
			}
			model.addAttribute("orderBaseList",orderBaseList);
			
			List<OrderBase> orderBaseListAll=orderBaseService.getListStatisticsUse((HashMap<String, String>) params);
			//总金额   =   商品总金额 -   红包  -   优惠券  -   积分  - 运费
			BigDecimal totalActualTotal=BigDecimal.valueOf(0, 2);
			//商品总金额 
			BigDecimal totalAfDiscountTotal=BigDecimal.valueOf(0, 2);
			//红包
			BigDecimal redEnvelopeAmountTotal=BigDecimal.valueOf(0, 2);
			//优惠券
			BigDecimal cardAmountTotal=BigDecimal.valueOf(0, 2);
			//积分
			BigDecimal epointsRedeemMoneyTotal=BigDecimal.valueOf(0, 2);
			//运费
			BigDecimal actualTransferFeeTotal=BigDecimal.valueOf(0, 2);
			
			for(OrderBase orderBase:orderBaseListAll){
				Long memberId=orderBase.getMemberId();
				String orderCode=orderBase.getOrderCode();
				OrderFee orderFee=orderFeeService.findByMemberIdAndOrderCode(memberId, orderCode);
				if(!orderFee.equals(null)){
					BigDecimal totalActual=orderFee.getTotalActual()==null?BigDecimal.valueOf(0, 2):orderFee.getTotalActual();
					BigDecimal totalAfDiscount=orderFee.getTotalAfDiscount()==null?BigDecimal.valueOf(0, 2):orderFee.getTotalAfDiscount();
					BigDecimal redEnvelopeAmount=orderFee.getRedEnvelopeAmountTotal()==null?BigDecimal.valueOf(0, 2):orderFee.getRedEnvelopeAmountTotal();
					BigDecimal cardAmount=orderFee.getCardAmount()==null?BigDecimal.valueOf(0, 2):orderFee.getCardAmount();
					BigDecimal epointsRedeemMoney=orderFee.getEpointsRedeemMoney()==null?BigDecimal.valueOf(0, 2):orderFee.getEpointsRedeemMoney();
					BigDecimal actualTransferFee=orderFee.getActualTransferFee()==null?BigDecimal.valueOf(0, 2):orderFee.getActualTransferFee();
					totalActualTotal=totalActualTotal.add(totalActual);
					totalAfDiscountTotal=totalAfDiscountTotal.add(totalAfDiscount);
					redEnvelopeAmountTotal=redEnvelopeAmountTotal.add(redEnvelopeAmount);
					cardAmountTotal=cardAmountTotal.add(cardAmount);
					epointsRedeemMoneyTotal=epointsRedeemMoneyTotal.add(epointsRedeemMoney);
					actualTransferFeeTotal=actualTransferFeeTotal.add(actualTransferFee);
				}
			}
			model.put("totalActualTotal", totalActualTotal);
			model.put("totalAfDiscountTotal",totalAfDiscountTotal );
			model.put("redEnvelopeAmountTotal",redEnvelopeAmountTotal );
			model.put("cardAmountTotal",cardAmountTotal );
			model.put("epointsRedeemMoneyTotal", epointsRedeemMoneyTotal);
			model.put("actualTransferFeeTotal", actualTransferFeeTotal);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("订单财务统计列表查询失败" + e.getMessage(), e);
		}
		return "models/order/orderStatistics";
	}
	
	/***
	 * 导出列表
	 * 
	 * @param orderDto
	 * @param response
	 * @param page
	 */
	@RequestMapping("export_excel_orderStatistics_list")
	public void exportExcelOrderList(OrderBaseDto orderBaseDto,
			HttpServletResponse response, BasePagination page) {
		try {
			Map<String, String> params = new HashMap<String, String>();
            params.put("orderStatus", OrderConfigServerEnum.SUCCESS_TRANSACTION.getCode());
            params.put("createTimeFrom", orderBaseDto.getCreateTimeFrom());
    		params.put("createTimeTo", orderBaseDto.getCreateTimeTo());
    		params.put("paymentType", orderBaseDto.getPaymentType());
			List<OrderBase> orderBaseListAll=orderBaseService.getListStatisticsUse((HashMap<String, String>) params);
			for(OrderBase orderBase:orderBaseListAll){
				Long memberId=orderBase.getMemberId();
				String orderCode=orderBase.getOrderCode();
				OrderFee orderFee=orderFeeService.findByMemberIdAndOrderCode(memberId, orderCode);
				if(!orderFee.equals(null)){
					BigDecimal totalActual=orderFee.getTotalActual()==null?BigDecimal.valueOf(0, 2):orderFee.getTotalActual();
					BigDecimal totalAfDiscount=orderFee.getTotalAfDiscount()==null?BigDecimal.valueOf(0, 2):orderFee.getTotalAfDiscount();
					BigDecimal redEnvelopeAmount=orderFee.getRedEnvelopeAmountTotal()==null?BigDecimal.valueOf(0, 2):orderFee.getRedEnvelopeAmountTotal();
					BigDecimal cardAmount=orderFee.getCardAmount()==null?BigDecimal.valueOf(0, 2):orderFee.getCardAmount();
					BigDecimal epointsRedeemMoney=orderFee.getEpointsRedeemMoney()==null?BigDecimal.valueOf(0, 2):orderFee.getEpointsRedeemMoney();
					BigDecimal actualTransferFee=orderFee.getActualTransferFee()==null?BigDecimal.valueOf(0, 2):orderFee.getActualTransferFee();
					orderBase.setTotalActual(totalActual);
					orderBase.setTotalAfDiscount(totalAfDiscount);
					orderBase.setRedEnvelopeAmount(redEnvelopeAmount);
					orderBase.setCardAmount(cardAmount);
					orderBase.setEpointsRedeemMoney(epointsRedeemMoney);
					orderBase.setActualTransferFee(actualTransferFee);
				}
			}
			OrderStatisticsExcelReport report = new OrderStatisticsExcelReport();
			report.getReport("订单财务统计列表Excel", orderBaseListAll, response);
		} catch (Exception e) {
			LOGGER.error("订单财务统计列表Excel导出失败", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryOrderBaseListByType/{orderType}")
	public String queryOrderBaseListByType(OrderBaseDto orderBaseDto,@PathVariable String orderType,
			BasePagination page, ModelMap model) {
		try {
			BasePagination basePage = null;
			if (page == null) {
				basePage = new BasePagination();
			}else{
				basePage = page;
			}
			orderBaseDto.setOrderType(orderType);
            Map<String, String> params = setParamByOrderDto(orderBaseDto);
            basePage.setParams(params);
            BasePagination pageResult = orderBaseService.findOrderBaseListByPage(basePage);
            List<OrderBase> orderBaseList= (List<OrderBase>) pageResult.getResult();
			model.addAttribute("page", page);
			model.addAttribute("orderBaseDto", orderBaseDto);
			// 获取到订单状态Map
			model.addAttribute("orderStateMap",OrderConfigServerEnum.getOrderConfigServerMap());
			// 获取到支付状态Map
			model.addAttribute("paymentStateMap",PaymentTypeEnum.getPaymentTypeMap());
			model.addAttribute("orderBaseList",orderBaseList);
			model.addAttribute("storeMap", this.getStoreMap());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("订单列表查询失败" + e.getMessage(), e);
		}
		
		return "models/order/orderListByType";
	}
	
	/***
	 * 此方法只供用户查看  不可进行其他的操作
	 * @param orderBaseDto
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/orderBaseList")
	public String OrderBaseList(OrderBaseDto orderBaseDto,
			BasePagination page, ModelMap model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = setParamByOrderDto(orderBaseDto);
			page.setParams(params);
			page = orderBaseService.findOrderBaseListByPage(page);
			List<OrderBase> orderBaseList= (List<OrderBase>) page.getResult();
	
	        model.addAttribute("page", page);
			model.addAttribute("orderBaseDto", orderBaseDto);
			// 获取到订单状态Map
			model.addAttribute("orderStateMap",OrderConfigServerEnum.getOrderConfigServerMap());
			// 获取到支付状态Map
			model.addAttribute("paymentStateMap",PaymentTypeEnum.getPaymentTypeMap());
			model.addAttribute("orderBaseList",orderBaseList);
			model.addAttribute("storeMap", this.getStoreMap());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("订单列表查询失败" + e.getMessage(), e);
		}
		return "models/order/orderBaseList2";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
	public Object deleteOrder(@RequestParam Long memberId,
			@RequestParam String orderCode,RedirectAttributes redirectAttributes,
			ModelMap model) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			orderBaseService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			orderLineService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			orderCardRelationService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			orderFeeService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			orderFinanceService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			orderMemberAddressService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			orderRemindDeliveryService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			orderTraceService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			orderTransService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			this.paymentAppAlabaoService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			this.paymentAppUnionService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			this.paymentAppWxService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			this.paymentAppZfbService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			this.paymentWebUnionService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			this.paymentWebWxService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			this.paymentWebZfbJsdzService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			this.paymentWapZfbJsdzService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			this.paymentWebZfbDbjyService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			this.refundSerivce.deleteByOrderCodeAndMemberId(orderCode, memberId);
			jsonModel.Success("删除订单成功!");
		} catch (Exception e) {
			jsonModel.Fail("删除订单失败!");
		}
		return jsonModel;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/orderRefundList")
	public String orderRefundList(OrderBaseDto orderBaseDto,BasePagination page, ModelMap model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			orderBaseDto.setOrderStatus(OrderConfigServerEnum.FAIL_AUDIT.getCode());
            Map<String, String> params = setParamByOrderDto(orderBaseDto);
            page.setParams(params);
            BasePagination pageResult = orderBaseService.findOrderBaseListByPage(page);
            List<OrderBase>  orderBaseList= (List<OrderBase>) pageResult.getResult();
            model.addAttribute("page", page);
			model.addAttribute("orderBaseDto", orderBaseDto);
			// 获取到订单状态Map
			model.addAttribute("orderStateMap",	OrderConfigServerEnum.getOrderConfigServerMap());
			// 获取到支付状态Map
			model.addAttribute("paymentStateMap",PaymentTypeEnum.getPaymentTypeMap());
			model.addAttribute("orderBaseList",	orderBaseList );
			model.addAttribute("storeMap", this.getStoreMap());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("订单列表查询失败" + e.getMessage(), e);
		}
		return "models/order/refund/orderRefundList";
	}
	
	
	
	
	public  Map<String, String> getStoreMap(){
		List<Store> storeList= this.storeService.findAll();
		Map<String, String> map=new HashMap<String, String>();
		for (Store store : storeList) {
			map.put(store.getCode(), store.getName());
		}
		return map;
	}
	
	
}
