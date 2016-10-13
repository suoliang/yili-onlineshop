package com.fushionbaby.app.controller.order;

import java.math.BigDecimal;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.card.model.MemberCard;
import com.aladingshop.card.service.MemberCardService;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.fushionbaby.act.activity.dto.MemberCouponReqDto;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.OrderExtraDto;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.coupon.MemberCouponFacade;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.OrderExtraCommonFacade;
import com.fushionbaby.facade.biz.common.order.dto.OrderExtraCommonDto;

/**
 * 生成订单前额外的操作
 */
@Controller
@RequestMapping("/order")
public class OrderExtraController {
	/** 日志记载 */
	private static final Log LOGGER = LogFactory.getLog(OrderExtraController.class);

	/** app，web共用的order */
	@Autowired
	private OrderExtraCommonFacade orderExtraCommonFacade;
	@Autowired
	private UserFacade userFacade;
	@Autowired
	private MemberCardService<MemberCard> memberCardService;
	
	
	@Autowired
	private ActivityShareMemberService<ActivityShareMember> activityShareMemberService;
	
	@Autowired
	private MemberCouponFacade memberCouponFacade;
	
	private static final String FLAG =CommonConstant.YES;
	
	/**
	 * 红包使用
	 * @sid
	 * @param redAmount 红包使用金额
	 * @param totalAmount 总价格
	 * @return
	 */
	@ResponseBody
	@RequestMapping("redEnvlopeUse")
	public Object rednvlopeUse(String sid, String isUse, String payoffId,
			@RequestParam(value = "couponCode",defaultValue="") String couponCode,
			@RequestParam(value = "areaCode", defaultValue = "") String areaCode){
		UserDto user = (UserDto) SessionCache.get(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.error(CommonMessage.NO_LOGIN);
		}
		MemberCouponReqDto reDto = null;
		if(StringUtils.isNotBlank(couponCode)){
			reDto = new MemberCouponReqDto();
			reDto.setAreaCode(areaCode);
			reDto.setCouponCode(couponCode);
			reDto.setPayOffId(payoffId);
			reDto.setSid(sid);
			reDto.setIsUseCouponCard(CommonConstant.NO);
			reDto.setMemberId(user.getMemberId());
		}
			
		
		if(StringUtils.equals(CommonConstant.YES, FLAG) && reDto!=null){
			
			Jsonp_data coupon = (Jsonp_data)memberCouponFacade.useMemberCoupon(reDto);
			OrderExtraDto orderExtraDtoCoupon = (OrderExtraDto)coupon.getData();
			BigDecimal totalActual = new BigDecimal(orderExtraDtoCoupon.getTotalActual());
			Jsonp_data result = (Jsonp_data)orderExtraCommonFacade.getRedEnvelop(user.getMemberId(), payoffId, isUse,areaCode);
			OrderExtraDto resultDto = (OrderExtraDto)result.getData();
			resultDto.setTotalActual(NumberFormatUtil.numberFormat( totalActual.subtract(new BigDecimal(resultDto.getRedEnvelop()) )) );
			result.setData(resultDto);
			return result;
			
			//return Jsonp_data.success(result);
		}
		
		return orderExtraCommonFacade.getRedEnvelop(user.getMemberId(), payoffId, isUse,areaCode);
		 
		
	}
	
	
	public static void main(String[] args) {
		
		OrderExtraDto orderExtraDto = new OrderExtraDto();
		orderExtraDto.setCouponMoney("10");
		Object obj = Jsonp_data.success(orderExtraDto);
		
		BigDecimal couponMoney = new BigDecimal(0);
		Jsonp_data coupon = (Jsonp_data)obj;
		OrderExtraDto orderExtra1 = (OrderExtraDto)coupon.getData();
		couponMoney = new BigDecimal(orderExtra1.getCouponMoney());
		
		
		OrderExtraDto orderExtraDto2 = new OrderExtraDto();
		orderExtraDto2.setTotalActual("100");
		Object obj2 = Jsonp_data.success(orderExtraDto2);
		
		Jsonp_data orderExtra = (Jsonp_data)obj2;
		
		OrderExtraDto orderExtra2 = (OrderExtraDto)orderExtra.getData();
		orderExtra2.setTotalActual(NumberFormatUtil.numberFormat( new BigDecimal(orderExtra2.getTotalActual()).add(couponMoney)));
		
		
		
		System.out.println(Jsonp_data.success(orderExtra2));
		
	}
	

	/**
	 * 计算运费接口
	 * 
	 * @param sid
	 * @param payoffId
	 *            结算序列ID
	 * @param areaCode
	 *            区域code
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getFreight")
	public Object getFreight(@RequestParam("sid") String sid, @RequestParam("payoffId") String payoffId,
			@RequestParam("areaCode") String areaCode) {
		try {
			if (CheckIsEmpty.isEmpty(sid, payoffId, areaCode)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			OrderExtraCommonDto freightDto = new OrderExtraCommonDto();
			freightDto.setAreaCode(areaCode);
			freightDto.setPayOffId(payoffId);
			return orderExtraCommonFacade.getFreight(freightDto);
		} catch (Exception e) {
			LOGGER.error("运费接口计算出错", e);
			return Jsonp.error();
		}
	}

	/**
	 * 计算积分接口
	 * 
	 * @param sid
	 * @param payoffId
	 * @param isUseIntegral
	 *            - y/n
	 * @param areaCode
	 *            -- 可为空
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getIntegral")
	public Object getIntegral(@RequestParam("sid") String sid, @RequestParam("payoffId") String payoffId,
			@RequestParam("isUseIntegral") String isUseIntegral,
			@RequestParam(value = "areaCode", defaultValue = "") String areaCode) {
		try {
			// 判断sid和is_use是否为空
			if (CheckIsEmpty.isEmpty(sid, payoffId)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			// 判断传的参数是不是y或n
			if (!(StringUtils.equals(isUseIntegral, CommonConstant.YES) || StringUtils.equals(isUseIntegral,
					CommonConstant.NO))) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			// 检查用户是否登陆。
			UserDto user = (UserDto) SessionCache.get(sid);
			if (ObjectUtils.equals(null, user)) {
				return Jsonp.error(CommonMessage.NO_LOGIN);
			}
			OrderExtraCommonDto integralDto = new OrderExtraCommonDto();
			integralDto.setSid(sid);
			integralDto.setPayOffId(payoffId);
			integralDto.setIsUsePoint(isUseIntegral);
			integralDto.setAreaCode(areaCode);
			integralDto.setEpoints(user.getEpoints().toString());
			return this.orderExtraCommonFacade.getIntegral(integralDto);
		} catch (Exception e) {
			LOGGER.error("计算积分出错", e);
			return Jsonp.error();
		}
	}

	/**
	 * 代金券获取接口
	 * 
	 * @param sid
	 * @param payoffId
	 * @param cardNo
	 * @param isUseCardno
	 *            - y/n
	 * @param areaCode
	 *            -- 可为空
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCardno")
	public Object getCardno(@RequestParam("sid") String sid, @RequestParam("payoffId") String payoffId,
			@RequestParam("cardNo") String cardNo, @RequestParam("isUseCardno") String isUseCouponCard,
			@RequestParam(value = "areaCode", defaultValue = "") String areaCode,@RequestParam(value="password",defaultValue="")String password) {
		try {
			if (CheckIsEmpty.isEmpty(sid, cardNo, payoffId)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			// 判断代金券是否使用传的参数是不是y或n
			if (!(StringUtils.equalsIgnoreCase(isUseCouponCard, CommonConstant.YES) || StringUtils.equalsIgnoreCase(
					isUseCouponCard, CommonConstant.NO))) {
				return Jsonp.paramError("使用代金券参数为y/n");
			}
			// 检查用户是否登陆。
			UserDto user = userFacade.getLatestUserBySid(sid);
			if (ObjectUtils.equals(null, user)) {
				return Jsonp.error(CommonMessage.NO_LOGIN);
			}
			OrderExtraCommonDto cardDto = new OrderExtraCommonDto();
			cardDto.setPayOffId(payoffId);
			cardDto.setIsUseCouponCard(isUseCouponCard);
			cardDto.setCouponCode(cardNo);
			cardDto.setAreaCode(areaCode);
			cardDto.setSid(sid);
			cardDto.setMemberId(user.getMemberId());
			cardDto.setFlag(SourceConstant.APP_CODE);
			cardDto.setPassword(password);
			return orderExtraCommonFacade.useCardNo(cardDto);
		} catch (Exception e) {
			LOGGER.error("代金券计算出错", e);
			return Jsonp.error();
		}
	}
}
