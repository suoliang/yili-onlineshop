package com.fushionbaby.cms.controller.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.model.OrderFee;
import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.model.OrderLine;
import com.fushionbaby.order.model.OrderMemberAddress;
import com.fushionbaby.order.model.OrderTrans;
import com.fushionbaby.order.service.OrderBaseService;
import com.fushionbaby.order.service.OrderFeeService;
import com.fushionbaby.order.service.OrderFinanceService;
import com.fushionbaby.order.service.OrderLineService;
import com.fushionbaby.order.service.OrderMemberAddressService;
import com.fushionbaby.order.service.OrderTransService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.fushionbaby.statistics.model.StatisticsOrder;
import com.fushionbaby.statistics.model.StatisticsSkuSell;
import com.fushionbaby.statistics.service.StatisticsOrderService;
import com.fushionbaby.statistics.service.StatisticsSkuSellService;

/**
 * @description 统计订单
 * @author 徐鹏飞
 * @date 2015年7月30日下午6:09:06
 */
@Controller
@RequestMapping("/statisticSku")
public class StatisticsSkuController {
	private static final Log LOGGER = LogFactory
			.getLog(StatisticsSkuController.class);
	@Autowired
	private OrderBaseService<OrderBase> orderBaseService;

	@Autowired
	private OrderTransService<OrderTrans> orderTransService;

	@Autowired
	private OrderLineService<OrderLine> orderLineService;
	
	@Autowired
	private StatisticsSkuSellService<StatisticsSkuSell> statisticsSkuSellService;

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
	
	/**
	 * 销售统计表service
	 */
	@Autowired
	private StatisticsOrderService<StatisticsOrder> statisticsOrderService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statisticsSkuSellList")
	public String queryOrderLineList(StatisticsSkuSell statisticsSkuSell,
			BasePagination page, ModelMap model) {
		try {
			model.put("createTimeFrom", statisticsSkuSell.getCreateTimeFrom());
			model.put("createTimeTo", statisticsSkuSell.getCreateTimeTo());
			model.put("skuNo", statisticsSkuSell.getSkuNo());
			model.put("skuName", statisticsSkuSell.getSkuName());
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("createTimeFrom", statisticsSkuSell.getCreateTimeFrom());
			params.put("createTimeTo", statisticsSkuSell.getCreateTimeTo());
			params.put("skuNo",statisticsSkuSell.getSkuNo());
			String skuNo=statisticsSkuSell.getSkuNo();
			if(StringUtils.isNotBlank(skuNo)){
				Sku skuInfotmp=skuInfoService.queryBySkuNo(skuNo,null);
				if(ObjectUtils.notEqual(null, skuInfotmp)){
					String skuCode=skuInfotmp.getUniqueCode();
					params.put("skuCode",skuCode);
				}else{
					model.addAttribute("page", page);
					model.addAttribute("StatisticsSkuSell", statisticsSkuSell);
					model.addAttribute("StatisticsSkuSellList",new ArrayList<StatisticsSkuSell>());
					return "models/statistics/statisticsSkuSellList";
				}
				
			}
			params.put("skuName",statisticsSkuSell.getSkuName());
			page.setParams(params);
			page = statisticsSkuSellService.getListPage(page);
			model.addAttribute("page", page);
			model.addAttribute("statisticsSkuSell" ,statisticsSkuSell);
			List<StatisticsSkuSell> statisticsSkuSellList=(ArrayList<StatisticsSkuSell>) page.getResult();
			for(StatisticsSkuSell statisticsSkuSelltmp: statisticsSkuSellList){
				Sku skutmp=skuInfoService.queryByUniqueCode(statisticsSkuSelltmp.getSkuCode());
				if(skutmp!=null){
					statisticsSkuSelltmp.setSkuNo(skutmp.getSkuNo());
					statisticsSkuSelltmp.setSkuBarCode(skutmp.getBarCode());
				}
			}
			model.addAttribute("statisticsSkuSellList",statisticsSkuSellList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("商品购买列表查询失败" + e.getMessage(), e);
		}
		return "models/statistics/statisticsSkuSellList";
	}
	
}
