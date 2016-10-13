package com.aladingshop.wap.controller.membercenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.dto.MemberAddressDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.areaconfig.AreaDto;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
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
 * @date 2015年8月14日下午5:30:50
 */
@RequestMapping("/address")
@Controller
public class AddressController {

	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaConfigService;
	@Autowired
	private MemberAddressFacade memberAddressFacade;
	
	@Autowired
	private MemberAddressService<MemberAddress> memberAddressService;
	
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private UserFacade userFacade;
	
	/**
	 * 查询当前用户下的地址信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String findAddressByMember(HttpServletRequest request,Model model){
		
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		UserDto user = userFacade.getLatestUserBySid(sid);
		if(user == null || user.getMemberId() == null){
			return "redirect:/login/index" ;
		}
		List<MemberAddressDto> addressList = memberAddressFacade.addressList(sid);
		model.addAttribute("addressList", addressList);
		/**我的地址页面选中*/
	    model.addAttribute("marker", "address");
		return "membercenter/address";
	}
	@RequestMapping("toAddAddress")
	public String toAddAddress(){
		return "membercenter/address-add";
	}

	/**
	 * 删除当前用户 指定的地址
	 * @param addressId 地址编号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteAddress")
	public Object deleteAddress(Long addressId,HttpServletRequest request){
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = userFacade.getLatestUserBySid(sid);
			memberAddressService.delete(addressId, user.getMemberId());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return Jsonp.error();
		}
		return Jsonp.success();
	}
	
	@RequestMapping("toModifyAddress")
	public String toModifyAddress(HttpServletRequest request,Long addressId,Model model){
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		UserDto user = userFacade.getLatestUserBySid(sid);
		Long defaultAddressId = memberService.findById(user.getMemberId()).getDefaultAddressId();
		if(user == null || user.getMemberId() == null){
			return "redirect:/login/index" ;
		}
		MemberAddress address = memberAddressService.findById(addressId);
		MemberAddressDto addressDto=new MemberAddressDto();
		String provinceId=address.getProvince();
		String cityId=address.getCity();
		String districtId=address.getDistrict();
		addressDto.setAddressId(addressId);
		addressDto.setProvinceId(provinceId);
		addressDto.setCityId(cityId);
		addressDto.setDistrictId(districtId);
		addressDto.setProvinceName(memberAreaConfigService.getNameByAreaCode(provinceId));
		addressDto.setCityName(memberAreaConfigService.getNameByAreaCode(cityId));
		addressDto.setDistrictName(memberAreaConfigService.getNameByAreaCode(districtId));
		addressDto.setAddressInfo(address.getAddress());
		addressDto.setName(address.getContactor());
		addressDto.setPhone(address.getMobile());
		if (ObjectUtils.equals(address.getId(), defaultAddressId)) {
			addressDto.setIsDefault(CommonConstant.YES);
		} else {
			addressDto.setIsDefault(CommonConstant.NO);
		}
		model.addAttribute("addressDto", addressDto);
		return "membercenter/address-edit";
	}
	
	/**
	 * 修改地址
	 * @param memberAddress 当前用户的地址信息
	 * @param t 类型 （1:修改地址;0:删除地址）
	 * @param request
	 * @return
	 */
	@RequestMapping(value="modifyAddress")
	public String modifyAddress(MemberAddressDto memberAddress,HttpServletRequest request){
		MemberAddress entry  = new MemberAddress();
		entry.setId(memberAddress.getAddressId());
		entry.setProvince(memberAddress.getProvinceId());
		entry.setCity(memberAddress.getCityId());
		entry.setDistrict(memberAddress.getDistrictId());
		entry.setMobile(memberAddress.getPhone());
		entry.setContactor(memberAddress.getName());
		entry.setAddress(memberAddress.getAddressInfo());
		
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = userFacade.getLatestUserBySid(sid);
			entry.setMemberId(user.getMemberId());
			memberAddressService.update(entry);
			if("y".equals(memberAddress.getIsDefault())){
				memberService.updateDefaultAddressIdByMemberId(user.getMemberId(), entry.getId());
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		/**跳转-*/
		String type = request.getParameter("type");
		if ("chooseAddress".equalsIgnoreCase(type)) {
			return "redirect:/address/list?type=chooseAddress";
		}
		return "redirect:/address/list";
	}
	
	/**
	 *  加载地址信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findAddressByParent")
	public Object findAddressByParent(@RequestParam("pCode") String pCode){
		
		List<AreaDto> areaDtoList = memberAreaConfigService.findByParentAreaCode(pCode);
		
		return Jsonp_data.success(areaDtoList);
	}
	
	/**
	 * 添加地址
	 * @param memberAddress 当前用户的地址信息
	 * @param request
	 * @return
	 */

	@RequestMapping(value="addAddress")
	public String AddAddress(MemberAddressDto memberAddress,HttpServletRequest request){
			MemberAddress entry  = new MemberAddress();
			entry.setProvince(memberAddress.getProvinceId());
			entry.setCity(memberAddress.getCityId());
			entry.setDistrict(memberAddress.getDistrictId());
			entry.setMobile(memberAddress.getPhone());
			entry.setContactor(memberAddress.getName());
			entry.setAddress(memberAddress.getAddressInfo());
		
			try {
				String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
				UserDto user = userFacade.getLatestUserBySid(sid);
				entry.setMemberId(user.getMemberId());
				memberAddressService.add(entry);
				if("y".equals(memberAddress.getIsDefault())){
					memberService.updateDefaultAddressIdByMemberId(user.getMemberId(), entry.getId());
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
		String type = request.getParameter("type");
		if ("chooseAddress".equalsIgnoreCase(type)) {
			return "redirect:/address/list?type=chooseAddress";
		}
		return "redirect:/address/list";
	}
	
	/***
	 * 设置为默认地址
	 * response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("/default")
	public Object changeDefault(
			@RequestParam("addressId") String addressId,HttpServletRequest request)  {	
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (CheckIsEmpty.isEmpty(addressId)) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		UserDto userDto = userFacade.getLatestUserBySid(sid);
		if(userDto == null) {
			return Jsonp.noLoginError("请先登录");
		}
		Long memberId = userDto.getMemberId();
		try {
			memberService.updateDefaultAddressIdByMemberId(memberId, Long.valueOf(addressId));
			return Jsonp.success();
		} catch(DataAccessException e) {
			e.printStackTrace();
			return Jsonp.error("设置为默认地址失败！");
		}		
	}
}
