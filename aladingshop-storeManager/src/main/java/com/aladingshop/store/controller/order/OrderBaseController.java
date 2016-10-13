package com.aladingshop.store.controller.order;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.controller.BaseController;
import com.aladingshop.store.dto.OrderDto;
import com.aladingshop.store.dto.PaymentDetailDto;
import com.aladingshop.store.util.CMSSessionUtils;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.order.OrderLineDto;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.order.dto.OrderBaseDto;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.model.OrderFee;
import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.model.OrderLine;
import com.fushionbaby.order.model.OrderMemberAddress;
import com.fushionbaby.order.service.OrderBaseService;
import com.fushionbaby.order.service.OrderFeeService;
import com.fushionbaby.order.service.OrderFinanceService;
import com.fushionbaby.order.service.OrderLineService;
import com.fushionbaby.order.service.OrderMemberAddressService;
import com.fushionbaby.payment.model.PaymentAppUnion;
import com.fushionbaby.payment.model.PaymentAppWx;
import com.fushionbaby.payment.model.PaymentAppZfb;
import com.fushionbaby.payment.model.PaymentWapZfbJsdz;
import com.fushionbaby.payment.model.PaymentWebUnion;
import com.fushionbaby.payment.model.PaymentWebWx;
import com.fushionbaby.payment.model.PaymentWebZfbJsdz;
import com.fushionbaby.payment.service.PaymentAppUnionService;
import com.fushionbaby.payment.service.PaymentAppWxService;
import com.fushionbaby.payment.service.PaymentAppZfbService;
import com.fushionbaby.payment.service.PaymentWapZfbJsdzService;
import com.fushionbaby.payment.service.PaymentWebUnionService;
import com.fushionbaby.payment.service.PaymentWebWxService;
import com.fushionbaby.payment.service.PaymentWebZfbDbjyService;
import com.fushionbaby.payment.service.PaymentWebZfbJsdzService;

@Controller
@RequestMapping("/order")
public class OrderBaseController extends BaseController {

	private static final Log LOGGER = LogFactory.getLog(OrderBaseController.class);
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
			
			StoreAuthUser user=CMSSessionUtils.getSessionUser(session);
			String storeCode = user.getStoreCode();
			orderBaseDto.setStoreCode(storeCode);
			Map<String, String> params = setParamByOrderDto(orderBaseDto);
			page.setParams(params);

				page = orderBaseService.findOrderBaseListByPage(page);

			List<OrderBase> orderBaseList=(List<OrderBase>) page.getResult();
			/**后台登陆用户会员等级标志  1 所有订单可处理  2 部分订单*/
			model.addAttribute("page", page);
			model.addAttribute("orderBaseDto", orderBaseDto);
			// 获取到订单状态Map
			model.addAttribute("orderStateMap",OrderConfigServerEnum.getOrderConfigServerMap());
			// 获取到支付状态Map
			model.addAttribute("paymentStateMap",PaymentTypeEnum.getPaymentTypeMap());
			model.addAttribute("orderBaseList",orderBaseList);
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
			StoreAuthUser user=CMSSessionUtils.getSessionUser(session);
			String storeCode = user.getStoreCode();
			orderBaseDto.setStoreCode(storeCode);
			orderBaseDto.setOrderStatus(orderStatus);
            Map<String, String> params = setParamByOrderDto(orderBaseDto);
            page.setParams(params);
            
			page = orderBaseService.findOrderBaseListByPage(page);

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
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("订单列表查询失败" + e.getMessage(), e);
		}
		return "models/order/orderList";
	}
	
	
	
	
	
	@RequestMapping(value = "/orderBaseDetails/{orderCode}", method = RequestMethod.GET)
	public String orderBaseDetails(@PathVariable String orderCode, ModelMap model) {
		OrderBase orderBase = orderBaseService.findByOrderCode(orderCode);
		Long memberId = orderBase.getMemberId();
		OrderFinance orderFinance = orderFinanceService.findByMemberIdAndOrderCode(memberId, orderCode);
		orderBase.setPaymentType(orderFinance.getPaymentType());
		orderBase.setIsInvoice(orderFinance.getIsInvoice());
		OrderFee orderFee = orderFeeService.findByMemberIdAndOrderCode(memberId, orderCode);
		List<OrderLine> orderLineList = orderLineService.findByOrderCode(orderCode, null);
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
		model.addAttribute("paymentStateMap",PaymentTypeEnum.getPaymentTypeMap());
		// 获取到来源状态Map
		model.addAttribute("sourceMap", SourceConstant.getSourceArray());
		//model.addAttribute("orderTrans", orderTrans);
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
	@RequestMapping(value="updateOrdersStatus", method = RequestMethod.POST)
	public JsonResponseModel updateOrdersStatus(@RequestParam("orderStatus")String status,
			@RequestParam("tempMemberIds")String tempMemberIds,
			@RequestParam("tempOrderCodes")String tempOrderCodes,HttpServletRequest request){
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			String[] memberIds=tempMemberIds.split(",");
			String[] orderCodes=tempOrderCodes.split(",");
	
			for(int i=0;i<orderCodes.length;i++){
				Long memberId=Long.parseLong(memberIds[i]);
				String orderCode=orderCodes[i];
				OrderBase orderBase=new OrderBase();
				orderBase.setMemberId(memberId);
				orderBase.setOrderCode(orderCode);
				orderBase.setOrderStatus(status);
				orderBaseService.update(orderBase);
			}
			jsonModel.Success("操作成功!");
		} catch (Exception e) {
			jsonModel.Fail("操作出错!");
			LOGGER.error("操作出错", e);
		}
		return jsonModel;
	}
	


	@RequestMapping(value = "/orderBasePrint/{memberId}/{orderCode}", method = RequestMethod.GET)
	public String orderBasePrint(@PathVariable Long memberId,@PathVariable String orderCode, ModelMap model,HttpSession session) {

		OrderBase orderBase = orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
		OrderFinance orderFinance = orderFinanceService.findByMemberIdAndOrderCode(memberId, orderCode);
		orderBase.setPaymentType(orderFinance.getPaymentType());
		orderBase.setIsInvoice(orderFinance.getIsInvoice());
		OrderFee orderFee = orderFeeService.findByMemberIdAndOrderCode(memberId, orderCode);
		List<OrderLine> orderLineList = orderLineService.findByOrderCode(orderCode, null);
		Integer totalQuantity=0;
		BigDecimal totalPrice=BigDecimal.valueOf(0);
		List<OrderLineDto> orderLineDtoList = new ArrayList<OrderLineDto>();
		for(OrderLine orderLine : orderLineList){
			OrderLineDto lineDtotmp = new OrderLineDto();
			lineDtotmp.setSkuName(orderLine.getSkuName());
			lineDtotmp.setUnitPrice(orderLine.getUnitPrice());
			lineDtotmp.setQuantity(orderLine.getQuantity());
			lineDtotmp.setRowsPriceTotal(orderLine.getUnitPrice().multiply(new BigDecimal(orderLine.getQuantity())));
			totalQuantity+=orderLine.getQuantity();
			totalPrice=totalPrice.add(lineDtotmp.getRowsPriceTotal());
			orderLineDtoList.add(lineDtotmp);
		}
		OrderMemberAddress orderMemberAddress = orderMemberAddressService.findByOrderCode(orderCode);
	
		getAddressNameByAreaCode(orderMemberAddress, model);
		model.addAttribute("totalQuantity", totalQuantity);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("orderBase", orderBase);
		model.addAttribute("orderFinance", orderFinance);
		model.addAttribute("orderFee", orderFee);
		model.addAttribute("orderLineDtoList", orderLineDtoList);
		model.addAttribute("orderMemberAddress", orderMemberAddress);
		// 获取到支付状态Map
		model.addAttribute("paymentStateMap",PaymentTypeEnum.getPaymentTypeMap());
		return "models/order/orderBasePrint";
	}

	
	
	
		@RequestMapping("export_excel_order_list")
		public void exportOrderList(OrderBaseDto orderBaseDto,
				BasePagination page, HttpServletResponse response, ModelMap model,HttpSession session){
			try {
				StoreAuthUser user=CMSSessionUtils.getSessionUser(session);
				orderBaseDto.setStoreCode(user.getStoreCode());
				Map<String, String> params = setParamByOrderDto(orderBaseDto);
				List<OrderBase> orderBaseList = orderBaseService.getListStatisticsUse((HashMap<String, String>) params);
				
				Map<String,String> orderStatusMap = OrderConfigServerEnum.getOrderConfigServerMap();
				
				List<OrderDto> orderList = new ArrayList<OrderDto>();
				for (OrderBase orderBase : orderBaseList) {
					
					OrderDto orderDto = new OrderDto();
					
					OrderMemberAddress orderMemberAddress=orderMemberAddressService.findByOrderCode(orderBase.getOrderCode());
					OrderFee orderFee = orderFeeService.findByOrderCode(orderBase.getOrderCode());
					orderDto.setMemberId(orderBase.getMemberId());
					orderDto.setMemberName(orderBase.getMemberName());
					orderDto.setOrderCode(orderBase.getOrderCode());
					orderDto.setCreateTime(orderBase.getCreateTime());
					orderDto.setPaymentTotalActual(orderFee.getTotalActual());
					//设置订单状态
					orderDto.setOrderStatus(orderStatusMap.get(orderBase.getOrderStatus()));
					orderDto.setMemo(orderBase.getMemo());
					if(orderMemberAddress != null){
						orderDto.setReceiver(orderMemberAddress.getReceiver());
						orderDto.setReceiverMobile(orderMemberAddress.getReceiverMobile());
					}
					
					orderList.add(orderDto);
				}
				
				
				OrderExcelReport report = new OrderExcelReport();
				String dateString = new String(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).getBytes("ISO-8859-1"),"UTF-8");
				report.getReport("订单导出Excel"+dateString, orderList, response);
			} catch (Exception e) {
				LOGGER.error("订单列表导出Excel失败"+e.getMessage(), e);
			}
		}
		
		
}
