package com.fushionbaby.cms.controller.alabao; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aladingshop.alabao.dto.AlabaoConsumeRecordDto;
import com.aladingshop.alabao.model.AlabaoConsumeRecord;
import com.aladingshop.alabao.service.AlabaoConsumeRecordService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.service.OrderBaseService;

@Controller
@RequestMapping("/alabaoConsumeRecord")
public class AlabaoConsumeRecordController extends BaseController {

	/** 转出申请*/
	@Autowired
	private AlabaoConsumeRecordService<AlabaoConsumeRecord> alabaoConsumeRecordService;
	
	@Autowired
	private OrderBaseService<OrderBase> orderBaseService;
	
	/** 会员信息*/
	@Autowired
	private MemberService<Member> memberService;
	
	
	private static final Log logger = LogFactory.getLog(AlabaoConsumeRecordController.class);
	
	private static final String PRE_="models/alabao/consume/";
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("alabaoConsumeRecordList")
	public String findAll(
			@RequestParam(value = "createTimeFrom", defaultValue = "") String createTimeFrom,
			@RequestParam(value = "createTimeTo", defaultValue = "") String createTimeTo,
			@RequestParam(value = "account", defaultValue = "") String account,
			
			@RequestParam(value = "orderCode", defaultValue = "") String orderCode,
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			model.addAttribute("createTimeFrom", createTimeFrom);
			model.addAttribute("createTimeTo", createTimeTo);
			model.addAttribute("account", account);
			
			model.addAttribute("orderCode", orderCode);
			Map<String, String> params = new HashMap<String, String>();
			params.put("createTimeFrom", createTimeFrom.trim());
			params.put("createTimeTo", createTimeTo.trim());
			params.put("account", account.trim());
			params.put("orderCode", orderCode.trim());
			
			page.setParams(params);
			page = this.alabaoConsumeRecordService.getListPage(page);
			
			String totalIncomeMoney = this.alabaoConsumeRecordService.getListPageMoney(page);
			
			List<AlabaoConsumeRecord> listConsumeRecord = (List<AlabaoConsumeRecord>) page.getResult();
			List<AlabaoConsumeRecordDto> listConsumeRecordDto =new ArrayList<AlabaoConsumeRecordDto>();
			for (AlabaoConsumeRecord alabaoConsumeRecord : listConsumeRecord) {
				AlabaoConsumeRecordDto tmpDto= new AlabaoConsumeRecordDto();
				Member member = memberService.findById(alabaoConsumeRecord.getMemberId());
				if(member!=null)
					tmpDto.setMemberName(member.getLoginName());
				tmpDto.setAccount(alabaoConsumeRecord.getAccount());
				tmpDto.setConsumeMoney(alabaoConsumeRecord.getConsumeMoney());
				tmpDto.setCreateTime(alabaoConsumeRecord.getCreateTime());
				tmpDto.setId(alabaoConsumeRecord.getId());
				tmpDto.setIsSuccess("y".equals(alabaoConsumeRecord.getIsSuccess())?"成功":"失败");
				
				OrderBase orderBase = orderBaseService.findByOrderCode(alabaoConsumeRecord.getOrderCode());
				if (ObjectUtils.notEqual(null, orderBase)) {
					tmpDto.setMemberId(orderBase.getMemberId());
				}
				tmpDto.setOrderCode(alabaoConsumeRecord.getOrderCode());
				listConsumeRecordDto.add(tmpDto);
			}
			
			model.addAttribute("list", listConsumeRecordDto);
			model.addAttribute("page", page);
			model.addAttribute("totalIncomeMoney", totalIncomeMoney);
			return PRE_+"alabaoConsumeRecordList";
		} catch (Exception e) {
			logger.error("如意消费卡消费记录列表查询异常  cms:AlabaoConsumeRecordController.java", e);
			return "";
		}
	}
	
	

}
