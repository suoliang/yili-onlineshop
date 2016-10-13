package com.fushionbaby.facade.biz.common.order;

import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.order.dto.OrderExtraCommonDto;

/***
 * 订单方面，app和web共有的方法
 * 
 * @author xupeijun
 *
 */
public interface OrderExtraCommonFacade {

	/***
	 * 得到运费的接口
	 * 
	 * @param sid
	 * @param payOffId
	 * @param areaCode
	 * @return
	 */
	public Object getFreight(OrderExtraCommonDto freightDto);
	
	
	/** 
	 * 获得红包金额
	 * @param sid
	 * @param payoffId
	 * @param isUse
	 * @return
	 */
	public Object  getRedEnvelop(Long memberId,String payoffId,String isUse,String areaCode);
	
	
	
	
	String canUsePoint(OrderExtraCommonDto integralDto);

	/***
	 * 获得积分的接口
	 * 
	 * @param sid
	 * @param payOffId
	 * @param isUseIntegral
	 * @param areaCode
	 * @param isUseTax
	 * @return
	 */
	public Object getIntegral(OrderExtraCommonDto integralDto);

	/**
	 * 使用账号余额
	 * 
	 * @param giftCardDto
	 * @return
	 */
	public Object useAccountBalance(OrderExtraCommonDto giftCardDto);

	/**
	 * 使用代金券
	 * 
	 * @param cardNoDto
	 *            代金券使用的参数
	 * @return
	 */
	public Object useCardNo(OrderExtraCommonDto cardDto);

	/**
	 * 查询优惠券
	 * 
	 * @param couponCode
	 *            优惠券编号
	 * @return
	 */
	public Jsonp_data queryCoupon(OrderExtraCommonDto orderExtraDto);
}
