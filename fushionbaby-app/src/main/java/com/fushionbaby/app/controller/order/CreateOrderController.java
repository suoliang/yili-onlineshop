package com.fushionbaby.app.controller.order;

import java.math.BigDecimal;
import java.util.Date;
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

import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonCode;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.SettlementConstant;
import com.fushionbaby.common.constants.store.StoreConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.GotoOrderDto;
import com.fushionbaby.common.dto.order.GotoOrderLineDto;
import com.fushionbaby.common.dto.order.app.AppGotoOrderDto;
import com.fushionbaby.common.enums.RedEnvelopeUseStatusEnum;
import com.fushionbaby.common.enums.RedEnvelopeUseTypeEnum;
import com.fushionbaby.common.enums.SendDateEnum;
import com.fushionbaby.common.enums.ShoppingCartCaptChaEnum;
import com.fushionbaby.common.util.BeanCopyUtil;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.EpointsUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.core.vo.OrderParamUser;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.CreateOrderFacade;
import com.fushionbaby.facade.biz.common.order.GotoOrderFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.service.SkuService;

/**
 * 
 * @author Leon
 *
 */
@Controller
@RequestMapping("/order")
public class CreateOrderController  {
	
	private static final Log logg = LogFactory.getLog(CreateOrderController.class);
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private GotoOrderFacade gotoOrderFacade;
	@Autowired
	private CreateOrderFacade createOrderFacade;
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> soShoppingCartSkuWebService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	@Autowired
	private UserFacade userFacade;
	@Autowired
	private ActivityShareMemberService<ActivityShareMember> activityShareMemberService;
	@Autowired
	private ActivityRedEnvlopeUseRecordService<ActivityRedEnvlopeUseRecord> activityRedEnvlopeUseRecordService;
	
	@Autowired
	private ActCardService<ActCard> actCardService;
	/**
	 * @see 为下订单页面准备数据 1.准备用户的默认收货地址 2.查询该用户购物车 3.查询用户勾选的购物车、已选中列表
	 * 4.查询到购物车行记录需要的商品参数属性,查询商品表;查询商品信息,有个别字段购物车表没有的字段
	 * 5.根据商品id,查询该购物车的优惠券,查找该商品有没有可以用的优惠购物卷
	 * 
	 * @param sid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("gotoOrder")
	public Object gotoOrder(String sid) {
		try {
			if (StringUtils.isBlank(sid)) {
				return Jsonp.paramError("sid不能为空!");
			}
			UserDto user = userFacade.getLatestUserBySid(sid);
			if (ObjectUtils.equals(null, user)) {
				return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
			}
			ShoppingCartQueryCondition cartQueryCondition = new ShoppingCartQueryCondition();
			cartQueryCondition.setIsSelected(CommonConstant.YES);
			cartQueryCondition.setMemberId(user.getMemberId());
			
			List<ShoppingCartSku> cartItemList = soShoppingCartSkuWebService.findByQueryCondition(cartQueryCondition);
			if (CollectionUtils.isEmpty(cartItemList)) {
				String resultCode = ShoppingCartCaptChaEnum.SHOPPING_CART_SELECTED_FALSE.getCode();
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
			for (ShoppingCartSku cartSkuItem : cartItemList) {
				Sku skuEntry = skuService.queryBySkuCode(cartSkuItem.getSkuCode());
				if (ObjectUtils.equals(null, skuEntry)) {
					return Jsonp.error();
				}
			}
			ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
			shoppingCartBo.setUser(user);
			Jsonp checkSkuInventory = createOrderFacade.checkSkuInventory(shoppingCartBo);
			if(!StringUtils.equals(checkSkuInventory.getResponseCode(),CommonCode.SUCCESS_CODE)){
				return checkSkuInventory;
			}
			GotoOrderDto gotoOrderDto = gotoOrderFacade.initGotoOrderDto(shoppingCartBo);
			if(ObjectUtils.equals(null, gotoOrderDto)){
				return Jsonp.error();
			}
			BeanNullConverUtil.nullConver(gotoOrderDto);
			AppGotoOrderDto appGotoOrderDto = new AppGotoOrderDto();
			BeanCopyUtil.copyProperty(gotoOrderDto, appGotoOrderDto, null);
			List<GotoOrderLineDto> orderLines = gotoOrderDto.getOrderLineItems();
			for (GotoOrderLineDto orderLine : orderLines) {
				orderLine.setDicountPriceTotal(NumberFormatUtil.numberFormat(new BigDecimal(orderLine.getCurrentPriceTotal()).multiply(this.discountRate())));
			}
			appGotoOrderDto.setOrderLineItems(orderLines);
			
			BigDecimal skuTotalPrice = new BigDecimal(gotoOrderDto.getSkuTotal());
			BigDecimal discountPrice = skuTotalPrice.multiply(this.discountRate());
			BigDecimal orderActualPrice = new BigDecimal(gotoOrderDto.getTotalActual());
			
			appGotoOrderDto.setTotalActual(NumberFormatUtil.numberFormat(orderActualPrice.subtract(skuTotalPrice).add(discountPrice)));
			appGotoOrderDto.setDiscountMoney(NumberFormatUtil.numberFormat(skuTotalPrice.subtract(discountPrice)));
			
			BeanNullConverUtil.nullConver(appGotoOrderDto);
			appGotoOrderDto.setRedMoney(this.getRedMoney(user.getMemberId(),skuTotalPrice));
			appGotoOrderDto.setUsableCouponCount("共 " +this.couponCount(user.getMemberId())  + " 张");
			/**红包使用说明 */
			String url="";
			SysmgrGlobalConfig config = sysmgrGlobalConfigService.findByCode(GlobalConfigConstant.RED_USE_DETAIL_URL);
            if(config!=null&&config.getValue()!=null)
            	url=config.getValue();
            appGotoOrderDto.setRedUrl(url);
            /**红包使用说明 */
			
			return Jsonp_data.success(appGotoOrderDto);
		} catch (Exception e) {
			e.printStackTrace();
			return Jsonp.error();
		}
		

	}
	
	
	private Integer couponCount(Long memberId){
		
		List<ActCard> actCardList=this.actCardService.findByMemberId(memberId);
		int count = 0;

		for (ActCard actCard : actCardList) {
			if(actCard.getStopTime().after(new Date()) && 
			   actCard.getUsedTime() == null && 
			   actCard.getStartTime().before(new Date())){
				count++;
			}
		}
		return count;
		
		
	}
	/**
	 * 获取红包可用金额
	 * @param memberId
	 * @return
	 */
	private BigDecimal getRedMoney(Long memberId,BigDecimal skuTotalPrice){
			
	
			ActivityShareMember	activityShareMember= activityShareMemberService.findByMemberId(memberId);
			if(activityShareMember!=null && activityShareMember.getExistingRedEnvelope()!=null){
				
				BigDecimal	redAmount = activityShareMember.getExistingRedEnvelope();
			
				BigDecimal useAmout = new BigDecimal(NumberFormatUtil.numberFormat(skuTotalPrice.multiply(BigDecimal.valueOf(0.3)))).setScale(0,BigDecimal.ROUND_HALF_UP);
				BigDecimal useAmountActual = redAmount.compareTo(useAmout)>-1?useAmout:redAmount;
				useAmountActual = useAmountActual.setScale(0,BigDecimal.ROUND_HALF_UP);
				return useAmountActual;
			}
			
			return BigDecimal.valueOf(0);
			
		}
	
	
	private BigDecimal discountRate(){
		SysmgrGlobalConfig config  = sysmgrGlobalConfigService.findByCode(AppConstant.APP_PRICE_DISCOUNT_RATE);
		if(ObjectUtils.notEqual(null, config)){
			return new BigDecimal(config.getValue());
		}
		return new BigDecimal(0);
	}

	/**
	 * 创建订单
	 * @param sid
	 * @param payoffId
	 * @param addressId
	 * @param isPoint
	 * @param sourceCode
	 * @param memo
	 * @param isCardNo
	 * @param cardNo
	 * @param isPickUp 是否自取
	 * @return
	 */
	@ResponseBody
	@RequestMapping("creatOrder")
	public Object creatOrder(@RequestParam("sid") String sid,@RequestParam("payoffId") String payoffId,
			@RequestParam("addressId") String addressId,
			@RequestParam(value="isPoint",defaultValue="n") String isPoint,
			@RequestParam("sourceCode") String sourceCode,
			@RequestParam(value="memo",defaultValue="") String memo,
			@RequestParam(value="isCardNo",defaultValue="n") String isCardNo,
			@RequestParam(value="cardNo",defaultValue="")String cardNo,
			@RequestParam(value="isInvoice",defaultValue="") String isInvoice,
			@RequestParam(value="invoiceType",defaultValue="") String invoiceType,
			@RequestParam(value="invoiceTitle",defaultValue="") String invoiceTitle,
			@RequestParam(value="sendDate",defaultValue="") String sendDate,
			@RequestParam(value="isRedUse",defaultValue="n") String isRedUse,
			@RequestParam(value="isPickUp",defaultValue="n") String  isPickUp
			) {
		try {
			if (StringUtils.isBlank(sid)) {
				return Jsonp.paramError("sid不能为空!");
			}
			if (CheckIsEmpty.isEmpty(payoffId, addressId, isPoint, sourceCode, isCardNo)) {
				return Jsonp.paramError(CommonConstant.CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto user = userFacade.getLatestUserBySid(sid);
			if (ObjectUtils.equals(null, user)) {
				return Jsonp.noLoginError("请先登录!");
			}
			/*MemberAddress selectArea = memberAddressService.findById(Long.valueOf(addressId));
			boolean checkIsSelectedPaymentType	= gotoOrderFacade.checkIsSupportHDFK(selectArea.getProvince());
			if(!checkIsSelectedPaymentType){
				return Jsonp.error("此区域暂不支持货到付款，请选择其它支付方式");
			}*/
			/*List<Long> couponsIdList = gotoOrderFacade.getCouponsList(orderParam.getCouponsIds());
			if( ObjectUtils.equals(null, couponsIdList)){
				return Jsonp.paramError("coupons_ids参数内容有误,必须是|号分隔,整数!");
			}*/
			/*if(StringUtils.equals(OrderConstant.GOTO_CART_PAYMENT, orderParam.getGotoType())){
				List<ShoppingCartSkuUser> cartItemList = soShoppingCartSkuWebService.findSelectedCartSkuByMemberId(user.getMemberId());// 根据购物车id查询所有被选中的行记录
				if (CollectionUtils.isEmpty(cartItemList)) {
					// 如果购物车没有勾选的商品列表,给客户端提示
					return Jsonp.paramError("亲!请选择您要购买的商品!在结算!");
				}
			}*/
			Long memberId = user.getMemberId();
			ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
			shoppingCartBo.setUser(user);
			Jsonp checkSkuInventory = createOrderFacade.checkSkuInventory(shoppingCartBo);
			if(!StringUtils.equals(checkSkuInventory.getResponseCode(),CommonCode.SUCCESS_CODE )){
				return checkSkuInventory;
			}
			OrderParamUser orderParam = new OrderParamUser();
			orderParam.setMemberId(memberId);
			orderParam.setSendDate(SendDateEnum.getTitle(sendDate));
			orderParam.setPayOffId(payoffId);	
			orderParam.setAddressId(addressId);
			orderParam.setIsPoint(isPoint);
			orderParam.setIsInvoice(isInvoice);
			orderParam.setInvoiceTitle(invoiceTitle);
			orderParam.setInvoiceType(invoiceType);
			orderParam.setPointUsd( NumberFormatUtil.numberFormat(user.getEpoints()));
			orderParam.setSourceCode(sourceCode);
			orderParam.setMemo(memo);
			orderParam.setIsCardNo(isCardNo);
			orderParam.setCardNo(cardNo);
			orderParam.setIsPickUp(isPickUp);//是否自取
			orderParam.setStoreCode(StoreConstant.STORE_CODE);
			String orderCode = EpointsUtil.generateOrdrNo();
			orderParam.setOrderCode(orderCode);
			BigDecimal useRedAmount = new BigDecimal(0);

			createOrderFacade.initOrderPriceInOrderParam(orderParam);
			if(StringUtils.equalsIgnoreCase(isRedUse, CommonConstant.YES)){
				useRedAmount = this.useRed(memberId, payoffId);
			}
			orderParam.setRedEnvelopeAmount(useRedAmount);
			
			createOrderFacade.saveOrder(orderParam);
		
			List<ShoppingCartSku> cartItemList = createOrderFacade.saveOrderLineByCart(orderParam.getOrderCode(), shoppingCartBo);
			createOrderFacade.updateInventoryByCart(cartItemList);
			createOrderFacade.deleteCartByCart(cartItemList);
			createOrderFacade.saveOrderMemberAddress(orderParam);
			memberService.updateDefaultAddressIdByMemberId(memberId, Long.valueOf(addressId));	
			if(StringUtils.equalsIgnoreCase(isRedUse, CommonConstant.YES) && useRedAmount!=null && 
					useRedAmount.compareTo(BigDecimal.valueOf(0))>0   ){
				this.redEnvlopeUseRecord(memberId,orderCode,useRedAmount);
			}
			return Jsonp_data.success(orderCode);
		} catch (Exception e) {
			logg.error("create订单创建异常:"+e);
			e.printStackTrace();
			return Jsonp.error("创建订单异常");
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
		BigDecimal skuTotalPrice = (BigDecimal)DataCache.get(payoffId + SettlementConstant._SKU_PRICE);
		BigDecimal redAmount = activityShareMember.getExistingRedEnvelope();
		
		BigDecimal useAmout = new BigDecimal(NumberFormatUtil.numberFormat(skuTotalPrice.multiply(BigDecimal.valueOf(0.3)))).setScale(0,BigDecimal.ROUND_HALF_UP);
		BigDecimal useAmountActual = redAmount.compareTo(useAmout)>-1?useAmout:redAmount;
	
		
		BigDecimal balanceAmount = redAmount.subtract(useAmountActual);
		if (balanceAmount.compareTo(BigDecimal.ZERO)<0) {
			balanceAmount = BigDecimal.ZERO;
		}
		activityShareMember.setExistingRedEnvelope(balanceAmount);
		activityShareMemberService.update(activityShareMember);
		
		return useAmountActual;
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
		activityRedEnvlopeUseRecord.setType(Integer.valueOf(RedEnvelopeUseTypeEnum.CONSUME.getCode()));/** 消费*/
		activityRedEnvlopeUseRecord.setUseStatus(Integer.valueOf(RedEnvelopeUseStatusEnum.MARCH.getCode()));/** 进行中*/
		activityRedEnvlopeUseRecord.setOrderCode(orderCode);
		activityRedEnvlopeUseRecord.setRedEnvelopeAmount(redEnvelopeAmount);
		activityRedEnvlopeUseRecordService.add(activityRedEnvlopeUseRecord);
		
	}
}
