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

import com.aladingshop.alabao.dto.AlabaoRollOffRecordDto;
import com.aladingshop.alabao.model.AlabaoRollOffRecord;
import com.aladingshop.alabao.service.AlabaoRollOffRecordService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;

@Controller
@RequestMapping("/alabaoRollOffRecord")
public class AlabaoRollOffRecordController extends BaseController {

	/** 转出申请*/
	@Autowired
	private AlabaoRollOffRecordService<AlabaoRollOffRecord> alabaoRollOffRecordService;
	
	/** 会员信息*/
	@Autowired
	private MemberService<Member> memberService;
	
	
	private static final Log logger = LogFactory.getLog(AlabaoRollOffRecordController.class);
	
	private static final String PRE_="models/alabao/rollOffRecord/";
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("alabaoRollOffRecordList")
	public String findAll(
			@RequestParam(value = "createTimeFrom", defaultValue = "") String createTimeFrom,
			@RequestParam(value = "createTimeTo", defaultValue = "") String createTimeTo,
			@RequestParam(value = "account", defaultValue = "") String account,
			@RequestParam(value = "rollOffAccountType", defaultValue = "") String rollOffAccountType,
			@RequestParam(value = "serialNum", defaultValue = "") String serialNum,
			@RequestParam(value = "batchNum", defaultValue = "") String batchNum,
			@RequestParam(value = "isSuccess", defaultValue = "") String isSuccess,
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			model.addAttribute("createTimeFrom", createTimeFrom);
			model.addAttribute("createTimeTo", createTimeTo);
			model.addAttribute("account", account);
			model.addAttribute("rollOffAccountType", rollOffAccountType);
			model.addAttribute("serialNum", serialNum);
			model.addAttribute("batchNum", batchNum);
			model.addAttribute("isSuccess", isSuccess);
			Map<String, String> params = new HashMap<String, String>();
			params.put("createTimeFrom", createTimeFrom.trim());
			params.put("createTimeTo", createTimeTo.trim());
			params.put("account", account.trim());
			params.put("rollOffAccountType", rollOffAccountType.trim());
			params.put("serialNum", serialNum.trim());
			params.put("batchNum", batchNum.trim());
			params.put("isSuccess", isSuccess.trim());
			
			page.setParams(params);
			page = this.alabaoRollOffRecordService.getListPage(page);
			List<AlabaoRollOffRecord> listRollOffRecord = (List<AlabaoRollOffRecord>) page.getResult();
			List<AlabaoRollOffRecordDto> listRollOffRecordDto =new ArrayList<AlabaoRollOffRecordDto>();
			for (AlabaoRollOffRecord alabaoRollOffRecord : listRollOffRecord) {
				AlabaoRollOffRecordDto tmpDto= new AlabaoRollOffRecordDto();
				Member member = memberService.findById(alabaoRollOffRecord.getMemberId());
				if(member!=null)
					tmpDto.setMemberName(member.getLoginName());
				tmpDto.setAccount(alabaoRollOffRecord.getAccount());
				tmpDto.setCreateTime(alabaoRollOffRecord.getCreateTime());
				tmpDto.setId(alabaoRollOffRecord.getId());
				tmpDto.setIsSuccess("y".equals(alabaoRollOffRecord.getIsSuccess())?"成功":"失败");
				tmpDto.setMemberId(alabaoRollOffRecord.getMemberId());
				tmpDto.setBatchNum(alabaoRollOffRecord.getBatchNum());
				tmpDto.setMemo(alabaoRollOffRecord.getMemo());
				tmpDto.setRollOffAccountType(alabaoRollOffRecord.getRollOffAccountType());
				tmpDto.setSerialNum(alabaoRollOffRecord.getSerialNum());
				tmpDto.setTransferMoney(alabaoRollOffRecord.getTransferMoney());
				listRollOffRecordDto.add(tmpDto);
			}
			
			model.addAttribute("list", listRollOffRecordDto);
			model.addAttribute("page", page);
			return PRE_+"alabaoRollOffRecordList";
		} catch (Exception e) {
			logger.error("如意消费卡转出金额记录列表查询异常  cms:AlabaoRollOffRecordController.java", e);
			return "";
		}
	}
	
	

}
