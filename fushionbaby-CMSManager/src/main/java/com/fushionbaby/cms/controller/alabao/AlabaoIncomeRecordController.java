package com.fushionbaby.cms.controller.alabao; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aladingshop.alabao.dto.AlabaoIncomeRecordDto;
import com.aladingshop.alabao.model.AlabaoIncomeRecord;
import com.aladingshop.alabao.service.AlabaoIncomeRecordService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;

@Controller
@RequestMapping("/alabaoIncomeRecord")
public class AlabaoIncomeRecordController extends BaseController {

	/** 转出申请*/
	@Autowired
	private AlabaoIncomeRecordService<AlabaoIncomeRecord> alabaoIncomeRecordService;
	
	/** 会员信息*/
	@Autowired
	private MemberService<Member> memberService;
	
	
	private static final Log logger = LogFactory.getLog(AlabaoIncomeRecordController.class);
	
	private static final String PRE_="models/alabao/income/";
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("alabaoIncomeRecordList")
	public String findAll(
			@RequestParam(value = "createTimeFrom", defaultValue = "") String createTimeFrom,
			@RequestParam(value = "createTimeTo", defaultValue = "") String createTimeTo,
			@RequestParam(value = "account", defaultValue = "") String account,
			
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			model.addAttribute("createTimeFrom", createTimeFrom);
			model.addAttribute("createTimeTo", createTimeTo);
			model.addAttribute("account", account);
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("createTimeFrom", createTimeFrom.trim());
			params.put("createTimeTo", createTimeTo.trim());
			params.put("account", account.trim());
		
			
			page.setParams(params);
			page = this.alabaoIncomeRecordService.getListPage(page);
			String totalIncomeMoney = this.alabaoIncomeRecordService.getListPageMoney(page);
			List<AlabaoIncomeRecord> listIncomeRecord = (List<AlabaoIncomeRecord>) page.getResult();
			List<AlabaoIncomeRecordDto> listIncomeRecordDto =new ArrayList<AlabaoIncomeRecordDto>();
			for (AlabaoIncomeRecord alabaoIncomeRecord : listIncomeRecord) {
				AlabaoIncomeRecordDto tmpDto= new AlabaoIncomeRecordDto();
				Member member = memberService.findById(alabaoIncomeRecord.getMemberId());
				if(member!=null)
				
				tmpDto.setAccount(alabaoIncomeRecord.getAccount());
				tmpDto.setIncomeMoney(alabaoIncomeRecord.getIncomeMoney());
				tmpDto.setBfIncomeMoney(alabaoIncomeRecord.getBfIncomeMoney());
				tmpDto.setAfIncomeMoney(alabaoIncomeRecord.getAfIncomeMoney());
				tmpDto.setCreateTime(alabaoIncomeRecord.getCreateTime());
				tmpDto.setId(alabaoIncomeRecord.getId());
				tmpDto.setMemberId(alabaoIncomeRecord.getMemberId());
				
				listIncomeRecordDto.add(tmpDto);
				
			}
			
			model.addAttribute("list", listIncomeRecordDto);
			model.addAttribute("page", page);
			model.addAttribute("totalIncomeMoney", totalIncomeMoney);
			System.out.println();
			return PRE_+"alabaoIncomeRecordList";
		} catch (Exception e) {
			logger.error("如意消费卡收益记录列表查询异常  cms:AlabaoIncomeRecordController.java", e);
			return "";
		}
	}
	
	

}
