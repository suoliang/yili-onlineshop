package com.fushionbaby.cms.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.OrderFinanceDto;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.service.OrderFinanceService;

@Controller
@RequestMapping("/orderFinance")
public class OrderFinanceController extends BaseController {

	private static final Log LOGGER = LogFactory
			.getLog(OrderFinanceController.class);


	@Autowired
	private OrderFinanceService<OrderFinance> orderFinanceService;
	@Autowired
	private MemberService<Member> memberService;
	


	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryOrderFinanceList")
	public String queryOrderFinanceList(OrderFinanceDto orderFinanceDto,
			BasePagination page, ModelMap model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			model.addAttribute("orderFinanceDto", orderFinanceDto);
			Map<String, String> params = new HashMap<String, String>();
			String memberName=orderFinanceDto.getMemberName();
			if(!"".equals(memberName)&&ObjectUtils.notEqual(null, memberName)){
				Member member=memberService.findByLoginName(memberName);
				if(ObjectUtils.equals(null, member)){
					model.addAttribute("list", new ArrayList<OrderFinanceDto>());
					model.addAttribute("page", page);
					return "models/order/orderFinanceList";
				}else{
					params.put("memberId",String.valueOf(member.getId()));
				}
			}
			params.put("orderCode", orderFinanceDto.getOrderCode());
			params.put("financeStatus", orderFinanceDto.getFinanceStatus());
			params.put("paymentType", orderFinanceDto.getPaymentType());
			params.put("paymentTotalActualFrom", orderFinanceDto.getPaymentTotalActualFrom());
			params.put("paymentTotalActualTo", orderFinanceDto.getPaymentTotalActualTo());
			params.put("paymemtCompleteTimeFrom", orderFinanceDto.getPaymentCompleteTimeFrom());
			params.put("paymemtCompleteTimeTo", orderFinanceDto.getPaymentCompleteTimeTo());
			page.setParams(params);
			page = orderFinanceService.findOrderFinanceListPage(page);
			model.addAttribute("page", page);
			
			model.addAttribute("paymentStateMap",
					PaymentTypeEnum.getPaymentTypeMap());
			List<OrderFinance> orderFinanceList=(ArrayList<OrderFinance>) page.getResult();
			List<OrderFinanceDto> orderFinanceDtoList=new ArrayList<OrderFinanceDto>();
			for(OrderFinance orderFinance: orderFinanceList){
				OrderFinanceDto dto=new OrderFinanceDto();
				Member member=memberService.findById(orderFinance.getMemberId());
				if(member!=null)
				dto.setMemberName(member.getLoginName());
				dto.setFinanceStatus(orderFinance.getFinanceStatus());
				dto.setId(orderFinance.getId());
				dto.setInvoiceType(orderFinance.getInvoiceType());
				dto.setIsInvoice(orderFinance.getIsInvoice());
				dto.setMemberId(orderFinance.getMemberId());
				dto.setOrderCode(orderFinance.getOrderCode());
				dto.setPaymentCompleteTime(orderFinance.getPaymentCompleteTime());
				dto.setPaymentTotalActual(NumberFormatUtil.numberFormat(orderFinance.getPaymentTotalActual()));
				dto.setPaymentType(orderFinance.getPaymentType());
				orderFinanceDtoList.add(dto);
			}
			model.addAttribute("orderFinanceDtoList",orderFinanceDtoList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("订单财务信息查询失败" + e.getMessage(), e);
		}
		return "models/order/orderFinanceList";
	}

}
