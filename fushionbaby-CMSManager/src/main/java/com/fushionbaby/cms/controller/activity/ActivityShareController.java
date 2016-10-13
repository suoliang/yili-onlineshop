/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月16日下午3:54:45
 */
package com.fushionbaby.cms.controller.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.model.ActivityShareRegisterRecord;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.aladingshop.periodactivity.service.ActivityShareRegisterRecordService;
import com.fushionbaby.cms.dto.acticity.ActivityShareMemberDto;
import com.fushionbaby.cms.dto.acticity.ActivityShareRedEnvlopeUseRecordDto;
import com.fushionbaby.cms.dto.acticity.ActivityShareRegisterDto;
import com.fushionbaby.common.enums.RedEnvelopeUseStatusEnum;
import com.fushionbaby.common.enums.RedEnvelopeUseTypeEnum;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月16日下午3:54:45
 */
@Controller
@RequestMapping("/activityShare")
public class ActivityShareController {
	
	
	@Autowired
	private MemberService<Member> memberService;

	@Autowired
	private ActivityShareMemberService<ActivityShareMember> activityShareMemberService;
	@Autowired
	private ActivityShareRegisterRecordService<ActivityShareRegisterRecord> activityShareRegisterRecordService;
	@Autowired
	private ActivityRedEnvlopeUseRecordService<ActivityRedEnvlopeUseRecord> activityRedEnvlopeUseRecordService;
	
	/**
	 * 分享赚红包用户列表
	 * @param memberDto 用户信息
	 * @param flag 是否显示没有邀请码的
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/memberList")
	public String activityShareMemberList(ActivityShareMemberDto memberDto,
			@RequestParam(value="sortAttr",defaultValue="") String sortAttr,
			@RequestParam(value="sortType",defaultValue="") String sortType,
			@RequestParam(value="flag",defaultValue="") String flag,BasePagination page,Model model){
		
		BasePagination basePage = new BasePagination();
		if (ObjectUtils.notEqual(page, null)) {
			basePage = page;
		}
		Map<String, String> params = new HashMap<String, String>();
		if(memberDto.getMemberId()!=null){
			params.put("memberId", StringUtils.trimToEmpty(memberDto.getMemberId()+"") );
		}
		
		if(StringUtils.isNotBlank(memberDto.getMemberName())){
			Member member = memberService.findByLoginName(memberDto.getMemberName());
			if(member!=null){
				params.put("memberId",member.getId()+"");
			}
		}
		
		params.put("inviteCode", StringUtils.trimToEmpty(memberDto.getInviteCode()));
		if(StringUtils.isBlank(memberDto.getInviteCode())){
			params.put("flag", StringUtils.trimToEmpty(flag));
		}
		params.put("sortAttr", sortAttr);
		params.put("sortType", sortType);
		
		basePage.setParams(params);
		BasePagination pageResult = activityShareMemberService.getListPage(basePage);
		
		@SuppressWarnings("unchecked")
		List<ActivityShareMember> memberList = (List<ActivityShareMember>)pageResult.getResult();
		
		List<ActivityShareMemberDto> memberDtos = new ArrayList<ActivityShareMemberDto>();
		for (ActivityShareMember member : memberList) {
			ActivityShareMemberDto dto = new ActivityShareMemberDto();
			dto.setMemberId(member.getMemberId());
			Member memberEntry = memberService.findById(member.getMemberId());
			String memberName = memberEntry!=null&& memberEntry.getLoginName()!=null  ? memberEntry.getLoginName() : StringUtils.EMPTY;
			dto.setMemberName(memberName);
			dto.setInviteCode(member.getInviteCode());
			dto.setExistingRedEnvelope(member.getExistingRedEnvelope());
			dto.setGrandGainRedEnvelope(member.getGrandGainRedEnvelope());
			dto.setCreateTime(member.getCreateTime());
			dto.setUpdateTime(member.getUpdateTime());
			memberDtos.add(dto);
		}
		
		model.addAttribute("sortAttr", sortAttr);
		model.addAttribute("sortType", sortType);
		model.addAttribute("memberDto", memberDto);
		model.addAttribute("flag", flag);
		model.addAttribute("page", pageResult);
		model.addAttribute("memberDtos", memberDtos);
		
		return "models/activity/activityShareMemberList";
	}
	
	
	
	/**
	 * 通过邀请码注册用户的信息列表
	 * @param registerDto 分享注册信息
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("registerList")
	public String registerList(ActivityShareRegisterDto registerDto,
			@RequestParam(value="sortAttr",defaultValue="") String sortAttr,
			@RequestParam(value="sortType",defaultValue="") String sortType,
			BasePagination page,Model model){
		
		BasePagination basePage = new BasePagination();
		if (ObjectUtils.notEqual(page, null)) {
			basePage = page;
		}
		Map<String, String> params = new HashMap<String, String>();
		
		List<Long> memberShareIdList = new ArrayList<Long>();
		List<Long> memberRegisterIdList = new ArrayList<Long>();
		
		if(registerDto.getMemberShareId()!=null){
			memberShareIdList.add(registerDto.getMemberShareId());
		}
		if(registerDto.getMemberRegisterId()!=null){
			memberRegisterIdList.add(registerDto.getMemberRegisterId());
		}
		if(StringUtils.isNotBlank(registerDto.getMemberShareName())){
			Member member = memberService.findByLoginName(registerDto.getMemberShareName());
			if(member!=null && StringUtils.isNotBlank(member.getLoginName())){
				memberShareIdList.add(member.getId());
			}
			
		}
		if(StringUtils.isNotBlank(registerDto.getMemberRegisterName())){
			Member member = memberService.findByLoginName(registerDto.getMemberRegisterName());
			if(member!=null && StringUtils.isNotBlank(member.getLoginName())){
				memberRegisterIdList.add(member.getId());
			}
		}
		if(StringUtils.isNotBlank(registerDto.getInviteCode())){
			ActivityShareMember shareMember = activityShareMemberService.findByInviteCode(registerDto.getInviteCode());
			if(shareMember!=null && shareMember.getMemberId()!=null){
				memberShareIdList.add(shareMember.getMemberId());
			}
		}
		if(memberShareIdList!=null && memberShareIdList.size()>0){
			params.put("memberShareIdList",  StringUtils.join(memberShareIdList, ","));
		}
		if(memberRegisterIdList!=null && memberRegisterIdList.size()>0){
			params.put("memberRegisterIdList",  StringUtils.join(memberRegisterIdList, ","));
		}
		
		if(registerDto!=null && StringUtils.isNotBlank(registerDto.getCreateStartTime())){
			params.put("createStartTime",registerDto.getCreateStartTime() + " 00:00:00");
		}
		if(registerDto!=null && StringUtils.isNotBlank(registerDto.getCreateEndTime())){
			params.put("createEndTime",  registerDto.getCreateEndTime() + " 23:59:59");
		}
		if(registerDto!=null && StringUtils.isNotBlank(registerDto.getMemberRegisterPwd())){
			params.put("memberRegisterPwd",  registerDto.getMemberRegisterPwd());
		}
		
		params.put("sortAttr", sortAttr);
		params.put("sortType", sortType);	
		
		basePage.setParams(params);
		BasePagination pageResult = activityShareRegisterRecordService.getListPage(basePage);
		@SuppressWarnings("unchecked")
		List<ActivityShareRegisterRecord> registerRecordList = (List<ActivityShareRegisterRecord>)pageResult.getResult();
		List<ActivityShareRegisterDto> registerDtoList = new ArrayList<ActivityShareRegisterDto>();
		
		for (ActivityShareRegisterRecord activityShareRegisterRecord : registerRecordList) {
			ActivityShareRegisterDto shareRegisterDto = new ActivityShareRegisterDto();
			
			
			shareRegisterDto.setMemberShareId(activityShareRegisterRecord.getMemberSharesId());
			shareRegisterDto.setMemberRegisterId(activityShareRegisterRecord.getMemberRegisterId());
			shareRegisterDto.setGainRedEnvelope(activityShareRegisterRecord.getGainRedEnvelope());
			shareRegisterDto.setCreateTime(activityShareRegisterRecord.getCreateTime());
			ActivityShareMember activitySharemMember = activityShareMemberService.findByMemberId(activityShareRegisterRecord.getMemberSharesId());
			if(activitySharemMember!=null && activitySharemMember.getInviteCode()!=null){
				shareRegisterDto.setInviteCode(activitySharemMember.getInviteCode());
			}
			Member shareMember = memberService.findById(activityShareRegisterRecord.getMemberSharesId());
			if(shareMember!=null && StringUtils.isNotBlank(shareMember.getLoginName())){
				shareRegisterDto.setMemberShareName(shareMember.getLoginName());
			}
			Member registerMember = memberService.findById(activityShareRegisterRecord.getMemberRegisterId());
			if(registerMember!=null && StringUtils.isNotBlank(registerMember.getLoginName())){
				shareRegisterDto.setMemberRegisterName(registerMember.getLoginName());
				shareRegisterDto.setMemberRegisterPwd(registerMember.getPassword());
			}
			
			registerDtoList.add(shareRegisterDto);
			
		}
		model.addAttribute("registerDto", registerDto);
		model.addAttribute("page", pageResult);
		model.addAttribute("registerDtoList", registerDtoList);
		
		return "models/activity/activityShareRegisterRecord";
	}
	/**
	 * 使用红包记录
	 * @param recordDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("redEnvelopeList")
	public String redEnvelopeList(ActivityShareRedEnvlopeUseRecordDto recordDto,
			@RequestParam(value="sortAttr",defaultValue="") String sortAttr,
			@RequestParam(value="sortType",defaultValue="") String sortType,
			BasePagination page,Model model){
		
		BasePagination basePage = new BasePagination();
		if (ObjectUtils.notEqual(page, null)) {
			basePage = page;
		}
		Map<String, String> params = new HashMap<String, String>();
		if(recordDto.getMemberId()!=null){
			params.put("memberId", recordDto.getMemberId()+"");
		}
		if(StringUtils.isNotBlank(recordDto.getOrderCode())){
			params.put("orderCode",recordDto.getOrderCode());
		}
		if(StringUtils.isNotBlank(recordDto.getUseStatus())){
			params.put("useStatus",recordDto.getUseStatus());
		}
		if(StringUtils.isNotBlank(recordDto.getMemberName())){
			Member member = memberService.findByLoginName(recordDto.getMemberName());
			if(member!=null){
				params.put("memberId",member.getId()+"");
			}
		}
		
		if(recordDto!=null && StringUtils.isNotBlank(recordDto.getCreateStartTime())){
			params.put("createStartTime",recordDto.getCreateStartTime() + " 00:00:00");
		}
		if(recordDto!=null && StringUtils.isNotBlank(recordDto.getCreateEndTime())){
			params.put("createEndTime",  recordDto.getCreateEndTime() + " 23:59:59");
		}
		params.put("sortAttr", sortAttr);
		params.put("sortType", sortType);
		basePage.setParams(params);
		BasePagination pageResult = activityRedEnvlopeUseRecordService.getListPage(basePage);
		
		@SuppressWarnings("unchecked")
		List<ActivityRedEnvlopeUseRecord> registerRecordList = (List<ActivityRedEnvlopeUseRecord>)pageResult.getResult();
		List<ActivityShareRedEnvlopeUseRecordDto> redEnvlopeUseRecordDtoList = new ArrayList<ActivityShareRedEnvlopeUseRecordDto>();
		
		for (ActivityRedEnvlopeUseRecord redEnvlopeUseRecord : registerRecordList) {
			ActivityShareRedEnvlopeUseRecordDto redEnvlopeUseRecordDto = new ActivityShareRedEnvlopeUseRecordDto();
			
			redEnvlopeUseRecordDto.setMemberId(redEnvlopeUseRecord.getMemberId());
			redEnvlopeUseRecordDto.setOrderCode(redEnvlopeUseRecord.getOrderCode());
			redEnvlopeUseRecordDto.setRedEnvelopeAmount(redEnvlopeUseRecord.getRedEnvelopeAmount());
			redEnvlopeUseRecordDto.setUseStatus(RedEnvelopeUseStatusEnum.getTitle(redEnvlopeUseRecord.getUseStatus()+""));
			redEnvlopeUseRecordDto.setType(RedEnvelopeUseTypeEnum.getTitle(redEnvlopeUseRecord.getType()+""));
			if(StringUtils.isBlank(redEnvlopeUseRecordDto.getType())){
				if( StringUtils.isNotBlank(redEnvlopeUseRecord.getOrderCode())){
					redEnvlopeUseRecordDto.setType(RedEnvelopeUseTypeEnum.CONSUME.getMsg());
				}else{
					redEnvlopeUseRecordDto.setType(RedEnvelopeUseTypeEnum.TRANSFER_ACCOUNTS.getMsg());
				}
			}
			
			Member member = memberService.findById(redEnvlopeUseRecord.getMemberId());
			if(member!=null && StringUtils.isNotBlank(member.getLoginName())){
				redEnvlopeUseRecordDto.setMemberName(member.getLoginName());
			}
			redEnvlopeUseRecordDto.setCreateTime(redEnvlopeUseRecord.getCreateTime());
			redEnvlopeUseRecordDtoList.add(redEnvlopeUseRecordDto);
			
		}
		
		model.addAttribute("sortAttr", sortAttr);
		model.addAttribute("sortType", sortType);
		model.addAttribute("typeMaps", RedEnvelopeUseTypeEnum.getAll());
		model.addAttribute("statusMaps", RedEnvelopeUseStatusEnum.getAll());
		model.addAttribute("recordDto", recordDto);
		model.addAttribute("page", pageResult);
		model.addAttribute("registerRecordList",redEnvlopeUseRecordDtoList);
		
		BigDecimal recordRedCount = this.recordRedCount(params,pageResult.getTotal());
		
		model.addAttribute("recordRedCount", recordRedCount);
		
		return "models/activity/activityRedEnvelopeuUseRecord";
	}
	
	@SuppressWarnings("unchecked")
	private BigDecimal recordRedCount(Map<String, String> params,Integer recordCount){
		BasePagination basePage = new BasePagination();
		basePage.setParams(params);
		basePage.setLimit(recordCount);
		BasePagination pageResult = activityRedEnvlopeUseRecordService.getListPage(basePage);
		
		BigDecimal count = new BigDecimal(0);
		
		for (ActivityRedEnvlopeUseRecord record : (List<ActivityRedEnvlopeUseRecord>)pageResult.getResult()) {
			count = count.add(record.getRedEnvelopeAmount());
		}
		
		return count;
	}
}
