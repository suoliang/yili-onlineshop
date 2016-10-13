/**
 * 
 */
package com.fushionbaby.common.constants;

/**
 * @author mengshaobo 定时任务常量
 */
public class ScheduleConstant {
	/** 一天的毫秒数 */
	public static final long ND = 1000 * 24 * 60 * 60;
	/** 一小时的毫秒数 */
	public static final long NH = 1000 * 60 * 60;
	/** 一分钟的毫秒数 */
	public static final long NM = 1000 * 60;
	
	/** 益多宝购卡时间 (天) */
	public static final int BACK_YIDUOBAO_TIMER = 30;
	/** 取消订单的时间 (小时) */
	public static final int CANCEL_TIME_ORDER = 24;
	/** 计算积分到会员的时间(小时) */
	public static final int COUNT_EPOINT_MEMBER = 24;
	/** cms已确认收货，用户未确认收货,超过一周强制用户确认收货*/
	public static final int FORCED_CONFIRM_RECEIPT = 7;
}
