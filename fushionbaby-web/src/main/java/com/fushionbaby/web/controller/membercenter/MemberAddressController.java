package com.fushionbaby.web.controller.membercenter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.AreaConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.AreaDto;
import com.fushionbaby.common.dto.MemberAddressDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAddressService;
import com.fushionbaby.member.service.MemberAreaConfigService;

/**
 * 用户地址
 * 
 * @author guolongchao
 */
@Controller
@RequestMapping("/address")
public class MemberAddressController {

	@Autowired
	private MemberAddressService<MemberAddress> memberAddressService;
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;

	
	private static final Log logg=LogFactory.getLog(MemberAddressController.class);
	/***
	 * 获取用户的地址列表（分页）
	 * 
	 * @param member_id
	 *            用户id
	 * @return
	 */
	@RequestMapping("/list_address")
	public ModelAndView list(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("person/add_address");
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isEmpty(sid)) {
			return modelAndView;
		}
		UserDto userDto = (UserDto) SessionCache.get(sid);
		long member_id = userDto.getMember_id();

		List<MemberAddress> list = memberAddressService.getListPage(member_id,
				WebConstant.DEFAULT_PAGE_INDEX, WebConstant.DEFAULT_PAGE_SIZE);
		if (CollectionUtils.isEmpty(list)) {
			return modelAndView;
		}
		List<MemberAddressDto> address_list = new ArrayList<MemberAddressDto>();
		String province_name = null;
		String city_name = null;
		String district_name = null;
		for (MemberAddress tmp : list) {
			MemberAddressDto dto = new MemberAddressDto();
			dto.setAddress_id(tmp.getId());
			dto.setName(tmp.getContactor());
			dto.setPhone(tmp.getMobile());
			dto.setProvince_id(tmp.getProvince());
			dto.setCity_id(tmp.getCity());
			dto.setDistrict_id(tmp.getDistrict());
			province_name = memberAreaService.getByAreaCode(dto.getProvince_id());
			city_name = memberAreaService.getByAreaCode(dto.getCity_id());
			district_name = memberAreaService.getByAreaCode(dto.getDistrict_id());
			dto.setProvince_name(province_name);
			dto.setCity_name(city_name);
			dto.setDistrict_name(district_name);
			dto.setAddress_info(tmp.getAddress());
			dto.setIsDefault(tmp.getIsDefault());
			address_list.add(dto);
		}
		modelAndView.addObject("addressList", address_list);
		return modelAndView;
	}

	/***
	 * 添加地址
	 * 
	 * @param member_id
	 *            用户id
	 * @param name
	 *            联系人姓名
	 * @param phone
	 *            联系人电话
	 * @param province_id
	 *            省id
	 * @param city_id
	 *            市id
	 * @param district_id
	 *            区id
	 * @param address
	 *            详细地址 response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Object add(@RequestParam("name") String name, @RequestParam("phone") String phone,
			@RequestParam("province_id") String province_id,
			@RequestParam("city_id") String city_id,
			@RequestParam("district_id") String district_id,
			@RequestParam("address") String address, HttpServletRequest request) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isEmpty(sid)) {
			return Jsonp.noLoginError("用户未登录");
		}
		UserDto userDto = (UserDto) SessionCache.get(sid);
		long member_id = userDto.getMember_id();

		try {
			MemberAddress memberAddr = new MemberAddress();
			memberAddr.setMemberId(member_id);
			memberAddr.setContactor(name);
			memberAddr.setMobile(phone);
			memberAddr.setProvince(province_id);
			memberAddr.setCity(city_id);
			memberAddr.setDistrict(district_id);
			memberAddr.setAddress(address);
			memberAddr.setIsDefault(CommonConstant.NO);

			memberAddressService.add(memberAddr);
			long address_id = memberAddr.getId();
			return Jsonp_data.success(address_id);
		} catch (DataAccessException e) {
			logg.error("会员地址添加失败", e);
			return Jsonp.error("添加地址失败！");
		}
	}

	/***
	 * 修改地址 response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("/change")
	public Object change(@RequestParam("name") String name,
			@RequestParam("address_id") String address_id, @RequestParam("phone") String phone,
			@RequestParam("province_id") String province_id,
			@RequestParam("city_id") String city_id,
			@RequestParam("district_id") String district_id,
			@RequestParam("address") String address, HttpServletRequest request) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isEmpty(sid)) {
			return Jsonp.noLoginError("用户未登录");
		}
		UserDto userDto = (UserDto) SessionCache.get(sid);
		long member_id = userDto.getMember_id();
		try {
			MemberAddress memberAddr = new MemberAddress();
			memberAddr.setId(Long.parseLong(address_id));
			memberAddr.setMemberId(member_id);
			memberAddr.setContactor(name);
			memberAddr.setMobile(phone);
			memberAddr.setProvince(province_id);
			memberAddr.setCity(city_id);
			memberAddr.setDistrict(district_id);
			memberAddr.setAddress(address);

			memberAddressService.update(memberAddr);
			return Jsonp.success();
		} catch (DataAccessException e) {
          logg.error("会员地址修改失败", e);
			return Jsonp.error("修改地址失败！");
		}
	}

	/***
	 * 删除地址 response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("address_id") String address_id, HttpServletRequest request) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isEmpty(sid)) {
			return Jsonp.noLoginError("用户未登录");
		}
		UserDto userDto = (UserDto) SessionCache.get(sid);
		long member_id = userDto.getMember_id();
		try {
			this.memberAddressService.delete(address_id, member_id);
			return Jsonp.success();
		} catch (DataAccessException e) {
			logg.error("会员地址删除失败", e);
			return Jsonp.error("删除地址失败！");
		}
	}

	/**
	 * 获取区域列表(省市区获取都通过此方法)
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/areaList")
	public Object provinceList(@RequestParam("area_id") String area_id) {
		try {
			if (area_id == null || "".equals(area_id)) {
				area_id = AreaConstant.PROVINCE_CODE;
			}
			List<AreaDto> areaList = memberAreaService.findByParentAreaCode(area_id);
			return Jsonp_data.success(areaList);
		} catch (DataAccessException e) {
              logg.error("获取地址区域列表失败", e);
			return Jsonp.error("获取区域列表失败！");
		}
	}

}
