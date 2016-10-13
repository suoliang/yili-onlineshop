package com.fushionbaby.app.controller.store;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.store.model.Store;
import com.aladingshop.store.model.StoreConfig;
import com.aladingshop.store.model.StoreMemberAddress;
import com.aladingshop.store.querycondition.StoreQueryCondition;
import com.aladingshop.store.service.StoreMemberAddressService;
import com.fushionbaby.app.controller.order.CreateOrderController;
import com.fushionbaby.app.dto.store.StoreConfigDto;
import com.fushionbaby.app.dto.store.StoreDto;
import com.fushionbaby.app.dto.store.StoreOrderDto;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.OrderConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonCode;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.SettlementConstant;
import com.fushionbaby.common.constants.store.StoreStatusConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.GotoOrderLineDto;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.SendDateEnum;
import com.fushionbaby.common.enums.ShoppingCartCaptChaEnum;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.EpointsUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.OrderBaseUser;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.model.OrderMemberAddressUser;
import com.fushionbaby.core.model.OrderTransUser;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.OrderBaseUserService;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.core.service.OrderMemberAddressUserService;
import com.fushionbaby.core.service.OrderTransUserService;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.core.vo.OrderParamUser;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.CreateOrderFacade;
import com.fushionbaby.facade.biz.common.order.GotoOrderFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.Sku;

/**
 * 门店订单
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月12日下午5:51:07
 */
@Controller
@RequestMapping("store/order")
public class StoreOrderController extends BaseStoreController {

	
	private static final Log LOGG = LogFactory.getLog(CreateOrderController.class);
	
	@Autowired
	private UserFacade userFacade;
	
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> shoppingCartSkuUserService;
	@Autowired
	private CreateOrderFacade createOrderFacade;
	@Autowired
	private GotoOrderFacade gotoOrderFacade;
	@Autowired
	private MemberService<Member> memberService;
	
	@Autowired
	private StoreMemberAddressService<StoreMemberAddress> storeMemberAddressService;
	
	@Autowired
	private OrderBaseUserService<OrderBaseUser> orderBaseUserService;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private OrderTransUserService<OrderTransUser> orderTransUserService;
	@Autowired
	private OrderMemberAddressUserService<OrderMemberAddressUser> orderMemberAddressUserService;
	
	/**
	 * 进入订单页
	 * @param sid
	 * @param storeCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("gotoOrder")
	public Object gotoOrder(String sid,String storeCode){
		
		if(StringUtils.isBlank(storeCode)){
			return Jsonp.error("门店编号是空!");
		}
		StoreQueryCondition storeQueryCondition = new StoreQueryCondition();
		storeQueryCondition.setCode(storeCode);
		storeQueryCondition.setStatus(StoreStatusConstant.OPEN);
		List<Store> storeList = storeService.queryByCondition(storeQueryCondition);
		if(storeList==null){
			return Jsonp.error("门店暂未开通");
		}
		
		if (StringUtils.isBlank(sid)) {
			return Jsonp.paramError("sid不能为空!");
		}
		UserDto user = userFacade.getLatestUserBySid(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		
		try {
			ShoppingCartQueryCondition cartQueryCondition = new ShoppingCartQueryCondition();
			cartQueryCondition.setMemberId(user.getMemberId());
			cartQueryCondition.setStoreCode(storeCode);
			cartQueryCondition.setIsSelected(CommonConstant.YES);
			List<ShoppingCartSku> cartItemList = shoppingCartSkuUserService.findByQueryCondition(cartQueryCondition);
			if (CollectionUtils.isEmpty(cartItemList)) {
				String resultCode = ShoppingCartCaptChaEnum.SHOPPING_CART_SELECTED_FALSE.getCode();
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
			for (ShoppingCartSku cartSkuItem : cartItemList) {
				Sku skuEntry = skuService.queryBySkuCode(cartSkuItem.getSkuCode());
				if(!StringUtils.equals(skuEntry.getStatus(),SkuStatusEnum.SKU_STATUS_TOP.getStrVlue())){
					return Jsonp.error("（"+skuEntry.getName()+"）该商品已下架,请从购物车中移除该商品！");
				}
				if (ObjectUtils.equals(null, skuEntry)) {
					return Jsonp.error();
				}
			}
			ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
			shoppingCartBo.setUser(user);
			shoppingCartBo.setStoreCode(storeCode);
			
			Jsonp checkSkuInventory = createOrderFacade.checkSkuInventory(shoppingCartBo);
			if(!StringUtils.equals(checkSkuInventory.getResponseCode(),CommonCode.SUCCESS_CODE)){
				return checkSkuInventory;
			}
			
			StoreOrderDto storeOrderDto = this.getStoreOrder(shoppingCartBo);
			storeOrderDto = this.getStoreAddress(storeOrderDto, storeCode, user.getMemberId());
			
			
			
			if(!ObjectUtils.equals(null, storeOrderDto)){
				return Jsonp_data.success(storeOrderDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Jsonp.error();
		}
	    return Jsonp.error();
		
	}
	/**
	 *获得 门店订单信息 
	 * @param shoppingCartBo
	 * @return
	 */
	private StoreOrderDto getStoreOrder(ShoppingCartBo shoppingCartBo){
		Long memberId = shoppingCartBo.getUser().getMemberId();
		ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition(); 
		queryCondition.setMemberId(memberId);
		queryCondition.setIsSelected(CommonConstant.YES);
		queryCondition.setStoreCode(shoppingCartBo.getStoreCode());
		List<ShoppingCartSku>  cartItemList = shoppingCartSkuUserService.findByQueryCondition(queryCondition);
		StoreOrderDto storeOrderDto = new StoreOrderDto();
		List<GotoOrderLineDto> orderLines = new ArrayList<GotoOrderLineDto>();
		BigDecimal totalPrice = new BigDecimal(0);
		for (ShoppingCartSku cartSkuItem : cartItemList) {
			Sku skuEntry = skuService.queryBySkuCode(cartSkuItem.getSkuCode());// 根据商品code拿到对应的商品
			GotoOrderLineDto gotoOrderLineDto = new GotoOrderLineDto();
			gotoOrderLineDto.setSkuCode(cartSkuItem.getSkuCode());
			gotoOrderLineDto.setSkuName(skuEntry.getName());
			
			gotoOrderLineDto.setCurrentPrice(NumberFormatUtil.numberFormat(cartSkuItem.getCurrentPrice()));
			gotoOrderLineDto.setRequestedQty(cartSkuItem.getQuantity());
			BigDecimal cartItemRowsTotal = cartSkuItem.getCurrentPrice().multiply(new BigDecimal(cartSkuItem.getQuantity()));// 购物行中商品现价小计总额
			gotoOrderLineDto.setCurrentPriceTotal(NumberFormatUtil.numberFormat(cartItemRowsTotal));
			BeanNullConverUtil.nullConver(gotoOrderLineDto);
			orderLines.add(gotoOrderLineDto);
			totalPrice = totalPrice.add(cartItemRowsTotal);// 商品现价总金额
			
		}
	//	storeOrderDto.setTotalActual(NumberFormatUtil.numberFormat(totalPrice));
		StoreConfigDto storeConfigDto = super.getStoreConfigDto(shoppingCartBo.getStoreCode());
		storeConfigDto.setShopHours("营业时间:" + storeConfigDto.getShopHours() + ",如超过营业时间下单,配送会延迟。");
		
		storeOrderDto.setStoreConfig(storeConfigDto);
		
		if(totalPrice.compareTo(  new BigDecimal( storeConfigDto.getFreeFreightAmount() ))  == -1  ){
			storeOrderDto.setTotalActual(NumberFormatUtil.numberFormat(totalPrice.add(new BigDecimal(storeConfigDto.getFreightAmount()))));
		}else{
			storeOrderDto.setTotalActual(NumberFormatUtil.numberFormat(totalPrice ));
		}
		
		storeOrderDto.setOrderLineItems(orderLines);
		String  skuCurrentTotalPrice = ObjectUtils.equals(null, storeOrderDto.getTotalActual()) ? "0.00" : storeOrderDto.getTotalActual();
		String payoffId = RandomNumUtil.getUUIDString();// 结算序列ID
		storeOrderDto.setPayOffId(payoffId);
		DataCache.put(payoffId, payoffId);
		DataCache.put(payoffId + SettlementConstant._SKU_PRICE, totalPrice);// 商品现价总金额
		DataCache.put(payoffId + SettlementConstant._TOTAL_ACTUAL, new BigDecimal(skuCurrentTotalPrice));// 订单总金额
		return storeOrderDto;
	}
	
	/**
	 * 门店订单信息
	 * @param storeOrderDto 
	 * @param storeCode 门店编号
	 * @param memberId
	 * @return
	 */
	private StoreOrderDto getStoreAddress(StoreOrderDto storeOrderDto,String storeCode,Long memberId){
	
		Store store = storeService.findByCode(storeCode);
	
		storeOrderDto.setProvinceCode(store.getProvinceCode());
		storeOrderDto.setProvinceName(store.getProvinceName());
		storeOrderDto.setCityCode(store.getCityCode());
		storeOrderDto.setCityName(store.getCityName());
		storeOrderDto.setCountryCode(store.getCountryCode());
		storeOrderDto.setCountryName(store.getCountryName());
		storeOrderDto.setCellCode(store.getCellCode());
		storeOrderDto.setCellName(store.getCellName());
		
		StoreMemberAddress storeMemberAddress = storeMemberAddressService.findByMemberIdAndStoreCode(memberId, storeCode);
		if(storeMemberAddress!=null){
			storeOrderDto.setAddress(storeMemberAddress.getAddress());
			storeOrderDto.setMobile(storeMemberAddress.getMobile());
			storeOrderDto.setContactor(storeMemberAddress.getContactor());
		}
	
		BeanNullConverUtil.nullConver(storeOrderDto);
		
		return storeOrderDto;
	}

	
	/**
	 * 创建订单
	 * @param sid
	 * @param payoffId
	 * @param addressId
	 * @param sourceCode
	 * @param memo
	 * @param sendDate
	 * @return
	 * @RequestParam("sid") String sid,
			@RequestParam("payOffId") String payoffId,
			@RequestParam("storeCode") String storeCode,
			@RequestParam("sourceCode") String sourceCode,
			@RequestParam("contactor") String contactor,
			@RequestParam("mobile") String mobile,
			@RequestParam("address") String address,
			@RequestParam(value="isPickUp",defaultValue=CommonConstant.NO) String isPickUp,
			@RequestParam(value="memo",defaultValue="") String memo,
			@RequestParam(value="sendDate",defaultValue="") String sendDate
	 */
	@ResponseBody
	@RequestMapping("createOrder")
	public Object createOrder(@RequestParam("sid") String sid,
			@RequestParam("payOffId") String payoffId,
			@RequestParam("storeCode") String storeCode,
			@RequestParam("sourceCode") String sourceCode,
			@RequestParam("contactor") String contactor,
			@RequestParam("mobile") String mobile,
			@RequestParam("address") String address,
			@RequestParam(value="isPickUp",defaultValue=CommonConstant.NO) String isPickUp,
			@RequestParam(value="memo",defaultValue="") String memo,
			@RequestParam(value="sendDate",defaultValue="") String sendDate){
		
		if (StringUtils.isBlank(sid)) {
			return Jsonp.paramError("sid不能为空!");
		}
		if (CheckIsEmpty.isEmpty(payoffId, storeCode)) {
			return Jsonp.paramError(CommonConstant.CommonMessage.PARAM_ERROR_MESSAGE);
		}
		UserDto user = userFacade.getLatestUserBySid(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.noLoginError("请先登录!");
		}
		
		Store store = storeService.findByCode(storeCode);
		if(store==null || StringUtils.isBlank(store.getCode())){
			return Jsonp.error("门店不存在！");
		}
		StoreDto storeDto = this.getStoreDto(store);
		if(!StringUtils.equals(storeDto.getStoreStatus(), StoreStatusConstant.StoreStatusDto.OPEN)){
			return Jsonp.error("该店暂未营业！");
		}
		
		String orderCode = EpointsUtil.generateOrdrNo();//生成订单号
		Long memberId = user.getMemberId();
		ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
		shoppingCartBo.setUser(user);
		shoppingCartBo.setStoreCode(storeCode);
		Jsonp checkSkuInventory = createOrderFacade.checkSkuInventory(shoppingCartBo);
		if(!StringUtils.equals(checkSkuInventory.getResponseCode(),CommonCode.SUCCESS_CODE )){
			return checkSkuInventory;
		}
		// 现价总金额
		BigDecimal currentPriceTotal = (BigDecimal) DataCache.get(payoffId + SettlementConstant._SKU_PRICE);
		// 订单总计
		BigDecimal totalActual = (BigDecimal) DataCache.get(payoffId + SettlementConstant._TOTAL_ACTUAL);
		OrderParamUser orderParam = new OrderParamUser();
		orderParam.setMemberId(memberId);
		orderParam.setStoreCode(storeCode);
		orderParam.setOrderCode(orderCode);
		orderParam.setSendDate(SendDateEnum.getTitle(sendDate));
		orderParam.setMemo(memo);
		orderParam.setSourceCode(sourceCode);
		orderParam.setCurrentPriceTotal(currentPriceTotal);
		orderParam.setTotalActual(totalActual);
		orderParam.setIsPickUp(isPickUp);
		try {
			this.saveOrderBaseInfo(orderParam);
			this.saveOrderFeeInfo(orderParam);
			this.saveOrderFianace(orderParam);
			this.saveOrderTrans(orderParam);
			List<ShoppingCartSku> cartItemList = createOrderFacade.saveOrderLineByCart(orderParam.getOrderCode(), shoppingCartBo);
			createOrderFacade.updateInventoryByCart(cartItemList);
			createOrderFacade.deleteCartByCart(cartItemList);
			StoreMemberAddress	storeMemberAddress = 
					storeMemberAddressService.findByMemberIdAndStoreCode(orderParam.getMemberId(), orderParam.getStoreCode());
			if(storeMemberAddress==null){
				storeMemberAddress = new StoreMemberAddress();
			}
			storeMemberAddress.setAddress(address);
			storeMemberAddress.setContactor(contactor);
			storeMemberAddress.setMemberId(memberId);
			storeMemberAddress.setMobile(mobile);
			storeMemberAddress.setStoreCode(storeCode);
			storeMemberAddressService.addOrUpdate(storeMemberAddress);
			this.saveOrderMemberAddress(orderParam);
		} catch (Exception e) {
			LOGG.error("订单创建异常:"+e);
			e.printStackTrace();
			return Jsonp.error("创建订单异常");
		}
		return Jsonp_data.success(orderCode);
		
	}
	
	
	
	private void saveOrderMemberAddress(OrderParamUser orderParam){
		
		Member userMember = memberService.findById(orderParam.getMemberId());
		OrderMemberAddressUser orderMemberAddressUser = new OrderMemberAddressUser();
		StoreMemberAddress selectArea = storeMemberAddressService.findByMemberIdAndStoreCode(orderParam.getMemberId(), orderParam.getStoreCode());
		Store store = storeService.findByCode(orderParam.getStoreCode());
		
		orderMemberAddressUser.setProvince(store.getProvinceCode());// 省市区存编码
		orderMemberAddressUser.setCity(store.getCityCode());
		orderMemberAddressUser.setDistrict(store.getCountryCode());
		orderMemberAddressUser.setAddress(store.getCellName() + selectArea.getAddress());
		orderMemberAddressUser.setContactPerson(selectArea.getContactor());
		orderMemberAddressUser.setStatus(OrderConstant.UNMODIFIED_ADDRESS);// 0地址未修改，1地址已修改
		orderMemberAddressUser.setMemberId(orderParam.getMemberId());
		orderMemberAddressUser.setMemberName(userMember.getLoginName());
		orderMemberAddressUser.setReceiver(selectArea.getContactor());
		orderMemberAddressUser.setReceiverMobile(selectArea.getMobile());
		orderMemberAddressUser.setReceiverPhone(selectArea.getMobile());
		orderMemberAddressUser.setOrderCode(orderParam.getOrderCode());
		orderMemberAddressUserService.add(orderMemberAddressUser);
	}
	
	
	private void saveOrderBaseInfo(OrderParamUser orderParam){
		Long memberId = orderParam.getMemberId();
		Member userMember = memberService.findById(memberId);
		OrderBaseUser orderBaseUser = new OrderBaseUser();
		orderBaseUser.setIsPickUp( orderParam.getIsPickUp());
		orderBaseUser.setStoreCode(orderParam.getStoreCode());
		orderBaseUser.setOrderCode(orderParam.getOrderCode());
		orderBaseUser.setMemberId(memberId);
		orderBaseUser.setMemberName(StringUtils.equals(null, userMember.getLoginName()) ? StringUtils.EMPTY : userMember.getLoginName());
		orderBaseUser.setIsRefused(CommonConstant.NO);
		orderBaseUser.setIsDelete(CommonConstant.NO);
		orderBaseUser.setSourceCode(orderParam.getSourceCode());
		orderBaseUser.setOrderPointGained(BigDecimal.ZERO);// 订单多少钱，就多少分
		orderBaseUser.setIsHandlePoint(CommonConstant.NO);
		orderBaseUser.setMemo(StringUtils.isEmpty(orderParam.getMemo()) ? StringUtils.EMPTY : orderParam.getMemo());
		orderBaseUser.setIsGroupBuy(CommonConstant.NO);
		orderBaseUser.setTotalPointUsed(BigDecimal.ZERO);
		BigDecimal dtotalActual = orderParam.getTotalActual();
		if (dtotalActual == null || dtotalActual.compareTo(BigDecimal.ZERO) < 1) {
			orderBaseUser.setOrderStatus(OrderConfigServerEnum.AUDIT.getCode());
		} else {
			orderBaseUser.setOrderStatus(OrderConfigServerEnum.WAITING_PAYMENT.getCode());
		}
		orderBaseUserService.addOrderBase(orderBaseUser);
	}
	
	
	private void saveOrderFeeInfo(OrderParamUser orderParam){
		
		Long memberId = orderParam.getMemberId();
		OrderFeeUser orderFeeUser = new OrderFeeUser();
		orderFeeUser.setStoreCode(orderParam.getStoreCode());
		orderFeeUser.setOrderCode(orderParam.getOrderCode());
		orderFeeUser.setMemberId(memberId);
		orderFeeUser.setTotalActual(orderParam.getTotalActual());// 订单总金额
		orderFeeUser.setTotalAfDiscount(orderParam.getCurrentPriceTotal());// 折扣后总价(当前单价总价)
		orderFeeUser.setRedEnvelopeAmount(BigDecimal.ZERO);
		orderFeeUser.setAlabaoCheapAmount(BigDecimal.ZERO);
		orderFeeUser.setEpointsRedeemMoney(BigDecimal.ZERO);
		orderFeeUser.setPayableTransferFee(BigDecimal.ZERO);// 根据区域拿运费价格
		orderFeeUser.setActualTransferFee(BigDecimal.ZERO);// 实际运费价格
		
		StoreConfig storeConfig = storeConfigService.findByStoreCode(orderParam.getStoreCode());
		if(storeConfig!=null ){
			int freightAmount  = storeConfig.getFreightAmount()==null ? 0:storeConfig.getFreightAmount();
			int freeFreightAmount = storeConfig.getFreeFreightAmount()==null? 0 :storeConfig.getFreeFreightAmount();
			if(orderParam.getTotalActual().compareTo(BigDecimal.valueOf(freeFreightAmount)) ==-1 ){
				orderFeeUser.setPayableTransferFee(BigDecimal.valueOf(freightAmount));// 根据区域拿运费价格
				orderFeeUser.setActualTransferFee(BigDecimal.valueOf(freightAmount));// 实际运费价格
			}
		}
		orderFeeUserService.addOrderFeeUser(orderFeeUser);
		
	}
	
	private void saveOrderFianace(OrderParamUser orderParam){
		OrderFinanceUser orderFinanceUser = new OrderFinanceUser();
		orderFinanceUser.setStoreCode(orderParam.getStoreCode());
		orderFinanceUser.setOrderCode(orderParam.getOrderCode());
		orderFinanceUser.setMemberId(orderParam.getMemberId());
		
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
	
	
	private void saveOrderTrans(OrderParamUser orderParam) {
		OrderTransUser orderTransUser = new OrderTransUser();
		orderTransUser.setStoreCode(orderParam.getStoreCode());
		orderTransUser.setMemberId(orderParam.getMemberId());
		orderTransUser.setOrderCode(orderParam.getOrderCode());
		orderTransUser.setTransStatus(CommonConstant.NO);
		orderTransUser.setSendDate(orderParam.getSendDate());
		orderTransUserService.addOrderTransUser(orderTransUser);
	}
	
	
}
