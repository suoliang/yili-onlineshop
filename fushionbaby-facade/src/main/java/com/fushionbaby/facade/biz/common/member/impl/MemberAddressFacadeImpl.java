package com.fushionbaby.facade.biz.common.member.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.MemberAddressDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.facade.biz.common.member.MemberAddressFacade;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAddressService;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.member.service.MemberService;


/**
 * 
 * @description 类描述...
 * @author 孟少博
 * @date 2015年8月24日下午7:39:09
 */
@Service
public class MemberAddressFacadeImpl  implements MemberAddressFacade {

	@Autowired
	private UserFacade userFacade;
	
	@Autowired
	private MemberAddressService<MemberAddress> memberAddressService;
	
	@Autowired
	private MemberService<Member> memberService;
	
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;
	
	
	public List<MemberAddressDto> addressList(String sid) {
		if (StringUtils.isEmpty(sid)) {
			return Collections.emptyList();
		}
		UserDto userDto =  userFacade.getLatestUserBySid(sid);
		long memberId = userDto.getMemberId();
		List<MemberAddress> list = memberAddressService.getListPage(memberId, WebConstant.DEFAULT_PAGE_INDEX, WebConstant.DEFAULT_PAGE_SIZE);
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyList();
		}
		Long defaultAddressId = memberService.findById(memberId).getDefaultAddressId();
		List<MemberAddressDto> addressList = new ArrayList<MemberAddressDto>();
		String provinceName = null;
		String cityName = null;
		String districtName = null;
		for (MemberAddress tmp : list) {
			MemberAddressDto dto = new MemberAddressDto();
			dto.setAddressId(tmp.getId());
			dto.setName(tmp.getContactor());
			dto.setPhone(tmp.getMobile()) ;
			dto.setProvinceId(tmp.getProvince());
			dto.setCityId(tmp.getCity());
			dto.setDistrictId(tmp.getDistrict());
			provinceName = memberAreaService.getNameByAreaCode(dto.getProvinceId());
			cityName = memberAreaService.getNameByAreaCode(dto.getCityId());
			districtName = memberAreaService.getNameByAreaCode(dto.getDistrictId());
			dto.setProvinceName(provinceName);
			dto.setCityName(cityName);
			dto.setDistrictName(districtName);
			dto.setAddressInfo(tmp.getAddress());
			if (ObjectUtils.equals(tmp.getId(), defaultAddressId)) {
				dto.setIsDefault(CommonConstant.YES);
			} else {
				dto.setIsDefault(CommonConstant.NO);
			}
			addressList.add(dto);
		}
		return addressList;
	}

}
