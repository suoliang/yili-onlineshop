package com.fushionbaby.cms.controller.alabao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.AlabaoShiftToRecordDto;
import com.fushionbaby.common.enums.AlabaoTransferTypeEnum;
import com.fushionbaby.common.util.BasePagination;

@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/alabaoShiftToRecord")
public class AlabaoShiftToRecordController extends BaseController {
	
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	
	private static final Log logger = LogFactory.getLog(AlabaoShiftToRecordController.class);
	private static final String PRE_="/models/alabao/shiftRecord/";
	
	@RequestMapping("alabaoShiftToRecordList")
	public String findAll(AlabaoShiftToRecordDto alabaoShiftToRecordDto, BasePagination page,ModelMap model){
		
		try {
			if(null == page){
				//初始化分页查询对象
				page = new BasePagination();
				}
			//将查询条件封装到一个map中，将该map值赋给分页查询page的条件参数
			Map<String,String> params = this.setParamByAlabaoShiftToRecordDto(alabaoShiftToRecordDto);
			page.setParams(params);
			
			//得到分页查询的结果
			page = alabaoShiftToRecordService.getListPage(page);
			
			List<AlabaoShiftToRecord> alabaoShiftToRecordList = (List<AlabaoShiftToRecord>) page.getResult();
			
			//分页查询结果发生到页面
			model.addAttribute("page", page);
			model.addAttribute("alabaoShiftToRecordList", alabaoShiftToRecordList);
			//将枚举转换方式放入map中，加载到页面上
			model.addAttribute("AlabaoTransferTypeMap", AlabaoTransferTypeEnum.getAlabaoTransferTypeMap());
			model.addAttribute("alabaoShiftToRecordDto", alabaoShiftToRecordDto);
			
			return PRE_ + "alabaoShiftRecordList";
		} catch (Exception e) {
			logger.error("查询账户转入记录错误！", e);
		}
		return "";
	}

	private Map<String, String> setParamByAlabaoShiftToRecordDto(
			AlabaoShiftToRecordDto alabaoShiftToRecordDto) {
		Map<String, String> searchCondition = new HashMap<String, String>();
		searchCondition.put("account", alabaoShiftToRecordDto.getAccount());
		searchCondition.put("batchNum", alabaoShiftToRecordDto.getBatchNum());
		searchCondition.put("createTimeFrom", alabaoShiftToRecordDto.getCreateTimeFrom());
		searchCondition.put("createTimeTo", alabaoShiftToRecordDto.getCreateTimeTo());
		return searchCondition;
	}
}
