package com.fushionbaby.cms.controller.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.order.model.OrderCardRelation;
import com.fushionbaby.order.service.OrderCardRelationService;

/**
 * @author cyla
 * 
 */

@Controller
@RequestMapping("/soOrderCardRelation")
public class SoOrderCardRelationController {
	/**
	 * 订单优惠券service
	 */
	@Autowired
	private OrderCardRelationService soOrderCardRelationService;

	@RequestMapping("/findAll")
	public String query(
			@RequestParam(value = "soCode", defaultValue = "") String soCode,
			@RequestParam(value = "memberName", defaultValue = "") String memberName,
			ModelMap model, BasePagination page) {
		if (page == null) {
			page = new BasePagination();
		}
		// 分页参数封装
		Map<String, String> params = new HashMap<String, String>();
		params.put("soCode", soCode);
		params.put("memberName", memberName);
		page.setParams(params);

		// 分页对象
		page = soOrderCardRelationService.findBySoCode(page);

		// 分页结果
		List<OrderCardRelation> soOrderCardRelations = (List<OrderCardRelation>) page
				.getResult();
		model.addAttribute("soOrderCardRelations", soOrderCardRelations);
		model.addAttribute("soCode", soCode);
		model.addAttribute("memberName", memberName);
		model.addAttribute("page", page);
		return "order/t_so_order_card_relation";

	}
}
