package com.fushionbaby.facade.biz.common.coupon;

import java.util.List;

import com.fushionbaby.act.activity.dto.MemberCouponReqDto;
import com.fushionbaby.common.dto.order.GotoOrderLineDto;
import com.fushionbaby.common.util.jsonp.Jsonp_data;

/***
 * 下单使用我的优惠券
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月6日上午9:25:23
 */
public interface MemberCouponFacade {

	/***
	 * 使用优惠券
	 * @param reDto
	 * @param couponCode  优惠券卡号
	 * @param payOffId   购物车结算标志
	 * @return
	 */
	Object useMemberCoupon(MemberCouponReqDto reDto);

	/***
	 * 注册赠送优惠券 
	 * @param id
	 */
	void registerSendCoupon(Long id);
	
	
	List<GotoOrderLineDto> getGotoOrderLineList(Long memberId) ;
	
	
	Jsonp_data getCardMoney(MemberCouponReqDto reDto) ;

	
}
