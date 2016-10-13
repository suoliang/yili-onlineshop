package com.aladingshop.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.card.model.MemberCard;
import com.aladingshop.card.service.MemberCardService;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.OrderExtraCommonFacade;
import com.fushionbaby.facade.biz.common.order.dto.OrderExtraCommonDto;

/**
 * 
 * @description 订单
 * @author 孟少博
 * @date 2015年8月18日下午6:35:11
 */
@Controller
@RequestMapping("/order")
public class OrderExtraController {

	@Autowired
	private OrderExtraCommonFacade orderExtraCommonFacade;

	@Autowired
	protected UserFacade userFacade;

	@Autowired
	private MemberCardService<MemberCard> memberCardService;

	/**
	 * 使用优惠券
	 * 
	 * @param couponCode
	 *            优惠券编号
	 * @param payOffId
	 *            支付交易码
	 * @param areaCode
	 *            地区编号
	 * @param isUseCouponCard
	 *            是否使用优惠券
	 * @param password
	 *            密码（可为空）
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "useCoupon", method = RequestMethod.POST)
	public Object useCoupon(String couponCode, String payOffId, String areaCode, String isUseCouponCard,
			String password, HttpServletRequest request) {
		// TODO 优惠券使用的时候要加入密码验证

		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isBlank(sid)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		UserDto user = userFacade.getLatestUserBySid(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}

		OrderExtraCommonDto orderExtraDto = new OrderExtraCommonDto();
		orderExtraDto.setCouponCode(couponCode);
		orderExtraDto.setPayOffId(payOffId);
		orderExtraDto.setMemberId(user.getMemberId());
		orderExtraDto.setFlag(SourceConstant.WEB_CODE);
		orderExtraDto.setIsUseCouponCard(isUseCouponCard);
		orderExtraDto.setAreaCode(areaCode);
		orderExtraDto.setPassword(password);
		Jsonp_data result = orderExtraCommonFacade.queryCoupon(orderExtraDto);

		return result;
	}

	/***
	 * 使用积分
	 * 
	 * @param payOffId
	 *            支付交易码
	 * @param isUsePoint
	 *            是否使用积分
	 * @param areaCode
	 *            地区编号
	 * @param isUseTax
	 *            是否有发票
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "usePoint", method = RequestMethod.POST)
	public Object usePoint(String payOffId, String isUsePoint, String areaCode, String isUseTax,
			HttpServletRequest request) {

		if (StringUtils.isBlank(payOffId)) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}

		if (!(StringUtils.equals(isUsePoint, CommonConstant.YES) || StringUtils.equals(isUsePoint, CommonConstant.NO))) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}

		if (!(StringUtils.equals(isUseTax, CommonConstant.YES) || StringUtils.equals(isUseTax, CommonConstant.NO))) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}

		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		UserDto user = userFacade.getLatestUserBySid(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		OrderExtraCommonDto orderDto = new OrderExtraCommonDto();
		orderDto.setSid(sid);
		orderDto.setPayOffId(payOffId);
		orderDto.setIsUsePoint(isUsePoint);
		orderDto.setAreaCode(areaCode);
		orderDto.setIsUseTax(isUseTax);
		orderDto.setEpoints(user.getEpoints() + "");
		return this.orderExtraCommonFacade.getIntegral(orderDto);

	}
}
