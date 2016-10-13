package com.fushionbaby.app.controller.membercenter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.AreaConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.dto.MemberAddressDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.areaconfig.AreaDto;
import com.fushionbaby.common.dto.areaconfig.ProvinceDto;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.member.MemberAddressFacade;
import com.fushionbaby.facade.biz.common.order.GotoOrderFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAddressService;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.member.service.MemberService;

/**
 * 用户地址
 * @author Leon
 */
@Controller
@RequestMapping("/address")
public class MemberAddressController {
	/** 注入日志*/
	private static final Log LOGGER = LogFactory.getLog(MemberAddressController.class);
	@Autowired
	private MemberAddressService<MemberAddress> memberAddressService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;
	@Autowired
	private GotoOrderFacade gotoOrderFacade;
	@Autowired
	private MemberAddressFacade memberAddressFacade;
	/***
	 * 获取用户的地址列表（分页）
	 * @param member_id 用户id
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Object list(@RequestParam(value="sid") String sid) {
		try {
			UserDto userDto = (UserDto) SessionCache.get(sid);
			if (userDto == null) {
				return Jsonp.noLoginError("请先登录");
			}
			List<MemberAddressDto> addressList = memberAddressFacade.addressList(sid);
			return Jsonp_data.success(addressList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("MemberAddressController地址列表加载失败"+e);
			return Jsonp.error("地址列表加载失败");
		}
	}

	/***
	 * 添加地址
	 * @param member_id   用户id
	 * @param name        联系人姓名
	 * @param phone       联系人电话
	 * @param provinceId 省id
	 * @param cityId     市id
	 * @param districtId 区id
	 * @param address     详细地址
	 * response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Object add(
			@RequestParam(value="sid") String sid,
			@RequestParam("name") String name,
			@RequestParam("phone") String phone,
			@RequestParam("provinceId") String provinceId,
			@RequestParam("cityId") String cityId,
			@RequestParam("districtId") String districtId,
			@RequestParam("address") String address,
			@RequestParam("isDefault")String isDefault)  {
		UserDto userDto= (UserDto)SessionCache.get(sid);
		if(userDto == null) {
			return Jsonp.noLoginError("请先登录");
		}
		if (CheckIsEmpty.isEmpty(name,phone,provinceId,cityId,districtId,address,isDefault)) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		Long memberId = userDto.getMemberId();
		try {
			List<MemberAddress> list = memberAddressService.getAddressListByMemberId(memberId);
			if (list.size() > 15) {
				return Jsonp.error("地址达到15个不能添加");
			}
			MemberAddress memberAddr = new MemberAddress();
			memberAddr.setMemberId(memberId);
			memberAddr.setContactor(name);
			memberAddr.setMobile(phone);
			memberAddr.setProvince(provinceId);
			memberAddr.setCity(cityId);
			memberAddr.setDistrict(districtId);
			memberAddr.setAddress(address);
			memberAddressService.add(memberAddr);
			if (StringUtils.equalsIgnoreCase(isDefault, CommonConstant.YES)) {
				memberService.updateDefaultAddressIdByMemberId(memberId,memberAddr.getId());
			}
			return Jsonp_data.success(memberAddr.getId());
		} catch(DataAccessException e) {
			e.printStackTrace();
			LOGGER.error("MemberAddressController添加地址失败"+e);
			return Jsonp.error("添加地址失败！");
		}		
	}
	
	/***
	 * 修改地址
	 * response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("/change")
	public Object change(@RequestParam(value="sid") String sid,
			@RequestParam("name") String name,@RequestParam("addressId") String addressId,
			@RequestParam("phone") String phone,@RequestParam("provinceId") String provinceId,
			@RequestParam("cityId") String cityId,@RequestParam("districtId") String districtId,
			@RequestParam("address") String address,@RequestParam("isDefault")String isDefault) {		
		UserDto userDto= (UserDto)SessionCache.get(sid);
		if(userDto == null) {
			return Jsonp.noLoginError("请先登录");
		}
		if (CheckIsEmpty.isEmpty(addressId,name,phone,provinceId,cityId,districtId,address,isDefault)) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		Long memberId = userDto.getMemberId();
		try {
			MemberAddress memberAddr = new MemberAddress();
			memberAddr.setId(Long.valueOf(addressId));
			memberAddr.setMemberId(memberId);
			memberAddr.setContactor(name);
			memberAddr.setMobile(phone);
			memberAddr.setProvince(provinceId);
			memberAddr.setCity(cityId);
			memberAddr.setDistrict(districtId);
			memberAddr.setAddress(address);
			memberAddressService.update(memberAddr);
			if (StringUtils.equalsIgnoreCase(isDefault, CommonConstant.YES)) {
				memberService.updateDefaultAddressIdByMemberId(memberId,Long.valueOf(addressId));
			}
			return Jsonp.success();
		} catch(DataAccessException e) {
			e.printStackTrace();
			LOGGER.error("MemberAddressController修改地址失败"+e);
			return Jsonp.error("修改地址失败！");
		}		
	}
	
	/***
	 * 删除地址
	 * response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam(value="sid") String sid,
			@RequestParam("addressId") String addressId)  {		
		UserDto userDto= (UserDto)SessionCache.get(sid);
		if(userDto == null) {
			return Jsonp.noLoginError("请先登录");
		}
		if (CheckIsEmpty.isEmpty(addressId)) {
			return Jsonp.paramError(CommonConstant.CommonMessage.PARAM_ERROR_MESSAGE);
		}
		Long memberId = userDto.getMemberId();
		try {
			memberAddressService.delete(Long.valueOf(addressId), memberId);
			List<MemberAddress> list = memberAddressService.getAddressListByMemberId(memberId);
			if (CollectionUtils.isEmpty(list)) {
				memberService.updateDefaultAddressIdByMemberId(memberId, 0L);
			}
			return Jsonp.success();
		} catch(DataAccessException e) {
			e.printStackTrace();
			LOGGER.error("MemberAddressController删除地址失败"+e);
			return Jsonp.error("删除地址失败！");
		}		
	}
	
	/***
	 * 设置为默认地址
	 * response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("/default")
	public Object changeDefault(@RequestParam(value="sid") String sid,
			@RequestParam("addressId") String addressId)  {	
		if (CheckIsEmpty.isEmpty(addressId)) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		UserDto userDto= (UserDto)SessionCache.get(sid);
		if(userDto == null) {
			return Jsonp.noLoginError("请先登录");
		}
		Long memberId = userDto.getMemberId();
		try {
			memberService.updateDefaultAddressIdByMemberId(memberId, Long.valueOf(addressId));
			return Jsonp.success();
		} catch(DataAccessException e) {
			e.printStackTrace();
			LOGGER.error("MemberAddressController设置为默认地址失败"+e);
			return Jsonp.error("设置为默认地址失败！");
		}		
	}
	
	/**
	 * 获取区域列表(省市区获取都通过此方法)
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/areaList")
	public Object provinceList(@RequestParam("areaId") String areaId) {		
		try {
			if(areaId==null||"".equals(areaId)){
				areaId = AreaConstant.PROVINCE_CODE;
			}
			List<AreaDto> areaList = memberAreaService.findByParentAreaCode(areaId);
			return Jsonp_data.success(areaList);
		} catch(DataAccessException e) {
			e.printStackTrace();
			LOGGER.error("MemberAddressController获取区域列表失败"+e);
			return Jsonp.error("获取区域列表失败！");
		}		
	}
	
	/**
	 * 下载地址
	 * @param request
	 * @param response
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("uploadAddress")
	public Object uploadAddress(HttpServletRequest request, HttpServletResponse response){
		
		List<ProvinceDto> provinceList = memberAreaService.findAll();
		
		return Jsonp_data.success(provinceList);
	}
	
}
