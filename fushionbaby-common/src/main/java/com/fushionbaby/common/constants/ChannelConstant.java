package com.fushionbaby.common.constants;

import java.util.HashMap;
import java.util.Map;

/***
 * 渠道常量类
 * 
 * @author King 索亮
 */
public class ChannelConstant {
	/** 默认登陆方式 -- 1 */
	public static final String DEFAULT_CHANNEL = "1";
	/** QQ登陆方式 -- 2 */
	public static final String QQ_CHANNEL = "2";
	/** 微信登陆方式 -- 3 */
	public static final String WX_CHANNEL = "3";
	/** 新浪登陆方式 -- 4 */
	public static final String SINA_CHANNEL = "4";
	/** 支付宝登陆方式--5 */
	public static final String ZFB_CHANNEL = "5";

	public static Map<String, String> getChannelArray() {
		Map<String, String> channelMap = new HashMap<String, String>();
		channelMap.put("1", "默认登录方式");
		channelMap.put("2", "QQ登录方式");
		channelMap.put("3", "微信登录方式");
		channelMap.put("4", "新浪登录方式");

		return channelMap;
	}
}
