/**
 * 
 */
package com.fushionbaby.facade.biz.common.order.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.SettlementConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.GotoOrderDto;
import com.fushionbaby.common.dto.order.GotoOrderLineDto;
import com.fushionbaby.common.util.EpointsUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.GotoOrderFacade;
import com.fushionbaby.facade.biz.common.order.OrderImmediatePaymentFacade;
import com.fushionbaby.facade.image.ImageProcess;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAddressService;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.service.SkuPriceService;
import com.fushionbaby.config.model.SysmgrSfFreightConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.config.service.SysmgrSfFreightConfigService;

/**
 * @author mengshaobo
 *
 */
@Deprecated
@Service
public class OrderImmediatePaymentFacadeImpl implements OrderImmediatePaymentFacade {
	
	@Autowired
	private ImageProcess imageProcess;
	@Autowired
	private MemberAddressService<MemberAddress> memberAddressService;
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;
	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	@Autowired
	private SysmgrSfFreightConfigService<SysmgrSfFreightConfig> sysmgrSfFreightService;
	@Autowired
	private UserFacade userFacade;
	@Autowired
	private GotoOrderFacade gotoOrderFacade;
	@Autowired
	private SkuPriceService<SkuPrice> skuPriceService;

	/* (non-Javadoc)
	 * @see com.fushionbaby.facade.biz.common.order.OrderPaymentFacade#immediatePayment()
	 */
	public GotoOrderDto immediatePayment(UserDto user,Sku skuEntry,Integer quantityNumber) {
		GotoOrderLineDto gotoOrderLineDto = new GotoOrderLineDto();// 购物行DTO
		gotoOrderLineDto.setSkuCode(skuEntry.getSkuNo());
		gotoOrderLineDto.setSkuName(skuEntry.getName());
		SkuPrice skuPrice = skuPriceService.findBySkuCode(skuEntry.getSkuNo());
		gotoOrderLineDto.setCurrentPrice(NumberFormatUtil.numberFormat(skuPrice.getCurrentPrice()));
		gotoOrderLineDto.setRequestedQty(quantityNumber);// 购物行中商品数量
		gotoOrderLineDto.setImgPath(imageProcess.getThumImagePath(skuEntry.getSkuNo(), ImageStandardConstant.IMAGE_STANDARD_APP_190));// 图片路径
		List<GotoOrderLineDto> orderLineItems = new ArrayList<GotoOrderLineDto>();
		orderLineItems.add(gotoOrderLineDto);
		GotoOrderDto gotoOrderDto = new GotoOrderDto();
		gotoOrderDto.setQuantityTotal(quantityNumber);
		gotoOrderDto.setOrderLineItems(orderLineItems);//设置购买记录
		BigDecimal quantityBigTmp = new BigDecimal(quantityNumber);// 商品数量
		BigDecimal cartItemRowsTotal = skuPrice.getCurrentPrice().multiply(quantityBigTmp);// 商品现价小计总额
		gotoOrderLineDto.setCurrentPriceTotal(NumberFormatUtil.numberFormat(cartItemRowsTotal));
		BigDecimal currentPriceTotal = cartItemRowsTotal;// 商品现价总金额
		gotoOrderDto.setSkuTotal(NumberFormatUtil.numberFormat(currentPriceTotal));
		/* 默认地址收货地址设置 */
		gotoOrderDto = gotoOrderFacade.giveGotoOrderDefaultAddress(gotoOrderDto, user.getMemberId());
		BigDecimal cartRetailPriceTotal = skuPrice.getRetailPrice().multiply(quantityBigTmp);// 商品原价小计总额
		BigDecimal retailPriceTotal = cartRetailPriceTotal;// 商品原价总金额

		// 运费计算
		BigDecimal freight = new BigDecimal(gotoOrderDto.getFreight());
		BigDecimal payableTransferFee = ObjectUtils.equals(null, freight) ?  BigDecimal.valueOf(0) :  freight ;// 运费金额
		BigDecimal orderPriceTotalActual = currentPriceTotal.add(payableTransferFee);
		orderPriceTotalActual = new BigDecimal(NumberFormatUtil.numberFormat(orderPriceTotalActual));
		gotoOrderDto.setTotalActual(NumberFormatUtil.numberFormat(orderPriceTotalActual));
		gotoOrderDto.setEpoints(user.getEpoints());
		gotoOrderDto.setCanEpoints(userFacade.getCanEpoints(user));
		String epointsToMoney = NumberFormatUtil.numberFormat(EpointsUtil.epointsToMoney(userFacade.getCanEpoints(user)));
		gotoOrderDto.setEpointsMoney(epointsToMoney);
		
		// 将计算结果放到缓存
		String payOffId = RandomNumUtil.getUUIDString();// 结算序列ID
		gotoOrderDto.setPayOffId(payOffId);
		DataCache.put(payOffId, payOffId);		
		
		DataCache.put(payOffId + SettlementConstant._SKU_QUANTITY, quantityNumber);
		DataCache.put(payOffId + SettlementConstant._SKU_CODE, skuEntry.getSkuNo());
		DataCache.put(payOffId + SettlementConstant._SKU_PRICE, currentPriceTotal);// 商品现价总金额
		DataCache.put(payOffId + SettlementConstant._RETAIL_PRICE, retailPriceTotal);// 商品原价总金额
		DataCache.put(payOffId + SettlementConstant._TOTAL_ACTUAL, orderPriceTotalActual);// 订单总金额
		DataCache.put(payOffId + SettlementConstant._FREIGHT_PRICE, payableTransferFee);// 运费
		DataCache.put(payOffId + SettlementConstant._INTEGRAL_PRICE, BigDecimal.valueOf(0));// 积分价格
		DataCache.put(payOffId + SettlementConstant._CARDNO_PRICE, BigDecimal.valueOf(0));// 优惠券
		DataCache.put(payOffId + SettlementConstant._INTEGRAL, user.getEpoints());// 积分数量
		
		return gotoOrderDto;
	}

}
