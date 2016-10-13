package com.fushionbaby.wap.util;

import com.qq.connect.utils.URLEncodeUtils;

public class LoginUtilsConstant {
	/** qq的申请信息 */
	public static final String QQ_CLIENT_ID = "101179105";
	public static final String QQ_CLIENT_SECRET = "c722257cb127fb9d01a89b4fefd6c223";
	/** 第一次的回调地址 */
	public static final String QQ_REDIRECT_URL_FIRST = URLEncodeUtils
			.encodeURL("http://www.fushionbaby.com/login/callBackQQ.do");
	/** 第二次的回调地址 */
	public static final String QQ_REDIRECT_URL_SECOND = URLEncodeUtils
			.encodeURL("http://www.fushionbaby.com/login/callBackQQ2.do");

	/** 微信的申请信息 */
	public static final String WEIXIN_CLIENT_ID = "wxdcb2470548eef8c0";
	public static final String WEIXIN_CLIENT_SECRET = "1d5c6304d3d4c292ce6af34cc92d3ebf";
	/** 线上 */
	public static final String WEIXIN_REDIRECT_URL = URLEncodeUtils
			.encodeURL("http://www.fushionbaby.com/login/WeixinLogin.do");
	/** 本地 */

	/** sina的申请信息 */
	public static final String SINA_CLIENT_ID = "1184907740";
	public static final String SINA_CLIENT_SECRET = "753a2771a78a4243bdc2c31aff581f78";
	/** 线上 */
	public static final String SINA_REDIRECT_URL = URLEncodeUtils
			.encodeURL("http://www.fushionbaby.com/login/callBackSina.do");
	public static final String SINA_REDIRECT_URL2 = URLEncodeUtils
			.encodeURL("http://www.fushionbaby.com/login/callBackSina2.do");
	// .encodeURL("http://www.fushionbaby.com");

	public static final String WEB_LOGIN_ADDRESS = "http://www.fushionbaby.com";

}
