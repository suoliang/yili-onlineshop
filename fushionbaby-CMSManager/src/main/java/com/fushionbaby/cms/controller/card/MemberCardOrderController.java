package com.fushionbaby.cms.controller.card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.alabao.model.AlabaoConsumeRecord;
import com.aladingshop.alabao.service.AlabaoConsumeRecordService;
import com.aladingshop.card.model.MemberCardOrder;
import com.aladingshop.card.service.MemberCardOrderService;
import com.fushionbaby.cms.dto.YiDuoBaoOrderDto;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.service.OrderFinanceService;
import com.fushionbaby.payment.model.PaymentAppAlabao;
import com.fushionbaby.payment.model.PaymentAppUnion;
import com.fushionbaby.payment.model.PaymentAppWx;
import com.fushionbaby.payment.model.PaymentAppZfb;
import com.fushionbaby.payment.model.PaymentWapZfbJsdz;
import com.fushionbaby.payment.service.PaymentAppAlabaoService;
import com.fushionbaby.payment.service.PaymentAppUnionService;
import com.fushionbaby.payment.service.PaymentAppWxService;
import com.fushionbaby.payment.service.PaymentAppZfbService;
import com.fushionbaby.payment.service.PaymentWapZfbJsdzService;

/***
 * 益多宝订单
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年9月30日下午3:38:24
 */
@RequestMapping("memberCard")
@Controller
public class MemberCardOrderController {

	private static final Log LOGGER=LogFactory.getLog(MemberCardOrderController.class);
	
	/** 益多宝订单*/
	@Autowired
	private MemberCardOrderService<MemberCardOrder> memberCardOrderService;
	
	@Autowired
	private OrderFinanceService<OrderFinance> orderFinanceService;
	
	@Autowired
	private PaymentWapZfbJsdzService<PaymentWapZfbJsdz> paymentWapZfbJsdzService;
	
	@Autowired
	private PaymentAppZfbService<PaymentAppZfb> paymentAppZfbService;
	
	@Autowired
	private AlabaoConsumeRecordService<AlabaoConsumeRecord> alabaoConsumeRecordService;
	
	@Autowired
	private PaymentAppUnionService<PaymentAppUnion> paymentAppUnionService;
	
	@Autowired
	private PaymentAppWxService<PaymentAppWx> paymentAppWxService;

	@Autowired
	private PaymentAppAlabaoService<PaymentAppAlabao> paymentAppAlabaoService;
	
	private static final String PRE_="models/card/order/";
	
	@SuppressWarnings("unchecked")
	@RequestMapping("orderList")
	public String orderList(YiDuoBaoOrderDto yiDuoBaoOrderDto,Model model,BasePagination page){
		LOGGER.info("cms:MemberCardOrderController.java  益多宝订单查询开始");
		if (page == null) {
			page = new BasePagination();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderCode", yiDuoBaoOrderDto.getOrderCode());
		params.put("memberName", yiDuoBaoOrderDto.getMemberName());
		params.put("createTimeFrom", yiDuoBaoOrderDto.getCreateTimeFrom());
		params.put("createTimeTo", yiDuoBaoOrderDto.getCreateTimeTo());
		params.put("financeStatus", yiDuoBaoOrderDto.getFinanceStatus());
		page.setParams(params);
		page = this.memberCardOrderService.getListPage(page);
		List<MemberCardOrder> memberCardOrderlist = (List<MemberCardOrder>) page.getResult();
		model.addAttribute("memberCardOrderlist", memberCardOrderlist);
		model.addAttribute("yiDuoBaoOrderDto", yiDuoBaoOrderDto);
		model.addAttribute("orderStatusMap",OrderConfigServerEnum.getOrderConfigServerMap());
		model.addAttribute("paymentStateMap",PaymentTypeEnum.getPaymentTypeMap());
		model.addAttribute("sourceMap",SourceConstant.getSourceArray());
		model.addAttribute("page", page);
		return PRE_+"memberCardOrderList";
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateOrderType", method = RequestMethod.POST)
	public Object updateOrderDetails(@RequestParam Long memberId,@RequestParam String orderType,
			@RequestParam String orderCode,RedirectAttributes redirectAttributes,
			ModelMap model) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			MemberCardOrder memberCardOrder = memberCardOrderService.findByMemberIdOrderCode(memberId, orderCode);
			memberCardOrder.setOrderType(orderType);
			memberCardOrderService.update(memberCardOrder);
			jsonModel.Success("订单类型修改成功!");
		} catch (Exception e) {
			jsonModel.Fail("订单类型修改失败!");
		}
		return jsonModel;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
	public Object deleteOrder(@RequestParam Long memberId,
			@RequestParam String orderCode,RedirectAttributes redirectAttributes,
			ModelMap model) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			MemberCardOrder memberCardOrder=memberCardOrderService.findByMemberIdOrderCode(memberId, orderCode);
			memberCardOrderService.deleteById(memberCardOrder.getId());
			orderFinanceService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			this.paymentAppUnionService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			this.paymentAppWxService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			this.paymentAppZfbService.deleteByOrderCodeAndMemberId(orderCode, memberId);
			alabaoConsumeRecordService.deleteByOrderCodeAndMemberId(orderCode, memberId);;
			jsonModel.Success("删除订单成功!");
		} catch (Exception e) {
			jsonModel.Fail("删除订单失败!");
		}
		return jsonModel;
	}
}
