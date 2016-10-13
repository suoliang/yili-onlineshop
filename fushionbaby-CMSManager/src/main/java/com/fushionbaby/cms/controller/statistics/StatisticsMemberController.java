package com.fushionbaby.cms.controller.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.statistics.model.StatisticsMember;
import com.fushionbaby.statistics.service.StatisticsMemberService;

/**
 * @description 统计会员
 * @author 索亮
 * @date 2015年7月30日下午3:22:36
 */
@Controller
@RequestMapping("/statisticMember")
public class StatisticsMemberController {
	@Autowired
	private StatisticsMemberService<StatisticsMember> statisticsMemberService;
	
	@RequestMapping("/sMemList")
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
		page = statisticsMemberService.getPageList(page);
		model.addAttribute("page", page);
		List<StatisticsMember> statisticsMemberList=statisticsMemberService.getListStatisticsUse((HashMap<String, String>) params);
		Long totalNumber=statisticsMemberList.get(0).getTotalNumber();
		Long totalAndroidCode=(long) 0;
		Long totalIosCode=(long) 0;
		Long totalWebCode=(long) 0;
		Long totalCmsCode=(long) 0;
		Long totalDefaultChannel=(long) 0;
		Long totalQqChannel=(long) 0;
		Long totalWxChannel=(long) 0;
		Long totalSinaChannel=(long) 0;
		for(StatisticsMember  statisticsMember : statisticsMemberList){
			totalAndroidCode+=Long.parseLong(statisticsMember.getAndroidCode());
			totalIosCode+=Long.parseLong(statisticsMember.getIosCode());
			totalWebCode+=Long.parseLong(statisticsMember.getWebCode());
			totalCmsCode+=Long.parseLong(statisticsMember.getCmsCode());
			totalDefaultChannel+=Long.parseLong(statisticsMember.getDefaultChannel());
			totalQqChannel+=Long.parseLong(statisticsMember.getQqChannel());
			totalWxChannel+=Long.parseLong(statisticsMember.getWxChannel());
			totalSinaChannel+=Long.parseLong(statisticsMember.getSinaChannel());
		}
		model.addAttribute("totalNumber", totalNumber);
		model.addAttribute("totalAndroidCode", totalAndroidCode);
		model.addAttribute("totalIosCode", totalIosCode);
		model.addAttribute("totalWebCode", totalWebCode);
		model.addAttribute("totalCmsCode", totalCmsCode);
		model.addAttribute("totalDefaultChannel", totalDefaultChannel);
		model.addAttribute("totalQqChannel", totalQqChannel);
		model.addAttribute("totalWxChannel", totalWxChannel);
		model.addAttribute("totalSinaChannel", totalSinaChannel);
		
		model.addAttribute("createTimeFrom", createTimeFrom);
		model.addAttribute("createTimeTo", createTimeTo);
		return "models/statistics/statisticsMemberList";
		
	}
	
}
