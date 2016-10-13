package com.fushionbaby.facade.biz.common.order.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import util.ImageConstantFacade;

import com.aladingshop.card.model.MemberCard;
import com.aladingshop.card.model.MemberCardConfig;
import com.aladingshop.card.service.MemberCardConfigService;
import com.aladingshop.card.service.MemberCardService;
import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.cache.service.GlobalConfig;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.enums.OrderConfigClientEnum;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.enums.RedEnvelopeUseStatusEnum;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CalcPageUtil;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.core.model.OrderBaseUser;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.model.OrderMemberAddressUser;
import com.fushionbaby.core.model.OrderTraceUser;
import com.fushionbaby.core.model.OrderTransUser;
import com.fushionbaby.core.model.request.OrderUserReq;
import com.fushionbaby.core.model.response.EvaluateOrderUserRes;
import com.fushionbaby.core.model.response.OrderUserRes;
import com.fushionbaby.core.service.OrderBaseUserService;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.core.service.OrderLineUserService;
import com.fushionbaby.core.service.OrderMemberAddressUserService;
import com.fushionbaby.core.service.OrderTraceUserService;
import com.fushionbaby.core.service.OrderTransUserService;
import com.fushionbaby.core.vo.AppOrderDetail;
import com.fushionbaby.core.vo.OrderUser;
import com.fushionbaby.core.vo.SkuByOrderLine;
import com.fushionbaby.facade.biz.common.order.OrderMemCenterFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.SkuImage;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.service.SkuImageService;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.google.common.base.Objects;

@Service
public class OrderMemCenterFacadeImpl implements OrderMemCenterFacade {
	
	
	private static final Log LOGGER = LogFactory.getLog(OrderMemCenterFacadeImpl.class);
	/** 代金券service */
	@Autowired
	private ActCardService<ActCard> actCardService;
	/** 商品库存操作service */
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeService;
	@Autowired
	private OrderBaseUserService<OrderBaseUser> orderBaseUserService;
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;
	@Autowired
	private OrderTransUserService<OrderTransUser> orderTransUserService;
	@Autowired
	private OrderTraceUserService<OrderTraceUser> orderTraceUserService;
	@Autowired
	private OrderMemberAddressUserService<OrderMemberAddressUser> orderMemberAddressUserService;
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaConfigService;
	@Autowired
	private SkuImageService<SkuImage> skuImageService;
	@Autowired
	private OrderLineUserService<OrderLineUser> orderLineUserService;
	@Autowired
	private GlobalConfig globalConfig;
	
	
	@Autowired
	private ActivityRedEnvlopeUseRecordService<ActivityRedEnvlopeUseRecord> activityRedEnvlopeUseRecordService;
	
	@Autowired
	private ActivityShareMemberService<ActivityShareMember> activityShareMemberService;
	
	/** 益多宝配置*/
	@Autowired
	private MemberCardConfigService<MemberCardConfig> memberCardConfigService;
	/** 益多宝*/
	@Autowired
	private MemberCardService<MemberCard> memberCardService;
	
	public OrderUserRes getOrderListByParam(OrderUserReq orderUserReq) {
		OrderUserRes res = new OrderUserRes();
		List<OrderUser> orderUserList = new ArrayList<OrderUser>();
		// 封装查询map
		Map<String, Object> paramMap = getParamMap(orderUserReq);
		Integer total = orderBaseUserService.getTotal(paramMap);
		// 获取当前数据总数
		orderUserReq.setTotal(total);
		// 获取limit上下限
		CalcPageUtil.setTotalPageByObject(orderUserReq, paramMap);
		res.setTotal(total);
		// 根据map获取当前分页数据
		List<OrderBaseUser> currentPageOrders = orderBaseUserService
				.getListPage(paramMap);
		// 遍历集合获取其它数据
		for (OrderBaseUser ub : currentPageOrders) {
			// 根据当前订单base信息获取财务、费用相关信息
			if (StringUtils.isBlank(paramMap.get("orderCode").toString())) {
				paramMap.put("orderCode", ub.getOrderCode());
			}
			OrderFinanceUser orderFinanceUser = orderFinanceUserService
					.findByParam(paramMap);
			OrderFeeUser orderFeeUser = orderFeeUserService
					.findByParam(paramMap);
			OrderTransUser transUser = orderTransUserService
					.findByParam(paramMap);
			/** 出现null数据订单数据不对应 */
			if (CheckObjectIsNull.isNull(orderFinanceUser, orderFeeUser,transUser)) {
				continue;
			}
			OrderMemberAddressUser addressUser = orderMemberAddressUserService
					.getOrderAddress(ub.getOrderCode());

			OrderUser orderUser = getOrderUserInfo(ub, orderFinanceUser,
					orderFeeUser, transUser, addressUser);
			
			paramMap.put("orderCode", "");
			BeanNullConverUtil.nullConver(orderUser);
			orderUserList.add(orderUser);
		}
		res.setOrderList(orderUserList);
		return res;
	}
	
	/**
	 * 获取需要的订单信息,其它额外信息往此处添加
	 * @param orderBaseUser
	 * @param orderFinanceUser
	 * @param orderFeeUser
	 * @param transUser
	 * @param addressUser
	 * @return
	 */
	private OrderUser getOrderUserInfo(OrderBaseUser orderBaseUser,
			OrderFinanceUser orderFinanceUser, OrderFeeUser orderFeeUser,
			OrderTransUser transUser, OrderMemberAddressUser addressUser) {
		OrderUser orderUser = new OrderUser();
		orderUser.setOrderStatus(orderBaseUser.getOrderStatus());
		orderUser.setOrderInfo(OrderConfigClientEnum.parseCode(orderBaseUser
				.getOrderStatus()));
		orderUser.setOrderCode(orderBaseUser.getOrderCode());
		orderUser.setMemo(orderBaseUser.getMemo() == null ? StringUtils.EMPTY
				: orderBaseUser.getMemo());
		orderUser.setSourceCode(orderBaseUser.getSourceCode());
		/**审核不通过原因*/
		orderUser.setFailReason(orderBaseUser.getAuditFailReason());
		String webPaymentType = "";
		String appPaymentType = "";
		if(StringUtils.isEmpty(orderFinanceUser.getPaymentType())){
			webPaymentType = PaymentTypeEnum.PAYMENT_ZFB_WEB_JSDZ.getCode();
			appPaymentType = PaymentTypeEnum.PAYMENT_ZFB_APP.getCode();
		}else{
			String paymentType = orderFinanceUser.getPaymentType();
			if (paymentType.contains("ZFB")) {
				webPaymentType = PaymentTypeEnum.PAYMENT_ZFB_WEB_JSDZ.getCode();
				appPaymentType = PaymentTypeEnum.PAYMENT_ZFB_APP.getCode();
			} else if(paymentType.contains("WX")){
				webPaymentType = PaymentTypeEnum.PAYMENT_WX_WEB.getCode();
				appPaymentType = PaymentTypeEnum.PAYMENT_WX_APP.getCode();
			} else if(paymentType.contains("ZXYL")){
				webPaymentType = PaymentTypeEnum.PAYMENT_ZXYL_WEB.getCode();
				appPaymentType = PaymentTypeEnum.PAYMENT_ZXYL_APP.getCode();
			}
		}
		orderUser.setAppPaymentType(appPaymentType);
		orderUser.setWebPaymentType(webPaymentType);
		orderUser.setActualTransferFee(NumberFormatUtil
				.numberFormat(orderFeeUser.getActualTransferFee()));
		orderUser.setCardAmount(NumberFormatUtil.numberFormat(orderFeeUser
				.getCardAmount()));
		orderUser.setEpointsMoney(NumberFormatUtil
				.numberFormat(orderFeeUser.getEpointsRedeemMoney()));
		orderUser.setPaymentTotalActual(NumberFormatUtil
				.numberFormat(orderFeeUser.getTotalActual()));
		orderUser.setRedAmount(this.useRedAmount(orderBaseUser.getOrderCode()));
		orderUser
				.setCreateOrderTime(orderBaseUser.getCreateTime() == null ? StringUtils.EMPTY
						: DateFormat.dateToString(orderBaseUser.getCreateTime()));
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(DateFormat.stringToDate(orderUser.getCreateOrderTime()));// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, +1); // 设置为后一天
		orderUser.setOrderEndTime(DateFormat.dateToString(calendar.getTime()));
		orderUser.setPaymentCompleteTime(orderFinanceUser
				.getPaymentCompleteTime() == null ? StringUtils.EMPTY
				: DateFormat.dateToString(orderFinanceUser
						.getPaymentCompleteTime()));
		orderUser.setTransStatus(transUser.getTransStatus());
		orderUser
				.setDeliveryTime(transUser.getDeliveryTime() == null ? StringUtils.EMPTY
						: DateFormat.dateToString(transUser
								.getDeliveryTime()));
		orderUser.setReceiver(addressUser==null?StringUtils.EMPTY:addressUser.getReceiver());
		orderUser.setReceiverMobile(addressUser==null?StringUtils.EMPTY:addressUser.getReceiverMobile());
		String province = memberAreaConfigService
				.getNameByAreaCode(addressUser==null?StringUtils.EMPTY:addressUser.getProvince());
		orderUser.setProvince(province==null?StringUtils.EMPTY:province);
		String city = memberAreaConfigService.getNameByAreaCode(addressUser==null?StringUtils.EMPTY:addressUser.getCity());
		orderUser.setCity(city==null?StringUtils.EMPTY:city);
		String district = memberAreaConfigService.getNameByAreaCode(addressUser==null?StringUtils.EMPTY:addressUser.getDistrict());
		orderUser.setDistrict(district==null?StringUtils.EMPTY:district);
		orderUser.setAddress(addressUser==null?StringUtils.EMPTY:addressUser.getAddress());
		orderUser.setCustomerServicePhone(globalConfig
				.findByCode(GlobalConfigConstant.CUSTOMER_SERVICE_PHONE));
		// 获取商品集合
		orderUser.setSkuByOrderLines(getSkusByCurrentOrder(orderBaseUser
				.getOrderCode()));
		
		/***APP订单详情开始**/
		List<AppOrderDetail> listAppDetail = new ArrayList<AppOrderDetail>();
		AppOrderDetail appOrderDetail = new AppOrderDetail();
		String  addre=StringUtils.EMPTY;
		String address = StringUtils.EMPTY;
		if(!ObjectUtils.equals(addressUser, null)){
			addre=addressUser.getReceiver()+" "+addressUser.getReceiverMobile();
			address = addressUser.getAddress();
		}
		String addressStr = "收件人:,"+addre+",收货地址:,"+province+	city+district+address;
		appOrderDetail.setAddressStr(addressStr.split("\\,"));
		
		StringBuffer orderStr = new StringBuffer();
		orderStr.append("订单状态:,"+OrderConfigClientEnum.parseCode(orderBaseUser.getOrderStatus())
				+",订单编号:,"+orderBaseUser.getOrderCode());
		if (!"-".equalsIgnoreCase(PaymentTypeEnum.getNameByCode(orderFinanceUser.getPaymentType()))) {			
			orderStr.append(",支付方式:,"+PaymentTypeEnum.getNameByCode(orderFinanceUser.getPaymentType()));
		}
		String createOrderTime = StringUtils.EMPTY;
		if (ObjectUtils.notEqual(null,orderBaseUser.getCreateTime())) {
			createOrderTime = DateFormat.dateToString(orderBaseUser.getCreateTime());
			orderStr.append(",下单时间:,"+createOrderTime);
		}
		if (StringUtils.isNotBlank(orderFinanceUser
				.getPaymentCompleteTime() == null ? StringUtils.EMPTY
				: DateFormat.dateToString(orderFinanceUser
						.getPaymentCompleteTime()))) {
			orderStr.append(",支付时间:,"+DateFormat.dateToString(orderFinanceUser.getPaymentCompleteTime()));
		}
		if (StringUtils.isNotBlank(transUser==null?StringUtils.EMPTY:transUser.getSendDate())) {
			orderStr.append(",配送时间:,"+transUser.getSendDate());
		}
		if (StringUtils.isNotBlank(orderBaseUser.getMemo() == null ? StringUtils.EMPTY
				: orderBaseUser.getMemo())) {
			orderStr.append(",买家留言:,"+orderBaseUser.getMemo());
		}
		if (StringUtils.isNotBlank(orderBaseUser.getAuditFailReason()==null?StringUtils.EMPTY
				: orderBaseUser.getAuditFailReason())) {
			orderStr.append(",审查不通过原因:,"+orderBaseUser.getAuditFailReason());
		}
		appOrderDetail.setOrderStr(orderStr.toString().split("\\,"));
		
		StringBuffer priceStr = new StringBuffer();
		priceStr.append("商品总额,￥"+NumberFormatUtil.numberFormat(orderFeeUser.getTotalAfDiscount())
				+",运费,￥"+NumberFormatUtil.numberFormat(orderFeeUser.getActualTransferFee()));
		if (ObjectUtils.equals(orderFeeUser.getStoreCode(), null)) {
			priceStr.append(",优惠券,-￥"+NumberFormatUtil.numberFormat(orderFeeUser.getCardAmount()));
		}
		priceStr.append(",如意消费卡折扣,-￥"+NumberFormatUtil.numberFormat(orderFeeUser.getAlabaoCheapAmount()));
		if (ObjectUtils.equals(orderFeeUser.getStoreCode(), null)) {
			priceStr.append(",红包,-￥"+NumberFormatUtil.numberFormat(orderFeeUser.getRedEnvelopeAmount()));
		}
//		if (ObjectUtils.equals(orderFeeUser.getStoreCode(), null)) {
//			priceStr.append(",积分,-￥"+NumberFormatUtil.numberFormat(orderFeeUser.getEpointsRedeemMoney()));
//		}
		appOrderDetail.setPriceStr(priceStr.toString().split("\\,"));
		
		listAppDetail.add(appOrderDetail);
		
		orderUser.setListAppDetail(listAppDetail);
		/***APP订单详情结束**/
		
		return orderUser;
	}

	/**
	 * 获取红包金额
	 * @param orderCode 订单编号
	 * @return
	 */
	private String useRedAmount(String orderCode){
		
		ActivityRedEnvlopeUseRecord redRecord = activityRedEnvlopeUseRecordService.findByOrderCode(orderCode);
		if(redRecord!=null && redRecord.getRedEnvelopeAmount().compareTo(BigDecimal.ZERO)==1){
			return NumberFormatUtil.numberFormat(redRecord.getRedEnvelopeAmount());
		}
		return NumberFormatUtil.numberFormat(BigDecimal.ZERO);
	}
	
	private List<SkuByOrderLine> getSkusByCurrentOrder(String orderCode) {
		List<SkuByOrderLine> results = new ArrayList<SkuByOrderLine>();
		// 根据当前订单信息获取商品行集合
		List<OrderLineUser> allLine = orderLineUserService.findByOrderCode(
				orderCode, null);
		if(orderCode.contains("ydb")){
			SkuByOrderLine skuByOrderLineCard = new SkuByOrderLine();
			String configId=orderCode.substring(0, orderCode.indexOf("ydb"));
			//MemberCardConfig memberCardConfig=this.memberCardConfigService.findById(Long.valueOf(configId));
			skuByOrderLineCard.setCreateOrderTime(allLine.get(0).getCreateTime() == null ? StringUtils.EMPTY
					: DateFormat.dateToString(allLine.get(0).getCreateTime()));
			skuByOrderLineCard.setSkuImg(ImageConstantFacade.YIDUOBAO_PICTURE_SERVER_PATH+configId+".jpg");
			skuByOrderLineCard.setSkuName("阿拉丁玛特益多宝");
			skuByOrderLineCard.setSkuPrice(allLine.get(0).getTotalActual().toString());
			skuByOrderLineCard.setLineTotalPrice(allLine.get(0).getTotalActual().toString());
			skuByOrderLineCard.setOrderLineId(allLine.get(0).getId());
			skuByOrderLineCard.setOrderCode(allLine.get(0).getOrderCode());
			skuByOrderLineCard.setSkuCode(allLine.get(0).getSkuCode());
			skuByOrderLineCard.setQuantity(allLine.get(0).getQuantity());
			BeanNullConverUtil.nullConver(skuByOrderLineCard);
			results.add(skuByOrderLineCard);
			return results;
		}
		// 封装信息
		for (OrderLineUser oline : allLine) {
			SkuByOrderLine skuByOrderLine = new SkuByOrderLine();
			// 商品图片还需关联其他表
			List<SkuImage> images = skuImageService.findBySkuCode(oline
					.getSkuCode());
			if (images != null && images.size() != 0)
				skuByOrderLine
						.setSkuImg(ImageConstantFacade.IMAGE_SERVER_ROOT_PATH
								+ images.get(0).getImgUrl());
			skuByOrderLine.setSkuName(oline.getSkuName());
			skuByOrderLine.setSkuPrice(NumberFormatUtil.numberFormat(oline
					.getUnitPrice()));
			skuByOrderLine.setSkuColor(oline.getColor());
			skuByOrderLine.setSkuSize(oline.getSize());
			skuByOrderLine.setIsComment(oline.getIsComment());
			skuByOrderLine.setQuantity(oline.getQuantity());
			skuByOrderLine.setOrderLineId(oline.getId());
			skuByOrderLine.setOrderCode(oline.getOrderCode());
			skuByOrderLine.setSkuCode(oline.getSkuCode());
			skuByOrderLine
					.setCreateOrderTime(oline.getCreateTime() == null ? StringUtils.EMPTY
							: DateFormat.dateToString(oline.getCreateTime()));
			skuByOrderLine.setLineTotalPrice(NumberFormatUtil
					.numberFormat(oline.getTotalPrice()));
			BeanNullConverUtil.nullConver(skuByOrderLine);
			results.add(skuByOrderLine);
		}
		return results;
	}

	private Map<String, Object> getParamMap(OrderUserReq orderUserReq) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderCode", orderUserReq.getOrderCode());
		paramMap.put("memberId", orderUserReq.getSid());
		paramMap.put("orderStatus",
				Objects.equal(orderUserReq.getOrderStatus(), "0") ? null
						: orderUserReq.getOrderStatus());
		if (StringUtils.isBlank(orderUserReq.getOrderStatus())
				&& StringUtils.isNotBlank(orderUserReq.getEvaluateStatus())) {
			paramMap.put("evaluateStatus", orderUserReq.getEvaluateStatus());
		}
		return paramMap;
	}

	public EvaluateOrderUserRes getEvaluateOrderListByParam(
			OrderUserReq orderUserReq) {
		// 封装查询map
		Map<String, Object> paramMap = getParamMap(orderUserReq);
		List<OrderBaseUser> currentPageOrders = orderBaseUserService
				.getListPage(paramMap);
		if (currentPageOrders == null || currentPageOrders.size() == 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for (OrderBaseUser orderBaseUser : currentPageOrders) {
			buffer.append("," + "'" + orderBaseUser.getOrderCode() + "'");
		}
		String result = buffer.toString();
		result = result.substring(1, result.length());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderCodes", result);
		Integer total = orderLineUserService.getTotal(params);
		// 获取当前数据总数
		orderUserReq.setTotal(total);
		// 获取limit上下限
		CalcPageUtil.setTotalPageByObject(orderUserReq, params);
		EvaluateOrderUserRes evaluateOrderUserRes = new EvaluateOrderUserRes();
		evaluateOrderUserRes.setTotal(total);
		List<OrderLineUser> orderLineList = orderLineUserService
				.getListPage(params);
		List<SkuByOrderLine> results = new ArrayList<SkuByOrderLine>();
		// 遍历集合获取其它数据
		for (OrderLineUser orderLineUser : orderLineList) {
			SkuByOrderLine skuByOrderLine = new SkuByOrderLine();
			// 商品图片还需关联其他表
			List<SkuImage> images = skuImageService.findBySkuCode(orderLineUser
					.getSkuCode());
			if (images != null && images.size() != 0) {
				skuByOrderLine
						.setSkuImg(ImageConstantFacade.IMAGE_SERVER_ROOT_PATH
								+ images.get(0).getImgUrl());
			}
			skuByOrderLine.setSkuName(orderLineUser.getSkuName());
			skuByOrderLine.setSkuPrice(NumberFormatUtil
					.numberFormat(orderLineUser.getUnitPrice()));
			skuByOrderLine.setSkuColor(orderLineUser.getColor());
			skuByOrderLine.setSkuSize(orderLineUser.getSize());
			skuByOrderLine.setQuantity(orderLineUser.getQuantity());
			skuByOrderLine.setOrderLineId(orderLineUser.getId());
			skuByOrderLine.setOrderCode(orderLineUser.getOrderCode());
			skuByOrderLine.setSkuCode(orderLineUser.getSkuCode());
			skuByOrderLine.setLineTotalPrice(orderLineUser.getTotalActual().toString());
			skuByOrderLine
					.setCreateOrderTime(orderLineUser.getCreateTime() == null ? ""
							: DateFormat.dateToString(orderLineUser
									.getCreateTime()));
			BeanNullConverUtil.nullConver(skuByOrderLine);
			results.add(skuByOrderLine);
		}
		evaluateOrderUserRes.setSkuByOrderLines(results);
		return evaluateOrderUserRes;
	}

	public void cancelOrder(Long memberId, String orderCode) {
		OrderBaseUser baseUser = orderBaseUserService
				.findObjectByMemIdAndOrdCode(memberId, orderCode);
		if (!CheckObjectIsNull.isNull(baseUser, baseUser.getTotalPointUsed())) {
			Member member = memberService.findById(memberId);
			BigDecimal epoints = member.getEpoints().add(baseUser.getTotalPointUsed());
			member.setEpoints(epoints);
			memberService.update(member);
		}
		// 查询订单行信息
		List<OrderLineUser> orderLineList = orderLineUserService
				.findByOrderCode(orderCode, "");
		for (OrderLineUser orderLine : orderLineList) {
			SkuInventory skuInventory = skuInventoryService
					.findBySkuCode(orderLine.getSkuCode());
			if (skuInventory != null) {
				// 更新库存量--增加
				int availableQty = skuInventory.getAvailableQty()
						+ orderLine.getQuantity();
				skuInventoryService.updateInventoryQuantity(
						orderLine.getSkuCode(), availableQty);
			}
		}
		OrderFeeUser orderFee = orderFeeService.findByMIdAndOrdCode(memberId,
				orderCode);
		// 代金券号如果存在，说明使用了代金券
		if (orderFee.getCardno() != null) {
			actCardService.updateBySysByCardNo(orderFee.getCardno());
		}

		OrderBaseUser orderBaseUser = new OrderBaseUser();
		orderBaseUser.setOrderCode(orderCode);
		orderBaseUser.setMemberId(memberId);
		orderBaseUser
				.setOrderStatus(OrderConfigServerEnum.WEB_CANCEL.getCode());
		orderBaseUser.setCancelTime(new Date());
		orderBaseUserService.updateOrderStatus(orderBaseUser);
		OrderTraceUser orderTraceUser = new OrderTraceUser();
		orderTraceUser.setOrderCode(orderCode);
		orderTraceUser.setMemberId(memberId);
		orderTraceUser.setOrderStatus(OrderConfigServerEnum.WEB_CANCEL
				.getCode());
		orderTraceUserService.updatetTraceStatus(orderTraceUser);
		
		/** 返回红包*/
		this.redEnvlopeUse(orderCode);
		
	}
	
	/***
	 * 红包使用
	 * @param orderCode
	 */
	private void redEnvlopeUse(String orderCode){
		
		ActivityRedEnvlopeUseRecord activityRedEnvlopeUseRecord = activityRedEnvlopeUseRecordService.findByOrderCode(orderCode);
		if(activityRedEnvlopeUseRecord==null){
			return ;
		}
		if(activityRedEnvlopeUseRecord.getUseStatus() == 0){
			ActivityShareMember activityShareMember = activityShareMemberService.findByMemberId(activityRedEnvlopeUseRecord.getMemberId());
			BigDecimal newRedEnvelope = activityShareMember.getExistingRedEnvelope().add(activityRedEnvlopeUseRecord.getRedEnvelopeAmount());
			activityShareMember.setExistingRedEnvelope(newRedEnvelope);	
			activityShareMemberService.update(activityShareMember);
			activityRedEnvlopeUseRecord.setUseStatus(Integer.valueOf(RedEnvelopeUseStatusEnum.FAIL.getCode()));//返还红包金额
			activityRedEnvlopeUseRecordService.updateUseStatus(activityRedEnvlopeUseRecord);
		}
		
		LOGGER.info("取消红包更新正常");
		
	}

	public OrderUser getOrderInfo(Long memberId, String orderCode) {
		// 封装查询map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", memberId);
		paramMap.put("orderCode", orderCode);
		OrderBaseUser baseUser = orderBaseUserService
				.findObjectByMemIdAndOrdCode(memberId, orderCode);
		OrderFinanceUser orderFinanceUser = orderFinanceUserService
				.findByParam(paramMap);
		OrderFeeUser orderFeeUser = orderFeeUserService.findByParam(paramMap);
		OrderTransUser transUser = orderTransUserService.findByParam(paramMap);
		OrderMemberAddressUser addressUser = orderMemberAddressUserService
				.getOrderAddress(orderCode);
		/** 出现null数据订单数据不对应 */
		if (CheckObjectIsNull.isNull(orderFinanceUser, orderFeeUser, transUser)) {
			return null;
		}
		OrderUser orderUser = getOrderUserInfo(baseUser, orderFinanceUser, orderFeeUser, transUser, addressUser);
		BeanNullConverUtil.nullConver(orderUser);
		return orderUser;
	}

	public OrderLineUser findByOrderLineId(String orderLineId,String type) {
		if(CommonConstant.YES.equals(type)){
			this.orderLineUserService.updateIsComment(Long.valueOf(orderLineId), type);
		}
		OrderLineUser orderLine=this.orderLineUserService.findById(Long.valueOf(orderLineId));
		return  orderLine;
	}

	public OrderUserRes getEachOrderQuantity(OrderUserReq orderUserReq) {
		OrderUserRes res = new OrderUserRes();
		// 封装查询map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderCode", orderUserReq.getOrderCode());
		paramMap.put("memberId", orderUserReq.getSid());
		
		
		paramMap.put("orderStatus", orderUserReq.getOrderStatus());
		res.setTotal(orderBaseUserService.getTotal(paramMap));
		
		orderUserReq.setOrderStatus(OrderConfigClientEnum.WAITING_PAYMENT.getCode());
		paramMap.put("orderStatus", orderUserReq.getOrderStatus());
		res.setToBePaidNo(orderBaseUserService.getTotal(paramMap));
		
		orderUserReq.setOrderStatus(OrderConfigClientEnum.SUCCESS_AUDIT.getCode());
		paramMap.put("orderStatus", orderUserReq.getOrderStatus());
		res.setToBeShippedNo(orderBaseUserService.getTotal(paramMap));
		
		orderUserReq.setOrderStatus(OrderConfigClientEnum.SUCCESS_SHIPPED.getCode());
		paramMap.put("orderStatus", orderUserReq.getOrderStatus());
		res.setToBeReceivedNo(orderBaseUserService.getTotal(paramMap));
		
		orderUserReq.setOrderStatus(null);
		paramMap.put("orderStatus", orderUserReq.getOrderStatus());
		orderUserReq.setEvaluateStatus(CommonConstant.YES);
		if (StringUtils.isBlank(orderUserReq.getOrderStatus())
				&& StringUtils.isNotBlank(orderUserReq.getEvaluateStatus())) {
			paramMap.put("evaluateStatus", orderUserReq.getEvaluateStatus());
		}
		List<OrderBaseUser> currentPageOrders = orderBaseUserService.getListPage(paramMap);
		Integer total = 0;
		if (!CollectionUtils.isEmpty(currentPageOrders)) {
			StringBuffer buffer = new StringBuffer();
			for (OrderBaseUser orderBaseUser : currentPageOrders) {
				buffer.append("," + "'" + orderBaseUser.getOrderCode() + "'");
			}
			String result = buffer.toString();
			result = result.substring(1, result.length());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderCodes", result);
			total = orderLineUserService.getTotal(params);
		}
		res.setToBeEvaluatedNo(total);
		
		return res;
	}

	
}
