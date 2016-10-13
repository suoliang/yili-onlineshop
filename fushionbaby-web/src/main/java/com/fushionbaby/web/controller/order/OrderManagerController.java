package com.fushionbaby.web.controller.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.OrderDetailsDto;
import com.fushionbaby.common.dto.order.OrderLineDto;
import com.fushionbaby.common.enums.OrderConfigClientEnum;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.SoSalesOrderUser;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.model.OrderMemberUser;
import com.fushionbaby.core.service.SoSalesOrderWebUserService;
import com.fushionbaby.core.service.OrderLineUserService;
import com.fushionbaby.core.service.OrderMemberUserService;
import com.fushionbaby.facade.image.ImageProcess;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.fushionbaby.web.controller.GotoAndCreteOrderCommon;

/***
 * 订单管理，详情
 * @author xupeijun
 *
 */
@RequestMapping("/order")
@Controller
public class OrderManagerController extends GotoAndCreteOrderCommon{

	/** 日志*/
	private static final Log LOGGER = LogFactory.getLog(CreateOrderController.class);
	/**
	 * 订单操作service
	 */
	@Autowired
	private SoSalesOrderWebUserService<SoSalesOrderUser> soSalesOrderWebService;
	/**
	 * 订单地址service
	 */
	@Autowired
	private OrderMemberUserService<OrderMemberUser> soSoMemberWebService;
	/**
	 * 地区字典service
	 */
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;
	/**
	 * 订单行信息查询service
	 */
	@Autowired
	private OrderLineUserService<OrderLineUser> soSoLineService;
	
	/**代金券service*/
	@Autowired
	private ActCardService<ActCard> actCardService;
	
	/**会员service*/
	@Autowired
	private MemberService<Member> memberService;
	
	/**商品库存操作service*/
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	
	/***
	 * 图片处理
	 */
	@Autowired
	private ImageProcess imageProcess;
	
	/***
	 * 订单详情 -- 公共方法
	 * 
	 * @param request
	 * @param order_code
	 * @return
	 */
	public  OrderDetailsDto orderDetailCommon(HttpServletRequest request, String order_code) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		UserDto userDto = (UserDto) SessionCache.get(sid);
		OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
		if (!ObjectUtils.equals(userDto, null)) {
			// 订单
			SoSalesOrderUser order = soSalesOrderWebService.getOrderByMemberIdAndOrderCode(userDto.getMember_id(), order_code);

			orderDetailsDto.setId(order.getId());
			orderDetailsDto.setCode(order.getCode());// 订单号
			orderDetailsDto.setOrder_status(order.getOrderStatus());// 状态
			orderDetailsDto.setOrder_information(OrderConfigClientEnum.parseCode(order.getOrderStatus()));// 订单状态显示信息
			orderDetailsDto.setCreate_time(DateFormat.dateToMinuteString(order.getCreateTime()));
			// 物流
			orderDetailsDto.setTrans_code(StringUtils.isEmpty(order.getTransCode()) ? StringUtils.EMPTY : order.getTransCode());
			orderDetailsDto.setTrans_name(StringUtils.isEmpty(order.getTransName()) ? StringUtils.EMPTY : order.getTransName());

			// 订单客户信息
			OrderMemberUser soSoMember = soSoMemberWebService.getOrderAddress(order.getCode());
			orderDetailsDto.setReceiver(soSoMember.getReceiver());// 收货人
			orderDetailsDto.setReceiver_mobile(soSoMember.getReceiverMobile());// 手机号
			String province = memberAreaService.getByAreaCode(soSoMember.getProvince());
			String city = memberAreaService.getByAreaCode(soSoMember.getCity());
			String district = memberAreaService.getByAreaCode(soSoMember.getDistrict());
			orderDetailsDto.setProvince(province);// 省
			orderDetailsDto.setCity(city);// 市
			orderDetailsDto.setDistrict(district);// 区
			orderDetailsDto.setAddress(soSoMember.getAddress());// 收货地址
			// 发票 抬头-类型
			orderDetailsDto.setInvoice_title(StringUtils.isEmpty(order.getInvoiceTitle()) ? StringUtils.EMPTY : order.getInvoiceTitle());
			orderDetailsDto.setInvoice_type(ObjectUtils.equals(order.getInvoiceType(), null) ? "" : order.getInvoiceType() == 1 ? "个人" : "公司");
			// 订单行信息
			List<OrderLineUser> items = soSoLineService.findByOrderCode(order.getCode(), "");
			List<OrderLineDto> lineItems = new ArrayList<OrderLineDto>();
			if (!CollectionUtils.isEmpty(items)) {
				for (OrderLineUser line : items) {
					OrderLineDto lineDto = new OrderLineDto();
					lineDto.setId(line.getId());
					lineDto.setSku_id(line.getSkuId());
					lineDto.setSku_name(line.getSkuName());// 商品名称
					lineDto.setSku_size(line.getSize());// 商品尺寸
					lineDto.setSku_color(line.getColor());// 商品颜色 rowsPriceTotal
					lineDto.setImg_path(imageProcess.getThumImagePath(line.getSkuCode(), ImageStandardConstant.IMAGE_STANDARD_WEB_75));// 商品图片路径
					lineDto.setQuantity(line.getQuantity());// 数量
					lineDto.setUnit_price(line.getUnitPrice());// 单价
					lineDto.setRowsPriceTotal(line.getUnitPrice().multiply(new BigDecimal(line.getQuantity())));// 总计
					lineDto.setIs_comment(line.getIsComment());
					lineItems.add(lineDto);
				}
				orderDetailsDto.setItems(lineItems);
			}

		}
		return orderDetailsDto;
	}
	
	/**
	 * 订单详情 -- 我的订单
	 * 
	 * @return
	 */
	@RequestMapping("orderDetail")
	public String info(@RequestParam("order_code") String orderCode,
			@RequestParam("time") Long time, HttpServletRequest request,ModelMap model) {
		String forward = "order/order-detail";
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto userDto = userFacade.getLatestUserBySid(sid);
			time = new Date().getTime();// 创建一个时间戳
			if(ObjectUtils.equals(userDto, null)){
				return forward;
			}
			OrderDetailsDto orderDetail = this.orderDetailCommon(request, orderCode);
			model.addAttribute("orderDetailsDto", orderDetail);
			model.addAttribute("time", time);
		} catch (Exception e) {
			LOGGER.error("订单详情出错", e);
		}
		return forward;
	}
	/**
	 * 删除订单 --- 个人中心 -- 我的订单
	 * 
	 * @param member_id
	 * @param order_code
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteOrderById")
	public Object delete(@RequestParam(value = "order_id", defaultValue = "") String orderId, HttpServletRequest request) {
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = userFacade.getLatestUserBySid(sid);
			if (!ObjectUtils.equals(user, null)) {
				Long memberId = user.getMember_id();
				soSalesOrderWebService.deleteOrderById(memberId, Long.valueOf(orderId));
			}
			return Jsonp.success();
		} catch (Exception e) {
		   LOGGER.error("删除订单失败", e);
			return Jsonp.error();
		}
	}
	/**
	 * 确认收货 --- 个人中心 -- 我的订单
	 * 
	 * @param member_id
	 * @param order_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("confirmReceipt")
	public Object confirmReceipt(@RequestParam(value = "order_code", defaultValue = "") String orderCode,
			HttpServletRequest request) {
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto userDto = userFacade.getLatestUserBySid(sid);
			if (!ObjectUtils.equals(userDto, null)) {
				Long member_id = userDto.getMember_id();
				SoSalesOrderUser order = soSalesOrderWebService.getOrderByMemberIdAndOrderCode(userDto.getMember_id(),orderCode);
				if(order!=null && OrderConfigServerEnum.CMS_CONFIRM_RECEIPT.getCode().equals(order.getOrderStatus())){
					soSalesOrderWebService.updateSuccessTransaction(member_id, orderCode);
				}else{
					soSalesOrderWebService.updateWebConfirmReceipt(member_id, orderCode);
				}
			}
			OrderDetailsDto orderDetail = this.orderDetailCommon(request, orderCode);
			return Jsonp_data.success(orderDetail);
		} catch (Exception e) {
			LOGGER.error("确认收货失败", e);
			return Jsonp.error();
		}
	}
	
	/**
	 * 用户取消订单
	 * @return
	 */
	@ResponseBody
	@RequestMapping("cancelOrder")
	public Object userCancelOrder(@RequestParam(value = "order_code", defaultValue = "") String orderCode,
			HttpServletRequest request){
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto userDto = userFacade.getLatestUserBySid(sid);
			if (!ObjectUtils.equals(userDto, null)) {
				Long member_id = userDto.getMember_id();
				SoSalesOrderUser order = soSalesOrderWebService.getOrderByMemberIdAndOrderCode(userDto.getMember_id(),orderCode);
				if(order!=null && OrderConfigServerEnum.WAITING_PAYMENT.getCode().equals(order.getOrderStatus())){
					//查询订单行信息
					List<OrderLineUser> soSoLineList = soSoLineService.findByOrderCode(order.getCode(), "");
					for (OrderLineUser soSoLine : soSoLineList) {
						SkuInventory skuInventory = skuInventoryService.findBySkuCode(soSoLine.getSkuCode());
						if (skuInventory != null) {
							//更新库存量--增加
							int availableQty = skuInventory.getAvailableQty() + soSoLine.getQuantity();
							skuInventoryService.updateInventoryQuantity(soSoLine.getSkuCode(), availableQty);
						}
					}
					//代金券号如果存在，说明使用了代金券
					if (order.getCardno() != null) {
						actCardService.updateBySysByCardNo(order.getCardno());
					}
					Long memberId = order.getMemberId();
					Member member = memberService.findById(memberId);
					//如果用户使用了积分
					if (order.getTotalPointUsed() != null) {
						//用户使用了的积分+会员表里的剩余积分
						int epoints = order.getTotalPointUsed().intValue() + member.getEpoints();
						memberService.updateMemberEpointsById(memberId, epoints);
					}
					if(order.getUseWalletMoney() !=null){
						BigDecimal newAvailableMoney = order.getUseWalletMoney().add(member.getAvailableMoney());
						member.setAvailableMoney(newAvailableMoney);
						memberService.update(member);
					}
					soSalesOrderWebService.updateWebCancel(member_id, orderCode);
				}
			}
			return Jsonp.success();
		} catch (Exception e) {
			LOGGER.error("会员取消订单失败", e);
			return Jsonp.error();
		}
	}
	
}
