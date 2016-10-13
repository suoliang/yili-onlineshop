package com.aladingshop.web.controller.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonCode;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SettlementConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartItemDto;
import com.fushionbaby.common.dto.order.GotoOrderDto;
import com.fushionbaby.common.enums.GoOrderPayEnum;
import com.fushionbaby.common.enums.ShoppingCartCaptChaEnum;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.EpointsUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.vo.OrderParamUser;
import com.fushionbaby.core.vo.OrderUser;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartQueryFacade;
import com.fushionbaby.facade.biz.common.member.MemberAddressFacade;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.CreateOrderFacade;
import com.fushionbaby.facade.biz.common.order.GotoOrderFacade;
import com.fushionbaby.facade.biz.common.order.OrderExtraCommonFacade;
import com.fushionbaby.facade.biz.common.order.OrderMemCenterFacade;
import com.fushionbaby.facade.biz.common.yiduobao.YiDuoBaoCardFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.service.SkuService;

/**
 * 
 * @description 去订单
 * @author 孟少博
 * @date 2015年8月14日下午1:06:12
 */
@RequestMapping("/order")
@Controller
public class GoOrderController {

	private static final Log logg = LogFactory.getLog(GoOrderController.class);

	@Autowired
	private GotoOrderFacade gotoOrderFacade;

	@Autowired
	protected UserFacade userFacade;

	@Autowired
	private ShoppingCartQueryFacade cartQueryFacade;
	@Autowired
	private SkuService skuService;

	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;

	@Autowired
	private CreateOrderFacade createOrderFacade;

	@Autowired
	private OrderExtraCommonFacade orderExtraCommonFacade;

	@Autowired
	private MemberService<Member> memberService;

	@Autowired
	private OrderMemCenterFacade orderMemCenterFacade;

	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;

	@Autowired
	private MemberAddressFacade memberAddressFacade;

	@Autowired
	private YiDuoBaoCardFacade yiDuoBaoCardFacade;

	/**
	 * 去结算页前验证
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "goOrderCheck", method = RequestMethod.POST)
	public Object goOrderCheck(HttpServletRequest request) {

		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);

		boolean checkUserLogin = userFacade.checkUserLogin(sid);

		if (!checkUserLogin) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		UserDto user = userFacade.getLatestUserBySid(sid);
		ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
		queryCondition.setIsSelected(CommonConstant.YES);
		queryCondition.setMemberId(user.getMemberId());
		List<CartItemDto> itemList = cartQueryFacade.findSelectedCartSkuByMemberId(queryCondition);
		if (ObjectUtils.equals(null, itemList)) {
			String resultCode = ShoppingCartCaptChaEnum.SHOPPING_CART_SELECTED_FALSE.getCode();
			return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
		}
		List<CartItemDto> notExistSkuList = new ArrayList<CartItemDto>();
		for (CartItemDto itemDto : itemList) {
			Sku skuEntry = skuService.queryBySkuCode(itemDto.getSkuCode());// 根据商品code拿到对应的商品
			if (skuEntry == null) {
				CartItemDto item = new CartItemDto();
				item.setSkuCode(itemDto.getSkuCode());
				item.setName(itemDto.getName());
				notExistSkuList.add(item);
			}
		}
		if (!CollectionUtils.isEmpty(notExistSkuList) && notExistSkuList.size() > 0) {
			String code = ShoppingCartCaptChaEnum.SKU_NO.getCode();
			return Jsonp_data.newInstance(code, notExistSkuList, ShoppingCartCaptChaEnum.getTitle(code));
		}
		ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
		shoppingCartBo.setUser(user);
		Jsonp checkSkuInventory = createOrderFacade.checkSkuInventory(shoppingCartBo);
		if (!StringUtils.equals(checkSkuInventory.getResponseCode(), CommonCode.SUCCESS_CODE)) {
			return checkSkuInventory;
		}

		return Jsonp.success();
	}

	@RequestMapping("goCartConfirm")
	public Object goOrder(HttpServletRequest request, Model model) {

		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isBlank(sid)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		UserDto user = userFacade.getLatestUserBySid(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}

		GotoOrderDto gotoOrderDto = new GotoOrderDto();
		ShoppingCartBo ShoppingCartBo = new ShoppingCartBo();
		ShoppingCartBo.setUser(user);

		gotoOrderDto = gotoOrderFacade.initGotoOrderDto(ShoppingCartBo);

		if (CollectionUtils.isEmpty(gotoOrderDto.getOrderLineItems()) || gotoOrderDto.getOrderLineItems().size() <= 0) {

			return "redirect:/cart/list.html";
		}

		model.addAttribute("sid", sid);
		model.addAttribute("user", user);
		model.addAttribute("gotoOrderDto", gotoOrderDto);
		model.addAttribute("addressList", memberAddressFacade.addressList(sid));

		return "cart/cart-confirm";

	}

	@ResponseBody
	@RequestMapping(value = "createOrder", method = RequestMethod.POST)
	public Object createOrder(String payOffId, String addressId, String isUsePoint, String isUseCoupon, String payWay,
			String memo, String inerestCard, String rebateCard, HttpServletRequest request) {
		final String payoffIdValue = (String) DataCache.get(payOffId);
		if (StringUtils.isBlank(payoffIdValue)) {
			return Jsonp.newInstance(GoOrderPayEnum.PAY_Off_FAILED_ERROR_MSG.getCode(),
					GoOrderPayEnum.PAY_Off_FAILED_ERROR_MSG.getMsg());
		}
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isBlank(sid)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}

		UserDto user = userFacade.getLatestUserBySid(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		if (StringUtils.isBlank(addressId)) {
			return Jsonp.newInstance(GoOrderPayEnum.ADDRESS_ISNULL.getCode(), GoOrderPayEnum.ADDRESS_ISNULL.getMsg());
		}
		ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
		queryCondition.setIsSelected(CommonConstant.YES);
		queryCondition.setMemberId(user.getMemberId());
		
		List<CartItemDto> cartItemDtos = cartQueryFacade.findSelectedCartSkuByMemberId(queryCondition);
		if (CollectionUtils.isEmpty(cartItemDtos) || cartItemDtos.size() < 1) {
			return Jsonp.newInstance(GoOrderPayEnum.SELECTED_SKU_ISNULL.getCode(),
					GoOrderPayEnum.SELECTED_SKU_ISNULL.getMsg());
		}

		try {
			Long memberId = user.getMemberId();
			
			ShoppingCartBo ShoppingCartBo = new ShoppingCartBo();
			ShoppingCartBo.setUser(user);
			
			Jsonp checkSkuInventory = createOrderFacade.checkSkuInventory(ShoppingCartBo);
			if (!StringUtils.equals(checkSkuInventory.getResponseCode(), CommonCode.SUCCESS_CODE)) {
				return checkSkuInventory;
			}
			OrderParamUser orderParam = new OrderParamUser();
			orderParam.setSourceCode(SourceConstant.WEB_CODE);
			orderParam.setAddressId(addressId);
			orderParam.setMemo(memo);
			orderParam.setPayOffId(payOffId);
			orderParam.setPaymentType(payWay);
			orderParam.setIsPoint(isUsePoint);
			orderParam.setIsCardNo(isUseCoupon);
			orderParam.setPointUsd(user.getEpoints() + "");
			String couponCode = (String) DataCache.get(payOffId + SettlementConstant._COUPON_CODE);
			orderParam.setCardNo(couponCode);
			String orderCode = EpointsUtil.generateOrdrNo();
			orderParam.setOrderCode(orderCode);
			orderParam.setMemberId(memberId);

			createOrderFacade.initOrderPriceInOrderParam(orderParam);
			createOrderFacade.saveOrder(orderParam);

			ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
			shoppingCartBo.setUser(user);
			
			List<ShoppingCartSku> cartItemList = createOrderFacade.saveOrderLineByCart(orderParam.getOrderCode(),
					shoppingCartBo);
			createOrderFacade.updateInventoryByCart(cartItemList);
			createOrderFacade.deleteCartByCart(cartItemList);

			createOrderFacade.saveOrderMemberAddress(orderParam);
			memberService.updateDefaultAddressIdByMemberId(memberId, Long.valueOf(addressId));

			Map<String, Object> result = new HashMap<String, Object>();
			result.put("sid", sid);
			result.put("payWay", payWay);
			result.put("orderCode", orderCode);
			return Jsonp_data.success(result);
		} catch (NumberFormatException e) {
			logg.error("create订单创建异常:" + e);
			e.printStackTrace();
			return Jsonp.newInstance(GoOrderPayEnum.ORDER_EXCEPTION.getCode(), GoOrderPayEnum.ORDER_EXCEPTION.getMsg());
		}
	}

	/**
	 * 去支付页
	 * 
	 * @param sid
	 * @param orderCode
	 *            订单编号
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "goPay", method = RequestMethod.POST)
	public String goPay(String sid, String payWay, String orderCode, Model model) {
		UserDto user = userFacade.getLatestUserBySid(sid);
		if (ObjectUtils.equals(null, user)) {
			return "redirect:/login/index";
		}

		OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(user.getMemberId(), orderCode);
		if (ObjectUtils.equals(null, orderFeeUser) || ObjectUtils.equals(null, orderFeeUser.getCreateTime())) {
			return "redirect:/cart/list";
		}
		OrderUser orderUser = orderMemCenterFacade.getOrderInfo(user.getMemberId(), orderCode);
		if (ObjectUtils.equals(null, orderUser)) {
			return "redirect:/cart/list";
		}
		if (orderFeeUser.getTotalActual().compareTo(BigDecimal.ZERO) == 0) {
			return "redirect:/order/orderList?orderStatus=0";
		}
		String zfbPay = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYZFBJSDZURL).getValue();
		String wxPay = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYWEBWXURL).getValue();
		String ylPay = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYYLURL).getValue();

		model.addAttribute("sid", sid);
		model.addAttribute("payWay", payWay);
		model.addAttribute("zfbPay", zfbPay);
		model.addAttribute("wxPay", wxPay);
		model.addAttribute("ylPay", ylPay);

		model.addAttribute("createOrderTime",
				DateFormatUtils.format(orderFeeUser.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		model.addAttribute("totalActual", NumberFormatUtil.numberFormat(orderFeeUser.getTotalActual()));
		model.addAttribute("orderCode", orderCode);
		model.addAttribute("epointMoney", ObjectUtils.defaultIfNull(orderUser.getEpointsMoney(), "0.00"));
		model.addAttribute("couponMoney", ObjectUtils.defaultIfNull(orderUser.getCardAmount(), "0.00"));
		model.addAttribute("transMoney", ObjectUtils.defaultIfNull(orderUser.getActualTransferFee(), "0.00"));
		return "cart/cart-pay";
	}

	/**
	 * 选择微信支付时跳转到微信支付页
	 * 
	 * @param sid
	 * @param orderCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "gotoWXPay", method = RequestMethod.POST)
	public String gotoWXPay(String sid, String orderCode, Model model) {
		UserDto user = userFacade.getLatestUserBySid(sid);
		if (ObjectUtils.equals(null, user)) {
			return "redirect:/login/index";
		}

		OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(user.getMemberId(), orderCode);
		if (ObjectUtils.equals(null, orderFeeUser) || ObjectUtils.equals(null, orderFeeUser.getCreateTime())) {
			return "redirect:/cart/list";
		}
		OrderUser orderUser = orderMemCenterFacade.getOrderInfo(user.getMemberId(), orderCode);
		if (ObjectUtils.equals(null, orderUser)) {
			return "redirect:/cart/list";
		}
		if (orderFeeUser.getTotalActual().compareTo(BigDecimal.ZERO) == 0) {
			return "redirect:/order/orderList?orderStatus=0";
		}
		String wxPay = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYWEBWXURL).getValue();
		model.addAttribute("sid", sid);
		model.addAttribute("wxPay", wxPay);
		model.addAttribute("orderCode", orderCode);
		model.addAttribute("orderUser", orderUser);
		return "pay/pay-weichat";
	}
}
