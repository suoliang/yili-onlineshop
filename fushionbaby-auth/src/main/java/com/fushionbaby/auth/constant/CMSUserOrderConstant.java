package com.fushionbaby.auth.constant;

/***
 * 关于后台操作订单的一些常量配置
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月26日上午9:06:07
 */
public class CMSUserOrderConstant {

	/**后台用户等级 1级可以处理所有的订单*/
	public static final Integer LEVEL_ONE=1;
	/**后台用户等级   2级只处理部分订单*/
	public static final Integer LEVEL_TWO=2;
	
	
	/** 订单的分配状态  默认未分配 1*/
	public static final Integer ORDER_DISTRIBUTION_STATUS_ONE=1;
	/** 订单的分配状态  已分配 2*/
	public static final Integer ORDER_DISTRIBUTION_STATUS_TWO=2;
	/** 订单的分配状态  分配已撤回 3*/
	public static final Integer ORDER_DISTRIBUTION_STATUS_THREE=3;
	
	
}
