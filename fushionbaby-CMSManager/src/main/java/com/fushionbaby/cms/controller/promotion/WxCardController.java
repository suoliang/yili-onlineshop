package com.fushionbaby.cms.controller.promotion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.log.model.LogWxCardVoucher;
import com.fushionbaby.log.service.LogWxCardVoucherService;

/**
 * @author mengshaobo
 *
 */
@Controller
@RequestMapping("/wxcard")
public class WxCardController {
@Autowired
private LogWxCardVoucherService<LogWxCardVoucher> logWxCardVoucherService;
	
	@RequestMapping("cardList")
	public String cardList(
			@RequestParam( value="orderId",defaultValue="") String orderId,
			@RequestParam(value="reduceCost",defaultValue="") String reduceCost,
			@RequestParam(value="useTimeFrom",defaultValue="") String useTimeFrom,
			@RequestParam(value="useTimeTo",defaultValue="") String useTimeTo,
			BasePagination page,ModelMap model){
		if(page == null){
			page = new BasePagination();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId);
		params.put("reduceCost", reduceCost);
		params.put("useTimeFrom", useTimeFrom);
		params.put("useTimeTo", useTimeTo);
		page.setParams(params);
		page = logWxCardVoucherService.getPageList(page);
		@SuppressWarnings("unchecked")
		List<LogWxCardVoucher> cardList = (List<LogWxCardVoucher>) page.getResult();
		if(CollectionUtils.isEmpty(cardList)){
			cardList = new ArrayList<LogWxCardVoucher>();
		}
		model.addAttribute("cardList", cardList);
		model.addAttribute("orderId", orderId);
		model.addAttribute("reduceCost", reduceCost);
		model.addAttribute("page", page);
		return "/promotion/wxcard/wxcard_list";
	}
}
