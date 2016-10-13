package com.fushionbaby.cms.controller.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.cms.dto.MemberAddressDto;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAddressService;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.member.service.MemberService;

/**
 * @author cyla
 * 
 */

@Controller
@RequestMapping("/memberAddress")
public class MemberAdressController {
	/**
	 * 注入会员地址service
	 */
	@Autowired
	private MemberAddressService<MemberAddress> memberAddressService;
	@Autowired
	private MemberService<Member> memberService;
	/** 注入地区 */
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;
	/**记录日志*/
	private static final Log logger = LogFactory.getLog(MemberAddress.class);

	@SuppressWarnings("unchecked")
	@RequestMapping("/findAll")
	public String query(
			@RequestParam(value = "contactor", defaultValue = "") String contactor,
			@RequestParam(value = "province", defaultValue = "") String province,
			@RequestParam(value = "mobile",defaultValue = "") String mobile,
			@RequestParam(value = "createTimeFrom", defaultValue = "") String createTimeFrom,
			@RequestParam(value = "createTimeTo", defaultValue = "") String createTimeTo,
			ModelMap model, BasePagination page) {

		if (page == null) {
			page = new BasePagination();
		}

		// 分页参数封装
		Map<String, String> params = new HashMap<String, String>();
		params.put("contactor", contactor);
		params.put("province", province);
		params.put("mobile", mobile);
		params.put("createTimeFrom", createTimeFrom);
		params.put("createTimeTo", createTimeTo);
		page.setParams(params);

		// 分页对象
		page = (BasePagination) memberAddressService.findByContactor(page);

		// 分页结果
		List<MemberAddress> memberAddress = (List<MemberAddress>) page.getResult();

		model.addAttribute("memberAddress", memberAddress);
		model.addAttribute("page", page);
		model.addAttribute("contactor", contactor);
		model.put("province", province);
		model.put("mobile", mobile );
		model.put("createTimeFrom", createTimeFrom);
		model.put("createTimeTo", createTimeTo);
		return "models/member/memberAddressList";

	}

	@RequestMapping("memberReceiveAddressList")
	public String memberAddressList(@RequestParam("memberId") Long memberId,
			ModelMap model) {
		try {
			List<MemberAddress> memberAddressList=memberAddressService.getAddressListByMemberId(memberId);
			List<MemberAddressDto> memberAddressDtoList=new ArrayList<MemberAddressDto>();
			for(MemberAddress memberAddress: memberAddressList){
				MemberAddressDto memberAddressDto=new MemberAddressDto();
				memberAddressDto.setProvince(memberAreaService.getNameByAreaCode(memberAddress.getProvince()));
				memberAddressDto.setCity(memberAreaService.getNameByAreaCode(memberAddress.getCity()));
				memberAddressDto.setDistrict(memberAreaService.getNameByAreaCode(memberAddress.getDistrict()));
				memberAddressDto.setAddress(memberAddress.getAddress());
				memberAddressDto.setMobile(memberAddress.getMobile());
				memberAddressDto.setContactor(memberAddress.getContactor());
				memberAddressDto.setId(memberAddress.getId());
				memberAddressDtoList.add(memberAddressDto);
			}
			model.addAttribute("memberAddressDtoList", memberAddressDtoList);
			
			Member member=memberService.findById(memberId);
			Long defaultAddressId=member.getDefaultAddressId();
			model.addAttribute("defaultAddressId",defaultAddressId);
			
			return "models/member/memberReceiveAddressList";
		} catch (Exception e) {
			logger.error("会员地址列表加载失败", e);
			return "";
		}
	}
}
