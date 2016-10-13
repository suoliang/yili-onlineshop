package com.aladingshop.wap.controller.integral;

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
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.SettlementConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartItemDto;
import com.fushionbaby.common.dto.order.GotoOrderDto;
import com.fushionbaby.common.dto.order.GotoOrderLineDto;
import com.fushionbaby.common.enums.ShoppingCartCaptChaEnum;
import com.fushionbaby.common.enums.SkuInventoryCheckEnum;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.EpointsUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.model.SysmgrSfFreightConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.config.service.SysmgrSfFreightConfigService;
import com.fushionbaby.facade.biz.common.member.MemberAddressFacade;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.facade.image.ImageProcess;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAddressService;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuEpoint;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.service.SkuEpointService;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.fushionbaby.sku.service.SkuPriceService;
import com.fushionbaby.sku.service.SkuService;

@Controller
@RequestMapping("/integral")
public class AtOnceExchangeController {
	
	/**注入日志*/
	private static final Log LOGGER = LogFactory.getLog(AtOnceExchangeController.class);
	
	@Autowired
	protected UserFacade userFacade;
	@Autowired
	private SkuService skuService;
	@Autowired
	private ImageProcess imageProcess;
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	@Autowired
	private MemberAddressService<MemberAddress> memberAddressService;
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private SkuPriceService<SkuPrice> skuPriceService;
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	@Autowired
	private SkuEpointService skuEpointService;
	@Autowired
	private SkuFacade skuFacade;
	@Autowired
	private MemberAddressFacade memberAddressFacade;
	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	@Autowired
	private SysmgrSfFreightConfigService<SysmgrSfFreightConfig> sysmgrSfFreightConfigService;
	
	/**
	 * 立即兑换验证
	 * @param skuCode 商品编码
	 * @param sid  登录用户标识
	 * @param type 类型-用于判断是不是APP(传值为APP)端请求的
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "goIntegralCheck")
	public Object goOrderCheck(HttpServletRequest request,
			@RequestParam(value="skuCode")String skuCode,
			@RequestParam(value="sid",defaultValue="")String sid,
			@RequestParam(value="type",defaultValue="")String type) {
		try {
			if (StringUtils.isBlank(sid)) {
				sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
			}
			boolean checkUserLogin = userFacade.checkUserLogin(sid);
			if (!checkUserLogin) {
				return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
			}
			List<CartItemDto> notExistSkuList = new ArrayList<CartItemDto>();
			Sku skuEntry = skuService.queryBySkuCode(skuCode);// 根据商品code拿到对应的商品
			if (skuEntry == null) {
				CartItemDto item = new CartItemDto();
				item.setSkuCode(skuCode);
				notExistSkuList.add(item);
			}
			if (!CollectionUtils.isEmpty(notExistSkuList) && notExistSkuList.size() > 0) {
				String code = ShoppingCartCaptChaEnum.SKU_NO.getCode();
				return Jsonp_data.newInstance(code, notExistSkuList,ShoppingCartCaptChaEnum.getTitle(code));
			}
			// 库存不足校验、出现库存不足客户端提示、停止整个订单
			if (!StringUtils.equals(skuEntry.getStatus(),SkuStatusEnum.SKU_STATUS_TOP.getStrVlue())) {
				return Jsonp.newInstance(
						SkuInventoryCheckEnum.SKU_OFF_SHElVES.getCode(),
						skuEntry.getName() + SkuInventoryCheckEnum.SKU_OFF_SHElVES.getMsg());
			}
			SkuInventory skuInventory = skuInventoryService.findBySkuCode(skuEntry.getUniqueCode());
			Integer availableQty = skuInventory == null ? 0 : skuInventory.getAvailableQty();
			int quantityNumber = 1;
			if (availableQty == null || availableQty < quantityNumber) {
				return Jsonp.newInstance(
						SkuInventoryCheckEnum.SKU_INVENTORY_FULL.getCode(),
						skuEntry.getName() + SkuInventoryCheckEnum.SKU_INVENTORY_FULL.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("兑换验证出错", e);
		}
		return Jsonp.success();
	}
	
	/**
	 * 立即兑换确定
	 * @param skuCode 商品编码
	 * @param sid  登录用户标识
	 * @param type 类型-用于判断是不是APP(传值为APP)端请求的
	 * @return
	 */
	@RequestMapping("goIntegralConfirm")
	public Object goOrder(HttpServletRequest request,
			@RequestParam(value="skuCode")String skuCode,
			@RequestParam(value="sid",defaultValue="")String sid,
			@RequestParam(value="type",defaultValue="")String type,
			Model model) {
		if (StringUtils.isBlank(sid)) {
			sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
		}
		
		if (StringUtils.isBlank(sid)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		UserDto user = userFacade.getLatestUserBySid(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}

		GotoOrderDto gotoOrderDto = new GotoOrderDto();
		gotoOrderDto = addGotoOrderLines(skuCode, user.getMemberId());
		gotoOrderDto = giveGotoOrderDefaultAddress(gotoOrderDto,user.getMemberId());	
		gotoOrderDto = initGotoOrder(user, gotoOrderDto);

		if (CollectionUtils.isEmpty(gotoOrderDto.getOrderLineItems())
				|| gotoOrderDto.getOrderLineItems().size() <= 0) {

			return "redirect:/integral/homeShow";
		}
		Long memberId = user.getMemberId();
		Member userMember = memberService.findById(memberId);
		BigDecimal totalActual = (BigDecimal) DataCache.get(gotoOrderDto.getPayOffId() + SettlementConstant._TOTAL_ACTUAL);
		/** 首先判断用户现有积分能不能下单 */
		if (userMember.getEpoints().compareTo(totalActual) < 0) {
			model.addAttribute("errorMsg", "您当前积分不足");
			return "error";
		}
		model.addAttribute("sid", sid);
		model.addAttribute("user", user);
		model.addAttribute("gotoOrderDto", gotoOrderDto);
		model.addAttribute("addressList", memberAddressFacade.addressList(sid));
		model.addAttribute("time", new Date().getTime());
		model.addAttribute("skuCode", skuCode);
		
		return "point/point-confirm-order";

	}
	
	private GotoOrderDto  addGotoOrderLines(String skuCode,Long memberId){
		GotoOrderDto gotoOrderDto = new GotoOrderDto();
		List<GotoOrderLineDto> orderLines = new ArrayList<GotoOrderLineDto>();
		int quantityTotal = 0;// 购买总数量
		
		Sku skuEntry = skuService.queryBySkuCode(skuCode);// 根据商品code拿到对应的商品
		SkuPrice skuPrice = skuPriceService.findBySkuCode(skuCode);
		GotoOrderLineDto gotoOrderLineDto = new GotoOrderLineDto();
		gotoOrderLineDto.setSkuCode(skuCode);
		gotoOrderLineDto.setSkuName(skuEntry.getName());
		
		/** 商品积分对象 */
		SkuEpoint skuEpoint = skuEpointService.findBySkuCode(skuCode);
		
		gotoOrderLineDto.setCurrentPrice(NumberFormatUtil.numberFormat(skuEpoint.getNeedEpoint()));
		gotoOrderLineDto.setRetailPrice(NumberFormatUtil.numberFormat(skuPrice.getRetailPrice()));
		gotoOrderLineDto.setRequestedQty(1);
		gotoOrderLineDto.setImgPath(imageProcess.getThumImagePath(skuEntry.getUniqueCode(), ImageStandardConstant.IMAGE_STANDARD_APP_190));// 图片路径
		gotoOrderLineDto.setColor(skuEntry.getColor());
		gotoOrderLineDto.setSize(skuEntry.getSize());
		gotoOrderLineDto.setCurrentPriceTotal(NumberFormatUtil.numberFormat(skuEpoint.getNeedEpoint()));
		
		orderLines.add(gotoOrderLineDto);
		
		quantityTotal = quantityTotal + 1;
		
		gotoOrderDto.setOrderLineItems(orderLines);
		gotoOrderDto.setRetailPriceTotal(NumberFormatUtil.numberFormat(skuPrice.getRetailPrice()));
		gotoOrderDto.setSkuTotal(NumberFormatUtil.numberFormat(skuEpoint.getNeedEpoint()));
		gotoOrderDto.setQuantityTotal(quantityTotal);
		return gotoOrderDto;
	}
	
	private GotoOrderDto giveGotoOrderDefaultAddress(GotoOrderDto gotoOrderDto,Long memberId){
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
	
	/** 积分商城运费，换算成积分运费 */
	private String getPayableTransferFee(GotoOrderDto gotoOrderDto,MemberAddress memberAddress){
		String  skuCurrentTotalPrice = ObjectUtils.equals(null, gotoOrderDto.getSkuTotal()) ? "0.00" : gotoOrderDto.getSkuTotal();
		Double skuTotalPrice = Double.valueOf(skuCurrentTotalPrice) * 100d;
		SysmgrGlobalConfig sysmgrGlobalConfig = sysmgrGlobalConfigService.findByCode(GlobalConfigConstant.FREE_FREIGHT_CODE);
		if (ObjectUtils.notEqual(sysmgrGlobalConfig, null)) {
			Double freeFreight = Double.valueOf(sysmgrGlobalConfig.getValue());
			freeFreight = freeFreight * 100d;
			if (skuTotalPrice < freeFreight) {
				SysmgrSfFreightConfig sysmgrSfFreight = sysmgrSfFreightConfigService.findByAreaCode(memberAddress.getProvince());
				if (ObjectUtils.notEqual(null, sysmgrSfFreight)) {
					Double freight = sysmgrSfFreight.getAmount().doubleValue()*100d;
					return freight.toString();
				}
			}
		}
		return "0.00";
	}
	
	public GotoOrderDto initGotoOrder(UserDto user,GotoOrderDto gotoOrderDto){
		String  epoints = ObjectUtils.equals(null, gotoOrderDto.getSkuTotal()) ? "0.00" : gotoOrderDto.getSkuTotal();
		BigDecimal orderTotalActual = new BigDecimal(0);
		/** 订单总计金额(+上运费的)new BigDecimal(ObjectUtils.equals(null,gotoOrderDto.getFreight()) ? "0.00" : gotoOrderDto.getFreight()) */
		orderTotalActual = new BigDecimal(epoints).add(BigDecimal.ZERO);
		gotoOrderDto.setTotalActual(NumberFormatUtil.numberFormat(orderTotalActual));
		gotoOrderDto.setEpoints(user.getEpoints());
		
		BigDecimal canEpoints = new BigDecimal(epoints).compareTo(EpointsUtil.epointsToMoney(user.getEpoints())) == -1 ? new BigDecimal(epoints) :  EpointsUtil.epointsToMoney(user.getEpoints()) ; 
		gotoOrderDto.setCanEpoints( EpointsUtil.moneyToEpoints(canEpoints) );
		gotoOrderDto.setEpointsMoney(NumberFormatUtil.numberFormat(canEpoints));
		gotoOrderDto.setQuantityTotal(ObjectUtils.equals(null, gotoOrderDto.getQuantityTotal()) ?  Integer.valueOf(0) : gotoOrderDto.getQuantityTotal());
		String payoffId = RandomNumUtil.getUUIDString();// 结算序列ID
		gotoOrderDto.setPayOffId(payoffId);
		DataCache.put(payoffId, payoffId);
		
		DataCache.put(payoffId + SettlementConstant._TOTAL_ACTUAL, orderTotalActual);// 订单总金额
		return gotoOrderDto;
	}
	
}
