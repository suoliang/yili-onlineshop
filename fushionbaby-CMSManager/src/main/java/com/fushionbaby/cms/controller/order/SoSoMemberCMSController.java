package com.fushionbaby.cms.controller.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cms.dto.SoSoMemberDto;
import com.fushionbaby.common.dto.areaconfig.AreaDto;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.order.model.OrderMemberAddress;
import com.fushionbaby.order.service.OrderMemberAddressService;

/**
 * @author 张明亮
 */
@Controller
@RequestMapping("/order")
public class SoSoMemberCMSController {
	private static final Log LOGGER = LogFactory
			.getLog(SoSoMemberCMSController.class);
	@Autowired
	private OrderMemberAddressService<OrderMemberAddress> orderMemberAddressService;
	
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;
	/**
	 * 根据订单编码,查询出订单的收货地址
	 * @param soCode
	 * @param model
	 */
	@ResponseBody
	@RequestMapping("find_order_address")
	public OrderMemberAddress findBySoCode(
			@RequestParam(value="orderCode",defaultValue="")String orderCode,
			ModelMap model){
		OrderMemberAddress soSoMember = new OrderMemberAddress();
		try{
			soSoMember = (OrderMemberAddress) orderMemberAddressService.findByOrderCode(orderCode);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(soSoMember!=null){
			//去掉时间,这里用不到,会影响json的格式
			soSoMember.setCreateTime(null);
			soSoMember.setVersion(null);
		}
		return soSoMember;
	}
	
	/**
	 * 修改订单收货地址
	 */
	@ResponseBody
	@RequestMapping("edit_order_address")
	public JsonResponseModel editOrderAddress(OrderMemberAddress record){
		JsonResponseModel jsonModel = new JsonResponseModel();
		try{
			orderMemberAddressService.updateByOrderCodeAddress(record);
			jsonModel.setResult(JsonResponseModel.SUCCESS);
			jsonModel.setMsg("修改订单收货地址成功!");
		}catch(Exception e){
			jsonModel.setResult(JsonResponseModel.FAIL);
			jsonModel.setMsg("修改订单收货地址失败!");
			e.printStackTrace();
		}
		return jsonModel;
	}
	
	/**
	 * 根据地区代码,查询该地区下的所有地名,0为顶级代码
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/find_area_list",method=RequestMethod.POST)
	public List<AreaDto> findAreaList(String code){
		List<AreaDto> list=memberAreaService.findByParentAreaCode(code);
		return list;
	}
	
	/**
	 * 设置查询参数
	 */
	private Map<String, String> setParamByMemberDto(SoSoMemberDto soSoMemberDto) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderCode", soSoMemberDto.getOrderCode());
		params.put("receiver", soSoMemberDto.getReceiver());
		params.put("receiverMobile", soSoMemberDto.getReceiverMobile());
		params.put("createTimeFrom", soSoMemberDto.getCreateTimeFrom());
		params.put("createTimeTo", soSoMemberDto.getCreateTimeTo());
		return params;
	}

	@RequestMapping("queryOrderMemberInfoList")
	public String findList(SoSoMemberDto soSoMemberDto, BasePagination page,
			HttpServletRequest request, ModelMap model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = setParamByMemberDto(soSoMemberDto);
			page.setParams(params);
			// 分页对象
			page = orderMemberAddressService.getListPage(page);
			// 分页结果对象
			@SuppressWarnings("unchecked")
			List<OrderMemberAddress> orderMemberAddress = (List<OrderMemberAddress>) page.getResult();

			model.addAttribute("orderMemberAddressList", orderMemberAddress);
			model.addAttribute("page", page);
			model.addAttribute("soSoMemberDto", soSoMemberDto);
			return "models/order/orderMemberInfoList";
		} catch (Exception e) {
			LOGGER.error("会员列表加载失败", e);
			return "";
		}
	}


}
