package com.aladingshop.web.controller.address;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.MemberAddressDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.areaconfig.ProvinceDto;
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
	/**
	 * 通过地址编号查询地址
	 * @param addressId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findById")
	public Object findById(Long addressId){
		
		MemberAddress address = memberAddressService.findById(addressId);
		return Jsonp_data.success(address);
	}
	/**
	 *  加载所有地址信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("uploadAddress")
	public Object uploadAddress(HttpServletRequest request, HttpServletResponse response){
		
		List<ProvinceDto> provinceList = memberAreaConfigService.findAll();
		
		return Jsonp_data.success(provinceList);
	}
	/**
	 * 编辑设置默认选择的地址
	 * @param addressId 地址编号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editDefaultAddress")
	public Object editDefaultAddress(Long addressId,HttpServletRequest request){
		
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = userFacade.getLatestUserBySid(sid);
			memberService.updateDefaultAddressIdByMemberId(user.getMemberId(), addressId);
		} catch (Exception e) {
			e.printStackTrace();
			return Jsonp.error();
		}
		return Jsonp.success();
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
	/**
	 * 修改地址
	 * @param memberAddress 当前用户的地址信息
	 * @param t 类型 （1:修改地址;0:删除地址）
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="modifyAddress",method=RequestMethod.POST)
	public Object modifyAddress(MemberAddressDto memberAddress,int t,HttpServletRequest request){
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
				if(null == memberAddress.getAddressId()){
					memberAddressService.add(entry);
				}else{
					entry.setId(memberAddress.getAddressId());
					memberAddressService.update(entry);
				}
				if(t==1){
					memberService.updateDefaultAddressIdByMemberId(user.getMemberId(), entry.getId());
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
				Jsonp.error();
			}
		
		return Jsonp.success();
	}
	
	
}
