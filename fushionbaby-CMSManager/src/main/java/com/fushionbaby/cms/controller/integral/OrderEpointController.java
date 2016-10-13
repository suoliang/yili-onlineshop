package com.fushionbaby.cms.controller.integral;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cms.controller.order.excel.MemberExcelReport;
import com.fushionbaby.cms.dto.IntegralDto;
import com.fushionbaby.common.enums.OrderEpoinEnum;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.dto.MemberDto;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.order.model.OrderEpoint;
import com.fushionbaby.order.service.OrderEpointService;


@Controller
@RequestMapping("/integral")
public class OrderEpointController {
	
	/** 记录日志 */
	private static final Log LOGGER = LogFactory.getLog(Member.class);
	
	@Autowired
	private OrderEpointService<OrderEpoint> orderEpointService;
	
	/**设置查询参数*/
	private Map<String, String> setParamByDto(IntegralDto integralDto) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("skuCode", integralDto.getSkuCode());
		params.put("skuName", integralDto.getSkuName());
		params.put("sourceCode", integralDto.getSourceCode());
		params.put("createTimeFrom", integralDto.getCreateTimeFrom());
		params.put("createTimeTo", integralDto.getCreateTimeTo());
		return params;
	}
	
	@RequestMapping("integralDeleteList")
	public String findDeleteList(IntegralDto integralDto, BasePagination page,
			HttpServletRequest request, ModelMap model){
		try {
			BasePagination basePage = new BasePagination();
			if (ObjectUtils.notEqual(page, null)) {
				basePage = page;
			}
			Map<String, String> params = setParamByDto(integralDto);
			basePage.setParams(params);
			// 分页对象
			BasePagination pageResult = orderEpointService.getListPage(page);
			model.addAttribute("page", pageResult);
			model.addAttribute("integralDto", integralDto);
			return  "models/epoint/deleteList";
			
		} catch (Exception e) {
			LOGGER.error("积分删除列表加载失败", e);
			return "";
		}
		
	}
	
	/**删除*/
	@ResponseBody
	@RequestMapping("deleteIntegral")
	public String delete(
			@RequestParam("memberId") Long memberId){
	try {
		if(memberId !=0 ){
			orderEpointService.deleteById(memberId);
		}
		return "success";
	} catch (Exception e) {
		LOGGER.error("积分删除失败", e);
		return "error";
	}
}	
	/**
	 * 分页
	 */
	@RequestMapping("epointOrderList")
	public String findList(IntegralDto integralDto, BasePagination page,
			HttpServletRequest request, ModelMap model) {
		try {
			BasePagination basePage = new BasePagination();
			if (ObjectUtils.notEqual(page, null)) {
				basePage = page;
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("skuCode", integralDto.getSkuCode());
			params.put("skuName", integralDto.getSkuName());
			params.put("sourceCode", integralDto.getSourceCode());
			params.put("createTimeFrom", integralDto.getCreateTimeFrom());
			params.put("createTimeTo", integralDto.getCreateTimeTo());
			basePage.setParams(params);
			// 分页对象
			BasePagination pageResult = orderEpointService.getListPage(page);
			// 分页结果对象
			List<OrderEpoint> orderEpointList = (List<OrderEpoint>) pageResult.getResult();		
			model.addAttribute("orderEpointList", orderEpointList);
			model.addAttribute("page", pageResult);
			model.addAttribute("integralDto", integralDto);
			
			Map<String, String> sourceMap = OrderEpoinEnum.getOrderConfigServerMap();
			model.addAttribute("sourceMap", sourceMap);
			return "models/epoint/integerList";
		} catch (Exception e) {
			LOGGER.error("积分删除列表加载失败", e);
			return "";
		}
	}
	/**
	 * 导出积分列表
	 */
	@RequestMapping("export_excel_integral_list")
	public void exportExcelOrderList(IntegralDto integralDto,
			HttpServletResponse response, BasePagination page) {
		try {
			Map<String, String> params = setParamByDto(integralDto);
			Map<String, Object> map = new HashMap<String, Object>();
			map.putAll(params);
			List<OrderEpoint> orderEpointList = orderEpointService.getListPage(map);
			ExcelReport report = new ExcelReport();
			report.getReport("积分列表Excel", orderEpointList, response);
		} catch (Exception e) {
			LOGGER.error("积分列表导出Excel失败", e);
		}
	}
	
	
	
}
