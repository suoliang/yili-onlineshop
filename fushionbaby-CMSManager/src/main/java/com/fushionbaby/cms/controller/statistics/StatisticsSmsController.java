package com.fushionbaby.cms.controller.statistics;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.statistics.model.StatisticsSms;
import com.fushionbaby.statistics.service.StatisticsSmsService;

/**
 * @description 统计短信
 * @author 索亮
 * @date 2015年8月3日下午2:52:53
 */
@Controller
@RequestMapping("/statisticSms")
public class StatisticsSmsController {

	/**
	 * 注入短信费用表service
	 */
	@Autowired
	private StatisticsSmsService<StatisticsSms> statisticsSmsService;
	
	@RequestMapping("/staSmsList")
	public String query(
			@RequestParam(value = "createTimeFrom", defaultValue = "") String createTimeFrom,
			@RequestParam(value = "createTimeTo", defaultValue = "") String createTimeTo,
			ModelMap model, BasePagination page){
		
		if (page == null) {
			page = new BasePagination();
		}
		// 分页参数封装
		Map<String, String> params = new HashMap<String, String>();
		params.put("createTimeFrom", createTimeFrom);
		params.put("createTimeTo", createTimeTo);
		page.setParams(params);
		
		// 分页对象
		page = (BasePagination) statisticsSmsService.getListPage(page);
		model.addAttribute("page", page);
		model.addAttribute("createTimeFrom", createTimeFrom);
		model.addAttribute("createTimeTo", createTimeTo);

		return "models/statistics/statisticsSmsList";
		
	}
	
}
