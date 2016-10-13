package com.fushionbaby.cms.controller.store;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.aladingshop.refund.service.RefundService;
import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.auth.model.AuthUserOrderRelation;
import com.fushionbaby.auth.service.AuthUserOrderRelationService;
import com.fushionbaby.auth.service.AuthUserService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.PaymentDetailDto;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.BasePagination;
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
import com.fushionbaby.order.service.OrderBaseService;
import com.fushionbaby.order.service.OrderCardRelationService;
import com.fushionbaby.order.service.OrderFeeService;
import com.fushionbaby.order.service.OrderFinanceService;
import com.fushionbaby.order.service.OrderLineService;
import com.fushionbaby.order.service.OrderMemberAddressService;
import com.fushionbaby.order.service.OrderRemindDeliveryService;
import com.fushionbaby.order.service.OrderTraceService;
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

@Controller
@RequestMapping("/storeOrder")
public class StoreOrderBaseController extends BaseController {

	private static final Log LOGGER = LogFactory.getLog(StoreOrderBaseController.class);
	@Autowired
	private OrderBaseService<OrderBase> orderBaseService;

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
		params.put("createTimeFrom", orderBaseDto.getCreateTimeFrom());
		params.put("createTimeTo", orderBaseDto.getCreateTimeTo());
		params.put("orderType", orderBaseDto.getOrderType());
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
			page = orderBaseService.findStoreOrderPageList(page);
			
			List<OrderBase> orderBaseList=(List<OrderBase>) page.getResult();
		     
	        
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
		return "models/store/order/storeOrderList";
	}

	
	
	
	
	
	
	@RequestMapping(value = "/orderBaseDetails/{memberId}/{orderCode}", method = RequestMethod.GET)
	public String orderBaseDetails(@PathVariable Long memberId,
			@PathVariable String orderCode, ModelMap model) {
		OrderBase orderBase = orderBaseService.findByMemberIdAndOrderCode(
				memberId, orderCode);
		
		OrderFinance orderFinance = orderFinanceService
				.findByMemberIdAndOrderCode(memberId, orderCode);
		orderBase.setPaymentType(orderFinance.getPaymentType());
		orderBase.setIsInvoice(orderFinance.getIsInvoice());
		
		OrderFee orderFee = orderFeeService.findByMemberIdAndOrderCode(
				memberId, orderCode);
		/**添加使用红包记录 begin*/
		ActivityRedEnvlopeUseRecord redRecord= activityRedEnvlopeUseRecordService.findByOrderCode(orderCode);
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
		
		model.addAttribute("orderFinance", orderFinance);
		model.addAttribute("orderFee", orderFee);
		model.addAttribute("orderLineList", orderLineList);
		model.addAttribute("orderMemberAddress", orderMemberAddress);
		return "models/store/order/storeOrderDetail";
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

	public  Map<String, String> getStoreMap(){
		List<Store> storeList= this.storeService.findAll();
		Map<String, String> map=new HashMap<String, String>();
		for (Store store : storeList) {
			map.put(store.getCode(), store.getName());
		}
		return map;
	}
	
	
}
