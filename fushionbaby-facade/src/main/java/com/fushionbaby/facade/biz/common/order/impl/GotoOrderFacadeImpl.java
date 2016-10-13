package com.fushionbaby.facade.biz.common.order.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.SettlementConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.GotoOrderDto;
import com.fushionbaby.common.dto.order.GotoOrderLineDto;
import com.fushionbaby.common.util.EpointsUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.model.SysmgrSfFreightConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.config.service.SysmgrSfFreightConfigService;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.GotoOrderFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.facade.image.ImageProcess;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAddressService;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuPriceService;
import com.fushionbaby.sku.service.SkuService;

/**
 * @author mengshaobo
 *
 */
@Service
public class GotoOrderFacadeImpl implements GotoOrderFacade {
	@Autowired
	private MemberAddressService<MemberAddress> memberAddressService;
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private SkuFacade skuFacade;
	
	@Autowired
	private SkuPriceService<SkuPrice> skuPriceService;
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	@Autowired
	private UserFacade userFacade;
	@Autowired
	private ImageProcess imageProcess;
	@Autowired
	private SysmgrSfFreightConfigService<SysmgrSfFreightConfig> sysmgrSfFreightConfigService;
	
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> soShoppingCartSkuWebService;
	
	public GotoOrderDto initGotoOrderDto(ShoppingCartBo shoppingCartBo) {
		Long memberId = shoppingCartBo.getUser().getMemberId();
		ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition(); 
		queryCondition.setMemberId(memberId);
		queryCondition.setIsSelected(CommonConstant.YES);
		queryCondition.setStoreCode(shoppingCartBo.getStoreCode());
		
		GotoOrderDto gotoOrderDto = new GotoOrderDto();
		List<ShoppingCartSku>  cartItemList = soShoppingCartSkuWebService.findByQueryCondition(queryCondition);
		gotoOrderDto = this.addGotoOrderLines(cartItemList,memberId);
		gotoOrderDto = this.giveGotoOrderDefaultAddress(gotoOrderDto,memberId);
		return initGotoOrder(shoppingCartBo.getUser(), gotoOrderDto);
	}
	
	public GotoOrderDto initGotoOrder(UserDto user,GotoOrderDto gotoOrderDto){
		String  skuCurrentTotalPrice = ObjectUtils.equals(null, gotoOrderDto.getSkuTotal()) ? "0.00" : gotoOrderDto.getSkuTotal();
		String  skuRetailTotalPrice = ObjectUtils.equals(null, gotoOrderDto.getRetailPriceTotal()) ? "0.00" : gotoOrderDto.getRetailPriceTotal();
		BigDecimal orderTotalActual = new BigDecimal(0);// 订单总计金额
		orderTotalActual = new BigDecimal(skuCurrentTotalPrice).add(new BigDecimal(ObjectUtils.equals(null,gotoOrderDto.getFreight()) ? "0.00" : gotoOrderDto.getFreight()));
		gotoOrderDto.setTotalActual(NumberFormatUtil.numberFormat(orderTotalActual));
		gotoOrderDto.setEpoints(user.getEpoints());
		
		BigDecimal canEpoints = new BigDecimal(skuCurrentTotalPrice).compareTo(EpointsUtil.epointsToMoney(user.getEpoints())) == -1 ? new BigDecimal(skuCurrentTotalPrice) :  EpointsUtil.epointsToMoney(user.getEpoints()) ; 
		gotoOrderDto.setCanEpoints( EpointsUtil.moneyToEpoints(canEpoints) );
		gotoOrderDto.setEpointsMoney(NumberFormatUtil.numberFormat(canEpoints));
		gotoOrderDto.setQuantityTotal(ObjectUtils.equals(null, gotoOrderDto.getQuantityTotal()) ?  Integer.valueOf(0) : gotoOrderDto.getQuantityTotal());
		String payoffId = RandomNumUtil.getUUIDString();// 结算序列ID
		gotoOrderDto.setPayOffId(payoffId);
		DataCache.put(payoffId, payoffId);
		
		DataCache.put(payoffId + SettlementConstant._SKU_ACTUAL_PRICE, new BigDecimal(skuCurrentTotalPrice)); 
		DataCache.put(payoffId + SettlementConstant._RETAIL_PRICE, new BigDecimal(skuRetailTotalPrice));// 商品总金额
		DataCache.put(payoffId + SettlementConstant._SKU_PRICE, new BigDecimal(skuCurrentTotalPrice));// 商品现价总金额
		DataCache.put(payoffId + SettlementConstant._TOTAL_ACTUAL, orderTotalActual);// 订单总金额
		DataCache.put(payoffId + SettlementConstant._FREIGHT_PRICE, new BigDecimal(gotoOrderDto.getFreight()));// 运费
		DataCache.put(payoffId + SettlementConstant._INTEGRAL_PRICE, BigDecimal.valueOf(0));// 积分价格
		DataCache.put(payoffId + SettlementConstant._CARDNO_PRICE, BigDecimal.valueOf(0));// 商品优惠券
		DataCache.put(payoffId + SettlementConstant._INTEGRAL, user.getEpoints());// 积分数量
		DataCache.put(payoffId + SettlementConstant._ACCOUNT_BALANCE, BigDecimal.valueOf(0)); //礼品金额
		DataCache.put(payoffId + SettlementConstant._RED_ENVELOPE, BigDecimal.valueOf(0)); //红包金额
		DataCache.put(payoffId + SettlementConstant._CARDNO_PRICE, BigDecimal.valueOf(0)); //优惠券金额
		return gotoOrderDto;
	}
	
	private GotoOrderDto  addGotoOrderLines(List<ShoppingCartSku> cartItemList,Long memberId){
		GotoOrderDto gotoOrderDto = new GotoOrderDto();
		List<GotoOrderLineDto> orderLines = new ArrayList<GotoOrderLineDto>();
		int quantityTotal = 0;// 购买总数量
		BigDecimal skuCurrentTotalPrice = new BigDecimal(0);// 商品现价总金额
		BigDecimal skuRetailTotalPrice = new BigDecimal(0);// 商品吊牌价总金额
		for (ShoppingCartSku cartSkuItem : cartItemList) {
			Sku skuEntry = skuService.queryBySkuCode(cartSkuItem.getSkuCode());// 根据商品code拿到对应的商品
			SkuPrice skuPrice = skuPriceService.findBySkuCode(cartSkuItem.getSkuCode());
			GotoOrderLineDto gotoOrderLineDto = new GotoOrderLineDto();
			gotoOrderLineDto.setSkuCode(cartSkuItem.getSkuCode());
			gotoOrderLineDto.setSkuName(skuEntry.getName());
			
			SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(cartSkuItem.getSkuCode());
			
			BigDecimal curentPrice = skuFacade.getCurrentSkuPrice(cartSkuItem.getSkuCode(), memberId, skuExtraInfo.getIsMemberSku());
			
			
			gotoOrderLineDto.setCurrentPrice(NumberFormatUtil.numberFormat(curentPrice));
			gotoOrderLineDto.setOrigPrice(NumberFormatUtil.numberFormat(skuPrice.getCurrentPrice()));
			gotoOrderLineDto.setRetailPrice(NumberFormatUtil.numberFormat(skuPrice.getRetailPrice()));
			gotoOrderLineDto.setRequestedQty(cartSkuItem.getQuantity());
			gotoOrderLineDto.setImgPath(imageProcess.getThumImagePath(skuEntry.getUniqueCode(), ImageStandardConstant.IMAGE_STANDARD_APP_190));// 图片路径
			gotoOrderLineDto.setColor(cartSkuItem.getColor());
			gotoOrderLineDto.setSize(cartSkuItem.getSize());
			BigDecimal currentPriceTotal = curentPrice.multiply(new BigDecimal(cartSkuItem.getQuantity()));
			gotoOrderLineDto.setCurrentPriceTotal(NumberFormatUtil.numberFormat(currentPriceTotal));
			
			
		
			orderLines.add(gotoOrderLineDto);
			
			BigDecimal quantity = new BigDecimal(cartSkuItem.getQuantity());// 购物行中商品数量
			BigDecimal cartItemRowsTotal = curentPrice.multiply(quantity);// 购物行中商品现价小计总额
		//	BigDecimal cartItemRowsRetailTotal = skuPrice.getRetailPrice().multiply(quantity);// 购物行中商品现价小计总额
			skuCurrentTotalPrice = skuCurrentTotalPrice.add(cartItemRowsTotal);// 商品现价总金额
		//	skuRetailTotalPrice = skuRetailTotalPrice.add(cartItemRowsRetailTotal);// 商品吊牌价总金额
			quantityTotal = quantityTotal + cartSkuItem.getQuantity();
		}
		gotoOrderDto.setOrderLineItems(orderLines);
		gotoOrderDto.setRetailPriceTotal(NumberFormatUtil.numberFormat(skuRetailTotalPrice));
		gotoOrderDto.setSkuTotal(NumberFormatUtil.numberFormat(skuCurrentTotalPrice));
		gotoOrderDto.setQuantityTotal(quantityTotal);
		return gotoOrderDto;
	}
	
	public GotoOrderDto giveGotoOrderDefaultAddress(GotoOrderDto gotoOrderDto,Long memberId){
		Long addressId = memberService.findById(memberId).getDefaultAddressId();
		MemberAddress memberAddress = memberAddressService.findById(addressId);
		if(ObjectUtils.equals(null, memberAddress)){
			gotoOrderDto.setFreight("0.00");
			return gotoOrderDto;
		}
		gotoOrderDto.setDefaultAddressId(memberAddress.getId());
		gotoOrderDto.setContactor(memberAddress.getContactor());
		gotoOrderDto.setProvince(memberAddress.getProvince());
		gotoOrderDto.setCity(memberAddress.getCity());
		gotoOrderDto.setDistrict(memberAddress.getDistrict());
		gotoOrderDto.setAddress(memberAddress.getAddress());
		gotoOrderDto.setMobile(memberAddress.getMobile());
		String provinceName = memberAreaService.getNameByAreaCode(gotoOrderDto.getProvince());
		String cityName = memberAreaService.getNameByAreaCode(gotoOrderDto.getCity());
		String districtName = memberAreaService.getNameByAreaCode(gotoOrderDto.getDistrict());
		gotoOrderDto.setDistrictName(districtName);
		gotoOrderDto.setProvinceName(provinceName);
		gotoOrderDto.setCityName(cityName);
		gotoOrderDto.setFreight(this.getPayableTransferFee(gotoOrderDto, memberAddress));
		return gotoOrderDto;
	}
	
	private String getPayableTransferFee(GotoOrderDto gotoOrderDto,MemberAddress memberAddress){
		String  skuCurrentTotalPrice = ObjectUtils.equals(null, gotoOrderDto.getSkuTotal()) ? "0.00" : gotoOrderDto.getSkuTotal();
		SysmgrGlobalConfig sysmgrGlobalConfig = sysmgrGlobalConfigService.findByCode(GlobalConfigConstant.FREE_FREIGHT_CODE);
		if (ObjectUtils.notEqual(sysmgrGlobalConfig, null)) {
			Double freeFreight = Double.valueOf(sysmgrGlobalConfig.getValue());
			if (Double.valueOf(skuCurrentTotalPrice)  < freeFreight) {
				SysmgrSfFreightConfig sysmgrSfFreight = sysmgrSfFreightConfigService.findByAreaCode(memberAddress.getProvince());
				if (ObjectUtils.notEqual(null, sysmgrSfFreight)) {
					return NumberFormatUtil.numberFormat(sysmgrSfFreight.getAmount());
				}
			}
		}
		return "0.00";
	}

	

	public boolean checkIsSupportHDFK(String provinceCode) {
		SysmgrGlobalConfig sysmgrGlobalConfig = sysmgrGlobalConfigService.findByCode(GlobalConfigConstant.HDFK_AREA_CODE);
		if(ObjectUtils.equals(null, sysmgrGlobalConfig) || StringUtils.equals(null, sysmgrGlobalConfig.getValue())){
			return false; 
		}
		String hdfkAreaCode = sysmgrGlobalConfig.getValue();//全局配置的区域值,逗号分割
		String area[] = hdfkAreaCode.split(",");
		int j = 0;
		for (int i = 0; i < area.length; i++) {
			if (area[i].equals(provinceCode)) {
				j++;// 如果匹配j++，跳出循环
				break;
			}
		}
		if (j <= 0) {
			return false; 
		}
		return true;
	}

}
