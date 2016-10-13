package com.fushionbaby.cms.controller.card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladingshop.card.model.MemberCard;
import com.aladingshop.card.model.MemberCardIncomeRecord;
import com.aladingshop.card.service.MemberCardIncomeRecordService;
import com.aladingshop.card.service.MemberCardService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.MemberCardDto;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;

@Controller
@RequestMapping("memberCardIncomeRecord")
public class MemberCardIncomeRecordController extends BaseController {
	@Autowired
	private MemberCardIncomeRecordService<MemberCardIncomeRecord> memberCardIncomeRecordService;
	
	@Autowired
	private MemberService<Member> memberService;
	
	@Autowired
	private MemberCardService<MemberCard> memberCardService;
	
	private static final Log logger = LogFactory.getLog(MemberCardIncomeRecordController.class);
	
	private static final String PRE_="models/card/incomeRecord/";
	
	@SuppressWarnings("unchecked")
	@RequestMapping("incomeRecordList")
	public String findAll(MemberCardDto memberCardDto,BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("incomeAcount", memberCardDto.getAcount());
			params.put("incomeCardCode", memberCardDto.getCardNo());
			params.put("createTimeFrom", memberCardDto.getCreateTimeFrom());
			params.put("createTimeTo", memberCardDto.getCreateTimeTo());
			page.setParams(params);
			page = this.memberCardIncomeRecordService.getListPage(page);
			List<MemberCardIncomeRecord> listFindAlls = (List<MemberCardIncomeRecord>) page.getResult();
			model.addAttribute("list", listFindAlls);
			model.addAttribute("page", page);
			model.addAttribute("memberCardDto", memberCardDto);
			return PRE_+"incomeRecordList";
		} catch (Exception e) {
			logger.error("cms:MemberCardIncomeRecordController.java 查询阿拉宝收益列表异常", e);
			return "";
		}
	}
}
