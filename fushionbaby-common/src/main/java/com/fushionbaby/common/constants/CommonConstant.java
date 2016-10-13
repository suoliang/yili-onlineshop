package com.fushionbaby.common.constants;

public class CommonConstant {

	public static final String YES = "y";
	public static final String NO = "n";
	
	public static final String STORE_CODE_SHOP = "999" ;

	/** 未登录状态 */
	public static final int NOT_LOGIN = 0;
	/** 已加入状态 */
	public static final int HAS_ADD = 1;
	/** 加入成功 */
	public static final int SUCCESS_ADD = 2;

	// 通用的消息
	public abstract class CommonMessage {
		public static final String FAILED_MESSAGE = "获取数据失败!";// 获取数据失败
		public static final String SUCCESS_MESSAGE = "请求数据成功!";// 获取数据失败
		public static final String ERROR_MESSAGE = "请求数据出错!!";// 获取数据出错!
		public static final String MD5_ERROR_MESSAGE = "MD5校验失败"; // MD5校验失败
		public static final String PARAM_ERROR_MESSAGE = "请求参数传递错误!!";// 参数传递错误
		public static final String SHOPPING_CART_FULL_MESSAGE = "亲!,您的购物车满了先下单结算吧^_^!";// 购物车满了
		public static final String NO_LOGIN = "用户未登录";
		public static final String PAY_OFF_FAILED_MSG = "订单结算序列失效!";
		public static final String ACOUNT_FAILURE = "支付密码错误!";
		public static final String SEARCH_FAILURE = "查询信息失败!";
		public static final String SMS_LIMIT_MESSAGE = "10分钟内只能获取3条";
	}

	// 通用的状态码
	public abstract class CommonCode {
		// 公共编码
		public static final String SUCCESS_CODE = "0";// 获取数据成功状态码
		public static final String ERROR_CODE = "500"; // 获取数据出错状态码
		public static final String PARAM_ERROR_CODE = "300";// 参数传递错误状态码
		public static final String NO_LOGIN_CODE = "201";// 请先登录
		public static final String SHOPPING_CART_FULL_CODE = "888";// 购物车满了
		public static final String PAY_OFF_FAILED = "202";// 202
															// 结算序列失效，请重新结算。跳到购物车结算页
		public static final String MD5_ERROR_CODE = "555"; // MD5校验失败状态码
		public static final String CURRENT_VERSION = "203";// 203表示系统最新的版本
		public static final String ACOUNT_FAILURE_CODE = "601"; // 账号支付密码验证失败码
		public static final String SEARCH_FAILURE_CODE = "602"; // 查询失败码
		public static final String SMS_LIMIT_CODE = "333";//短信发送量超出限制失败码

	}

}
