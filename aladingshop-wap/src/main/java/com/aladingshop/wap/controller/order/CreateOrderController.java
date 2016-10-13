package com.aladingshop.wap.controller.order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
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
import com.fushionbaby.common.enums.GoOrderPayEnum;
import com.fushionbaby.common.enums.SendDateEnum;
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
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.CreateOrderFacade;
import com.fushionbaby.facade.biz.common.order.OrderMemCenterFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;

@Controller
@RequestMapping("/order")
public class CreateOrderController {
	
	private static final Log LOGGER = LogFactory.getLog(CreateOrderController.class);
	
	@Autowired
	private CreateOrderFacade createOrderFacade;
	@Autowired
	private ShoppingCartQueryFacade cartQueryFacade;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private UserFacade userFacade;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	@Autowired
	private OrderMemCenterFacade orderMemCenterFacade;
	@Autowired
	private ActivityShareMemberService<ActivityShareMember> activityShareMemberService;
	@Autowired
	private ActivityRedEnvlopeUseRecordService<ActivityRedEnvlopeUseRecord> activityRedEnvlopeUseRecordService;
	
	@ResponseBody
	@RequestMapping(value = "createOrder", method = RequestMethod.POST)
	public Object createOrder(String payOffId, String addressId, String isUsePoint, String isUseCoupon,
			String memo,@RequestParam(value="sendDate",defaultValue="") String sendDate,
			@RequestParam(value="isRedUse",defaultValue="n") String isRedUse,HttpServletRequest request) {
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
			orderParam.setSendDate(SendDateEnum.getTitle(sendDate));
			orderParam.setSourceCode(SourceConstant.WAP_CODE);
			orderParam.setAddressId(addressId);
			orderParam.setMemo(memo);
			orderParam.setPayOffId(payOffId);
			orderParam.setIsPoint(isUsePoint);
			orderParam.setIsCardNo(isUseCoupon);
			orderParam.setPointUsd(user.getEpoints() + "");
			String couponCode = (String) DataCache.get(payOffId + SettlementConstant._COUPON_CODE);
			orderParam.setCardNo(couponCode);
			String orderCode = EpointsUtil.generateOrdrNo();
			orderParam.setOrderCode(orderCode);
			orderParam.setMemberId(memberId);

			createOrderFacade.initOrderPriceInOrderParam(orderParam);
			
			BigDecimal useRedAmount = new BigDecimal(0);
			if(StringUtils.equalsIgnoreCase(isRedUse, CommonConstant.YES)){
				useRedAmount = this.useRed(memberId, payoffIdValue);
			}
			orderParam.setRedEnvelopeAmount(useRedAmount);
			BigDecimal totalActual = (BigDecimal) DataCache.get(payoffIdValue + SettlementConstant._TOTAL_ACTUAL);
			orderParam.setTotalActual(totalActual);
			createOrderFacade.saveOrder(orderParam);

			ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
			shoppingCartBo.setUser(user);
			
			List<ShoppingCartSku> cartItemList = createOrderFacade.saveOrderLineByCart(orderParam.getOrderCode(), shoppingCartBo);
			createOrderFacade.updateInventoryByCart(cartItemList);
			createOrderFacade.deleteCartByCart(cartItemList);
			
			createOrderFacade.saveOrderMemberAddress(orderParam);
			memberService.updateDefaultAddressIdByMemberId(memberId, Long.valueOf(addressId));	
			
			if(StringUtils.equalsIgnoreCase(isRedUse, CommonConstant.YES) && useRedAmount!=null && 
					useRedAmount.compareTo(BigDecimal.valueOf(0))>0   ){
				this.redEnvlopeUseRecord(memberId,orderCode,useRedAmount);
			}

			Map<String, Object> result = new HashMap<String, Object>();
			result.put("sid", sid);
			result.put("orderCode", orderCode);
			return Jsonp_data.success(result);
		} catch (NumberFormatException e) {
			LOGGER.error("create订单创建异常:" + e);
			e.printStackTrace();
			return Jsonp.newInstance(GoOrderPayEnum.ORDER_EXCEPTION.getCode(), GoOrderPayEnum.ORDER_EXCEPTION.getMsg());
		}
	}
	
	/**
	 * 使用给红包
	 * @param memberId
	 * @param payoffId
	 * @return 红包使用金额
	 */
	private BigDecimal useRed(Long memberId,String payoffId){
		
		ActivityShareMember	activityShareMember= activityShareMemberService.findByMemberId(memberId);
		if(activityShareMember==null || activityShareMember.getExistingRedEnvelope()==null){
			return BigDecimal.valueOf(0);
		}
		// 订单总计
		BigDecimal totalActual = (BigDecimal) DataCache.get(payoffId + SettlementConstant._TOTAL_ACTUAL);
		
		BigDecimal redAmount = activityShareMember.getExistingRedEnvelope();
		BigDecimal useAmount = redAmount.compareTo(totalActual)>-1?totalActual:redAmount;
		BigDecimal newTotalActual = totalActual.subtract(useAmount);
		BigDecimal balanceAmount = redAmount.subtract(useAmount);
		
		activityShareMember.setExistingRedEnvelope(balanceAmount);
		activityShareMemberService.update(activityShareMember);
		DataCache.put(payoffId + SettlementConstant._TOTAL_ACTUAL,newTotalActual);
		return useAmount;
	}
	/**
	 * 红包使用 记录
	 * @param memberId
	 * @param orderCode
	 * @param redEnvelopeAmount
	 */
	
	private void redEnvlopeUseRecord(Long memberId,String orderCode,BigDecimal redEnvelopeAmount){
		ActivityRedEnvlopeUseRecord activityRedEnvlopeUseRecord = new ActivityRedEnvlopeUseRecord();
		activityRedEnvlopeUseRecord.setMemberId(memberId);
		activityRedEnvlopeUseRecord.setUseStatus(0);
		activityRedEnvlopeUseRecord.setOrderCode(orderCode);
		activityRedEnvlopeUseRecord.setRedEnvelopeAmount(redEnvelopeAmount);
		activityRedEnvlopeUseRecordService.add(activityRedEnvlopeUseRecord);
		
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
	@RequestMapping(value = "goPay")
	public String goPay(String orderCode, Model model,HttpServletRequest request) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
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
		String zfbPay = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.WAPZFBURL).getValue();

		model.addAttribute("sid", sid);
		model.addAttribute("wapZfbPay", zfbPay);

		model.addAttribute("totalActual", NumberFormatUtil.numberFormat(orderFeeUser.getTotalActual()));
		model.addAttribute("orderCode", orderCode);
		
		return "pay/pay";
	}
	
}
