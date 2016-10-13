package com.fushionbaby.cms.controller.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.model.LotteryDetail;
import com.fushionbaby.sysactivity.service.LotterDetailService;

/**
 * @author cyla
 * 
 */
@Controller
@RequestMapping("/lotteryDetails")
public class LotteryDetailController {
	@Autowired
	private LotterDetailService<LotteryDetail> lotterDetailService;
	@RequestMapping("/findAll")
	@SuppressWarnings("unchecked")
	public String query(
			@RequestParam(value = "loginName", defaultValue = "") String loginName,
			@RequestParam(value = "code", defaultValue = "") String code,
			ModelMap model, BasePagination page) {
		if (page == null) {
			page = new BasePagination();
		}
		// 分页参数封装
		Map<String, String> params = new HashMap<String, String>();
		params.put("loginName", loginName);
		params.put("code", code);
		page.setParams(params);
		// 分页对象
		page = (BasePagination) lotterDetailService.findByLoginName(page);
		// 分页结果
		List<LotteryDetail> lotteryDetails = (List<LotteryDetail>) page.getResult();
		model.addAttribute("lotteryDetails", lotteryDetails);
		model.addAttribute("page", page);
		model.addAttribute("loginName", loginName);
		model.addAttribute("code", code);

		return "sysmgr/t_lottery_details";
	}
	
}