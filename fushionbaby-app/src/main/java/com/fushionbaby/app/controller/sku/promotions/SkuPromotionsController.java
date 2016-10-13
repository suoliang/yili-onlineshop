package com.fushionbaby.app.controller.sku.promotions;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.app.dto.PromotionSkuDto;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.condition.log.LogPromotionsRecordQueryConditon;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonCode;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.SettlementConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.GotoOrderDto;
import com.fushionbaby.common.dto.order.GotoOrderLineDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.EpointsUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrSfFreightConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.config.service.SysmgrSfFreightConfigService;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderLineUserService;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.core.vo.OrderParamUser;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.CreateOrderFacade;
import com.fushionbaby.facade.biz.common.order.GotoOrderFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.facade.image.ImageProcess;
import com.fushionbaby.log.model.LogPromotionsRecord;
import com.fushionbaby.log.service.LogPromotionsRecordService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.service.MemberAddressService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.model.SkuPromotions;
import com.fushionbaby.sku.model.SkuPromotionsInfo;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuPriceService;
import com.fushionbaby.sku.service.SkuPromotionsInfoService;
import com.fushionbaby.sku.service.SkuPromotionsService;
import com.fushionbaby.sku.service.SkuService;

/**
 * @description 活动信息
 * @author 孟少博
 * @date 2015年9月9日上午11:31:56
 */
@Controller
@RequestMapping("/sku/promotions")
public class SkuPromotionsController {
	
	@Autowired
	private LogPromotionsRecordService pmRecordService;
	@Autowired
	private SkuPromotionsService pmService;
	@Autowired
	private SkuPromotionsInfoService pmInfoService;
	
	@Autowired
	private SkuFacade skuFacade;
	
	@Autowired
	private SkuService skuService;
	
	@Autowired
	private ImageProcess imageProcess;
	
	@Autowired
	private GotoOrderFacade gotoOrderFacade;
	
	@Autowired
	private SysmgrGlobalConfigService configService;
	
	@Autowired
	private SkuPriceService<SkuPrice> skuPriceService;
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private CreateOrderFacade createOrderFacade;
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> soShoppingCartSkuWebService;
	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	@Autowired
	private UserFacade userFacade;
	@Autowired
	private OrderLineUserService<OrderLineUser> orderLineUserService;

	@Autowired
	private MemberAddressService<MemberAddress> memberAddressService;
	@Autowired
	private SysmgrSfFreightConfigService<SysmgrSfFreightConfig> sysmgrSfFreightConfigService;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;
	
	private static final String MOOMCAKE_88 = "88MOONCAKE";
	private static final String MOOMCAKE_68 = "68MOONCAKE";
	private static final String MOMOMCAKE_ACTIVITY = "yuebing";
	private static final String AS_ONE_WISHES = "asOneWishes";
	private static final String IS_RECORD_ORDER = "isRecordOrder";
	
	
	/**
	 * 是否弹出月饼窗口
	 * @return
	 */
	@ResponseBody
	@RequestMapping("isOpenMooncakeActivity")
	public Object isOpenMooncakeActivity(@RequestParam(value = "sid",defaultValue="") String sid){
		
		String pmCode = MOMOMCAKE_ACTIVITY; 
		SkuPromotionsInfo info = pmInfoService.queryByPmCode(pmCode);
		
		UserDto user = (UserDto) SessionCache.get(sid);
		if(user!=null && user.getMemberId()!=null){
			LogPromotionsRecordQueryConditon   queryCondition = new LogPromotionsRecordQueryConditon();
			queryCondition.setPmCode(pmCode);
			queryCondition.setMemberId(user.getMemberId());
			
			List<LogPromotionsRecord> recordList  = pmRecordService.queryByCondition(queryCondition);
			if(recordList!=null && recordList.size() > 0){
				return Jsonp.newInstance("300",  "不用弹出抢购月饼的窗口,已经参加过活动了");
			}
		}
		return  this.checkTimeRange(info);
	}
	
	/**
	 * 进入5元抢月饼活动
	 * @param sid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("comeMooncakeActivity")
	public Object comeMooncakeActivity(@RequestParam(value = "sid",defaultValue="") String sid){
		try {
			String pmCode = MOMOMCAKE_ACTIVITY; 
			SkuPromotionsInfo info = pmInfoService.queryByPmCode(pmCode);
			Jsonp checkTimeRangeResult = this.checkTimeRange(info);
			if(!StringUtils.equals(CommonCode.SUCCESS_CODE,checkTimeRangeResult.getResponseCode())){
				return checkTimeRangeResult;
			}
			UserDto user = (UserDto) SessionCache.get(sid);
			
			
			if(StringUtils.isNotBlank(sid) && user!=null && user.getMemberId() !=null){ 
				LogPromotionsRecordQueryConditon queryCondition = new LogPromotionsRecordQueryConditon();
				queryCondition.setMemberId(user.getMemberId());
				queryCondition.setPmCode(pmCode);
				List<LogPromotionsRecord> recordList = pmRecordService.queryByCondition(queryCondition);
				if(recordList!=null && recordList.size()>0){
					return Jsonp.newInstance("100" ,"你的抢购机会已用完！");
				}
			}
			
			LogPromotionsRecordQueryConditon queryConditionByPmCode = new LogPromotionsRecordQueryConditon();
			queryConditionByPmCode.setPmCode(pmCode);
			queryConditionByPmCode.setIsToDay(CommonConstant.YES);
			Integer total = pmRecordService.queryByCondition(queryConditionByPmCode).size();
			SkuDto skuDto = null;
			
			List<SkuPromotions> promotionsList = pmService.queryByPmCode(pmCode);
			if(promotionsList == null || promotionsList.size()<1){
				return Jsonp.newInstance("101" ,"商品已经抢完！");
			}
			SkuPromotions currentSku = null;
			if(total <= configService.getConfigValueByCode(MOOMCAKE_88)){
				for (SkuPromotions skuPromotions : promotionsList) {
					if(skuPromotions.getSpecialPrice().compareTo( BigDecimal.valueOf(88)) == 0){
						currentSku = skuPromotions;
						break;
					}
				}
				if(currentSku == null){
					return this.getRandomSku(promotionsList, info);
				}
				skuDto =  skuFacade.findBySkuCode(currentSku.getSkuCode());
				PromotionSkuDto result = this.getPromotionSkuDto(info, skuDto);
				return Jsonp_data.newInstance("111", result,"参加88元的月饼抢购活动！");
			}else if(total <=    configService.getConfigValueByCode(MOOMCAKE_68)){
				for (SkuPromotions skuPromotions : promotionsList) {
					if(skuPromotions.getSpecialPrice().compareTo(BigDecimal.valueOf(68)) == 0){
						currentSku = skuPromotions;
						break;
					}
				}
				if(currentSku == null){
					return this.getRandomSku(promotionsList, info);
				}
				skuDto =  skuFacade.findBySkuCode(currentSku.getSkuCode());
				PromotionSkuDto result = this.getPromotionSkuDto(info, skuDto);
				return Jsonp_data.newInstance("112" ,result,"参加68元的月饼抢购活动！");
			}
			
			
			return this.getRandomSku(promotionsList, info);
		} catch (Exception e) {
			e.printStackTrace();
			return Jsonp.error();
		}
	}
	/**
	 * 获取5元随机商品
	 * @param promotionsList 活动商品列表
	 * @param info 活动信息
	 * @return
	 */
	private Object getRandomSku(List<SkuPromotions> promotionsList,SkuPromotionsInfo info ){
		List<SkuPromotions> newPromotionSkus = new ArrayList<SkuPromotions>();
		for (SkuPromotions skuPromotions : promotionsList ) {
			if(skuPromotions.getSpecialPrice().compareTo(BigDecimal.valueOf(68)) == 0){
				continue;
			}
			if(skuPromotions.getSpecialPrice().compareTo(BigDecimal.valueOf(88)) == 0 ){
				continue;
			}
			newPromotionSkus.add(skuPromotions);
		}
		SkuPromotions skuPromotions = newPromotionSkus.get(new Random().nextInt(newPromotionSkus.size()));
		SkuDto skuDto =  skuFacade.findBySkuCode(skuPromotions.getSkuCode());
		PromotionSkuDto result = this.getPromotionSkuDto(info, skuDto);
		return Jsonp_data.newInstance("113" ,result,"5元随机商品区！");
	}
	/**
	 * 进入1元随心购活动
	 * @return
	 */
	@ResponseBody
	@RequestMapping("wishes")
	public Object comeAsOneWishes(@RequestParam(value = "sid",defaultValue="") String sid){
		String pmCode = AS_ONE_WISHES;
		if (StringUtils.isNotBlank(sid)) {
			
			UserDto user = (UserDto) SessionCache.get(sid);
			LogPromotionsRecordQueryConditon queryCondition = new LogPromotionsRecordQueryConditon();
			queryCondition.setMemberId(user.getMemberId());
			queryCondition.setPmCode(pmCode);
			queryCondition.setIsToDay(CommonConstant.YES);
			List<LogPromotionsRecord> recordList = pmRecordService.queryByCondition(queryCondition);
			if(recordList!=null && recordList.size()>0){
				return Jsonp.newInstance("100" ,"您今天的抢购机会已用完！");
			}
		}
		
		SkuPromotionsInfo info = pmInfoService.queryByPmCode(pmCode);
		Jsonp checkTimeRangeResult = this.checkTimeRange(info);
		if(!StringUtils.equals(CommonCode.SUCCESS_CODE,checkTimeRangeResult.getResponseCode())){
			return checkTimeRangeResult;
		}
		List<SkuPromotions> promotionsList = pmService.queryByPmCode(pmCode);
		if(CollectionUtils.isEmpty(promotionsList) || promotionsList.size() < 1){
			return Jsonp.newInstance("101" ,"商品已经抢完！");
		}
		List<String> skuCodeList = new ArrayList<String>();
		for (SkuPromotions skuPromotions : promotionsList) {
			skuCodeList.add(skuPromotions.getSkuCode());
		}
		List<SkuDto> skuList = skuFacade.findSkuListBySkuCodeList(skuCodeList);
		List<PromotionSkuDto> resultSkus = new ArrayList<PromotionSkuDto>();
		for (SkuDto skuDto : skuList) {
			resultSkus.add(this.getPromotionSkuDto(info, skuDto));
		}
		
		return Jsonp_data.newInstance("120" ,resultSkus,"进入1元随心购活动！");
	}
	/***
	 * 获取活动的商品信息
	 * @param info 活动信息
	 * @param skuDto 商品信息
	 * @return
	 */
	private PromotionSkuDto getPromotionSkuDto(SkuPromotionsInfo info,SkuDto skuDto){
		String pmCode = info.getPromotionsCode();
		PromotionSkuDto result = new PromotionSkuDto();
		result.setImgPath(StringUtils.trimToEmpty(skuDto.getImgPath()));
		result.setSkuCode(skuDto.getSkuCode());
		result.setSkuName(skuDto.getName());
		result.setOrigPrice(skuDto.getPriceNew());
		result.setSalesPrice(info.getSalesPrice().toString());
		result.setOverNum(this.getOverNum(pmCode, skuDto.getSkuCode()));
		result.setPmCode(pmCode);
		return result;
	}
	/**
	 * 获取该活动下  该商品剩余数量
	 * @param pmCode 活动编号
	 * @param skuCode 商品编号
	 * @return
	 */
	private Integer getOverNum(String pmCode,String skuCode){
		
		SkuPromotions skuPromotions = pmService.queryByPmCodeAndSkuCode(pmCode, skuCode);
		if(skuPromotions==null){
			return 0;
		}
		LogPromotionsRecordQueryConditon   queryCondition = new LogPromotionsRecordQueryConditon();
		queryCondition.setPmCode(pmCode);
		queryCondition.setSkuCode(skuCode);
		List<LogPromotionsRecord> recordList = pmRecordService.queryByCondition(queryCondition);
		if(skuPromotions.getLimitCount() == 0){
			return -1;
		}
		
		if(recordList == null){
			return  skuPromotions.getLimitCount();
		}
		return skuPromotions.getLimitCount() - recordList.size();
		
	}
	
	/**
	 * 验证活动时间范围
	 * @param pmCode
	 * @return
	 */
	private Jsonp checkTimeRange(SkuPromotionsInfo info){
		
		Long currentTimes = System.currentTimeMillis();
		
		if(info == null){
			return Jsonp.newInstance("999" ,"没有该活动！"); 
		}
		String[] timeRanges = info.getTimeRange().split(",");
		boolean flag = false;
		if(currentTimes >=info.getStartDate().getTime()  && currentTimes <= this.getEndDate(info.getEndDate())){
			
			if(timeRanges == null || StringUtils.isBlank(timeRanges[0]) ||  timeRanges.length < 1 ){
				flag = true;
			}else{
				for (String time : timeRanges) {
					if(StringUtils.isBlank(time)){
						continue;
					}
					 String sdtime =  DateFormatUtils.format(new Date(), "yyyy-MM-dd") +" "+time.split("-")[0] +":00";
					 String edtime = DateFormatUtils.format(new Date(), "yyyy-MM-dd") +" "+time.split("-")[1] +":00";
					try {
						Date startDate =  DateUtils.parseDate(sdtime, "yyyy-MM-dd HH:mm:ss");
						
						Date endDate =  DateUtils.parseDate(edtime, "yyyy-MM-dd HH:mm:ss");
						
						long currentTime = System.currentTimeMillis();
						if(currentTime > startDate.getTime() && currentTime< endDate.getTime()){
							flag = true;
							break;
						}
					} catch (ParseException e) {
						e.printStackTrace();
						return Jsonp.error();
					}
				}
			}
				
		}
		if(flag==false){
			return Jsonp.newInstance("105" ,"不在活动时间范围:" + info.getTimeRange()); 
		}
		return Jsonp.success();
	}
	/**
	 * 立即购买
	 * @param sid
	 * @param pmCode 活动编号
	 * @param skuCode 商品编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("nowBuy")
	public Object nowBuy(@RequestParam(value = "sid",defaultValue="") String sid,@RequestParam(value="pmCode",defaultValue="") String pmCode,
			@RequestParam(value="skuCode") String skuCode){
		
		if (StringUtils.isBlank(sid)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		if(ObjectUtils.equals(user, null)){
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}   
		if(StringUtils.isBlank(skuCode)){
			return Jsonp.error();
		}
		LogPromotionsRecordQueryConditon   queryCondition = new LogPromotionsRecordQueryConditon();
		queryCondition.setPmCode(pmCode);
		queryCondition.setMemberId(user.getMemberId());
		if(StringUtils.equals(pmCode, AS_ONE_WISHES)){
			queryCondition.setIsToDay(CommonConstant.YES);
		}
		
		List<LogPromotionsRecord> recordList  = pmRecordService.queryByCondition(queryCondition);
		if(recordList!=null && recordList.size() > 0){
			return Jsonp.newInstance("300",  "已经参加过活动了");
		}
		
		
		
		SkuPromotionsInfo info = pmInfoService.queryByPmCode(pmCode);
		try {
			
			BigDecimal price = null;
			if(StringUtils.isNotBlank(pmCode)){
				SkuPromotions skuPromotions = pmService.queryByPmCodeAndSkuCode(pmCode,skuCode);
				if(skuPromotions!=null && skuPromotions.getSpecialPrice() !=null && 
						StringUtils.equals(skuPromotions.getSkuPromotionsStatus(),CommonConstant.YES)	){
					if(this.getOverNum(pmCode, skuCode) <=0){
						return Jsonp.newInstance("1", "该商品已经抢购完");
					}
					
					if(info!=null){
						price = info.getSalesPrice();
					}
				}
			}
			GotoOrderDto gotoOrderDto  = new GotoOrderDto();
			
			List<GotoOrderLineDto> orderLines = new ArrayList<GotoOrderLineDto>();
			
			SkuDto sku = skuFacade.findBySkuCode(skuCode);
			if(sku==null){
				return Jsonp.error("没有这件商品");
			}
			if(price == null){
				price = new BigDecimal(sku.getPriceNew());
			}
			GotoOrderLineDto gotoOrderLine = new GotoOrderLineDto();
			gotoOrderLine.setColor(sku.getColor());
			gotoOrderLine.setCurrentPrice(price.toString());
			gotoOrderLine.setCurrentPriceTotal(price.toString());
			gotoOrderLine.setImgPath(imageProcess.getThumImagePath(sku.getSkuCode(), ImageStandardConstant.IMAGE_STANDARD_APP_190));
			gotoOrderLine.setRequestedQty(1);
			gotoOrderLine.setSize(sku.getSize());
			gotoOrderLine.setSkuCode(skuCode);
			gotoOrderLine.setSkuName(sku.getName());
			gotoOrderDto.setOrderLineItems(orderLines);
			gotoOrderDto.setQuantityTotal(1);
			BeanNullConverUtil.nullConver(gotoOrderLine);
			List<GotoOrderLineDto> lines = new ArrayList<GotoOrderLineDto>();
			lines.add(gotoOrderLine);
			gotoOrderDto.setOrderLineItems(lines);
			gotoOrderDto.setSkuTotal(price.toString());
			gotoOrderDto = gotoOrderFacade.giveGotoOrderDefaultAddress(gotoOrderDto, user.getMemberId());
			gotoOrderDto = gotoOrderFacade.initGotoOrder(user, gotoOrderDto);
			
			this.promotionsFee(gotoOrderDto,user.getMemberId());
			
		
			if(ObjectUtils.equals(null, gotoOrderDto)){
				return Jsonp.error();
			}
	
			BeanNullConverUtil.nullConver(gotoOrderDto);
			return Jsonp_data.success(gotoOrderDto);
		} catch (Exception e) {
			e.printStackTrace();
			return Jsonp.error();
		}
		
	}
	/**
	 * 活动时间内运费
	 * @param gotoOrderDto
	 * @param memberId
	 */
	private void promotionsFee(GotoOrderDto gotoOrderDto,Long memberId){
		
		
		SkuPromotionsInfo wishesInfo = pmInfoService.queryByPmCode(AS_ONE_WISHES);
		SkuPromotionsInfo cakeinfo = pmInfoService.queryByPmCode(MOMOMCAKE_ACTIVITY);
		LogPromotionsRecordQueryConditon queryCondition = new LogPromotionsRecordQueryConditon();
		queryCondition.setMemberId(memberId);
		List<LogPromotionsRecord> recordList  = pmRecordService.queryByCondition(queryCondition);
		BigDecimal freight = new BigDecimal(ObjectUtils.equals(null,gotoOrderDto.getFreight()) ? "0.00" : gotoOrderDto.getFreight());
		if(recordList!=null && recordList.size() > 0){
			for(LogPromotionsRecord record : recordList){
				if((record.getCreateTime().getTime() >= wishesInfo.getStartDate().getTime() && 
				    record.getCreateTime().getTime() <= this.getEndDate(wishesInfo.getEndDate())   )
				   || (record.getCreateTime().getTime() >= cakeinfo.getStartDate().getTime() && 
					   record.getCreateTime().getTime() <= this.getEndDate(cakeinfo.getEndDate()))  ){
					freight = BigDecimal.valueOf(0);
					break;
				}
			}
		}
		 
		BigDecimal orderTotalActual = new BigDecimal(gotoOrderDto.getSkuTotal()).add(freight);
		
		String payoffId = gotoOrderDto.getPayOffId();
		
		DataCache.put(payoffId + SettlementConstant._TOTAL_ACTUAL, orderTotalActual);// 订单总金额
		DataCache.put(payoffId + SettlementConstant._FREIGHT_PRICE, freight);// 运费
		
		gotoOrderDto.setTotalActual(orderTotalActual.toString());
		gotoOrderDto.setFreight(freight.toString());
		
	}
	
	
	/**
	 * 获取结束时间的时间戳
	 * @return
	 */
	private Long getEndDate(Date endDate){
		
		Long endDateTime = 0L;
		try {
			endDateTime = DateUtils.parseDate(DateFormatUtils.format(endDate, "yyyy-MM-dd") + " 23:59:59", "yyyy-MM-dd HH:ss:mm").getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return endDateTime;
	}
	
	
	/**
	 * 创建订单
	 * @param sid
	 * @param payoffId
	 * @param addressId
	 * @param sourceCode
	 * @param memo
	 * @param pmCode
	 * @param skuCode
	 * @param storeCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("createOrderByPromotions")
	public Object createOrderByPromotions(@RequestParam("sid") String sid,@RequestParam("payoffId") String payoffId,
			@RequestParam("addressId") String addressId,@RequestParam("sourceCode") String sourceCode,
			@RequestParam(value="memo",defaultValue="") String memo,
			@RequestParam("pmCode") String pmCode,@RequestParam("skuCode") String skuCode,
			@RequestParam(value="storeCode",defaultValue="") String storeCode){
		
		if (StringUtils.isBlank(sid)) {
			return Jsonp.paramError("sid不能为空!");
		}
		if (CheckIsEmpty.isEmpty(payoffId, addressId,sourceCode)) {
			return Jsonp.paramError(CommonConstant.CommonMessage.PARAM_ERROR_MESSAGE);
		}
		UserDto user = userFacade.getLatestUserBySid(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.noLoginError("请先登录!");
		}
		Long memberId = user.getMemberId();
		ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
		shoppingCartBo.setUser(user);
		shoppingCartBo.setStoreCode(storeCode);
		Jsonp checkSkuInventory = createOrderFacade.checkSkuInventory(shoppingCartBo);
		if(!StringUtils.equals(checkSkuInventory.getResponseCode(),CommonCode.SUCCESS_CODE )){
			return checkSkuInventory;
		}
		OrderParamUser orderParam = new OrderParamUser();
		orderParam.setPayOffId(payoffId);
		orderParam.setAddressId(addressId);
		orderParam.setIsPoint(CommonConstant.NO);
		orderParam.setPointUsd(user.getEpoints()+"");
		orderParam.setSourceCode(sourceCode);
		orderParam.setMemberId(memberId);
		orderParam.setMemo(memo);
		orderParam.setIsCardNo(CommonConstant.NO);
		orderParam.setCardNo(StringUtils.EMPTY);
		String orderCode = EpointsUtil.generateOrdrNo();
		orderParam.setOrderCode(orderCode);
		createOrderFacade.initOrderPriceInOrderParam(orderParam);
		createOrderFacade.saveOrder(orderParam);
		
		
		SkuPromotions skuPromotions =  pmService.queryByPmCodeAndSkuCode(pmCode,skuCode);
		this.saveOrderLineBySkuCode(orderCode, skuCode, memberId, skuPromotions.getSpecialPrice());
		createOrderFacade.saveOrderMemberAddress(orderParam);
		memberService.updateDefaultAddressIdByMemberId(memberId, Long.valueOf(addressId));
		Integer isRecord = configService.getConfigValueByCode(IS_RECORD_ORDER);
		try {
			if(isRecord!=null && isRecord>0){
				LogPromotionsRecord logPromotionsRecord = new LogPromotionsRecord();
				logPromotionsRecord.setOrderCode(orderCode);
				logPromotionsRecord.setSkuCode(skuCode);
				logPromotionsRecord.setPmCode(pmCode);
				logPromotionsRecord.setMemberId(user.getMemberId());
				logPromotionsRecord.setRecordStatus("1"); // 交易状态（1.未支付，2.已支付）
				pmRecordService.add(logPromotionsRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Jsonp_data.success(orderCode);
	}
	
	
	private void saveOrderLineBySkuCode(String orderCode,String skuCode,Long memberId,BigDecimal specialPrice){
		Sku skuEntry = skuService.queryBySkuCode(skuCode);
		SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(skuCode);
		OrderLineUser soSoLine = new OrderLineUser();
		soSoLine.setOrderCode(orderCode);
		soSoLine.setSkuCode(skuEntry.getUniqueCode());
		soSoLine.setLineType(skuExtraInfo.getIsGift());
		soSoLine.setColor(skuEntry.getColor());
		soSoLine.setSize(skuEntry.getSize());
		soSoLine.setSkuName(skuEntry.getName());
		soSoLine.setUnitPrice(specialPrice);// 商品现价
		soSoLine.setIsComment(CommonConstant.NO);
		soSoLine.setQuantity(1);
		soSoLine.setTotalActual(specialPrice);
		soSoLine.setTotalPrice(specialPrice);
		orderLineUserService.add(soSoLine);
		
	}	
	
	/**
	 * 提交订单后，添加活动商品交易记录
	 * @param sid
	 * @param orderCode 订单编号
	 * @param skuCode 商品编号
	 * @param pmCode 活动编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("record")
	public Object record(@RequestParam(value = "sid") String sid,String orderCode,String skuCode,String pmCode){
		if (StringUtils.isEmpty(sid)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		if(ObjectUtils.equals(user, null)){
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		} 
		try {
			LogPromotionsRecord logPromotionsRecord = new LogPromotionsRecord();
			logPromotionsRecord.setOrderCode(orderCode);
			logPromotionsRecord.setSkuCode(skuCode);
			logPromotionsRecord.setPmCode(pmCode);
			logPromotionsRecord.setMemberId(user.getMemberId());
			logPromotionsRecord.setRecordStatus("1"); // 交易状态（1.未支付，2.已支付）
			pmRecordService.add(logPromotionsRecord);
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Jsonp.error();
		}
	} 
	
	
}
