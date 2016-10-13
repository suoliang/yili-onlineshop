package com.fushionbaby.app.controller.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.fushionbaby.act.activity.dto.MemberCouponReqDto;
import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonCode;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.SettlementConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.GotoOrderLineDto;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.common.util.EpointsUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrSfFreightConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.config.service.SysmgrSfFreightConfigService;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartQueryFacade;
import com.fushionbaby.facade.biz.common.coupon.MemberCouponFacade;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.OrderExtraCommonFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.service.SkuExtraInfoService;

/**
 * 
 * @description 类描述...
 * @author 孟少博
 * @date 2016年1月14日下午5:03:04
 */
@RequestMapping("/order")
@Controller
public class OrderSettleController {

	
	private static String PAY_Off_FAILED_ERROR_MSG = "订单结算序列失效!";
	
	
	@Autowired
	private MemberCouponFacade memberCouponFacade;
	
	@Autowired
	private ShoppingCartQueryFacade shoppingCartQueryFacade;
	
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> shoppingCartSkuUserService;
	
	@Autowired
	private ActCardService<ActCard> actCardService;
	
	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	@Autowired
	private SysmgrSfFreightConfigService<SysmgrSfFreightConfig> sysmgrSfFreightService;
	
	@Autowired
	private OrderExtraCommonFacade orderExtraCommonFacade;
	
	@Autowired
	private ActivityShareMemberService<ActivityShareMember> activityShareMemberService;
	
	@Autowired
	private UserFacade userFacade;
	
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	
	@Autowired
	private SkuFacade skuFacade;
	
	/**
	 * 使用红包和优惠券
	 * @param sid
	 * @param payoffId
	 * @param isUseRedEnvelop
	 * @param couponCode
	 * @param isUseCouponCard
	 * @param isUseIntegral
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("useRedAndCoupon")
	public Object useRedAndCoupon(@RequestParam("sid") String sid, @RequestParam("payoffId") String payoffId,
			@RequestParam(value = "isUseRedEnvelop",defaultValue="") String isUseRedEnvelop,
			@RequestParam(value = "couponCode",defaultValue="") String couponCode,
			@RequestParam(value = "isUseCouponCard",defaultValue="") String isUseCouponCard,
			@RequestParam(value = "isUseIntegral",defaultValue="") String isUseIntegral){
		
		UserDto user = (UserDto) SessionCache.get(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.error(CommonMessage.NO_LOGIN);
		}
		final String payoffIdValue = (String) DataCache.get(payoffId);
		if (StringUtils.isBlank(payoffIdValue)) {
			return Jsonp.payOffFailedError(PAY_Off_FAILED_ERROR_MSG);
		}
		Long memberId = user.getMemberId();
		MemberCouponReqDto couponReqDto = null;
		if(StringUtils.isNotBlank(couponCode)){
			couponReqDto = new MemberCouponReqDto();
			couponReqDto.setSid(sid);
			couponReqDto.setPayOffId(payoffId);
			couponReqDto.setMemberId(memberId);
			couponReqDto.setCouponCode(couponCode);
			couponReqDto.setIsUseCouponCard(isUseCouponCard);
		}
		
		BigDecimal redEnvelope  =  (BigDecimal)  DataCache.get(payoffId + SettlementConstant._RED_ENVELOPE);// 红包
		BigDecimal couponAmount = (BigDecimal) DataCache.get(payoffId + SettlementConstant._CARDNO_PRICE);// 优惠券
		BigDecimal integralPrice = (BigDecimal) DataCache.get(payoffId + SettlementConstant._INTEGRAL_PRICE);// 积分价格
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(isUseRedEnvelop) && StringUtils.isBlank(isUseCouponCard) && StringUtils.isBlank(isUseIntegral)){
			this.useRed(memberId, payoffId, isUseRedEnvelop);
			
			if(integralPrice.compareTo(BigDecimal.ZERO) !=0){
				Jsonp pointResult = this.usePoint(sid, payoffId, CommonConstant.YES);
				if(!StringUtils.equals(pointResult.getResponseCode(),"0")){
					return pointResult;
				}
			}
		}
		if(StringUtils.isNotBlank(isUseIntegral) && StringUtils.isBlank(isUseRedEnvelop) && StringUtils.isBlank(isUseCouponCard)){

			Jsonp pointResult = this.usePoint(sid, payoffId, isUseIntegral);
			if(!StringUtils.equals(pointResult.getResponseCode(),"0")){
				return pointResult;
			}
			if(isUseIntegral.equals(CommonConstant.NO) && redEnvelope.compareTo(BigDecimal.ZERO)!=0 ){
				this.useRed(memberId, payoffId, CommonConstant.YES);
			}
		}
		if(StringUtils.isNotBlank(isUseCouponCard) ){
			Jsonp_data couponResult = this.useCoupon(couponReqDto);
			if(!StringUtils.equals(couponResult.getResponseCode(),"0")){
				return couponResult;
			}
			if(StringUtils.isNotBlank(isUseRedEnvelop)){
				this.useRed(memberId, payoffId, isUseRedEnvelop);
			}
		}
		
		BigDecimal skuActualPrice = (BigDecimal) DataCache.get(payoffId + SettlementConstant._SKU_ACTUAL_PRICE);// 商品实收商品金额
		BigDecimal orderTotalActual = (BigDecimal) DataCache.get(payoffId + SettlementConstant._TOTAL_ACTUAL);// 订单总计
		redEnvelope =  (BigDecimal)  DataCache.get(payoffId + SettlementConstant._RED_ENVELOPE);// 红包
		couponAmount = (BigDecimal)  DataCache.get(payoffId + SettlementConstant._CARDNO_PRICE);// 优惠券
		integralPrice =(BigDecimal) DataCache.get(payoffId + SettlementConstant._INTEGRAL_PRICE);// 积分价格
		
		result.put("orderTotalActual", NumberFormatUtil.numberFormat(orderTotalActual) );
		result.put("redEnvelopeUsed", NumberFormatUtil.numberFormat(redEnvelope));
		result.put("couponAmountUsed", NumberFormatUtil.numberFormat(couponAmount));
		result.put("integralPriceUsed", NumberFormatUtil.numberFormat(integralPrice));
		
		result.put("integralPriceCand",this.canUsePoint(sid,payoffId));
		result.put("redEnvelopeCand", NumberFormatUtil.numberFormat(this.getUseRedCand(memberId, skuActualPrice)) );
//		result.put("skuActualPrice", skuActualPrice);
		return Jsonp_data.success(result);
	
	}
	
	
	
	
	public Jsonp usePoint(@RequestParam("sid") String sid, @RequestParam("payoffId") String payoffId,@RequestParam("isUseIntegral") String isUseIntegral){
		
		if (CheckIsEmpty.isEmpty(sid, payoffId)) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		// 判断传的参数是不是y或n
		if (!(StringUtils.equals(isUseIntegral, CommonConstant.YES) || StringUtils.equals(isUseIntegral,
				CommonConstant.NO))) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		// 检查用户是否登陆。
		UserDto user = (UserDto) SessionCache.get(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.error(CommonMessage.NO_LOGIN);
		}
		
		String integralPrice = "0.00";// 积分价格
		
		if (StringUtils.equals(isUseIntegral, CommonConstant.YES)) {
			integralPrice = this.canUsePoint(sid, payoffId);
		}
		
 		DataCache.put(payoffId + SettlementConstant._INTEGRAL_PRICE, new BigDecimal(integralPrice));// 积分
		
		this.getTotalMoney(payoffId);	
		
		return Jsonp.success();
	}
	
	
	public String canUsePoint(String sid,String payoffId){
		
		BigDecimal canEpoints = userFacade.getCanEpoints(userFacade.getLatestUserBySid(sid));
		// 拿到积分
		BigDecimal epoints = CheckObjectIsNull.isNull(canEpoints) ? BigDecimal.valueOf(0) : canEpoints;
		// 换算成金额
		BigDecimal ponintMoney = EpointsUtil.epointsToMoney(epoints);
		BigDecimal skuPrice = (BigDecimal) DataCache.get(payoffId + SettlementConstant._SKU_PRICE);
		BigDecimal couponCardPrice = (BigDecimal) DataCache.get(payoffId + SettlementConstant._CARDNO_PRICE);// 代金券价格
		BigDecimal redEnvelopePrice = (BigDecimal)  DataCache.get(payoffId + SettlementConstant._RED_ENVELOPE);// 红包
		
		BigDecimal  canUsePointMoney = skuPrice.subtract(couponCardPrice).subtract(redEnvelopePrice);
				
		if(canUsePointMoney.compareTo(ponintMoney) ==-1){
			return   NumberFormatUtil.numberFormat( canUsePointMoney);
		} 
		return  NumberFormatUtil.numberFormat(ponintMoney);
	}
	
	
	/**
	 * 能使用的红包数量
	 * @param memberId
	 * @param skuTotalPrice
	 * @return
	 */
	private BigDecimal getUseRedCand(Long memberId, BigDecimal skuTotalPrice){
		ActivityShareMember	activityShareMember= activityShareMemberService.findByMemberId(memberId);
		BigDecimal	redAmount = activityShareMember!=null && activityShareMember.getExistingRedEnvelope()!=null ? activityShareMember.getExistingRedEnvelope() : BigDecimal.ZERO;
		
		BigDecimal useAmout = new BigDecimal(NumberFormatUtil.numberFormat(skuTotalPrice.multiply(BigDecimal.valueOf(0.3)))).setScale(0,BigDecimal.ROUND_HALF_UP);
		
		BigDecimal useAmountActual = redAmount.compareTo(useAmout) > -1 ? useAmout : redAmount;
		
		return useAmountActual.setScale(0,BigDecimal.ROUND_HALF_UP);
		
	}
	
	private void useRed(Long memberId,String payOffId,String isUseRedEnvelop){
		
		BigDecimal skuTotalPrice = (BigDecimal)DataCache.get(payOffId + SettlementConstant._SKU_PRICE);
		
		BigDecimal skuActalPrice = (BigDecimal)DataCache.get(payOffId + SettlementConstant._SKU_ACTUAL_PRICE);
		
		
		ActivityShareMember	activityShareMember= activityShareMemberService.findByMemberId(memberId);
		BigDecimal	redAmount = new BigDecimal(0);
		if(StringUtils.equalsIgnoreCase(isUseRedEnvelop, CommonConstant.YES) && activityShareMember!=null){
			redAmount = activityShareMember.getExistingRedEnvelope();
		}
		BigDecimal useAmout = new BigDecimal(NumberFormatUtil.numberFormat(skuTotalPrice.multiply(BigDecimal.valueOf(0.3)))).setScale(0,BigDecimal.ROUND_HALF_UP);
		
		BigDecimal useAmountActual = redAmount.compareTo(useAmout) > -1 ? useAmout : redAmount;
		
		useAmountActual = useAmountActual.setScale(0,BigDecimal.ROUND_HALF_UP);
		
		BigDecimal redEnvelopeUsed = skuActalPrice.compareTo(useAmountActual) > -1 ? useAmountActual : skuActalPrice;
		
		 
		DataCache.put(payOffId + SettlementConstant._RED_ENVELOPE, redEnvelopeUsed.compareTo(skuActalPrice)==-1?redEnvelopeUsed:skuActalPrice );//使用红包
	
		
		this.getTotalMoney(payOffId);			
		
	}
	
	
	
	private Jsonp_data useCoupon(MemberCouponReqDto couponReqDto){
		
		String payOffId = couponReqDto.getPayOffId();
		
		Jsonp_data jsonp = memberCouponFacade.getCardMoney(couponReqDto);
		if (!StringUtils.equals(CommonCode.SUCCESS_CODE, jsonp.getResponseCode())) {
			return jsonp;
		}
		String cardPriceStr = (String) jsonp.getData();
		BigDecimal cardPrice = StringUtils.isBlank(cardPriceStr) ? BigDecimal.valueOf(0) : new BigDecimal(cardPriceStr);
		DataCache.put(payOffId + SettlementConstant._CARDNO_PRICE, cardPrice);
		
		this.getTotalMoney(payOffId);

		return Jsonp_data.success(true);
	}
	
	
	
	
	private void getTotalMoney(String payOffId) {
		
		BigDecimal skuTotalPrice = (BigDecimal) DataCache.get(payOffId + SettlementConstant._SKU_PRICE);// 商品总额
		BigDecimal orderTotalActual = (BigDecimal) DataCache.get(payOffId + SettlementConstant._TOTAL_ACTUAL);// 订单总计
		BigDecimal freightPrice = (BigDecimal) DataCache.get(payOffId + SettlementConstant._FREIGHT_PRICE);// 运费
		BigDecimal integralPrice = (BigDecimal) DataCache.get(payOffId + SettlementConstant._INTEGRAL_PRICE);// 积分价格
		BigDecimal couponCardPrice = (BigDecimal) DataCache.get(payOffId + SettlementConstant._CARDNO_PRICE);// 代金券价格
		BigDecimal accountBalance = (BigDecimal) DataCache.get(payOffId + SettlementConstant._ACCOUNT_BALANCE);// 礼品卡
		BigDecimal redEnvelopePrice = (BigDecimal)  DataCache.get(payOffId + SettlementConstant._RED_ENVELOPE);// 红包
		
		// 商品实际价格=(商品价格-积分价格-商品优惠券价格-礼品卡金额-红包金额)
		BigDecimal actualPrice = skuTotalPrice.subtract(couponCardPrice).subtract(integralPrice)
				.subtract(redEnvelopePrice).subtract(accountBalance);
				
		
		actualPrice = actualPrice.compareTo(BigDecimal.ZERO)==-1 ? BigDecimal.ZERO : actualPrice;
		// 订单总计价格=实际价格+运费价格
		orderTotalActual = actualPrice.add(freightPrice);
		// 订单总计是否小于0
		if (orderTotalActual.compareTo(new BigDecimal(0)) < 0) {
			orderTotalActual = new BigDecimal(0);
		}
		// 保留两位小数
		String orderPrice = NumberFormatUtil.numberFormat(orderTotalActual);
		DataCache.put(payOffId + SettlementConstant._TOTAL_ACTUAL, new BigDecimal(orderPrice));// 订单最终价格
		DataCache.put(payOffId + SettlementConstant._SKU_ACTUAL_PRICE, actualPrice );// 商品实际总额
	}
	
	
	public List<GotoOrderLineDto> getGotoOrderLineList(Long memberId) {
		List<GotoOrderLineDto> goToOrderLineList = new ArrayList<GotoOrderLineDto>();
		
		ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
		queryCondition.setIsSelected(CommonConstant.YES);
		queryCondition.setMemberId(memberId);
		
		List<ShoppingCartSku> skuCartList = shoppingCartSkuUserService.findByQueryCondition(queryCondition);

		if (CollectionUtils.isEmpty(skuCartList)) {
			return null;
		}
		for (ShoppingCartSku soShoppingCartSkuUser : skuCartList) {
			GotoOrderLineDto goToOrderLineDto = new GotoOrderLineDto();
			goToOrderLineDto.setSkuCode(soShoppingCartSkuUser.getSkuCode());
			
			
			SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(soShoppingCartSkuUser.getSkuCode());
			BigDecimal currentPriceTotal = skuFacade.getCurrentSkuPrice(soShoppingCartSkuUser.getSkuCode(), memberId, skuExtraInfo.getIsMemberSku()).multiply(
					new BigDecimal(soShoppingCartSkuUser.getQuantity()));
			goToOrderLineDto.setCurrentPriceTotal(NumberFormatUtil.numberFormat(currentPriceTotal));
			goToOrderLineList.add(goToOrderLineDto);
		}
		return goToOrderLineList;
	}
	
	
}
