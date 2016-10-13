package com.fushionbaby.facade.biz.common.order.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.CardTypeConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonCode;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.SettlementConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.CouponCardDto;
import com.fushionbaby.common.dto.order.GotoOrderLineDto;
import com.fushionbaby.common.dto.order.OrderExtraDto;
import com.fushionbaby.common.enums.GoOrderPayEnum;
import com.fushionbaby.common.enums.UseCouponEnum;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.common.util.EpointsUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.StringTools;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.model.SysmgrSfFreightConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.config.service.SysmgrSfFreightConfigService;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.OrderExtraCommonFacade;
import com.fushionbaby.facade.biz.common.order.dto.OrderExtraCommonDto;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.sku.model.SkuBrandRelation;
import com.fushionbaby.sku.model.SkuCategoryRelation;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.service.SkuBrandRelationService;
import com.fushionbaby.sku.service.SkuCategoryRelationService;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuLabelRelationService;
import com.fushionbaby.sku.service.SkuService;

@Service
public class OrderExtraCommonFacadeImpl implements OrderExtraCommonFacade {
	/** 全局变量配置service */
	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	/** 顺丰快递运费service */
	@Autowired
	private SysmgrSfFreightConfigService<SysmgrSfFreightConfig> sysmgrSfFreightService;
	/** 注入购物车行记录 */
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> shoppingCartSkuUserService;
	@Autowired
	private ActCardService<ActCard> actCardService;
	@Autowired
	private SkuBrandRelationService<SkuBrandRelation> skuBrandRelationService;
	@Autowired
	private SkuCategoryRelationService<SkuCategoryRelation> skuCategoryRelationService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private UserFacade userFacade;
	
	@Autowired
	private SkuFacade skuFacade;
	
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo>  skuExtraInfoService;
	
	@Autowired
	private ActivityShareMemberService<ActivityShareMember> activityShareMemberService;

	private static final String PAY_Off_FAILED_ERROR_MSG = "订单结算序列失效!";
	// private static final String CARDNO_ERROR_MSG_WX= "代金券号不存在或已被使用或不满足使用条件!";
	// private static final String CARDNO_ERROR_MSG_WX= "此微信卡券不可用";
	/**
	 * 商品Lable Relation
	 */
	@Autowired
	private SkuLabelRelationService skuLabelRelationService;

	public Object getFreight(OrderExtraCommonDto freightDto) {
		String payoffId = freightDto.getPayOffId();
		if (ObjectUtils.notEqual(null, checkPayoffId(payoffId))) {
			return checkPayoffId(payoffId);
		}
		String skuTotalPrice = (String) DataCache.get(payoffId + SettlementConstant._SKU_PRICE).toString();// 商品总计
		String freightPrice = "0.00";// （运费价格）
		SysmgrGlobalConfig sysmgrGlobalConfig = sysmgrGlobalConfigService
				.findByCode(GlobalConfigConstant.FREE_FREIGHT_CODE);
		if (ObjectUtils.equals(null, sysmgrGlobalConfig)) {
			return Jsonp.error("没有此配置信息!");
		}
		BigDecimal freeFreight = new BigDecimal(sysmgrGlobalConfig.getValue());// 拿到配置表的value值
		// 小于0表示前面数小后面数大--收运费
		if (new BigDecimal(skuTotalPrice).compareTo(freeFreight) < 0) {
			SysmgrSfFreightConfig sysmgrSfFreight = sysmgrSfFreightService.findByAreaCode(freightDto.getAreaCode());
			if (ObjectUtils.notEqual(null, sysmgrSfFreight)) {
				freightPrice = NumberFormatUtil.numberFormat(sysmgrSfFreight.getAmount());
			} else {
				return Jsonp.error("没有此区域的运费信息!");
			}
		}
		DataCache.put(payoffId + SettlementConstant._FREIGHT_PRICE, new BigDecimal(freightPrice));
		String totalActual = getTotalMoney(freightDto.getPayOffId());// 计算总金额
		OrderExtraDto orderExtraDto = new OrderExtraDto();
		orderExtraDto.setFreightPrice(freightPrice);
		orderExtraDto.setTotalActual(totalActual);
		return Jsonp_data.success(orderExtraDto);
	}

	public Object useAccountBalance(OrderExtraCommonDto giftCardDto) {
		String payoffId = giftCardDto.getPayOffId();
		if (ObjectUtils.notEqual(null, this.checkPayoffId(payoffId))) {
			return this.checkPayoffId(payoffId);
		}
		if (ObjectUtils.equals(null, giftCardDto)) {
			Jsonp.error(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		BigDecimal accountBalance = StringUtils.isBlank(giftCardDto.getUseAccountMoney()) ? BigDecimal.valueOf(0)
				: new BigDecimal(giftCardDto.getUseAccountMoney());
		DataCache.put(payoffId + SettlementConstant._ACCOUNT_BALANCE, accountBalance);

		String freightPrice = getFreightMoney(payoffId, giftCardDto.getAreaCode());// 计算后的运费
		// String taxPrice =
		// getTaxMoney(payoffId,giftCardDto.getIsUseTax());//计算税费
		String totalActual = this.getTotalMoney(payoffId);// 计算总金额
		OrderExtraDto orderExtraDto = new OrderExtraDto();
		orderExtraDto.setGiftCardMoney(accountBalance.toString());
		orderExtraDto.setFreightPrice(freightPrice);
		// orderExtraDto.setTaxPrice(taxPrice);
		orderExtraDto.setTotalActual(totalActual);
		return Jsonp_data.success(orderExtraDto);
	}

	public Object getIntegral(OrderExtraCommonDto integralDto) {
		final String payoffId = integralDto.getPayOffId();
		if (ObjectUtils.notEqual(null, checkPayoffId(payoffId))) {
			return checkPayoffId(payoffId);
		}
		String integralPrice = "0.00";// 积分价格

//		if (StringUtils.equals(integralDto.getIsUsePoint(), CommonConstant.YES)) {
//			BigDecimal canEpoints = userFacade.getCanEpoints(userFacade.getLatestUserBySid(integralDto.getSid()));
//			// 拿到积分
//			BigDecimal epoints = CheckObjectIsNull.isNull(canEpoints) ? BigDecimal.valueOf(0) : canEpoints;
//			// 换算成金额
//			BigDecimal price = EpointsUtil.epointsToMoney(epoints);
//			// 四舍五入保留两位小数
//			integralPrice = NumberFormatUtil.numberFormat(price);
//			
//		}
		integralPrice = this.canUsePoint(integralDto);
		DataCache.put(payoffId + SettlementConstant._INTEGRAL_PRICE, new BigDecimal(integralPrice));// 积分
		//String freightPrice = getFreightMoney(payoffId, integralDto.getAreaCode());// 计算后的运费
		String totalActual = this.getTotalMoney(payoffId);// 计算总金额
		OrderExtraDto orderExtraDto = new OrderExtraDto();
		orderExtraDto.setIntegralPrice(integralPrice);// 积分价格
	//	orderExtraDto.setFreightPrice(freightPrice);
		orderExtraDto.setTotalActual(totalActual); 
		return Jsonp_data.success(orderExtraDto);

	}
	
	public String canUsePoint(OrderExtraCommonDto integralDto){
		String integralPrice= "0.00";
		if (!StringUtils.equals(integralDto.getIsUsePoint(), CommonConstant.YES)) {
			return integralPrice;
		}
		
		final String payoffId = integralDto.getPayOffId();
		
		BigDecimal canEpoints = userFacade.getCanEpoints(userFacade.getLatestUserBySid(integralDto.getSid()));
		// 拿到积分
		BigDecimal epoints = CheckObjectIsNull.isNull(canEpoints) ? BigDecimal.valueOf(0) : canEpoints;
		// 换算成金额
		BigDecimal ponintMoney = EpointsUtil.epointsToMoney(epoints);
		BigDecimal totalActual = (BigDecimal) DataCache.get(payoffId + SettlementConstant._TOTAL_ACTUAL);
		BigDecimal redMoney = (BigDecimal) DataCache.get(payoffId + SettlementConstant._RED_ENVELOPE);
		BigDecimal counponMoey = (BigDecimal) DataCache.get(payoffId + SettlementConstant._CARDNO_PRICE);
		BigDecimal freightPrice = (BigDecimal) DataCache.get(payoffId + SettlementConstant._FREIGHT_PRICE);
		
		BigDecimal canUserPointMoney = totalActual.subtract(freightPrice).subtract(counponMoey).subtract(redMoney);
		
		
		if(canUserPointMoney.compareTo(ponintMoney) ==-1){
			return   NumberFormatUtil.numberFormat( canUserPointMoney);
		} 
		return  NumberFormatUtil.numberFormat(ponintMoney);
	}
	

	private Object checkPayoffId(String payoffId) {
		final String payoffIdValue = (String) DataCache.get(payoffId);
		if (StringUtils.isBlank(payoffIdValue)) {
			return Jsonp.payOffFailedError(PAY_Off_FAILED_ERROR_MSG);
		}
		return null;
	}

	/***
	 * 在一些商品标签下使用优惠券
	 * 
	 * @param skuLabelIdList
	 * @param couponCardDto
	 * @return
	 */
	private Jsonp useCardInSomeSkuLabel(List<String> skuLabelCodeList, CouponCardDto couponCardDto) {
		List<String> labelCodeList = skuLabelRelationService.findLabelCodesBySkuCodes(skuLabelCodeList);
		if (CollectionUtils.isEmpty(labelCodeList)) {
			// 购买商品列表、商品Label为空,不允许使用代金卷
			return Jsonp.newInstance(UseCouponEnum.COUPON_LABEL_ISNULL.getCode(),
					UseCouponEnum.COUPON_LABEL_ISNULL.getMsg());
		}
		String codeStr = StringTools.replaceAllDot(couponCardDto.getCodeList());
		List<String> codeList = StringTools.splitStringToList(codeStr, ",");
		if (Collections.disjoint(labelCodeList, codeList)) {
			// 勾选购买商品的Label列表不包含,cardno代金卷对应的Label列表,则不使用该代金卷
			return Jsonp.newInstance(UseCouponEnum.COUPON_NOT_LABEL.getCode(), UseCouponEnum.COUPON_NOT_LABEL.getMsg());
		}
		List<String> skuCodeList = new ArrayList<String>();
		for (String labelCode : labelCodeList) {
			String SkuLabelRelationList = skuLabelRelationService.getSkuCodeAryByLabelCode(labelCode);
			String[] skuLabelRelationStr = SkuLabelRelationList.split(",");
			for (String skuCode : skuLabelRelationStr) {
				skuCodeList.add(skuCode);
			}
		}
		return judgeIsCanUseManZeng(couponCardDto, skuCodeList);
	}

	/***
	 * 在一些商品列表下 使用优惠券
	 * 
	 * @param skuCodeList
	 * @param couponCardDto
	 * @return
	 */
	private Jsonp useCardInSomeSku(List<String> skuCodeList, CouponCardDto couponCardDto) {
		String codeStr = StringTools.replaceAllDot(couponCardDto.getCodeList());
		List<String> codeList = StringTools.splitStringToList(codeStr, ",");
		if (Collections.disjoint(skuCodeList, codeList)) {
			// 勾选购买商品列表不包含,cardno代金卷对应的商品列表,则不使用该代金卷
			return Jsonp.newInstance(UseCouponEnum.COUPON_NOT_SKUS.getCode(), UseCouponEnum.COUPON_NOT_SKUS.getMsg());
		}
		return judgeIsCanUseManZeng(couponCardDto, skuCodeList);
	}

	/***
	 * 在商品分类下使用优惠券
	 * 
	 * @param skuCategoryIdList
	 * @param couponCardDto
	 * @return
	 */
	private Jsonp useCardInSomeSkuCategory(List<String> skuCategoryCodeList, CouponCardDto couponCardDto) {
		String skuCodesStr = StringTools.listToString(skuCategoryCodeList, false);
		List<String> categoryCodeList = skuCategoryRelationService.findCategoryCodesBySkuCodes(skuCodesStr);
		if (CollectionUtils.isEmpty(categoryCodeList)) {
			// 购买商品列表、商品分类为空,不允许使用优惠券
			return Jsonp.newInstance(UseCouponEnum.COUPON_CATEGORY_ISNULL.getCode(),
					UseCouponEnum.COUPON_CATEGORY_ISNULL.getMsg());
		}
		String idStr = StringTools.replaceAllDot(couponCardDto.getCodeList());
		List<String> idList = StringTools.splitStringToList(idStr, ",");
		if (Collections.disjoint(categoryCodeList, idList)) {
			// 勾选购买商品的分类列表不包含,cardno代金卷对应的分类列表,则不使用该代金卷
			return Jsonp.newInstance(UseCouponEnum.COUPON_NOT_CATEGORY.getCode(),
					UseCouponEnum.COUPON_NOT_CATEGORY.getMsg());
		}
		List<String> skuCodeList = new ArrayList<String>();
		for (String categoryCode : categoryCodeList) {
			List<String> skuCodes = skuCategoryRelationService.findSkuCodesByCategoryCode(categoryCode);
			skuCodeList.addAll(skuCodes);
		}
		return judgeIsCanUseManZeng(couponCardDto, skuCodeList);
	}

	/***
	 * 在一些商品品牌 列表下（Code）优惠使用该card
	 * 
	 * @param skuCodeList
	 *            订单中存在的skuCode
	 * @param couponCardDto
	 * @param flag
	 *            app--web
	 * @return
	 */
	private Jsonp useCardInSomeSkuBrand(List<String> skuBrandCodeList, CouponCardDto couponCardDto) {
		String skuCodesStr = StringTools.listToString(skuBrandCodeList, false);
		List<String> brandCodeList = skuBrandRelationService.findBrandCodesBySkuCodes(skuCodesStr);
		if (CollectionUtils.isEmpty(brandCodeList)) {
			// 购买商品列表、商品品牌为空,不允许使用优惠券
			return Jsonp.newInstance(UseCouponEnum.COUPON_BRAND_ISNULL.getCode(),
					UseCouponEnum.COUPON_BRAND_ISNULL.getMsg());
		}
		String codeStr = StringTools.replaceAllDot(couponCardDto.getCodeList());
		List<String> codeList = StringTools.splitStringToList(codeStr, ",");
		if (Collections.disjoint(brandCodeList, codeList)) {
			// 勾选购买商品的品牌列表中不包含,优惠券对应的品牌列表,则不使用该优惠券
			return Jsonp.newInstance(UseCouponEnum.COUPON_NOT_BRAND.getCode(), UseCouponEnum.COUPON_NOT_BRAND.getMsg());
		}
		List<String> skuCodeLists = new ArrayList<String>();
		for (String brandCode : brandCodeList) {
			List<String> skuCodes = skuBrandRelationService.findSkuCodesByBrandCode(brandCode);
			skuCodeLists.addAll(skuCodes);
		}
		return judgeIsCanUseManZeng(couponCardDto, skuCodeLists);
	}

	/***
	 * 判断是否满足满赠的条件
	 * 
	 * @param couponCardDto
	 * @param brandIdList
	 * @param idList
	 * @return
	 */
	private Jsonp judgeIsCanUseManZeng(CouponCardDto couponCardDto, List<String> skuCodeList) {
		BigDecimal skuTotalMoney = new BigDecimal(0);// 在优惠范围内的商品总价
		BigDecimal conditionSkuPriceAbove = new BigDecimal(couponCardDto.getConditionSkuPriceAbove());// 可使用优惠的最低消费金额（满赠条件）
		List<GotoOrderLineDto> orderLineList = couponCardDto.getOrderLineItems();

		for (GotoOrderLineDto gotoOrderLineDto : orderLineList) {
			String skuCode = gotoOrderLineDto.getSkuCode();// 订单行商品code集合
			if (skuCodeList.contains(skuCode)) {
				// BigDecimal
				// orderLineTotal=gotoOrderLineDto.getCurrentPrice().multiply(new
				// BigDecimal(gotoOrderLineDto.getRequestedQty()));
				BigDecimal orderLineTotal = new BigDecimal(gotoOrderLineDto.getCurrentPriceTotal());//
				if (SourceConstant.APP_CODE.equals(couponCardDto.getFlag())) {
					orderLineTotal = orderLineTotal.multiply(this.discountRate());
				}
				skuTotalMoney = skuTotalMoney.add(orderLineTotal);
			}
		}
		if (skuTotalMoney.compareTo(conditionSkuPriceAbove) < 0) {
			return Jsonp.newInstance(UseCouponEnum.FULL_USE_COUPON.getCode(), "满" + conditionSkuPriceAbove
					+ UseCouponEnum.FULL_USE_COUPON.getMsg());
		}
		return Jsonp.success();
	}

	private BigDecimal discountRate() {
		SysmgrGlobalConfig config = sysmgrGlobalConfigService.findByCode(AppConstant.APP_PRICE_DISCOUNT_RATE);
		if (ObjectUtils.notEqual(null, config)) {
			return new BigDecimal(config.getValue());
		}
		return new BigDecimal(0);
	}

	/***
	 * 使用微信卡券交易
	 * 
	 * @param cardDto
	 * @return
	 */
//	private Map<String, String> useWXCard(String cardCode) {
//		Map<String, String> resultMap = new HashMap<String, String>();
//		String card_id = UseWXCrad.getCardIdByCode(cardCode);
//		if (StringUtils.isBlank(card_id)) {
//			resultMap.put("result", "error");
//			return resultMap;
//		}
//		String cardJson = UseWXCrad.getCardInfo(card_id);
//		if (StringUtils.isBlank(cardJson)) {
//			resultMap.put("result", "error");
//			return resultMap;
//		}
//		try {
//			JSONObject jsonObj = new JSONObject(cardJson);
//			String errmsg = jsonObj.getString("errmsg");
//			if (!StringUtils.equals(errmsg, "ok")) {
//				resultMap.put("result", "error");
//				return resultMap;
//			}
//			JSONObject cardObj = jsonObj.getJSONObject("card");
//			String card_type = cardObj.getString("card_type");
//			if (!StringUtils.equals(card_type, "CASH")) {
//				resultMap.put("result", "error");
//				return resultMap;
//			}
//			JSONObject cashJsonObj = cardObj.getJSONObject("cash");
//			String leastCostStr = cashJsonObj.getString("least_cost");
//			String reduceCostStr = cashJsonObj.getString("reduce_cost");
//			resultMap.put("leastCost", leastCostStr);
//			resultMap.put("reduceCost", reduceCostStr);
//
//		} catch (Exception e) {
//			return null;
//		}
//		return resultMap;
//	}

	/***
	 * 如果是微信card则该卡号在数据库不存在，如果该卡的类型为0代表该卡不可用
	 * 
	 * @param cardDto
	 * @return
	 */
//	private boolean judgeCardIsWXCard(CouponCardDto cardDto) {
//
//		if (ObjectUtils.equals(null, cardDto) || StringUtils.equals(CardTypeConstant.USE_NO, cardDto.getCardType())) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	/**
	 * 订单总计计算方法 返回两位小数总计价格
	 * 
	 * @param payoffId
	 *            结算序列ID
	 * @return
	 */
	private String getTotalMoney(String payOffId) {
		BigDecimal skuTotalPrice = (BigDecimal) DataCache.get(payOffId + SettlementConstant._SKU_PRICE);// 商品总额
		BigDecimal orderTotalActual = (BigDecimal) DataCache.get(payOffId + SettlementConstant._TOTAL_ACTUAL);// 订单总计
		BigDecimal freightPrice = (BigDecimal) DataCache.get(payOffId + SettlementConstant._FREIGHT_PRICE);// 运费
		BigDecimal integralPrice = (BigDecimal) DataCache.get(payOffId + SettlementConstant._INTEGRAL_PRICE);// 积分价格
		BigDecimal couponCardPrice = (BigDecimal) DataCache.get(payOffId + SettlementConstant._CARDNO_PRICE);// 代金券价格
		BigDecimal accountBalance = (BigDecimal) DataCache.get(payOffId + SettlementConstant._ACCOUNT_BALANCE);// 礼品卡
		BigDecimal redEnvelopePrice = (BigDecimal)  DataCache.get(payOffId + SettlementConstant._RED_ENVELOPE);// 红包
		
		// 商品实际价格=(商品价格-积分价格-商品优惠券价格-礼品卡金额-红包金额)
		BigDecimal actualPrice = skuTotalPrice.subtract(couponCardPrice).subtract(integralPrice)
				.subtract(accountBalance).subtract(redEnvelopePrice);
		// 订单总计价格=实际价格+运费价格
		orderTotalActual = actualPrice.add(freightPrice);
		// 订单总计是否小于0
		if (orderTotalActual.compareTo(new BigDecimal(0)) < 0) {
			orderTotalActual = new BigDecimal(0);
		}
		// 保留两位小数
		String orderPrice = NumberFormatUtil.numberFormat(orderTotalActual);
		DataCache.put(payOffId + SettlementConstant._TOTAL_ACTUAL, new BigDecimal(orderPrice));// 订单最终价格
		return orderPrice;
	}

	/**
	 * 订单运费计算方法 -- 保留两位小数运费
	 * 
	 * @param payoffId
	 *            结算序列ID
	 * @return
	 */
	private String getFreightMoney(String payoffId, String areaCode) {
		BigDecimal skuPrice = (BigDecimal) DataCache.get(payoffId + SettlementConstant._SKU_PRICE);// 商品总额
		BigDecimal integralPrice = (BigDecimal) DataCache.get(payoffId + SettlementConstant._INTEGRAL_PRICE);// 积分价格
		BigDecimal couponCardPrice = (BigDecimal) DataCache.get(payoffId + SettlementConstant._CARDNO_PRICE);// 优惠券价格
		BigDecimal redEnvelopePrice = (BigDecimal) DataCache.get(payoffId + SettlementConstant._RED_ENVELOPE);// 优惠券价格
		
		// 商品实际价格=(商品价格-积分价格-商品优惠券价格-益多宝收益额-益多宝赠券额)
		BigDecimal actualPrice = skuPrice.subtract(couponCardPrice).subtract(integralPrice).subtract(couponCardPrice).subtract(redEnvelopePrice);
		String freightPrice = "0.00";// 运费
		SysmgrGlobalConfig sysmgrGlobalConfig = sysmgrGlobalConfigService
				.findByCode(GlobalConfigConstant.FREE_FREIGHT_CODE);
		if (!ObjectUtils.equals(sysmgrGlobalConfig, null)) {
			BigDecimal freeFreight = new BigDecimal(sysmgrGlobalConfig.getValue());// 拿到配置表的value值
			// 小于0表示前面数小后面数大--收运费
			if (actualPrice.compareTo(freeFreight) < 0) {
				SysmgrSfFreightConfig sysmgrSfFreight = sysmgrSfFreightService.findByAreaCode(areaCode);
				if (sysmgrSfFreight != null) {
					// 保留两位小数
					freightPrice = NumberFormatUtil.numberFormat(sysmgrSfFreight.getAmount());
				}
			}
		}
		DataCache.put(payoffId + SettlementConstant._FREIGHT_PRICE, new BigDecimal(freightPrice));// 运费
		return freightPrice;
	}

//	private Jsonp_data getWxCardReduceCostMoney(OrderExtraCommonDto cardDto, BigDecimal orderTotalAcualMoney) {
//		Map<String, String> resultMap = this.useWXCard(cardDto.getCouponCode());
//		String resultCode = "0";
//		if (CollectionUtils.isEmpty(resultMap)) {
//			resultCode = UseCouponEnum.COUPON_ISNULL.getCode();
//			return Jsonp_data.newInstance(resultCode, null, UseCouponEnum.getTitle(resultCode));
//		}
//		String result = resultMap.get("result");
//		if (StringUtils.equals("error", result)) {
//			resultCode = UseCouponEnum.COUPON_ISNULL.getCode();
//			return Jsonp_data.newInstance(resultCode, null, UseCouponEnum.getTitle(resultCode));
//		}
//		String leastCostStr = resultMap.get("leastCost");
//		String reduceCostStr = resultMap.get("reduceCost");
//		BigDecimal leastCostMoney = new BigDecimal(leastCostStr).multiply(BigDecimal.valueOf(0.01));// 满多少钱可以使用
//		BigDecimal reduceCostMoney = new BigDecimal(reduceCostStr).multiply(BigDecimal.valueOf(0.01));// 优惠金额
//		if (orderTotalAcualMoney.doubleValue() < leastCostMoney.doubleValue()) {
//			String leastCostStrTmp = NumberFormatUtil.numberFormat(leastCostMoney);
//			resultCode = UseCouponEnum.FULL_USE_COUPON.getCode();
//
//			return Jsonp_data.newInstance(resultCode, null, "满" + leastCostStrTmp + UseCouponEnum.getTitle(resultCode));
//		}
//
//		return Jsonp_data.success(NumberFormatUtil.numberFormat(reduceCostMoney));
//	}

	private Jsonp_data getCardMoney(OrderExtraCommonDto orderDto, BigDecimal orderTotalAcualMoney, CouponCardDto cardDto) {
		String factorPrice = "0";
		if (StringUtils.equals(orderDto.getIsUseCouponCard(), CommonConstant.YES)) {
			factorPrice = cardDto.getDiscountMoney();
		}
		if (StringUtils.isBlank(factorPrice)) {
			return Jsonp_data.newInstance(UseCouponEnum.COUPON_EXCEPTION.getCode(), null,
					UseCouponEnum.COUPON_EXCEPTION.getMsg());
		}
		String result = NumberFormatUtil.numberFormat(new BigDecimal(factorPrice));
		List<String> skuCodeList = this.getSkuCodeList(orderDto);
		String cardType = cardDto.getCardType();

		if (StringUtils.equals(CardTypeConstant.USE_ALL, cardType)) {
			String skuPriceAbove = cardDto.getConditionSkuPriceAbove();
			BigDecimal skuPriceAboveResult = StringUtils.isBlank(skuPriceAbove) ? BigDecimal.valueOf(0)
					: new BigDecimal(skuPriceAbove);
			boolean flag = orderTotalAcualMoney.compareTo(skuPriceAboveResult) < 0;
			/** 不使用优惠券不能进行最低消费验证,直接return */
			if (StringUtils.equals(CommonConstant.NO, orderDto.getIsUseCouponCard()) || !flag) {
				return Jsonp_data.success(result);
			}
			return Jsonp_data.newInstance(UseCouponEnum.FULL_USE_COUPON.getCode(), null,
					"满" + cardDto.getConditionSkuPriceAbove() + UseCouponEnum.FULL_USE_COUPON.getMsg());
		} else if (StringUtils.equals(CardTypeConstant.USE_BRAND, cardType)) {
			Jsonp skuBrandResult = this.useCardInSomeSkuBrand(skuCodeList, cardDto);
			if (!StringUtils.equals(skuBrandResult.getResponseCode(), CommonCode.SUCCESS_CODE)) {
				return Jsonp_data.newInstance(skuBrandResult.getResponseCode(), null, skuBrandResult.getMsg());
			}
			return Jsonp_data.success(result);
		} else if (StringUtils.equals(CardTypeConstant.USE_CATEGORY, cardType)) {
			Jsonp categoryResult = this.useCardInSomeSkuCategory(skuCodeList, cardDto);
			if (!StringUtils.equals(categoryResult.getResponseCode(), CommonCode.SUCCESS_CODE)) {
				return Jsonp_data.newInstance(categoryResult.getResponseCode(), null, categoryResult.getMsg());
			}
			return Jsonp_data.success(result);
		} else if (StringUtils.equals(CardTypeConstant.USE_SOME, cardType)) {
			Jsonp skuResult = this.useCardInSomeSku(skuCodeList, cardDto);
			if (!StringUtils.equals(skuResult.getResponseCode(), CommonCode.SUCCESS_CODE)) {
				return Jsonp_data.newInstance(skuResult.getResponseCode(), null, skuResult.getMsg());
			}
			return Jsonp_data.success(result);
		} else if (StringUtils.equals(CardTypeConstant.USER_LABEL, cardType)) {
			Jsonp skuLabelResult = useCardInSomeSkuLabel(skuCodeList, cardDto);
			if (!StringUtils.equals(skuLabelResult.getResponseCode(), CommonCode.SUCCESS_CODE)) {
				return Jsonp_data.newInstance(skuLabelResult.getResponseCode(), null, skuLabelResult.getMsg());
			}
			return Jsonp_data.success(result);
		}
		return Jsonp_data.newInstance(UseCouponEnum.COUPON_ISNULL.getCode(), null, UseCouponEnum.COUPON_ISNULL.getMsg());
	}

	/***
	 * 得到购物车，已选中的商品的id集合
	 * 
	 * @param cardDto
	 * @return
	 */
	private List<String> getSkuCodeList(OrderExtraCommonDto cardDto) {
		ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
		queryCondition.setIsSelected(CommonConstant.YES);
		queryCondition.setMemberId(cardDto.getMemberId());
		List<String> skuCodeList = shoppingCartSkuUserService.findCartSkuSelectedSkuCode(queryCondition);
		if (CollectionUtils.isEmpty(skuCodeList)) {
			return null;
		}
		return skuCodeList;

	}

	/***
	 * 得到已选中的购物车的订单行对象（主要使用商品id和商品数量，商品单价）
	 * 
	 * @param cardDto
	 * @return
	 */
	private List<GotoOrderLineDto> getGotoOrderLineList(Long memberId) {
		List<ShoppingCartSku> skuCartList = new ArrayList<ShoppingCartSku>();
		List<GotoOrderLineDto> goToOrderLineList = new ArrayList<GotoOrderLineDto>();

		ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
		queryCondition.setIsSelected(CommonConstant.YES);
		queryCondition.setMemberId(memberId);
		skuCartList = shoppingCartSkuUserService.findByQueryCondition(queryCondition);

		if (CollectionUtils.isEmpty(skuCartList)) {
			return null;
		}
		for (ShoppingCartSku soShoppingCartSkuUser : skuCartList) {
			GotoOrderLineDto goToOrderLineDto = new GotoOrderLineDto();
			goToOrderLineDto.setSkuCode(soShoppingCartSkuUser.getSkuCode());
			
			SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(soShoppingCartSkuUser.getSkuCode());
			
			
			BigDecimal currentPrice =skuFacade.getCurrentSkuPrice(soShoppingCartSkuUser.getSkuCode(), memberId, skuExtraInfo.getIsMemberSku());
			 
			
			BigDecimal currentPriceTotal = currentPrice.multiply(
					new BigDecimal(soShoppingCartSkuUser.getQuantity()));
			goToOrderLineDto.setCurrentPriceTotal(NumberFormatUtil.numberFormat(currentPriceTotal));
			goToOrderLineList.add(goToOrderLineDto);
		}
		return goToOrderLineList;
	}

	/***
	 * 使用优惠券
	 */
	public Object useCardNo(OrderExtraCommonDto cardDto) {
		String payoffId = cardDto.getPayOffId();
		if (ObjectUtils.notEqual(null, checkPayoffId(payoffId))) {
			return checkPayoffId(payoffId);
		}
		BigDecimal orderTotalAcualMoney = new BigDecimal(getTotalMoney(payoffId));

		CouponCardDto couponCardDto = actCardService.getAmountByCardNo(cardDto.getCouponCode());
		if(CardTypeConstant.USE_NO.equals(couponCardDto.getCardType())){
		     return Jsonp.error(UseCouponEnum.COUPON_ISNULL.getMsg());
		}
		couponCardDto.setFlag(cardDto.getFlag());
		couponCardDto.setOrderLineItems(this.getGotoOrderLineList(cardDto.getMemberId()));
		String cardPriceStr = null;
//		if (judgeCardIsWXCard(couponCardDto)) {
//			Jsonp_data wxCardResult = this.getWxCardReduceCostMoney(cardDto, orderTotalAcualMoney);
//			if (wxCardResult.getResponseCode() != "0") {
//				return wxCardResult;
//			}
//			String wxCardReduceCostMoneyStr = (String) wxCardResult.getData();
//			cardPriceStr = wxCardReduceCostMoneyStr;
//		} else {
			Jsonp_data jsonp = this.getCardMoney(cardDto, orderTotalAcualMoney, couponCardDto);
			if (!StringUtils.equals(CommonCode.SUCCESS_CODE, jsonp.getResponseCode())) {
				return Jsonp.error(jsonp.getMsg());
			}
			cardPriceStr = (String) jsonp.getData();
			// cardPriceStr = this.getCardMoney(cardDto, orderTotalAcualMoney,
			// couponCardDto);
			// if(StringUtils.isBlank(cardPriceStr)){
			// return Jsonp.error(couponCardDto.getHintMessage());
			// }
	//	}
		BigDecimal cardPrice = ObjectUtils.equals(null, cardPriceStr) ? BigDecimal.valueOf(0) : new BigDecimal(
				cardPriceStr);
		DataCache.put(payoffId + SettlementConstant._CARDNO_PRICE, cardPrice);
		String freightPrice = getFreightMoney(payoffId, cardDto.getAreaCode());
		String totalActualPrice = getTotalMoney(payoffId);
		OrderExtraDto orderExtraDto = new OrderExtraDto();
		orderExtraDto.setCardnoPrice(NumberFormatUtil.numberFormat(cardPrice));
		orderExtraDto.setFreightPrice(freightPrice);
		orderExtraDto.setTotalActual(totalActualPrice);
		return Jsonp_data.success(orderExtraDto);
	}

	public Jsonp_data queryCoupon(OrderExtraCommonDto orderExtraDto) {

		String payoffId = orderExtraDto.getPayOffId();

		final String payoffIdValue = (String) DataCache.get(payoffId);
		String resultCode = "0";
		if (StringUtils.isBlank(payoffIdValue)) {
			resultCode = GoOrderPayEnum.PAY_Off_FAILED_ERROR_MSG.getCode();
			return Jsonp_data.newInstance(resultCode, null, GoOrderPayEnum.getTitle(resultCode));
		}
		BigDecimal orderTotalAcualMoney = new BigDecimal(this.getTotalMoney(payoffId));
		CouponCardDto cardDto = actCardService.getAmountByCardNo(orderExtraDto.getCouponCode());
		cardDto.setFlag(orderExtraDto.getFlag());
		cardDto.setOrderLineItems(this.getGotoOrderLineList(orderExtraDto.getMemberId()));
		String cardPriceStr = null;
		Jsonp_data jsonp = this.getCardMoney(orderExtraDto, orderTotalAcualMoney, cardDto);
		if (!StringUtils.equals(CommonCode.SUCCESS_CODE, jsonp.getResponseCode())) {
			return Jsonp_data.newInstance(jsonp.getResponseCode(), null, jsonp.getMsg());
		}
		cardPriceStr = (String) jsonp.getData();

		BigDecimal cardPrice = ObjectUtils.equals(null, cardPriceStr) ? BigDecimal.valueOf(0) : new BigDecimal(
				cardPriceStr);
		DataCache.put(payoffId + SettlementConstant._CARDNO_PRICE, cardPrice);
		DataCache.put(payoffId + SettlementConstant._COUPON_CODE, orderExtraDto.getCouponCode());
		String freightPrice = this.getFreightMoney(payoffId, orderExtraDto.getAreaCode());
		String totalActualPrice = getTotalMoney(payoffId);
		OrderExtraDto orderDto = new OrderExtraDto();
		orderDto.setCardnoPrice(NumberFormatUtil.numberFormat(cardPrice));
		orderDto.setCouponMoney(NumberFormatUtil.numberFormat(cardPrice));
		orderDto.setFreightPrice(freightPrice);
		orderDto.setTotalActual(totalActualPrice);
		return Jsonp_data.success(orderDto);
	}

	public Object getRedEnvelop(Long memberId, String payoffId, String isUse,String areaCode) {
		final String payoffIdValue = (String) DataCache.get(payoffId);
		String resultCode = "0";
		if (StringUtils.isBlank(payoffIdValue)) {
			resultCode = GoOrderPayEnum.PAY_Off_FAILED_ERROR_MSG.getCode();
			return Jsonp_data.newInstance(resultCode, null, GoOrderPayEnum.getTitle(resultCode));
		}
		
		BigDecimal skuTotalPrice = (BigDecimal)DataCache.get(payoffId + SettlementConstant._SKU_PRICE);
		
		ActivityShareMember	activityShareMember= activityShareMemberService.findByMemberId(memberId);
		BigDecimal	redAmount = new BigDecimal(0);
		
		if(StringUtils.equalsIgnoreCase(isUse, CommonConstant.YES) && activityShareMember!=null){
			redAmount = activityShareMember.getExistingRedEnvelope();
		}
	
		BigDecimal useAmout = new BigDecimal(NumberFormatUtil.numberFormat(skuTotalPrice.multiply(BigDecimal.valueOf(0.3)))).setScale(0,BigDecimal.ROUND_HALF_UP);
		BigDecimal useAmountActual = redAmount.compareTo(useAmout)>-1?useAmout:redAmount;
	
		useAmountActual = useAmountActual.setScale(0,BigDecimal.ROUND_HALF_UP);
		
		DataCache.put(payoffId + SettlementConstant._RED_ENVELOPE, useAmountActual);//使用红包
		

		String freightPrice = this.getFreightMoney(payoffId, areaCode);
		String totalActualPrice = getTotalMoney(payoffId);			
		OrderExtraDto orderExtraDto = new OrderExtraDto();
		orderExtraDto.setRedEnvelop(useAmountActual.toString());
		orderExtraDto.setFreightPrice(freightPrice);
		orderExtraDto.setTotalActual(totalActualPrice);
		BeanNullConverUtil.nullConver(orderExtraDto);
		return Jsonp_data.success(orderExtraDto);
		
	}

	/***
	 * 立即支付的订单行信息获取
	 * 
	 * @param cardDto
	 * @param cardVo
	 * 
	 *            private void setImmediatePayParam(OrderExtraCommonDto cardDto,
	 *            CardVo cardVo) { String payOffId=cardDto.getPayOffId();
	 *            BigDecimal skuPrice =
	 *            (BigDecimal)DataCache.get(payOffId+SettlementConstant
	 *            ._SKU_PRICE);//商品总额 String skuCode=(String)
	 *            DataCache.get(payOffId+SettlementConstant._SKU_CODE); Sku sku=
	 *            this.findSkuService.queryBySkuCode(skuCode); GotoOrderLineDto
	 *            orderLine=new GotoOrderLineDto();
	 *            orderLine.setSkuCode(sku.getCode());
	 *            orderLine.setCurrentPriceTotal(skuPrice);
	 *            List<GotoOrderLineDto> lineOrderList=new
	 *            ArrayList<GotoOrderLineDto>(); lineOrderList.add(orderLine);
	 *            cardVo.setOrderLineItems(lineOrderList); }
	 */

}
