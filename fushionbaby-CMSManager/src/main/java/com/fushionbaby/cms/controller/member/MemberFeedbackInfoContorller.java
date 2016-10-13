package com.fushionbaby.cms.controller.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.MemberFeedbackInfoDto;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrSourceConfig;
import com.fushionbaby.config.service.SysmgrSourceConfigService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberFeedbackInfo;
import com.fushionbaby.member.service.MemberFeedbackInfoService;
import com.fushionbaby.member.service.MemberService;

/**
 * 
 * @author cyla
 * <p>处理用户意见反馈的功能</p>
 * @version 1.0.0
 */
@Controller
@RequestMapping("/feedback")
public class MemberFeedbackInfoContorller extends BaseController{
	
private static final Log LOGGER = LogFactory
			.getLog(MemberFeedbackInfoContorller.class);	
@Autowired
private MemberFeedbackInfoService<MemberFeedbackInfo> memberFeedbackInfoService;
@Autowired
private MemberService<Member> memberService;
/**版本来源*/
@Autowired
private SysmgrSourceConfigService<SysmgrSourceConfig> sysmgrSourceService;
/**
 * 分页查询功能
 * 
 * @param member_id 用户id
 * @param source_code  版本来源编码
 * @param page
 *            分页
 * @return 显示页面
 */
@RequestMapping("/findAll")
public String query(
		@RequestParam(value =  "sourceCode",defaultValue = "") String sourceCode,
		ModelMap modelMap,BasePagination page){
	if (page == null) {
		page = new BasePagination();
	}
	// 分页参数封装
	Map<String, String> params = new HashMap<String, String>();
	params.put("sourceCode", sourceCode);
	page.setParams(params);
	List<SysmgrSourceConfig> sysmgrSourceList = this.sysmgrSourceService
			.findAll();
	modelMap.addAttribute("sysmgrSourceList", sysmgrSourceList);
	modelMap.addAttribute("sourceCode", sourceCode);
	// 分页对象
	page = memberFeedbackInfoService.findMemberFeedbackInfo(page);
	// 分页结果
	@SuppressWarnings("unchecked")
	List<MemberFeedbackInfo> memberFeedbackInfosList = (List<MemberFeedbackInfo>) page.getResult();
	List<MemberFeedbackInfoDto> memberFeedbackInfosDtoList = new ArrayList<MemberFeedbackInfoDto>();
	for(MemberFeedbackInfo memberFeedbackInfo:memberFeedbackInfosList){
		MemberFeedbackInfoDto memberFeedbackInfoDto= new MemberFeedbackInfoDto();
		memberFeedbackInfoDto.setAddTime(memberFeedbackInfo.getAddTime());
		memberFeedbackInfoDto.setContactInformation(memberFeedbackInfo.getContactInformation());
		memberFeedbackInfoDto.setContent(memberFeedbackInfo.getContent());
		memberFeedbackInfoDto.setId(memberFeedbackInfo.getId());
		memberFeedbackInfoDto.setMemberId(memberFeedbackInfo.getMemberId());
		Member member = memberService.findById(memberFeedbackInfo.getMemberId());
		String memberName = "";
		if (ObjectUtils.notEqual(member, null)) {
			memberName = member.getLoginName();
		}
		memberFeedbackInfoDto.setMemberName(memberName);
		memberFeedbackInfoDto.setShowDate(memberFeedbackInfo.getShowDate());
		memberFeedbackInfoDto.setSourceCode(memberFeedbackInfo.getSourceCode());
		memberFeedbackInfosDtoList.add(memberFeedbackInfoDto);
	}
	modelMap.addAttribute("memberFeedbackInfo", memberFeedbackInfosDtoList);
	modelMap.addAttribute("page",page);
	return "models/member/memberFeedbackInfoList";
}

/**
 * 通过id删除意见反馈内容的功能
 * 
 * @param id
 * @return 操作结果
 */
@ResponseBody
@RequestMapping("/deleteById/{id}")
public String deleteMemberFeedbackInfo(
		@PathVariable(value="id") String id) {
	try {
		memberFeedbackInfoService.deleteById(Long.valueOf(id));
	} catch (DataAccessException e) {
		LOGGER.error(e.getMessage());
		return Constant.FAILURE;
	}
	return Constant.SUCCESS;
	
}

}
