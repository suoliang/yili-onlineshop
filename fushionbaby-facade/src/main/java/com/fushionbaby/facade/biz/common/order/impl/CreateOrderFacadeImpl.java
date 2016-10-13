/**
 * 
 */
package com.fushionbaby.facade.biz.common.order.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.OrderConstant;
import com.fushionbaby.common.constants.SettlementConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.ShoppingCartCaptChaEnum;
import com.fushionbaby.common.enums.SkuInventoryCheckEnum;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.EpointsUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.model.SysmgrSfFreightConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.config.service.SysmgrSfFreightConfigService;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.OrderBaseUser;
import com.fushionbaby.core.model.OrderCardRelationUser;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.model.OrderMemberAddressUser;
import com.fushionbaby.core.model.OrderTraceUser;
import com.fushionbaby.core.model.OrderTransUser;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.OrderBaseUserService;
import com.fushionbaby.core.service.OrderCardRelationUserService;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.core.service.OrderLineUserService;
import com.fushionbaby.core.service.OrderMemberAddressUserService;
import com.fushionbaby.core.service.OrderTraceUserService;
import com.fushionbaby.core.service.OrderTransUserService;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.core.vo.OrderParamUser;
import com.fushionbaby.facade.biz.common.order.CreateOrderFacade;
import com.fushionbaby.log.model.LogWxCardVoucher;
import com.fushionbaby.log.service.LogWxCardVoucherService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.service.MemberAddressService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.fushionbaby.sku.service.SkuPriceService;
import com.fushionbaby.sku.service.SkuService;

/**
 * @author mengshaobo
 *
 */
@Service
public class CreateOrderFacadeImpl implements CreateOrderFacade {

	//private static final Log LOGG = LogFactory.getLog(CreateOrderFacadeImpl.class);
	@Autowired
	private MemberAddressService<MemberAddress> memberAddressService;
	@Autowired
	private SysmgrSfFreightConfigService<SysmgrSfFreightConfig> sysmgrSfFreightService;
	@Autowired
	private OrderCardRelationUserService orderCardRelationUserService;
	@Autowired
	private ActCardService<ActCard> actCardService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private LogWxCardVoucherService<LogWxCardVoucher> logWxCardVoucherService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private SkuPriceService<SkuPrice> skuPriceService;
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	@Autowired
	private OrderLineUserService<OrderLineUser> orderLineUserService;
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> shoppingCartSkuUserService;
	@Autowired
	private OrderMemberAddressUserService<OrderMemberAddressUser> orderMemberAddressUserService;
	@Autowired
	private OrderBaseUserService<OrderBaseUser> orderBaseUserService;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private OrderTraceUserService<OrderTraceUser> orderTraceUserService;
	@Autowired
	private OrderTransUserService<OrderTransUser> orderTransUserService;
	
	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;

	public void initOrderPriceInOrderParam(OrderParamUser orderParam) {
		String payoffId = orderParam.getPayOffId();
		// 原价总金额
		BigDecimal retailPriceTotal = (BigDecimal) DataCache.get(payoffId + SettlementConstant._RETAIL_PRICE);
		// 现价总金额
		BigDecimal currentPriceTotal = (BigDecimal) DataCache.get(payoffId + SettlementConstant._SKU_PRICE);
		// 订单总计
		BigDecimal totalActual = (BigDecimal) DataCache.get(payoffId + SettlementConstant._TOTAL_ACTUAL);
		// 运费价格
		BigDecimal freightPrice = (BigDecimal) DataCache.get(payoffId + SettlementConstant._FREIGHT_PRICE);
	     /**如果是自取，运费为零*/
		if(CommonConstant.YES.equals(orderParam.getIsPickUp())){
			if(totalActual.compareTo(freightPrice) >-1){
				totalActual = totalActual.subtract(freightPrice);
				DataCache.put(payoffId + SettlementConstant._TOTAL_ACTUAL,totalActual);
			}
	    	freightPrice=new BigDecimal(0);
	    	DataCache.put(payoffId + SettlementConstant._FREIGHT_PRICE,freightPrice);
		}
		orderParam.setRetailPriceTotal(retailPriceTotal);
		orderParam.setCurrentPriceTotal(currentPriceTotal);
		orderParam.setTotalActual(totalActual);
		orderParam.setFreightPrice(freightPrice);
		
		BigDecimal redMoney = (BigDecimal) DataCache.get(payoffId + SettlementConstant._RED_ENVELOPE);
		BigDecimal counponMoey = (BigDecimal) DataCache.get(payoffId + SettlementConstant._CARDNO_PRICE);
		
		if(StringUtils.equals(orderParam.getIsPoint(),CommonConstant.YES)){
			
			BigDecimal canUserPointMoney = totalActual.subtract(freightPrice).subtract(counponMoey).subtract(redMoney);
			
			BigDecimal ponintMoney = EpointsUtil.epointsToMoney(new BigDecimal(orderParam.getPointUsd()));
			
			if(canUserPointMoney.compareTo(ponintMoney) ==-1){
				orderParam.setPointUsd( EpointsUtil.moneyToEpoints(canUserPointMoney).intValue() + "");
			} 
			
		}
		
	//	orderParam.setPointUsd( NumberFormatUtil.numberFormat(user.getEpoints()));
	}

	public void saveOrder(OrderParamUser orderParam) {
		/* 特殊人员使用时，所有价格都是0.01 开始 */
		Member member = memberService.findById(orderParam.getMemberId());
		if(member!=null && StringUtils.isNotBlank(member.getTelephone())  &&
				orderParam.getTotalActual().compareTo(BigDecimal.ZERO)!=0){
			SysmgrGlobalConfig mobileList = sysmgrGlobalConfigService.findByCode("MOBILELIST");
			if(mobileList!=null && StringUtils.isNotBlank(mobileList.getValue())){
				List<String> phoneList = Arrays.asList(StringUtils.trimToEmpty(mobileList.getValue()).split(","));
				if (phoneList.contains(member.getTelephone())) {
					orderParam.setTotalActual(BigDecimal.valueOf(0.01));
				}
			}
		}
		/* 结束 */
		addOrderBaseUser(orderParam);
		addOrderFeeUser(orderParam);
		addOrderFinanceUser(orderParam);
		addOrderTransUser(orderParam);
		addOrderTraceUser(orderParam);
	}
	
	

	/***
	 * 保存到订单基本表信息
	 * 
	 * @param memberId
	 * @param orderParam
	 */
	private void addOrderBaseUser( OrderParamUser orderParam) {
		Long memberId = orderParam.getMemberId();
		Member userMember = memberService.findById(memberId);
		OrderBaseUser orderBaseUser = new OrderBaseUser();
		orderBaseUser.setOrderCode(orderParam.getOrderCode());
		orderBaseUser.setMemberId(memberId);
		orderBaseUser.setMemberName(this.getUserName(userMember));
		orderBaseUser.setIsRefused(CommonConstant.NO);
		orderBaseUser.setIsDelete(CommonConstant.NO);
		orderBaseUser.setSourceCode(orderParam.getSourceCode());
		orderBaseUser.setOrderPointGained(orderParam.getTotalActual());// 订单多少钱，就多少分
		orderBaseUser.setIsHandlePoint(CommonConstant.NO);
		orderBaseUser
				.setMemo(StringUtils.equals(orderParam.getMemo(), null) ? StringUtils.EMPTY : orderParam.getMemo());
		orderBaseUser.setIsGroupBuy(CommonConstant.NO);
		orderBaseUser.setIsPickUp(orderParam.getIsPickUp());
		orderBaseUser.setStoreCode(orderParam.getStoreCode());
		/** 如果使用积分 */
		if (CommonConstant.YES.equals(orderParam.getIsPoint())) {
			orderBaseUser.setTotalPointUsed(new BigDecimal(orderParam.getPointUsd()));
			BigDecimal points = userMember.getEpoints().subtract(new BigDecimal(orderParam.getPointUsd()));
			userMember.setEpoints(points.compareTo(BigDecimal.valueOf(0))==-1 ? BigDecimal.valueOf(0): points);
			memberService.update(userMember);
		}
		double dtotalActual = orderParam.getTotalActual() == null ? 0 : orderParam.getTotalActual().doubleValue();
		if (dtotalActual <= 0) {
			orderBaseUser.setOrderStatus(OrderConfigServerEnum.AUDIT.getCode());
		} else {
			orderBaseUser.setOrderStatus(OrderConfigServerEnum.WAITING_PAYMENT.getCode());
		}
		orderBaseUserService.addOrderBase(orderBaseUser);	
	}

	/**
	 * 保存到订单费用表信息
	 * 
	 * @param memberId
	 * @param orderParam
	 */
	private void addOrderFeeUser( OrderParamUser orderParam) {
		Long memberId = orderParam.getMemberId();
		OrderFeeUser orderFeeUser = new OrderFeeUser();
		orderFeeUser.setOrderCode(orderParam.getOrderCode());
		orderFeeUser.setMemberId(memberId);
		orderFeeUser.setRedEnvelopeAmount(orderParam.getRedEnvelopeAmount());
		orderFeeUser.setAlabaoCheapAmount(BigDecimal.ZERO);
		orderFeeUser.setStoreCode(orderParam.getStoreCode());

		/** 如果使用代金券 */
		if (StringUtils.equals(CommonConstant.YES, orderParam.getIsCardNo())) {
			orderFeeUser = useCard(orderFeeUser, orderParam);
		}
		orderFeeUser.setTotalActual(orderParam.getTotalActual());// 订单总金额
		orderFeeUser.setTotalAfDiscount(orderParam.getCurrentPriceTotal());// 折扣后总价(当前单价总价)
		/** 如果使用积分 */
		if (CommonConstant.YES.equals(orderParam.getIsPoint())) {
			orderFeeUser.setEpointsRedeemMoney(EpointsUtil.epointsToMoney(new BigDecimal(orderParam.getPointUsd())));
		}
		String province = memberAddressService.findById(Long.valueOf(orderParam.getAddressId())).getProvince();
		BigDecimal payableTransferFee = sysmgrSfFreightService.findByAreaCode(province).getAmount();
		orderFeeUser.setPayableTransferFee(payableTransferFee);// 根据区域拿运费价格
		orderFeeUser.setActualTransferFee(orderParam.getFreightPrice());// 实际运费价格
		orderFeeUserService.addOrderFeeUser(orderFeeUser);
	}

	/***
	 * 保存到订单财务表信息
	 * 
	 * @param memberId
	 * @param orderParam
	 */
	private void addOrderFinanceUser(OrderParamUser orderParam) {
		OrderFinanceUser orderFinanceUser = new OrderFinanceUser();
		orderFinanceUser.setOrderCode(orderParam.getOrderCode());
		orderFinanceUser.setMemberId(orderParam.getMemberId());
		orderFinanceUser.setStoreCode(orderParam.getStoreCode());
		/** 如果需要发票 */
		if (StringUtils.equals(CommonConstant.YES, orderParam.getIsInvoice())) {
			orderFinanceUser.setIsInvoice(CommonConstant.YES);
			orderFinanceUser.setInvoiceType(Integer.valueOf(orderParam.getInvoiceType()));
			orderFinanceUser.setInvoiceTitle(orderParam.getInvoiceTitle());
		} else {
			orderFinanceUser.setIsInvoice(CommonConstant.NO);
		}
		double dtotalActual = orderParam.getTotalActual() == null ? 0 : orderParam.getTotalActual().doubleValue();
		if (dtotalActual <= 0) {
			orderFinanceUser.setFinanceStatus(CommonConstant.YES);
		} else {
			orderFinanceUser.setFinanceStatus(CommonConstant.NO);
		}
		orderFinanceUserService.addOrderFinanceUser(orderFinanceUser);
	}

	/***
	 * 保存到订单物流表
	 * 
	 * @param memberId
	 * @param orderParam
	 */
	private void addOrderTransUser(OrderParamUser orderParam) {
		OrderTransUser orderTransUser = new OrderTransUser();
		orderTransUser.setMemberId(orderParam.getMemberId());
		orderTransUser.setOrderCode(orderParam.getOrderCode());
		orderTransUser.setTransStatus(CommonConstant.NO);
		orderTransUser.setSendDate(orderParam.getSendDate());
		orderTransUser.setSendDate(orderParam.getSendDate());
		orderTransUser.setStoreCode(orderParam.getStoreCode());
		orderTransUserService.addOrderTransUser(orderTransUser);
	}

	/**
	 * 保存到订单跟踪表信息
	 * 
	 * @param memberId
	 * @param orderParam
	 */
	private void addOrderTraceUser(OrderParamUser orderParam) {
		OrderTraceUser orderTraceUser = new OrderTraceUser();
		orderTraceUser.setMemberId(orderParam.getMemberId());
		orderTraceUser.setOrderCode(orderParam.getOrderCode());
		double dtotalActual = orderParam.getTotalActual() == null ? 0 : orderParam.getTotalActual().doubleValue();
		if (dtotalActual <= 0) {
			orderTraceUser.setOrderStatus(OrderConfigServerEnum.AUDIT.getCode());
		} else {
			orderTraceUser.setOrderStatus(OrderConfigServerEnum.WAITING_PAYMENT.getCode());
		}
		orderTraceUser.setStoreCode(orderParam.getStoreCode());
		orderTraceUserService.addOrderTraceUser(orderTraceUser);
	}

	/**
	 * 使用代金券
	 * 
	 * @param orderFeeUser
	 *            用户订单
	 * @param memberId
	 *            用户id
	 * @param orderParam
	 *            订单参数
	 */
	private OrderFeeUser useCard(OrderFeeUser orderFeeUser,  OrderParamUser orderParam) {
		orderFeeUser.setCardno(orderParam.getCardNo());// 代金券号码
		// 代金券金额
		BigDecimal cardAmount = (BigDecimal) DataCache.get(orderParam.getPayOffId() + SettlementConstant._CARDNO_PRICE);
		orderFeeUser.setCardAmount(cardAmount);
		// 使用代金券将信息添加到订单代金券关联表
		OrderCardRelationUser orderCardRelationUser = new OrderCardRelationUser();
		orderCardRelationUser.setMemberId(orderParam.getMemberId());
		orderCardRelationUser.setOrderCode(orderParam.getOrderCode());
		orderCardRelationUser.setCardNo(orderParam.getCardNo());
		orderCardRelationUser.setAmount(cardAmount);
		orderCardRelationUserService.add(orderCardRelationUser);
		// 更新代金券使用时间和次数
		ActCard actCard = actCardService.findActCardByCardNo(orderParam.getCardNo());
		if (ObjectUtils.notEqual(null, actCard)) {
			actCard.setUsedTime(new Date());// 使用时间
			actCardService.updateByCardNo(actCard.getCardNo());
		}
		//this.destoryWxCard(orderParam.getCardNo(), orderParam.getOrderCode(), cardAmount);
		return orderFeeUser;
	}

	/**
	 * 销毁微信卡券
	 * 
	 * @param cardno
	 *            卡号
	 * @param orderCodeTmp
	 *            订单编号
	 * @param cardAmount
	 *            卡金额
	 */
//	private void destoryWxCard(String cardno, String orderCodeTmp, BigDecimal cardAmount) {
//		try {
//			boolean flag = this.destoryCard(orderCodeTmp, cardno, cardAmount);
//			if (!flag) {
//				throw new Exception("订单创建微信卡券销毁异常.");
//			}
//		} catch (Exception e) {
//			LOGG.error("create订单创建微信卡券销毁异常:" + e);
//		}
//	}

	/**
	 * 销毁微信优惠卡券
	 * 
	 * @param memberId
	 * @param orderCode
	 * @param cardno
	 * @param cardAmount
	 *            卡金额
	 */
//	private boolean destoryCard(String orderCode, String cardno, BigDecimal cardAmount) {
//		if (StringUtils.isBlank(cardno)) {
//			return false;
//		}
//		String cardnoId = UseWXCrad.getCardIdByCode(cardno);
//		if (StringUtils.isBlank(cardnoId)) {
//			return false;
//		}
//		String cardnoIdTmp = UseWXCrad.destoryCard(cardno, cardnoId);
//		if (StringUtils.isBlank(cardnoIdTmp)) {
//			return false;
//		}
//		LOGG.debug("微信优惠卡券销毁成功!卡券:" + cardno);
//		LogWxCardVoucher cardVoucher = new LogWxCardVoucher();
//		cardVoucher.setCardId(cardnoId);
//		cardVoucher.setCode(cardno);
//		cardVoucher.setReduceCost(String.valueOf(cardAmount.doubleValue()));
//		cardVoucher.setOrderId(orderCode);
//		logWxCardVoucherService.addCardVoucher(cardVoucher);
//		return true;
//	}

	public void updateInventoryByCart(List<ShoppingCartSku> cartItemList) {
		for (ShoppingCartSku cartSkuItem : cartItemList) {
			// 库存数量减少更新
			SkuInventory skuInventory = skuInventoryService.findBySkuCode(cartSkuItem.getSkuCode());
			if (skuInventory != null) {
				int availableQty = skuInventory.getAvailableQty() - cartSkuItem.getQuantity();
				skuInventoryService.updateInventoryQuantity(cartSkuItem.getSkuCode(), availableQty);
			}
		}
	}

	public void deleteCartByCart(List<ShoppingCartSku> cartItemList) {
		for (ShoppingCartSku cartSkuItem : cartItemList) {
			ShoppingCartSku result =  shoppingCartSkuUserService.findBySkuCodeAndMemberId(cartSkuItem.getSkuCode(), cartSkuItem.getMemberId());
			if(result==null){
				continue;
			}
			shoppingCartSkuUserService.deleteById(result.getId());
		}
	}

	public void saveOrderMemberAddress(OrderParamUser orderParam) {
		Member userMember = memberService.findById(orderParam.getMemberId());
		OrderMemberAddressUser orderMemberAddressUser = new OrderMemberAddressUser();
		MemberAddress selectArea = memberAddressService.findById(Long.valueOf(orderParam.getAddressId()));
		orderMemberAddressUser.setProvince(selectArea.getProvince());// 省市区存编码
		orderMemberAddressUser.setCity(selectArea.getCity());
		orderMemberAddressUser.setDistrict(selectArea.getDistrict());
		orderMemberAddressUser.setAddress(selectArea.getAddress());
		orderMemberAddressUser.setContactPerson(selectArea.getContactor());
		orderMemberAddressUser.setStatus(OrderConstant.UNMODIFIED_ADDRESS);// 0地址未修改，1地址已修改
		orderMemberAddressUser.setMemberEmail(selectArea.getEmail());
		orderMemberAddressUser.setMemberId(orderParam.getMemberId());
		orderMemberAddressUser.setMemberName(getUserName(userMember));
		orderMemberAddressUser.setReceiver(selectArea.getContactor());
		orderMemberAddressUser.setReceiverMobile(selectArea.getMobile());
		orderMemberAddressUser.setReceiverPhone(selectArea.getTelephone());
		orderMemberAddressUser.setOrderCode(orderParam.getOrderCode());
		orderMemberAddressUserService.add(orderMemberAddressUser);
	}

	public Jsonp checkSkuInventory(ShoppingCartBo shoppingCartBo) {
		
		ShoppingCartQueryCondition queryCondition = new  ShoppingCartQueryCondition();
		queryCondition.setIsSelected(CommonConstant.YES);
		queryCondition.setMemberId(shoppingCartBo.getUser().getMemberId());
		queryCondition.setStoreCode(shoppingCartBo.getStoreCode());
		
		List<ShoppingCartSku> cartItemList = shoppingCartSkuUserService.findByQueryCondition(queryCondition);
		if (ObjectUtils.equals(null, cartItemList)) {
			return Jsonp.newInstance(ShoppingCartCaptChaEnum.SKU_NO.getCode(), ShoppingCartCaptChaEnum.SKU_NO.getMsg());
		}
		// 库存不足校验、出现库存不足客户端提示、停止整个订单
		for (ShoppingCartSku cartItem : cartItemList) {
			Sku skuEntry = skuService.queryBySkuCode(cartItem.getSkuCode());
			if (ObjectUtils.equals(null, skuEntry)) {
				continue;
			}
			if (!StringUtils.equals(skuEntry.getStatus(), SkuStatusEnum.SKU_STATUS_TOP.getStrVlue())) {

				return Jsonp.newInstance(SkuInventoryCheckEnum.SKU_OFF_SHElVES.getCode(), skuEntry.getName()
						+ SkuInventoryCheckEnum.SKU_OFF_SHElVES.getMsg());
			}
			SkuInventory skuInventory = skuInventoryService.findBySkuCode(skuEntry.getUniqueCode());
			Integer availableQty = skuInventory == null ? 0 : skuInventory.getAvailableQty();
			int quantityNumber = cartItem.getQuantity();
			if (availableQty == null || availableQty < quantityNumber) {
				return Jsonp.newInstance(SkuInventoryCheckEnum.SKU_INVENTORY_FULL.getCode(), skuEntry.getName()
						+ SkuInventoryCheckEnum.SKU_INVENTORY_FULL.getMsg());
			}
		}
		return Jsonp.success();
	}

	/**
	 * 得到用户名
	 * 
	 * @param userMember
	 *            用户名
	 * @return
	 */
	private String getUserName(Member userMember) {
		String userName = StringUtils.EMPTY;
		if (ObjectUtils.notEqual(null, userMember)) {
			userName = StringUtils.equals(null, userMember.getLoginName()) ? StringUtils.EMPTY : userMember
					.getLoginName();
		}
		return userName;
	}

	public List<ShoppingCartSku> saveOrderLineByCart(String orderCode,ShoppingCartBo shoppingCartBo ) {
		Long memberId =  shoppingCartBo.getUser().getMemberId();
		ShoppingCartQueryCondition queryCondition = new  ShoppingCartQueryCondition();
		queryCondition.setIsSelected(CommonConstant.YES);
		queryCondition.setMemberId(shoppingCartBo.getUser().getMemberId());
		queryCondition.setStoreCode(shoppingCartBo.getStoreCode());
		List<ShoppingCartSku> cartItemList = shoppingCartSkuUserService.findByQueryCondition(queryCondition);
		for (ShoppingCartSku cartSkuItem : cartItemList) {
			Sku skuEntry = skuService.queryBySkuCode(cartSkuItem.getSkuCode());// 根据商品code拿对应的商品
			SkuPrice skuPrice = skuPriceService.findBySkuCode(cartSkuItem.getSkuCode());
			SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(cartSkuItem.getSkuCode());
			OrderLineUser soSoLine = new OrderLineUser();
			soSoLine.setOrderCode(orderCode);
			soSoLine.setMemberId(memberId);
			soSoLine.setSkuCode(skuEntry.getUniqueCode());
			soSoLine.setLineType(skuExtraInfo.getIsGift());
			soSoLine.setColor(cartSkuItem.getColor());
			soSoLine.setSize(cartSkuItem.getSize());
			soSoLine.setCostPrice(skuPrice.getCostPrice());
			soSoLine.setSkuName(skuEntry.getName());
			soSoLine.setSkuRetailPrice(skuPrice.getRetailPrice());// 商品原价
			soSoLine.setUnitPrice(cartSkuItem.getCurrentPrice());// 商品现价
			soSoLine.setIsComment(CommonConstant.NO);
			soSoLine.setQuantity(cartSkuItem.getQuantity());

			BigDecimal quantity = new BigDecimal(cartSkuItem.getQuantity());
			BigDecimal cartItemRowsTotal = cartSkuItem.getCurrentPrice().multiply(quantity);
			soSoLine.setTotalPrice(cartItemRowsTotal);
			soSoLine.setTotalActual(cartItemRowsTotal);
			orderLineUserService.add(soSoLine);
		}
		return cartItemList;
	}

	public void modifyMemberAccountBalance(OrderParamUser orderParam, UserDto user) {
		BigDecimal accountMoneyUsd = StringUtils.isBlank(orderParam.getAccountMoneyUsd()) ? new BigDecimal(0)
				: new BigDecimal(orderParam.getAccountMoneyUsd());
		Member member = memberService.findById(user.getMemberId());
		BigDecimal availableMoney = member.getAvailableMoney();
		if (ObjectUtils.equals(null, availableMoney)) {
			return;
		}
		if (ObjectUtils.equals(BigDecimal.valueOf(0).doubleValue(), Double.valueOf(0))) {
			return;
		}
		if (availableMoney.doubleValue() < accountMoneyUsd.doubleValue()) {
			return;
		}
		member.setAvailableMoney(availableMoney.subtract(accountMoneyUsd));
		memberService.update(member);
	}



}
