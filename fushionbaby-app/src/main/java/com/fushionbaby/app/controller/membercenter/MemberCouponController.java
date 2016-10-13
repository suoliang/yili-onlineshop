package com.fushionbaby.app.controller.membercenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.act.activity.dto.MemberCouponReqDto;
import com.fushionbaby.act.activity.dto.MemberCouponResDto;
import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.model.ActCardType;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.act.activity.service.ActCardTypeService;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.OrderExtraDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.coupon.MemberCouponFacade;
import com.fushionbaby.facade.biz.common.order.OrderExtraCommonFacade;
import com.google.gson.Gson;

/***
 * 会员的优惠券  
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月31日下午1:19:40
 */
@Controller
@RequestMapping("/memberCoupon")
public class MemberCouponController {

	private static final Log LOGGER=LogFactory.getLog(MemberCouponController.class);
	
	/**代金券*/
	@Autowired
	private ActCardService<ActCard> actCardService;  
	
	/**代金券类型*/
	@Autowired
	private ActCardTypeService<ActCardType> actCardTypeService;  
	
	/** 优惠券*/
	@Autowired  
	private MemberCouponFacade memberCouponFacade;
	
	@Autowired
	private OrderExtraCommonFacade orderExtraCommonFacade;
	
	private static final String FLAG = CommonConstant.YES;
	
	
	/***
	 * 请求 data  sid
	 * 用户的优惠券列表
	 * @param data
	 * @param mac
	 * @return
	 */
	@RequestMapping("getMemberCouponList")
	@ResponseBody
	public Object getMemberCouponList(@RequestParam("data") String data, @RequestParam("mac") String mac){
		
		try {
			LOGGER.info("获取会员 优惠券列表  接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			MemberCouponReqDto reDto = gson.fromJson(data, MemberCouponReqDto.class);
			if (CheckIsEmpty.isEmpty(reDto.getSid())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto userDto= (UserDto)SessionCache.get(reDto.getSid());
			if (userDto == null) {
				return Jsonp.noLoginError("请先登录");
			}
			List<ActCard> actCardList=this.actCardService.findByMemberId(userDto.getMemberId());
			
			List<MemberCouponResDto> resDtoList=new ArrayList<MemberCouponResDto>();
			for (ActCard actCard : actCardList) {
				ActCardType cardType=this.actCardTypeService.findById(actCard.getCardTypeId());
				if(cardType==null||CommonConstant.YES.equals(cardType.getDisable())){
					return Jsonp.error("优惠券类型加载失败");
				}
				MemberCouponResDto resDto=new MemberCouponResDto();
				resDto.setCouponDesc(cardType.getDescription()==null?"全场购物可用":cardType.getDescription());
				resDto.setCouponMoney(cardType.getDiscountMoney());
				resDto.setCouponCode(actCard.getCardNo());
				resDto.setCouponTimeDesc("有限期："+DateFormat.dateTimeToDateString(actCard.getStartTime())+" 至 "+DateFormat.dateTimeToDateString(actCard.getStopTime()));
				if(actCard.getUsedTime()!=null){//已使用过
					resDto.setStatus("2");
				}
				if(actCard.getStopTime().before(new Date())){//已过期
					resDto.setStatus("3");
				}
				if(actCard.getStartTime().after(new Date())){
					resDto.setStatus("4");
				}
				if(actCard.getStopTime().after(new Date())&&actCard.getUsedTime()==null&&actCard.getStartTime().before(new Date())){
					resDto.setStatus("1");
				}
				resDto.setPassword(actCard.getPassword()==null?"":actCard.getPassword());
				resDtoList.add(resDto);
			}
			return Jsonp_data.success(resDtoList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("app MemberCouponController.java 接口 getMemberCouponList  异常", e);
			return Jsonp.error("会员优惠券列表加载异常");
		}
	}
	
	
	
	/***
	 * 会员使用代金券
	 * @param data
	 * @param mac
	 * @return
	 */
	@RequestMapping("useMemberCoupon")
	@ResponseBody
	public Object useMemberCoupon(@RequestParam("data") String data, @RequestParam("mac") String mac){
		
		try {
			LOGGER.info("app MemberCouponController.java 会员使用优惠券接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) 
			{
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			MemberCouponReqDto reDto = gson.fromJson(data, MemberCouponReqDto.class);
			if (CheckIsEmpty.isEmpty(reDto.getSid(),reDto.getPayOffId(),reDto.getIsUseCouponCard(),reDto.getAreaCode())) 
			{
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			if(StringUtils.isBlank(reDto.getCouponCode()))
			{
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto userDto= (UserDto)SessionCache.get(reDto.getSid());
			if (userDto == null) {
				return Jsonp.noLoginError("请先登录");
			}
			reDto.setMemberId(userDto.getMemberId());
			
			if(StringUtils.equals(CommonConstant.YES, FLAG)){
				BigDecimal totalActual = new BigDecimal(0);
				 Jsonp_data obj = (Jsonp_data)orderExtraCommonFacade.getRedEnvelop(
						userDto.getMemberId(), reDto.getPayOffId(), CommonConstant.NO, reDto.getAreaCode());
				  
				 OrderExtraDto orderExtraDto = (OrderExtraDto)obj.getData();
				 totalActual = new BigDecimal(orderExtraDto.getTotalActual());
				 
				 Jsonp_data couponObj = (Jsonp_data)memberCouponFacade.useMemberCoupon(reDto);
				 
				 OrderExtraDto couponDto =(OrderExtraDto)couponObj.getData();
				 couponDto.setTotalActual(NumberFormatUtil.numberFormat( totalActual.subtract(  new BigDecimal(couponDto.getCouponMoney()))) );
				 couponObj.setData(couponDto);
				 
				 return couponObj;
			}
			
			
			
			return memberCouponFacade.useMemberCoupon(reDto);
		} catch (Exception e) {
			LOGGER.error("app MemberCouponController.java  接口 useMemberCoupon 异常", e);
			return Jsonp.error();
		}
		 
	}
	
	
	/***
	 * 请求 data  sid
	 * 用户的优惠券列表
	 * @param data
	 * @param mac
	 * @return
	 */
	@RequestMapping("getCanUseCouponList")
	@ResponseBody
	public Object getMemberCanUseCouponList(@RequestParam("data") String data, @RequestParam("mac") String mac){
		
		try {
			LOGGER.info("获取会员可用 优惠券列表 getCanUseCouponList 接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			MemberCouponReqDto reDto = gson.fromJson(data, MemberCouponReqDto.class);
			if (CheckIsEmpty.isEmpty(reDto.getSid())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto userDto= (UserDto)SessionCache.get(reDto.getSid());
			if (userDto == null) {
				return Jsonp.noLoginError("请先登录");
			}
			List<ActCard> actCardList=this.actCardService.findByMemberId(userDto.getMemberId());
			List<MemberCouponResDto> resDtoList=new ArrayList<MemberCouponResDto>();
			for (ActCard actCard : actCardList) {
				/**会员可用的优惠券列表*/
				if(actCard.getStopTime().after(new Date())&&actCard.getUsedTime()==null&&actCard.getStartTime().before(new Date())){
					ActCardType cardType=this.actCardTypeService.findById(actCard.getCardTypeId());
					if(cardType==null){
						return Jsonp.error("优惠券类型加载失败");
					}
					MemberCouponResDto resDto=new MemberCouponResDto();
					resDto.setCouponDesc(cardType.getDescription()==null?"全场购物可用":cardType.getDescription());
					resDto.setCouponMoney(cardType.getDiscountMoney());
					resDto.setCouponCode(actCard.getCardNo());
					resDto.setCouponTimeDesc("有限期："+DateFormat.dateTimeToDateString(actCard.getStartTime())+" 至 "+DateFormat.dateTimeToDateString(actCard.getStopTime()));
					resDto.setStatus("1");
					resDtoList.add(resDto);
				}
			}
			return Jsonp_data.success(resDtoList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("app MemberCouponController.java 接口 getCanUseCouponList  异常", e);
			return Jsonp.error("会员可用优惠券列表加载异常");
		}
	}
	
	
	
	
}
