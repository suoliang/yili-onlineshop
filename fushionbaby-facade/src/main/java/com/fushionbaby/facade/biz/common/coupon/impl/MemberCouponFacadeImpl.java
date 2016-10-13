package com.fushionbaby.facade.biz.common.coupon.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.act.activity.dto.MemberCouponReqDto;
import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.model.ActCardType;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.act.activity.service.ActCardTypeService;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.constants.CardTypeConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonCode;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.SettlementConstant;
import com.fushionbaby.common.dto.CouponCardDto;
import com.fushionbaby.common.dto.order.GotoOrderLineDto;
import com.fushionbaby.common.dto.order.OrderExtraDto;
import com.fushionbaby.common.enums.UseCouponEnum;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.RandomNumUtil;
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
import com.fushionbaby.facade.biz.common.coupon.MemberCouponFacade;
import com.fushionbaby.sku.model.SkuBrandRelation;
import com.fushionbaby.sku.model.SkuCategoryRelation;
import com.fushionbaby.sku.service.SkuBrandRelationService;
import com.fushionbaby.sku.service.SkuCategoryRelationService;
import com.fushionbaby.sku.service.SkuLabelRelationService;
import com.fushionbaby.sku.service.SkuService;
/***
 * 下订单使用优惠券  app 使用
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月6日上午9:24:26
 */
@Service
public class MemberCouponFacadeImpl implements MemberCouponFacade{

	
	private static final Log LOGGER=LogFactory.getLog(MemberCouponFacadeImpl.class);
	/** 优惠券*/
	@Autowired
	private ActCardService<ActCard> actCardService;
	
	/**优惠券类型*/
	@Autowired
	private ActCardTypeService<ActCardType> actCardTypeService;
	
	private static String PAY_Off_FAILED_ERROR_MSG="订单结算序列失效!";
	
	/** 注入购物车行记录 */
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> shoppingCartSkuUserService;
	/**商品品牌关联*/
	@Autowired
	private SkuBrandRelationService<SkuBrandRelation> skuBrandRelationService;
	/**商品分类关联*/
	@Autowired
	private SkuCategoryRelationService<SkuCategoryRelation> skuCategoryRelationService;
	/**商品*/
	@Autowired
	private SkuService skuService;
	/**商品标签关联*/
	@Autowired
	private SkuLabelRelationService skuLabelRelationService;
	/** 重要配置*/
	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	/** 顺丰快递运费service */
	@Autowired
	private SysmgrSfFreightConfigService<SysmgrSfFreightConfig> sysmgrSfFreightService;

	public Object useMemberCoupon(MemberCouponReqDto reDto) {

		String payOffId = reDto.getPayOffId();
		if (ObjectUtils.notEqual(null, checkPayoffId(payOffId))) {
			return checkPayoffId(payOffId);
		}
		
		Jsonp_data jsonp = this.getCardMoney(reDto);
		if (!StringUtils.equals(CommonCode.SUCCESS_CODE, jsonp.getResponseCode())) {
			return Jsonp.error(jsonp.getMsg());
		}
		String cardPriceStr = (String) jsonp.getData();
		BigDecimal cardPrice = StringUtils.isBlank(cardPriceStr) ? BigDecimal.valueOf(0) : new BigDecimal(cardPriceStr);
		DataCache.put(payOffId + SettlementConstant._CARDNO_PRICE, cardPrice);
		
		String freightPrice = getFreightMoney(payOffId, reDto.getAreaCode());
		/**返回给订单的总金额 未减去优惠券*/
		String totalActualPrice = this.getTotalMoney(payOffId);
		OrderExtraDto orderExtraDto = new OrderExtraDto();
		/**代金券金额 大于订单金额*/
//		if(cardPrice.compareTo(new BigDecimal(totalActualPrice))>0){
//			orderExtraDto.setCouponMoney(totalActualPrice);
//			orderExtraDto.setTotalActual(NumberFormatUtil.numberFormat(new BigDecimal(0)));
//			DataCache.put(payOffId + SettlementConstant._TOTAL_ACTUAL, BigDecimal.ZERO);
//		}else{
//			orderExtraDto.setTotalActual(NumberFormatUtil.numberFormat(new BigDecimal(totalActualPrice).subtract(cardPrice)));
//			orderExtraDto.setCouponMoney(NumberFormatUtil.numberFormat(cardPrice));
//			DataCache.put(payOffId + SettlementConstant._TOTAL_ACTUAL, new BigDecimal(totalActualPrice).subtract(cardPrice));
//		}
		
		orderExtraDto.setTotalActual(NumberFormatUtil.numberFormat(new BigDecimal(totalActualPrice)));
		orderExtraDto.setCouponMoney(NumberFormatUtil.numberFormat(cardPrice));
		orderExtraDto.setFreightPrice(freightPrice);
	   return 	Jsonp_data.success(orderExtraDto);
	}
	
	/***
	 *  验证订单结算是否合法
	 * @param payoffId
	 * @return
	 */
	private Object checkPayoffId(String payoffId) {
		final String payoffIdValue = (String) DataCache.get(payoffId);
		if (StringUtils.isBlank(payoffIdValue)) {
			return Jsonp.payOffFailedError(PAY_Off_FAILED_ERROR_MSG);
		}
		return null;
	}
	/***
	 * 得到已选中的购物车的订单行对象（主要使用商品id和商品数量，商品单价）
	 * 
	 * @param cardDto
	 * @return
	 */
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
			BigDecimal currentPriceTotal = soShoppingCartSkuUser.getCurrentPrice().multiply(
					new BigDecimal(soShoppingCartSkuUser.getQuantity()));
			goToOrderLineDto.setCurrentPriceTotal(NumberFormatUtil.numberFormat(currentPriceTotal));
			goToOrderLineList.add(goToOrderLineDto);
		}
		return goToOrderLineList;
	}
	/***
	 * 优惠券使用和购物车订单行的商品的处理
	 * @param reDto
	 * @param skuTotalPrice
	 * @param cardDto
	 * @return
	 */
	public Jsonp_data getCardMoney(MemberCouponReqDto reDto) {
		
		/**得到购物车中待结算订单的商品总价格*/
		BigDecimal skuTotalPrice = (BigDecimal) DataCache.get(reDto.getPayOffId() + SettlementConstant._SKU_PRICE);// 商品总额
		
		/**计算优惠券*/
		CouponCardDto cardDto = actCardService.getAmountByCardNo(reDto.getCouponCode());
		if(CardTypeConstant.USE_NO.equals(cardDto.getCardType())){
		     return Jsonp_data.newInstance(UseCouponEnum.COUPON_ISNULL.getCode(), "", UseCouponEnum.COUPON_ISNULL.getMsg());
		     
		}
		cardDto.setOrderLineItems(this.getGotoOrderLineList(reDto.getMemberId()));
		
		
		String factorPrice = "0";
		if (StringUtils.equals(reDto.getIsUseCouponCard(), CommonConstant.YES)) {
			factorPrice = cardDto.getDiscountMoney();
		}
		if (StringUtils.isBlank(factorPrice)) {
			return Jsonp_data.newInstance(UseCouponEnum.COUPON_EXCEPTION.getCode(), null,UseCouponEnum.COUPON_EXCEPTION.getMsg());
		}
		String result = NumberFormatUtil.numberFormat(new BigDecimal(factorPrice));
		/**得到购物车订单行的商品id集合*/
		List<String> skuCodeList = this.getSkuCodeList(reDto.getMemberId());
		String cardType = cardDto.getCardType();
		if (StringUtils.equals(CardTypeConstant.USE_ALL, cardType)) {
			/**使用要满足的条件*/
			String skuPriceAbove = cardDto.getConditionSkuPriceAbove();
			BigDecimal skuPriceAboveResult = StringUtils.isBlank(skuPriceAbove) ? BigDecimal.valueOf(0)
					: new BigDecimal(skuPriceAbove);
			boolean flag = skuTotalPrice.compareTo(skuPriceAboveResult) < 0;
			/** 不使用优惠券不能进行最低消费验证,直接return */
			if (StringUtils.equals(CommonConstant.NO, reDto.getIsUseCouponCard()) || !flag) {
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
	private List<String> getSkuCodeList(Long memberId) {
		
		ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
		queryCondition.setIsSelected(CommonConstant.YES);
		queryCondition.setMemberId(memberId);
		
		List<String> skuCodeList = shoppingCartSkuUserService.findCartSkuSelectedSkuCode(queryCondition);
		if (CollectionUtils.isEmpty(skuCodeList)) {
			return null;
		}
		return skuCodeList;

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
		/**代金券有效作用范围集合*/
		List<String> codeList = StringTools.splitStringToList(idStr, ",");
		if (Collections.disjoint(categoryCodeList, codeList)) {
			// 勾选购买商品的分类列表不包含,cardno代金卷对应的分类列表,则不使用该代金卷
			return Jsonp.newInstance(UseCouponEnum.COUPON_NOT_CATEGORY.getCode(),
					UseCouponEnum.COUPON_NOT_CATEGORY.getMsg());
		}
		/**该分类下的商品集合*/
		List<String> skuCodeList = new ArrayList<String>();
		for (String categoryCode : categoryCodeList) {
			List<String> skuCodes = skuCategoryRelationService.findSkuCodesByCategoryCode(categoryCode);
			skuCodeList.addAll(skuCodes);
		}
		return judgeIsCanUseManZeng(couponCardDto, skuCodeList);
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
				BigDecimal orderLineTotal = new BigDecimal(gotoOrderLineDto.getCurrentPriceTotal());//
				skuTotalMoney = skuTotalMoney.add(orderLineTotal);
			}
		}
		if (skuTotalMoney.compareTo(conditionSkuPriceAbove) < 0) {
			return Jsonp.newInstance(UseCouponEnum.FULL_USE_COUPON.getCode(), "满" + conditionSkuPriceAbove
					+ UseCouponEnum.FULL_USE_COUPON.getMsg());
		}
		return Jsonp.success();
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

	
	
	
	/**
	 * 订单总计计算方法 返回两位小数总计价格
	 * 
	 * 为减去优惠券的
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
		BigDecimal redBalance = (BigDecimal) DataCache.get(payOffId + SettlementConstant._RED_ENVELOPE);//红包金额
		/** 未减去优惠券的价格*/
		BigDecimal money=skuTotalPrice.subtract(integralPrice).subtract(accountBalance).subtract(redBalance);
		// 商品实际价格=(商品价格-积分价格-商品优惠券价格-礼品卡金额-红包金额)
		BigDecimal actualPrice = money.subtract(couponCardPrice);
		//money = money.add(freightPrice);
		// 订单总计价格=实际价格+运费价格
		orderTotalActual = actualPrice.add(freightPrice);
		// 订单总计是否小于0
		if (orderTotalActual.compareTo(new BigDecimal(0)) < 0) {
			orderTotalActual = new BigDecimal(0);
		}
		//if (money.compareTo(new BigDecimal(0)) < 0) {
		//	money = new BigDecimal(0);
		//}
		// 保留两位小数
		String orderPrice = NumberFormatUtil.numberFormat(orderTotalActual);
		/***/
		//String moneyStr = NumberFormatUtil.numberFormat(money);
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
		// 商品实际价格=(商品价格-积分价格-商品优惠券价格-益多宝收益额-益多宝赠券额)
		BigDecimal actualPrice = skuPrice.subtract(couponCardPrice).subtract(integralPrice);
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

		public void registerSendCoupon(Long id) {
			ActCardType cardType=this.actCardTypeService.findByCode(CardTypeConstant.REGISTER_SEND_ACTIVITY);
			Date now=new Date();
			LOGGER.info("新用户注册送优惠券开始，是否存在该活动"+ cardType==null);
			if(ObjectUtils.notEqual(cardType, null)&&cardType.getBeginTime().before(now)&&cardType.getEndTime().after(now)){
				ActCard card=new ActCard();
				card.setCardNo(CardTypeConstant.REGISTER_SEND_CARD_PRE+RandomNumUtil.getNumber(8));
				card.setCardTypeId(cardType.getId());
				card.setMemberId(id);
				card.setPassword(RandomNumUtil.getNumber(8));
				card.setStartTime(now);
				Calendar ca=Calendar.getInstance();
				ca.add(Calendar.DAY_OF_YEAR, CardTypeConstant.REGISTER_SEND_ACTIVITY_TIME);
				card.setStopTime(ca.getTime());
				card.setUseType(cardType.getUseType());
				card.setUseCount(0);
				card.setUseStatus("1");
				LOGGER.info("新用户注册送优惠券,会员id为："+id+"新增数据开始"+now.toString());
				this.actCardService.add(card);
				LOGGER.info("新用户注册送优惠券,会员id为："+id+"数据添加完毕，优惠券卡号为："+now.toString());
			}
			
		}
}
